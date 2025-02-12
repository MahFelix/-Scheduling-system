FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -v
COPY . .

RUN apt-get install maven
RUN mvn clean install
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-slim

EXPOSE 8090

COPY --from=build /target/agendamento-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "Java", "-jar", "app.jar"]