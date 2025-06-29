# Aula 02

## Arquitetura MVC e primeiras implementações

Arquitetura MVC é um padrão de arquitetura de software que divide a aplicação em três camadas: manipulação dos dados (model), interação do usuário (view) e camada de controle (controller).

A comunicação entre interfaces e regras de negócios é definida através de um controlador, que separa as camadas. Quando um evento é executado na interface gráfica, como um clique em um botão, a interface se comunicará com o controlador, que por sua vez se comunica com as regras de negócios.

De maneira resumida, as camada:
* **Model** é camada que representa os dados e regras de negócio.

* **View** é responsável pela apresentação da interface do usuário. Ela exibe informações ao usuário e recebe entrada. 

* **Controller** é a camada intermediário entre Model e a View que gerencia requisições e coordena fluxo entre Model e View.

### Benefícios do padrão MVC:

* Separação de responsabilidades.

* Facilita manutenção e testes.

* Código organizado e modular.

## Exploração da estrutura no Spring
* Controllers → `@RestController`

* Models (ou Entities) → classes que representam dados -> `@Entity`

* Services → camada intermediária onde é implementada a lógica de negócio 

* Repositories → camada de acesso ao banco de dados


## Criando elementos da camada Model, Controller e View.
