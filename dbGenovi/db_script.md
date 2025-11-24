DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
GRANT ALL ON SCHEMA public TO genovi2025;

-- ==================================================
-- DATABASE SCHEMA: GenOvi
-- Sistema de Gerenciamento de Ovinos
-- ==================================================

-- Sequences para geração de IDs
-- ==================================================

CREATE SEQUENCE gen_id_criador START 1;
CREATE SEQUENCE gen_id_funcionario START 1;
CREATE SEQUENCE gen_id_vendedor START 1;
CREATE SEQUENCE gen_id_doenca START 1;
CREATE SEQUENCE gen_id_medicamento START 1;
CREATE SEQUENCE gen_id_compra START 1;
CREATE SEQUENCE gen_id_usuario START 1;
CREATE SEQUENCE gen_id_reproducao START 1;
CREATE SEQUENCE gen_id_gestacao START 1;
CREATE SEQUENCE gen_id_parto START 1;
CREATE SEQUENCE gen_id_ovino START 1;
CREATE SEQUENCE gen_id_ascendencia START 1;
CREATE SEQUENCE gen_id_amamentacao START 1;
CREATE SEQUENCE gen_id_ciclo_cio START 1;
CREATE SEQUENCE gen_id_aplicacao START 1;
CREATE SEQUENCE gen_id_ocorrencia_doenca START 1;
CREATE SEQUENCE gen_id_pesagem START 1;
CREATE SEQUENCE gen_id_registro START 1;

-- Tabelas sem dependências externas (nível 0)
-- ==================================================

CREATE TABLE criador
(
id       bigint NOT NULL DEFAULT nextval('gen_id_criador'),
nome     character varying,
cpf_cnpj character varying,
telefone character varying,
endereco character varying,
CONSTRAINT criador_pkey PRIMARY KEY (id),
CONSTRAINT criador_cpf_cnpj_key UNIQUE (cpf_cnpj)
);

CREATE TABLE funcionario
(
id            bigint NOT NULL DEFAULT nextval('gen_id_funcionario'),
nome          character varying,
cpf_cnpj      character varying,
telefone      character varying,
endereco      character varying,
data_admissao timestamp without time zone,
data_recisao  timestamp without time zone,
CONSTRAINT funcionario_pkey PRIMARY KEY (id),
CONSTRAINT funcionario_cpf_cnpj_key UNIQUE (cpf_cnpj)
);

CREATE TABLE vendedor
(
id       bigint NOT NULL DEFAULT nextval('gen_id_vendedor'),
nome     character varying,
cpf_cnpj character varying,
email    character varying,
telefone character varying,
endereco character varying,
ativo    boolean,
CONSTRAINT vendedor_pkey PRIMARY KEY (id),
CONSTRAINT vendedor_cpf_cnpj_key UNIQUE (cpf_cnpj)
);

CREATE TABLE doenca
(
id        bigint NOT NULL DEFAULT nextval('gen_id_doenca'),
nome      character varying,
descricao character varying,
CONSTRAINT doenca_pkey PRIMARY KEY (id)
);

CREATE TABLE medicamento
(
id               bigint NOT NULL DEFAULT nextval('gen_id_medicamento'),
nome             character varying,
fabricante       character varying,
is_vacina        boolean,
quantidade_doses integer,
intervalo_doses  integer,
CONSTRAINT medicamento_pkey PRIMARY KEY (id)
);

-- Tabelas de nível 1 (dependem apenas de tabelas de nível 0)
-- ==================================================

CREATE TABLE compra
(
id          bigint NOT NULL DEFAULT nextval('gen_id_compra'),
vendedor_id bigint,
data_compra timestamp without time zone,
valor       double precision,
CONSTRAINT compra_pkey PRIMARY KEY (id),
CONSTRAINT compra_vendedor_fkey FOREIGN KEY (vendedor_id) REFERENCES vendedor (id)
);

CREATE TABLE usuario
(
id              bigint NOT NULL DEFAULT nextval('gen_id_usuario'),
id_funcionario  bigint,
email           character varying,
senha           character varying,
ativo           boolean,
autenticacao2fa boolean,
CONSTRAINT usuario_pkey PRIMARY KEY (id),
CONSTRAINT usuario_email_key UNIQUE (email),
CONSTRAINT usuario_id_funcionario_key UNIQUE (id_funcionario),
CONSTRAINT usuario_funcionario_fkey FOREIGN KEY (id_funcionario) REFERENCES funcionario (id)
);

CREATE TABLE medicamento_doenca
(
medicamento_id bigint NOT NULL,
doenca_id      bigint NOT NULL,
CONSTRAINT medicamento_doenca_medicamento_fkey FOREIGN KEY (medicamento_id) REFERENCES medicamento (id),
CONSTRAINT medicamento_doenca_doenca_fkey FOREIGN KEY (doenca_id) REFERENCES doenca (id)
);

CREATE TABLE usuario_roles
(
usuario_id bigint NOT NULL,
role       character varying,
CONSTRAINT usuario_roles_usuario_fkey FOREIGN KEY (usuario_id) REFERENCES usuario (id)
);

CREATE TABLE usuario_enum_roles
(
usuario_id bigint NOT NULL,
role       character varying
);

-- Tabelas com referências circulares (ovino, parto, gestacao, reproducao)
-- Criadas sem FKs primeiro, depois adicionadas as constraints
-- ==================================================

CREATE TABLE reproducao
(
id              bigint NOT NULL DEFAULT nextval('gen_id_reproducao'),
id_ovelha_mae   bigint,
id_carneiro_pai bigint,
data_reproducao timestamp without time zone,
reproducao      character varying,
observacoes     character varying,
CONSTRAINT reproducao_pkey PRIMARY KEY (id)
);

CREATE TABLE gestacao
(
id            bigint NOT NULL DEFAULT nextval('gen_id_gestacao'),
id_ovelha_mae bigint,
id_ovelha_pai bigint,
id_reproducao bigint,
data_gestacao timestamp without time zone,
CONSTRAINT gestacao_pkey PRIMARY KEY (id),
CONSTRAINT gestacao_id_reproducao_key UNIQUE (id_reproducao)
);

CREATE TABLE parto
(
id           bigint NOT NULL DEFAULT nextval('gen_id_parto'),
id_ovino_mae bigint,
id_ovino_pai bigint,
id_gestacao  bigint,
data_parto   timestamp without time zone,
CONSTRAINT parto_pkey PRIMARY KEY (id),
CONSTRAINT parto_id_gestacao_key UNIQUE (id_gestacao)
);

CREATE TABLE ovino
(
id              bigint NOT NULL DEFAULT nextval('gen_id_ovino'),
nome            character varying,
sexo            character varying,
raca            character varying,
grau_pureza     character varying,
rfid            bigint,
fbb             character varying,
foto_ovino      character varying,
status          character varying,
data_nascimento timestamp without time zone,
data_cadastro   timestamp without time zone,
id_ovino_mae    bigint,
id_ovino_pai    bigint,
id_parto        bigint,
id_compra       bigint,
CONSTRAINT ovino_pkey PRIMARY KEY (id),
CONSTRAINT ovino_rfid_key UNIQUE (rfid),
CONSTRAINT ovino_compra_fkey FOREIGN KEY (id_compra) REFERENCES compra (id),
CONSTRAINT ovino_ovino_mae_fkey FOREIGN KEY (id_ovino_mae) REFERENCES ovino (id),
CONSTRAINT ovino_ovino_pai_fkey FOREIGN KEY (id_ovino_pai) REFERENCES ovino (id),
CONSTRAINT ovino_parto_fkey FOREIGN KEY (id_parto) REFERENCES parto (id)
);

-- Adicionar FKs para as tabelas com referências circulares
-- ==================================================

ALTER TABLE reproducao
ADD CONSTRAINT reproducao_ovelha_mae_fkey FOREIGN KEY (id_ovelha_mae) REFERENCES ovino (id),
ADD CONSTRAINT reproducao_carneiro_pai_fkey FOREIGN KEY (id_carneiro_pai) REFERENCES ovino (id);

ALTER TABLE gestacao
ADD CONSTRAINT gestacao_ovelha_mae_fkey FOREIGN KEY (id_ovelha_mae) REFERENCES ovino (id),
ADD CONSTRAINT gestacao_ovelha_pai_fkey FOREIGN KEY (id_ovelha_pai) REFERENCES ovino (id),
ADD CONSTRAINT gestacao_reproducao_fkey FOREIGN KEY (id_reproducao) REFERENCES reproducao (id);

ALTER TABLE parto
ADD CONSTRAINT parto_ovino_mae_fkey FOREIGN KEY (id_ovino_mae) REFERENCES ovino (id),
ADD CONSTRAINT parto_ovino_pai_fkey FOREIGN KEY (id_ovino_pai) REFERENCES ovino (id),
ADD CONSTRAINT parto_gestacao_fkey FOREIGN KEY (id_gestacao) REFERENCES gestacao (id);

-- Tabelas que dependem de ovino (nível 2)
-- ==================================================

CREATE TABLE ascendencia
(
id           bigint NOT NULL DEFAULT nextval('gen_id_ascendencia'),
id_ovino_mae bigint,
id_ovino_pai bigint,
CONSTRAINT ascendencia_pkey PRIMARY KEY (id),
CONSTRAINT ascendencia_ovino_mae_fkey FOREIGN KEY (id_ovino_mae) REFERENCES ovino (id),
CONSTRAINT ascendencia_ovino_pai_fkey FOREIGN KEY (id_ovino_pai) REFERENCES ovino (id)
);

CREATE TABLE amamentacao
(
id                  bigint NOT NULL DEFAULT nextval('gen_id_amamentacao'),
"id ovelha_mae"     bigint,
id_cordeiro_mamando bigint,
data_inicio         timestamp without time zone,
data_fim            timestamp without time zone,
observacoes         character varying,
CONSTRAINT amamentacao_pkey PRIMARY KEY (id),
CONSTRAINT amamentacao_ovelha_mae_fkey FOREIGN KEY ("id ovelha_mae") REFERENCES ovino (id),
CONSTRAINT amamentacao_cordeiro_fkey FOREIGN KEY (id_cordeiro_mamando) REFERENCES ovino (id)
);

CREATE TABLE ciclo_cio
(
id          bigint NOT NULL DEFAULT nextval('gen_id_ciclo_cio'),
id_ovelha   bigint,
data_inicio timestamp without time zone,
data_fim    timestamp without time zone,
observacoes character varying,
CONSTRAINT ciclo_cio_pkey PRIMARY KEY (id),
CONSTRAINT ciclo_cio_ovelha_fkey FOREIGN KEY (id_ovelha) REFERENCES ovino (id)
);

CREATE TABLE aplicacao
(
id                bigint NOT NULL DEFAULT nextval('gen_id_aplicacao'),
id_ovino          bigint,
id_medicamento    bigint,
data_aplicacao    timestamp without time zone,
data_proxima_dose timestamp without time zone,
CONSTRAINT aplicacao_pkey PRIMARY KEY (id),
CONSTRAINT aplicacao_ovino_fkey FOREIGN KEY (id_ovino) REFERENCES ovino (id),
CONSTRAINT aplicacao_medicamento_fkey FOREIGN KEY (id_medicamento) REFERENCES medicamento (id)
);

CREATE TABLE ocorrencia_doenca
(
id          bigint NOT NULL DEFAULT nextval('gen_id_ocorrencia_doenca'),
id_ovino    bigint,
id_doenca   bigint,
data_inicio timestamp without time zone,
data_final  timestamp without time zone,
is_curada   boolean,
CONSTRAINT ocorrencia_doenca_pkey PRIMARY KEY (id),
CONSTRAINT ocorrencia_doenca_ovino_fkey FOREIGN KEY (id_ovino) REFERENCES ovino (id),
CONSTRAINT ocorrencia_doenca_doenca_fkey FOREIGN KEY (id_doenca) REFERENCES doenca (id)
);

CREATE TABLE pesagem
(
id           bigint NOT NULL DEFAULT nextval('gen_id_pesagem'),
ovino_id     bigint NOT NULL,
peso         double precision,
data_pesagem timestamp without time zone,
CONSTRAINT pesagem_pkey PRIMARY KEY (id),
CONSTRAINT pesagem_ovino_fkey FOREIGN KEY (ovino_id) REFERENCES ovino (id)
);

-- Tabela de registro (nível 3 - depende de várias outras)
-- ==================================================

CREATE TABLE registro
(
id                   bigint                      NOT NULL DEFAULT nextval('gen_id_registro'),
id_funcionario       bigint                      NOT NULL,
data_registro        timestamp without time zone NOT NULL,
is_sugestao          boolean,
id_pesagem           bigint,
id_aplicacao         bigint,
id_ocorrencia_doenca bigint,
id_reproducao        bigint,
id_gestacao          bigint,
id_parto             bigint,
descricao_registro   TEXT,

    -- Primary key
    CONSTRAINT registro_pkey PRIMARY KEY (id),

    -- Foreign keys
    CONSTRAINT registro_funcionario_fkey
        FOREIGN KEY (id_funcionario)
        REFERENCES funcionario(id),

    CONSTRAINT registro_pesagem_fkey
        FOREIGN KEY (id_pesagem)
        REFERENCES pesagem(id)
        ON DELETE SET NULL,

    CONSTRAINT registro_aplicacao_fkey
        FOREIGN KEY (id_aplicacao)
        REFERENCES aplicacao(id)
        ON DELETE SET NULL,

    CONSTRAINT registro_ocorrencia_doenca_fkey
        FOREIGN KEY (id_ocorrencia_doenca)
        REFERENCES ocorrencia_doenca(id)
        ON DELETE SET NULL,

    CONSTRAINT registro_reproducao_fkey
        FOREIGN KEY (id_reproducao)
        REFERENCES reproducao(id)
        ON DELETE SET NULL,

    CONSTRAINT registro_gestacao_fkey
        FOREIGN KEY (id_gestacao)
        REFERENCES gestacao(id)
        ON DELETE SET NULL,

    CONSTRAINT registro_parto_fkey
        FOREIGN KEY (id_parto)
        REFERENCES parto(id)
        ON DELETE SET NULL
);