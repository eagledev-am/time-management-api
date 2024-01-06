FROM maven:3.8.5-openjdk-17
ADD target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
