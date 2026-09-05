# UNIVERSITY ASSIGNMENT REPORT
## Blood Donation Management System (Assignment 3)

---

### SECTION 1: COVER PAGE

* **Course / Unit**: Enterprise Web Application Development (Java EE)
* **Assignment Title**: Assignment 3 — Blood Donation Management System
* **Technology Stack**: Java 8, Maven, Java EE 8 (javax.*), JSF 2.3, JPA 2.2 / Hibernate 5.4, Tomcat 9, XHTML, CSS
* **Date of Submission**: September 2026
* **GitHub Repository Placeholder**: `[INSERT PUBLIC GITHUB LINK HERE]`
* **Video Explanation Placeholder**: `[INSERT VIDEO LINK HERE]`

---

### SECTION 2: ABSTRACT

The Blood Donation Management System (BDMS) is a computerized enterprise web application built to streamline and automate the management of blood donors, recipients, blood donations, requests, and inventory levels across healthcare facilities. Legacy blood management processes heavily depend on manual logbooks and fragmented spreadsheets, resulting in data inaccuracies, delayed response times during urgent medical needs, and potential miscommunication regarding blood stock levels. 

This project provides a robust, web-based platform developed using Java EE 8, JSF 2.3, and JPA/Hibernate. The system implements complete Create, Read, Update, and Delete (CRUD) operations for key entities (Donors, Recipients, and Blood Inventory) while enforcing a strict three-tiered validation model across the Presentation/UI layer, ManagedBean/Business layer, and Database/JPA layer. It features Doctor/Admin authentication (`DOC-1001` format) for protected inventory management and comprehensive styling through External, Internal, and Inline CSS.

---

### SECTION 3: PROBLEM STATEMENT

Current manual or semi-automated blood donation recordkeeping exhibits significant operational inefficiencies:
1. **Manual Record Errors**: Paper logs and disparate spreadsheets frequently suffer from duplicate entries, illegible handwriting, or missing medical eligibility data.
2. **Inaccurate Inventory Visibility**: Real-time visibility into available blood bags categorized by blood type (A+, A-, B+, B-, AB+, AB-, O+, O-) is lacking, causing delays during emergency surgeries.
3. **Weak Validation & Integrity**: Lack of automated business rule enforcement leads to invalid donor registrations (e.g. donors under 18 years of age) or duplicate registration emails.
4. **Unprotected Inventory Access**: Lack of role-based staff authentication allows unauthorized modification of sensitive blood stock records.

---

### SECTION 4: PROJECT SCOPE

#### In-Scope:
* Modeling of 7 core system entities: `Donor`, `Recipient`, `BloodDonation`, `BloodRequest`, `BloodInventory`, `Doctor`, `Admin`.
* Complete end-to-end CRUD management for `Donor`, `Recipient`, and `BloodInventory` entities.
* Staff Authentication Portal (`staff-login.xhtml`) for Doctors (`DOC-1001` format) and Admins (`ADM-1001` format).
* Implementation of 3 validation layers (JSF UI validation, Java Business logic validation, JPA/Bean validation).
* Styling via External CSS (`style.css`), Internal CSS (`<style>`), and Inline CSS (`style="..."`).
* Persistent object relational mapping using JPA 2.2 and Hibernate with H2 Database.
* Interactive web dashboard displaying live inventory status and summary analytics.

---

### SECTION 5: AS-IS MODEL (MANUAL WORKFLOW)

```
[Donor Arrives] ──> [Manual Paper Registration Form] ──> [Filing Cabinet Storage]
                                                                │
[Recipient Request] ──> [Telephone Inquiry to Lab] ──> [Manual Search in Logbook]
                                                                │
[Donation Recorded] ──> [Handwritten Label on Bag] ──> [Unsynchronized Spreadsheet]
```

---

### SECTION 6: TO-BE MODEL (COMPUTERIZED WORKFLOW)

```
       [ Staff / Doctor Logs In via User ID (DOC-1001) ]
                        │
       [ 3-Layer Automated Validation Checks ]
       (UI Regex & Range ──> Bean Logic ──> JPA Constraints)
                        │
       [ Protected CRUD Executed on Donors, Recipients & Inventory ]
                        │
       [ Data Saved to H2 Database via JPA / Hibernate ]
                        │
       [ Real-Time Dashboard Updates Stock & Low-Volume Alerts ]
```

---

### SECTION 7: BUSINESS REQUIREMENTS

* **BR-01**: The system shall allow authorized staff to register new blood donors.
* **BR-02**: The system shall validate donor input data at UI, business, and database levels.
* **BR-03**: The system shall allow authorized staff to view all registered donors in a tabular format.
* **BR-04**: The system shall allow authorized staff to edit existing donor records.
* **BR-05**: The system shall allow authorized staff to delete donor records.
* **BR-06**: The system shall allow authorized staff to register new blood recipients.
* **BR-07**: The system shall allow authorized staff to view all recipient records.
* **BR-08**: The system shall allow authorized staff to update recipient records.
* **BR-09**: The system shall allow authorized staff to delete recipient records.
* **BR-10**: The system shall record blood donation events linked to donors.
* **BR-11**: The system shall associate blood donations with medical approval statuses.
* **BR-12**: The system shall record recipient blood requests.
* **BR-13**: The system shall require Doctor/Admin authentication (`DOC-1001` format) for modifying inventory stock.
* **BR-14**: The system shall maintain real-time blood stock inventory per blood group with low-stock alerts.
* **BR-15**: The system shall enforce data uniqueness (e.g. unique email addresses, doctor codes).
* **BR-16**: The system shall provide responsive UI navigation across all management modules.

---

### SECTION 8: APPLICABLE SOFTWARE QUALITY ATTRIBUTES

1. **Usability**: Clean, modern web interface with clear forms, input hints, breadcrumbs, and instant validation messages.
2. **Reliability**: Transactional safety handled by JPA `EntityTransaction` to prevent partial data writes.
3. **Performance**: Efficient JPQL queries with low memory footprint and sub-second page rendering on Tomcat 9.
4. **Security**: Session-based staff authentication and User ID format validation (`^(DOC|ADM|DOR)-[0-9]{4}$`).
5. **Maintainability**: Clear architectural separation into Entity, DAO, Service, and ManagedBean packages.
6. **Scalability**: Standard Java EE structure capable of deploying to enterprise application servers.
7. **Availability**: Non-blocking database connections managed by thread-safe `EntityManagerFactory`.
8. **Data Integrity**: Enforced via relational database constraints (`NOT NULL`, `UNIQUE`, `@Min`, `@Max`).
9. **Testability**: Decoupled DAO and Service layers allowing straightforward unit and integration testing.
10. **Portability**: Standard Maven build runnable on any OS with Java 8+ and Tomcat 9.

---

### SECTION 9: INITIAL CLASS DIAGRAM

The class diagram source is available in PlantUML format at [`blood_donation_class_diagram.puml`](file:///c:/Users/tgogo/OneDrive/Desktop/BloodDonationManagementSystem/blood_donation_class_diagram.puml).

---

### SECTION 10: ENTITY DESCRIPTION

1. **Donor**: Represents a voluntary blood donor. Attributes: `donorId`, `firstName`, `lastName`, `age`, `gender`, `bloodGroup`, `phone`, `email`, `address`, `registrationDate`, `eligibilityStatus`.
2. **Recipient**: Represents a patient requiring blood. Attributes: `recipientId`, `firstName`, `lastName`, `age`, `gender`, `bloodGroup`, `phone`, `email`, `address`, `medicalNeed`, `registrationDate`, `status`.
3. **BloodDonation**: Represents a donation event. Attributes: `donationId`, `donor` (@ManyToOne), `bloodGroup`, `donationDate`, `quantity`, `donationStatus`, `medicalApproval`.
4. **BloodRequest**: Represents a recipient's request. Attributes: `requestId`, `recipient` (@ManyToOne), `bloodGroup`, `quantityRequested`, `requestDate`, `priority`, `requestStatus`.
5. **BloodInventory**: Resource entity representing stock levels per blood group. Attributes: `inventoryId`, `bloodGroup`, `quantityAvailable`, `lastUpdated`, `storageStatus`. (NOT modeled as a person).
6. **Doctor**: Medical staff reviewing donations/requests. Attributes: `doctorId`, `doctorCode` (`DOC-1001`), `firstName`, `lastName`, `specialization`, `phone`, `email`, `licenseNumber`.
7. **Admin**: System administrator managing records. Attributes: `adminId`, `adminCode` (`ADM-1001`), `firstName`, `lastName`, `username`, `email`, `role`.

---

### SECTION 11: VALIDATION TECHNIQUES (3 LAYERS)

#### Layer 1: Presentation / UI Validation (JSF)
* `required="true"` with custom `requiredMessage`.
* `<f:validateLength minimum="2" maximum="50">` on names.
* `<f:validateLongRange minimum="18" maximum="75">` on donor age.
* `<f:validateRegex pattern="^(DOC|ADM|DOR)-[0-9]{4}$">` on Staff User ID.

#### Layer 2: ManagedBean / Business Validation (Java Logic)
* `DonorService.validateDonor()` checks age is between 18 and 75.
* Email uniqueness check querying database (`donorDAO.isEmailExists()`).
* `AuthBean.login()` validates staff credentials and format.

#### Layer 3: Database / JPA Validation (Hibernate Annotations)
* `@NotNull`, `@Size`, `@Email`, `@Min`, `@Max`, `@Column(nullable = false, unique = true)`.

---

### SECTION 12: CSS IMPLEMENTATION (3 TYPES)

1. **External CSS**: `css/style.css` defining global variables, navbar, cards, forms, tables, and buttons.
2. **Internal CSS**: `<style>` block in `index.xhtml` customizing summary cards and dashboard grid layout.
3. **Inline CSS**: `style="..."` on notification banners (`<h:messages>`) and dataTable status badges in `donor-list.xhtml`, `recipient-list.xhtml`, and `inventory-list.xhtml`.

---

### SECTION 13: GITHUB LINK

`[INSERT PUBLIC GITHUB LINK HERE]`

---

### SECTION 14: VIDEO LINK

`[INSERT VIDEO LINK HERE]`

---

### SECTION 15: CONCLUSION

The Blood Donation Management System successfully fulfills all practical and theoretical requirements of Assignment 3. The project demonstrates standard Java EE 8 web architecture, clean JPA/Hibernate persistence, Doctor/Admin authentication (`DOC-1001` format), protected inventory CRUD operations, and clean UI styling.
