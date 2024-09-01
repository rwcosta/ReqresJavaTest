# language: pt
@Users @PartialUpdateUser
Funcionalidade: Altera parcialmente um usuário

  # ------------ Schema ------------ #
  @schema @main
  Cenario: Validar o contrato do endpoint PATCH /users/{id}
    Dado o contrato users/putUsers.json
    E que preparo uma requisicao para alterar um usuario
    Quando envio uma requisicao PATCH para o path /users/{id}
    Entao deve retornar o status code 200
    E o contrato deve ser valido

  # ------------ Main ------------ #
  @main
  Cenario: Alterar parcialmente um usuário
    Dado que preparo uma requisicao para alterar parcialmente um usuario informando todos os campos
    Quando envio uma requisicao PATCH para o path /users/{id}
    Entao deve retornar o status code 200
    E a alteracao parcial do usuario deve ter sido feita corretamente

  # ------------ Alternative ------------ #
  # API doesn't have invalid type validation
  @alternative @bug
  Esquema do Cenario: Alterar parcialmente um usuário informando <field> com valor <value> inválido
    Dado que preparo uma requisicao para alterar um usuario
    E adiciono o campo <field> no body com valor <value>
    Quando que envio uma requisicao PATCH para o path /users/{id}
    Entao deve retornar o status code 400
    E deve retornar a mensagem de erro INVALID_FORMAT_EXCEPTION com os valores String

    Exemplos:
      | field | value  |
      | name  | 123    |
      | name  | 100.99 |
      | job   | 123    |
      | job   | 100.99 |
