# ☕ Empório Pito de Pango

Sistema Web desenvolvido em **Java + Spring Boot** para gerenciamento de uma CoffeShop, permitindo o controle de produtos, clientes, fornecedores, pedidos, carrinho de compras e administração do sistema.

O projeto nasceu da refatoração de uma aplicação Desktop em Java Swing e evoluiu para uma aplicação Web seguindo boas práticas de arquitetura, organização em camadas e preparação para deploy.

---

## 📸 Demonstração

> Em desenvolvimento.

---

## 🚀 Funcionalidades

### Clientes

- Visualização do catálogo de produtos
- Carrinho de compras
- Checkout
- Confirmação de pedidos
- Página inicial institucional
- Página Sobre
- Página Contato

### Administração

- Gerenciamento de Produtos
- Gerenciamento de Clientes
- Gerenciamento de Fornecedores
- Gerenciamento de Pedidos
- Gerenciamento de Usuários
- Controle de Caixa

---

## 🛠 Tecnologias

### Backend

- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- JDBC
- Maven

### Frontend

- Thymeleaf
- HTML5
- CSS3
- JavaScript

### Banco de Dados

- MySQL

### DevOps

- Docker
- Docker Compose
- GitHub Actions
- Git

---

## 📁 Estrutura do Projeto

```
src
 ├── controller
 ├── service
 ├── repository
 ├── model
 ├── security
 ├── config
 └── resources
```

---

## ⚙️ Executando o projeto

### Clone o repositório

```bash
git clone https://github.com/Thalles-Fonseca/emporio-pito-de-pango-refatorado.git
```

### Entre na pasta

```bash
cd emporio-pito-de-pango-refatorado
```

### Configure as variáveis de ambiente

Copie

```
.env.example
```

para

```
.env
```

e ajuste as credenciais do banco.

### Executando com Docker

```bash
docker compose up --build
```

ou

### Executando com Maven

```bash
mvn spring-boot:run
```

---

## 📦 Estrutura de Deploy

O projeto possui:

- Dockerfile
- Docker Compose
- GitHub Actions (CI/CD)
- Arquivo DEPLOY.md

---

## 🔄 Roadmap

### ✅ Concluído

- Spring Boot
- Spring MVC
- Thymeleaf
- CRUD Produtos
- CRUD Clientes
- CRUD Fornecedores
- CRUD Pedidos
- Carrinho
- Checkout
- Administração
- Docker
- CI/CD

### 🚧 Próximas implementações

- JWT Authentication
- Spring Security completo
- Swagger/OpenAPI
- Flyway
- DTOs
- Bean Validation
- Testes Unitários
- Testcontainers
- Dashboard Administrativo
- Deploy em Cloud

---

## 📚 Documentação

- CHANGELOG.md
- DEPLOY.md

---

## 👨‍💻 Autor

**Thalles Fonseca**

GitHub:
https://github.com/Thalles-Fonseca

LinkedIn:
www.linkedin.com/in/thalles-fonseca-correa



---

## 📄 Licença

Projeto desenvolvido para fins de estudo e portfólio.