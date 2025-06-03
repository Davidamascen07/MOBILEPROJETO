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

        // Inserir dados iniciais
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
            return; // Dados já existem, não inserir novamente
        }
        
        // Inserir usuário padrão
        ContentValues userValues = new ContentValues();
        userValues.put(COL_USER_NOME, "David Damásceño da Frota");
        userValues.put(COL_USER_MATRICULA, "2810000317");
        userValues.put(COL_USER_CURSO, "Sistema de Informação - Bacharelado");
        userValues.put(COL_USER_SENHA, "123456");
        db.insert(TABLE_USUARIOS, null, userValues);

        // Inserir disciplinas
        insertDisciplina(db, "SINF062", "LABORATÓRIO DE PROGRAMAÇÃO II", 5, 75.0);
        insertDisciplina(db, "SINF061", "TÓPICOS EM DESENVOLVIMENTO MOBILE", 5, 75.0);
        insertDisciplina(db, "FIED017", "TRABALHO DE CONCLUSÃO DE CURSO II", 5, 75.0);
        insertDisciplina(db, "SINF065", "ESTÁGIO SUPERVISIONADO II", 10, 150.0);
        insertDisciplina(db, "SINF066", "TÓPICOS ESPECIAIS EM ENGENHARIA DE SOFTWARE", 5, 75.0);
        
        // Inserir dados de exemplo (notas e faltas)
        insertExampleData(db);
    }

    // Método para inserir dados de exemplo (notas e faltas)
    private void insertExampleData(SQLiteDatabase db) {
        // Inserir notas de exemplo para o período 2025.1 (usando IDs corretos)
        insertNotaExample(db, 1, "2025.1", 10.0, 10.0, null, "CURSANDO");
        insertNotaExample(db, 2, "2025.1", 6.0, null, null, "CURSANDO");
        insertNotaExample(db, 3, "2025.1", null, null, null, "CURSANDO");
        insertNotaExample(db, 4, "2025.1", 8.0, null, null, "CURSANDO");
        insertNotaExample(db, 5, "2025.1", 7.5, null, null, "CURSANDO");
        
        // Inserir faltas de exemplo (usando IDs corretos)
        insertFaltaExample(db, 1, "2025.1", 0, 12, 0, 4, 0, 0);
        insertFaltaExample(db, 2, "2025.1", 0, 10, 2, 4, 2, 0);
        insertFaltaExample(db, 3, "2025.1", 0, 0, 2, 0, 0, 0);
        insertFaltaExample(db, 4, "2025.1", 0, 8, 1, 2, 1, 0);
        insertFaltaExample(db, 5, "2025.1", 0, 5, 2, 1, 2, 0);
    }

    // Método auxiliar para inserir notas de exemplo
    private void insertNotaExample(SQLiteDatabase db, int disciplinaId, String periodo, Double nt1, Double nt2, Double nt3, String situacao) {
        ContentValues values = new ContentValues();
        values.put(COL_NOTA_DISCIPLINA_ID, disciplinaId);
        values.put(COL_NOTA_PERIODO, periodo);
        if (nt1 != null) values.put(COL_NOTA_NT1, nt1);
        if (nt2 != null) values.put(COL_NOTA_NT2, nt2);
        if (nt3 != null) values.put(COL_NOTA_NT3, nt3);
        values.put(COL_NOTA_SITUACAO, situacao);
        
        db.insert(TABLE_NOTAS, null, values);
    }

    // Método auxiliar para inserir faltas de exemplo
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
        
        int total = jan + fev + mar + abr + mai + jun;
        values.put(COL_FALTA_TOTAL, total);
        
        // Corrigir cálculo do percentual - considerando 75h como padrão
        double percentual = ((double) total / 75.0) * 100;
        values.put(COL_FALTA_PERCENTUAL, percentual);
        
        db.insert(TABLE_FALTAS, null, values);
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

    private void insertDisciplina(SQLiteDatabase db, String codigo, String nome, int creditos, double ch) {
        ContentValues values = new ContentValues();
        values.put(COL_DISC_CODIGO, codigo);
        values.put(COL_DISC_NOME, nome);
        values.put(COL_DISC_CREDITOS, creditos);
        values.put(COL_DISC_CH, ch);
        db.insert(TABLE_DISCIPLINAS, null, values);
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
}
