# SmartPark API

## Overview

"SmartPark" is a technology company developing an intelligent parking management system for
urban areas. The company is aiming to optimize the use of parking spaces and facilitate easy
navigation for drivers.

## Technologies Used

- **Java 17**
- **Spring Boot 3.4.3**
- **Spring Data JPA**
- **Spring Web**
- **H2 Database (for testing)**
- **Mockito & JUnit 5** (for unit testing)
- **Postman** (for API testing)

### Prerequisites

- Java 17
- Maven

### Run the Application

```sh
mvn spring-boot:run
```

## API Endpoints

- 📌 [Parking Lot](http://localhost:8080/parking-lots)

- 📌 [Vehicle](http://localhost:8080/vehicles)

### Parking Lot APIs

| Method | Endpoint                    | Description                                             |
|--------|-----------------------------|---------------------------------------------------------|
| `POST` | `/parking-lots`             | Register a new parking lot                              |
| `GET`  | `/parking-lots/{lotId}`     | Get current occupancy and availability of a parking lot |
| `GET`  | `/parking-lots/lot/{lotId}` | Get all vehicles currently parked in a lot              |

### Vehicle APIs

| Method | Endpoint                                    | Description            |
|--------|---------------------------------------------|------------------------|
| `POST` | `/vehicles`                                 | Register a new vehicle |
| `POST` | `/vehicles/{lotId}/check-in/{licensePlate}` | Check-in a vehicle     |
| `POST` | `/vehicles/check-out/{licensePlate}`        | Check-out a vehicle    |

## H2 Database Configuration

This project uses **H2 Database** for development and testing. It runs in-memory by default.

### H2 Console Access

After running the application, access the **H2 console** at:

- 📌 [H2 Console](http://localhost:8080/h2-console)

Use the following credentials:

- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Username:** `sa`
- **Password:** *(leave empty)*

## Running Unit Tests

To execute tests using JUnit & Mockito:

```sh
mvn test
```

To run a specific test class:

```sh
mvn -Dtest=VehicleServiceTest test
```

To run a specific test method:

```sh
mvn -Dtest=VehicleServiceTest#registerVehicle_Success test
```

## Postman Collection

A Postman collection is available for testing.Import the`REST API- Smart Park.postman_collection.json` file into
Postman.
