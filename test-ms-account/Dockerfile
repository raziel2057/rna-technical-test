# FROM maven:3.9.8-eclipse-temurin-22-alpine # for Java 22
FROM maven:3.9.8-eclipse-temurin-22-alpine

WORKDIR /test-ms-account
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run