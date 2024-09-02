<!-- TOC -->
# Conteúdo
* [Cenários](#cenários)
  * [Clientes](#clientes)
    * [Listar Clientes](#listar-clientes)
    * [Consultar Cliente](#consultar-cliente)
    * [Cadastrar Cliente](#cadastrar-cliente)
    * [Alterar Cliente](#alterar-cliente)
    * [Deletar Cliente](#deletar-cliente)
  * [Transação](#transação)
    * [Listar Transações](#listar-transações)
    * [Cadastrar Transação](#cadastrar-transação)
    * [Limpar Base](#limpar-base)
* [Bugs](#bugs)
  * [Listar Clientes](#listar-clientes-1)
    * [Aplicação não considera os valores passados nos parâmetro nome e data validade](#aplicação-não-considera-os-valores-passados-nos-parâmetro-nome-e-data-validade)
    * [Filtros de clientes ativos e inativos não funcionam](#filtros-de-clientes-ativos-e-inativos-não-funcionam)
  * [Cadastrar Clientes](#cadastrar-clientes)
    * [Aplicação não consegue lidar com valores acima de 999.99 para o parâmetro saldo disponivel ao tentar cadastrar um cliente](#aplicação-não-consegue-lidar-com-valores-acima-de-99999-para-o-parâmetro-saldo-disponivel-ao-tentar-cadastrar-um-cliente)
    * [É possível cadastrar cliente sem informar o campo saldo disponível](#é-possível-cadastrar-cliente-sem-informar-o-campo-saldo-disponível)
    * [É possível cadastrar cliente informando um CPF já cadastrado anteriormente](#é-possível-cadastrar-cliente-informando-um-cpf-já-cadastrado-anteriormente)
    * [É possível cadastrar um usuário informando um CPF válido e em seguida substituir o valor no campo pelos valores inválidos](#é-possível-cadastrar-um-usuário-informando-um-cpf-válido-e-em-seguida-substituir-o-valor-no-campo-pelos-valores-inválidos)
  * [Alterar Cliente](#alterar-cliente-1)
    * [Ao clicar em limpar, o cliente é cadastrado mesmo assim](#ao-clicar-em-limpar-o-cliente-é-cadastrado-mesmo-assim)
    * [Aplicação não consegue lidar com valores acima de 999.99 para o parâmetro saldo disponivel ao tentar alterar um cliente](#aplicação-não-consegue-lidar-com-valores-acima-de-99999-para-o-parâmetro-saldo-disponivel-ao-tentar-alterar-um-cliente)
    * [É possível alterar cliente sem informar o campo saldo disponível](#é-possível-alterar-cliente-sem-informar-o-campo-saldo-disponível)
    * [É possível alterar cliente informando um CPF já cadastrado anteriormente](#é-possível-alterar-cliente-informando-um-cpf-já-cadastrado-anteriormente)
  * [Deletar Cliente](#deletar-cliente-1)
    * [Não é possível remover cliente](#não-é-possível-remover-cliente)
  * [Listar Transações](#listar-transações-1)
    * [Não é possível consultar as transações de um cliente específico](#não-é-possível-consultar-as-transações-de-um-cliente-específico)
  * [Cadastrar Transação](#cadastrar-transação-1)
    * [Aplicação não consegue lidar com valores acima de 999.99 para o parâmetro saldo disponivel ao tentar cadastrar uma transação](#aplicação-não-consegue-lidar-com-valores-acima-de-99999-para-o-parâmetro-saldo-disponivel-ao-tentar-cadastrar-uma-transação)
    * [Aplicação cadastra transação para cliente inativo](#aplicação-cadastra-transação-para-cliente-inativo)
    * [A aplicação cadastra a transação com valor maior que o saldo disponível e não debita do cliente](#a-aplicação-cadastra-a-transação-com-valor-maior-que-o-saldo-disponível-e-não-debita-do-cliente)
    * [Aplicação trava e não responde ao tentar cadastrar transação sem informar ambos os campos](#aplicação-trava-e-não-responde-ao-tentar-cadastrar-transação-sem-informar-ambos-os-campos)
    * [Aplicação trava e não responde ao tentar cadastrar transação sem informar um dos campos](#aplicação-trava-e-não-responde-ao-tentar-cadastrar-transação-sem-informar-um-dos-campos)
* [Melhorias](#melhorias)
  * [Listar Clientes](#listar-clientes-2)
  * [Cadastrar / Alterar Cliente](#cadastrar--alterar-cliente)
  * [Cadastrar Transação](#cadastrar-transação-2)
<!-- TOC -->

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
    @bug
    Examples:
        | field        |
        | dataValidade |

# Não acontece nada ao tentar filtrar por clientes Ativos e Inativos
@bug
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
    @bug
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
    @bug
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
    @bug
    Examples:
        | field           |
        | saldoDisponivel |

# É possível cadastrar cliente informando um CPF já cadastrado anteriormente
@bug
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
@bug
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
@bug
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
    @bug
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
    @bug
    Examples:
        | field           |
        | saldoDisponivel |

# É possível alterar cliente informando um CPF já cadastrado anteriormente
@bug
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
@bug
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
@bug
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
    @bug
    Examples:
        | value     |
        | 1,000.00  |
        | 10,000.00 |

# ------------ Fluxo Alternativo ------------ #
# Aplicação cadastra transação para cliente inativo
@bug
Scenario: Cadastrar transação para um cliente inativo
    Given que preparo o cadastro de uma transacao para um cliente inativo
    When tentar cadastrar a transacao
    Then nao deve ser possivel cadastrar a transacao
    And o saldo do cliente nao deve ter sido debitado

# A aplicação cadastra a transação com valor maior que o saldo disponível e não debita do cliente
@bug
Scenario: Cadastrar transação informando valor maior que o saldo do cliente
    Given que preparo o cadastro de uma transacao para um cliente ativo
    And altero o valor da transacao para um valor maior que o saldo do cliente
    When tentar cadastrar a transacao
    Then nao deve ser possivel cadastrar a transacao
    And o saldo do cliente nao deve ter sido debitado

# Aplicação trava e não responde, o ideal seria não cadastrar destacando os campos obrigatórios
@bug
Scenario: Cadastrar transação sem informar campos
    Given que preparo o cadastro de uma transacao sem informar os campos
    When tentar cadastrar a transacao
    Then nao deve ser possivel cadastrar a transacao
    And nenhum cliente deve ter o saldo debitado

# Aplicação trava e não responde, o ideal seria não cadastrar destacando o campo como obrigatório
@bug
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

# Bugs
## Listar Clientes
### Aplicação não considera os valores passados nos parâmetro nome e data validade

Reprodução:
- Informar valor de um cliente já cadastrado no campo nome/data validade
- Tentar listar cliente

Retorno:
- Aplicação trás valores que não correspondem com o valor passado

<br>

### Filtros de clientes ativos e inativos não funcionam
Reprodução:
- Listar todos os clientes
- Clicar em algum dos filtros de conta ativa ou inativa

Retorno:
- Não acontece nada ao tentar filtrar por clientes Ativos e Inativos

<br>

## Cadastrar Clientes
### Aplicação não consegue lidar com valores acima de 999.99 para o parâmetro saldo disponivel ao tentar cadastrar um cliente
Reprodução:
- Informar valor maior do que 999.99 no campo saldo disponível
- Informar valores válidos em todos os campos
- Tentar cadastrar cliente

Retorno:
- Aplicação trava e não cadastra o cliente

<br>

### É possível cadastrar cliente sem informar o campo saldo disponível
Reprodução:
- Informar valores válidos em todos os campos menos no saldo disponível
- Tentar cadastrar cliente

Retorno:
- Aplicação cadastra cliente sem saldo disponível

<br>

### É possível cadastrar cliente informando um CPF já cadastrado anteriormente
Reprodução:
- Informar CPF já cadastrado anteriormente
- Informar valores válidos em todos os campos
- Tentar cadastrar cliente

Retorno:
- Aplicação cadastra cliente com CPF duplicado

<br>

### É possível cadastrar um usuário informando um CPF válido e em seguida substituir o valor no campo pelos valores inválidos
Reprodução:
- Informar valores válidos em todos os campos
- Selecionar CPF e substituir por valores de tipo inválidos
- Tentar cadastrar cliente

Retorno:
- Aplicação cadastra cliente com CPF com tipo inválido

## Alterar Cliente
### Ao clicar em limpar, o cliente é cadastrado mesmo assim
Reprodução:
- Consultar cliente
- Clicar em alterar
- Informar valor válido em qualquer um dos campos
- Clicar em limpar alterações

Retorno:
- Aplicação altera o cliente

<br>

### Aplicação não consegue lidar com valores acima de 999.99 para o parâmetro saldo disponivel ao tentar alterar um cliente
Reprodução:
- Consultar cliente
- Clicar em alterar
- Informar valor maior do que 999.99 no campo saldo disponível
- Tentar alterar cliente

Retorno:
- Aplicação trava e não cadastra o cliente

<br>

### É possível alterar cliente sem informar o campo saldo disponível
Reprodução:
- Consultar cliente
- Clicar em alterar
- Apagar valor do campo saldo disponível
- Tentar alterar cliente

Retorno:
- Aplicação remove campo saldo disponível do cliente

<br>

### É possível alterar cliente informando um CPF já cadastrado anteriormente
Reprodução:
- Consultar cliente
- Clicar em alterar
- Informar CPF já cadastrado anteriormente
- Tentar alterar cliente

Retorno:
- Aplicação altera o CPF do cliente para um valor duplicado

## Deletar Cliente

### Não é possível remover cliente
Reprodução:
- Consultar cliente
- Clicar em remover

Retorno:
- Aplicação exibe a página de alterar cliente

## Listar Transações
### Não é possível consultar as transações de um cliente específico
Reprodução:
- Escolher cliente ativo
- Tentar listar transações

Retorno:
- Não é retornado as transações do cliente

<br>

## Cadastrar Transação
### Aplicação não consegue lidar com valores acima de 999.99 para o parâmetro saldo disponivel ao tentar cadastrar uma transação
Reprodução:
- Escolher cliente ativo
- Informar valor maior do que 999.99 no campo valor transação
- Tentar cadastrar transação

Retorno:
- Aplicação trava e não cadastra a transação

<br>

### Aplicação cadastra transação para cliente inativo
Reprodução:
- Escolher cliente inativo
- Informar valor transação válido
- Tentar cadastrar transação

Retorno:
- Aplicação cadastra a transação e debita do saldo do cliente inativo

<br>

### A aplicação cadastra a transação com valor maior que o saldo disponível e não debita do cliente
Reprodução:
- Escolher cliente ativo
- Informar valor transação acima do limite do cliente
- Tentar cadastrar transação

Retorno:
- Aplicação cadastra a transação e não debita do saldo do cliente

<br>

### Aplicação trava e não responde ao tentar cadastrar transação sem informar ambos os campos
Reprodução:
- Não informar valores para nenhum campo
- Tentar cadastrar transação

Retorno:
- Aplicação trava e não responde

<br>

### Aplicação trava e não responde ao tentar cadastrar transação sem informar um dos campos
Reprodução:
- Informar valor válido para apenas um dos campos
- Tentar cadastrar transação

Retorno:
- Aplicação trava e não responde

# Melhorias

## Listar Clientes
- Não deixar campos nome e dataValidade como obrigatórios

## Cadastrar / Alterar Cliente
- Definir um limite de caracteres para o nome do cliente
- Definir um limite de dígitos para o saldo disponível

## Cadastrar Transação
- Definir um limite de dígitos para o valor da transação
