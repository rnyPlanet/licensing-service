FROM openjdk:11-slim as build

ARG JAR_FILE

COPY ${JAR_FILE} licensing-service.jar

ENTRYPOINT ["java", "-jar", "/licensing-service.jar"]

#docker run -it -d -p 8081:8081 ostock/licensing-service:0.3.0-SNAPSHOT