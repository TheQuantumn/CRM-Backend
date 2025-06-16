# Use OpenJDK 17 base image
FROM eclipse-temurin:21-jdk

# Set working directory in container
WORKDIR /app

# Copy project files into the container
COPY . .

RUN chmod +x ./mvnw

# Package the application (skip tests to speed up)
RUN ./mvnw clean package -DskipTests

# Run the Spring Boot app
CMD ["java", "-jar", "target/cmr-0.0.1-SNAPSHOT.jar"]
