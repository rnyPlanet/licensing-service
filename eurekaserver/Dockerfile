FROM openjdk:11-slim as build

ARG JAR_FILE=/target/*SNAPSHOT.jar

COPY ${JAR_FILE} eurekaserver.jar

ENTRYPOINT ["java", "-jar", "/eurekaserver.jar"]