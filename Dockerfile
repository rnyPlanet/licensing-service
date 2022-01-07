FROM openjdk:11-slim as build

ARG JAR_FILE

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

#docker run -it -d -p 8081:8081 ostock/licensing-service:0.3.0-SNAPSHOT