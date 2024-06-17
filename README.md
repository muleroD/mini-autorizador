# Mini Autorizador

Este é um projeto desenvolvido como parte de um desafio técnico para a VR Benefícios. O Mini Autorizador é uma aplicação
Spring Boot com interface REST que simula um sistema de autorização para transações de cartões. Ele permite criar
cartões com saldo inicial, consultar o saldo e autorizar transações com base em regras específicas.

## Começando

Estas instruções permitirão que você obtenha uma cópia do projeto em funcionamento na sua máquina local para fins de
desenvolvimento e teste.

### Pré-requisitos

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas na sua máquina:

- Java 17
- Maven 3.3.2 ou superior
- MySQL 5.7 (ou utilize o banco de dados disponível no `docker-compose.yml`)
- Docker

### Instalando

Siga os passos abaixo para configurar e executar o projeto:

1. Clone o repositório
   ```sh
   git clone https://github.com/muleroD/mini-autorizador.git
    ```
2. Navegue até o diretório do projeto `mini-autorizador`
3. Compile o projeto e instale as dependências
   ```sh
   mvn clean install
   ```
4. Inicie o banco de dados MySQL utilizando Docker Compose
   ```sh
   docker-compose up mysql -d
   ```
    * Este comando irá iniciar um container Docker com o MySQL conforme as configurações do arquivo `docker-compose.yml`

5. Execute o comando abaixo para rodar a aplicação
   ```sh
    mvn spring-boot:run
    ```
    * A aplicação será iniciada e estará disponível em `http://localhost:8080`

Ao seguir esses passos, você terá o ambiente de desenvolvimento pronto para explorar e testar o Mini Autorizador
localmente.

## Funcionalidades

Todas as funcionalidades do projeto são acessadas via uma API REST. Além de criar cartões, consultar saldo e
realizar transações, o projeto também possui regras de autorização que devem ser respeitadas para que uma transação seja
autorizada.

Para realizar qualquer operação, é necessário autenticar-se usando o método Basic Auth. O usuário e senha padrão são
`username` e `password`, respectivamente.

Todas as transações são registradas em um banco de dados MySQL informando o cartão, o tipo de transação (Saque, Depósito
ou Transferência), o valor da transação e a data da transação.

O projeto possui as seguintes funcionalidades principais sendo:

### Criar novo cartão

Permite criar um novo cartão com um saldo inicial de R$500,00, conforme o contrato abaixo:

- **Método**: `POST`
- **URL**: `/cartoes`
- **Body (JSON)**:
  ```json
  {
      "numeroCartao": "1234567890123456",
      "senha": "1234"
  }
  ```
- **Autenticação**: Basic (login e senha)
- **Possíveis Respostas**:
    - **201 (Created)**: Cartão criado com sucesso.
    - **401 (Unauthorized)**: Erro de autenticação.
    - **422 (Unprocessable Entity)**: Cartão já existe.

### Obter saldo do Cartão

Permite consultar o saldo de um cartão, precisando apenas informar o número do cartão, conforme o contrato abaixo:

- **Método**: `GET`
- **URL**: `/cartoes/{numeroCartao}`
- **Autenticação**: Basic (login e senha)
- **Possíveis Respostas**:
    - **200 (OK)**: Retorna o saldo do cartão.
    - **401 (Unauthorized)**: Erro de autenticação.
    - **404 (Not Found)**: Cartão não encontrado.

### Realizar uma Transação

Permite realizar uma transação debitando o valor do saldo do cartão, se autorizado, conforme o contrato abaixo:

- **Método
- **URL**: `/transacoes`
- **Body (JSON)**:
  ```json
  {
      "numeroCartao": "1234567890123456",
      "senhaCartao": "1234",
      "valor": 100
  }
  ```
- **Autenticação**: Basic (login e senha)
- **Possíveis Respostas**:
    - **201 (Created)**: Transação realizada com sucesso.
    - **401 (Unauthorized)**: Erro de autenticação.
    - **422 (Unprocessable Entity)**: Transação barrada por alguma regra de autorização. Possíveis erros:
        - `CARTAO_INEXISTENTE`
        - `SENHA_INVALIDA`
        - `SALDO_INSUFICIENTE`

### Realizar uma Transação de Depósito

Tomando como base a funcionalidade de realizar uma transação, foi criada uma nova funcionalidade que permite realizar um
depósito no cartão, creditando o valor informado no saldo do cartão, conforme o contrato abaixo:

- **Método
- **URL**: `/transacoes/deposito`
- **Body (JSON)**:
  ```json
  {
      "numeroCartao": "1234567890123456",
      "senhaCartao": "1234",
      "valor": 100
  }
  ```
- **Autenticação**: Basic (login e senha)
- **Possíveis Respostas**:
    - **201 (Created)**: Depósito realizado com sucesso.
    - **401 (Unauthorized)**: Erro de autenticação.
    - **422 (Unprocessable Entity)**: Depósito barrado por alguma regra de autorização. Possíveis erros:
        - `CARTAO_INEXISTENTE`
        - `SENHA_INVALIDA`

- **Observação**: O valor do depósito é creditado no saldo do cartão.

### Realizar uma Transferência

Assim como a funcionalidade de depósito, foi criada também a funcionalidade de transferência, onde é possível transferir
um valor de um cartão para outro onde apenas as informações do cartão de envio e o número do cartão de destino são
necessários, conforme o contrato abaixo:

- **Método
- **URL**: `/transacoes/transferencia/{numeroCartaoDestino}`
- **Body (JSON)**:
  ```json
  {
      "numeroCartao": "1234567890123456",
      "senhaCartao": "1234",
      "valor": 100
  }
  ```
- **Autenticação**: Basic (login e senha)
- **Possíveis Respostas**:
    - **201 (Created)**: Transferência realizada com sucesso.
    - **401 (Unauthorized)**: Erro de autenticação.
    - **422 (Unprocessable Entity)**: Transferência barrada por alguma regra de autorização. Possíveis erros:
        - `CARTAO_INEXISTENTE`
        - `SENHA_INVALIDA`
        - `SALDO_INSUFICIENTE`

## Metodologias, Práticas e Padrões de Projeto

Durante o desenvolvimento do projeto Mini Autorizador, foram adotadas diversas metodologias e práticas de
desenvolvimento de software para garantir a qualidade e a eficiência do código. Abaixo estão destacadas algumas delas:

### Chain of Responsibility

O padrão de projeto Chain of Responsibility foi utilizado para implementar as regras de autorização das transações. Cada
regra de autorização é encapsulada em um handler que verifica se a transação pode ser autorizada ou não. Caso uma regra
não seja atendida, o handler correspondente retorna um erro indicando o motivo da não autorização.

A implementação da cadeia de responsabilidade ocorre no `TransactionValidator`, que é responsável por encadear os
handlers conforme o tipo de transação. Esta abordagem permite que as regras de autorização sejam independentes e
facilmente adicionadas ou removidas da cadeia.

Quando uma transação é submetida, o `TransactionValidator` inicia a validação chamando o primeiro handler da cadeia, que
por sua vez pode chamar o próximo handler. Os handlers são projetados para verificar informações cruciais como a
existência do cartão, a corretude da senha e a suficiência de saldo. Caso qualquer condição não seja satisfeita,
exceções são lançadas e a transação é rejeitada.

Estas práticas contribuem significativamente para a qualidade do código e a confiabilidade da aplicação, facilitando a
manutenção e evolução do sistema ao longo do tempo.

### Query by Example

O padrão Query by Example (QBE) do Spring Data JPA foi utilizado para realizar consultas dinâmicas no banco de dados.
Esta abordagem permite criar consultas de forma programática, sem a necessidade de escrever queries SQL manualmente.
Basta definir um objeto de exemplo com os campos que deseja consultar, e o Spring Data JPA se encarrega de montar a
query automaticamente.

Esta metodologia é particularmente útil para consultas simples, onde não é necessário criar métodos específicos no
repositório ou utilizar Specifications para definir critérios de busca mais complexos. O QBE simplifica o
desenvolvimento ao eliminar a necessidade de escrever código SQL repetitivo, promovendo uma maior produtividade e
manutenibilidade do código.

No contexto do projeto Mini Autorizador, o uso do QBE facilita a implementação de consultas flexíveis e eficientes,
alinhando-se com as boas práticas de desenvolvimento ágil e modular.

### Message Source

O Message Source do Spring foi utilizado para internacionalizar as mensagens de erro no projeto Mini Autorizador. Esta
funcionalidade permite criar arquivos de mensagens para cada idioma suportado pela aplicação e injetar essas mensagens
diretamente no código. Dessa forma, é possível retornar mensagens de erro em diferentes idiomas conforme necessário.

A classe `I18nConfig` é responsável por configurar o Message Source e carregar os arquivos de mensagens. No
pacote `i18n`, estão localizados os arquivos de mensagens para cada idioma suportado. Atualmente, a aplicação suporta
mensagens em português (`messages_pt_BR.properties`) e inglês (`messages_en_US.properties`).

Essa abordagem não apenas melhora a experiência do usuário ao apresentar mensagens em seu idioma preferido, mas também
facilita a manutenção do código ao separar as mensagens do código-fonte principal. O uso do Message Source do Spring
promove uma aplicação mais robusta e adaptável às necessidades de internacionalização.

### Exception Handler

O `GlobalExceptionHandler` foi implementado no projeto Mini Autorizador para tratar exceções de forma centralizada. Este
componente intercepta todas as exceções lançadas pela aplicação e retorna uma resposta padronizada contendo o status
HTTP apropriado e uma mensagem de erro descritiva.

A principal vantagem do `GlobalExceptionHandler` é a centralização do tratamento de exceções, o que promove uma
experiência consistente para o usuário final ao apresentar mensagens de erro claras e informativas. Além disso, ele
utiliza o Message Source do Spring para internacionalizar as mensagens de erro, garantindo que as respostas sejam
adequadas ao idioma configurado na aplicação.

Para padronizar as respostas de erro da API, foi adotado o `ProblemDetail` seguindo o padrão RFC 7807. O `ProblemDetail`
é um formato de resposta estruturada que inclui informações essenciais sobre o erro, como o status HTTP, o tipo do erro,
a mensagem detalhada e a data e hora em que o erro ocorreu.

Este padrão facilita a identificação e o tratamento de erros na aplicação, oferecendo a flexibilidade de ser estendido
para incluir informações adicionais relevantes para diagnóstico e resolução de problemas,
como `stacktrace`, `path`, `method`, entre outros.

A combinação do `GlobalExceptionHandler` e do `ProblemDetail` contribui significativamente para a manutenção da
qualidade da aplicação, permitindo uma gestão eficaz de exceções e uma resposta rápida às situações de erro que possam
ocorrer durante o uso da API.

### OpenAPI

O OpenAPI foi utilizado no projeto Mini Autorizador para documentar a API REST de forma padronizada e automatizada.
Utilizando a versão 3 da especificação, o OpenAPI permite descrever APIs RESTful de maneira detalhada usando JSON ou
YAML. Esta abordagem facilita a compreensão e utilização da API, pois descreve endpoints, métodos HTTP, parâmetros,
respostas e possíveis erros de forma estruturada.

Todos os aspectos do projeto foram cuidadosamente documentados seguindo a especificação do OpenAPI. Isso inclui a
descrição completa dos endpoints disponíveis, a definição dos métodos HTTP suportados, a documentação dos parâmetros
necessários para cada requisição, a especificação detalhada das respostas retornadas pela API e o mapeamento dos erros
que podem ocorrer durante a execução das operações.

A documentação da API foi gerada automaticamente a partir das especificações OpenAPI e está disponível para consulta
em [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html). Esta interface não apenas facilita a
visualização da estrutura da API, mas também permite que desenvolvedores testem os endpoints diretamente pela
documentação, promovendo uma integração mais fluida e eficiente.

O uso do OpenAPI no projeto Mini Autorizador contribui para a transparência e acessibilidade da API, garantindo que
todas as informações necessárias estejam claramente documentadas e acessíveis aos usuários e desenvolvedores que
interagem com o sistema.

### Security

O Spring Security foi adotado no projeto Mini Autorizador para assegurar a API e atender aos requisitos de segurança.
Utilizando o Spring Security, foi implementado um sistema de autenticação simples utilizando Basic Auth. Dada a
simplicidade do projeto, a autenticação foi configurada em memória, com usuário e senha fixos que podem ser modificados
no arquivo `application.yml`.

As configurações de segurança estão centralizadas no arquivo `SecurityConfig`, onde é possível definir regras de
autorização e especificar quais URLs devem ser protegidas. No contexto deste projeto, todas as URLs são protegidas,
exigindo autenticação para acessar qualquer recurso, exceto a documentação da API e o Actuator.

As senhas dos usuários são armazenadas de forma segura no banco de dados utilizando o algoritmo de
criptografia `BCrypt`. O Spring Security é responsável pela criptografia das senhas durante o processo de autenticação e
comparação com a senha informada durante o login.

Este sistema de segurança garante que apenas usuários autenticados e autorizados tenham acesso aos recursos protegidos
da API, proporcionando uma camada adicional de proteção contra acessos não autorizados e garantindo a integridade dos
dados manipulados pelo sistema.

### Lombok

O Lombok foi integrado ao projeto Mini Autorizador para reduzir a verbosidade do código e simplificar a escrita das
classes. Esta biblioteca é amplamente reconhecida na comunidade Java por automatizar a geração de métodos comuns, como
getters, setters, equals, hashCode e toString, sem a necessidade de escrever esses métodos manualmente.

Com o Lombok, podemos focar mais na lógica de negócio das classes, evitando a repetição de código boilerplate. Ele
funciona por anotações simples, como `@Getter`, `@Setter`, `@EqualsAndHashCode`, `@ToString`, entre outras, processadas
durante a compilação para adicionar automaticamente os métodos correspondentes às classes.

Além de melhorar a legibilidade e a manutenção do código, o Lombok ajuda a reduzir erros humanos associados à
implementação manual desses métodos. Isso torna o desenvolvimento mais eficiente e permite que os desenvolvedores se
concentrem em aspectos mais críticos e específicos da aplicação.

A utilização do Lombok no projeto Mini Autorizador contribui significativamente para a produtividade da equipe de
desenvolvimento, promovendo um código mais limpo e conciso sem comprometer a funcionalidade e a robustez das classes
implementadas.

### Testes Automatizados

Para assegurar a qualidade do código e a integridade das funcionalidades, foram implementados testes automatizados em
duas camadas principais: testes unitários e testes de integração.

#### Testes Unitários

Os testes unitários visam verificar o funcionamento correto das unidades individuais do código, como classes
de serviço e handlers de autorização. Eles são executados de forma isolada, sem depender de outros componentes externos,
garantindo que cada unidade execute suas funções conforme esperado. Os testes unitários são essenciais para validar o
comportamento de métodos específicos e verificar se a lógica de negócio está implementada corretamente.

#### Testes de Integração

Os testes de integração são responsáveis por validar a interação entre diferentes componentes do sistema, especialmente
os endpoints da API. Eles garantem que as diversas partes da aplicação funcionem em conjunto de maneira adequada,
testando o fluxo completo de requisições HTTP, desde a entrada até a saída da API. Os testes de integração são
fundamentais para verificar se a API se comporta conforme esperado em um ambiente próximo ao de produção.

Ambas as camadas de teste foram implementadas utilizando o framework de testes do Maven. Para executar todos os testes
automatizados, basta utilizar o comando `mvn test` no terminal. Isso garantirá que todas as funcionalidades do projeto
sejam testadas de forma abrangente, contribuindo para a robustez e confiabilidade do sistema desenvolvido.
