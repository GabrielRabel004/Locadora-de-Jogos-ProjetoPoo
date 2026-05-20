package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Funcionario;

public class FuncionarioDAO {

    public void salvar(Funcionario funcionario) {

        String sql =
                "INSERT INTO funcionarios "
                + "(nome, cpf, telefone, cargo, usuario, senha) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try {

            Connection connection = DatabaseSupport.getConnection();

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(1, funcionario.getNome());
                statement.setString(2, funcionario.getCpf());
                statement.setString(3, funcionario.getTelefone());
                statement.setString(4, funcionario.getCargo());
                statement.setString(5, funcionario.getUsuario());
                statement.setString(6, funcionario.getSenha());
                statement.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new IllegalStateException(
                    "Nao foi possivel salvar o funcionario no banco.",
                    ex
            );
        }
    }

    public boolean autenticar(String usuario, String senha) {

        String sql =
                "SELECT COUNT(1) AS total "
                + "FROM funcionarios WHERE usuario = ? AND senha = ?";

        try {

            Connection connection = DatabaseSupport.getConnection();

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, usuario);
                statement.setString(2, senha);

                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next() && resultSet.getInt("total") > 0;
                }
            }

        } catch (SQLException ex) {
            throw new IllegalStateException(
                    "Nao foi possivel validar o login do funcionario.",
                    ex
            );
        }
    }
}
