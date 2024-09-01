# language: pt
@Users @RemoveUser
Funcionalidade: Remover usuário
  # ------------ Main ------------ #
  @main
  Cenario: Remover usuário
    Dado que preparo uma requisicao para remover um usuario
    Quando envio uma requisicao DELETE para o path /users/{id}
    Entao deve retornar o status code 204
    E o usuario deve ter sido removido corretamente

  # ------------ Alternative ------------ #
  @alternative @bug
  Esquema do Cenario: Remover usuário informando ID <value> inválido
    Dado que preparo uma requisicao com o path param id de valor <value>
    Quando que envio uma requisicao DELETE para o path /users/{id}
    Entao deve retornar o status code <code>
    E deve retornar a mensagem de erro <error> com os valores <listOfValues>

    # API doesn't have invalid type validation
    Exemplos:
      | value               | code | error                    | listOfValues |
      | abc                 | 400  | INVALID_FORMAT_EXCEPTION | Integer      |
      | 9999999999999999999 | 400  | INVALID_FORMAT_EXCEPTION | Integer      |
      | ""                  | 400  | INVALID_FORMAT_EXCEPTION | Integer      |

    # Should return an error message or some information
    Exemplos:
      | value              | code | error          |
      | 999999999999999999 | 404  | USER_NOT_FOUND |
