package service;

import java.util.List;

import dao.ClienteDAO;
import model.Cliente;
import util.ValidationUtils;

public class ClienteService {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    public void salvar(Cliente cliente) {

        validar(cliente);
        clienteDAO.salvar(cliente);
    }

    public List<Cliente> listar() {
        return clienteDAO.listar();
    }

    public List<Cliente> buscarClientes(String termo) {
        return clienteDAO.buscarClientes(termo);
    }

    public List<Cliente> buscarPorNome(String nome) {

        if (ValidationUtils.isBlank(nome)) {
            throw new IllegalArgumentException("Informe um nome para pesquisar.");
        }

        return clienteDAO.buscarPorNome(nome);
    }

    public Cliente buscarPorId(int id) {

        validarId(id);
        return clienteDAO.buscarPorId(id);
    }

    public Cliente buscarPorCpf(String cpf) {

        if (ValidationUtils.isBlank(cpf)) {
            throw new IllegalArgumentException("Informe um CPF para pesquisar.");
        }

        return clienteDAO.buscarPorCpf(cpf);
    }

    public void atualizar(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente invalido.");
        }

        validarId(cliente.getId());
        validar(cliente);
        clienteDAO.atualizar(cliente);
    }

    public void excluir(int id) {

        validarId(id);
        clienteDAO.excluir(id);
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

    private void validarId(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException("Selecione um cliente cadastrado.");
        }
    }
}
