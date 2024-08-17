FROM gradle:7.3.1-jdk17-alpine AS build
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY gradlew ./gradle
COPY src ./src
RUN gradle build

FROM openjdk:17-jdk-alpine
WORKDIR /app
RUN ls
COPY --from=build /app/target/*T.jar ./app.jar
RUN ls
ENTRYPOINT ["java", "-jar", "app.jar"]