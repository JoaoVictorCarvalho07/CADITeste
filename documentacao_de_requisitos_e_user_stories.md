# Documentação de Requisitos e User Stories

## Sistema Integrado de Gestão Educacional

---

## 1. Visão Geral do Sistema

O Sistema Integrado de Gestão Educacional tem como objetivo centralizar e otimizar o gerenciamento de uma instituição educacional, contemplando os aspectos acadêmicos, administrativos, financeiros e psicossociais. A solução visa garantir controle de acesso multinível, segurança das informações, eficiência operacional e transparência nos processos.

O sistema será acessível via plataforma web, com suporte a dispositivos desktop e móveis, utilizando uma arquitetura centralizada de dados e dashboards personalizados por perfil de usuário.

---

## 2. Hierarquia de Acesso

A hierarquia de acesso define os níveis de permissão do sistema, organizados de forma crescente:

1. Aluno / Responsável
2. Professor
3. Secretaria Acadêmica
4. Gestão Administrativa
5. Financeiro
6. Psicólogo

Cada perfil possui acesso estritamente limitado às suas responsabilidades, respeitando o princípio do menor privilégio e as diretrizes da Lei Geral de Proteção de Dados (LGPD).

---

## 3. Perfis de Usuário

### 3.1 Aluno / Responsável

Perfil com acesso restrito exclusivamente aos dados do próprio aluno e às informações institucionais autorizadas.

### 3.2 Professor

Perfil com acesso pedagógico às turmas sob sua responsabilidade, sem permissões administrativas ou financeiras.

### 3.3 Administrativo

Dividido em dois subníveis:

- Secretaria Acadêmica
- Gestão Administrativa (perfil supervisor)

### 3.4 Financeiro

Perfil responsável exclusivamente por operações financeiras e documentos fiscais da instituição.

### 3.5 Psicólogo

Perfil com acesso clínico controlado, confidencial e isolado dos demais módulos do sistema.

---

## 4. Requisitos Funcionais

### 4.1 Aluno / Responsável

- RF-01: Consultar frequência escolar do aluno
- RF-02: Visualizar dados cadastrais básicos
- RF-03: Atualizar dados de contato (telefone e e-mail)
- RF-04: Visualizar grade horária das turmas matriculadas
- RF-05: Acessar mural de comunicados institucionais
- RF-06: Enviar atestados médicos e justificativas

---

### 4.2 Professor

- RF-07: Visualizar dados pessoais e profissionais
- RF-08: Acessar lista de turmas atribuídas
- RF-09: Listar alunos de cada turma
- RF-10: Registrar frequência por aluno
- RF-11: Consultar dados reduzidos dos alunos
- RF-12: Registrar pareceres descritivos individuais
- RF-13: Visualizar grade horária da turma

---

### 4.3 Secretaria Acadêmica

- RF-14: Cadastrar, editar e remover alunos
- RF-15: Gerenciar dados completos dos responsáveis
- RF-16: Vincular alunos a turmas
- RF-17: Cadastrar e gerenciar professores
- RF-18: Atribuir professores às turmas
- RF-19: Gerenciar oficinas por turma
- RF-20: Cadastrar colaboradores (voluntários, limpeza, psicólogos e outros)
- RF-21: Cadastrar padrinhos
- RF-22: Gerenciar lista de espera com pré-cadastro
- RF-23: Emitir certificados e declarações
- RF-24: Gerenciar documentos institucionais

---

### 4.4 Gestão Administrativa

- RF-25: Gerenciar usuários da secretaria
- RF-26: Visualizar dados financeiros (somente leitura)
- RF-27: Gerar relatórios analíticos institucionais
- RF-28: Monitorar frequência global dos alunos
- RF-29: Acompanhar cronograma de oficinas
- RF-30: Visualizar participação de voluntários

---

### 4.5 Financeiro

- RF-31: Gerenciar dados pessoais do usuário financeiro
- RF-32: Emitir e controlar notas fiscais
- RF-33: Gerenciar contas a pagar
- RF-34: Gerenciar contas a receber
- RF-35: Registrar cupons fiscais
- RF-36: Registrar doações de padrinhos
- RF-37: Gerar relatórios financeiros consolidados

---

### 4.6 Psicólogo

- RF-38: Visualizar dados essenciais do paciente
- RF-39: Registrar anotações terapêuticas privadas
- RF-40: Consultar histórico clínico restrito

---

## 5. Requisitos Não Funcionais

- RNF-01: Interface intuitiva e acessível
- RNF-02: Design responsivo para desktop e dispositivos móveis
- RNF-03: Performance adequada sob diferentes cargas de trabalho
- RNF-04: Controle de acesso baseado em perfis e permissões
- RNF-05: Armazenamento seguro de dados sensíveis
- RNF-06: Sistema automático de backup semanal
- RNF-07: Dashboard unificado com indicadores por perfil
- RNF-08: Arquitetura centralizada de dados

---

## 6. User Stories

### 6.1 Aluno / Responsável

US-01: Como aluno ou responsável, quero consultar a frequência escolar, para acompanhar a participação do aluno nas atividades.

US-02: Como responsável, quero atualizar meus dados de contato, para garantir comunicação eficiente com a instituição.

US-03: Como aluno ou responsável, quero visualizar comunicados institucionais, para me manter informado sobre mudanças importantes.

---

### 6.2 Professor

US-04: Como professor, quero registrar a frequência dos alunos, para manter o controle pedagógico da turma.

US-05: Como professor, quero elaborar pareceres descritivos individuais, para acompanhar o desenvolvimento dos alunos.

---

### 6.3 Secretaria Acadêmica

US-06: Como secretário acadêmico, quero cadastrar novos alunos, para manter os registros atualizados.

US-07: Como secretário acadêmico, quero vincular alunos às turmas, para organizar a estrutura acadêmica.

US-08: Como secretário acadêmico, quero emitir declarações e certificados, para atender solicitações formais.

---

### 6.4 Gestão Administrativa

US-09: Como gestor administrativo, quero visualizar relatórios institucionais, para apoiar decisões estratégicas.

US-10: Como gestor administrativo, quero acompanhar dados financeiros de forma consolidada, para avaliar a saúde financeira da instituição.

---

### 6.5 Financeiro

US-11: Como responsável financeiro, quero registrar contas a pagar e a receber, para controlar o fluxo de caixa.

US-12: Como financeiro, quero emitir notas fiscais, para cumprir obrigações legais.

---

### 6.6 Psicólogo

US-13: Como psicólogo, quero acessar apenas dados essenciais do paciente, para preservar o sigilo profissional.

US-14: Como psicólogo, quero registrar observações terapêuticas confidenciais, para acompanhar a evolução dos atendimentos.

---

## 7. Considerações Finais

Este documento estabelece a base funcional e técnica para o desenvolvimento do Sistema Integrado de Gestão Educacional, podendo ser utilizado como referência para equipes de desenvolvimento, design de interface, validação institucional, propostas técnicas, editais e documentação acadêmica.
