FROM gradle:7.3.1-jdk17-alpine AS build
WORKDIR /app
COPY . .
FROM openjdk:17-jdk-alpine
COPY --from=build /app/target/*.jar ./app.jar
ENTRYPOINT ["gradle","build","&&","java", "-jar", "app.jar"]