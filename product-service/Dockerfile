#FROM openjdk:8
#ADD target/*.jar product-service.jar
#EXPOSE 8083
#ENTRYPOINT ["java","-jar","product-service.jar"]


FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} product-service.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","/product-service.jar"]