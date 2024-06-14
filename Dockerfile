# Use a base image with JDK pre-installed
FROM openjdk:17-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file into the container at path /app
COPY target/ministore-0.0.1-SNAPSHOT.jar /app/ministore.jar

# Expose port 9090
EXPOSE 9090

# Specify the command to run your application
CMD ["java", "-jar", "ministore.jar"]