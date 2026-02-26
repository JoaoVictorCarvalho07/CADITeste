-- DDL MySQL (InnoDB) — Módulo Financeiro CADI
-- Observação: campos FK "opcionais" estão como NULL.

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS movimentacao_financeira;
DROP TABLE IF EXISTS apadrinhamento;
DROP TABLE IF EXISTS conta_financeira;
DROP TABLE IF EXISTS categoria;
DROP TABLE IF EXISTS grupo_categoria;
DROP TABLE IF EXISTS projeto;
DROP TABLE IF EXISTS doador;
DROP TABLE IF EXISTS aluno;
DROP TABLE IF EXISTS unidade;

SET FOREIGN_KEY_CHECKS = 1;

-- =========================
-- UNIDADE
-- =========================
CREATE TABLE unidade (
  id_unidade        INT AUTO_INCREMENT PRIMARY KEY,
  nome              VARCHAR(100) NOT NULL,
  endereco          VARCHAR(255) NULL,
  ativa             BOOLEAN NOT NULL DEFAULT TRUE,
  created_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =========================
-- USUARIO
-- =========================
CREATE TABLE Usuario (
  id_usuario INT PRIMARY KEY AUTO_INCREMENT,

  nome VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  telefone VARCHAR(30),
  cpf VARCHAR(20),
  data_nascimento DATE,

  nome_mae VARCHAR(100),
  nome_pai VARCHAR(100),

  ativo BOOLEAN NOT NULL DEFAULT TRUE,

  CONSTRAINT uq_usuario_email UNIQUE (email),
  CONSTRAINT uq_usuario_cpf UNIQUE (cpf)
) ENGINE=InnoDB;
-- =========================
-- Turma
-- =========================
CREATE TABLE Turma (
  id_turma INT PRIMARY KEY AUTO_INCREMENT,
  titulo VARCHAR(100) NOT NULL,
  horario VARCHAR(100),
  id_oficina INT NOT NULL,

  CONSTRAINT fk_turma_oficina
    FOREIGN KEY (id_oficina) REFERENCES Oficina(id_oficina)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;
-- =========================
-- Oficina
-- =========================
CREATE TABLE Oficina (
  id_oficina INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL
) ENGINE=InnoDB;
-- =========================
-- CONTA FINANCEIRA
-- =========================
CREATE TABLE conta_financeira (
  id_conta          INT AUTO_INCREMENT PRIMARY KEY,
  id_unidade        INT NOT NULL,
  nome              VARCHAR(100) NOT NULL,
  tipo              ENUM('BANCO','CAIXA','RESERVA','OUTRA') NOT NULL DEFAULT 'BANCO',
  saldo_atual       DECIMAL(12,2) NOT NULL DEFAULT 0.00,
  ativa             BOOLEAN NOT NULL DEFAULT TRUE,
  created_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  CONSTRAINT fk_conta_unidade
    FOREIGN KEY (id_unidade) REFERENCES unidade(id_unidade)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_conta_unidade ON conta_financeira(id_unidade);

-- =========================
-- GRUPO + CATEGORIA (Plano de contas)
-- =========================
CREATE TABLE grupo_categoria (
  id_grupo          INT AUTO_INCREMENT PRIMARY KEY,
  nome              VARCHAR(100) NOT NULL,
  tipo              ENUM('ENTRADA','SAIDA') NOT NULL,
  created_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE categoria (
  id_categoria      INT AUTO_INCREMENT PRIMARY KEY,
  id_grupo          INT NOT NULL,
  nome              VARCHAR(100) NOT NULL,
  ativa             BOOLEAN NOT NULL DEFAULT TRUE,
  created_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  CONSTRAINT fk_categoria_grupo
    FOREIGN KEY (id_grupo) REFERENCES grupo_categoria(id_grupo)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_categoria_grupo ON categoria(id_grupo);

-- =========================
-- DOADOR
-- =========================
CREATE TABLE doador (
  id_doador         INT AUTO_INCREMENT PRIMARY KEY,
  nome              VARCHAR(150) NOT NULL,
  tipo              ENUM('PF','PJ') NOT NULL DEFAULT 'PF',
  documento         VARCHAR(30) NULL,     -- CPF/CNPJ (se quiser, normalizar depois)
  contato           VARCHAR(150) NULL,    -- email/telefone/whatsapp
  ativo             BOOLEAN NOT NULL DEFAULT TRUE,
  created_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_doador_documento ON doador(documento);

-- =========================
-- ALUNO (s)
-- =========================
CREATE TABLE Aluno (
  id_usuario INT PRIMARY KEY, -- PK = FK -> Usuario

  matricula VARCHAR(100) NOT NULL,
  frequencia DECIMAL(5,2), -- ex: 75.50 (%)

  id_oficina INT NOT NULL,  -- 1 aluno -> 1 oficina
  id_turma INT NOT NULL,    -- 1 aluno -> 1 turma

  status VARCHAR(50) DEFAULT 'ATIVO',

  CONSTRAINT uq_aluno_matricula UNIQUE (matricula),

  CONSTRAINT fk_aluno_usuario
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id_usuario)
    ON DELETE CASCADE ON UPDATE CASCADE,

  CONSTRAINT fk_aluno_oficina
    FOREIGN KEY (id_oficina) REFERENCES Oficina(id_oficina)
    ON DELETE RESTRICT ON UPDATE CASCADE,

  CONSTRAINT fk_aluno_turma
    FOREIGN KEY (id_turma) REFERENCES Turma(id_turma)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;
-- =========================
-- PROJETO
-- =========================
CREATE TABLE projeto (
  id_projeto        INT AUTO_INCREMENT PRIMARY KEY,
  nome              VARCHAR(120) NOT NULL,
  descricao         VARCHAR(300) NULL,
  meta_financeira   DECIMAL(12,2) NULL,
  ativo             BOOLEAN NOT NULL DEFAULT TRUE,
  created_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =========================
-- APADRINHAMENTO
-- (pode ser para um ALUNO ou para um PROJETO; ambos opcionais)
-- =========================
CREATE TABLE apadrinhamento (
  id_apadrinhamento INT AUTO_INCREMENT PRIMARY KEY,
  id_doador         INT NOT NULL,
  id_aluno          INT NULL,
  id_projeto        INT NULL,
  valor_mensal      DECIMAL(12,2) NOT NULL,
  data_inicio       DATE NOT NULL,
  data_fim          DATE NULL,
  status            ENUM('ATIVO','PAUSADO','CANCELADO','ENCERRADO') NOT NULL DEFAULT 'ATIVO',
  observacao        VARCHAR(255) NULL,
  created_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  CONSTRAINT fk_apadrinhamento_doador
    FOREIGN KEY (id_doador) REFERENCES doador(id_doador)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,

  CONSTRAINT fk_apadrinhamento_aluno
    FOREIGN KEY (id_aluno) REFERENCES aluno(id_aluno)
    ON UPDATE CASCADE
    ON DELETE SET NULL,

  CONSTRAINT fk_apadrinhamento_projeto
    FOREIGN KEY (id_projeto) REFERENCES projeto(id_projeto)
    ON UPDATE CASCADE
    ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_apadrinhamento_doador ON apadrinhamento(id_doador);
CREATE INDEX idx_apadrinhamento_aluno ON apadrinhamento(id_aluno);
CREATE INDEX idx_apadrinhamento_projeto ON apadrinhamento(id_projeto);

-- =========================
-- MOVIMENTAÇÃO FINANCEIRA (central)
-- =========================
CREATE TABLE movimentacao_financeira (
  id_movimentacao   INT AUTO_INCREMENT PRIMARY KEY,
  tipo              ENUM('ENTRADA','SAIDA') NOT NULL,
  valor             DECIMAL(12,2) NOT NULL,
  data              DATE NOT NULL,
  descricao         VARCHAR(300) NULL,

  -- vínculos obrigatórios (estrutura base)
  id_conta          INT NOT NULL,
  id_categoria      INT NOT NULL,
  id_unidade        INT NOT NULL,

  -- vínculos opcionais (rastreamento)
  id_projeto        INT NULL,
  id_apadrinhamento INT NULL,
  id_doador         INT NULL,

  comprovante_url   VARCHAR(255) NULL,    -- ou caminho/ID do arquivo
  usuario_responsavel INT NULL,           -- se quiser ligar ao módulo de usuários depois

  created_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  CONSTRAINT fk_mov_conta
    FOREIGN KEY (id_conta) REFERENCES conta_financeira(id_conta)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,

  CONSTRAINT fk_mov_categoria
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,

  CONSTRAINT fk_mov_unidade
    FOREIGN KEY (id_unidade) REFERENCES unidade(id_unidade)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,

  CONSTRAINT fk_mov_projeto
    FOREIGN KEY (id_projeto) REFERENCES projeto(id_projeto)
    ON UPDATE CASCADE
    ON DELETE SET NULL,

  CONSTRAINT fk_mov_apadrinhamento
    FOREIGN KEY (id_apadrinhamento) REFERENCES apadrinhamento(id_apadrinhamento)
    ON UPDATE CASCADE
    ON DELETE SET NULL,

  CONSTRAINT fk_mov_doador
    FOREIGN KEY (id_doador) REFERENCES doador(id_doador)
    ON UPDATE CASCADE
    ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_mov_data ON movimentacao_financeira(data);
CREATE INDEX idx_mov_conta ON movimentacao_financeira(id_conta);
CREATE INDEX idx_mov_categoria ON movimentacao_financeira(id_categoria);
CREATE INDEX idx_mov_unidade ON movimentacao_financeira(id_unidade);
CREATE INDEX idx_mov_projeto ON movimentacao_financeira(id_projeto);
CREATE INDEX idx_mov_apadrinhamento ON movimentacao_financeira(id_apadrinhamento);
CREATE INDEX idx_mov_doador ON movimentacao_financeira(id_doador);