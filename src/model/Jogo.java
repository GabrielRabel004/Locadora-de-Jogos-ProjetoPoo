package model;

public class Jogo {

    private int id;
    private String nome;
    private String genero;
    private int ano;
    private double preco;
    private String plataforma;

    // CONSTRUTOR VAZIO
    public Jogo() {

    }

    // CONSTRUTOR COMPLETO
    public Jogo(int id, String nome, String genero,
                 int ano, double preco, String plataforma) {

        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.ano = ano;
        this.preco = preco;
        this.plataforma = plataforma;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }
}