package service;

import dao.ConsoleDAO;
import model.Console;
import util.ValidationUtils;

public class ConsoleService {

    private final ConsoleDAO consoleDAO = new ConsoleDAO();

    public void salvar(Console console) {

        validar(console);
        consoleDAO.salvar(console);
    }

    private void validar(Console console) {

        if (console == null) {
            throw new IllegalArgumentException("Console invalido.");
        }

        if (ValidationUtils.isBlank(console.getNome())) {
            throw new IllegalArgumentException("Informe o nome do console.");
        }

        if (ValidationUtils.isBlank(console.getMarca())) {
            throw new IllegalArgumentException("Informe a marca do console.");
        }

        if (ValidationUtils.isBlank(console.getGeracao())) {
            throw new IllegalArgumentException("Informe a geracao do console.");
        }

        if (console.getQuantidade() < 0) {
            throw new IllegalArgumentException("A quantidade nao pode ser negativa.");
        }
    }
}
