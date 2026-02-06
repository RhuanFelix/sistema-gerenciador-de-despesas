# Gerenciador de Despesas Pessoais

Descrição do projeto.

## Requisitos do Sistema

### modelagem do banco
Categoria - Despesas (1:N)

### Endpoints
Post, Put, Get, Delete

/categoria (post, get)
/categoria/{id} (put, delete, get)

/despesa (post, get)
/despesa/{id} (put, delete, get)

### Atributos das entidades
#### Categoria
- id
- nome
- descricao

#### Despesas
- id
- nome
- descricao
- valor
- data_vencimento
- forma_pagamento (cartao, pix, cedula)
- recorrencia (diario/semanal/mensal/anual)
- id_categoria

## Tecnologias Utilizadas

- Listar tecnologais.

## Arquitetura do Projeto

O projeto possui arquitetura em camadas, sendo elas:

- **Configs**: serve para inserir configurações do projeto;
- **Controllers**: serve para expor os endpoints da API;
- **Models**: serve para definir as entidades do banco e DTOs de requisição e resposta;
- **Repositories**: serve para criar a conexão com o banco de dados;
- **Services**: serve para inserir a lógica de negócio do projeto.