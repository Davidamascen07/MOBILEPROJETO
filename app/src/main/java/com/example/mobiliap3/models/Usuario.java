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

    // Dados pessoais completos
    private String cpf = "049.072.773-50";
    private String rg = "7126414";
    private String orgaoExpedidor = "MTPS-CE";
    private String dataNascimento = "23/05/1995";
    private String naturalidade = "GUARULHOS";
    private String uf = "SP";
    private String nacionalidade = "BRASILEIRA";
    private String sexo = "MASCULINO";
    private String endereco = "Rua Conselheiro João Lourenço, 406-Centro, Tianguá-CE";
    private String cep = "62320-000";
    private String telefone = "(88) 3671-2034";
    
    // Dados acadêmicos
    private String dataIngresso = "10/08/2021";
    private String semestreIngresso = "2021.2";
    private String tipoIngresso = "FIES SELEÇÃO - REALIZADO EM AGOSTO/2021";
    private String situacaoAcademica = "ATIVO";
    private double iraGeral = 7.84;
    private int cargaHorariaValidada = 2565;
    private int horasAtividadesComplementares = 215;
    private int cargaHorariaTotalValidada = 2780;
    private int cargaHorariaMinimaGraduacao = 3210;

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

    // Getters e Setters para os novos campos
    
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    
    public String getRg() { return rg; }
    public void setRg(String rg) { this.rg = rg; }
    
    public String getOrgaoExpedidor() { return orgaoExpedidor; }
    public void setOrgaoExpedidor(String orgaoExpedidor) { this.orgaoExpedidor = orgaoExpedidor; }
    
    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }
    
    public String getNaturalidade() { return naturalidade; }
    public void setNaturalidade(String naturalidade) { this.naturalidade = naturalidade; }
    
    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }
    
    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }
    
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public String getDataIngresso() { return dataIngresso; }
    public void setDataIngresso(String dataIngresso) { this.dataIngresso = dataIngresso; }
    
    public String getSemestreIngresso() { return semestreIngresso; }
    public void setSemestreIngresso(String semestreIngresso) { this.semestreIngresso = semestreIngresso; }
    
    public String getTipoIngresso() { return tipoIngresso; }
    public void setTipoIngresso(String tipoIngresso) { this.tipoIngresso = tipoIngresso; }
    
    public String getSituacaoAcademica() { return situacaoAcademica; }
    public void setSituacaoAcademica(String situacaoAcademica) { this.situacaoAcademica = situacaoAcademica; }
    
    public double getIraGeral() { return iraGeral; }
    public void setIraGeral(double iraGeral) { this.iraGeral = iraGeral; }
    
    public int getCargaHorariaValidada() { return cargaHorariaValidada; }
    public void setCargaHorariaValidada(int cargaHorariaValidada) { this.cargaHorariaValidada = cargaHorariaValidada; }
    
    public int getHorasAtividadesComplementares() { return horasAtividadesComplementares; }
    public void setHorasAtividadesComplementares(int horasAtividadesComplementares) { this.horasAtividadesComplementares = horasAtividadesComplementares; }
    
    public int getCargaHorariaTotalValidada() { return cargaHorariaTotalValidada; }
    public void setCargaHorariaTotalValidada(int cargaHorariaTotalValidada) { this.cargaHorariaTotalValidada = cargaHorariaTotalValidada; }
    
    public int getCargaHorariaMinimaGraduacao() { return cargaHorariaMinimaGraduacao; }
    public void setCargaHorariaMinimaGraduacao(int cargaHorariaMinimaGraduacao) { this.cargaHorariaMinimaGraduacao = cargaHorariaMinimaGraduacao; }
}
