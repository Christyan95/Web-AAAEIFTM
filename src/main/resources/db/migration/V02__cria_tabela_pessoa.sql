CREATE TABLE public.pessoa
(
    codigo serial NOT NULL,
    nome text,
    cpf text,
    data_nascimento date,
    PRIMARY KEY (codigo)
);