package model;

import java.time.LocalDate;

public class Locacao {

    private int id;
    private String cliente;
    private String jogo;
    private LocalDate dataLocacao;
    private LocalDate dataDevolucao;
    private double valor;

    // CONSTRUTOR VAZIO
    public Locacao() {

    }

    // CONSTRUTOR COMPLETO
    public Locacao(int id, String cliente,
                   String jogo,
                   LocalDate dataLocacao,
                   LocalDate dataDevolucao,
                   double valor) {

        this.id = id;
        this.cliente = cliente;
        this.jogo = jogo;
        this.dataLocacao = dataLocacao;
        this.dataDevolucao = dataDevolucao;
        this.valor = valor;
    }

    // GETTERS E SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getJogo() {
        return jogo;
    }

    public void setJogo(String jogo) {
        this.jogo = jogo;
    }

    public LocalDate getDataLocacao() {
        return dataLocacao;
    }

    public void setDataLocacao(LocalDate dataLocacao) {
        this.dataLocacao = dataLocacao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}