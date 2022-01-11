FROM openjdk:11-slim as build

ARG JAR_FILE=/target/*SNAPSHOT.jar

COPY ${JAR_FILE} organization-service.jar

ENTRYPOINT ["java", "-jar", "/organization-service.jar"]