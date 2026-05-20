package service;

import dao.FuncionarioDAO;
import model.Funcionario;
import util.ValidationUtils;

public class FuncionarioService {

    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    public void salvar(Funcionario funcionario) {

        validar(funcionario);
        funcionarioDAO.salvar(funcionario);
    }

    private void validar(Funcionario funcionario) {

        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionario invalido.");
        }

        if (ValidationUtils.isBlank(funcionario.getNome())) {
            throw new IllegalArgumentException("Informe o nome do funcionario.");
        }

        if (!ValidationUtils.isValidCpf(funcionario.getCpf())) {
            throw new IllegalArgumentException("CPF invalido.");
        }

        if (!ValidationUtils.isValidPhone(funcionario.getTelefone())) {
            throw new IllegalArgumentException("Telefone invalido.");
        }

        if (ValidationUtils.isBlank(funcionario.getCargo())) {
            throw new IllegalArgumentException("Informe o cargo do funcionario.");
        }

        if (ValidationUtils.isBlank(funcionario.getUsuario())
                || funcionario.getUsuario().trim().length() < 4) {
            throw new IllegalArgumentException("O usuario deve ter ao menos 4 caracteres.");
        }

        if (ValidationUtils.isBlank(funcionario.getSenha())
                || funcionario.getSenha().trim().length() < 3) {
            throw new IllegalArgumentException("A senha deve ter ao menos 3 caracteres.");
        }
    }
}
