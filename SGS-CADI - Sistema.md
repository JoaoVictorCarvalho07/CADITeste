## 1. Visão Geral
Sistema integrado para gerenciamento completo de instituição educacional, contemplando os aspectos acadêmicos, administrativos e financeiros com controle de acesso multinível.
## 2. Hierarquia de Acesso
Aluno/Responsável → Professor → Secretaria → Administrativo → Financeiro
## 3. Perfis de Usuário e Permissões
### 3.1 Aluno/Responsável
**Acesso restrito aos próprios dados:**
- Consulta de frequência escolar
- Visualização de dados cadastrais básicos e informações de contato
- Acesso à grade horária das turmas matriculadas
- Mural de atualizações institucionais (alterações de professor, sala, calendário acadêmico)
- Gerenciamento de atestados médicos e justificativas
### 3.2 Professor
**Acesso pessoal e pedagógico:**
- Dados pessoais e profissionais
- Turmas atribuídas:
    - Listagem completa de alunos
    - Registro de pareceres descritivos individuais (Anotações)
    - Controle de frequência por aluno
    - Dados reduzidos dos alunos (nome, data de nascimento, matrícula)
    - Grade horária da turma (itinerário)
### 3.3 Administrativo
#### 3.3.1 Secretaria Acadêmica
**Acesso operacional completo:**
- Gerenciamento de dados pessoais
- Cadastro e manutenção de alunos:
    - Informações completas do aluno e responsáveis
    - Vinculação a turmas
- Cadastro de professores:
    - Dados profissionais completos
    - Atribuição a turmas
- Controle de oficinas:
    - Organização por turmas
- Registro de colaboradores:
    - Equipe de limpeza
    - Voluntários
    - Psicólogos
    - Demais funcionários
- Cadastro de padrinhos
#### 3.3.2 Gestão Administrativa
**Acesso supervisor com visão ampliada:**
- Todas as permissões da secretaria acadêmica
- Gerenciamento de usuários secretários
- Acompanhamento dos dados financeiros (somente visualização)
### 3.4 Setor Financeiro
**Acesso especializado transacional:**
- Dados pessoais do usuário
- Gestão de documentos fiscais:
    - Emissão e controle de notas fiscais
- Controle de fluxo de caixa:
    - Contas a pagar
    - Contas a receber
    - Cupons fiscais
    - Doações de padrinhos
### 3.5 Psicólogo
**Acesso clínico controlado:**
- Dados essenciais do paciente:
    - Identificação básica (nome, idade)
    - Informações sobre condições especiais
- Sistema de anotações terapêuticas privadas
## 4. Requisitos Funcionais por Perfil
### 4.1 Alunos/Responsáveis
- Consulta das informações autorizadas
- Atualização de dados de contato (e-mail e telefone)
### 4.2 Professores
- Acesso às informações das turmas sob sua responsabilidade
- Registro de presença e faltas em tempo real
- Elaboração de pareceres descritivos individuais por aluno
### 4.3 Administrativo
- Sistema de notificações automáticas
- Geração de relatórios analíticos:
    - Frequência global de beneficiários
    - Cronograma de oficinas realizadas
    - Participação e frequência de voluntários
- Emissão automatizada de documentos:
    - Certificados
    - Declarações de matrícula e frequência
- Gestão do ciclo de vida acadêmico:
    - Processos de matrícula e desistência
    - Remanejamento de alunos entre turmas
- Operações CRUD completas para todas as entidades do sistema
- Repositório centralizado de documentos institucionais
- Controle de lista de espera com pré-cadastro
### 4.4 Financeiro
- Relatórios financeiros consolidados:
    - Histórico de notas fiscais
    - Demonstrativo de despesas
### 4.5 Psicólogo
- Acesso seguro aos dados clínicos essenciais
- Registro confidencial de observações e acompanhamentos
## 5. Requisitos Não Funcionais
- Interface intuitiva com experiência de usuário otimizada
- Performance responsiva em diferentes cargas de trabalho
- Arquitetura centralizada de dados
- Design responsivo para acesso via desktop e dispositivos móveis
- Dashboard unificado com métricas e indicadores
- Sistema automático de backup semanal
## 6. Módulos Principais
- Controle de chamada digital
- Gestão documental de alunos
- Processos de matrícula e remanejamento
- Sistema de comunicação institucional