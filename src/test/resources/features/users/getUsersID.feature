# language: pt
@Users @ConsultUser
Funcionalidade: Consulta usuário

  # ------------ Schema ------------ #
  @schema @main
  Cenario: Validar o contrato do endpoint GET /users/{id}
    Dado o contrato users/getUsersID.json
    E que preparo uma requisicao para consultar um usuario
    Quando envio uma requisicao GET para o path /users/{id}
    Entao deve retornar o status code 200
    E o contrato deve ser valido

  # ------------ Main ------------ #
  @main
  Cenario: Consultar usuário
    Dado que preparo uma requisicao para consultar um usuario
    Quando envio uma requisicao GET para o path /users/{id}
    Entao deve retornar o status code 200
    E o usuario consultado deve ser valido

  # ------------ Alternative ------------ #
  @alternative @bug
  Esquema do Cenario: Consultar usuário informando ID <value> inválido
    Dado que preparo uma requisicao com o path param id de valor <value>
    Quando que envio uma requisicao GET para o path /users/{id}
    Entao deve retornar o status code <code>
    E deve retornar a mensagem de erro <error> com os valores <listOfValues>

    # API doesn't have invalid type validation
    Exemplos:
      | value               | code | error                    | listOfValues |
      | abc                 | 400  | INVALID_FORMAT_EXCEPTION | Integer      |
      | 9999999999999999999 | 400  | INVALID_FORMAT_EXCEPTION | Integer      |
      | ""                  | 400  | INVALID_FORMAT_EXCEPTION | Integer      |

    # Should return an error message or some information besides empty JSON
    Exemplos:
      | value              | code | error          |
      | 999999999999999999 | 404  | USER_NOT_FOUND |
