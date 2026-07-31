# Deploy - Empório Pito de Pango

## Rodando localmente com Docker (mais próximo de produção)

Sobe o app + um MySQL já com o `schema.sql` aplicado automaticamente:

```bash
docker compose up --build
```

Acesse http://localhost:8080. Na primeira subida, o `AdminSeeder` cria o
usuário `admin` / `troque123` — troque assim que entrar.

Pra derrubar tudo (mantendo os dados do banco no volume):

```bash
docker compose down
```

Pra apagar também os dados do banco:

```bash
docker compose down -v
```

## Rodando só o jar (sem Docker)

```bash
mvn clean package -DskipTests
java -jar target/app.jar
```

Isso usa os valores padrão de `application.properties`
(`jdbc:mysql://localhost:3306/coffeeshop_db`, usuário `root`, sem senha) -
ajuste as variáveis de ambiente `DATABASE_URL`/`DATABASE_USERNAME`/
`DATABASE_PASSWORD` se o seu MySQL local for diferente.

## Deploy em produção

O app já está pronto pra qualquer provedor que aceite uma imagem Docker (ou
um jar Java) + um banco MySQL gerenciado - por exemplo Railway, Render,
Clever Cloud, ou uma VM com Docker instalado. Nenhum código muda entre
provedores, só a forma de configurar as variáveis de ambiente.

**Passo a passo, independente do provedor escolhido:**

1. Suba um banco MySQL gerenciado (o próprio provedor costuma oferecer um
   "MySQL addon"/"managed database"). Rode o `src/main/resources/schema.sql`
   nele uma vez (via linha de comando `mysql -u ... -p ... < schema.sql`
   ou o cliente que o provedor disponibilizar).
2. Configure as variáveis de ambiente do app (veja `.env.example`):
   `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD`.
3. Aponte o provedor pra construir a partir do `Dockerfile` deste repositório
   (a maioria detecta automaticamente) ou rode o jar com `java -jar app.jar`
   se o provedor preferir buildpacks em vez de Docker.
4. Exponha a porta 8080 (ou a que a variável `PORT` estiver usando).

Como as instruções específicas de cada provedor mudam com frequência, vale a
pena checar a documentação atual deles na hora de configurar — o que não
muda é o que o app espera receber (passos 1 e 2 acima).

## Checklist antes de ir pra produção

- [ ] Trocar a senha do usuário `admin` criado pelo `AdminSeeder` (ou criar
      um `GERENTE` novo e excluir o `admin`).
- [ ] Confirmar que `DATABASE_PASSWORD` em produção é uma senha forte (o
      padrão local é vazio de propósito, só pra facilitar o dev).
- [ ] HTTPS: normalmente é o próprio provedor de deploy que termina TLS na
      borda (Railway/Render fazem isso automaticamente) - não precisa
      configurar nada na aplicação para isso.
