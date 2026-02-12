# Gerenciador de Despesas Pessoais

Este projeto faz parte de um seminário da disciplina de Banco de Dados II. Possui o objetivo de criar uma API Rest com Spring Boot utilizando o banco de dados não relacional: Apache CouchDB.

O sistema possui duas entidades: categoria e despesa.

## Funcionalidades

O gerenciador de despesas pessoais possui como funcionalidade:
                                
- Criar, atualizar, consultar (listando todas ou por id) e deletar categorias;
- Criar, atualizar, consultar (listando todas, ou por id ou listando de uma categoria) e deletar despesas.

## Tecnologias Utilizadas

- Spring Boot
- Spring Web
- Spring Doc OpenAPI
- Ektorp (CouchDB)
- HTTP Client
- Lombok
- Jackson Datatype: JSR310

## Instalação do CouchDB

O CouchDB é um SGBD multiplataforma, então você pode consultar a [documentação do CouchDB](https://docs.couchdb.org/en/stable/install/index.html) e escolher o guia de instalação do sistema operacional que você usa.

Após o processo inicial da instalação, vai aparecer algumas opções de uso do CouchDB ainda no terminal; para este projeto o **Standalone** é suficiente, já que você utilizará apenas um servidor para usar o CouchDB. Se quiser saber mais informações sobre essas configurações, consulte a [documentação do CouchDB](https://docs.couchdb.org/en/stable/setup/index.html).

Após o passo anterior, será solicitado uma interface de rede (endereço ip). Se você selecionou **Standalone** no passo anterior, deixe o seu endereço de loopback.

Por fim será solicitado uma senha para o seu usuário administrador, crie uma senha e depois digite novamente para confirmar.

Para confirmar se o CouchDB foi instalado corretamente e está sendo executado, no seu navegador, digite a seguinte URL: ```http://127.0.0.1:5984/```. Aparecerá algo semelhante ao JSON abaixo:

```JSON
{
  "couchdb": "Welcome",
  "version": "3.x.x",
  "features": ["scheduler"],
  "vendor": {
    "name": "The Apache Software Foundation"
  }
}

```

## Criando um usuário personalizado no Fauxton (Opcional)

O **Fauxton** é a interface gráfica do CouchDB, para acessá-lo você deve colocar a seguinte URL no seu navegador: http://localhost:5984/_utils/#login. Com isso, será socilitado usuário e senha, no usuário você digita "admin" e no campo de senha você digita sua senha que você definiu durante a intalação do CouchDB. A partir daqui você terá acesso aos bancos de dados que você criou e também você pode manipular seus bancos pelo Fauxton.

Após estar logado com sua conta "admin", você clica no ícone simbolizando um usuário do lado esquerdo da tela e clica em "Create Server Admin", digite seu novo usuário e sua nova senha e clica no botão "Create Admin".

## Atualizando o application.properties

No arquivo [application.properties](./src/main/resources/application.properties), você deve substituir os valores seu_usuario e sua_senha por "admin" e sua senha que você definiu durante a instalação, respectivamente; ou inserir seu usuário e senha que você criou pelo Fauxton.

## Arquitetura do Projeto

```
src/
└── main/
    ├── java/
    │   └── com/
    │       └── tsi/
    │           └── despesas/
    │               ├── configs/                     # Configurações do projeto
    │               ├── controllers/                 # Endpoints da API
    │               ├── models/                      # Entidades e DTOS
    │               ├── repositories/                # Classes que acessam os dados do banco
    │               ├── services/                    # Lógica de negócio
    │               └── DespesasApplication.java
    └── resources/
```