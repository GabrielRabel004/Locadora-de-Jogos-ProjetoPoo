package service;

import dao.ClienteDAO;
import model.Cliente;
import util.ValidationUtils;

public class ClienteService {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    public void salvar(Cliente cliente) {

        validar(cliente);
        clienteDAO.salvar(cliente);
    }

    private void validar(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente invalido.");
        }

        if (ValidationUtils.isBlank(cliente.getNome())) {
            throw new IllegalArgumentException("Informe o nome do cliente.");
        }

        if (cliente.getDataNascimento() == null) {
            throw new IllegalArgumentException("Informe a data de nascimento.");
        }

        if (!ValidationUtils.isAdult(cliente.getDataNascimento())) {
            throw new IllegalArgumentException("Nao e permitido cadastrar menores de idade.");
        }

        if (!ValidationUtils.isValidCpf(cliente.getCpf())) {
            throw new IllegalArgumentException("CPF invalido.");
        }

        if (!ValidationUtils.isValidPhone(cliente.getTelefone())) {
            throw new IllegalArgumentException("Telefone invalido.");
        }

        if (!ValidationUtils.isValidEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Email invalido.");
        }
    }
}
