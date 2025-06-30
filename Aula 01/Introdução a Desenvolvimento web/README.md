# Aula 01 

##  Introdução ao desenvolvimento web

### O que é front-end?
Parte que o usuário vê e interage diretamente (ex: páginas HTML, botões, formulários).

Responsável por:

* Interface visual

* Experiência do usuário (UX)

* Comunicação com APIs (back-end)


### O que é back-end?
Parte que roda no servidor, "por trás dos panos".

Responsável por:

* Lógica de negócio

* Acesso a banco de dados

* Segurança e autenticação

* Gerar respostas para o front

### Fluxo básico de uma requisição web
1️⃣ Usuário acessa um site (por exemplo, clicar num botão "Ver produtos").

2️⃣ Front envia uma request HTTP para o servidor.

3️⃣ O servidor (back-end) processa e devolve uma response HTTP.

4️⃣ Front exibe as informações (ex: lista de produtos).

<div align="center">
  <img src="Diagram.png" alt="Diagram" width="600"/>
</div>


## O que é HTTP?

HTTP significa HyperText Transfer Protocol (Protocolo de Transferência de Hipertexto). É o protocolo padrão usado para comunicação entre clientes (como navegador ou um app) e servidores web.

### Como funciona?
Quando você digita uma URL no navegador, por exemplo:

    http://www.exemplo.com
  
Seu navegador faz uma *request HTTP* para o servidor.
O servidor devolve uma *response HTTP* (por exemplo, a página HTML).

### Características do HTTP
* Stateless: cada requisição é independente. O servidor não guarda "memória" das requisições anteriores.

* Baseado em texto: fácil de ler (você consegue ver headers, body etc.).

* Usa verbos: GET, POST, PUT, DELETE etc.

#### Métodos HTTP:

* **GET**: recupera um recurso

* **POST**: cria um recurso
* **PATCH**: atualizar parcialmente o recurso
* **PUT**: atualiza o recurso existente.
* **DELETE**: remove o recurso.

## HTTPS
* HTTPS significa HyperText Transfer Protocol Secure.

* É basicamente HTTP + segurança (SSL/TLS).

* Criptografa os dados trocados (ex: senhas, informações pessoais).

* Verifica autenticidade do servidor (certificado digital).

* Garante integridade (ninguém pode alterar os dados no caminho).



| **HTTP**                                  | **HTTPS**                                                            |
| ----------------------------------------- | -------------------------------------------------------------------- |
| Sem criptografia.                         | Com criptografia (SSL/TLS).                                          |
| Dados podem ser interceptados.            | Dados são protegidos contra interceptação e alteração.               |
| Usado em sites sem informações sensíveis. | Recomendado (ou obrigatório) para login, pagamentos, dados pessoais. |


### URL – O Endereço Digital
URL significa Uniform Resource Locator, ou Localizador Uniforme de Recursos é o endereço completo que usamos para acessar algum recurso na internet, como uma página web, uma API ou um arquivo.
A URL indica para onde a request deve ir e o que está pedido. 

Exemplo de URL:

<div align="center">
  <img src="url.png" alt="URL Diagram" width="700"/>
</div>


### O que é uma request?
Pedido enviado pelo cliente (navegador ou app) para o servidor.

Contém:

* Método HTTP (GET, POST, PUT, DELETE)

* URL

* Cabeçalhos (headers): informações extras (ex: tipo de conteúdo, autorização)

* Body (em alguns métodos): dados enviados (ex: cadastro de usuário)


Exemplo de request:

```
POST /api/usuarios HTTP/1.1
Host: api.exemplo.com
Content-Type: application/json
Authorization: Bearer abcdef12345
User-Agent: PostmanRuntime/7.32.3
Accept: application/json
Content-Length: 74
Body (payload):
{
  "nome": "Maria Silva",
  "email": "maria@exemplo.com",
  "senha": "123456"
}
```

### O que é uma response?
Resposta do servidor para o cliente.

Contém:

* Status code (ex: 200 OK, 404 Not Found, 500 Internal Server Error)

* Headers (informações sobre resposta)

* Body (conteúdo retornado, ex: JSON com dados)

Códigos de Status HTTP 

* 200 OK: tudo correu bem.

* 201 Created: algo novo foi criado.

* 400 Bad Request: ops, algo deu errado na sua solicitação.

* 404 Not Found: o que você queria não está lá.

* 500 Internal Server Error: algo deu errado no lado do outro programa.

Exemplo de response:

```
HTTP/1.1 201 Created
Content-Type: application/json
Location: /api/usuarios/10
Date: Sat, 29 Jun 2025 18:00:00 GMT

Body:
{
  "id": 10,
  "nome": "Maria Silva",
  "email": "maria@exemplo.com",
  "criadoEm": "2025-06-29T18:00:00Z"
}
```

### Algumas formato de retorno do response

* **HTML** : Quando você acessa uma página web tradicional no navegador, Oservidor devolve um documento HTML completo, que será renderizado na tela.
* **JSON** :  JavaScript Object Notation (JSON) é Forma mais comum em APIs REST. Os dados são estruturados em chave-valor. Fácil de manipular em front-end ou em aplicativos.
Exemplo:

```json
{
  "id": 1,
  "nome": "Camisa",
  "preco": 79.9
}
```
* **XML**: Muito usado antigamente, ou em integrações legadas (ex: SOAP). Os dados são estruturado em tags. Exemplo:

```xml
<produto>
  <id>1</id>
  <nome>Camisa</nome>
  <preco>79.9</preco>
</produto>
```

⚖️ O que determina o formato do response? O tipo de conteúdo (Content-Type) especificado no header do response.

Exemplos:

`Content-Type: application/json`

`Content-Type: text/html`

`Content-Type: application/pdf`

`Content-Type: image/png`


### O que é uma API?

API (Application Programming Interface), ou Interface de Programação de Aplicações, é uma interface que permite que um sistema converse com outro. É um *meio de comunicação* entre sistemas ou partes de um software.

APIs web geralmente seguem estilo REST.

Exemplo do dia a dia:

Um app de delivery consulta uma API para mostrar os restaurantes disponíveis.

Você clica → o app envia request → API responde com lista de pratos.

#### Benefícios das APIs
* Permitem integrar sistemas diferentes.

* Separação clara entre front-end e back-end.

* Reutilização: o mesmo back-end pode atender diferentes front-ends (web, mobile, etc.).

#### Exemplos famosos de APIs
* [The movie database](https://developer.themoviedb.org/docs/getting-started)


 Buscar livros por palavra-chave

**GET** https://api.themoviedb.org/3/search/movie?api_key=8af0020b2964b97740da52dcd4438fab&query=inception

Busca todos os filmes que contêm “inception” no título ou descrição.

Listar filmes mais populares:

**GET** https://api.themoviedb.org/3/movie/popular?api_key=8af0020b2964b97740da52dcd4438fab&page=1


* API do Twitter (postar ou ler tweets)

* API de pagamento (ex: Stripe, PayPal)

### API REST

Uma API REST (ou RESTful API) é uma interface de programação que segue os princípios do estilo arquitetural REST (Representational State Transfer).

Ela permite que diferentes sistemas se comuniquem através da web, usando o protocolo HTTP (o mesmo que navegadores usam).

#### Princípios básicos do REST

* Baseado em recursos

  * Cada "coisa" (ex: usuários, produtos, pedidos) é um recurso.

  * Cada recurso tem uma URL única (por exemplo: `/api/usuarios`).

* Desacoplamento cliente-servidor

* Uso de verbos HTTP
* Possibilidade de armazenamento em cache
* Stateless (sem estado)

  * Cada request deve conter toda a informação necessária.

  * O servidor não guarda contexto entre as requisições.

* Representações

  * Os dados do recurso são enviados em representações, geralmente JSON ou XML.

#### Por que REST?
* Simples e usa HTTP (amplamente suportado).

* Escalável.

* Flexível (qualquer linguagem ou tecnologia pode consumir).