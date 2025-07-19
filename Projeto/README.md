# Sistema de Gerenciamento de Tarefas API

## Geração do workspace e instalação das dependências
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

## Subir o Banco de Dados

No terminal, execute o seguinte comando:

```
mysql -u <nomeDoUserRoot> -p
```
Digite a senha do usuário root.

Crie o banco de dados `sgpdatabase` executando o seguinte comando:
```
CREATE DATABASE sgpdatabase;
USE sgpdatabase;
```

## Configurar conexão da Aplicação com o Banco de Dados

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

No campo `spring.datasource.password` coloque a senha do usuário root para acessar o MySQL.

## Primeira execução da aplicação

Vamos executar a aplicação pela primeira vez seguindo os seguintes passos:

1. Abra o projeto no VS Code.
2. Abra a classe `SistemaDeGerenciamentoDeTarefasApplication` e execute.
Quando a aplicação estiver disponível, será exibido a seguinte informação nos logs:

```log
2025-07-02T10:03:21.876-03:00  INFO 975 --- [Sistema de Gerenciamento de Tarefas] [  restartedMain] stemaDeGerenciamentoDeTarefasApplication : Started SistemaDeGerenciamentoDeTarefasApplication in 1.386 seconds (process running for 1.631)
```

3. Após aparecer a mensagem de log acima, acesse [http://localhost:8080](http://localhost:8080):

## Criação das classes de domínio

Vá para /src/main/java/com/treina/recife/sgp e crie o pacote de modelo de nome `model` 

Crie as classes `Usuario`, `Tarefa` e `Projeto` com os respecitvos atributoes e anotações.

Derrube a aplicação e suba novamente. A mensagem de log seguinte será exibida no terminal indicando a conexão do seu projeto spring com o banco de dados.

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


### Solução para problema com a dependência Lombok (se houver): 
Para IDE **VS Code**: Instale a extensão Lombok Annotations Support for VS Code (se não tiver). Reinicie a IDE.

Para IDE **IntelliJ IDEA**: Vá em File → Settings (ou Preferences no mac) → Build, Execution, Deployment → Compiler → Annotation Processors. Marque *Enable annotation processing*

## Criação das classes Repository
No Spring Data JPA, os métodos no Repository podem ser derivados automaticamente do nome, seguindo a sintaxe query methods. Ou seja, o próprio Spring cria a query a partir do nome do método.

As interfaces Repository são responsáveis por acessar e manipular os dados no banco de forma simples e direta. Elas servem como uma ponte entre a aplicação e o banco de dados, permitindo salvar, buscar, atualizar e excluir informações.


### Estrutura geral do nome dos métodos

```
<Prefixo>By<Atributo1>[And|Or<Atributo2>][OrderBy<Atributo3Asc|Desc>]
```

### Filtros (atributos)
Após a palavra `By`, vem o nome dos campos da entidade, exatamente como estão na classe, respeitando camelCase. Por exemplo:

```java
List<User> findByName(String name);
```
Se for um campo aninhado (relacionamento)

```java
List<Tarefa> findByProjetoProjectId(Long projectId);
```

```Projeto``` é o atributo na entidade `Tarefa`.

`ProjectId` é o campo dentro de `Projeto`.


## Criação das classes Services

### Função principal
Contém a lógica de negócio da aplicação.

Realiza operações como:

- Validações complexas.

- Cálculos.

- Regras específicas de domínio.

- Coordenação de múltiplas operações (transações, chamadas a repositórios, etc).

- Serve como uma camada intermediária entre o Controller e o acesso a dados (Repository/DAO).

- Define contratos claros por meio de interfaces, facilitando a manutenção e testes.

### Por que usar interface + implementação?

Separação de contratos e implementação: facilita trocar implementações sem mudar quem usa o serviço.

Facilita testes: você pode mockar as interfaces para testes unitários dos controllers.

Permite múltiplas implementações: por exemplo, uma implementação real e outra para testes ou integração.


## Criação das classes Controller

### Função principal

As classes Controller são responsáveis por receber e intermediar as requisições HTTP feitas pelo frontend ou clientes REST. Elas capturam os dados enviados, validam informações básicas e delegam a lógica de negócio para as classes de Service. Além disso, formatam e enviam as respostas apropriadas para o cliente, incluindo status e dados.

Essa organização facilita a criação de APIs claras e bem estruturadas, centraliza o ponto de entrada do sistema e separa a lógica de comunicação da lógica de negócio, deixando o código mais organizado e fácil de manter.

## Requisições
As requisições a SGP API vai se dar por meio de URLs onde o spring vai identificar qual é a classe específica que a request vai. Abaixo seguem alguns bodys que serão necessários em algumas requisições.

### Usuários

- **POST**
```json
{
    "nome": "Isabela",
    "cpf": "123.456;789-72",
    "email": "isabelanogueira@email.com",
    "senha": "senha1234",
    "dataNascimento": "22/12/2000"
}
```
- **PUT**
```json
{
    "nome": "Isabela Nogueira",
    "cpf": "123.456;789-72",
    "email": "isabelanogueira@email.com",
    "senha": "senha1234",
    "dataNascimento": "22/12/2000"
}
```

- **PATCH** Atualizar status
```json
{
    "status": "inativo"
}
```

### Projeto
- **POST**
```json
{
    "nome": "Criação de portal institucional",
    "descricao": "Portal de inovação da Prefeitura do Recife",
    "dataInicio": "22/05/2025",
    "dataConclusao": "22/05/2027"
}
```

- **PUT**

```json
{
   "nome": "Criação de portal institucional TESTE",
    "descricao": "Portal de inovação da Prefeitura do Recife",
    "dataInicio": "22/05/2025",
    "dataConclusao": "22/05/2027",
    "status": "ATIVO"
}
```

### Tarefa

**POST**

```json
{
    "titulo": "Mapear Processos Administrativos Atuais",
    "dataCriacao": "19/07/2025",
    "dataConclusao": "30/07/2025",
    "prioridade": "BAIXA",
    "status": "PENDENTE"
}
```

**PUT**

```json
{
    "titulo": "Mapear Processos Administrativos Atuais",
    "dataCriacao": "19/07/2025",
    "dataConclusao": "30/08/2025",
    "prioridade": "BAIXA",
    "status": "PENDENTE",
    "userId": 1,
    "projectId": 1
}
```

**PATCH**

```json
{
    "status" : "FAZENDO"
}
```