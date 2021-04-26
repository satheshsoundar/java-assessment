#FROM openjdk:8
#ADD target/*.jar customer-service.jar
#EXPOSE 8082
#ENTRYPOINT ["java","-jar","customer-service.jar"]



FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} customer-service.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/customer-service.jar"]