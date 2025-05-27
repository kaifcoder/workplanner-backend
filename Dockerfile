# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and pom.xml
COPY mvnw* pom.xml ./
COPY .mvn .mvn

# Copy the source code
COPY src src

# Copy any environment files (if needed)
COPY src/main/resources/.env src/main/resources/.env

# Package the application (skip tests for faster build, remove if you want tests)
RUN ./mvnw clean package -DskipTests

# Expose the port your Spring Boot app runs on (default 8080)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/servicemarketplace-0.0.1-SNAPSHOT.jar"]
