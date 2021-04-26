#FROM openjdk:8
#ADD target/*.jar cart-service.jar
#EXPOSE 8081
#ENTRYPOINT ["java","-jar","cart-service.jar"]

FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cart-service.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/cart-service.jar"]