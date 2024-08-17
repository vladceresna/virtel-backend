# Stage 1: Build the application
FROM gradle:7.6.0-jdk17 AS builder

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper files and the build.gradle file
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copy the source code
COPY src src

# Ensure gradle wrapper is executable
RUN chmod +x ./gradlew

# Build the application (includes download of dependencies)
RUN ./gradlew clean build --no-daemon

# Stage 2: Run the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port on which the app runs
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]