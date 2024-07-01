# ChatGod

ChatGod is a distributed chat application developed using Spring Boot, following the Model-View-Controller (MVC) architecture. This README provides an overview of the project structure and essential details.

## Project Structure

The project is organized into the following directories:

- **config**: Contains configuration files for the application.
- **controller**: Houses the controllers that handle HTTP requests and responses.
- **exception**: Includes custom exception classes for error handling.
- **model**: Contains the data models representing the application's entities.
- **repository**: Comprises the repositories for data access and persistence.
- **service**: Encompasses the service classes that contain the business logic.

Additionally, the root directory contains the main application file, `ChatGodApplication.java`, which serves as the entry point for the Spring Boot application.

## Key Components

### Config
This directory holds configuration-related files, such as application properties and security configurations.

### Controller
The controller classes manage the incoming HTTP requests, map them to the appropriate service methods, and return the responses to the clients.

### Exception
Custom exception classes defined here handle various error scenarios, ensuring proper error messages and statuses are returned to the clients.

### Model
The model classes represent the data structures used within the application, typically corresponding to database entities.

### Repository
Repositories interface with the database, providing methods for CRUD operations on the model entities.

### Service
Service classes contain the core business logic of the application. They interact with the repositories to retrieve and manipulate data, and are called by the controllers to process client requests.

## Getting Started

To run the ChatGod application locally, follow these steps:

1. **Clone the repository**:
   ```sh
   git clone <repository-url>
   cd ChatGod
   ```
 2. **Execute Maven clean install**
    ```sh
    ./mvnw clean install
    ```
 3.  **Run the application like this**
    ```sh
./mvnw spring-boot:run
```
