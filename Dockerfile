FROM openjdk:8-jdk-alpine
ADD build/libs/hospedaje-0.0.1.jar producto_hospedaje.jar
EXPOSE 8182
ENTRYPOINT ["java", "-jar", "producto_hospedaje.jar"]