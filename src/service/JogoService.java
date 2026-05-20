package service;

import java.time.LocalDate;

import dao.JogoDAO;
import model.Jogo;
import util.ValidationUtils;

public class JogoService {

    private final JogoDAO jogoDAO = new JogoDAO();

    public void salvar(Jogo jogo) {

        validar(jogo);
        jogoDAO.salvar(jogo);
    }

    private void validar(Jogo jogo) {

        if (jogo == null) {
            throw new IllegalArgumentException("Jogo invalido.");
        }

        if (ValidationUtils.isBlank(jogo.getNome())) {
            throw new IllegalArgumentException("Informe o nome do jogo.");
        }

        if (ValidationUtils.isBlank(jogo.getGenero())) {
            throw new IllegalArgumentException("Informe o genero do jogo.");
        }

        if (ValidationUtils.isBlank(jogo.getPlataforma())) {
            throw new IllegalArgumentException("Informe a plataforma.");
        }

        int currentYear = LocalDate.now().getYear() + 1;

        if (jogo.getAno() < 1970 || jogo.getAno() > currentYear) {
            throw new IllegalArgumentException("Informe um ano valido.");
        }

        if (jogo.getPreco() < 0) {
            throw new IllegalArgumentException("O preco nao pode ser negativo.");
        }
    }
}
