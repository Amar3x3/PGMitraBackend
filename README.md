# PG Mitra - Paying Guest Accommodation Management System (Backend)

A comprehensive backend solution for managing Paying Guest (PG) accommodations. Designed for property owners and tenants alike, this system ensures streamlined operations and a seamless living experience through robust user authentication, property and room management, financial tracking, announcements, dining schedules, and feedback mechanisms.

---

## 📚 Table of Contents
- [🚀 Features](#-features)
- [🛠️ Technologies Used](#-technologies-used)
- [🔧 Prerequisites](#-prerequisites)
- [⚙️ Getting Started](#️-getting-started)
  - [1️⃣ Clone the Repository](#1-clone-the-repository)
  - [2️⃣ Database Setup](#2-database-setup)
  - [3️⃣ Environment Configuration](#3-environment-configuration)
  - [4️⃣ Build and Run](#4-build-and-run)
- [📌 API Endpoints](#-api-endpoints)
- [🧩 Database Schema](#-database-schema)
- [🔐 Authentication & Authorization](#-authentication--authorization)
- [⏰ Scheduled Jobs](#-scheduled-jobs)
- [❌ Error Handling](#-error-handling)
- [📁 Project Structure](#-project-structure)
- [📄 .gitignore Overview](#-gitignore-overview)
- [📦 Maven Wrapper Info](#-maven-wrapper-info)
- [📝 pom.xml Summary](#-pomxml-summary)
- [✅ Testing](#-testing)
- [📚 Help & Resources](#-help--resources)

---

## 🚀 Features

### 👥 User Management
- Distinct login and registration for **Owners** and **Tenants**.
- Secure JWT-based authentication.
- Token refresh mechanism for session continuity.
- Tenant profile viewing and updates.

### 🏢 Property & Room Management
- Owners can register multiple PG properties.
- Add/manage rooms with rent and capacity.
- Assign or remove tenants from rooms.

### 📣 Communication
- Owners can post announcements for tenants.
- Tenants receive only relevant notifications.

### 🍽️ Dining Menu
- Owners manage daily menus.
- Tenants view menus by date.

### 💳 Financials
- Owners generate rent requests.
- Track rent status and view history.
- Automated rent reminder emails.

### 💬 Feedback & Support
- Tenants submit feedback or complaints.
- Structured and trackable system.

### 🧱 Robust Error Handling
- Custom exceptions with meaningful messages.

---

## 🛠️ Technologies Used
- **Backend**: Java 21, Spring Boot 3.x
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA / Hibernate
- **Security**: Spring Security + JWT (jjwt)
- **Email Notifications**: Spring Mail
- **Build Tool**: Apache Maven
- **Others**: Lombok, Jakarta Mail API

---

## 🔧 Prerequisites
- JDK 21 or higher
- PostgreSQL Database
- Apache Maven 3.x (or use mvnw)
- Gmail account with App Password enabled (for email)

---

## ⚙️ Getting Started

### 1️⃣ Clone the Repository
```bash
git clone <repository_url>
cd pg-mitra-backend
```

### 2️⃣ Database Setup
```sql
CREATE DATABASE pgmitra;
```

### 3️⃣ Environment Configuration
Update `src/main/resources/application.properties`:

<details>
<summary><b>Click to Expand Configuration</b></summary>

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/pgmitra
spring.datasource.username=your_postgres_username
spring.datasource.password=your_postgres_password

# Email (for rent reminders)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# JWT
app.jwt.secret=your-secret-key
app.jwt.expiration=900000
app.jwt.refresh-expiration=604800000

# Logging
logging.level.root=INFO
logging.level.com.PGmitra.app=DEBUG

# Misc
server.port=1234
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```
</details>

### 4️⃣ Build and Run
```bash
./mvnw clean install
./mvnw spring-boot:run
```
Access at: [http://localhost:1234](http://localhost:1234)

---

## 📌 API Endpoints
<details>
<summary><b>Click to Expand API Overview</b></summary>

- **Authentication**: `/api/auth`
  - `POST /register/owner`
  - `POST /register/tenant`
  - `POST /login`
  - `POST /refresh`

- **Tenant**: `/api/tenant`
  - `GET /hello`
  - `GET /profile/{username}`
  - `POST /feedback?tenantId={}`
  - `PUT /update/{tenantId}`

- **Owner**: `/api/vendor`
  - `GET /hello`
  - `POST /property/{id}`
  - `POST /room/{id}`
  - `POST /addNewTenant`
  - `POST /deleteTenant`
  - `POST /announcement`
  - `POST /payment`
  - `GET /payments/{ownerId}`
  - `PUT /payment/{paymentId}/status`

- **Announcements**: `/api/announcement`
  - `GET /owner/{ownerId}`

- **Dining**: `/api/Dining`
  - `GET /owner/{ownerId}?date={date}`
  - `POST /dummy/{ownerId}`
</details>

---

## 🧩 Database Schema
Each table is mapped using JPA Entities:
- `owners`, `tenants`, `properties`, `rooms`, `payments`
- `announcements`, `dining_menus`, `feedback`

Refer to the full schema [here](#).

---

## 🔐 Authentication & Authorization
- Uses **JWT (Access + Refresh tokens)**.
- Roles: `ROLE_OWNER`, `ROLE_TENANT`
- Secure endpoints with Spring Security.

---

## ⏰ Scheduled Jobs
- `ReminderScheduler` runs **daily at 9 AM**.
- Emails sent for **due/overdue payments**.

---

## ❌ Error Handling
- `ResourceNotFoundException`: 404 Not Found
- `ResourceAlreadyExistsException`: 409 Conflict
- `RoomCapacityFull`: 409 Conflict

All errors return structured JSON responses.

---

## 📁 Project Structure
```bash
src/main/java/com/PGmitra/app
├── controller
├── dto
├── entity
├── exception
├── repository
├── security
├── service
└── scheduler
```

---

## 📄 .gitignore Overview
- Ignores:
  - Compiled classes (`/target`)
  - IDE files (`.idea/`, `.vscode/`)
  - Maven wrapper JARs (`.mvn/`)

---

## 📦 Maven Wrapper Info
- `mvnw`, `mvnw.cmd` to build/run without global Maven.
- Ensures consistent build across systems.

---

## 📝 pom.xml Summary
Key dependencies:
- Spring Boot Starters: Web, Security, Data JPA, Mail
- PostgreSQL Driver
- Lombok
- jjwt (JWT library)

Plugins:
- `spring-boot-maven-plugin`
- `maven-compiler-plugin`

---

## ✅ Testing
- `AppApplicationTests.java` includes `contextLoads()` test.
- Placeholder for future unit/integration tests.

---

## 📚 Help & Resources
- Refer to `HELP.md` for additional Spring Boot documentation.
- Useful for troubleshooting and further learning.

---


