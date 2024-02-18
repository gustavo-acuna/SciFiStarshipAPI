FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean compile package -DskipTests
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build  /app/target/scifistarship-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","scifistarship-0.0.1-SNAPSHOT.jar"]