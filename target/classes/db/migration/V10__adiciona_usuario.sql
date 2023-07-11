INSERT INTO area (codigo, nome) VALUES
(1, 'Secretaria (Controle de atas de reunião, documentações, emissão de certificados e qualquer outro tipo de documentos)'),
(2, 'Financeiro (Planejamento financeiro, controle de fluxo de caixa e produtos, emissão de notas, vendas)'),
(3, 'Esportivo (Organização de eventos esportivos, controle de atletas, treinos e materiais esportivos)'),
(4, 'Marketing (Controle das redes sociais, divulgação, criação de artes e conceitos de novos produtos)'),
(5, 'Eventos (Auxiliar na logísticas de competições, nas participações da Atléticas em eventos e ações sociais)');

--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

INSERT INTO diretor (nome, cpf, data_nascimento, status) VALUES
('Joao', 12345678900, '08-02-2000', 'ATIVO'),
('Ana', 12345678900, '25-12-2001', 'ATIVO');

--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

INSERT INTO diretor_area (codigo_diretor, codigo_area) VALUES
(1, 1),
(2, 2);

--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

INSERT INTO papel (codigo, nome) VALUES 
(1, 'ROLE_ADMIN'),
(2, 'ROLE_DIRETOR'),
(3, 'ROLE_USUARIO');

--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

INSERT INTO pessoa (nome, cpf, data_nascimento, status) VALUES
('Andraina', 12345678900, '12-04-2000', 'ATIVO'),
('Moisac', 12345678900, '26-12-2003', 'ATIVO');

--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

INSERT INTO produto (nome, descricao, status) VALUES
('Caneca', '850 ml', 'ATIVO'),
('Colete', 'Sublimação total', 'ATIVO'),
('Tirante', '900 x 40', 'ATIVO');

--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

INSERT INTO usuario (nome, nome_usuario, email, senha, cpf, data_nascimento, telefone, ativo) VALUES
('Christyan', 'chris', 'chris@gmail.com', '{noop}12345', 12345678900, '03-08-2002', 34996571397, true);

--xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

INSERT INTO usuario_papel (codigo_usuario, codigo_papel) VALUES
(1, 1);



