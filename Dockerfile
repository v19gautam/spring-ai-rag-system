# Build stage
FROM openjdk:17.0.1-jdk AS builder
WORKDIR /app

# Copy Maven wrapper and POM configuration first (for layer caching)
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .

# Make Maven wrapper executable
RUN chmod +x mvnw

# Download dependencies offline to optimize build cache
#RUN ./mvnw dependency:go-offline -B

# Copy application source code
COPY src src

# Package the application (skipping tests for image build speed)
RUN ./mvnw package -DskipTests

# Runtime stage
FROM openjdk:17.0.1-jdk

WORKDIR /app

COPY data data
COPY --from=builder /app/target/vcorp-ai-backend-0.0.1-SNAPSHOT.jar app.jar


EXPOSE 8080

ENTRYPOINT ["java", "-XX:-UseContainerSupport", "-jar", "app.jar"]