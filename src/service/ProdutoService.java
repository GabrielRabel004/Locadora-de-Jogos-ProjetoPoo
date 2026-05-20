package service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import dao.ConsoleDAO;
import dao.JogoDAO;
import model.Console;
import model.Jogo;
import model.ProdutoResumo;

public class ProdutoService {

    private final JogoDAO jogoDAO = new JogoDAO();
    private final ConsoleDAO consoleDAO = new ConsoleDAO();
    private final DecimalFormat currencyFormat = new DecimalFormat("0.00");

    public List<ProdutoResumo> listarProdutos() {

        List<ProdutoResumo> produtos = new ArrayList<>();

        for (Jogo jogo : jogoDAO.listar()) {

            produtos.add(new ProdutoResumo(
                    jogo.getNome(),
                    "Jogo",
                    jogo.getGenero() + " / " + jogo.getPlataforma(),
                    "R$ " + currencyFormat.format(jogo.getPreco()),
                    "-"
            ));
        }

        for (Console console : consoleDAO.listar()) {

            produtos.add(new ProdutoResumo(
                    console.getNome(),
                    "Console",
                    console.getMarca() + " / " + console.getGeracao(),
                    "-",
                    Integer.toString(console.getQuantidade())
            ));
        }

        produtos.sort(Comparator.comparing(
                ProdutoResumo::getNome,
                String.CASE_INSENSITIVE_ORDER
        ));

        return produtos;
    }
}
