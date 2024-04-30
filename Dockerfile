# Use a base image with Java and Maven installed
FROM maven:3-eclipse-temurin-21-jammy AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml file
COPY pom.xml .

# Download the dependencies
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Set the application.properties to the docker.properties
WORKDIR /app/src/main/resources
RUN rm application.properties
RUN mv docker.properties application.properties

WORKDIR /app

# Build the application
RUN mvn spotless:apply
RUN mvn package -DskipTests

# Use a base image with Java installed
FROM eclipse-temurin:21
# Set the working directory
WORKDIR /app

# Copy the compiled JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port that the application listens on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]