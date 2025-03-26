# Use an official JDK 21 runtime as a base image
FROM eclipse-temurin:21-jdk AS builder

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml to cache dependencies
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./

# Install dependencies (will be cached if unchanged)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests=true

# Use a minimal runtime image for deployment
FROM eclipse-temurin:21-jre

# Set working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
