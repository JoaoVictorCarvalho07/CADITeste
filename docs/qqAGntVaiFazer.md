## 1. Fechar decisões


* Stack: Java Spring Boot

* Banco principal: mysql

* NoSQL: MongoDB agora ou depois

* Mensageria: RabbitMQ

* Storage: local / S3 / MinIO || azure

* Deploy: azure 


## 2. Definir o MVP real (escopo mínimo)

### o que precisa existir para o sistema ser usável:

* Login + RBAC (perfis)

* CRUD de Aluno / Professor / Turma / Matrícula

* Frequência (registro e consulta)

* Comunicação/Mural (postar + visualizar)

* Notificação (mesmo que seja só log no começo)


## 3. Modelar o banco SQL do núcleo

* Criem um ER simples com 6–10 tabelas:

---
usuarios

roles / user_roles

turmas

matriculas

frequencias (ou aulas + presenças)

comunicados (se ficar no SQL no MVP)


---

## 4. Criar o repositório e padrões do projeto

Criar repo Git + padrão de branch (main/dev)

Definir estilo:

estrutura de pacotes por módulo

padrão de DTO/Service/Repository

tratamento de erros (GlobalExceptionHandler)

validação (Bean Validation)

## 5. Implementar primeiro: Auth + RBAC

Ordem boa:

cadastro de usuário (interno/admin)

login

geração/validação JWT

proteção por roles nas rotas

## 6. Implementar o “fluxo acadêmico mínimo”

Turmas

Matrículas (Aluno ↔ Turma)

Registro de frequência (Professor)

Consulta de frequência (Aluno/Responsável/Admin)

## 7. Implementar Comunicação/Mural

CRUD de comunicados

segmentação (por turma/perfil)

endpoint de listagem por usuário

## 8. Implementar Notificações (MVP)

Scheduler: tabela notification_queue + @Scheduled

RabbitMQ: filas + consumer



## 9. Documentos (se entrar no MVP)

upload de atestado/justificativa

metadados no SQL

arquivo local ou bucket
