FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-alpine
WORKDIR /app
RUN ls
COPY --from=build /app/target/*.jar ./app.jar
RUN ls
ENTRYPOINT ["java", "-jar", "app.jar"]