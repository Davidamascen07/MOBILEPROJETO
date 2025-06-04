package com.example.mobiliap3.models;

public class Historico {
    private String periodo;           // "6º PERÍODO"
    private String disciplinaNome;    // Nome da disciplina
    private String periodoLetivo;     // "2024.1"
    private String situacao;          // "APROVADO", "REPROVADO", "CURSANDO"
    private double notaFinal;
    private int totalFaltas;

    public Historico() {}

    public Historico(String periodo, String disciplinaNome, String periodoLetivo, String situacao, double notaFinal, int totalFaltas) {
        this.periodo = periodo;
        this.disciplinaNome = disciplinaNome;
        this.periodoLetivo = periodoLetivo;
        this.situacao = situacao;
        this.notaFinal = notaFinal;
        this.totalFaltas = totalFaltas;
    }

    // Getters e Setters
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
}
