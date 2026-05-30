# Stage 1: Build the application
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copy Maven wrapper and pom.xml first (for better Docker layer caching)
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Make the Maven wrapper executable
RUN chmod +x mvnw

# Download dependencies (cached layer if pom.xml hasn't changed)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src/ src/

# Build the application (skip tests for faster builds)
RUN ./mvnw clean package -DskipTests -B

# Stage 2: Run the application
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port (Render sets PORT env var)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
