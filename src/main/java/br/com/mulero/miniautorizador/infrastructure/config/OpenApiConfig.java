package br.com.mulero.miniautorizador.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    public static final String BASIC = "basic";
    public static final String BASIC_SCHEME = "basicScheme";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(getInfo())
                .components(getSecuritySchemes())
                .addSecurityItem(new SecurityRequirement().addList(BASIC_SCHEME));
    }

    private Info getInfo() {
        return new Info()
                .title("Mini Autorizador")
                .description("Mini Autorizador")
                .version("1.0.0");
    }

    private Components getSecuritySchemes() {
        SecurityScheme scheme = new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme(BASIC);
        return new Components().addSecuritySchemes(BASIC_SCHEME, scheme);
    }
}
