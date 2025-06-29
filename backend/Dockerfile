FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copia o JAR do host para dentro do container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
