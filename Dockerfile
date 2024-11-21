# Etapa 1: Build (Construção do Aplicativo)
# Usamos uma imagem base do Maven com o JDK 17 para construir o projeto.
# Essa etapa é isolada para garantir que a imagem final seja o mais leve possível.
FROM maven:3.9.4-eclipse-temurin-17 AS builder

# Define o diretório de trabalho dentro do container onde os comandos serão executados.
WORKDIR /app

# Copia o arquivo pom.xml e a pasta .mvn para o container.
# Isso permite baixar as dependências Maven antes de copiar o restante do código.
COPY pom.xml ./
COPY .mvn/ .mvn/
COPY mvnw ./

# Baixa todas as dependências do Maven especificadas no pom.xml.
# Esse passo é feito separadamente para usar o cache do Docker e evitar downloads desnecessários em builds futuros.
RUN ./mvnw dependency:resolve

# Copia o código-fonte completo para o container.
COPY src ./src

# Compila e empacota o projeto, gerando um arquivo JAR no diretório target.
# O parâmetro `-DskipTests` é usado para ignorar os testes durante o build.
RUN ./mvnw package -DskipTests

# Etapa 2: Runtime (Execução do Aplicativo)
# Usamos uma imagem base leve com JRE 17 (Java Runtime Environment) para rodar a aplicação.
# Essa etapa garante que a imagem final seja pequena e otimizada para produção.
FROM eclipse-temurin:17-jre-alpine

# Cria um usuário não-root para rodar o aplicativo, aumentando a segurança.
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Define o diretório de trabalho dentro do container onde o aplicativo será executado.
WORKDIR /app

# Copia apenas o arquivo JAR gerado na etapa de build para a imagem final.
COPY --from=builder /app/target/*.jar app.jar

# Altera a propriedade dos arquivos no diretório de trabalho para o usuário não-root criado.
RUN chown -R appuser:appgroup /app

# Alterna para o usuário não-root para executar o aplicativo.
USER appuser

# Expõe a porta 8080 do container, que é a porta padrão do Spring Boot.
EXPOSE 8080

# Define o comando de entrada para rodar o aplicativo usando o arquivo JAR gerado.
ENTRYPOINT ["java", "-jar", "app.jar"]
