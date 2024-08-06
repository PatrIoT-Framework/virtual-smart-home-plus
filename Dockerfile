# Use an official Maven image as the base image
FROM maven:3-openjdk-17 AS build
ADD target/virtual-smart-home-plus.jar virtual-smart-home-plus.jar
ENTRYPOINT ["java", "-jar", "virtual-smart-home-plus.jar"]
EXPOSE 8081
