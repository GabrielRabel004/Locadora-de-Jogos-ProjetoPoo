package model;

public class Funcionario {

    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String cargo;
    private String usuario;
    private String senha;

    // CONSTRUTOR VAZIO
    public Funcionario() {

    }

    // CONSTRUTOR COMPLETO
    public Funcionario(int id, String nome, String cpf,
                       String telefone, String cargo,
                       String usuario, String senha) {

        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.cargo = cargo;
        this.usuario = usuario;
        this.senha = senha;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}