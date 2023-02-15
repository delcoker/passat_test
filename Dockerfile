# Build stage
FROM maven:3.8.1-openjdk-17 as build

# Set the working directory to the pos-be directory inside the container
WORKDIR pos-api

# Copy the submodules to the working directory
COPY . .

# Build the project using Maven with multiple threads
RUN mvn clean install

# Runtime stage
FROM openjdk:17.0.1-jdk-slim

# Copy the compiled application to the working directory
COPY --from=build pos-api/pos-application/target/*.jar app.jar
#COPY --from=build pos-api/pos-infrastructure/target/*.jar infra.jar

COPY .env .env

EXPOSE 8090

# Load environment variables from .env file
ENV $(cat .env | xargs)

# Start the Spring Maven project
CMD ["java", "-jar", "app.jar"]
#CMD ["java", "-jar", "infra.jar"]
