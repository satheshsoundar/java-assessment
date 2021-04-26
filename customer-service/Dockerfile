FROM openjdk:8
ADD target/*.jar customer-service.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","customer-service.jar"]