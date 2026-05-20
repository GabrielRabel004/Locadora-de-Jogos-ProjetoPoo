-- =====================================================
-- SCRIPT INICIAL DO PROJETO LOCADORA GAMES
-- =====================================================
-- Este script foi pensado para ser executado do zero.
-- Por isso ele remove as tabelas antigas e cria tudo novamente.
--
-- Neste projeto:
-- - data_lancamento guarda a data real de lancamento do jogo
-- - preco representa o preco da diaria da locadora
-- - quantidade representa quantas unidades existem em estoque
-- =====================================================

CREATE DATABASE IF NOT EXISTS locadora_games;
USE locadora_games;

DROP TABLE IF EXISTS aluguel;
DROP TABLE IF EXISTS produto;
DROP TABLE IF EXISTS cliente;

-- =====================================================
-- TABELA: cliente
-- Guarda os dados basicos de cada cliente.
-- =====================================================
CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

-- =====================================================
-- TABELA: produto
-- Guarda jogos e consoles em uma tabela unica.
-- O campo tipo informa se o produto e JOGO ou CONSOLE.
-- =====================================================
CREATE TABLE produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    data_lancamento DATE NOT NULL,
    quantidade INT NOT NULL,
    genero VARCHAR(50),
    plataforma VARCHAR(50),
    marca VARCHAR(50),
    modelo VARCHAR(50)
);

-- =====================================================
-- TABELA: aluguel
-- Liga um cliente a um produto alugado.
-- =====================================================
CREATE TABLE aluguel (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    produto_id INT NOT NULL,
    data_aluguel DATE NOT NULL,
    data_devolucao DATE NOT NULL,
    CONSTRAINT fk_aluguel_cliente
        FOREIGN KEY (cliente_id) REFERENCES cliente(id),
    CONSTRAINT fk_aluguel_produto
        FOREIGN KEY (produto_id) REFERENCES produto(id)
);

-- =====================================================
-- CLIENTES INICIAIS
-- =====================================================
INSERT INTO cliente (nome, email) VALUES
('Ana Souza', 'ana@email.com'),
('Carlos Lima', 'carlos@email.com'),
('Marina Rocha', 'marina@email.com');

-- =====================================================
-- JOGOS INICIAIS
-- Os titulos abaixo sao jogos reais.
-- As datas de lancamento foram conferidas.
-- O preco e a quantidade foram definidos para a realidade da locadora.
-- =====================================================
INSERT INTO produto (nome, preco, tipo, data_lancamento, quantidade, genero, plataforma, marca, modelo) VALUES
('Hades', 12.90, 'JOGO', '2020-09-17', 8, 'Roguelike', 'PC', NULL, NULL),
('Hollow Knight', 9.90, 'JOGO', '2017-02-24', 10, 'Metroidvania', 'PC', NULL, NULL),
('Celeste', 8.90, 'JOGO', '2018-01-25', 7, 'Plataforma', 'PC', NULL, NULL),
('Stardew Valley', 7.90, 'JOGO', '2016-02-26', 12, 'Simulacao', 'PC', NULL, NULL),
('Terraria', 6.90, 'JOGO', '2011-05-16', 12, 'Sandbox', 'PC', NULL, NULL),
('Dead Cells', 10.90, 'JOGO', '2018-08-06', 8, 'Roguelite', 'PC', NULL, NULL),
('Cuphead', 9.90, 'JOGO', '2017-09-29', 6, 'Run and Gun', 'PC', NULL, NULL),
('Slay the Spire', 8.90, 'JOGO', '2019-01-23', 7, 'Cartas', 'PC', NULL, NULL),
('Undertale', 6.90, 'JOGO', '2015-09-15', 10, 'RPG', 'PC', NULL, NULL),
('Ori and the Blind Forest', 8.90, 'JOGO', '2015-03-11', 6, 'Plataforma', 'PC', NULL, NULL),
('Ori and the Will of the Wisps', 11.90, 'JOGO', '2020-03-10', 5, 'Plataforma', 'PC', NULL, NULL),
('Portal 2', 6.90, 'JOGO', '2011-04-18', 8, 'Puzzle', 'PC', NULL, NULL),
('Left 4 Dead 2', 7.90, 'JOGO', '2009-11-16', 9, 'FPS', 'PC', NULL, NULL),
('BioShock Infinite', 8.90, 'JOGO', '2013-03-25', 6, 'FPS', 'PC', NULL, NULL),
('Batman: Arkham Knight', 9.90, 'JOGO', '2015-06-23', 6, 'Acao', 'PC', NULL, NULL),
('Divinity: Original Sin 2', 13.90, 'JOGO', '2017-09-14', 5, 'RPG Tatico', 'PC', NULL, NULL),
('Baldur''s Gate 3', 17.90, 'JOGO', '2023-08-03', 4, 'RPG', 'PC', NULL, NULL),
('XCOM 2', 9.90, 'JOGO', '2016-02-04', 5, 'Estrategia', 'PC', NULL, NULL),
('No Man''s Sky', 11.90, 'JOGO', '2016-08-12', 6, 'Exploracao', 'PC', NULL, NULL),
('Subnautica', 10.90, 'JOGO', '2018-01-23', 7, 'Sobrevivencia', 'PC', NULL, NULL),
('Euro Truck Simulator 2', 7.90, 'JOGO', '2012-10-18', 10, 'Simulacao', 'PC', NULL, NULL),
('DAVE THE DIVER', 12.90, 'JOGO', '2023-06-28', 5, 'Aventura', 'PC', NULL, NULL),
('Vampire Survivors', 5.90, 'JOGO', '2022-10-20', 12, 'Roguelite', 'PC', NULL, NULL),
('Sea of Stars', 13.90, 'JOGO', '2023-08-28', 5, 'JRPG', 'PC', NULL, NULL),
('Palworld', 15.90, 'JOGO', '2024-01-18', 4, 'Sobrevivencia', 'PC', NULL, NULL),
('Lies of P', 16.90, 'JOGO', '2023-09-18', 4, 'Soulslike', 'PC', NULL, NULL),
('Sons Of The Forest', 14.90, 'JOGO', '2024-02-22', 4, 'Sobrevivencia', 'PC', NULL, NULL),
('Project Zomboid', 8.90, 'JOGO', '2013-11-08', 7, 'Sobrevivencia', 'PC', NULL, NULL),
('RimWorld', 12.90, 'JOGO', '2018-10-17', 5, 'Simulacao', 'PC', NULL, NULL),
('Cult of the Lamb', 11.90, 'JOGO', '2022-08-11', 6, 'Roguelite', 'PC', NULL, NULL),
('Don''t Starve Together', 7.90, 'JOGO', '2016-04-21', 8, 'Sobrevivencia', 'PC', NULL, NULL),
('Cyberpunk 2077', 15.90, 'JOGO', '2020-12-09', 5, 'RPG', 'PC', NULL, NULL),
('Among Us', 4.90, 'JOGO', '2018-11-16', 15, 'Party Game', 'PC', NULL, NULL),
('The Witcher 3: Wild Hunt', 12.90, 'JOGO', '2015-05-18', 7, 'RPG', 'PC', NULL, NULL),
('Elden Ring', 16.90, 'JOGO', '2022-02-24', 5, 'Action RPG', 'PC', NULL, NULL),
('Sekiro: Shadows Die Twice', 13.90, 'JOGO', '2019-03-21', 5, 'Acao', 'PC', NULL, NULL),
('DARK SOULS III', 12.90, 'JOGO', '2016-04-11', 5, 'Action RPG', 'PC', NULL, NULL),
('Resident Evil 4', 15.90, 'JOGO', '2023-03-23', 5, 'Survival Horror', 'PC', NULL, NULL),
('Resident Evil Village', 13.90, 'JOGO', '2021-05-06', 5, 'Survival Horror', 'PC', NULL, NULL),
('Resident Evil 2', 12.90, 'JOGO', '2019-01-24', 6, 'Survival Horror', 'PC', NULL, NULL),
('Devil May Cry 5', 12.90, 'JOGO', '2019-03-07', 6, 'Hack and Slash', 'PC', NULL, NULL),
('Monster Hunter: World', 11.90, 'JOGO', '2018-08-08', 6, 'Action RPG', 'PC', NULL, NULL),
('Persona 5 Royal', 14.90, 'JOGO', '2022-10-20', 5, 'JRPG', 'PC', NULL, NULL),
('Sid Meier''s Civilization VI', 9.90, 'JOGO', '2016-10-20', 6, 'Estrategia', 'PC', NULL, NULL),
('NieR:Automata', 12.90, 'JOGO', '2017-03-17', 5, 'Action RPG', 'PC', NULL, NULL),
('Factorio', 11.90, 'JOGO', '2020-08-14', 5, 'Automacao', 'PC', NULL, NULL),
('Valheim', 10.90, 'JOGO', '2021-02-02', 7, 'Sobrevivencia', 'PC', NULL, NULL),
('Fallout 4', 10.90, 'JOGO', '2015-11-09', 6, 'RPG', 'PC', NULL, NULL),
('The Elder Scrolls V: Skyrim Special Edition', 11.90, 'JOGO', '2016-10-27', 6, 'RPG', 'PC', NULL, NULL),
('Red Dead Redemption 2', 15.90, 'JOGO', '2019-12-05', 5, 'Acao', 'PC', NULL, NULL);

-- =====================================================
-- CONSOLES INICIAIS
-- Aqui usamos os mesmos campos basicos do produto.
-- =====================================================
INSERT INTO produto (nome, preco, tipo, data_lancamento, quantidade, genero, plataforma, marca, modelo) VALUES
('PlayStation 5', 25.90, 'CONSOLE', '2020-11-12', 3, NULL, NULL, 'Sony', 'Slim'),
('Xbox Series S', 21.90, 'CONSOLE', '2020-11-10', 4, NULL, NULL, 'Microsoft', '512GB'),
('Nintendo Switch', 19.90, 'CONSOLE', '2017-03-03', 4, NULL, NULL, 'Nintendo', 'Padrao');

-- =====================================================
-- ALUGUEIS DE EXEMPLO
-- Os ids abaixo consideram que:
-- - os clientes foram inseridos primeiro
-- - os jogos comecam no id 1
-- - os consoles foram inseridos depois dos 50 jogos
-- =====================================================
INSERT INTO aluguel (cliente_id, produto_id, data_aluguel, data_devolucao) VALUES
(1, 1, '2026-04-01', '2026-04-04'),
(2, 14, '2026-04-05', '2026-04-08'),
(3, 51, '2026-04-09', '2026-04-11');
