# CADI Backend - System Overview

This document provides a comprehensive technical overview of the CADI Backend system, explaining its architecture, modules, and core functionalities.

## 1. Architecture

The system is built as a **Modular Monolith** using **Spring Boot 3** and **Java 21**. This approach ensures a clean separation of concerns while maintaining the simplicity of a single deployment unit.

### Layered Architecture
Each module follows a strict layered pattern:
1.  **API (Controllers):** Handles HTTP requests, validation, and Swagger documentation.
2.  **DTOs:** Decouples the internal database schema from the public API.
3.  **Application (Services):** Contains the business logic and orchestrates domain entities.
4.  **Domain (Entities):** Represents the core data models and business rules.
5.  **Infrastructure (Repositories):** Manages data persistence via Spring Data JPA.

---

## 2. Core Modules

### 🔐 Identity & Access Management (IAM)
- **Authentication:** Stateless authentication using **JWT (JSON Web Tokens)**.
- **RBAC (Role-Based Access Control):** Every **User** has exactly **one Role**, simplifying permission management:
    - `ROLE_ADMIN`: Full system access.
    - `ROLE_SECRETARIA`: Operational management of people and classes.
    - `ROLE_PROFESSOR`: Access to assigned classes and attendance.
    - `ROLE_ALUNO`: Access to personal frequency and mural.
    - `ROLE_FINANCEIRO`: Access to transactions and project budgets.
    - `ROLE_PSICOLOGO`: Access to confidential clinical records.
- **Security:** Passwords hashed with **BCrypt**. Protection against IDOR (Insecure Direct Object Reference) via explicit ownership checks in the security layer.

### 👤 People Management
- Centralized management of Students, Professors, Donors, and Staff.
- Decouples **Authentication User** from **Identity Person**, allowing people to exist in the system without necessarily having login credentials.

### 📚 Academic Module
- **Turmas & Matrículas:** Manages class groups and student enrollments.
- **Frequency Control:** Automated tracking of student attendance (`Aula` and `Presenca`).

### 💰 Finance Module
- **Projects:** Management of institutional projects and social initiatives.
- **Transactions:** High-precision tracking of Income and Expenses using `BigDecimal`.

### 🏥 Psychosocial Module
- Restricted access to clinical history (`Prontuario`) and therapeutic notes.
- Exclusive access for Psychologists and Administrators.

---

## 3. Messaging & Communication System

The system implements a robust, asynchronous messaging infrastructure.

### 📢 Institutional Mural
- Posts can be targeted to specific audiences (e.g., only for a specific `Turma` or a specific `Role`).
- Post visibility is calculated dynamically based on the user's single role and enrollments.

### 📩 Notification Flow & RabbitMQ
1.  **Event Generation:** When a notification is sent, a record is created in the database.
2.  **Asynchronous Publishing:** The system publishes a `WhatsAppMessageEvent` to a **RabbitMQ Topic Exchange** (`cadi.notifications`).
3.  **Consumption:** A dedicated `WhatsAppConsumer` listens to the queue and processes the external integration.
4.  **Integration:** Ready for **WhatsApp API** integration, currently using a simulated service for development.

---

## 4. Documentation & Development
- **Swagger/OpenAPI:** Full interactive API documentation available at `/swagger-ui.html`. Includes realistic examples and detailed response codes for every endpoint.
- **Database Schema:** The current relational schema can be found in [docs/CURRENT_DATABASE_SCHEMA.sql](docs/CURRENT_DATABASE_SCHEMA.sql).
- **Database:** Uses **H2** for rapid development/testing and is configured for **MySQL** in production.
- **Validation:** Strict input validation using Jakarta Bean Validation.

---

## 5. Summary of Functional Coverage
The system fulfills requirements **RF-01 through RF-40**, covering everything from student frequency to financial transparency and clinical confidentiality.
