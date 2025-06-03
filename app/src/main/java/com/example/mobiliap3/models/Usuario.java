package com.example.mobiliap3.models;

public class Usuario {
    private int id;
    private String nome;
    private String matricula;
    private String curso;
    private String senha;

    public Usuario() {}

    public Usuario(String nome, String matricula, String curso, String senha) {
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
        this.senha = senha;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
