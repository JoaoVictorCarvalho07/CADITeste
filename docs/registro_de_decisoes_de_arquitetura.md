# Registro de Decisões de Arquitetura e Evolução do Projeto

Este documento tem como objetivo registrar **decisões técnicas**, **mudanças de arquitetura**, **escolhas de stack** e **evoluções planejadas** do sistema. Ele serve como fonte oficial de consulta para evitar retrabalho, justificar escolhas em contexto acadêmico/profissional e acompanhar a maturidade do projeto ao longo do tempo.

---

## 1. Objetivo do Documento

* Registrar decisões arquiteturais relevantes
* Manter histórico de mudanças
* Justificar escolhas técnicas
* Facilitar comunicação entre desenvolvedores
* Servir como material de defesa para banca, professores ou stakeholders

Este documento segue o modelo de **ADR – Architecture Decision Record**.

---

## 2. Visão Geral do Sistema

* Tipo de sistema: Sistema integrado de gestão educacional
* Arquitetura: Monólito modular orientado a serviços
* Estilo arquitetural: Camadas + eventos assíncronos
* Comunicação interna: REST + Mensageria(Eventos)

---

## 3. Decisões Arquiteturais Registradas

### ADR-001 — Arquitetura em Camadas

**Status:** Em analise

**Decisão:**
O sistema foi estruturado em cinco camadas principais:

1. Apresentação
2. Entrada (API Gateway / BFF)
3. Serviços (Domínio)
4. Infraestrutura
5. Dados

**Justificativa:**
Essa separação melhora a organização do código, reduz acoplamento e facilita manutenção, testes e evolução futura.

---

### ADR-002 — Monólito Modular

**Status:** Em analise

**Decisão:**
O sistema será implementado como um monólito modular, com separação lógica por domínios (acadêmico, financeiro, comunicação, etc.).

**Justificativa:**
Reduz complexidade inicial em comparação a microserviços, mantendo boa organização e permitindo futura extração de serviços.

---

### ADR-003 — Linguagem e Framework

**Status:** Em analise

**Decisão:**
Utilização de Java com Spring Boot como base do backend.

**Justificativa:**
Spring Boot oferece maturidade, ampla documentação, boa integração com segurança, bancos de dados e mensageria, além de forte aceitação acadêmica e profissional.

---

### ADR-004 — Camada de Entrada (API Gateway)

**Status:** Aprovado

**Decisão:**
Todas as requisições externas passam por uma camada de entrada (API Gateway/BFF).

**Responsabilidades:**

* Validação de JWT
* Autorização básica (RBAC)
* Roteamento de requisições
* Padronização de respostas
* Proteção e observabilidade

**Justificativa:**
Centraliza preocupações transversais e protege os serviços internos.

---

### ADR-005 — Autenticação e Autorização

**Status:** Aprovado

**Decisão:**
Uso de JWT para autenticação e RBAC para controle de permissões.

**Detalhes:**

* Serviço de Autenticação responsável apenas por login e refresh token
* Validação do JWT ocorre no Gateway
* Serviços aplicam regras de escopo e negócio

---

### ADR-006 — Mensageria e Eventos

**Status:** Em analise

**Decisão:**
Adoção de comunicação orientada a eventos para notificações e processos assíncronos.

**Implementação inicial:**

* RabbitM ou fila baseada em banco (scheduler) 

**Evolução planejada:**

* Apache Kafka como broker de eventos

**Justificativa:**
Desacopla serviços, melhora resiliência e permite escalabilidade.

---

### ADR-007 — Sistema de Comunicação e Notificações

**Status:** Em analise

**Decisão:**
Separação clara entre:

* Serviço de Comunicação/Mural (conteúdo institucional)
* Notification Service (envio de notificações)

**Justificativa:**
Evita acoplamento e permite evolução independente dos canais de entrega.

---

### ADR-008 — Persistência de Dados

**Status:** Em analise

**Decisão:**

* Banco Relacional (PostgreSQL/MySQL) para dados acadêmicos e financeiros
* Banco NoSQL (MongoDB) opcional para prontuários e conteúdos flexíveis

**Justificativa:**
Garante integridade para dados críticos e flexibilidade para dados não estruturados.

---

### ADR-009 — Armazenamento de Arquivos

**Status:** Aprovado

**Decisão:**
Arquivos serão armazenados em Object Storage (S3/MinIO), com apenas metadados no banco de dados.

---

## 4. Evolução Planejada da Arquitetura

| Etapa         | Evolução                      |
| ------------- | ----------------------------- |
| MVP           | Fila em banco + Scheduler Ou rabbitMQ   |
| Intermediária | RabbitMQ                      |
| Avançada      | Kafka + DLQ + Observabilidade |

---

## 5. Registro de Alterações

| Data | Alteração        | Motivo                   |
| ---- | ---------------- | ------------------------ |
| --   | Documento criado | Início formal do projeto |

---

## 6. Observações Finais

Este documento deve ser atualizado sempre que:

* uma decisão arquitetural for revista
* uma tecnologia for substituída
* um novo módulo estrutural for adicionado

Ele representa a **fonte de verdade arquitetural** do projeto.
