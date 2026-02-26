-- =========================
-- BASE: UNIDADE / ENDEREÇO
-- =========================

CREATE TABLE unidade (
  id_unidade INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  ativa BOOLEAN NOT NULL DEFAULT TRUE,

  -- endereço simples da unidade (ok manter aqui)
  logradouro VARCHAR(100),
  numero VARCHAR(20),
  complemento VARCHAR(100),
  bairro VARCHAR(100),
  cidade VARCHAR(100),
  estado VARCHAR(50),
  cep VARCHAR(20)
) ENGINE=InnoDB;

CREATE TABLE endereco (
  id_endereco INT PRIMARY KEY AUTO_INCREMENT,
  logradouro VARCHAR(100),
  numero VARCHAR(20),
  complemento VARCHAR(100),
  bairro VARCHAR(100),
  cidade VARCHAR(100),
  estado VARCHAR(50),
  cep VARCHAR(20)
) ENGINE=InnoDB;

-- vínculo usuário-endereço (permite mais de 1 endereço por usuário)
CREATE TABLE usuario_endereco (
  id_usuario INT NOT NULL,
  id_endereco INT NOT NULL,
  tipo VARCHAR(30) DEFAULT 'residencial', -- residencial, cobranca, etc.
  principal BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (id_usuario, id_endereco),
  CONSTRAINT fk_usuario_endereco_endereco
    FOREIGN KEY (id_endereco) REFERENCES endereco(id_endereco)
    ON DELETE CASCADE
) ENGINE=InnoDB;


-- =========================
-- USUÁRIO (BASE) + EXTENSÕES
-- =========================

CREATE TABLE usuario (
  id_usuario INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(150) UNIQUE,
  telefone VARCHAR(30),
  cpf VARCHAR(20) UNIQUE,
  data_nascimento DATE,
  status VARCHAR(30) DEFAULT 'ativo', -- ativo, inativo, bloqueado, etc.

  -- se quiser vincular usuário a uma unidade (opcional)
  id_unidade INT NULL,

  CONSTRAINT fk_usuario_unidade
    FOREIGN KEY (id_unidade) REFERENCES unidade(id_unidade)
    ON DELETE SET NULL
) ENGINE=InnoDB;

-- agora que usuario existe, finaliza FK de usuario_endereco
ALTER TABLE usuario_endereco
  ADD CONSTRAINT fk_usuario_endereco_usuario
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
  ON DELETE CASCADE;

-- Aluno = extensão 1:1 do usuário
CREATE TABLE aluno (
  id_usuario INT PRIMARY KEY,
  matricula VARCHAR(100),
  ativo BOOLEAN NOT NULL DEFAULT TRUE,

  CONSTRAINT fk_aluno_usuario
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
    ON DELETE CASCADE
) ENGINE=InnoDB;

-- Doador = extensão 1:1 do usuário
CREATE TABLE doador (
  id_usuario INT PRIMARY KEY,
  tipo VARCHAR(100),        -- pessoa_fisica, empresa, recorrente, pontual etc.
  documento VARCHAR(100),   -- pode ser CNPJ, outro doc
  contato VARCHAR(100),     -- observação/contato preferencial
  ativo BOOLEAN NOT NULL DEFAULT TRUE,

  CONSTRAINT fk_doador_usuario
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
    ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE cargo (
  id_cargo INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  nivel VARCHAR(100),
  descricao VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE setor (
  id_setor INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- Funcionario = extensão 1:1 do usuário
CREATE TABLE funcionario (
  id_usuario INT PRIMARY KEY,
  matricula_funcional VARCHAR(100),
  id_cargo INT,
  id_setor INT,
  tipo_contrato VARCHAR(100),
  data_admissao DATE,
  carga_horaria_semanal INT,

  CONSTRAINT fk_funcionario_usuario
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
    ON DELETE CASCADE,

  CONSTRAINT fk_funcionario_cargo
    FOREIGN KEY (id_cargo) REFERENCES cargo(id_cargo)
    ON DELETE SET NULL,

  CONSTRAINT fk_funcionario_setor
    FOREIGN KEY (id_setor) REFERENCES setor(id_setor)
    ON DELETE SET NULL
) ENGINE=InnoDB;


-- =========================
-- FINANCEIRO
-- =========================

CREATE TABLE conta_financeira (
  id_conta INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  tipo VARCHAR(100) NOT NULL, -- banco, caixa, pix, etc.
  saldo_atual DECIMAL(12,2) NOT NULL DEFAULT 0,
  id_unidade INT NOT NULL,

  CONSTRAINT fk_conta_unidade
    FOREIGN KEY (id_unidade) REFERENCES unidade(id_unidade)
    ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE grupo_categoria (
  id_grupo INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  tipo VARCHAR(50) NOT NULL -- entrada/saida (recomendado)
) ENGINE=InnoDB;

CREATE TABLE categoria (
  id_categoria INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  id_grupo INT NOT NULL,

  CONSTRAINT fk_categoria_grupo
    FOREIGN KEY (id_grupo) REFERENCES grupo_categoria(id_grupo)
    ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE projeto (
  id_projeto INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  descricao VARCHAR(300),
  meta_financeira DECIMAL(12,2),
  ativo BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB;

-- Apadrinhamento liga doador + aluno + projeto
CREATE TABLE apadrinhamento (
  id_apadrinhamento INT PRIMARY KEY AUTO_INCREMENT,
  id_aluno INT NOT NULL,    -- FK para aluno.id_usuario
  id_doador INT NOT NULL,   -- FK para doador.id_usuario
  id_projeto INT NULL,      -- opcional: apadrinha um projeto específico
  valor_mensal DECIMAL(12,2) NOT NULL,
  data_inicio DATE NOT NULL,
  data_fim DATE NULL,
  status VARCHAR(30) DEFAULT 'ativo', -- ativo, pausado, encerrado

  CONSTRAINT fk_apadrinhamento_aluno
    FOREIGN KEY (id_aluno) REFERENCES aluno(id_usuario)
    ON DELETE CASCADE,

  CONSTRAINT fk_apadrinhamento_doador
    FOREIGN KEY (id_doador) REFERENCES doador(id_usuario)
    ON DELETE CASCADE,

  CONSTRAINT fk_apadrinhamento_projeto
    FOREIGN KEY (id_projeto) REFERENCES projeto(id_projeto)
    ON DELETE SET NULL
) ENGINE=InnoDB;

-- Movimentação financeira: bem amarrada e flexível
CREATE TABLE movimentacao_financeira (
  id_movimentacao INT PRIMARY KEY AUTO_INCREMENT,
  tipo VARCHAR(50) NOT NULL,          -- entrada/saida
  valor DECIMAL(12,2) NOT NULL,
  data DATE NOT NULL,
  descricao VARCHAR(300),

  id_conta INT NOT NULL,
  id_categoria INT NOT NULL,
  id_projeto INT NULL,
  id_apadrinhamento INT NULL,
  id_doador INT NULL,                 -- doador relacionado, se houver
  id_unidade INT NULL,                -- redundante opcional (se quiser consultar rápido)
  usuario_responsavel INT NULL,        -- quem lançou

  comprovante VARCHAR(255),

  CONSTRAINT fk_mov_conta
    FOREIGN KEY (id_conta) REFERENCES conta_financeira(id_conta)
    ON DELETE RESTRICT,

  CONSTRAINT fk_mov_categoria
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
    ON DELETE RESTRICT,

  CONSTRAINT fk_mov_projeto
    FOREIGN KEY (id_projeto) REFERENCES projeto(id_projeto)
    ON DELETE SET NULL,

  CONSTRAINT fk_mov_apadrinhamento
    FOREIGN KEY (id_apadrinhamento) REFERENCES apadrinhamento(id_apadrinhamento)
    ON DELETE SET NULL,

  CONSTRAINT fk_mov_doador
    FOREIGN KEY (id_doador) REFERENCES doador(id_usuario)
    ON DELETE SET NULL,

  CONSTRAINT fk_mov_unidade
    FOREIGN KEY (id_unidade) REFERENCES unidade(id_unidade)
    ON DELETE SET NULL,

  CONSTRAINT fk_mov_usuario_responsavel
    FOREIGN KEY (usuario_responsavel) REFERENCES usuario(id_usuario)
    ON DELETE SET NULL
) ENGINE=InnoDB;

-- Indices úteis
-- CREATE INDEX idx_mov_data ON movimentacao_financeira(data);
-- CREATE INDEX idx_mov_tipo ON movimentacao_financeira(tipo);
-- CREATE INDEX idx_mov_conta ON movimentacao_financeira(id_conta);
-- CREATE INDEX idx_mov_categoria ON movimentacao_financeira(id_categoria);
-- CREATE INDEX idx_apad_aluno ON apadrinhamento(id_aluno);
-- CREATE INDEX idx_apad_doador ON apadrinhamento(id_doador);