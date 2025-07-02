
# Spring Boot

## Antes do Spring Boot

Antes do advento do Spring, o **desenvolvimento Java** de aplicações empresariais era conhecido por sua **complexidade**, **verbosidade** e **dificuldade de configuração**, especialmente com o modelo EJB (Enterprise JavaBeans). O Spring surgiu para simplificar esse processo, introduzindo conceitos como Inversão de Controle (IoC) e Injeção de Dependência (DI). 

Exigia-se **muita configuração manual**, incluindo a criação de arquivos XML extensos. Isso tornava o processo **mais demorado, trabalhoso e propenso a erros**.  

O Spring Boot foi criado justamente para simplificar esse cenário. Ele oferece **autoconfiguração**, uma abordagem opinativa para as configurações e a possibilidade de criar aplicativos independentes (standalone), tornando o desenvolvimento **mais rápido, ágil e produtivo**.

As principais dificuldades no desenvolvimento com Spring tradicional incluíam:

- **Gerenciamento manual de dependências**: era necessário adicionar e gerenciar as versões das bibliotecas manualmente, o que aumentava a chance de conflitos e inconsistências.
- **Configuração repetitiva**: grande parte das configurações precisava ser reescrita em diferentes projetos, resultando em retrabalho.
- **Alta complexidade**: a dependência de arquivos XML e múltiplas configurações dificultava a manutenção e a evolução do código.

### O que o Spring Boot trouxe de diferente

- **Autoconfiguração**: o Spring Boot analisa o projeto e configura automaticamente os beans e dependências, eliminando a necessidade de configuração manual.
- **Abordagem opinativa**: oferece configurações padrão (convenções) para a maioria dos casos de uso, permitindo ao desenvolvedor focar na lógica de negócio.
- **Aplicativos standalone**: permite criar aplicativos autossuficientes, que podem ser executados diretamente, sem necessidade de um servidor de aplicação externo.

## O que é o Spring

O Spring é um **framework open source** para a plataforma Java, com uma comunidade ativa e um ecossistema robusto (Spring Boot, Spring Data, Spring Security, entre outros).  

Ele se baseia nos princípios de **Injeção de Dependência (DI)** e **Inversão de Controle (IoC)**, configurados principalmente via anotações.

### Inversão de Controle (IoC)

A Inversão de Controle (IoC) é um princípio de design onde o controle sobre a criação e gerenciamento de objetos passa a ser responsabilidade do framework (ou container), em vez do próprio desenvolvedor.  

Quando uma classe precisa de outra, o Spring se encarrega de "injetar" a dependência automaticamente.

**Exemplo sem IoC:**

```java
public class VendaDeProduto {
	
    public void vendeProduto(Produto produto) {
        // Código para vender o produto
        Log log = new Log("Arquivo.txt");
        log.grava(produto);
    }
}
```

**Com IoC:**

```java
public class VendaDeProduto {
    
    private Log log;

    public VendaDeProduto(Log logVenda) {
        this.log = logVenda;
    }

    public void vendeProduto(Produto produto) {
        // Código para vender o produto
        log.grava(produto);
    }
}
```

### Injeção de Dependência

A **Injeção de Dependência (Dependency Injection)** é uma forma prática de implementar IoC, onde as dependências são fornecidas (ou "injetadas") ao invés de serem criadas manualmente pela classe.

**Exemplo:**

```java
@Autowired
private PagamentoService pagamentoService;
```

## Estrutura geral de um projeto Spring Boot

- `src/main/java`: contém o código-fonte Java.
- `src/main/resources`: arquivos de configuração, `application.properties`, templates e recursos estáticos.
- `pom.xml` ou `build.gradle`: gerencia as dependências do projeto.
- Classe com `@SpringBootApplication`: ponto de entrada da aplicação (contém o método `main`).

## Estrutura do Spring

### Spring Core Container

Responsável por funcionalidades fundamentais como Injeção de Dependência e Inversão de Controle, o Core Container gerencia os objetos (beans) da aplicação.

#### Beans

No Spring, um **bean** é um objeto gerenciado pelo container. Eles podem ser definidos por anotações como `@Component` ou configurados explicitamente com `@Bean`.

O container Spring cuida da criação, configuração e ciclo de vida dos beans, utilizando anotações como `@Autowired` para injeção.

#### Core

O módulo **Spring Core** contém o núcleo de funcionalidades, incluindo gerenciamento de beans e implementação dos princípios de IoC e DI.

## Gerenciamento de dependências

O Maven ou o Gradle são os responsáveis por gerenciar as bibliotecas do projeto, facilitando a inclusão e atualização de dependências.