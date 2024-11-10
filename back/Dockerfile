# Stage 1: Build the Spring Boot application
FROM openjdk:17-jdk-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and the wrapper scripts
COPY pom.xml ./

# Copy the Maven Wrapper scripts and .mvn directory
COPY mvnw ./
COPY .mvn .mvn

# Make sure the mvnw script is executable
RUN chmod +x mvnw

# Download the dependencies and go offline to speed up the build
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final image with the built jar file
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar /app/backend-0.0.1-SNAPSHOT.jar

# Expose the port that the app will run on
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]