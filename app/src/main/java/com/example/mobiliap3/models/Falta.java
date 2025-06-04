package com.example.mobiliap3.models;

public class Falta {
    private int id;
    private int disciplinaId;
    private int usuarioId; // ADICIONAR campo usuarioId
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

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

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

    // Métodos auxiliares
    public void calcularTotalFaltas() {
        this.totalFaltas = janeiro + fevereiro + marco + abril + maio + junho;
    }

    public void calcularPercentual(double cargaHoraria) {
        if (cargaHoraria > 0) {
            this.percentual = ((double) totalFaltas / cargaHoraria) * 100;
        } else {
            this.percentual = 0;
        }
    }

    public String getStatusFaltas() {
        if (percentual >= 25.0) {
            return "ACIMA_LIMITE";
        } else if (percentual >= 20.0) {
            return "PROXIMO_LIMITE";
        } else {
            return "DENTRO_LIMITE";
        }
    }

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

    // ADICIONAR: Método para recalcular com carga horária específica
    public void recalcularComCargaHoraria(double cargaHoraria) {
        calcularTotalFaltas();
        calcularPercentual(cargaHoraria);
    }

    // ADICIONAR: Método para validar se cálculo está correto
    public boolean isCalculoCorreto(double cargaHorariaEsperada) {
        double percentualEsperado = ((double) totalFaltas / cargaHorariaEsperada) * 100;
        return Math.abs(percentual - percentualEsperado) < 0.01; // Tolerância de 0.01%
    }

    // ADICIONAR: Método para debug
    public String getDebugInfo(double cargaHoraria) {
        return String.format("Faltas: %d, CH: %.0fh, Percentual: %.2f%% (%s)", 
                           totalFaltas, cargaHoraria, percentual, getStatusFaltas());
    }
}
