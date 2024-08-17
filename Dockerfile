ARG BUILD_HOME=/app

FROM gradle:7.3.1-jdk17-alpine as build-image
ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME
WORKDIR $APP_HOME
COPY --chown=gradle:gradle build.gradle settings.gradle $APP_HOME/
COPY --chown=gradle:gradle src $APP_HOME/src
COPY --chown=gradle:gradle gradle $APP_HOME/gradle
RUN gradle --no-daemon build
FROM openjdk:17-jdk-alpine
ARG BUILD_HOME
ENV APP_HOME=$BUILD_HOME
COPY --from=build-image $APP_HOME/build/libs/*T.jar app.jar
ENTRYPOINT java -jar app.jar