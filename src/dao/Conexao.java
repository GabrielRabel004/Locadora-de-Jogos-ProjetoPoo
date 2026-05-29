package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL =
            "jdbc:sqlserver://10.109.8.9:1433;"
            + "databaseName=POO_gp03;"
            + "user=POO_gp03;"
            + "password=;"
            + "encrypt=false;"
            + "trustServerCertificate=true;"
            + "loginTimeout=30;";

    public static Connection conexao;

    public static Connection conectar() throws SQLException {

        conexao = DriverManager.getConnection(URL);
        return conexao;
    }

    public static void desconectar() {

        try {

            if (conexao != null) {

                conexao.close();
                conexao = null;
            }

        } catch (SQLException ex) {
            conexao = null;
        }
    }
}
