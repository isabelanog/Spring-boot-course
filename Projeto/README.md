

## Criando a aplicação Spring Boot Sistema de Gerenciamento de Tarefas - SGP

1. Acesse o [Spring Initializr](https://start.spring.io/).
2. Configure o projeto:
   - **Project**: Maven
   - **Language**: Java
   - **Spring Boot**: 3.5.3
   - **Java version**: 17
   - **Packaging**: Jar
    Em **Project Metadata**:
   - **Group**: `com.treina.recife`
   - **Artifact**: `sgp`
   - **Name**: `Sistema de Gerenciamento de Tarefas`
   - **Description**: API destinada ao Sistema de Gerencimaneto de Projetos (SGP)
   - **Package name**: `com.treina.recife.sgp`

3.Em **Dependencies**, selecione as seguintes dependências:
   - Lombok
   - Spring Web
   - Validation
   - JPA
    - DevTools
    - MySQL
4. Clique em **Generate**.
5. Será feito o download do arquivo `SGP.zip`. Extraia o arquivo.
7. Abra o projeto no VS Code.
8. Abra a classe `SistemaDeGerenciamentoDeTarefasApplication` e execute.
Quando a aplicação estiver disponível, será exibido a seguinte informação nos logs:

```log
2025-07-02T10:03:21.876-03:00  INFO 975 --- [Sistema de Gerenciamento de Tarefas] [  restartedMain] stemaDeGerenciamentoDeTarefasApplication : Started SistemaDeGerenciamentoDeTarefasApplication in 1.386 seconds (process running for 1.631)
```

9. Após aparecer a mensagem de log acima, acesse [http://localhost:8080](http://localhost:8080):

Após verificar que aplicação está desponível, seguiremos para os seguintes passos:

## Configurar conexão com o banco de dados

Vá para main/com/treina/recife/sgp/resources e abra o arquivo `aplication.properties`
Insira no arquivo `application.properties` as seguintes linhas:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sgpdatabase
spring.datasource.username=root
spring.datasource.password=senha123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

```

## Criação do pacote Model 

Crie o pacote de modelo de nome `model`

Crie as classes `Usuario`, `Tarefa` e `Projeto`.

Inicialize o projeto. A mensagem de log abaixo vai ser exibida no terminal, indicando a conexão do seu projeto spring com o banco de dados.

```log
2025-07-02T14:03:10.735-03:00  INFO 3110 --- [Sistema de Gerenciamento de Tarefas] [  restartedMain] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection com.mysql.cj.jdbc.ConnectionImpl@429220da
```

A seguinte mensagem demonstra a criação da tabela "Projeto" no banco de dados:

```log
    create table projeto (
        id bigint not null auto_increment,
        descricao varchar(255) not null,
        nome varchar(255) not null,
        primary key (id)
    ) engine=InnoDB
```