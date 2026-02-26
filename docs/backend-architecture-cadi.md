# Backend Architecture & Business Logic --- CADI

## Contexto Técnico

- Backend: Java + Spring Boot\
- Banco de Dados: MySQL\
- Arquitetura: Monólito Modular\
- Comunicação: REST + Eventos Assíncronos\
- Autenticação: JWT\
- Autorização: RBAC + Policy Layer

---

# 1. Estrutura Arquitetural do Backend

## Organização por Módulo

api → Controllers + DTOs + validações\
application → Casos de uso (Services)\
domain → Entidades + regras de negócio\
infra → Repositories + integrações externas\
config → Segurança + configurações

### Princípios

- Controller não contém regra de negócio\
- Repository não contém regra de negócio\
- A lógica vive na camada application/domain\
- Cada caso de uso deve ser transacional (@Transactional)

---

# 2. Módulos do Sistema

## Identity & Access (IAM)

- Login e Refresh Token\
- Emissão e validação de JWT\
- Controle de Roles

## People

- Aluno\
- Responsável\
- Professor\
- Colaboradores\
- Padrinhos\
- Vínculos entre entidades

## Academic

- Turmas\
- Oficinas\
- Frequência\
- Parecer descritivo

## Finance

- Contas a pagar\
- Contas a receber\
- Doações\
- Relatórios financeiros

## Communication

- Mural institucional\
- Comunicados\
- Anexos

## Psychosocial

- Prontuário psicológico\
- Anotações terapêuticas\
- Histórico clínico

---

# 3. Modelo de Autorização

## Nível 1 --- RBAC

Proteção global por roles via Spring Security.

## Nível 2 --- Policy Layer

Validação por vínculo (ex: professor só acessa turmas atribuídas).

Exemplo: ProfessorPolicy.assertCanAccessTurma(userId, turmaId)\
ResponsavelPolicy.assertCanAccessAluno(userId, alunoId)\
PsicologoPolicy.assertCanAccessPaciente(userId, alunoId)

---

# 4. Casos de Uso Críticos

## Registrar Frequência

- Professor deve estar vinculado à turma\
- Frequência é por data\
- UNIQUE(turma_id, data) no banco

## Registrar Parecer

- Professor vinculado à turma\
- Histórico obrigatório

## Enviar Justificativa

1.  Validar vínculo\
2.  Validar arquivo\
3.  Salvar em Object Storage\
4.  Registrar metadados\
5.  Gerar evento

## Registrar Doação

- Validar padrinho\
- Registrar valor\
- Gerar evento

## Registrar Anotação Psicológica

- Apenas psicólogo\
- Apenas pacientes atribuídos\
- Auditoria obrigatória

---

# 5. Transações e Integridade

- Uso de @Transactional\
- NOT NULL\
- FOREIGN KEY\
- UNIQUE\
- Auditoria para dados críticos

---

# 6. Estratégia de Eventos

## Transactional Outbox (MVP)

1.  Caso de uso executa regra\
2.  Evento salvo na tabela outbox_event\
3.  Scheduler processa eventos

Preparado para futura migração para RabbitMQ ou Kafka.

---

# 7. Estrutura de Packages

br.cadi\
shared\
iam\
people\
academic\
finance\
communication\
psychosocial

---

# 8. Próximos Passos

1.  Definir modelo User + Role + vínculo\
2.  Implementar Policy Layer\
3.  Implementar Login + Turmas + Frequência\
4.  Padronizar respostas e erros\
5.  Implementar Outbox + Scheduler

---

Este documento é a referência oficial da arquitetura backend do Sistema
CADI.
