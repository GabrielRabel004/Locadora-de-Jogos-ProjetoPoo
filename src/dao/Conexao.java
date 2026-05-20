package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

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

    public static Connection conectar() {

        try {

            conexao = DriverManager.getConnection(URL);

            JOptionPane.showMessageDialog(
                    null,
                    "Conexao realizada com sucesso!"
            );

            return conexao;

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    null,
                    "Erro de conexao!\nERRO: " + ex.getMessage()
            );

            return null;
        }
    }

    public static void desconectar() {

        try {

            if (conexao != null) {

                conexao.close();
                conexao = null;

                JOptionPane.showMessageDialog(
                        null,
                        "Conexao fechada com sucesso!"
                );
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao fechar conexao!\nERRO: " + ex.getMessage()
            );
        }
    }
}
