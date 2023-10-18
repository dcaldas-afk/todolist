FROM        ubuntu:latest AS build
RUN         apt update
RUN         apt intstall -y openjdk-17-jddk
COPY        . .
RUN         apt intall -y maven
RUN         mvn clean intstall
FROM        openjdk:17-jdk-slim
EXPOSE      8080
COPY        --from=build /target/todolist-1.0.jar app.jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]