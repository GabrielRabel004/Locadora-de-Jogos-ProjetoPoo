package dao;

import java.sql.Connection;
import java.sql.SQLException;

public final class DatabaseSupport {

    private DatabaseSupport() {
    }

    public static Connection getConnection() throws SQLException {

        if (Conexao.conexao != null && !Conexao.conexao.isClosed()) {
            return Conexao.conexao;
        }

        Connection connection = Conexao.conectar();

        if (connection == null) {
            throw new SQLException("Nao foi possivel obter conexao com o banco.");
        }

        return connection;
    }
}
