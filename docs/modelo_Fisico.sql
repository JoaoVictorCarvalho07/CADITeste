/* modelo_Logico_Financeiro_V1: */

CREATE TABLE Unidade (
    id_unidade int PRIMARY KEY,
    nome varchar(100),
    ativa BOOLEAN,
    bairro varchar(100),
    complemento varchar(100),
    logradouro varchar(100),
    cidade varchar(100),
    CEP varchar(100),
    estado varchar(100),
    numero varchar(100)
);

CREATE TABLE Conta_Financeira (
    id_conta int PRIMARY KEY,
    nome varchar(100),
    tipo varchar(100),
    saldo_atual DECIMAL,
    id_unidade int
);

CREATE TABLE Grupo_categoria (
    id_grupo int PRIMARY KEY,
    nome varchar(100),
    tipo varchar(100)
);

CREATE TABLE Doador_Aluno_Usuario_Funcionario (
    id_usuario int PRIMARY KEY,
    tipo varchar(100),
    documento varchar(100),
    contato varchar(100),
    turma varchar(100),
    ativo BOOLEAN,
    matricula varchar(100),
    id_apadrinhamento int,
    nome varchar(100),
    email varchar(100),
    telefone varchar(100),
    cpf varchar(100),
    data_nascimento DATE,
    logradouro varchar(100),
    numero varchar(100),
    complemento varchar(100),
    bairro varchar(100),
    cidade varchar(100),
    estado varchar(100),
    CEP varchar(100),
    matricula_funcional varchar(100),
    id_cargo int,
    id_setor int,
    tipo_contrato varchar(100),
    data_admissao DATE,
    carga_horaria_semanal int,
    status varchar(100),
    id_unidade int
);

CREATE TABLE Projeto (
    id_projeto int PRIMARY KEY,
    nome varchar(100),
    Descricao varchar(300),
    meta_financeira int,
    ativo BOOLEAN
);

CREATE TABLE Movimentacao_financeira (
    id_movimentacao int PRIMARY KEY,
    tipo varchar(100),
    valor DECIMAL,
    data DATE,
    descricao varchar(300),
    id_conta int,
    id_categoria int,
    id_projeto int,
    id_apadrinhamento int,
    id_doador int,
    id_unidade int,
    comprovante varchar(100),
    usuario_responsavel int
);

CREATE TABLE Categoria (
    id_categoria int PRIMARY KEY,
    nome varchar(100),
    id_grupo int
);

CREATE TABLE Apadrinhamento (
    id_apadrinhamento int PRIMARY KEY,
    id_aluno int,
    id_projeto int,
    valor_mensal DECIMAL,
    data_inicio DATE,
    data_fim DATE,
    status BOOLEAN
);

CREATE TABLE Cargo (
    id_cargo int PRIMARY KEY,
    nome varchar(100),
    nivel varchar(100),
    descricao varchar(100)
);

CREATE TABLE Setor (
    id_setor int PRIMARY KEY,
    nome varchar(100)
);
 
ALTER TABLE Conta_Financeira ADD CONSTRAINT FK_Conta_Financeira_2
    FOREIGN KEY (fk_Unidade_id_unidade)
    REFERENCES Unidade (id_unidade)
    ON DELETE CASCADE;
 
ALTER TABLE Conta_Financeira ADD CONSTRAINT FK_Conta_Financeira_3
    FOREIGN KEY (id_unidade???)
    REFERENCES ??? (???);
 
ALTER TABLE Doador_Aluno_Usuario_Funcionario ADD CONSTRAINT FK_Doador_Aluno_Usuario_Funcionario_2
    FOREIGN KEY (id_apadrinhamento, id_endereco???, id_cargo???, id_setor???)
    REFERENCES Apadrinhamento (id_apadrinhamento, ???, ???, ???);
 
ALTER TABLE Doador_Aluno_Usuario_Funcionario ADD CONSTRAINT FK_Doador_Aluno_Usuario_Funcionario_3
    FOREIGN KEY (fk_Setor_id_setor)
    REFERENCES Setor (id_setor);
 
ALTER TABLE Doador_Aluno_Usuario_Funcionario ADD CONSTRAINT FK_Doador_Aluno_Usuario_Funcionario_4
    FOREIGN KEY (fk_Cargo_id_cargo)
    REFERENCES Cargo (id_cargo);
 
ALTER TABLE Doador_Aluno_Usuario_Funcionario ADD CONSTRAINT FK_Doador_Aluno_Usuario_Funcionario_5
    FOREIGN KEY (id_unidade)
    REFERENCES Unidade (id_unidade);
 
ALTER TABLE Movimentacao_financeira ADD CONSTRAINT FK_Movimentacao_financeira_2
    FOREIGN KEY (fk_Projeto_id_projeto)
    REFERENCES Projeto (id_projeto)
    ON DELETE CASCADE;
 
ALTER TABLE Movimentacao_financeira ADD CONSTRAINT FK_Movimentacao_financeira_3
    FOREIGN KEY (fk_Unidade_id_unidade)
    REFERENCES Unidade (id_unidade)
    ON DELETE SET NULL;
 
ALTER TABLE Movimentacao_financeira ADD CONSTRAINT FK_Movimentacao_financeira_4
    FOREIGN KEY (fk_Conta_Financeira_id_conta)
    REFERENCES Conta_Financeira (id_conta)
    ON DELETE CASCADE;
 
ALTER TABLE Movimentacao_financeira ADD CONSTRAINT FK_Movimentacao_financeira_5
    FOREIGN KEY (fk_Categoria_id_categoria)
    REFERENCES Categoria (id_categoria)
    ON DELETE RESTRICT;
 
ALTER TABLE Movimentacao_financeira ADD CONSTRAINT FK_Movimentacao_financeira_6
    FOREIGN KEY (fk_Doador_Aluno_Usuario_Funcionario_id_usuario)
    REFERENCES Doador_Aluno_Usuario_Funcionario (id_usuario)
    ON DELETE CASCADE;
 
ALTER TABLE Movimentacao_financeira ADD CONSTRAINT FK_Movimentacao_financeira_7
    FOREIGN KEY (fk_Apadrinhamento_id_apadrinhamento)
    REFERENCES Apadrinhamento (id_apadrinhamento)
    ON DELETE CASCADE;
 
ALTER TABLE Movimentacao_financeira ADD CONSTRAINT FK_Movimentacao_financeira_8
    FOREIGN KEY (id_conta???, id_categoria???, id_projeto???, id_apadrinhamento???, id_doador???, id_unidade???, usuario_responsavel???)
    REFERENCES ??? (???);
 
ALTER TABLE Categoria ADD CONSTRAINT FK_Categoria_2
    FOREIGN KEY (fk_Grupo_categoria_id_grupo)
    REFERENCES Grupo_categoria (id_grupo)
    ON DELETE RESTRICT;
 
ALTER TABLE Categoria ADD CONSTRAINT FK_Categoria_3
    FOREIGN KEY (id_grupo???)
    REFERENCES ??? (???);
 
ALTER TABLE Apadrinhamento ADD CONSTRAINT FK_Apadrinhamento_2
    FOREIGN KEY (fk_Doador_Aluno_Usuario_Funcionario_id_usuario)
    REFERENCES Doador_Aluno_Usuario_Funcionario (id_usuario)
    ON DELETE CASCADE;
 
ALTER TABLE Apadrinhamento ADD CONSTRAINT FK_Apadrinhamento_3
    FOREIGN KEY (id_aluno???, id_doador???, id_projeto???)
    REFERENCES ??? (???);