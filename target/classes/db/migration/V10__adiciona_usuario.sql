INSERT INTO usuario (nome, nome_usuario, email, senha, cpf, data_nascimento, telefone, ativo) VALUES
('Christyan', 'chris', 'chris@gmail.com', '{noop}12345', 12345678900, '03-08-2002', 34996571397, true);

INSERT INTO diretor (nome, cpf, data_nascimento, ativo) VALUES
('Joao', 12345678900, '08-02-2000', true),
('Ana', 12345678900, '25-12-2001', true);

INSERT INTO papel (codigo, nome) VALUES 
(1, 'ROLE_ADMIN');

INSERT INTO usuario_papel (codigo_usuario, codigo_papel) VALUES
(1, 1);

INSERT INTO area (codigo, nome) VALUES
(1, 'Secretaria (Controle de atas de reunião, documentações, emissão de certificados e qualquer outro tipo de documentos)'),
(2, 'Marketing (Controle das redes sociais, divulgação, criação de artes e conceitos de novos produtos)');

INSERT INTO diretor_area (codigo_diretor, codigo_area) VALUES
(1, 1),
(2, 2);