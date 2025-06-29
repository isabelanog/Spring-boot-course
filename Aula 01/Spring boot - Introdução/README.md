# Spring Boot

## Definição
É uma ferramenta de alavancagem de produtividade e qualidade.

Spring é uma plataforma Java que disponibiliza, de forma ordenada e compreensiva, uma infraestrutura para o desenvolvimento de aplicações corporativas. O Spring cuida da infraestrutura e você, desenvolvedor, de sua aplicação/negócio.

Antes do Spring, aplicações Java eram baseadas em Servlets e EJBs (Enterprise Java Beans).

Problemas: configuração complexa, muito XML, pouca flexibilidade.

Spring surgiu em 2003 para simplificar desenvolvimento e trazer modularidade.

Reduz configuração manual.

Baseado em boas práticas (injeção de dependência, separação de responsabilidades).

Comunidade ativa e enorme ecossistema (Spring Boot, Spring Data, Spring Security, etc.).

## Conceitos fundamentais

### Inversão de Controle (IoC)
Inversão de Controle (IoC) é um princípio de design onde o controle de criação e gerenciamento dos objetos não fica mais na sua mão, mas sim no framework ou container (como o Spring).


Quando uma classe precisa de outra, Spring "injeta" automaticamente.

Exemplo sem inversão de contrle:

```java
public class VendaDeProduto {
	
    public void vendeProduto(Produto produto) {
	    //Todo o código para a venda do produto...
	    Log log = new Log("Arquivo.txt");
	    log.grava(produto);
	}
}
```

Com inversão de controle:

```java
public class VendaDeProduto {
    
    private Log log;

    public void VendaDeProduto(Log logVenda) {
            this.log = logVenda;
    }

public void vendeProduto(Produto produto) {
        //Todo o código para a venda do produto...
        log.grava(produto);
}
}
```

### Injeção de Dependência

Injeção de Dependência (Dependency Injection) é uma forma de implementar IoC, onde as dependências de uma classe são fornecidas (injetadas) em vez de a própria classe criar.

```java
@Autowired
private PagamentoService pagamentoService;
```



## Estrutura geral de um projeto Spring Boot
`src/main/java`: código Java.

`src/main/resources`: configs, `application.properties`, static, templates.

`pom.xml` ou `build.gradle`: gerenciador de dependências.

Classe com `@SpringBootApplication`: ponto de entrada (método `main`).


## Dependências

Maven ou Gradle cuidam das bibliotecas.


## Primeira aplicação Spring Boot (Hello World)

* Criar projeto com Spring Initializr

* Acessar https://start.spring.io/

* Configurações básicas:

    * Project: Maven

    * Language: Java
    * Selecione a versão 3.5.3 do Spring boot 
    * Java version: 17
    * Packaging: Jar
    * Dependencies: Spring Web

* Em **Project Metadata**:

    * Group: com.treina.recife
    * Artifact:  MyFirstSrpingBootProject
    * Name: MyFirstSrpingBootProject
    * Description: Introdução ao curso de spring boot
    * Package name: com.treina.recife.MyFirstSrpingBootProject

* Clique em GENERATE 
* Será feito o download do `MyFirstSrpingBootProject.zip`
* Extraia o arquivo
* Mova o arquivo para a sua pasta do projeto
* Abra o arquivo no VS Code
* Vá na classe `MyFirstSpringBootProject` e clique no botão "executar"
* Após a mensagem de log abaixo for exibida, vá para http://localhost:8080

```log
2025-06-29T18:32:12.295-03:00  INFO 3448 --- [MyFirstSrpingBootProject] [main] .r.M.MyFirstSrpingBootProjectApplication : Started MyFirstSrpingBootProjectApplication in 1.044 seconds (process running for 1.202)
```


