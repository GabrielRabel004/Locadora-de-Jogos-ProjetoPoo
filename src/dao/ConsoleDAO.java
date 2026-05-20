package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Console;

public class ConsoleDAO {

    public List<Console> listar() {

        List<Console> consoles = new ArrayList<>();

        String sql =
                "SELECT id, nome, marca, geracao, quantidade "
                + "FROM consoles ORDER BY nome";

        try {

            Connection connection = DatabaseSupport.getConnection();

            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Console console = new Console();
                    console.setId(resultSet.getInt("id"));
                    console.setNome(resultSet.getString("nome"));
                    console.setMarca(resultSet.getString("marca"));
                    console.setGeracao(resultSet.getString("geracao"));
                    console.setQuantidade(resultSet.getInt("quantidade"));

                    consoles.add(console);
                }
            }

            return consoles;

        } catch (SQLException ex) {
            throw new IllegalStateException(
                    "Nao foi possivel carregar os consoles.",
                    ex
            );
        }
    }

    public void salvar(Console console) {

        String sql =
                "INSERT INTO consoles "
                + "(nome, marca, geracao, quantidade) "
                + "VALUES (?, ?, ?, ?)";

        try {

            Connection connection = DatabaseSupport.getConnection();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(1, console.getNome());
                statement.setString(2, console.getMarca());
                statement.setString(3, console.getGeracao());
                statement.setInt(4, console.getQuantidade());
                statement.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new IllegalStateException(
                    "Nao foi possivel salvar o console no banco.",
                    ex
            );
        }
    }
}
