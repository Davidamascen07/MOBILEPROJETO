package com.example.mobiliap3.models;

public class Falta {
    private int id;
    private int disciplinaId;
    private String periodo;
    private int janeiro;
    private int fevereiro;
    private int marco;
    private int abril;
    private int maio;
    private int junho;
    private int totalFaltas;
    private double percentual;

    public Falta() {}

    public Falta(int disciplinaId, String periodo) {
        this.disciplinaId = disciplinaId;
        this.periodo = periodo;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDisciplinaId() { return disciplinaId; }
    public void setDisciplinaId(int disciplinaId) { this.disciplinaId = disciplinaId; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public int getJaneiro() { return janeiro; }
    public void setJaneiro(int janeiro) { this.janeiro = janeiro; }

    public int getFevereiro() { return fevereiro; }
    public void setFevereiro(int fevereiro) { this.fevereiro = fevereiro; }

    public int getMarco() { return marco; }
    public void setMarco(int marco) { this.marco = marco; }

    public int getAbril() { return abril; }
    public void setAbril(int abril) { this.abril = abril; }

    public int getMaio() { return maio; }
    public void setMaio(int maio) { this.maio = maio; }

    public int getJunho() { return junho; }
    public void setJunho(int junho) { this.junho = junho; }

    public int getTotalFaltas() { return totalFaltas; }
    public void setTotalFaltas(int totalFaltas) { this.totalFaltas = totalFaltas; }

    public double getPercentual() { return percentual; }
    public void setPercentual(double percentual) { this.percentual = percentual; }

    // Método para calcular total de faltas
    public void calcularTotalFaltas() {
        this.totalFaltas = janeiro + fevereiro + marco + abril + maio + junho;
    }

    // Método para calcular percentual
    public void calcularPercentual(double cargaHoraria) {
        if (cargaHoraria > 0) {
            this.percentual = ((double) totalFaltas / cargaHoraria) * 100;
        } else {
            this.percentual = 0.0;
        }
    }

    // Método para obter status das faltas
    public String getStatusFaltas() {
        if (percentual < 20.0) {
            return "DENTRO_LIMITE";
        } else if (percentual < 25.0) {
            return "PROXIMO_LIMITE";
        } else {
            return "ACIMA_LIMITE";
        }
    }

    // Método para verificar se pode reprovar por falta
    public boolean podeReprovarPorFalta() {
        return percentual >= 25.0;
    }

    // Método para obter descrição do status
    public String getDescricaoStatus() {
        switch (getStatusFaltas()) {
            case "DENTRO_LIMITE":
                return "Dentro do limite permitido";
            case "PROXIMO_LIMITE":
                return "Próximo ao limite (25%)";
            case "ACIMA_LIMITE":
                return "Acima do limite - Reprovação por falta";
            default:
                return "Status indefinido";
        }
    }
}
