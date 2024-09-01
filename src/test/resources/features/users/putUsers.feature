# language: pt
@Users @UpdateUser
Funcionalidade: Altera usuário

  # ------------ Schema ------------ #
  @schema @main
  Cenario: Validar o contrato do endpoint PUT /users/{id}
    Dado o contrato users/putUsers.json
    E que preparo uma requisicao para alterar um usuario
    Quando envio uma requisicao PUT para o path /users/{id}
    Entao deve retornar o status code 200
    E o contrato deve ser valido

  # ------------ Main ------------ #
  @main
  Cenario: Alterar usuário
    Dado que preparo uma requisicao para alterar um usuario
    Quando envio uma requisicao PUT para o path /users/{id}
    Entao deve retornar o status code 200
    E o usuario deve ter sido alterado corretamente

  # ------------ Alternative ------------ #
  # API doesn't have invalid type validation
  @alternative @bug
  Esquema do Cenario: Alterar usuário informando <field> com valor <value> inválido
    Dado que preparo uma requisicao para alterar um usuario
    E adiciono o campo <field> no body com valor <value>
    Quando que envio uma requisicao PUT para o path /users/{id}
    Entao deve retornar o status code 400
    E deve retornar a mensagem de erro INVALID_FORMAT_EXCEPTION com os valores String

    Exemplos:
      | field | value  |
      | name  | 123    |
      | name  | 100.99 |
      | job   | 123    |
      | job   | 100.99 |
