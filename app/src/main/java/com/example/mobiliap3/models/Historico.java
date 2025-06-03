package com.example.mobiliap3.models;

public class Historico {
    private int id;
    private String periodo;
    private String disciplinaNome;
    private String periodoLetivo;
    private String situacao;
    private double notaFinal;
    private int totalFaltas;
    private double chIntegralizada;

    public Historico() {}

    public Historico(String periodo, String disciplinaNome, String periodoLetivo, String situacao, double notaFinal, int totalFaltas) {
        this.periodo = periodo;
        this.disciplinaNome = disciplinaNome;
        this.periodoLetivo = periodoLetivo;
        this.situacao = situacao;
        this.notaFinal = notaFinal;
        this.totalFaltas = totalFaltas;
        this.chIntegralizada = 75.0; // Padrão
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public String getDisciplinaNome() { return disciplinaNome; }
    public void setDisciplinaNome(String disciplinaNome) { this.disciplinaNome = disciplinaNome; }

    public String getPeriodoLetivo() { return periodoLetivo; }
    public void setPeriodoLetivo(String periodoLetivo) { this.periodoLetivo = periodoLetivo; }

    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }

    public double getNotaFinal() { return notaFinal; }
    public void setNotaFinal(double notaFinal) { this.notaFinal = notaFinal; }

    public int getTotalFaltas() { return totalFaltas; }
    public void setTotalFaltas(int totalFaltas) { this.totalFaltas = totalFaltas; }

    public double getChIntegralizada() { return chIntegralizada; }
    public void setChIntegralizada(double chIntegralizada) { this.chIntegralizada = chIntegralizada; }
}
