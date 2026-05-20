package model;

public class ProdutoResumo {

    private final String nome;
    private final String categoria;
    private final String detalhe;
    private final String preco;
    private final String estoque;

    public ProdutoResumo(String nome, String categoria,
                         String detalhe, String preco, String estoque) {

        this.nome = nome;
        this.categoria = categoria;
        this.detalhe = detalhe;
        this.preco = preco;
        this.estoque = estoque;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public String getPreco() {
        return preco;
    }

    public String getEstoque() {
        return estoque;
    }

    public String getTextoBusca() {
        return (nome + " " + categoria + " " + detalhe + " " + preco + " " + estoque)
                .toLowerCase();
    }
}
