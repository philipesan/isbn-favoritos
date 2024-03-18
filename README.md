# Projeto - API ISBN Ancora
**Feito por Victor P. A. Smirnov.**
Desenvolvido utilizando Java Springboot, banco de dados H2 na IDE Spring Tools Suite.

**Documentação dos Endpoints:** 
https://www.postman.com/philipesan123/workspace/isbn-ancora

**Porta padrão da aplicação**
***http://localhost:8080***

## Introdução:
Este projeto foi realizado de acordo com o solicitado no desafio, baseado em um software para gerenciamento de favoritos e usuários utilizando consulta em API e cacheing.

### Funcionalidades do Sistema:
- Autenticar na aplicação utilizando Bearer Token.
- Cadastrar, listar, alterar e excluir usuários.
- Buscar detalhes de livros, com cache, na API fornecida.
- Cadastrar, remover e listar detalhes de livros favoritos por usuários.

## Dependências

| **Dependência**                                | **Descrição**                                                                                                     |
|-----------------------------------------------|--------------------------------------------------------------------------------------------------------------------|
| spring-boot-starter-validation                | Fornece suporte de validação para aplicativos Spring Boot.                                                         |
| spring-boot-starter-data-jpa                  | Inclui o Spring Data JPA, que simplifica o acesso ao banco de dados usando a API de Persistência Java (JPA).        |
| spring-boot-starter-jdbc                      | Fornece suporte para conectividade com banco de dados JDBC (Java Database Connectivity) em aplicativos Spring Boot. |
| spring-boot-starter-oauth2-resource-server    | Habilita a funcionalidade de servidor de recursos OAuth2 em aplicativos Spring Boot.                                |
| spring-boot-starter-web                       | Inclui o Spring Web MVC e outros componentes relacionados à web para construir aplicativos da web.                 |
| h2                                            | Um banco de dados em memória para fins de desenvolvimento e teste.                                                  |
| spring-boot-configuration-processor            | Gera metadados para propriedades de configuração.                                                                   |
| lombok                                        | Uma biblioteca que simplifica o código Java, fornecendo anotações para gerar código boilerplate (como getters, setters e construtores). |
| lombok-mapstruct-binding                       | Uma biblioteca de binding para o Lombok e o MapStruct.                                                             |
| mapstruct                                      | Uma biblioteca para geração automática de código para mapeamento entre objetos.                                    |
| mapstruct-processor                            | Processador do MapStruct para geração de código.                                                                   |
| spring-boot-starter-test                       | Fornece suporte para testes em aplicativos Spring Boot.                                                             |


## Instruções de build
### Docker
Para buildar a aplicação via docker e roda-la em um conteiner:

Clonar o repositório da aplicação com o comando :

    git clone https://github.com/philipesan/isbn-favoritos.git

Após isso, no diretória que foi criado, executar os comando:

    docker compose up --build
Após isso, se a aplicação for buildada com sucesso, ela estará disponível na porta padrão para consumo.

### Maven

Após clonar o diretório do git, executar os seguintes comandos:

    mvn clean install
    mvn spring-boot:run
Após isso, se a aplicação for buildada com sucesso, ela estará disponível na porta padrão para consumo.
