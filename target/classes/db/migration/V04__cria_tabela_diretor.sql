CREATE TABLE public.diretor
(   
    codigo serial NOT NULL,
    nome text,
    cpf text,
    data_nascimento date,
    ativo boolean,
    PRIMARY KEY (codigo)
);

CREATE TABLE public.area
(
	codigo bigserial NOT NULL,
	nome text,
	PRIMARY KEY (codigo)
);

CREATE TABLE public.diretor_area
(
	codigo_diretor bigint NOT NULL,
	codigo_area bigint NOT NULL
);

ALTER TABLE public.diretor_area
    ADD FOREIGN KEY (codigo_diretor)
    REFERENCES public.diretor (codigo)
    NOT VALID;


ALTER TABLE public.diretor_area
    ADD FOREIGN KEY (codigo_area)
    REFERENCES public.area (codigo)
    NOT VALID;