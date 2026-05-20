/*
    Script incremental para SQL Server 2014.
    Objetivo:
    - manter compatibilidade com a conexao legada;
    - evitar DROP de tabelas;
    - criar apenas o que nao existir.

    Observacao:
    ajuste o contexto do banco antes de executar, se necessario.
*/

IF OBJECT_ID('dbo.funcionarios', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.funcionarios (
        id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        nome VARCHAR(120) NOT NULL,
        cpf VARCHAR(14) NOT NULL,
        telefone VARCHAR(20) NOT NULL,
        cargo VARCHAR(60) NOT NULL,
        usuario VARCHAR(60) NOT NULL,
        senha VARCHAR(120) NOT NULL
    );

    CREATE UNIQUE INDEX UX_funcionarios_cpf ON dbo.funcionarios(cpf);
    CREATE UNIQUE INDEX UX_funcionarios_usuario ON dbo.funcionarios(usuario);
END;

IF OBJECT_ID('dbo.jogos', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.jogos (
        id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        nome VARCHAR(120) NOT NULL,
        genero VARCHAR(60) NOT NULL,
        ano INT NOT NULL,
        preco DECIMAL(10,2) NOT NULL,
        plataforma VARCHAR(60) NOT NULL
    );
END;

IF OBJECT_ID('dbo.consoles', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.consoles (
        id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        nome VARCHAR(120) NOT NULL,
        marca VARCHAR(60) NOT NULL,
        geracao VARCHAR(60) NOT NULL,
        quantidade INT NOT NULL DEFAULT 0
    );
END;

IF OBJECT_ID('dbo.clientes', 'U') IS NULL AND OBJECT_ID('dbo.cliente', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.clientes (
        id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        nome VARCHAR(120) NOT NULL,
        data_nascimento DATE NOT NULL,
        cpf VARCHAR(14) NOT NULL,
        telefone VARCHAR(20) NOT NULL,
        email VARCHAR(120) NOT NULL
    );

    CREATE UNIQUE INDEX UX_clientes_cpf ON dbo.clientes(cpf);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.funcionarios WHERE usuario = 'admin')
BEGIN
    INSERT INTO dbo.funcionarios (nome, cpf, telefone, cargo, usuario, senha)
    VALUES ('Administrador', '52998224725', '(11)99999-9999', 'Gerente', 'admin', '123');
END;
