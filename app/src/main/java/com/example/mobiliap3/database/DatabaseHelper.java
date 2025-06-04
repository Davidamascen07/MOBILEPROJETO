package com.example.mobiliap3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mobiliap3.models.Usuario;
import com.example.mobiliap3.models.Disciplina;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mobiliap3.db";
    private static final int DATABASE_VERSION = 2; // Incrementar versão para forçar recriação

    // Tabela Usuarios
    public static final String TABLE_USUARIOS = "usuarios";
    public static final String COL_USER_ID = "id";
    public static final String COL_USER_NOME = "nome";
    public static final String COL_USER_MATRICULA = "matricula";
    public static final String COL_USER_CURSO = "curso";
    public static final String COL_USER_SENHA = "senha";

    // Tabela Disciplinas
    public static final String TABLE_DISCIPLINAS = "disciplinas";
    public static final String COL_DISC_ID = "id";
    public static final String COL_DISC_CODIGO = "codigo";
    public static final String COL_DISC_NOME = "nome";
    public static final String COL_DISC_CREDITOS = "creditos";
    public static final String COL_DISC_CH = "carga_horaria";

    // Tabela Notas
    public static final String TABLE_NOTAS = "notas";
    public static final String COL_NOTA_ID = "id";
    public static final String COL_NOTA_DISCIPLINA_ID = "disciplina_id";
    public static final String COL_NOTA_PERIODO = "periodo";
    public static final String COL_NOTA_NT1 = "nt1";
    public static final String COL_NOTA_NT2 = "nt2";
    public static final String COL_NOTA_NT3 = "nt3";
    public static final String COL_NOTA_MEDIA_FINAL = "media_final";
    public static final String COL_NOTA_NAF = "naf";
    public static final String COL_NOTA_FINAL = "nota_final";
    public static final String COL_NOTA_SITUACAO = "situacao";

    // Tabela Faltas
    public static final String TABLE_FALTAS = "faltas";
    public static final String COL_FALTA_ID = "id";
    public static final String COL_FALTA_DISCIPLINA_ID = "disciplina_id";
    public static final String COL_FALTA_PERIODO = "periodo";
    public static final String COL_FALTA_JANEIRO = "janeiro";
    public static final String COL_FALTA_FEVEREIRO = "fevereiro";
    public static final String COL_FALTA_MARCO = "marco";
    public static final String COL_FALTA_ABRIL = "abril";
    public static final String COL_FALTA_MAIO = "maio";
    public static final String COL_FALTA_JUNHO = "junho";
    public static final String COL_FALTA_TOTAL = "total_faltas";
    public static final String COL_FALTA_PERCENTUAL = "percentual";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criar tabela usuarios
        String createUsuarios = "CREATE TABLE " + TABLE_USUARIOS + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_NOME + " TEXT NOT NULL, " +
                COL_USER_MATRICULA + " TEXT UNIQUE NOT NULL, " +
                COL_USER_CURSO + " TEXT NOT NULL, " +
                COL_USER_SENHA + " TEXT NOT NULL)";
        db.execSQL(createUsuarios);

        // Criar tabela disciplinas
        String createDisciplinas = "CREATE TABLE " + TABLE_DISCIPLINAS + " (" +
                COL_DISC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DISC_CODIGO + " TEXT UNIQUE NOT NULL, " +
                COL_DISC_NOME + " TEXT NOT NULL, " +
                COL_DISC_CREDITOS + " INTEGER NOT NULL, " +
                COL_DISC_CH + " REAL NOT NULL)";
        db.execSQL(createDisciplinas);

        // Criar tabela notas
        String createNotas = "CREATE TABLE " + TABLE_NOTAS + " (" +
                COL_NOTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NOTA_DISCIPLINA_ID + " INTEGER NOT NULL, " +
                COL_NOTA_PERIODO + " TEXT NOT NULL, " +
                COL_NOTA_NT1 + " REAL, " +
                COL_NOTA_NT2 + " REAL, " +
                COL_NOTA_NT3 + " REAL, " +
                COL_NOTA_MEDIA_FINAL + " REAL, " +
                COL_NOTA_NAF + " REAL, " +
                COL_NOTA_FINAL + " REAL, " +
                COL_NOTA_SITUACAO + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + COL_NOTA_DISCIPLINA_ID + ") REFERENCES " + 
                TABLE_DISCIPLINAS + "(" + COL_DISC_ID + "))";
        db.execSQL(createNotas);

        // Criar tabela faltas
        String createFaltas = "CREATE TABLE " + TABLE_FALTAS + " (" +
                COL_FALTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FALTA_DISCIPLINA_ID + " INTEGER NOT NULL, " +
                COL_FALTA_PERIODO + " TEXT NOT NULL, " +
                COL_FALTA_JANEIRO + " INTEGER DEFAULT 0, " +
                COL_FALTA_FEVEREIRO + " INTEGER DEFAULT 0, " +
                COL_FALTA_MARCO + " INTEGER DEFAULT 0, " +
                COL_FALTA_ABRIL + " INTEGER DEFAULT 0, " +
                COL_FALTA_MAIO + " INTEGER DEFAULT 0, " +
                COL_FALTA_JUNHO + " INTEGER DEFAULT 0, " +
                COL_FALTA_TOTAL + " INTEGER DEFAULT 0, " +
                COL_FALTA_PERCENTUAL + " REAL DEFAULT 0, " +
                "FOREIGN KEY(" + COL_FALTA_DISCIPLINA_ID + ") REFERENCES " + 
                TABLE_DISCIPLINAS + "(" + COL_DISC_ID + "))";
        db.execSQL(createFaltas);

        // Inserir dados iniciais com histórico completo
        insertInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FALTAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISCIPLINAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        onCreate(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        // Verificar se já existem dados antes de inserir
        if (hasInitialData(db)) {
            return;
        }
        
        // Inserir usuário com dados completos do histórico
        ContentValues userValues = new ContentValues();
        userValues.put(COL_USER_NOME, "David Damasceno da Frota");
        userValues.put(COL_USER_MATRICULA, "2810000317");
        userValues.put(COL_USER_CURSO, "Bacharelado em Sistemas de Informação");
        userValues.put(COL_USER_SENHA, "123456");
        db.insert(TABLE_USUARIOS, null, userValues);

        // Inserir disciplinas do histórico completo
        insertDisciplinasHistorico(db);
        
        // Inserir dados de notas e faltas atuais (2025.1)
        insertDadosAtuais(db);
        
        // Inserir histórico completo de disciplinas aprovadas
        insertHistoricoCompleto(db);
    }

    private void insertDisciplinasHistorico(SQLiteDatabase db) {
        // Disciplinas atuais (2025.1) - CURSANDO
        insertDisciplina(db, "SINF061", "TÓPICOS EM DESENVOLVIMENTO MOBILE", 5, 75.0);
        insertDisciplina(db, "SINF062", "LABORATÓRIO DE PROGRAMAÇÃO II", 5, 75.0);
        insertDisciplina(db, "SINF070", "TÓPICOS ESPECIAIS EM ENGENHARIA DE SOFTWARE", 3, 45.0);
        insertDisciplina(db, "SINF065", "ESTÁGIO SUPERVISIONADO II", 10, 150.0);
        insertDisciplina(db, "FIED017", "TRABALHO DE CONCLUSÃO DE CURSO II", 5, 75.0);
        
        // Disciplinas já cursadas (histórico)
        insertDisciplina(db, "SINF042", "INGLÊS INSTRUMENTAL", 3, 45.0);
        insertDisciplina(db, "FIED010", "METODOLOGIA DA PESQUISA CIENTÍFICA", 5, 75.0);
        insertDisciplina(db, "SINF044", "LÓGICA MATEMÁTICA COMPUTACIONAL", 3, 45.0);
        insertDisciplina(db, "CSAP023", "INTRODUÇÃO À ADMINISTRAÇÃO", 5, 75.0);
        insertDisciplina(db, "CSAP035", "MATEMÁTICA BÁSICA", 5, 75.0);
        insertDisciplina(db, "SINF043", "FUNDAMENTOS DE SISTEMAS DE INFORMAÇÃO", 3, 45.0);
        insertDisciplina(db, "CSAP033", "CÁLCULO I", 5, 75.0);
        insertDisciplina(db, "CSAP021", "TÓPICOS DO DIREITO PÚBLICO E PRIVADO", 3, 45.0);
        insertDisciplina(db, "FIED012", "PROJETOS INTERDISCIPLINARES I", 3, 45.0);
        insertDisciplina(db, "SINF074", "TÓPICOS ESPECIAIS EM SISTEMAS OPERACIONAIS", 3, 45.0);
        insertDisciplina(db, "SINF046", "ORGANIZAÇÃO E ARQUITETURA DE COMPUTADORES", 5, 75.0);
        insertDisciplina(db, "SINF045", "TÉCNICAS DE PROGRAMAÇÃO I", 6, 90.0);
        insertDisciplina(db, "CSAP028", "EMPREENDEDORISMO E INOVAÇÃO", 3, 45.0);
        insertDisciplina(db, "CSAP025", "RESPONSABILIDADE SOCIOAMBIENTAL", 3, 45.0);
        insertDisciplina(db, "CSAP034", "CÁLCULO II", 3, 45.0);
        insertDisciplina(db, "CSAP032", "GESTÃO DE SISTEMAS DE INFORMAÇÃO", 5, 75.0);
        insertDisciplina(db, "SINF047", "TÉCNICAS DE PROGRAMAÇÃO II", 5, 75.0);
        insertDisciplina(db, "CSAP027", "ESTATÍSTICA E PROBABILIDADE", 3, 45.0);
        insertDisciplina(db, "FIED013", "PROJETOS INTERDISCIPLINARES II", 3, 45.0);
        insertDisciplina(db, "CSAP031", "GESTÃO DE PROJETOS", 3, 45.0);
        insertDisciplina(db, "FIED014", "PROJETOS INTERDISCIPLINARES III", 3, 45.0);
        insertDisciplina(db, "SINF075", "TÓPICOS ESPECIAIS EM ROBÓTICA", 3, 45.0);
        insertDisciplina(db, "SINF048", "BANCO DE DADOS I", 5, 75.0);
        insertDisciplina(db, "SINF049", "REDES DE COMPUTADORES", 5, 75.0);
        insertDisciplina(db, "SINF051", "BANCO DE DADOS II", 5, 75.0);
        insertDisciplina(db, "SINF050", "PROGRAMAÇÃO ORIENTADA A OBJETOS", 6, 90.0);
        insertDisciplina(db, "SINF064", "GESTÃO DA QUALIDADE DE SOFTWARE", 5, 75.0);
        insertDisciplina(db, "SINF066", "TÓPICOS ESPECIAIS EM REDES DE COMPUTADORES", 3, 45.0);
        insertDisciplina(db, "SINF071", "METODOLOGIAS DE DESENVOLVIMENTO DE SOFTWARE", 3, 45.0);
        insertDisciplina(db, "SINF058", "ANÁLISE E PROJETOS DE SISTEMAS", 5, 75.0);
        insertDisciplina(db, "SINF059", "COMPUTAÇÃO GRÁFICA", 5, 75.0);
        insertDisciplina(db, "SINF054", "ESTRUTURA DE DADOS", 5, 75.0);
        insertDisciplina(db, "SINF056", "TEORIA DA COMPUTAÇÃO E LINGUAGENS FORMAIS", 5, 75.0);
        insertDisciplina(db, "SINF055", "TÓPICOS EM PROGRAMAÇÃO WEB", 5, 75.0);
        insertDisciplina(db, "SINF052", "AUDITORIA E SEGURANÇA EM SISTEMAS DE INFORMAÇÃO", 5, 75.0);
        insertDisciplina(db, "SINF053", "ENGENHARIA DE SOFTWARE", 6, 90.0);
        insertDisciplina(db, "SINF060", "LABORATÓRIO DE PROGRAMAÇÃO I", 5, 75.0);
        insertDisciplina(db, "SINF063", "ESTÁGIO SUPERVISIONADO I", 10, 150.0);
        insertDisciplina(db, "FIED016", "TRABALHO DE CONCLUSÃO DE CURSO I", 5, 75.0);
        insertDisciplina(db, "SINF057", "INTELIGÊNCIA ARTIFICIAL", 5, 75.0);
    }

    private void insertDadosAtuais(SQLiteDatabase db) {
        // Notas atuais (2025.1) - apenas algumas disciplinas têm notas
        insertNotaExample(db, 1, "2025.1", 10.0, 10.0, null, "CURSANDO"); // MOBILE
        insertNotaExample(db, 2, "2025.1", 8.5, null, null, "CURSANDO");  // LAB PROG II
        insertNotaExample(db, 3, "2025.1", null, null, null, "CURSANDO"); // ENG SOFTWARE
        insertNotaExample(db, 4, "2025.1", 9.0, null, null, "CURSANDO");  // ESTÁGIO II
        insertNotaExample(db, 5, "2025.1", 7.5, null, null, "CURSANDO");  // TCC II
        
        // Faltas atuais (2025.1)
        insertFaltaExample(db, 1, "2025.1", 0, 8, 2, 6, 0, 0);  // MOBILE
        insertFaltaExample(db, 2, "2025.1", 0, 12, 0, 4, 2, 0); // LAB PROG II
        insertFaltaExample(db, 3, "2025.1", 0, 0, 1, 0, 0, 0);  // ENG SOFTWARE
        insertFaltaExample(db, 4, "2025.1", 0, 10, 2, 8, 1, 0); // ESTÁGIO II
        insertFaltaExample(db, 5, "2025.1", 0, 6, 1, 2, 1, 0);  // TCC II
    }

    private void insertHistoricoCompleto(SQLiteDatabase db) {
        // Inserir histórico de disciplinas aprovadas
        
        // 2021.2
        insertHistoricoDisciplina(db, 6, "2021.2", 9.92, 100, "APROVADO");  // INGLÊS
        insertHistoricoDisciplina(db, 7, "2021.2", 7.83, 100, "APROVADO");  // METODOLOGIA
        insertHistoricoDisciplina(db, 8, "2021.2", 9.50, 100, "APROVADO");  // LÓGICA MAT
        insertHistoricoDisciplina(db, 9, "2021.2", 8.00, 100, "APROVADO");  // INTRO ADM
        insertHistoricoDisciplina(db, 10, "2021.2", 9.33, 100, "APROVADO"); // MAT BÁSICA
        insertHistoricoDisciplina(db, 11, "2021.2", 9.00, 100, "APROVADO"); // FUND SI
        
        // 2022.1
        insertHistoricoDisciplina(db, 12, "2022.1", 8.17, 100, "APROVADO"); // CÁLCULO I
        insertHistoricoDisciplina(db, 13, "2022.1", 8.67, 100, "APROVADO"); // DIREITO
        insertHistoricoDisciplina(db, 14, "2022.1", 8.17, 100, "APROVADO"); // PROJ I
        insertHistoricoDisciplina(db, 15, "2022.1", 9.00, 100, "APROVADO"); // SIST OP
        insertHistoricoDisciplina(db, 16, "2022.1", 8.17, 100, "APROVADO"); // ORG COMP
        insertHistoricoDisciplina(db, 17, "2022.1", 8.22, 100, "APROVADO"); // PROG I
        
        // 2022.2
        insertHistoricoDisciplina(db, 18, "2022.2", 7.67, 96, "APROVADO");  // EMPREEND
        insertHistoricoDisciplina(db, 19, "2022.2", 7.87, 100, "APROVADO"); // RESP SOCIAL
        insertHistoricoDisciplina(db, 20, "2022.2", 9.00, 89, "APROVADO");  // CÁLCULO II
        insertHistoricoDisciplina(db, 21, "2022.2", 7.60, 100, "APROVADO"); // GESTÃO SI
        insertHistoricoDisciplina(db, 22, "2022.2", 8.00, 100, "APROVADO"); // PROG II
        insertHistoricoDisciplina(db, 23, "2022.2", 9.17, 96, "APROVADO");  // ESTATÍSTICA
        insertHistoricoDisciplina(db, 24, "2022.2", 8.27, 96, "APROVADO");  // PROJ II
        insertHistoricoDisciplina(db, 25, "2022.2", 7.83, 98, "APROVADO");  // GESTÃO PROJ
        
        // 2023.1
        insertHistoricoDisciplina(db, 26, "2023.1", 7.93, 96, "APROVADO");  // PROJ III
        insertHistoricoDisciplina(db, 27, "2023.1", 8.00, 100, "APROVADO"); // ROBÓTICA
        insertHistoricoDisciplina(db, 28, "2023.1", 9.00, 98, "APROVADO");  // BD I
        insertHistoricoDisciplina(db, 29, "2023.1", 8.33, 98, "APROVADO");  // REDES
        
        // 2023.2
        insertHistoricoDisciplina(db, 30, "2023.2", 7.83, 98, "APROVADO");  // BD II
        insertHistoricoDisciplina(db, 31, "2023.2", 7.67, 84, "APROVADO");  // POO
        insertHistoricoDisciplina(db, 32, "2023.2", 7.00, 93, "APROVADO");  // QUALIDADE
        insertHistoricoDisciplina(db, 33, "2023.2", 7.00, 100, "APROVADO"); // REDES ESP
        insertHistoricoDisciplina(db, 34, "2023.2", 6.34, 91, "APROVADO");  // METODOLOGIAS
        
        // 2024.1
        insertHistoricoDisciplina(db, 35, "2024.1", 5.20, 84, "APROVADO");  // ANÁLISE
        insertHistoricoDisciplina(db, 36, "2024.1", 5.21, 87, "APROVADO");  // COMP GRÁF
        insertHistoricoDisciplina(db, 37, "2024.1", 6.24, 86, "APROVADO");  // EST DADOS
        insertHistoricoDisciplina(db, 38, "2024.1", 7.00, 96, "APROVADO");  // TEORIA COMP
        insertHistoricoDisciplina(db, 39, "2024.1", 8.67, 84, "APROVADO");  // PROG WEB
        
        // 2024.2
        insertHistoricoDisciplina(db, 40, "2024.2", 8.83, 97, "APROVADO");  // AUDITORIA
        insertHistoricoDisciplina(db, 41, "2024.2", 7.10, 93, "APROVADO");  // ENG SOFT
        insertHistoricoDisciplina(db, 42, "2024.2", 5.67, 93, "APROVADO");  // LAB PROG I
        insertHistoricoDisciplina(db, 43, "2024.2", 10.00, 100, "APROVADO"); // ESTÁGIO I
        insertHistoricoDisciplina(db, 44, "2024.2", 8.90, 100, "APROVADO"); // TCC I
        insertHistoricoDisciplina(db, 45, "2024.2", 4.75, 81, "REPROVADO"); // IA
    }

    private void insertHistoricoDisciplina(SQLiteDatabase db, int disciplinaId, String periodo, double notaFinal, int frequencia, String situacao) {
        ContentValues notaValues = new ContentValues();
        notaValues.put(COL_NOTA_DISCIPLINA_ID, disciplinaId);
        notaValues.put(COL_NOTA_PERIODO, periodo);
        notaValues.put(COL_NOTA_FINAL, notaFinal);
        notaValues.put(COL_NOTA_SITUACAO, situacao);
        
        // Calcular faltas baseado na frequência
        double cargaHoraria = 75.0; // Padrão para maioria
        if (disciplinaId == 17 || disciplinaId == 31 || disciplinaId == 41) cargaHoraria = 90.0; // 6 créditos
        if (disciplinaId == 43 || disciplinaId == 4) cargaHoraria = 150.0; // 10 créditos
        
        int totalAulas = (int) cargaHoraria;
        int faltasTotal = totalAulas - (int) (totalAulas * frequencia / 100.0);
        
        db.insert(TABLE_NOTAS, null, notaValues);
        
        ContentValues faltaValues = new ContentValues();
        faltaValues.put(COL_FALTA_DISCIPLINA_ID, disciplinaId);
        faltaValues.put(COL_FALTA_PERIODO, periodo);
        faltaValues.put(COL_FALTA_TOTAL, faltasTotal);
        faltaValues.put(COL_FALTA_PERCENTUAL, 100.0 - frequencia);
        
        db.insert(TABLE_FALTAS, null, faltaValues);
    }

    private void insertDisciplina(SQLiteDatabase db, String codigo, String nome, int creditos, double ch) {
        ContentValues values = new ContentValues();
        values.put(COL_DISC_CODIGO, codigo);
        values.put(COL_DISC_NOME, nome);
        values.put(COL_DISC_CREDITOS, creditos);
        values.put(COL_DISC_CH, ch);
        db.insert(TABLE_DISCIPLINAS, null, values);
    }

    private void insertNotaExample(SQLiteDatabase db, int disciplinaId, String periodo, Double nt1, Double nt2, Double nt3, String situacao) {
        ContentValues values = new ContentValues();
        values.put(COL_NOTA_DISCIPLINA_ID, disciplinaId);
        values.put(COL_NOTA_PERIODO, periodo);

        if (nt1 != null) {
            values.put(COL_NOTA_NT1, nt1);
        }
        if (nt2 != null) {
            values.put(COL_NOTA_NT2, nt2);
        }
        if (nt3 != null) {
            values.put(COL_NOTA_NT3, nt3);
        }
        
        // Calcular média final se houver notas suficientes
        if (nt1 != null && nt2 != null) {
            double mediaFinal = (nt1 + nt2) / 2.0;
            if (nt3 != null) {
                mediaFinal = (nt1 + nt2 + nt3) / 3.0;
            }
            values.put(COL_NOTA_MEDIA_FINAL, mediaFinal);
            values.put(COL_NOTA_FINAL, mediaFinal);
        }
        
        values.put(COL_NOTA_SITUACAO, situacao);
        db.insert(TABLE_NOTAS, null, values);
    }

    private void insertFaltaExample(SQLiteDatabase db, int disciplinaId, String periodo, int jan, int fev, int mar, int abr, int mai, int jun) {
        ContentValues values = new ContentValues();
        values.put(COL_FALTA_DISCIPLINA_ID, disciplinaId);
        values.put(COL_FALTA_PERIODO, periodo);
        values.put(COL_FALTA_JANEIRO, jan);
        values.put(COL_FALTA_FEVEREIRO, fev);
        values.put(COL_FALTA_MARCO, mar);
        values.put(COL_FALTA_ABRIL, abr);
        values.put(COL_FALTA_MAIO, mai);
        values.put(COL_FALTA_JUNHO, jun);
        
        int totalFaltas = jan + fev + mar + abr + mai + jun;
        values.put(COL_FALTA_TOTAL, totalFaltas);
        
        // Calcular percentual (assumindo 75h = 75 aulas padrão)
        double cargaHoraria = 75.0;
        if (disciplinaId == 4) cargaHoraria = 150.0; // ESTÁGIO II
        if (disciplinaId == 3) cargaHoraria = 45.0;  // ENG SOFTWARE
        
        double percentual = (totalFaltas / cargaHoraria) * 100.0;
        values.put(COL_FALTA_PERCENTUAL, percentual);
        
        db.insert(TABLE_FALTAS, null, values);
    }

    // Método para autenticar usuário (com suporte a matriculas antigas)
    public Usuario autenticarUsuario(String matricula, String senha) {
        SQLiteDatabase db = this.getReadableDatabase();
        Usuario usuario = null;

        String query = "SELECT * FROM " + TABLE_USUARIOS + 
                      " WHERE (" + COL_USER_MATRICULA + "=? OR " + COL_USER_MATRICULA + "=?) AND " + COL_USER_SENHA + "=?";
        
        // Tentar com ambas as matrículas (nova e antiga)
        Cursor cursor = db.rawQuery(query, new String[]{matricula, "20241234567", senha});
        
        if (cursor.moveToFirst()) {
            usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_ID)));
            usuario.setNome(cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_NOME)));
            usuario.setMatricula(cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_MATRICULA)));
            usuario.setCurso(cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_CURSO)));
            
            // Se logou com matrícula antiga, atualizar para nova
            if ("20241234567".equals(usuario.getMatricula())) {
                atualizarMatriculaUsuario(db, usuario.getId(), "2810000317");
                usuario.setMatricula("2810000317");
            }
        }
        
        cursor.close();
        db.close();
        return usuario;
    }

    // Método auxiliar para atualizar matrícula
    private void atualizarMatriculaUsuario(SQLiteDatabase db, int userId, String novaMatricula) {
        ContentValues values = new ContentValues();
        values.put(COL_USER_MATRICULA, novaMatricula);
        db.update(TABLE_USUARIOS, values, COL_USER_ID + "=?", new String[]{String.valueOf(userId)});
    }

    // Método para atualizar ou criar usuário com nova matrícula
    public void atualizarUsuario() {
        SQLiteDatabase db = this.getWritableDatabase();
        
        // Verificar se usuário com nova matrícula já existe
        String checkQuery = "SELECT COUNT(*) FROM " + TABLE_USUARIOS + 
                           " WHERE " + COL_USER_MATRICULA + "=?";
        Cursor cursor = db.rawQuery(checkQuery, new String[]{"2810000317"});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        
        if (count == 0) {
            // Atualizar matrícula existente ou inserir novo usuário
            ContentValues userValues = new ContentValues();
            userValues.put(COL_USER_NOME, "David Damásceño da Frota");
            userValues.put(COL_USER_MATRICULA, "2810000317");
            userValues.put(COL_USER_CURSO, "Sistema de Informação - Bacharelado");
            userValues.put(COL_USER_SENHA, "123456");
            
            // Tentar atualizar primeiro
            int rowsAffected = db.update(TABLE_USUARIOS, userValues, 
                                       COL_USER_MATRICULA + "=?", 
                                       new String[]{"20241234567"});
            
            // Se não atualizou nenhuma linha, inserir novo usuário
            if (rowsAffected == 0) {
                db.insert(TABLE_USUARIOS, null, userValues);
            }
        }
        
        db.close();
    }

    // Método para verificar se os dados iniciais já existem
    private boolean hasInitialData(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_USUARIOS, null);
        cursor.moveToFirst();
        int userCount = cursor.getInt(0);
        cursor.close();
        
        cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_DISCIPLINAS, null);
        cursor.moveToFirst();
        int disciplinaCount = cursor.getInt(0);
        cursor.close();
        
        // Se existem usuários E disciplinas, dados já foram inseridos
        return userCount > 0 && disciplinaCount > 0;
    }

    // Método público para verificar se dados já existem (para usar no Repository)
    public boolean hasData() {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean hasData = hasInitialData(db);
        db.close();
        return hasData;
    }
}
