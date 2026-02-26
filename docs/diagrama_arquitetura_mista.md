```mermaid
    flowchart TB
        U[Usuario: Aluno/Resp/Professor/Admin/Financeiro/Psicologo] --> UI[Web App / Mobile]

        UI --> GW[API Gateway / BFF]
        GW --> AUTH[Servico de Autenticacao e Autorizacao RBAC/JWT]
        GW --> ACAD[Servico Academico]
        GW --> FIN[Servico Financeiro]
        GW --> PSY[Servico Psicologia]
        GW --> COMMS[Servico Comunicacão/Mural]
        GW --> DOC[Servico Documentos]
        GW --> REP[Servico Relatorios]

        AUTH --> RDB[(Banco Relacional SQL)]
        ACAD --> RDB
        FIN --> RDB
        DOC --> RDB

        PSY --> NDB[(Banco NoSQL Documentos)]
        COMMS --> NDB
        REP --> RDB
        REP --> NDB

        DOC --> OBJ[(Object Storage: S3/MinIO/Bucket)]

        GW --> CACHE[(Redis Cache)]
        REP --> MQ[[Fila: RabbitMQ/Kafka]]
        COMMS --> MQ

        MQ --> NOTIF[Notificacoes: Email/WhatsApp/Push]
        MQ --> JOBS[Jobs: backups, PDFs, consolidacoes]
