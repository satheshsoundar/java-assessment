FROM openjdk:8
ADD target/*.jar product-service.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","product-service.jar"]