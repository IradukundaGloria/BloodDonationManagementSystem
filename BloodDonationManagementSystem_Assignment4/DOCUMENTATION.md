# Blood Donation Management System

## 1. Project Title

Blood Donation Management System – Assignment 4

## 2. Introduction

This project is a Spring Boot REST application that manages donor, recipient, and blood donation records. It was built as a university Assignment 4 practical. The system exposes CRUD APIs that can be tested with Postman and stores data in an H2 database.

## 3. Problem Overview

Blood donation centres need a simple way to keep donor details, recipient needs, and donation records. Without a structured system, it is difficult to validate personal data, confirm that a donation belongs to a real donor, or retrieve records quickly.

## 4. Project Scope

The scope of this assignment is limited to:

- Three entities: Donor, Recipient, and BloodDonation
- Full CRUD operations for each entity
- REST API testing with Postman
- H2 database persistence
- Validation and error handling
- Source code on GitHub
- A short video demonstration

The project does not include login, a frontend, payment features, or cloud deployment.

## 5. Selected Entities

1. Donor
2. Recipient
3. BloodDonation

## 6. Entity Attributes

### Donor

- donorId
- firstName
- lastName
- age
- gender
- bloodGroup
- phone
- email
- address
- registrationDate
- eligibilityStatus

### Recipient

- recipientId
- firstName
- lastName
- age
- gender
- bloodGroup
- phone
- email
- address
- medicalNeed
- registrationDate
- status

### BloodDonation

- donationId
- donor
- bloodGroup
- donationDate
- quantity
- donationStatus
- medicalApproval

## 7. Entity Relationship

One Donor can have many BloodDonation records.

- Donor: `@OneToMany(mappedBy = "donor")`
- BloodDonation: `@ManyToOne` with `@JoinColumn(name = "donor_id")`

JSON recursion is prevented with Jackson `@JsonIgnoreProperties`. Donor responses include donations without nesting the donor again. Donation responses include the donor without nesting the donor’s donation list.

## 8. Business Requirements

1. Donor information must be valid.
2. Recipient information must be valid.
3. Blood group must be provided and must be a standard group (A+, A-, B+, B-, AB+, AB-, O+, O-).
4. Email must be valid.
5. Required personal information cannot be blank.
6. Donor age must be between 18 and 65.
7. Recipient age must be between 1 and 120.
8. Donation quantity must be positive.
9. Donation date must be provided.
10. Donation status must be provided.
11. Medical approval must be provided.
12. A BloodDonation must reference an existing Donor.

## 9. Technologies Used

- Java 17
- Spring Boot 3.3.5
- Spring Web
- Spring Data JPA
- Jakarta Bean Validation
- H2 Database
- Maven
- Postman

## 10. Spring Boot Architecture

The application uses a layered architecture:

Controller → Service → Repository → Database

Packages:

- `entity` – JPA entities
- `repository` – Spring Data JPA repositories
- `service` – business logic and existence checks
- `controller` – REST endpoints
- `exception` – `ResourceNotFoundException` and `GlobalExceptionHandler`

## 11. CRUD Implementation

Each entity has create, read all, read by ID, update, and delete operations in its service class. Controllers call the service layer only. If a requested ID does not exist, the service throws `ResourceNotFoundException`. Blood donation create and update operations load the referenced donor from the database and reject a donation that points to a missing donor.

## 12. REST API Endpoints

| Entity | Method | Endpoint | Success status |
|--------|--------|----------|----------------|
| Donor | POST | `/api/donors` | 201 Created |
| Donor | GET | `/api/donors` | 200 OK |
| Donor | GET | `/api/donors/{id}` | 200 OK |
| Donor | PUT | `/api/donors/{id}` | 200 OK |
| Donor | DELETE | `/api/donors/{id}` | 204 No Content |
| Recipient | POST | `/api/recipients` | 201 Created |
| Recipient | GET | `/api/recipients` | 200 OK |
| Recipient | GET | `/api/recipients/{id}` | 200 OK |
| Recipient | PUT | `/api/recipients/{id}` | 200 OK |
| Recipient | DELETE | `/api/recipients/{id}` | 204 No Content |
| Blood Donation | POST | `/api/donations` | 201 Created |
| Blood Donation | GET | `/api/donations` | 200 OK |
| Blood Donation | GET | `/api/donations/{id}` | 200 OK |
| Blood Donation | PUT | `/api/donations/{id}` | 200 OK |
| Blood Donation | DELETE | `/api/donations/{id}` | 204 No Content |

Invalid requests return 400 Bad Request. Missing resources return 404 Not Found.

## 13. Validation Rules

Jakarta validation is applied on entity fields. Controllers use `@Valid` on request bodies.

- Names, gender, blood group, phone, email, address, eligibility status, medical need, donation status, and recipient status cannot be blank.
- Email must use a valid format.
- Donor age: 18–65.
- Recipient age: 1–120.
- Donation quantity must be `@Positive`.
- Donation date and medical approval are required.

## 14. Error Handling

`GlobalExceptionHandler` returns JSON without Java stack traces.

Not found example:

```json
{
  "status": 404,
  "message": "Donor not found with id: 1"
}
```

Validation example:

```json
{
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "email": "Email must be valid"
  }
}
```

## 15. Database

H2 file-based database:

- URL: `jdbc:h2:file:./data/blooddonationdb`
- Username: `sa`
- Password: empty
- `spring.jpa.hibernate.ddl-auto=update`
- Console: `http://localhost:8080/h2-console`

## 16. Postman Testing

Import `Blood_Donation_Assignment_4_Postman_Collection.json` and test every CRUD operation. Also test invalid email, missing required fields, invalid age, zero/negative quantity, a nonexistent donor ID, and a nonexistent resource ID.

## 17. Example Requests

Create Donor:

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

Create Recipient:

```json
{
  "firstName": "Jane",
  "lastName": "Doe",
  "age": 30,
  "gender": "Female",
  "bloodGroup": "A+",
  "phone": "0790000000",
  "email": "jane.doe@example.com",
  "address": "Kigali",
  "medicalNeed": "Blood transfusion",
  "registrationDate": "2026-09-19",
  "status": "Pending"
}
```

Create Blood Donation:

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

## 18. GitHub Repository

[INSERT PUBLIC GITHUB LINK HERE]

## 19. Video Demonstration

[INSERT VIDEO LINK HERE]

## 20. Conclusion

The project provides a simple, working REST API for three blood donation entities with validation, relationship handling, H2 persistence, and Postman-ready endpoints. After adding the GitHub and video links, the documentation is ready for assignment submission.
