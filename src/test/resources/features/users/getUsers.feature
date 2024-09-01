# language: pt
@Users @ListUsers
Funcionalidade: Lista usuários

  # ------------ Schema ------------ #
  @schema @main
  Cenario: Validar o contrato do endpoint GET /users
    Dado o contrato users/getUsers.json
    Quando envio uma requisicao GET para o path /users
    Entao deve retornar o status code 200
    E o contrato deve ser valido

  # ------------ Main ------------ #
  @main
  Cenario: Listar usuários
    Dado que envio uma requisicao GET para o path /users
    Entao deve retornar o status code 200
    E os elementos listados sao validos

  @main
  Esquema do Cenario: Listar usuários informando parâmetro de paginação <value>
    Dado que preparo uma requisicao com o query param page com valor <value>
    E adiciono o query param per_page com valor 1
    Quando envio uma requisicao GET para o path /users
    Entao deve retornar o status code 200
    E deve listar a pagina <value>
    E os elementos listados sao validos

    Exemplos:
      | value |
      | 1     |
      | 2     |

  @main
  Esquema do Cenario: Listar usuários informando parâmetro de limite <value>
    Dado que preparo uma requisicao com o query param per_page com valor <value>
    Quando envio uma requisicao GET para o path /users
    Entao deve retornar o status code 200
    E a lista data deve ter <value> resultados
    E os elementos listados sao validos

    Exemplos:
      | value |
      | 1     |
      | 5     |

  # ------------ Alternative ------------ #
  # API doesn't have invalid type validation
  @alternative @bug
  Esquema do Cenario: Listar usuários informando parâmetro query <param> com valor <value> inválido
    Dado que preparo uma requisicao com o query param <param> com valor <value>
    Quando que envio uma requisicao GET para o path /users
    Entao deve retornar o status code 400
    E deve retornar a mensagem de erro <error> com os valores <listOfValues>

    Exemplos:
      | param    | value | error                    | listOfValues |
      | page     | -1    | EXCEPTION_MIN_VALUE      | 1            |
      | page     | 1`@bc | INVALID_FORMAT_EXCEPTION | Integer      |
      | per_page | -1    | EXCEPTION_MIN_VALUE      | 1            |
      | per_page | 51    | EXCEPTION_MAX_VALUE      | 50           |
      | per_page | 1`@bc | INVALID_FORMAT_EXCEPTION | Integer      |
