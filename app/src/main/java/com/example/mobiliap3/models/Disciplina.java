package com.example.mobiliap3.models;

public class Disciplina {
    private int id;
    private String codigo;
    private String nome;
    private int creditos;
    private double cargaHoraria;

    public Disciplina() {}

    public Disciplina(String codigo, String nome, int creditos, double cargaHoraria) {
        this.codigo = codigo;
        this.nome = nome;
        this.creditos = creditos;
        this.cargaHoraria = cargaHoraria;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getCreditos() { return creditos; }
    public void setCreditos(int creditos) { this.creditos = creditos; }

    public double getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(double cargaHoraria) { this.cargaHoraria = cargaHoraria; }
}
