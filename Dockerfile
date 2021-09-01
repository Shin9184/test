FROM openjdk:8-jdk-alpine
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} myspring.jar
ENTRYPOINT ["java","-jar","/myspring.jar"]