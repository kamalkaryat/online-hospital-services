FROM openjdk-8
EXPOSE 8080
ADD target/online-hospital-service.jar online-hospital=service.jar
ENTRYPOINT ["java", "-jar", "/online-hospital-service.jar"]
