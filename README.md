# Virtual Smart Home Plus

## Description
Virtual Smart Home Plus is a Java-based project that uses Spring Boot and Maven. It aims to provide a virtual representation of a smart home, allowing users to interact with various home appliances and systems in a simulated environment.

## Installation

### Prerequisites
- Java 17 or higher
- Maven
- Docker

### Steps
1. Clone the repository
2. Navigate to the project directory
3. Run `mvn spring-boot:run` to start the application

## Create and Run the Docker Container
To create and run the Docker container, follow these steps:
1. mvn clean install
2. docker build -t virtual-smart-home-plus .
3. docker run -p local_port:docker_port virtual-smart-home-plus

## License
This project is licensed under the Apache License, Version 2.0.

## API Endpoints

The application provides the following RESTful endpoints:

### GET Endpoints
1. **GET /api/{apiVersion}/house/device/fireplace/{id}**: Returns the status of the fireplace device with the specified ID in the virtual house.
2. **GET /api/{apiVersion}/house/device/door/{id}**: Returns the status of the door device with the specified ID in the virtual house.
3. **GET /api/{apiVersion}/house/device/thermometer/{id}**: Returns the status of the thermometer device with the specified ID in the virtual house.
4. **GET /api/{apiVersion}/house/device/rgb/{id}**: Returns the status of the RGB light device with the specified ID in the virtual house.
5. **GET /api/{apiVersion}/house/device**: Returns a list of all devices in the virtual house.
6. **GET /api/{apiVersion}/house/device/{id}**: Returns the details of the device with the specified ID in the virtual house.
7. **GET /api/{apiVersion}/house**: Returns the details of the virtual house.
8. **GET /api/{apiVersion}/house/status**: Returns the status of the house.

### POST Endpoints
1. **POST /api/{apiVersion}/house/device/fireplace/{id}**: Creates or updates the fireplace device with the specified ID in the virtual house. The request body should include the new status.
2. **POST /api/{apiVersion}/house/device/door/{id}**: Creates or updates the door device with the specified ID in the virtual house. The request body should include the new status.
3. **POST /api/{apiVersion}/house/device/thermometer/{id}**: Creates or updates the thermometer device with the specified ID in the virtual house. The request body should include the new status.
4. **POST /api/{apiVersion}/house/device/rgb/{id}**: Creates or updates the RGB light device with the specified ID in the virtual house. The request body should include the new status.

### PUT Endpoints
1. **PUT /api/{apiVersion}/house/device/fireplace/{id}**: Updates the status of the fireplace device with the specified ID in the virtual house. The request body should include the new status.
2. **PUT /api/{apiVersion}/house/device/door/{id}**: Updates the status of the door device with the specified ID in the virtual house. The request body should include the new status.
3. **PUT /api/{apiVersion}/house/device/thermometer/{id}**: Updates the status of the thermometer device with the specified ID in the virtual house. The request body should include the new status.
4. **PUT /api/{apiVersion}/house/device/rgb/{id}**: Updates the status of the RGB light device with the specified ID in the virtual house. The request body should include the new status.

### DELETE Endpoints
1. **DELETE /api/{apiVersion}/house/device/fireplace/{id}**: Deletes the fireplace device with the specified ID in the virtual house.
2. **DELETE /api/{apiVersion}/house/device/door/{id}**: Deletes the door device with the specified ID in the virtual house.
3. **DELETE /api/{apiVersion}/house/device/thermometer/{id}**: Deletes the thermometer device with the specified ID in the virtual house.
4. **DELETE /api/{apiVersion}/house/device/rgb/{id}**: Deletes the RGB light device with the specified ID in the virtual house.

Please replace `{apiVersion}` with the actual API version you are using (e.g., `v0.1` or `v1.0`), and `{id}` with the actual ID of the resource you want to access.

Please note that all requests and responses are in JSON format.
