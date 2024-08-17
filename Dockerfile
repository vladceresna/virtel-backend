FROM openjdk:17-jdk-alpine
CMD ["./gradlew", "clean", "bootJar"]
COPY build/libs/*T.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]