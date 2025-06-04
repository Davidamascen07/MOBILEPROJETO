package com.example.mobiliap3.models;

public class Historico {
    private int id;
    private int disciplinaId; // ADICIONAR para facilitar navegação
    private String disciplinaNome; // DEVE SER STRING para armazenar o nome da disciplina
    private String periodo;
    private String situacao;
    private double notaFinal;
    private int totalFaltas;
    private double frequencia;

    // Construtores
    public Historico() {}

    public Historico(int id, String disciplinaNome, String periodo, String situacao, 
                    double notaFinal, int totalFaltas, double frequencia) {
        this.id = id;
        this.disciplinaNome = disciplinaNome;
        this.periodo = periodo;
        this.situacao = situacao;
        this.notaFinal = notaFinal;
        this.totalFaltas = totalFaltas;
        this.frequencia = frequencia;
    }

    // ADICIONAR construtor com disciplinaId
    public Historico(int id, int disciplinaId, String disciplinaNome, String periodo, String situacao, 
                    double notaFinal, int totalFaltas, double frequencia) {
        this.id = id;
        this.disciplinaId = disciplinaId;
        this.disciplinaNome = disciplinaNome;
        this.periodo = periodo;
        this.situacao = situacao;
        this.notaFinal = notaFinal;
        this.totalFaltas = totalFaltas;
        this.frequencia = frequencia;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(int disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public String getDisciplinaNome() { // RETORNA STRING
        return disciplinaNome;
    }

    public void setDisciplinaNome(String disciplinaNome) { // ACEITA STRING
        this.disciplinaNome = disciplinaNome;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public int getTotalFaltas() {
        return totalFaltas;
    }

    public void setTotalFaltas(int totalFaltas) {
        this.totalFaltas = totalFaltas;
    }

    public double getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(double frequencia) {
        this.frequencia = frequencia;
    }

    @Override
    public String toString() {
        return "Historico{" +
                "id=" + id +
                ", disciplinaId=" + disciplinaId +
                ", disciplinaNome='" + disciplinaNome + '\'' +
                ", periodo='" + periodo + '\'' +
                ", situacao='" + situacao + '\'' +
                ", notaFinal=" + notaFinal +
                ", totalFaltas=" + totalFaltas +
                ", frequencia=" + frequencia +
                '}';
    }
}
