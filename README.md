# Locadora Games

Projeto desktop em JavaFX para uma locadora de games, com interface grafica moderna sobre uma base legada em SQL Server. O sistema faz autenticacao de funcionarios e oferece telas de cadastro e consulta para jogos, consoles, clientes e funcionarios.

## O que o projeto faz

- Tela de login com acesso por `admin / 123` ou por funcionarios cadastrados no banco.
- Menu principal com painel resumido do catalogo.
- Consulta unificada de produtos, juntando jogos e consoles.
- Cadastro de jogos com validacao de nome, genero, plataforma, ano e preco.
- Cadastro de consoles com validacao de nome, marca, geracao e quantidade.
- Cadastro de clientes com validacao de CPF, telefone, email e maioridade.
- Cadastro de funcionarios com validacao de CPF, telefone, usuario e senha.

## Estrutura

- `src/view`: telas JavaFX, classe principal e arquivo `script.sql`.
- `src/service`: regras de negocio e validacoes antes de gravar no banco.
- `src/dao`: acesso ao SQL Server via JDBC.
- `src/model`: entidades do sistema.
- `src/util`: validacoes auxiliares e alertas.

## Tecnologias e requisitos

- Java 17
- JavaFX 17
- SQL Server
- Driver JDBC da Microsoft para SQL Server
- Eclipse

Observacao: o projeto nao usa Maven nem Gradle. A configuracao atual depende do `.classpath` do Eclipse.

## Banco de dados

A conexao fica em `src/dao/Conexao.java` e aponta para um SQL Server remoto. O script base do banco esta em `src/view/script.sql`.

O script cria, se necessario:

- `funcionarios`
- `jogos`
- `consoles`
- `clientes`

Ele tambem insere o usuario legado:

- usuario: `admin`
- senha: `123`

## Como executar

1. Importe o projeto no Eclipse como projeto Java existente.
2. Garanta que a biblioteca `FX17` esteja configurada no Eclipse.
3. Ajuste o caminho do driver JDBC no `.classpath`, se necessario. Hoje ele aponta para `H:/mssql-jdbc-12.10.0.jre11.jar`.
4. Revise a string de conexao em `src/dao/Conexao.java`.
5. Execute o script `src/view/script.sql` no SQL Server, se o banco ainda nao estiver preparado.
6. Rode a classe principal `view.Principal`.

## Observacoes

- O projeto esta organizado em camadas `view`, `service`, `dao` e `model`.
- A classe `Locacao` existe no projeto, mas o fluxo de locacao ainda nao esta implementado nas telas.
- A conexao JDBC e mantida de forma simples, com abertura sob demanda e fechamento ao encerrar a sessao.
