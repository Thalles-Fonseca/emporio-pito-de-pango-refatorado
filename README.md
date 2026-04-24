# Empório Pito de Pango - Projeto Integrador

## Descrição do projeto

Este projeto foi desenvolvido como parte do Projeto Integrador, com o objetivo de refatorar e reorganizar o sistema desktop criado na etapa anterior, preparando sua estrutura para reutilização em um futuro sistema web.

A proposta principal foi separar as responsabilidades das classes, removendo o acoplamento entre interface gráfica, regras de negócio e persistência em banco de dados, aplicando princípios SOLID, especialmente o Princípio da Responsabilidade Única (SRP).

## Objetivos da refatoração

- Reaproveitar a lógica do sistema desktop anterior
- Separar regras de negócio da interface Java Swing
- Organizar o projeto em camadas
- Melhorar a manutenção e reutilização do código
- Aplicar princípios SOLID
- Eliminar code smells do projeto original
- Preparar a base para futura migração para sistema web

## Estrutura do projeto

```text
src/
└── main/
    ├── java/
    │   └── br/com/coffeshop/emporiopitodepango/
    │
    │       ├── EmporioPitoDePangoApplication.java
    │
    │       ├── controller/
    │       │   ├── HomeController.java
    │       │   └── ProdutoController.java
    │
    │       ├── model/
    │       │   ├── Produto.java
    │       │   ├── Cliente.java
    │       │   └── Pedido.java
    │
    │       ├── repository/
    │       │   ├── ConexaoBD.java
    │       │   ├── ProdutoRepository.java
    │       │   ├── ClienteRepository.java
    │       │   └── PedidoRepository.java
    │
    │       ├── service/
    │       │   ├── ProdutoService.java
    │       │   ├── ClienteService.java
    │       │   └── PedidoService.java
    │
    └── resources/
        ├── templates/
        │   ├── index.html
        │   ├── produtos.html
        │   ├── produto.html
        │   ├── produto-form.html
        │   ├── carrinho.html
        │   ├── contato.html
        │   └── sobre.html
        │
        ├── static/
        │   ├── css/
        │   │   └── style.css
        │   │
        │   ├── js/
        │   │   ├── produtos.js
        │   │   └── validacoes.js
        │   │
        │   └── img/
        │       └── LogoPitodePango.png
        │
        └── database.properties

Tecnologias utilizadas:
Java 17
NetBeans 13
Maven
MySQL
JDBC
Git
GitHub
Funcionalidades implementadas
Cadastro de produtos
Listagem de produtos
Atualização de produtos
Exclusão de produtos
Cadastro de clientes
Listagem de clientes
Busca de clientes
Atualização de clientes
Exclusão de clientes
Cadastro de pedidos
Listagem de pedidos
Busca de pedidos
Atualização de pedidos
Exclusão de pedidos
Princípios e padrões aplicados
SOLID

Foi aplicado principalmente o princípio:

SRP (Single Responsibility Principle): cada classe passou a ter apenas uma responsabilidade.
Padrões utilizados
Arquitetura em camadas
Repository
Service
Refatorações realizadas
Separação entre modelo, persistência e regras de negócio
Remoção de dependências da interface gráfica nas classes de lógica
Padronização de nomes de atributos e métodos
Correção de modelagem entre cliente e pedido
Melhoria no tratamento de exceções
Externalização da configuração do banco de dados
Banco de dados

O projeto utiliza o banco de dados MySQL coffeeshop_db.

As tabelas principais são:

cliente
produto
pedido



Como executar o projeto
Clonar o repositório
Abrir o projeto no NetBeans
Configurar o banco de dados MySQL
Criar o arquivo database.properties
Executar o script SQL do banco
Executar a classe principal do projeto
Testes

Os testes básicos foram realizados por meio do método main(), verificando:

cadastro
listagem
busca
atualização
exclusão

das entidades principais do sistema.

Autor

Projeto desenvolvido por Thalles Fonseca para fins acadêmicos no Projeto Integrador.

# emporio-pito-de-pango-web
