# Blood Donation Management System - Assignment 4

Spring Boot REST API for a Blood Donation Management System. The project implements full CRUD operations for three entities: Donor, Recipient, and BloodDonation.

## Technologies

- Java 17
- Spring Boot 3.3.5
- Spring Web
- Spring Data JPA
- Jakarta Validation
- H2 Database
- Maven 3.9+

## Requirements

- Java 17
- Maven 3.9 or later

## Installation / Setup

1. Open the project folder `BloodDonationManagementSystem_Assignment4`.
2. Confirm Java 17 and Maven are installed:

```bash
java -version
mvn -version
```

## How to run

Compile:

```bash
mvn clean compile
```

Run tests:

```bash
mvn test
```

Start the application:

```bash
mvn spring-boot:run
```

The API is available at `http://localhost:8080`.

## REST endpoints

### Donor

| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/donors` | Create donor |
| GET | `/api/donors` | Get all donors |
| GET | `/api/donors/{id}` | Get donor by ID |
| PUT | `/api/donors/{id}` | Update donor |
| DELETE | `/api/donors/{id}` | Delete donor |

### Recipient

| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/recipients` | Create recipient |
| GET | `/api/recipients` | Get all recipients |
| GET | `/api/recipients/{id}` | Get recipient by ID |
| PUT | `/api/recipients/{id}` | Update recipient |
| DELETE | `/api/recipients/{id}` | Delete recipient |

### Blood Donation

| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/donations` | Create donation |
| GET | `/api/donations` | Get all donations |
| GET | `/api/donations/{id}` | Get donation by ID |
| PUT | `/api/donations/{id}` | Update donation |
| DELETE | `/api/donations/{id}` | Delete donation |

## Postman instructions

1. Start the application with `mvn spring-boot:run`.
2. Open Postman.
3. Import `Blood_Donation_Assignment_4_Postman_Collection.json`.
4. Run the requests in this order: create Donor, create Recipient, create Donation, then GET/PUT/DELETE.

Create Donor example:

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "age": 25,
  "gender": "Male",
  "bloodGroup": "O+",
  "phone": "0780000000",
  "email": "john.doe@example.com",
  "address": "Kigali",
  "registrationDate": "2026-09-19",
  "eligibilityStatus": "Eligible"
}
```

Create Blood Donation example (use an existing donor ID):

```json
{
  "donor": {
    "donorId": 1
  },
  "bloodGroup": "O+",
  "donationDate": "2026-09-19",
  "quantity": 450,
  "donationStatus": "Completed",
  "medicalApproval": true
}
```

## Database

The application uses a file-based H2 database:

- JDBC URL: `jdbc:h2:file:./data/blooddonationdb`
- Username: `sa`
- Password: (empty)
- H2 console: `http://localhost:8080/h2-console`

Hibernate DDL is set to `update`, so tables are created automatically.

## Project structure

```
src/main/java/com/blooddonation/
    entity/
    repository/
    service/
    controller/
    exception/
    BloodDonationManagementSystemAssignment4Application.java
src/main/resources/application.properties
src/test/java/com/blooddonation/
DOCUMENTATION.md
Blood_Donation_Assignment_4_Postman_Collection.json
```
