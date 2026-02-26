-- DDL MySQL (InnoDB) — Módulo Financeiro CADI
-- Observação: campos FK "opcionais" estão como NULL.

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

SET FOREIGN_KEY_CHECKS = 1;

-- =========================
-- unidade
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
-- usuario
-- =========================
CREATE TABLE usuario (
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
-- oficina
-- =========================
CREATE TABLE oficina (
  id_oficina INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- =========================
-- turma
-- =========================
CREATE TABLE turma (
  id_turma INT PRIMARY KEY AUTO_INCREMENT,
  titulo VARCHAR(100) NOT NULL,
  horario VARCHAR(100),
  id_oficina INT NOT NULL,

  CONSTRAINT fk_turma_oficina
    FOREIGN KEY (id_oficina) REFERENCES oficina(id_oficina)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =========================
-- conta_financeira
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
-- grupo_categoria + categoria (plano de contas)
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
-- doador
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
-- aluno (s)
-- =========================
CREATE TABLE aluno (
  id_usuario INT PRIMARY KEY, -- PK = FK -> usuario

  matricula VARCHAR(100) NOT NULL,
  frequencia DECIMAL(5,2), -- ex: 75.50 (%)

  id_oficina INT NOT NULL,  -- 1 aluno -> 1 oficina
  id_turma INT NOT NULL,    -- 1 aluno -> 1 turma

  status VARCHAR(50) DEFAULT 'ATIVO',

  CONSTRAINT uq_aluno_matricula UNIQUE (matricula),

  CONSTRAINT fk_aluno_usuario
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
    ON DELETE CASCADE ON UPDATE CASCADE,

  CONSTRAINT fk_aluno_oficina
    FOREIGN KEY (id_oficina) REFERENCES oficina(id_oficina)
    ON DELETE RESTRICT ON UPDATE CASCADE,

  CONSTRAINT fk_aluno_turma
    FOREIGN KEY (id_turma) REFERENCES turma(id_turma)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =========================
-- projeto
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
-- apadrinhamento
-- (pode ser para um aluno ou para um projeto; ambos opcionais)
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
    FOREIGN KEY (id_aluno) REFERENCES aluno(id_usuario)
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
-- movimentacao_financeira (central)
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

-- =========================
-- outbox_event
-- =========================
CREATE TABLE outbox_event (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  event_id CHAR(36) NOT NULL,                 -- UUID (ex: 550e8400-e29b-41d4-a716-446655440000)
  aggregate_type VARCHAR(60) NOT NULL,        -- ex: "AULA", "DOACAO", "OFICINA"
  aggregate_id BIGINT UNSIGNED NOT NULL,      -- id da entidade principal
  event_type VARCHAR(80) NOT NULL,            -- ex: "AULA_CANCELADA"
  routing_key VARCHAR(120) NOT NULL,          -- ex: "notify.persist.aula_cancelada"
  exchange_name VARCHAR(120) NOT NULL DEFAULT 'cadi.notifications',

  payload_json JSON NOT NULL,                 -- conteúdo do evento (titulo, msg, userIds, metadata...)
  headers_json JSON NULL,                     -- correlationId, traceId, etc (opcional)

  status ENUM('PENDING','PUBLISHED','FAILED','DEAD') NOT NULL DEFAULT 'PENDING',
  retry_count INT UNSIGNED NOT NULL DEFAULT 0,
  next_retry_at DATETIME NULL,                -- backoff
  last_error VARCHAR(500) NULL,

  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  locked_at DATETIME NULL,                    -- controle de concorrência do job
  locked_by VARCHAR(80) NULL,                 -- ex: hostname/instance-id
  published_at DATETIME NULL,

  PRIMARY KEY (id),
  UNIQUE KEY uk_outbox_event_event_id (event_id),

  KEY idx_outbox_status_created (status, created_at),
  KEY idx_outbox_next_retry (status, next_retry_at),
  KEY idx_outbox_locked (status, locked_at),
  KEY idx_outbox_aggregate (aggregate_type, aggregate_id)
) ENGINE=InnoDB;

-- =========================
-- notificacao
-- =========================
CREATE TABLE notificacao (
  id_notificacao BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  event_id CHAR(36) NOT NULL, -- UUID do evento (vem do Rabbit/Outbox)
  titulo VARCHAR(150) NOT NULL,
  mensagem TEXT NOT NULL,
  tipo VARCHAR(50) NULL, -- ex: "AULA", "FINANCEIRO", "DOACAO"
  prioridade ENUM('BAIXA','MEDIA','ALTA') NOT NULL DEFAULT 'MEDIA',

  data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  criado_por BIGINT UNSIGNED NULL, -- id_usuario (se aplicável)
  ativa BOOLEAN NOT NULL DEFAULT TRUE,

  PRIMARY KEY (id_notificacao),
  UNIQUE KEY uk_notificacao_event_id (event_id),
  KEY idx_notificacao_data (data_criacao),
  KEY idx_notificacao_tipo (tipo),
  KEY idx_notificacao_prioridade (prioridade)

  -- Se sua tabela usuario existir:
  ,CONSTRAINT fk_notificacao_criado_por FOREIGN KEY (criado_por) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB;

-- =========================
-- notificacao_usuario
-- =========================
CREATE TABLE notificacao_usuario (
  id_notificacao_usuario BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  id_notificacao BIGINT UNSIGNED NOT NULL,
  id_usuario BIGINT UNSIGNED NOT NULL,

  lida BOOLEAN NOT NULL DEFAULT FALSE,
  data_leitura DATETIME NULL,

  enviada_email BOOLEAN NOT NULL DEFAULT FALSE,
  enviada_push  BOOLEAN NOT NULL DEFAULT FALSE,

  PRIMARY KEY (id_notificacao_usuario),
  UNIQUE KEY uk_notif_user (id_notificacao, id_usuario),
  KEY idx_user_lida (id_usuario, lida),
  KEY idx_notif (id_notificacao),

  CONSTRAINT fk_notif_user_notif
    FOREIGN KEY (id_notificacao) REFERENCES notificacao(id_notificacao)
    ON DELETE CASCADE

  -- Se sua tabela usuario existir:
  ,CONSTRAINT fk_notif_user_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
) ENGINE=InnoDB;

-- =========================
-- notificacao_evento_processado
-- =========================
CREATE TABLE notificacao_evento_processado (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  event_id CHAR(36) NOT NULL,
  canal ENUM('PERSIST','EMAIL','PUSH') NOT NULL, -- qual consumer processou
  processed_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (id),
  UNIQUE KEY uk_event_canal (event_id, canal)
) ENGINE=InnoDB;

CREATE INDEX idx_mov_data ON movimentacao_financeira(data);
CREATE INDEX idx_mov_conta ON movimentacao_financeira(id_conta);
CREATE INDEX idx_mov_categoria ON movimentacao_financeira(id_categoria);
CREATE INDEX idx_mov_unidade ON movimentacao_financeira(id_unidade);
CREATE INDEX idx_mov_projeto ON movimentacao_financeira(id_projeto);
CREATE INDEX idx_mov_apadrinhamento ON movimentacao_financeira(id_apadrinhamento);
CREATE INDEX idx_mov_doador ON movimentacao_financeira(id_doador);