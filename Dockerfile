# Build Stage
# 1. Use maven image to build the project
FROM maven:3-eclipse-temurin-17-alpine AS build

# 2. Set the working directory
WORKDIR /app

# 3. Copy the pom.xml file
COPY pom.xml .

# 4. Download the dependencies. First time it will take time to download all the dependencies but next time it will be faster as it will be cached.
RUN mvn dependency:go-offline

# 5. Copy the source code
COPY src src

# 6. Build the project
RUN mvn package -DskipTests

# 7. Create a symbolic link to the latest .jar file
RUN ln -s $(ls -t target/*.jar | head -n 1) target/latest.jar

## Deploy Stage
# 1. Use the temurin image to deploy the application
FROM eclipse-temurin:17-alpine AS deploy

# 2. Set the working directory
WORKDIR /app

# 3. Copy the symbolic link (and the file it points to) from the build stage
COPY --from=build /app/target/latest.jar app.jar

# 4. Expose the port
EXPOSE 8080

# 5. Run the application
CMD ["java", "-jar", "app.jar"]