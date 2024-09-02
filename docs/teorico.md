# Cenários

## Clientes
### Listar Clientes
```gherkin
# ------------ Fluxo Principal ------------ #
Scenario: Listar clientes informando todos os campos válidos
    Given que preparo a listagem dos clientes informando todos os campos validos
    When tentar listar clientes
    Then os clientes devem ter sido listados corretamente

Scenario Outline: Listar clientes informando o campo <field> válido
    Given que preparo a listagem dos clientes informando o campo <field> valido
    When tentar listar clientes
    Then os clientes devem ter sido listados corretamente

    Examples:
        | field        |
        | nome         |

    # Aplicação trás valores que não correspondem com o valor passado
    @bug1
    Examples:
        | field        |
        | dataValidade |

# Não acontece nada ao tentar filtrar por clientes Ativos e Inativos
@bug1
Scenario Outline: Listar clientes filtrando pelos <value>
    Given que preparo a listagem dos clientes informando todos os campos validos
    When tentar listar clientes
    And filtro apenas os clientes
    Then os clientes devem ter sido listados corretamente

    Examples:
        | value   |
        | Ativo   |
        | Inativo |

# ------------ Fluxo Alternativo ------------ #
Scenario: Listar clientes não informando os campos obrigatórios
    Given que preparo a listagem dos clientes sem informar campos obrigatórios
    When tentar listar clientes
    Then nao deve ser possivel listar os clientes

Scenario Outline: Listar clientes informando campo dataValidade com mês inválido <value>
    Given que preparo a listagem dos clientes informando o mes <value>
    When tentar listar clientes
    Then nao deve ser possivel listar os clientes

    Examples:
        | value |
        | 00    |
        | 13    |

Scenario Outline: Listar clientes informando <field> não cadastrado
    Given que preparo a listagem dos clientes informando o campo <field> nao cadastrado
    When tentar listar clientes
    Then a lista deve estar vazia

    Examples:
        | field        |
        | nome         |

    # Valor no campo não é levado em consideração pelo filtro
    @bug1
    Examples:
        | field        |
        | dataValidade |
```
<br>

### Consultar Cliente
```gherkin
# ------------ Fluxo Principal ------------ #
Scenarios: Consultar cliente
    Given que preparo a consulta de um cliente
    When tentar consultar o cliente
    Then o cliente deve ter sido consultado corretamente
```
<br>

### Cadastrar Cliente
```gherkin
# ------------ Fluxo Principal ------------ #
Scenario: Cadastrar cliente informando todos os campos válidos
    Given que preparo o cadastro de um cliente informando todos os campos validos
    When tentar cadastrar o cliente
    Then o cliente deve ter sido cadastrado corretamente

Scenario Outline: Cadastrar cliente informando o campo ativo <value>
    Given que preparo o cadastro de um cliente informando o campo ativo <value>
    When tentar cadastrar o cliente
    Then o cliente deve ter sido cadastrado corretamente

    Examples:
        | value   |
        | Ativo   |
        | Inativo |

Scenario Outline: Cadastrar cliente informando o saldo <value>
    Given que preparo o cadastro de um cliente informando o saldo <value>
    When tentar cadastrar o cliente
    Then o cliente deve ter sido cadastrado corretamente

    Examples:
        | value     |
        | 100.00    |

    # Aplicação trava e não cadastra o cliente
    @bug1
    Examples:
        | value     |
        | 1,000.00  |
        | 10,000.00 |

# ------------ Fluxo Alternativo ------------ #
Scenario Outline: Cadastrar cliente não informando o campo <field>
    Given que preparo o cadastro de um cliente nao informando o campo <field>
    When tentar cadastrar o cliente
    Then nao deve ser possivel cadastrar o cliente

    Examples:
        | field |
        | nome  |
        | cpf   |

    # É possível cadastrar cliente não informando o campo saldoDisponível
    @bug1
    Examples:
        | field           |
        | saldoDisponivel |

# É possível cadastrar cliente informando um CPF já cadastrado anteriormente
@bug1
Scenario: Cadastrar cliente informando CPF já cadastrado
    Given que preparo o cadastro de um cliente com CPF ja cadastrado
    When tentar cadastrar o cliente
    Then nao deve ser possivel cadastrar o cliente

Scenario: Cadastrar cliente informando CPF inválido
    Given que preparo o cadastro de um cliente informando todos os campos validos
    And altero o valor do campo cpf para 123.456.789.10
    When tentar cadastrar o cliente
    Then nao deve ser possivel cadastrar o cliente

# É possível cadastrar um usuário informando um CPF válido e em seguida substituir o valor no campo pelos valores inválidos
@bug1
Scenario Outline: Cadastrar cliente informando CPF não numérico
    Given que preparo o cadastro de um cliente com CPF ja cadastrado
    And preencho o campo CPF com um valor válido
    And substituo o valor do campo CPF para <value>
    When tentar cadastrar o cliente
    Then nao deve ser possivel cadastrar o cliente

    Examples:
        | value |
        | abc   |
        | 123   |
```
<br>

### Alterar Cliente
```gherkin
# ------------ Fluxo Principal ------------ #
Scenario: Alterar cliente informando todos os campos
    Given que preparo a alteracao de um cliente informando todos os campos
    When tentar alterar o cliente
    Then o cliente deve ter sido alterado corretamente

Scenario Outline: Alterar status ativo do cliente de <actualValue> para <newValue>
    Given que preparo a alteracao de um cliente <actualValue>
    And altero o campo ativo para <newValue>
    When tentar alterar o cliente
    Then o cliente deve ter sido alterado corretamente

    Examples:
        | actualValue | newValue |
        | Ativo       | Inativo  |
        | Inativo     | Ativo    |

# Ao clicar em limpar o cliente é alterado mesmo assim
@bug1
Scenario: Alterar cliente sem confirmar alteração
    Given que preparo a alteracao de um cliente
    When limpar as alteracoes
    Then o cliente não deve ter sido alterado

Scenario Outline: Alterar cliente informando o saldo <value>
    Given que preparo a alteração de um cliente informando o saldo <value>
    When tentar alterar o cliente
    Then o cliente deve ter sido alterado corretamente

    Examples:
        | value     |
        | 100.00    |

    # Aplicação trava e não altera o cliente
    @bug1
    Examples:
        | value     |
        | 1,000.00  |
        | 10,000.00 |

# ------------ Fluxo Alternativo ------------ #
Scenario Outline: Alterar cliente não informando o campo <field>
    Given que preparo a alteracao de um cliente nao informando o campo <field>
    When tentar alterar o cliente
    Then nao deve ser possivel alterar o cliente

    Examples:
        | field |
        | nome  |
        | cpf   |

    # É possível alterar cliente não informando o campo saldoDisponível
    @bug1
    Examples:
        | field           |
        | saldoDisponivel |

# É possível alterar cliente informando um CPF já cadastrado anteriormente
@bug1
Scenario: Alterar cliente informando CPF já cadastrado
    Given que preparo a alteracao de um cliente informando CPF ja cadastrado
    When tentar alterar o cliente
    Then nao deve ser possivel alterar o cliente

Scenario: Alterar cliente informando CPF inválido
    Given que preparo a alteracao de um cliente informando todos os campos validos
    And altero o valor do campo cpf para 123.456.789.10
    When tentar alterar o cliente
    Then nao deve ser possivel alterar o cliente
```
<br>

### Deletar Cliente
```gherkin
# ------------ Fluxo Principal ------------ #
# O usuário não é removido, mas sim, é aberto a página para alterar cliente
@bug1
Scenario: Deletar cliente
    Given que preparo a remoção de um cliente
    When tentar remover o cliente
    Then o cliente deve ter sido removido corretamente
```
<br>

## Transação
### Listar Transações
```gherkin
# ------------ Fluxo Principal ------------ #
Scenario: Listar transações para todos os clientes
    Given que preparo a listagem de todas as transacoes
    When tentar listar transacoes
    Then todas as transacoes devem ter sido listadas corretamente

# Não é retornado as transações do cliente
@bug1
Scenario: Listar transações de um cliente
    Given que preparo a listagem de todas as transacoes de um cliente
    When tentar listar transacoes
    Then todas as transacoes do cliente devem ter sido listadas corretamente

# ------------ Fluxo Alternativo ------------ #
Scenario: Listar transações de um cliente que não possui transações cadastradas
    Given que preparo a listagem das transacoes de um cliente sem transações cadastradas
    When tentar listar transacoes
    Then não deve retornar nenhuma transação
```
<br>

### Cadastrar Transação
```gherkin
# ------------ Fluxo Principal ------------ #
Scenario: Cadastrar transação para um cliente ativo
    Given que preparo o cadastro de uma transacao para um cliente ativo
    When tentar cadastrar a transacao
    Then a transacao deve ter sido cadastrada correntamente
    And o saldo do cliente deve ter sido debitado corretamente

Scenario: Cadastrar transação informando valor igual ao saldo do cliente
    Given que preparo o cadastro de uma transacao para um cliente ativo
    And altero o valor da transacao para um valor igual ao saldo do cliente
    When tentar cadastrar a transacao
    Then a transacao deve ter sido cadastrada correntamente
    And o saldo do cliente deve ter sido debitado corretamente

Scenario Outline: Cadastrar transação informando valor <value>
    Given que preparo o cadastro de uma transacao para um cliente com saldo maior ou igual que <value>
    And altero o valor da transacao para <value>
    When tentar cadastrar a transacao
    Then a transacao deve ter sido cadastrada correntamente
    And o saldo do cliente deve ter sido debitado corretamente

    Examples:
        | value     |
        | 100.00    |

    # Aplicação trava e não cadastra transação
    @bug1
    Examples:
        | value     |
        | 1,000.00  |
        | 10,000.00 |

# ------------ Fluxo Alternativo ------------ #
# Aplicação cadastra transação para cliente inativo
@bug1
Scenario: Cadastrar transação para um cliente inativo
    Given que preparo o cadastro de uma transacao para um cliente inativo
    When tentar cadastrar a transacao
    Then nao deve ser possivel cadastrar a transacao
    And o saldo do cliente nao deve ter sido debitado

# A aplicação cadastra a transação com valor maior que o saldo disponível e não debita do cliente
@bug1
Scenario: Cadastrar transação informando valor maior que o saldo do cliente
    Given que preparo o cadastro de uma transacao para um cliente ativo
    And altero o valor da transacao para um valor maior que o saldo do cliente
    When tentar cadastrar a transacao
    Then nao deve ser possivel cadastrar a transacao
    And o saldo do cliente nao deve ter sido debitado

# Aplicação trava e não responde, o ideal seria não cadastrar destacando os campos obrigatórios
@bug1
Scenario: Cadastrar transação sem informar campos
    Given que preparo o cadastro de uma transacao sem informar os campos
    When tentar cadastrar a transacao
    Then nao deve ser possivel cadastrar a transacao
    And nenhum cliente deve ter o saldo debitado

# Aplicação trava e não responde, o ideal seria não cadastrar destacando o campo como obrigatório
@bug1
Scenario Outline: Cadastrar transação sem informar o campo <field>
    Given que preparo o cadastro de uma transacao sem informar o campo <field>
    When tentar cadastrar a transacao
    Then nao deve ser possivel cadastrar a transacao
    And nenhum cliente deve ter o saldo debitado

    Examples:
        | field          |
        | cliente        |
        | valorTransacao |
```
<br>

### Limpar Base
```gherkin
Scenario: Limpar base
    Given que preparo a limpar a base
    When tentar limpar a base
    Then todos os dados devem ter sido removidos corretamente
```

# Melhorias

## Listar Clientes
- Não deixar campos nome e dataValidade como obrigatórios

## Cadastrar / Alterar Cliente
- Definir um limite de caracteres para o nome do cliente
- Definir um limite de dígitos para o saldo disponível

## Cadastrar Transação
- Definir um limite de dígitos para o valor da transação
