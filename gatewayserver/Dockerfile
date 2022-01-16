FROM openjdk:11-slim as build

ARG JAR_FILE=/target/*SNAPSHOT.jar

COPY ${JAR_FILE} gateway-server.jar

ENTRYPOINT ["java", "-jar", "/gateway-server.jar"]