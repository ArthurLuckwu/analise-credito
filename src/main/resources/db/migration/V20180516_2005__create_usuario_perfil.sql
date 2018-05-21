CREATE SEQUENCE perfil_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 3
  CACHE 1;

CREATE SEQUENCE usuario_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 13
  CACHE 1;

CREATE TABLE perfil (
  id bigint NOT NULL DEFAULT nextval('perfil_seq'),
  nome varchar(30) NOT NULL,
  excluido boolean NOT NULL DEFAULT false,
  data_cadastro timestamp with time zone NOT NULL DEFAULT now(),
  data_ultima_alteracao timestamp with time zone,
  CONSTRAINT perfil_pk PRIMARY KEY (id)
);

CREATE TABLE perfil_permissao (
  id_perfil bigint NOT NULL,
  permissao varchar(70) NOT NULL,
  CONSTRAINT perfil_permissao_pk PRIMARY KEY (id_perfil, permissao),
  CONSTRAINT perfil_permissao_perfil_fk FOREIGN KEY (id_perfil)
      REFERENCES perfil (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE usuario (
  id bigint NOT NULL DEFAULT nextval('usuario_seq'),
  nome varchar(80) NOT NULL,
  email varchar(100),
  senha character(134),
  ativo boolean NOT NULL,
  cpf character(11) NOT NULL,
  rua varchar(100),
  bairro varchar(60),
  numero varchar(6),
  complemento varchar(100),
  cep character(9),
  municipio varchar(100),
  uf character(2),
  ultimo_login timestamp with time zone,
  ip_ultimo_login varchar(15),
  id_perfil bigint NOT NULL,
  excluido boolean NOT NULL DEFAULT false,
  data_cadastro timestamp with time zone NOT NULL DEFAULT now(),
  data_ultima_alteracao timestamp with time zone,
  CONSTRAINT usuario_pk PRIMARY KEY (id),
  CONSTRAINT usuario_perfil_fk FOREIGN KEY (id_perfil)
      REFERENCES perfil (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

insert into perfil (id, nome, excluido) values (1, 'Perfil Analista', false);
insert into perfil (id, nome, excluido) values (2, 'Perfil Captação', false);

insert into usuario (id, nome, cpf, email, senha, ativo, id_perfil, excluido) values (1, 'Usuário Analista', '68798118170', 'analista@cdt.com.br', '1000:77402a2805cf35c04a85f3d8324be47e65fff012a8c2328b5b285dc3a31e61ee:7ef0226c3afc7b9a7c9b7efde5251d499c094d24ae01d4be2794f8f72cb1d412', true, 1, false);
insert into usuario (id, nome, cpf, email, senha, ativo, id_perfil, excluido) values (2, 'Usuário Captação', '91894373570', 'captacao@cdt.com.br', '1000:77402a2805cf35c04a85f3d8324be47e65fff012a8c2328b5b285dc3a31e61ee:7ef0226c3afc7b9a7c9b7efde5251d499c094d24ae01d4be2794f8f72cb1d412', true, 2, false);
