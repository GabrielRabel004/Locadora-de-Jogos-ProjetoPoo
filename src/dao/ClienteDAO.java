package dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import model.Cliente;

public class ClienteDAO {

    public void salvar(Cliente cliente) {

        try {

            Connection connection = DatabaseSupport.getConnection();
            String tableName = resolveTableName(connection);
            String birthDateColumn = resolveBirthDateColumn(connection, tableName);

            String sql =
                    "INSERT INTO " + tableName
                    + " (nome, " + birthDateColumn + ", cpf, telefone, email) "
                    + "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, cliente.getNome());
                statement.setDate(2, Date.valueOf(cliente.getDataNascimento()));
                statement.setString(3, cliente.getCpf());
                statement.setString(4, cliente.getTelefone());
                statement.setString(5, cliente.getEmail());
                statement.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new IllegalStateException(
                    "Nao foi possivel salvar o cliente no banco.",
                    ex
            );
        }
    }

    public List<Cliente> listar() {

        List<Cliente> clientes = new ArrayList<>();

        try {

            Connection connection = DatabaseSupport.getConnection();
            String tableName = resolveTableName(connection);
            String birthDateColumn = resolveBirthDateColumn(connection, tableName);

            String sql =
                    "SELECT id, nome, " + birthDateColumn
                    + ", cpf, telefone, email "
                    + "FROM " + tableName + " ORDER BY nome";

            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    clientes.add(mapCliente(resultSet, birthDateColumn));
                }
            }

            return clientes;

        } catch (SQLException ex) {
            throw new IllegalStateException(
                    "Nao foi possivel carregar os clientes.",
                    ex
            );
        }
    }

    public List<Cliente> buscarClientes(String termo) {

        if (termo == null || termo.trim().isEmpty()) {
            return listar();
        }

        List<Cliente> clientes = new ArrayList<>();

        try {

            Connection connection = DatabaseSupport.getConnection();
            String tableName = resolveTableName(connection);
            String birthDateColumn = resolveBirthDateColumn(connection, tableName);

            String termoLimpo = termo.trim();
            String cpfDigits = onlyDigits(termoLimpo);
            Integer idPesquisado = parseId(termoLimpo);

            String sql =
                    "SELECT id, nome, " + birthDateColumn
                    + ", cpf, telefone, email "
                    + "FROM " + tableName
                    + " WHERE LOWER(nome) LIKE ? "
                    + "OR cpf LIKE ? "
                    + "OR REPLACE(REPLACE(REPLACE(cpf, '.', ''), '-', ''), ' ', '') LIKE ? ";

            if (idPesquisado != null) {
                sql += "OR id = ? ";
            }

            sql += "ORDER BY nome";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, "%" + termoLimpo.toLowerCase(Locale.ROOT) + "%");
                statement.setString(2, "%" + termoLimpo + "%");
                statement.setString(3, "%" + cpfDigits + "%");

                if (idPesquisado != null) {
                    statement.setInt(4, idPesquisado);
                }

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        clientes.add(mapCliente(resultSet, birthDateColumn));
                    }
                }
            }

            return clientes;

        } catch (SQLException ex) {
            throw new IllegalStateException(
                    "Nao foi possivel pesquisar clientes.",
                    ex
            );
        }
    }

    public List<Cliente> buscarPorNome(String nome) {

        List<Cliente> clientes = new ArrayList<>();

        try {

            Connection connection = DatabaseSupport.getConnection();
            String tableName = resolveTableName(connection);
            String birthDateColumn = resolveBirthDateColumn(connection, tableName);

            String sql =
                    "SELECT id, nome, " + birthDateColumn
                    + ", cpf, telefone, email "
                    + "FROM " + tableName
                    + " WHERE LOWER(nome) LIKE ? ORDER BY nome";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, "%" + nome.trim().toLowerCase(Locale.ROOT) + "%");

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        clientes.add(mapCliente(resultSet, birthDateColumn));
                    }
                }
            }

            return clientes;

        } catch (SQLException ex) {
            throw new IllegalStateException(
                    "Nao foi possivel pesquisar clientes por nome.",
                    ex
            );
        }
    }

    public Cliente buscarPorId(int id) {

        try {

            Connection connection = DatabaseSupport.getConnection();
            String tableName = resolveTableName(connection);
            String birthDateColumn = resolveBirthDateColumn(connection, tableName);

            String sql =
                    "SELECT id, nome, " + birthDateColumn
                    + ", cpf, telefone, email "
                    + "FROM " + tableName + " WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, id);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapCliente(resultSet, birthDateColumn);
                    }
                }
            }

            return null;

        } catch (SQLException ex) {
            throw new IllegalStateException(
                    "Nao foi possivel buscar o cliente.",
                    ex
            );
        }
    }

    public Cliente buscarPorCpf(String cpf) {

        try {

            Connection connection = DatabaseSupport.getConnection();
            String tableName = resolveTableName(connection);
            String birthDateColumn = resolveBirthDateColumn(connection, tableName);
            String cpfDigits = onlyDigits(cpf);

            String sql =
                    "SELECT id, nome, " + birthDateColumn
                    + ", cpf, telefone, email "
                    + "FROM " + tableName
                    + " WHERE cpf = ? "
                    + "OR REPLACE(REPLACE(REPLACE(cpf, '.', ''), '-', ''), ' ', '') = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, cpf);
                statement.setString(2, cpfDigits);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapCliente(resultSet, birthDateColumn);
                    }
                }
            }

            return null;

        } catch (SQLException ex) {
            throw new IllegalStateException(
                    "Nao foi possivel buscar o cliente por CPF.",
                    ex
            );
        }
    }

    public void atualizar(Cliente cliente) {

        try {

            Connection connection = DatabaseSupport.getConnection();
            String tableName = resolveTableName(connection);
            String birthDateColumn = resolveBirthDateColumn(connection, tableName);

            String sql =
                    "UPDATE " + tableName
                    + " SET nome = ?, " + birthDateColumn + " = ?, cpf = ?, "
                    + "telefone = ?, email = ? WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, cliente.getNome());
                statement.setDate(2, Date.valueOf(cliente.getDataNascimento()));
                statement.setString(3, cliente.getCpf());
                statement.setString(4, cliente.getTelefone());
                statement.setString(5, cliente.getEmail());
                statement.setInt(6, cliente.getId());

                int rows = statement.executeUpdate();

                if (rows == 0) {
                    throw new IllegalStateException("Cliente nao encontrado para atualizar.");
                }
            }

        } catch (SQLException ex) {
            throw new IllegalStateException(
                    "Nao foi possivel atualizar o cliente.",
                    ex
            );
        }
    }

    public void excluir(int id) {

        try {

            Connection connection = DatabaseSupport.getConnection();
            String tableName = resolveTableName(connection);

            String sql = "DELETE FROM " + tableName + " WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, id);
                int rows = statement.executeUpdate();

                if (rows == 0) {
                    throw new IllegalStateException("Cliente nao encontrado para excluir.");
                }
            }

        } catch (SQLException ex) {
            throw new IllegalStateException(
                    "Nao foi possivel excluir o cliente.",
                    ex
            );
        }
    }

    private Cliente mapCliente(ResultSet resultSet, String birthDateColumn)
            throws SQLException {

        Cliente cliente = new Cliente();
        cliente.setId(resultSet.getInt("id"));
        cliente.setNome(resultSet.getString("nome"));
        cliente.setCpf(resultSet.getString("cpf"));
        cliente.setTelefone(resultSet.getString("telefone"));
        cliente.setEmail(resultSet.getString("email"));

        Date birthDate = resultSet.getDate(birthDateColumn);

        if (birthDate != null) {
            cliente.setDataNascimento(birthDate.toLocalDate());
        }

        return cliente;
    }

    private String onlyDigits(String value) {

        if (value == null) {
            return "";
        }

        return value.replaceAll("\\D", "");
    }

    private Integer parseId(String value) {

        if (value == null || !value.matches("\\d{1,9}")) {
            return null;
        }

        return Integer.valueOf(value);
    }

    private String resolveTableName(Connection connection) throws SQLException {

        DatabaseMetaData metaData = connection.getMetaData();

        for (String candidate : new String[] {"clientes", "cliente"}) {
            if (tableExists(metaData, candidate)) {
                return candidate;
            }
        }

        throw new SQLException(
                "Tabela de clientes nao encontrada. Esperado: clientes ou cliente."
        );
    }

    private boolean tableExists(DatabaseMetaData metaData, String tableName)
            throws SQLException {

        String[] candidates = {
                tableName,
                tableName.toUpperCase(Locale.ROOT),
                tableName.toLowerCase(Locale.ROOT)
        };

        for (String candidate : candidates) {
            try (ResultSet tables =
                         metaData.getTables(null, null, candidate, new String[] {"TABLE"})) {
                if (tables.next()) {
                    return true;
                }
            }
        }

        return false;
    }

    private String resolveBirthDateColumn(Connection connection, String tableName)
            throws SQLException {

        Set<String> columns = new HashSet<>();

        try (ResultSet resultSet =
                     connection.getMetaData().getColumns(null, null, tableName, null)) {

            while (resultSet.next()) {
                columns.add(resultSet.getString("COLUMN_NAME").toLowerCase(Locale.ROOT));
            }
        }

        if (columns.contains("data_nascimento")) {
            return "data_nascimento";
        }

        if (columns.contains("nascimento")) {
            return "nascimento";
        }

        throw new SQLException(
                "Coluna de data de nascimento nao encontrada na tabela " + tableName + "."
        );
    }
}
