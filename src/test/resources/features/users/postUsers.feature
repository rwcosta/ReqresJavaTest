# language: pt
@Users @RegisterUser
Funcionalidade: Cadastra usuário

  # ------------ Schema ------------ #
  @schema @main
  Cenario: Validar o contrato do endpoint POST /users
    Dado o contrato users/postUsers.json
    E que preparo uma requisicao para cadastrar um usuario
    Quando envio uma requisicao POST para o path /users
    Entao deve retornar o status code 201
    E o contrato deve ser valido

  # ------------ Main ------------ #
  @main
  Cenario: Cadastrar usuário
    Dado que preparo uma requisicao para cadastrar um usuario
    Quando envio uma requisicao POST para o path /users
    Entao deve retornar o status code 201
    E o usuario deve ter sido cadastrado corretamente

  # ------------ Alternative ------------ #
  # API doesn't have invalid type validation
  @alternative @bug
  Esquema do Cenario: Cadastrar usuário informando <field> com valor <value> inválido
    Dado que preparo uma requisicao para cadastrar um usuario
    E adiciono o campo <field> no body com valor <value>
    Quando que envio uma requisicao POST para o path /users
    Entao deve retornar o status code 400
    E deve retornar a mensagem de erro INVALID_FORMAT_EXCEPTION com os valores String

    Exemplos:
      | field | value  |
      | name  | 123    |
      | name  | 100.99 |
      | job   | 123    |
      | job   | 100.99 |
