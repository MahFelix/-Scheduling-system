FROM ubuntu:latest AS build

# Atualiza o repositório e instala o OpenJDK e outras dependências necessárias
RUN apt-get update && apt-get install -y openjdk-17-jdk curl wget gnupg2

# Adiciona o repositório do Maven
RUN curl -fsSL https://apache.osuosl.org/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.tar.gz -o /tmp/maven.tar.gz \
    && tar -xvzf /tmp/maven.tar.gz -C /opt \
    && rm /tmp/maven.tar.gz


# Configura o Maven
ENV MAVEN_HOME /opt/apache-maven-3.8.4
ENV PATH $MAVEN_HOME/bin:$PATH

COPY . .

# Limpeza e construção do projeto
RUN mvn clean install
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-slim

EXPOSE 8090

COPY --from=build /target/agendamento-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]
