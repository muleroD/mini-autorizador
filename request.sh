#!/bin/bash

# Definição das variáveis de autenticação básica
username="username"
password="password"

# URL e corpo JSON da requisição para porta 8080
url1="http://localhost:8080/transacoes"
json='{"numeroCartao": "1234567890123456", "senha": "1234", "valor": 100}'

# URL e corpo JSON da requisição para porta 8081
url2="http://localhost:8081/transacoes"

# Função para fazer a requisição HTTP
make_request() {
  local url=$1
  local json_data=$2

  curl -X POST \
    -H "accept: */*" \
    -H "Content-Type: application/json" \
    -H "Authorization: Basic $(echo -n "$username:$password" | base64)" \
    -d "$json_data" \
    "$url"
}

# Chamada da função para cada URL
make_request "$url1" "$json" &
make_request "$url2" "$json" &
wait

echo "Requisições finalizadas."
