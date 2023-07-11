CREATE TABLE public.usuario
(
	codigo bigserial NOT NULL,
	nome text,
	nome_usuario text,
	email text,
	senha text,
	cpf text,
	data_nascimento date,
	telefone text,
	ativo boolean,
	PRIMARY KEY (codigo)
);

CREATE TABLE public.papel
(
	codigo bigserial NOT NULL,
	nome text,
	PRIMARY KEY (codigo)
);

CREATE TABLE public.usuario_papel
(
	codigo_usuario bigint NOT NULL,
	codigo_papel bigint NOT NULL
);

ALTER TABLE public.usuario_papel
    ADD FOREIGN KEY (codigo_usuario)
    REFERENCES public.usuario (codigo)
    NOT VALID;


ALTER TABLE public.usuario_papel
    ADD FOREIGN KEY (codigo_papel)
    REFERENCES public.papel (codigo)
    NOT VALID;

-- =================================================

CREATE TABLE public.pessoa
(
    codigo serial NOT NULL,
    nome text,
    cpf text,
    data_nascimento date,
    PRIMARY KEY (codigo)
);

ALTER TABLE public.pessoa ADD COLUMN status text DEFAULT 'ATIVO';

-- =================================================

CREATE TABLE public.produto 
(
	codigo serial NOT NULL,
	nome text,
	descricao text,
	-- codigo_carrinho bigint NOT NULL,
	PRIMARY KEY (codigo)
	-- FOREIGN KEY (codigo_carrinho) REFERENCES carrinho(codigo)
);

ALTER TABLE public.produto ADD COLUMN status text DEFAULT 'ATIVO';

CREATE TABLE public.carrinho
(
    codigo serial NOT NULL,
	-- codigo_usuario bigint NOT NULL,
	codigo_pessoa bigint NOT NULL,
	codigo_produto bigint NOT NULL,
	PRIMARY KEY (codigo),
    -- FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
	FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo),
	FOREIGN KEY (codigo_produto) REFERENCES produto(codigo)
);