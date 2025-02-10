# Usa a imagem oficial do Java 17
FROM eclipse-temurin:17-jdk AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto
COPY . .

# Compila o projeto com Maven
RUN ./mvnw clean package -DskipTests

# Usa outra imagem para rodar apenas o JAR (reduz tamanho do container)
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copia o JAR gerado para dentro do container final
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080
EXPOSE 8080

# Comando para rodar a aplicaçã
CMD ["java", "-jar", "app.jar"]
