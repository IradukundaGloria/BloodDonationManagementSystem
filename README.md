# Blood Donation Management System

A university-level Java EE 8 web application built with **JSF 2.3**, **JPA 2.2 / Hibernate 5.4**, and **Apache Tomcat 9**, providing a computerized platform for managing blood donors, recipients, donations, requests, and blood inventory.

![Java EE](https://img.shields.io/badge/JavaEE-8-red.svg)
![JSF](https://img.shields.io/badge/JSF-2.3-orange.svg)
![Hibernate](https://img.shields.io/badge/Hibernate-5.4-blue.svg)
![H2 Database](https://img.shields.io/badge/Database-H2-green.svg)

---

## 📌 Project Overview

The **Blood Donation Management System** streamlines blood bank administration by automating donor registration, recipient needs tracking, and blood stock monitoring. It replaces error-prone manual logbooks with a validated, multi-tiered enterprise web solution.

### Key Highlights
- **Full Working CRUD**: Complete Create, Read, Update, and Delete operations for both **Donor** and **Recipient** entities.
- **3-Layer Validation Architecture**:
  1. **Presentation / UI Layer**: JSF validators (`required`, `validateLength`, `validateLongRange`, `validateRegex`).
  2. **ManagedBean / Business Layer**: Java service validation (donor eligibility 18–75, duplicate email prevention, blood group verification).
  3. **Database / JPA Layer**: JPA entity constraints (`@NotNull`, `@Size`, `@Email`, `@Min`, `@Max`, `@Column(unique=true)`).
- **3 CSS Implementation Styles**: External CSS (`style.css`), Internal CSS (`<style>`), and Inline CSS (`style="..."`).
- **7 Core Entities**: `Donor`, `Recipient`, `BloodDonation`, `BloodRequest`, `BloodInventory`, `Doctor`, `Admin`.

---

## 🛠️ Technology Stack

- **Java**: Java 8 (target 1.8)
- **Framework**: Java EE 8 (`javax.*` API namespace)
- **Web Layer**: JSF 2.3 (JavaServer Faces)
- **Persistence**: JPA 2.2 / Hibernate 5.4.33.Final
- **Database**: H2 Engine (Embedded / File-based)
- **Server**: Apache Tomcat 9
- **Build Tool**: Apache Maven 3.x
- **Diagrams**: PlantUML (`blood_donation_class_diagram.puml`)

---

## 🚀 How to Build & Run

### Prerequisites
- JDK 8 or higher
- Apache Maven 3.x

### 1. Build the WAR Artifact
Open a terminal in the project directory and run:
```bash
mvn clean package
```
This generates `target/BloodDonationManagementSystem.war`.

### 2. Run Locally with Embedded Tomcat 9 (Cargo)
You can launch the web application immediately using the Maven Cargo plugin:
```bash
mvn cargo:run
```

### 3. Open in Browser
Once Tomcat starts, navigate to:
```
http://localhost:8080/BloodDonationManagementSystem/index.xhtml
```

---

## 📁 Project Structure

```
BloodDonationManagementSystem/
├── pom.xml                               # Maven project configuration
├── README.md                             # Project GitHub README
├── DOCUMENTATION.md                      # Comprehensive 20-section report
├── blood_donation_class_diagram.puml     # PlantUML Class Diagram
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── blooddonation/
        │           ├── entity/           # 7 Core JPA Entities
        │           ├── dao/              # Generic & Specific Data Access Objects
        │           ├── service/          # Layer 2 Business Validation Services
        │           ├── managedbean/      # JSF Managed Beans
        │           └── util/             # JPAUtil EntityManager Factory
        ├── resources/
        │   └── META-INF/
        │       └── persistence.xml       # JPA Persistence Unit Configuration
        └── webapp/
            ├── WEB-INF/
            │   ├── web.xml               # Servlet & Faces Mapping
            │   └── faces-config.xml      # JSF Config
            ├── css/
            │   └── style.css             # External CSS Stylesheet
            ├── index.xhtml               # Dashboard (Internal CSS)
            ├── donor-list.xhtml          # Donor Directory (Inline CSS)
            ├── donor-form.xhtml          # Donor Form (Layer 1 UI Validation)
            ├── recipient-list.xhtml       # Recipient Directory (Inline CSS)
            └── recipient-form.xhtml      # Recipient Form
```

---

## 🔗 Project Links & Placeholders

- **Public GitHub Repository**: `[INSERT PUBLIC GITHUB LINK HERE]`
- **Video Walkthrough & Explanation**: `[INSERT VIDEO LINK HERE]`

---

## 👤 Author

* Student / Author Placeholder
* Course: Java EE Enterprise Application Development
