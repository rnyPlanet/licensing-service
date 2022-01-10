FROM openjdk:11-slim as build

ARG JAR_FILE=/target/*SNAPSHOT.jar

COPY ${JAR_FILE} configserver.jar

ENTRYPOINT ["java", "-jar", "/configserver.jar"]

#docker run -it -d -p 8071:8071 ostock/configserver:0.0.1-SNAPSHOT