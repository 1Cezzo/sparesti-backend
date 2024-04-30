# Use a base image with Java and Maven installed
FROM maven:3-eclipse-temurin-21-jammy AS build

# Set the working directory
WORKDIR /app

ARG password
ENV KEYSTORE_PASSWORD=${password}
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

# Install mkcert and openssl
RUN apt-get update && apt-get install -y openssl && apt-get clean
RUN apt install -y libnss3-tools && apt clean
RUN curl -JLO "https://dl.filippo.io/mkcert/latest?for=linux/amd64"
RUN chmod +x mkcert-v*-linux-amd64
RUN cp mkcert-v*-linux-amd64 /usr/local/bin/mkcert
RUN mkcert -install

WORKDIR /app/src/main/resources
RUN rm -rf keystore
WORKDIR /app/src/main/resources/keystore
#Create certificate and key files
RUN mkcert -key-file key.pem -cert-file cert.pem localhost 127.0.0.1 ::1

# Create a keystore. 
RUN openssl pkcs12 -export -out keystore.p12 -inkey key.pem -in cert.pem -passout env:KEYSTORE_PASSWORD

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
EXPOSE 8443
# Run the application
CMD ["java", "-jar", "app.jar"]