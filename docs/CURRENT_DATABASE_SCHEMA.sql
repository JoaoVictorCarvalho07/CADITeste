-- ========================================================
-- CADI BACKEND - CURRENT DATABASE SCHEMA
-- Generated based on JPA Entities (Modular Monolith)
-- ========================================================

-- 1. IAM (Identity & Access Management)
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role_id BIGINT,
    CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- 2. People Management
CREATE TABLE people (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    phone VARCHAR(255),
    cpf VARCHAR(255) UNIQUE,
    birth_date DATE,
    street VARCHAR(255),
    number VARCHAR(255),
    complement VARCHAR(255),
    neighborhood VARCHAR(255),
    city VARCHAR(255),
    state VARCHAR(255),
    zip_code VARCHAR(255),
    type VARCHAR(255), -- Enum: STUDENT, PROFESSOR, DONOR, VOLUNTEER, STAFF, PSYCHOLOGIST
    user_id BIGINT UNIQUE,
    CONSTRAINT fk_person_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 3. Academic Module
CREATE TABLE turmas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    schedule VARCHAR(255),
    professor_id BIGINT,
    active BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_turma_professor FOREIGN KEY (professor_id) REFERENCES people(id)
);

CREATE TABLE matriculas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    turma_id BIGINT NOT NULL,
    enrollment_date DATE,
    status VARCHAR(255), -- e.g., ACTIVE, CANCELLED
    CONSTRAINT fk_matricula_student FOREIGN KEY (student_id) REFERENCES people(id),
    CONSTRAINT fk_matricula_turma FOREIGN KEY (turma_id) REFERENCES turmas(id)
);

CREATE TABLE aulas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    turma_id BIGINT NOT NULL,
    date_time TIMESTAMP,
    topic VARCHAR(255),
    CONSTRAINT fk_aula_turma FOREIGN KEY (turma_id) REFERENCES turmas(id)
);

CREATE TABLE presencas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    aula_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    present BOOLEAN NOT NULL,
    observation VARCHAR(255),
    CONSTRAINT fk_presenca_aula FOREIGN KEY (aula_id) REFERENCES aulas(id),
    CONSTRAINT fk_presenca_student FOREIGN KEY (student_id) REFERENCES people(id)
);

-- 4. Finance Module
CREATE TABLE projects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    financial_goal DECIMAL(38,2),
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE financial_transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255), -- Enum: INCOME, EXPENSE
    transaction_value DECIMAL(38,2),
    date_time TIMESTAMP,
    description VARCHAR(255),
    project_id BIGINT,
    person_id BIGINT,
    receipt_url VARCHAR(255),
    CONSTRAINT fk_transaction_project FOREIGN KEY (project_id) REFERENCES projects(id),
    CONSTRAINT fk_transaction_person FOREIGN KEY (person_id) REFERENCES people(id)
);

-- 5. Communication Module
CREATE TABLE notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    message VARCHAR(1000) NOT NULL,
    type VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sender_id BIGINT,
    CONSTRAINT fk_notification_sender FOREIGN KEY (sender_id) REFERENCES users(id)
);

CREATE TABLE notification_users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    notification_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    read BOOLEAN DEFAULT FALSE,
    read_at TIMESTAMP,
    sent_whatsapp BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_notif_user_notif FOREIGN KEY (notification_id) REFERENCES notifications(id),
    CONSTRAINT fk_notif_user_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE mural_posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(2000) NOT NULL,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    author_id BIGINT,
    global BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_mural_author FOREIGN KEY (author_id) REFERENCES users(id)
);

CREATE TABLE mural_post_roles (
    post_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (post_id, role_id),
    CONSTRAINT fk_mural_role_post FOREIGN KEY (post_id) REFERENCES mural_posts(id),
    CONSTRAINT fk_mural_role_role FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE mural_post_turmas (
    post_id BIGINT NOT NULL,
    turma_id BIGINT NOT NULL,
    PRIMARY KEY (post_id, turma_id),
    CONSTRAINT fk_mural_turma_post FOREIGN KEY (post_id) REFERENCES mural_posts(id),
    CONSTRAINT fk_mural_turma_turma FOREIGN KEY (turma_id) REFERENCES turmas(id)
);

-- 6. Psychosocial Module
CREATE TABLE prontuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    psychologist_id BIGINT NOT NULL,
    notes VARCHAR(4000) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    confidential_code VARCHAR(255),
    CONSTRAINT fk_prontuario_patient FOREIGN KEY (patient_id) REFERENCES people(id),
    CONSTRAINT fk_prontuario_psychologist FOREIGN KEY (psychologist_id) REFERENCES people(id)
);
