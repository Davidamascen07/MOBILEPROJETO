package com.example.mobiliap3.models;

public class DisciplinaCompleta {
    // Dados da disciplina
    private int disciplinaId;
    private String codigo;
    private String nome;
    private int creditos;
    private double cargaHoraria;
    
    // Dados das notas
    private Double nt1;
    private Double nt2;
    private Double nt3;
    private String situacao;
    
    // Dados das faltas
    private int janeiro;
    private int fevereiro;
    private int marco;
    private int abril;
    private int maio;
    private int junho;
    private int totalFaltas;
    private double percentual;

    public DisciplinaCompleta() {}

    // Getters e Setters
    public int getDisciplinaId() { return disciplinaId; }
    public void setDisciplinaId(int disciplinaId) { this.disciplinaId = disciplinaId; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getCreditos() { return creditos; }
    public void setCreditos(int creditos) { this.creditos = creditos; }

    public double getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(double cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    public Double getNt1() { return nt1; }
    public void setNt1(Double nt1) { this.nt1 = nt1; }

    public Double getNt2() { return nt2; }
    public void setNt2(Double nt2) { this.nt2 = nt2; }

    public Double getNt3() { return nt3; }
    public void setNt3(Double nt3) { this.nt3 = nt3; }

    public String getSituacao() { return situacao; }
    public void setSituacao(String situacao) { this.situacao = situacao; }

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

    // Método utilitário para obter status das faltas
    public String getStatusFaltas() {
        if (percentual >= 25.0) {
            return "ACIMA_LIMITE";
        } else if (percentual >= 20.0) {
            return "PROXIMO_LIMITE";
        } else {
            return "DENTRO_LIMITE";
        }
    }

    @Override
    public String toString() {
        return "DisciplinaCompleta{" +
                "disciplinaId=" + disciplinaId +
                ", codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", nt1=" + nt1 +
                ", nt2=" + nt2 +
                ", totalFaltas=" + totalFaltas +
                ", percentual=" + percentual +
                '}';
    }
}
