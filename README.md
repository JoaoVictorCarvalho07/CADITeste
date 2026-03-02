# CADI Backend

Financial management system for a non-profit educational institution.

## Technologies
- Java 21 LTS
- Spring Boot 3
- Spring Security + JWT
- Spring Data JPA
- MySQL (Production) / H2 (Development)
- Maven
- Lombok

## How to run
1. Clone the repository
2. Run `./mvnw spring-boot:run`

## Modules
- **IAM**: Identity and Access Management with JWT and RBAC.
- **People**: Management of students, professors, donors, and staff.
- **Finance**: Management of projects, donations, and financial transactions.

## Architecture
- Modular Monolith
- Layered Architecture (Controller, Service, Repository, Entity)
- For messagerie configure the application with api key gmail etc
