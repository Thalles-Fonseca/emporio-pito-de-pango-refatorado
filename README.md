# Empório Pito de Pango

Sistema web da cafeteria Pito de Pango: loja pública (catálogo, carrinho e
checkout) e painel administrativo (produtos, estoque, fornecedores, caixa e
usuários) numa aplicação Spring Boot só, com um banco MySQL único.

Este projeto nasceu como a evolução web do sistema desktop
[`PitoDePango`](../PitoDePango) (Java Swing), desenvolvido em etapa anterior
do Projeto Integrador. O objetivo desta fase foi unificar as duas frentes -
a parte administrativa/logística (antes só no desktop) e a loja (este
projeto) - numa aplicação única, pronta pra deploy. Com a Fase 4 (abaixo),
este sistema web passou a cobrir 100% do que o desktop fazia, e o
`PitoDePango` foi formalmente descontinuado.

## Arquitetura

Uma aplicação, dois públicos, um banco:

```text
┌───────────────────────────────────────────────────┐
│           Aplicação Spring Boot (1 deploy)         │
│                                                     │
│  ┌───────────────────────┐  ┌────────────────────┐ │
│  │   Loja (pública)       │  │  Painel (/admin)    │ │
│  │   catálogo, carrinho,  │  │  produtos,          │ │
│  │   checkout             │  │  fornecedores,      │ │
│  │                        │  │  caixa, usuários    │ │
│  └───────────┬────────────┘  └──────────┬──────────┘ │
│              └─────────────┬────────────┘            │
│                  Services + Repositories              │
└─────────────────────────────┬──────────────────────────┘
                               │
                     MySQL (coffeeshop_db)
```

- **Loja**: aberta pra qualquer visitante. Checkout hoje é "convidado" (sem
  login) - login de cliente com cadastro de benefícios fica como próximo
  passo, e quando existir vai ser um segundo "realm" de autenticação
  (`/minha-conta/**`), separado do login da equipe, sem misturar os dois.
- **Painel administrativo** (`/admin/**`): exige login, com 3 perfis
  (`GERENTE`, `FINANCEIRO`, `ATENDENTE`), cada um com acessos diferentes.

## Tecnologias

- Java 17
- Spring Boot 3 (Web MVC, Security, Thymeleaf)
- MySQL (JDBC puro, sem JPA/Hibernate - repositories escritos à mão)
- Maven
- Docker / Docker Compose
- BCrypt (hash de senha)

## Estrutura do projeto

```text
src/
└── main/
    ├── java/br/com/coffeshop/emporiopitodepango/
    │   ├── EmporioPitoDePangoApplication.java
    │   │
    │   ├── config/
    │   │   └── AdminSeeder.java           # cria o 1º usuário admin se o banco estiver vazio
    │   │
    │   ├── security/
    │   │   ├── SecurityConfig.java        # regras de acesso por perfil
    │   │   └── UsuarioDetailsService.java # ponte entre login e tabela "usuario"
    │   │
    │   ├── controller/
    │   │   ├── HomeController.java        # páginas institucionais da loja
    │   │   ├── ProdutoController.java     # catálogo + CRUD de produto
    │   │   ├── CarrinhoController.java    # carrinho e checkout
    │   │   ├── CarrinhoModelAdvice.java   # contador do carrinho no cabeçalho
    │   │   ├── AdminController.java       # login e dashboard do painel
    │   │   ├── FornecedorController.java
    │   │   ├── CaixaController.java
    │   │   └── UsuarioController.java
    │   │
    │   ├── model/
    │   │   ├── Produto.java, Cliente.java, Pedido.java
    │   │   ├── Fornecedor.java, Caixa.java, Usuario.java
    │   │   └── Carrinho.java, ItemCarrinho.java   # carrinho em sessão
    │   │
    │   ├── repository/     # acesso a dados via JDBC puro (ConexaoBD)
    │   └── service/        # regras de negócio e validações
    │
    └── resources/
        ├── application.properties   # config via variável de ambiente, com default de dev local
        ├── schema.sql                # script único de criação do banco
        ├── templates/
        │   ├── index.html, produtos.html, produto.html, carrinho.html,
        │   │   checkout.html, pedido-confirmado.html, sobre.html, contato.html
        │   ├── fragments/topo-loja.html      # cabeçalho único da loja
        │   └── admin/                        # telas do painel administrativo
        │       ├── fragments/topo.html
        │       ├── login.html, dashboard.html
        │       ├── fornecedores.html, fornecedor-form.html
        │       ├── caixa.html, caixa-form.html
        │       └── usuarios.html, usuario-form.html
        └── static/{css,js,img}

Dockerfile, docker-compose.yml, .env.example, DEPLOY.md   # infraestrutura de deploy
```

## Funcionalidades

**Loja (pública)**
- Catálogo de produtos com estoque em tempo real
- Adicionar ao carrinho, atualizar quantidade, remover item
- Checkout convidado (sem exigir login) com criação/atualização de cliente
- Pedido persistido no banco e **baixa automática do estoque** ao finalizar

**Painel administrativo** (login obrigatório)
- Produtos: cadastro, listagem, exclusão
- Fornecedores: CRUD completo
- Clientes: cadastro, busca por nome/CPF, edição, exclusão
- Pedidos: histórico completo com detalhe (consulta e exclusão - editar um
  pedido já feito não é permitido, por decisão do time)
- Caixa: abertura e histórico de registros
- Usuários do painel: cadastro e exclusão (só `GERENTE`)
- Controle de acesso por perfil (`GERENTE` / `FINANCEIRO` / `ATENDENTE`)

## Como rodar localmente

**Com Docker (recomendado - sobe o app + o banco já configurado):**

```bash
docker compose up --build
```

Acesse http://localhost:8080. Veja mais detalhes e o passo a passo de deploy
em produção no [`DEPLOY.md`](DEPLOY.md).

**Sem Docker:**

```bash
# 1. Crie o banco a partir do schema.sql
mysql -u root -p < src/main/resources/schema.sql

# 2. Rode a aplicação (usa localhost:3306, usuário root, sem senha por padrão)
mvn spring-boot:run
```

### Primeiro acesso ao painel

Se a tabela `usuario` estiver vazia, a aplicação cria automaticamente na
primeira subida:

- usuário: `admin`
- senha: `troque123`
- perfil: `GERENTE`

**Troque essa senha (ou crie outro `GERENTE` e exclua o `admin`) assim que
entrar.** Isso é só um usuário inicial pra você conseguir logar pela
primeira vez, não deve ficar assim em produção.

### Variáveis de ambiente

| Variável | Uso | Padrão local |
|---|---|---|
| `DATABASE_URL` | string JDBC do MySQL | `jdbc:mysql://localhost:3306/coffeeshop_db` |
| `DATABASE_USERNAME` | usuário do banco | `root` |
| `DATABASE_PASSWORD` | senha do banco | (vazio) |
| `PORT` | porta HTTP da aplicação | `8080` |

Veja `.env.example` para mais detalhes.

## Testes

Testes automatizados cobrindo as regras de cálculo de pedido:
`CalculadoraPedidoServiceTest`.

## Histórico de mudanças (por fase)

O projeto original (refatoração do sistema desktop pra camadas
model/repository/service/controller) foi evoluído em 4 fases pra virar o
sistema integrado e pronto pra deploy que está aqui hoje.

### Fase 0 — Higienização de banco e configuração
- Criado `schema.sql` único e versionado com todas as tabelas
  (`usuario`, `fornecedor`, `cliente`, `produto`, `pedido`, `caixa`),
  substituindo a criação manual via MySQL Workbench.
- Corrigido `ConexaoBD`: lia um arquivo/chaves que não existiam
  (`db.url`/`db.user`/`db.password` de dentro de `application.properties`) e
  por isso a conexão sempre falhava. Agora é um `@Component` do Spring que lê
  `spring.datasource.*` corretamente, com resolução de variável de ambiente.
- `application.properties` passou a ter valores padrão de desenvolvimento
  embutidos (`${DATABASE_URL:jdbc:mysql://localhost:3306/coffeeshop_db}`),
  então roda local sem configurar nada, e aceita variável de ambiente em
  produção.
- Removidos `database.properties`/`example.database.properties`, órfãos
  desde a correção acima.
- Corrigido bug no `ProdutoControle` do sistema desktop: o `UPDATE` gravava
  na coluna errada (`data` em vez de `data_cadastro`).

### Fase 1 — Painel administrativo e autenticação
- Portados `Fornecedor`, `Caixa` e `Usuario` para o padrão
  model/repository/service/controller já usado por `Produto`.
- Adicionado Spring Security com 3 perfis (`GERENTE`, `FINANCEIRO`,
  `ATENDENTE`), login com senha em hash BCrypt (antes era texto puro,
  comparado direto em SQL).
- Criado `AdminSeeder` pra gerar o primeiro usuário `GERENTE` automaticamente
  quando o banco está vazio.
- Cadastrar/editar/excluir produto passou a exigir login - antes disso
  qualquer visitante da loja conseguia mexer no catálogo.
- Corrigido bug de descoberta: o link de login só existia em uma página; o
  cabeçalho da loja (duplicado em 7 templates) foi unificado num fragmento
  único (`fragments/topo-loja.html`) com links condicionais de
  entrar/painel conforme o login.

### Fase 2 — Carrinho e checkout de verdade
- Carrinho de compras real, guardado na sessão HTTP (`Carrinho`,
  `@SessionScope`) - sem precisar de login nem tabela nova no banco.
- `ClienteRepository`/`PedidoRepository` viraram beans do Spring de fato
  (não eram antes); `PedidoRepository` passou a deixar o banco gerar
  `numero_pedido` via `AUTO_INCREMENT` em vez de exigir que o chamador
  soubesse o próximo número.
- `CarrinhoController` com fluxo completo: adicionar, alterar quantidade,
  remover, checkout convidado.
- Checkout persiste `Cliente` e `Pedido` de verdade e **baixa o estoque**
  do produto (`ProdutoService`) - fechando o ciclo entre venda na loja e
  controle de estoque do painel.
- Removido `TesteManual.java` (smoke test manual incompatível com as novas
  assinaturas) e o contador de carrinho falso via `localStorage` que
  conflitava com o contador real do servidor.

### Fase 3 — Preparação para deploy
- `Dockerfile` multi-stage (build com Maven, execução só com JRE, usuário
  não-root).
- `docker-compose.yml` pra subir app + MySQL local com o `schema.sql`
  aplicado automaticamente.
- `.env.example` documentando as variáveis de ambiente esperadas em
  produção.
- `DEPLOY.md` com o passo a passo de deploy independente de provedor
  (Railway, Render, Clever Cloud, VM com Docker, etc).

### Fase 4 — Paridade completa e descontinuação do sistema desktop
- Portados `Cliente` e `Pedido` pro painel administrativo
  (`ClienteController`, `PedidoController` + telas), fechando a última
  lacuna de funcionalidade que ainda só existia no sistema desktop. A
  camada de repository/service já estava pronta desde a Fase 2 - faltava só
  o controller e as telas.
- Pedido no painel é só consulta e exclusão (sem edição) - decisão
  deliberada, diferente do desktop que permitia atualizar um pedido já
  feito.
- Exclusão de pedido restrita a `GERENTE`/`FINANCEIRO` (mesmo critério já
  usado pro Caixa), já que apagar uma venda registrada é sensível.
- Com o painel web cobrindo 100% do que o desktop fazia, o `PitoDePango`
  foi formalmente descontinuado - README do desktop atualizado com aviso
  apontando pra este projeto como sucessor. O repositório do desktop
  continua existindo como registro histórico do Projeto Integrador, mas não
  deve mais ser usado em produção.

## Próximos passos (fora do escopo atual)

- **Login de cliente com cadastro de benefícios**: vai entrar como um
  segundo realm de autenticação (`/minha-conta/**`), separado do login da
  equipe, sem afetar o que já existe em `/admin/**`.
- **Tabela de itens de pedido**: hoje cada linha do carrinho vira uma linha
  solta em `pedido` (mesma limitação herdada do sistema desktop) - uma
  tabela `pedido_item` agrupando várias linhas sob um único pedido é uma
  melhoria de modelagem futura.
- **Página `/produto`** (vitrine de produto único) continua estática/mock,
  não ligada ao catálogo real.

## Autor

Projeto desenvolvido por Thalles Fonseca para fins acadêmicos no Projeto
Integrador, com evolução posterior para sistema web integrado.
