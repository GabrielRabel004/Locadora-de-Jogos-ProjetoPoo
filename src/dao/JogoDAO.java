package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Jogo;

public class JogoDAO {

    public List<Jogo> listar() {

        List<Jogo> jogos = new ArrayList<>();

        String sql =
                "SELECT id, nome, genero, ano, preco, plataforma "
                + "FROM jogos ORDER BY nome";

        try {

            Connection connection = DatabaseSupport.getConnection();

            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Jogo jogo = new Jogo();
                    jogo.setId(resultSet.getInt("id"));
                    jogo.setNome(resultSet.getString("nome"));
                    jogo.setGenero(resultSet.getString("genero"));
                    jogo.setAno(resultSet.getInt("ano"));
                    jogo.setPreco(resultSet.getDouble("preco"));
                    jogo.setPlataforma(resultSet.getString("plataforma"));

                    jogos.add(jogo);
                }
            }

            return jogos;

        } catch (SQLException ex) {
            throw new IllegalStateException(
                    "Nao foi possivel carregar os jogos.",
                    ex
            );
        }
    }

    public void salvar(Jogo jogo) {

        String sql =
                "INSERT INTO jogos "
                + "(nome, genero, ano, preco, plataforma) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {

            Connection connection = DatabaseSupport.getConnection();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(1, jogo.getNome());
                statement.setString(2, jogo.getGenero());
                statement.setInt(3, jogo.getAno());
                statement.setDouble(4, jogo.getPreco());
                statement.setString(5, jogo.getPlataforma());
                statement.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new IllegalStateException(
                    "Nao foi possivel salvar o jogo no banco.",
                    ex
            );
        }
    }
}
