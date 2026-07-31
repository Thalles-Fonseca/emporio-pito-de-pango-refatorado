# ---- Etapa 1: build ----------------------------------------------------
# Compila o projeto com Maven. Isolado numa imagem separada pra imagem
# final nao carregar o JDK completo nem o cache do Maven.
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copia so o pom.xml primeiro para o Docker conseguir cachear as
# dependencias baixadas em builds seguintes (só re-baixa se o pom mudar).
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# ---- Etapa 2: imagem final ----------------------------------------------
# So o JRE (nao o JDK) + o jar ja pronto. Imagem final bem menor.
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Roda como usuario nao-root (boa pratica de seguranca em produção)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

COPY --from=build /app/target/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
