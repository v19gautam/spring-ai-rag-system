FROM openjdk:17.0.1-jdk

WORKDIR /app

COPY target/vcorp-ai-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]