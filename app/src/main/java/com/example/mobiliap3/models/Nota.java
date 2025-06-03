package com.example.mobiliap3.models;

public class Nota {
    private int id;
    private int disciplinaId;
    private String periodo;
    private Double nt1;
    private Double nt2;
    private Double nt3;
    private Double mediaFinal;
    private Double naf;
    private Double notaFinal;
    private String situacao;

    public Nota() {}

    public Nota(int disciplinaId, String periodo, String situacao) {
        this.disciplinaId = disciplinaId;
        this.periodo = periodo;
        this.situacao = situacao;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDisciplinaId() { return disciplinaId; }
    public void setDisciplinaId(int disciplinaId) { this.disciplinaId = disciplinaId; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public Double getNt1() { return nt1; }
    public void setNt1(Double nt1) { this.nt1 = nt1; }

    public Double getNt2() { return nt2; }
    public void setNt2(Double nt2) { this.nt2 = nt2; }

    public Double getNt3() { return nt3; }
    public void setNt3(Double nt3) { this.nt3 = nt3; }

    public Double getMediaFinal() { return mediaFinal; }
    public void setMediaFinal(Double mediaFinal) { this.mediaFinal = mediaFinal; }

    public Double getNaf() { return naf; }
    public void setNaf(Double naf) { this.naf = naf; }

    public Double getNotaFinal() { return notaFinal; }
    public void setNotaFinal(Double notaFinal) { this.notaFinal = notaFinal; }

    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }

    // Método utilitário para calcular média - MELHORADO
    public void calcularMedia() {
        if (nt1 != null && nt2 != null && nt3 != null) {
            this.mediaFinal = (nt1 + nt2 + nt3) / 3;
        } else if (nt1 != null && nt2 != null) {
            // Média parcial com duas notas
            this.mediaFinal = (nt1 + nt2) / 2;
        }
    }

    // Método para calcular nota final considerando NAF
    public void calcularNotaFinal() {
        if (mediaFinal != null) {
            if (mediaFinal >= 7.0) {
                this.notaFinal = mediaFinal;
            } else if (naf != null && mediaFinal >= 4.0) {
                // Média entre média final e NAF
                this.notaFinal = (mediaFinal + naf) / 2;
            } else {
                this.notaFinal = mediaFinal;
            }
        }
    }

    // Método para determinar situação baseada na nota
    public void determinarSituacao() {
        if (notaFinal == null) {
            this.situacao = "CURSANDO";
        } else if (notaFinal >= 7.0) {
            this.situacao = "APROVADO";
        } else if (notaFinal >= 5.0) {
            this.situacao = "APROVADO";
        } else {
            this.situacao = "REPROVADO";
        }
    }

    // Método para verificar se precisa de NAF
    public boolean precisaNaf() {
        return mediaFinal != null && mediaFinal >= 4.0 && mediaFinal < 7.0;
    }
}
