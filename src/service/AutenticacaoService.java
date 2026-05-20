package service;

import dao.DatabaseSupport;
import dao.FuncionarioDAO;

public class AutenticacaoService {

    private static final String LEGACY_USER = "admin";
    private static final String LEGACY_PASSWORD = "123";

    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    public boolean autenticar(String usuario, String senha) {

        if (usuario == null || usuario.isBlank()
                || senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("Informe usuario e senha.");
        }

        try {
            DatabaseSupport.getConnection();
        } catch (Exception ex) {
            throw new IllegalStateException(
                    "Nao foi possivel conectar ao banco de dados.",
                    ex
            );
        }

        if (LEGACY_USER.equals(usuario) && LEGACY_PASSWORD.equals(senha)) {
            return true;
        }

        return funcionarioDAO.autenticar(usuario, senha);
    }
}
