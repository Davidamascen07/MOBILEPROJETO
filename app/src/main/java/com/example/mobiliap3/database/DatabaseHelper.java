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
    private static final int DATABASE_VERSION = 12; // INCREMENTAR de 5 para 6 - forçar reset dos dados

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

    // Tabela Notas - ATUALIZADA
    public static final String TABLE_NOTAS = "notas";
    public static final String COL_NOTA_ID = "id";
    public static final String COL_NOTA_USUARIO_ID = "usuario_id"; // NOVO CAMPO
    public static final String COL_NOTA_DISCIPLINA_ID = "disciplina_id";
    public static final String COL_NOTA_PERIODO = "periodo";
    public static final String COL_NOTA_NT1 = "nt1";
    public static final String COL_NOTA_NT2 = "nt2";
    public static final String COL_NOTA_NT3 = "nt3";
    public static final String COL_NOTA_MEDIA_FINAL = "media_final";
    public static final String COL_NOTA_NAF = "naf";
    public static final String COL_NOTA_FINAL = "nota_final";
    public static final String COL_NOTA_SITUACAO = "situacao";

    // Tabela Faltas - ATUALIZADA  
    public static final String TABLE_FALTAS = "faltas";
    public static final String COL_FALTA_ID = "id";
    public static final String COL_FALTA_USUARIO_ID = "usuario_id"; // NOVO CAMPO
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

        // Criar tabela notas COM usuario_id
        String createNotas = "CREATE TABLE " + TABLE_NOTAS + " (" +
                COL_NOTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NOTA_USUARIO_ID + " INTEGER NOT NULL, " + // NOVO
                COL_NOTA_DISCIPLINA_ID + " INTEGER NOT NULL, " +
                COL_NOTA_PERIODO + " TEXT NOT NULL, " +
                COL_NOTA_NT1 + " REAL, " +
                COL_NOTA_NT2 + " REAL, " +
                COL_NOTA_NT3 + " REAL, " +
                COL_NOTA_MEDIA_FINAL + " REAL, " +
                COL_NOTA_NAF + " REAL, " +
                COL_NOTA_FINAL + " REAL, " +
                COL_NOTA_SITUACAO + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + COL_NOTA_USUARIO_ID + ") REFERENCES " + 
                TABLE_USUARIOS + "(" + COL_USER_ID + "), " +
                "FOREIGN KEY(" + COL_NOTA_DISCIPLINA_ID + ") REFERENCES " + 
                TABLE_DISCIPLINAS + "(" + COL_DISC_ID + "))";
        db.execSQL(createNotas);

        // Criar tabela faltas COM usuario_id
        String createFaltas = "CREATE TABLE " + TABLE_FALTAS + " (" +
                COL_FALTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FALTA_USUARIO_ID + " INTEGER NOT NULL, " + // NOVO
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
                "FOREIGN KEY(" + COL_FALTA_USUARIO_ID + ") REFERENCES " + 
                TABLE_USUARIOS + "(" + COL_USER_ID + "), " +
                "FOREIGN KEY(" + COL_FALTA_DISCIPLINA_ID + ") REFERENCES " + 
                TABLE_DISCIPLINAS + "(" + COL_DISC_ID + "))";
        db.execSQL(createFaltas);

        // Inserir dados iniciais com histórico completo
        insertInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Forçar recriação completa do banco
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FALTAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISCIPLINAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        onCreate(db);
    }

    // Alterar visibilidade de private para public
    public void insertInitialData(SQLiteDatabase db) {
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
        // DISCIPLINAS NA ORDEM CORRETA (2025.1) - IDs 1-5
        insertDisciplina(db, "FIED017", "TRABALHO DE CONCLUSÃO DE CURSO II", 5, 75.0);        // ID 1
        insertDisciplina(db, "SINF061", "TÓPICOS EM DESENVOLVIMENTO MOBILE", 5, 75.0);        // ID 2  
        insertDisciplina(db, "SINF062", "LABORATÓRIO DE PROGRAMAÇÃO II", 5, 75.0);            // ID 3
        insertDisciplina(db, "SINF065", "ESTÁGIO SUPERVISIONADO II", 10, 150.0);             // ID 4
        insertDisciplina(db, "SINF070", "TÓPICOS ESPECIAIS EM ENGENHARIA DE SOFTWARE", 3, 45.0); // ID 5
        
        // Disciplinas já cursadas (histórico) - CORRIGIR ORDEM
        insertDisciplina(db, "SINF042", "INGLÊS INSTRUMENTAL", 3, 45.0);                      // ID 6
        insertDisciplina(db, "FIED010", "METODOLOGIA DA PESQUISA CIENTÍFICA", 5, 75.0);       // ID 7
        insertDisciplina(db, "SINF044", "LÓGICA MATEMÁTICA COMPUTACIONAL", 3, 45.0);          // ID 8
        insertDisciplina(db, "CSAP023", "INTRODUÇÃO À ADMINISTRAÇÃO", 5, 75.0);               // ID 9
        insertDisciplina(db, "CSAP035", "MATEMÁTICA BÁSICA", 5, 75.0);                        // ID 10
        insertDisciplina(db, "SINF043", "FUNDAMENTOS DE SISTEMAS DE INFORMAÇÃO", 3, 45.0);   // ID 11
        insertDisciplina(db, "CSAP033", "CÁLCULO I", 5, 75.0);                                // ID 12
        insertDisciplina(db, "CSAP021", "TÓPICOS DO DIREITO PÚBLICO E PRIVADO", 3, 45.0);     // ID 13
        insertDisciplina(db, "FIED012", "PROJETOS INTERDISCIPLINARES I", 3, 45.0);            // ID 14
        insertDisciplina(db, "SINF074", "TÓPICOS ESPECIAIS EM SISTEMAS OPERACIONAIS", 3, 45.0); // ID 15
        insertDisciplina(db, "SINF046", "ORGANIZAÇÃO E ARQUITETURA DE COMPUTADORES", 5, 75.0); // ID 16
        insertDisciplina(db, "SINF045", "TÉCNICAS DE PROGRAMAÇÃO I", 6, 90.0);                // ID 17
        insertDisciplina(db, "CSAP028", "EMPREENDEDORISMO E INOVAÇÃO", 3, 45.0);              // ID 18
        insertDisciplina(db, "CSAP025", "RESPONSABILIDADE SOCIOAMBIENTAL", 3, 45.0);          // ID 19
        insertDisciplina(db, "CSAP034", "CÁLCULO II", 3, 45.0);                               // ID 20
        insertDisciplina(db, "CSAP032", "GESTÃO DE SISTEMAS DE INFORMAÇÃO", 5, 75.0);         // ID 21
        insertDisciplina(db, "SINF047", "TÉCNICAS DE PROGRAMAÇÃO II", 5, 75.0);               // ID 22
        insertDisciplina(db, "CSAP027", "ESTATÍSTICA E PROBABILIDADE", 3, 45.0);              // ID 23
        insertDisciplina(db, "FIED013", "PROJETOS INTERDISCIPLINARES II", 3, 45.0);           // ID 24
        insertDisciplina(db, "CSAP031", "GESTÃO DE PROJETOS", 3, 45.0);                       // ID 25
        insertDisciplina(db, "FIED014", "PROJETOS INTERDISCIPLINARES III", 3, 45.0);          // ID 26
        insertDisciplina(db, "SINF075", "TÓPICOS ESPECIAIS EM ROBÓTICA", 3, 45.0);            // ID 27
        insertDisciplina(db, "SINF048", "BANCO DE DADOS I", 5, 75.0);                         // ID 28
        insertDisciplina(db, "SINF049", "REDES DE COMPUTADORES", 5, 75.0);                    // ID 29
        insertDisciplina(db, "SINF051", "BANCO DE DADOS II", 5, 75.0);                        // ID 30
        insertDisciplina(db, "SINF050", "PROGRAMAÇÃO ORIENTADA A OBJETOS", 6, 90.0);          // ID 31
        insertDisciplina(db, "SINF064", "GESTÃO DA QUALIDADE DE SOFTWARE", 5, 75.0);          // ID 32
        insertDisciplina(db, "SINF066", "TÓPICOS ESPECIAIS EM REDES DE COMPUTADORES", 3, 45.0); // ID 33
        insertDisciplina(db, "SINF071", "METODOLOGIAS DE DESENVOLVIMENTO DE SOFTWARE", 3, 45.0); // ID 34
        insertDisciplina(db, "SINF058", "ANÁLISE E PROJETOS DE SISTEMAS", 5, 75.0);           // ID 35
        insertDisciplina(db, "SINF059", "COMPUTAÇÃO GRÁFICA", 5, 75.0);                       // ID 36
        insertDisciplina(db, "SINF054", "ESTRUTURA DE DADOS", 5, 75.0);                       // ID 37
        insertDisciplina(db, "SINF056", "TEORIA DA COMPUTAÇÃO E LINGUAGENS FORMAIS", 5, 75.0); // ID 38
        insertDisciplina(db, "SINF055", "TÓPICOS EM PROGRAMAÇÃO WEB", 5, 75.0);               // ID 39
        insertDisciplina(db, "SINF052", "AUDITORIA E SEGURANÇA EM SISTEMAS DE INFORMAÇÃO", 5, 75.0); // ID 40
        insertDisciplina(db, "SINF053", "ENGENHARIA DE SOFTWARE", 6, 90.0);                   // ID 41
        insertDisciplina(db, "SINF060", "LABORATÓRIO DE PROGRAMAÇÃO I", 5, 75.0);             // ID 42
        insertDisciplina(db, "SINF063", "ESTÁGIO SUPERVISIONADO I", 10, 150.0);               // ID 43 
        insertDisciplina(db, "FIED016", "TRABALHO DE CONCLUSÃO DE CURSO I", 5, 75.0);         // ID 44
        insertDisciplina(db, "SINF057", "INTELIGÊNCIA ARTIFICIAL", 5, 75.0);                  // ID 45
    }

    private void insertDadosAtuais(SQLiteDatabase db) {
        // NOTAS NA ORDEM CORRETA conforme IDs das disciplinas
        insertNotaExample(db, 1, 1, "2025.1", null, null, null, "CURSANDO");   // TCC II (ID 1)
        insertNotaExample(db, 1, 2, "2025.1", 6.00, 8.80, null, "CURSANDO");   // MOBILE (ID 2) 
        insertNotaExample(db, 1, 3, "2025.1", 10.0, 10.0, null, "CURSANDO");   // LAB PROG II (ID 3)
        insertNotaExample(db, 1, 4, "2025.1", null, null, null, "CURSANDO");   // ESTÁGIO II (ID 4)
        insertNotaExample(db, 1, 5, "2025.1", 8.00, 7.70, null, "CURSANDO");   // ENG SOFTWARE (ID 5)
        
        // FALTAS NA ORDEM CORRETA conforme IDs das disciplinas
        insertFaltaExample(db, 1, 1, "2025.1", 0, 2, 0, 0, 0, 0);     // TCC II (ID 1)
        insertFaltaExample(db, 1, 2, "2025.1", 0, 10, 2, 4, 2, 0);    // MOBILE (ID 2) 
        insertFaltaExample(db, 1, 3, "2025.1", 0, 12, 0, 4, 0, 0);    // LAB PROG II (ID 3)
        insertFaltaExample(db, 1, 4, "2025.1", 0, 0, 0, 0, 0, 0);     // ESTÁGIO II (ID 4)
        insertFaltaExample(db, 1, 5, "2025.1", 0, 5, 2, 1, 2, 0);     // ENG SOFTWARE (ID 5)
    }

    private void insertHistoricoCompleto(SQLiteDatabase db) {
        // Inserir histórico de disciplinas aprovadas para usuário ID 1
        
        // 2021.2
        insertHistoricoDisciplina(db, 1, 6, "2021.2", 9.92, 100, "APROVADO");  // INGLÊS
        insertHistoricoDisciplina(db, 1, 7, "2021.2", 7.83, 100, "APROVADO");  // METODOLOGIA
        insertHistoricoDisciplina(db, 1, 8, "2021.2", 9.50, 100, "APROVADO");  // LÓGICA MAT
        insertHistoricoDisciplina(db, 1, 9, "2021.2", 8.00, 100, "APROVADO");  // INTRO ADM
        insertHistoricoDisciplina(db, 1, 10, "2021.2", 9.33, 100, "APROVADO"); // MAT BÁSICA
        insertHistoricoDisciplina(db, 1, 11, "2021.2", 9.00, 100, "APROVADO"); // FUND SI
        
        // 2022.1
        insertHistoricoDisciplina(db, 1, 12, "2022.1", 8.17, 100, "APROVADO"); // CÁLCULO I
        insertHistoricoDisciplina(db, 1, 13, "2022.1", 8.67, 100, "APROVADO"); // DIREITO
        insertHistoricoDisciplina(db, 1, 14, "2022.1", 8.17, 100, "APROVADO"); // PROJ I
        insertHistoricoDisciplina(db, 1, 15, "2022.1", 9.00, 100, "APROVADO"); // SIST OP
        insertHistoricoDisciplina(db, 1, 16, "2022.1", 8.17, 100, "APROVADO"); // ORG COMP
        insertHistoricoDisciplina(db, 1, 17, "2022.1", 8.22, 100, "APROVADO"); // PROG I
        
        // 2022.2
        insertHistoricoDisciplina(db, 1, 18, "2022.2", 7.67, 96, "APROVADO");  // EMPREEND
        insertHistoricoDisciplina(db, 1, 19, "2022.2", 7.87, 100, "APROVADO"); // RESP SOCIAL
        insertHistoricoDisciplina(db, 1, 20, "2022.2", 9.00, 89, "APROVADO");  // CÁLCULO II
        insertHistoricoDisciplina(db, 1, 21, "2022.2", 7.60, 100, "APROVADO"); // GESTÃO SI
        insertHistoricoDisciplina(db, 1, 22, "2022.2", 8.00, 100, "APROVADO"); // PROG II
        insertHistoricoDisciplina(db, 1, 23, "2022.2", 9.17, 96, "APROVADO");  // ESTATÍSTICA
        insertHistoricoDisciplina(db, 1, 24, "2022.2", 8.27, 96, "APROVADO");  // PROJ II
        insertHistoricoDisciplina(db, 1, 25, "2022.2", 7.83, 98, "APROVADO");  // GESTÃO PROJ
        
        // 2023.1
        insertHistoricoDisciplina(db, 1, 26, "2023.1", 7.93, 96, "APROVADO");  // PROJ III
        insertHistoricoDisciplina(db, 1, 27, "2023.1", 8.00, 100, "APROVADO"); // ROBÓTICA
        insertHistoricoDisciplina(db, 1, 28, "2023.1", 9.00, 98, "APROVADO");  // BD I
        insertHistoricoDisciplina(db, 1, 29, "2023.1", 8.33, 98, "APROVADO");  // REDES
        
        // 2023.2
        insertHistoricoDisciplina(db, 1, 30, "2023.2", 7.83, 98, "APROVADO");  // BD II
        insertHistoricoDisciplina(db, 1, 31, "2023.2", 7.67, 84, "APROVADO");  // POO
        insertHistoricoDisciplina(db, 1, 32, "2023.2", 7.00, 93, "APROVADO");  // QUALIDADE
        insertHistoricoDisciplina(db, 1, 33, "2023.2", 7.00, 100, "APROVADO"); // REDES ESP
        insertHistoricoDisciplina(db, 1, 34, "2023.2", 6.34, 91, "APROVADO");  // METODOLOGIAS
        
        // 2024.1
        insertHistoricoDisciplina(db, 1, 35, "2024.1", 5.20, 84, "APROVADO");  // ANÁLISE
        insertHistoricoDisciplina(db, 1, 36, "2024.1", 5.21, 87, "APROVADO");  // COMP GRÁF
        insertHistoricoDisciplina(db, 1, 37, "2024.1", 6.24, 86, "APROVADO");  // EST DADOS
        insertHistoricoDisciplina(db, 1, 38, "2024.1", 7.00, 96, "APROVADO");  // TEORIA COMP
        insertHistoricoDisciplina(db, 1, 39, "2024.1", 8.67, 84, "APROVADO");  // PROG WEB
        
        // 2024.2 - CORRIGIR IDs DOS ESTÁGIOS
        insertHistoricoDisciplina(db, 1, 40, "2024.2", 8.83, 97, "APROVADO");  // AUDITORIA
        insertHistoricoDisciplina(db, 1, 41, "2024.2", 7.10, 93, "APROVADO");  // ENG SOFT
        insertHistoricoDisciplina(db, 1, 42, "2024.2", 5.67, 93, "APROVADO");  // LAB PROG I
        insertHistoricoDisciplina(db, 1, 43, "2024.2", 10.00, 100, "APROVADO"); // ESTÁGIO I ✅ ID 43
        insertHistoricoDisciplina(db, 1, 44, "2024.2", 8.90, 100, "APROVADO"); // TCC I
        insertHistoricoDisciplina(db, 1, 45, "2024.2", 4.75, 81, "REPROVADO"); // IA
    }

    private void insertHistoricoDisciplina(SQLiteDatabase db, int usuarioId, int disciplinaId, String periodo, double notaFinal, int frequencia, String situacao) {
        ContentValues notaValues = new ContentValues();
        notaValues.put(COL_NOTA_USUARIO_ID, usuarioId);
        notaValues.put(COL_NOTA_DISCIPLINA_ID, disciplinaId);
        notaValues.put(COL_NOTA_PERIODO, periodo);
        notaValues.put(COL_NOTA_FINAL, notaFinal);
        notaValues.put(COL_NOTA_SITUACAO, situacao);
        
        // CORRIGIR: Calcular faltas baseado na carga horária REAL da disciplina
        double cargaHoraria = getCargaHorariaPorDisciplina(disciplinaId);
        int totalAulas = (int) cargaHoraria;
        int faltasTotal = totalAulas - (int) (totalAulas * frequencia / 100.0);
        
        db.insert(TABLE_NOTAS, null, notaValues);
        
        ContentValues faltaValues = new ContentValues();
        faltaValues.put(COL_FALTA_USUARIO_ID, usuarioId);
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

    private void insertNotaExample(SQLiteDatabase db, int usuarioId, int disciplinaId, String periodo, Double nt1, Double nt2, Double nt3, String situacao) {
        ContentValues values = new ContentValues();
        values.put(COL_NOTA_USUARIO_ID, usuarioId); // ADICIONAR usuario_id
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

    private void insertFaltaExample(SQLiteDatabase db, int usuarioId, int disciplinaId, String periodo, int jan, int fev, int mar, int abr, int mai, int jun) {
        ContentValues values = new ContentValues();
        values.put(COL_FALTA_USUARIO_ID, usuarioId);
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
        
        // CORRIGIR: Calcular percentual baseado na carga horária REAL de cada disciplina
        double cargaHoraria;
        switch (disciplinaId) {
            case 1: // TCC II - 5 créditos = 75h
                cargaHoraria = 75.0;
                break;
            case 2: // MOBILE - 5 créditos = 75h  
                cargaHoraria = 75.0;
                break;
            case 3: // LAB PROG II - 5 créditos = 75h
                cargaHoraria = 75.0;
                break;
            case 4: // ESTÁGIO II - 10 créditos = 150h
                cargaHoraria = 150.0;
                break;
            case 5: // ENG SOFTWARE - 3 créditos = 45h ⚠️ CORREÇÃO PRINCIPAL
                cargaHoraria = 45.0;
                break;
            default:
                cargaHoraria = 75.0;
        }
        
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

    // Método para criar dados iniciais APENAS para um usuário específico
    public void criarDadosInicaisUsuario(int usuarioId) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        // Verificar se usuário já tem dados
        String checkQuery = "SELECT COUNT(*) FROM " + TABLE_NOTAS + 
                           " WHERE " + COL_NOTA_USUARIO_ID + " = ?";
        Cursor cursor = db.rawQuery(checkQuery, new String[]{String.valueOf(usuarioId)});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        
        if (count == 0) {
            // Inserir dados de exemplo para este usuário
            insertDadosUsuario(db, usuarioId);
        }
        
        db.close();
    }

    private void insertDadosUsuario(SQLiteDatabase db, int usuarioId) {
        // CORRIGIR: Usar mesmos dados que insertDadosAtuais para consistência
        
        // Notas do período atual para este usuário - DADOS IDÊNTICOS
        insertNotaUsuario(db, usuarioId, 1, "2025.1", null, null, null, "CURSANDO");    // TCC II - sem notas
        insertNotaUsuario(db, usuarioId, 2, "2025.1", 6.00, 8.80, null, "CURSANDO");    // MOBILE - NT1: 6.00, NT2: 8.80
        insertNotaUsuario(db, usuarioId, 3, "2025.1", 10.0, 10.0, null, "CURSANDO");    // LAB PROG II - NT1: 10.0, NT2: 10.0
        insertNotaUsuario(db, usuarioId, 4, "2025.1", null, null, null, "CURSANDO");    // ESTÁGIO II - pendente aditamento
        insertNotaUsuario(db, usuarioId, 5, "2025.1", 8.00, 7.70, null, "CURSANDO");    // ENG SOFTWARE - NT1: 8.00, NT2: 7.70
        
        // Faltas do período atual para este usuário - DADOS IDÊNTICOS
        insertFaltaUsuario(db, usuarioId, 1, "2025.1", 0, 2, 0, 0, 0, 0);     // TCC II - 2 faltas (2.67%)
        insertFaltaUsuario(db, usuarioId, 2, "2025.1", 0, 10, 2, 4, 2, 0);    // MOBILE - 18 faltas (24%)
        insertFaltaUsuario(db, usuarioId, 3, "2025.1", 0, 12, 0, 4, 0, 0);    // LAB PROG II - 16 faltas (21.33%)
        insertFaltaUsuario(db, usuarioId, 4, "2025.1", 0, 0, 0, 0, 0, 0);     // ESTÁGIO II - 0 faltas (0%)
        insertFaltaUsuario(db, usuarioId, 5, "2025.1", 0, 5, 2, 1, 2, 0);     // ENG SOFTWARE - 10 faltas (22.22%)
    }

    private void insertNotaUsuario(SQLiteDatabase db, int usuarioId, int disciplinaId, String periodo, Double nt1, Double nt2, Double nt3, String situacao) {
        ContentValues values = new ContentValues();
        values.put(COL_NOTA_USUARIO_ID, usuarioId); // INCLUIR usuario_id
        values.put(COL_NOTA_DISCIPLINA_ID, disciplinaId);
        values.put(COL_NOTA_PERIODO, periodo);
        
        if (nt1 != null) values.put(COL_NOTA_NT1, nt1);
        if (nt2 != null) values.put(COL_NOTA_NT2, nt2);
        if (nt3 != null) values.put(COL_NOTA_NT3, nt3);
        
        values.put(COL_NOTA_SITUACAO, situacao);
        db.insert(TABLE_NOTAS, null, values);
    }

    private void insertFaltaUsuario(SQLiteDatabase db, int usuarioId, int disciplinaId, String periodo, int jan, int fev, int mar, int abr, int mai, int jun) {
        ContentValues values = new ContentValues();
        values.put(COL_FALTA_USUARIO_ID, usuarioId); // INCLUIR usuario_id
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
        
        // CORRIGIR: Usar carga horária real baseada no disciplinaId
        double cargaHoraria = getCargaHorariaPorDisciplina(disciplinaId);
        double percentual = (totalFaltas / cargaHoraria) * 100.0;
        values.put(COL_FALTA_PERCENTUAL, percentual);
        
        db.insert(TABLE_FALTAS, null, values);
    }

    // ADICIONAR: Método para obter carga horária por disciplina
    private double getCargaHorariaPorDisciplina(int disciplinaId) {
        switch (disciplinaId) {
            case 1: return 75.0;  // TCC II - 5 créditos
            case 2: return 75.0;  // MOBILE - 5 créditos  
            case 3: return 75.0;  // LAB PROG II - 5 créditos
            case 4: return 150.0; // ESTÁGIO II - 10 créditos
            case 5: return 45.0;  // ENG SOFTWARE - 3 créditos ⚠️
            case 17: return 90.0; // PROG I - 6 créditos
            case 31: return 90.0; // POO - 6 créditos  
            case 41: return 90.0; // ENG SOFT - 6 créditos
            case 43: return 150.0; // ESTÁGIO I - 10 créditos
            default: return 75.0; // Padrão 5 créditos
        }
    }

    // Método para cadastrar novo usuário
    public long cadastrarUsuario(String nome, String matricula, String curso, String senha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put(COL_USER_NOME, nome);
        values.put(COL_USER_MATRICULA, matricula);
        values.put(COL_USER_CURSO, curso);
        values.put(COL_USER_SENHA, senha);
        
        long userId = db.insert(TABLE_USUARIOS, null, values);
        db.close();
        
        // Criar dados iniciais para o novo usuário
        if (userId != -1) {
            criarDadosInicaisUsuario((int) userId);
        }
        
        return userId;
    }

    // ADICIONAR método para forçar drop manual (opcional - para debug)
    public void forceRecreateDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db, DATABASE_VERSION - 1, DATABASE_VERSION);
        db.close();
    }
}
