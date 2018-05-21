CREATE SEQUENCE portador_credito_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 13
  CACHE 1;
  
 CREATE SEQUENCE credito_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 13
  CACHE 1;

CREATE TABLE portador_credito (
  id bigint NOT NULL DEFAULT nextval('portador_credito_seq'),
  nome varchar(80) NOT NULL,
  email varchar(100),
  cpf character(11) NOT NULL,
  rua varchar(100),
  bairro varchar(60),
  numero varchar(6),
  complemento varchar(100),
  cep character(9),
  municipio varchar(100),
  uf character(2),
  divida_serasa numeric(11,2),
  divida_spc numeric(11,2),
  divida_cartorio numeric(11,2),
  aprovado boolean NOT NULL DEFAULT false,
  analisado boolean NOT NULL DEFAULT false,
  id_usuario_cadastro bigint,
  id_usuario_ultima_alteracao bigint,
  data_cadastro timestamp with time zone NOT NULL DEFAULT now(),
  data_ultima_alteracao timestamp with time zone,
  CONSTRAINT portador_credito_pk PRIMARY KEY (id)
);

CREATE TABLE credito (
  id bigint NOT NULL DEFAULT nextval('credito_seq'),
  id_portador_credito bigint NOT NULL,
  credito_concedido numeric(11,2) NOT NULL,
  data_cadastro timestamp with time zone NOT NULL DEFAULT now(),
  id_usuario_cadastro bigint,
  id_usuario_ultima_alteracao bigint,
  CONSTRAINT credito_pk PRIMARY KEY (id),
  CONSTRAINT credito_portador_credito_fk FOREIGN KEY (id_portador_credito)
      REFERENCES portador_credito (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
