# Changelog

Todas as alterações relevantes deste projeto serão documentadas neste arquivo.

O formato segue as recomendações do **Keep a Changelog** e utiliza **Versionamento Semântico (SemVer)**.

---

## [1.0.0] - 2026-07-31

### 🎉 Primeira Release

#### Adicionado

- Migração do projeto Desktop para Spring Boot Web.
- Estrutura MVC utilizando Spring Boot e Thymeleaf.
- Cadastro de produtos.
- Cadastro de clientes.
- Cadastro de fornecedores.
- Controle de pedidos.
- Carrinho de compras.
- Checkout.
- Tela administrativa.
- Módulo de caixa.
- Gerenciamento de usuários.
- Serviços e repositórios para novos módulos.
- Estrutura inicial de segurança.
- Dockerfile.
- Docker Compose.
- Arquivo `.env.example`.
- Documentação de deploy (`DEPLOY.md`).
- Pipeline de CI/CD com GitHub Actions.

#### Alterado

- Refatoração da arquitetura do projeto.
- Organização em camadas (Controller, Service, Repository e Model).
- Atualização do README.
- Atualização do `pom.xml`.
- Melhorias nas páginas Thymeleaf.
- Melhorias na camada de acesso ao banco.
- Atualização da configuração do Spring Boot.

#### Removido

- `database.properties`
- `example.database.properties`

---

## [Unreleased]

### Planejado

- JWT Authentication
- Spring Security completo
- DTOs
- Validações Bean Validation
- Swagger / OpenAPI
- Tratamento Global de Exceções
- Flyway
- Testes Unitários
- Testcontainers
- Deploy automatizado