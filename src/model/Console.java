package model;

public class Console {

    private int id;
    private String nome;
    private String marca;
    private String geracao;
    private int quantidade;

    // CONSTRUTOR VAZIO
    public Console() {

    }

    // CONSTRUTOR COMPLETO
    public Console(int id, String nome,
                   String marca, String geracao,
                   int quantidade) {

        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.geracao = geracao;
        this.quantidade = quantidade;
    }

    // GETTERS E SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getGeracao() {
        return geracao;
    }

    public void setGeracao(String geracao) {
        this.geracao = geracao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}