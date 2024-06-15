# Mini Autorizador - VR Benefícios

Este é um projeto de exemplo desenvolvido como parte de um processo seletivo para a VR Benefícios. O objetivo é criar um
mini autorizador para processar transações de Vale Refeição e Vale Alimentação, entre outras.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.3.0
- Maven 3.3.2
- MySQL 5.7

## Funcionalidades

### 1. Criação de um Cartão

Permite a criação de um novo cartão com um número e senha especificados.

- **Método**: `POST`
- **URL**: `/cartoes`
- **Body (JSON)**:
  ```json
  {
      "senha": "1234",
      "numeroCartao": "6549873025634501"
  }
  ```
- **Possíveis Respostas**:
    - **201 (Criado)**: Cartão criado com sucesso.
    - **422 (Unprocessable Entity)**: Cartão já existente.
    - **401 (Unauthorized)**: Erro de autenticação.

### 2. Obter Saldo do Cartão

Permite consultar o saldo disponível de um cartão específico.

- **Método**: `GET`
- **URL**: `/cartoes/{numeroCartao}`
- **Autenticação**: Basic (login e senha)
- **Possíveis Respostas**:
    - **200 (OK)**: Retorna o saldo do cartão.
    - **404 (Not Found)**: Cartão não existe.
    - **401 (Unauthorized)**: Erro de autenticação.

### 3. Realizar uma Transação

Permite realizar uma transação debitando o valor do saldo do cartão, se autorizado.

- **Método**: `POST`
- **URL**: `/transacoes`
- **Body (JSON)**:
  ```json
  {
      "numeroCartao": "6549873025634501",
      "senhaCartao": "1234",
      "valor": 10.00
  }
  ```
- **Autenticação**: Basic (login e senha)
- **Possíveis Respostas**:
    - **201 (Created)**: Transação realizada com sucesso.
    - **422 (Unprocessable Entity)**: Transação barrada por alguma regra de autorização. Possíveis erros:
        - `SALDO_INSUFICIENTE`
        - `SENHA_INVALIDA`
        - `CARTAO_INEXISTENTE`
    - **401 (Unauthorized)**: Erro de autenticação.

## Regras de Autorização

1. **Saldo Insuficiente**: Verifica se o saldo do cartão é suficiente para cobrir o valor da transação.
2. **Senha Inválida**: Verifica se a senha fornecida está correta.
3. **Cartão Inexistente**: Verifica se o número do cartão existe.

## Testes Automatizados

Este projeto inclui testes automatizados para garantir a qualidade e o correto funcionamento das funcionalidades.

## Desafios Opcionais

- Implementar a solução sem utilizar `if`. 
- Garantir que transações simultâneas não causem problemas de concorrência. Por exemplo, se um cartão possui R$10.00 de
  saldo e duas transações de R$10.00 são realizadas simultaneamente, o sistema deve lidar corretamente com essa situação.

## Como Executar o Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/mini-autorizador.git
   ```
2. Navegue até o diretório do projeto:
   ```bash
   cd mini-autorizador
   ```
3. Compile o projeto com Maven:
   ```bash
   mvn clean install
   ```
4. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

## Documentação de API

A documentação detalhada da API com exemplos de requisições e respostas está
disponível [aqui](http://localhost:8080/swagger-ui/index.html).