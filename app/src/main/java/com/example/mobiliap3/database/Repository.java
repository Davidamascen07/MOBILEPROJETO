package com.example.mobiliap3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mobiliap3.models.Disciplina;
import com.example.mobiliap3.models.Nota;
import com.example.mobiliap3.models.Falta;
import com.example.mobiliap3.models.Usuario;
import com.example.mobiliap3.models.Historico;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public Repository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    private void openDatabase() {
        if (database == null || !database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }
    }

    private void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    // Métodos para Disciplinas
    public List<Disciplina> getAllDisciplinas() {
        List<Disciplina> disciplinas = new ArrayList<>();
        openDatabase();

        Cursor cursor = database.query(DatabaseHelper.TABLE_DISCIPLINAS, null, null, null, null, null, null);
        
        if (cursor.moveToFirst()) {
            do {
                Disciplina disciplina = new Disciplina();
                disciplina.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DISC_ID)));
                disciplina.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DISC_CODIGO)));
                disciplina.setNome(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DISC_NOME)));
                disciplina.setCreditos(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DISC_CREDITOS)));
                disciplina.setCargaHoraria(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DISC_CH)));
                
                disciplinas.add(disciplina);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        closeDatabase();
        return disciplinas;
    }

    public Disciplina getDisciplinaById(int id) {
        openDatabase();
        Disciplina disciplina = null;

        String selection = DatabaseHelper.COL_DISC_ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = database.query(DatabaseHelper.TABLE_DISCIPLINAS, null, selection, selectionArgs, null, null, null);
        
        if (cursor.moveToFirst()) {
            disciplina = new Disciplina();
            disciplina.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DISC_ID)));
            disciplina.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DISC_CODIGO)));
            disciplina.setNome(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DISC_NOME)));
            disciplina.setCreditos(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DISC_CREDITOS)));
            disciplina.setCargaHoraria(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DISC_CH)));
        }
        
        cursor.close();
        closeDatabase();
        return disciplina;
    }

    // Métodos para Notas
    public List<Nota> getNotasByPeriodo(String periodo) {
        List<Nota> notas = new ArrayList<>();
        openDatabase();

        String selection = DatabaseHelper.COL_NOTA_PERIODO + "=?";
        String[] selectionArgs = {periodo};

        Cursor cursor = database.query(DatabaseHelper.TABLE_NOTAS, null, selection, selectionArgs, null, null, null);
        
        if (cursor.moveToFirst()) {
            do {
                Nota nota = createNotaFromCursor(cursor);
                notas.add(nota);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        closeDatabase();
        return notas;
    }

    public long insertNota(Nota nota) {
        openDatabase();
        ContentValues values = new ContentValues();
        
        values.put(DatabaseHelper.COL_NOTA_DISCIPLINA_ID, nota.getDisciplinaId());
        values.put(DatabaseHelper.COL_NOTA_PERIODO, nota.getPeriodo());
        values.put(DatabaseHelper.COL_NOTA_NT1, nota.getNt1());
        values.put(DatabaseHelper.COL_NOTA_NT2, nota.getNt2());
        values.put(DatabaseHelper.COL_NOTA_NT3, nota.getNt3());
        values.put(DatabaseHelper.COL_NOTA_MEDIA_FINAL, nota.getMediaFinal());
        values.put(DatabaseHelper.COL_NOTA_NAF, nota.getNaf());
        values.put(DatabaseHelper.COL_NOTA_FINAL, nota.getNotaFinal());
        values.put(DatabaseHelper.COL_NOTA_SITUACAO, nota.getSituacao());

        long result = database.insert(DatabaseHelper.TABLE_NOTAS, null, values);
        closeDatabase();
        return result;
    }

    // Métodos para Faltas
    public List<Falta> getFaltasByPeriodo(String periodo) {
        List<Falta> faltas = new ArrayList<>();
        openDatabase();

        String selection = DatabaseHelper.COL_FALTA_PERIODO + "=?";
        String[] selectionArgs = {periodo};

        Cursor cursor = database.query(DatabaseHelper.TABLE_FALTAS, null, selection, selectionArgs, null, null, null);
        
        if (cursor.moveToFirst()) {
            do {
                Falta falta = createFaltaFromCursor(cursor);
                faltas.add(falta);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        closeDatabase();
        return faltas;
    }

    public long insertFalta(Falta falta) {
        openDatabase();
        ContentValues values = new ContentValues();
        
        values.put(DatabaseHelper.COL_FALTA_DISCIPLINA_ID, falta.getDisciplinaId());
        values.put(DatabaseHelper.COL_FALTA_PERIODO, falta.getPeriodo());
        values.put(DatabaseHelper.COL_FALTA_JANEIRO, falta.getJaneiro());
        values.put(DatabaseHelper.COL_FALTA_FEVEREIRO, falta.getFevereiro());
        values.put(DatabaseHelper.COL_FALTA_MARCO, falta.getMarco());
        values.put(DatabaseHelper.COL_FALTA_ABRIL, falta.getAbril());
        values.put(DatabaseHelper.COL_FALTA_MAIO, falta.getMaio());
        values.put(DatabaseHelper.COL_FALTA_JUNHO, falta.getJunho());
        values.put(DatabaseHelper.COL_FALTA_TOTAL, falta.getTotalFaltas());
        values.put(DatabaseHelper.COL_FALTA_PERCENTUAL, falta.getPercentual());

        long result = database.insert(DatabaseHelper.TABLE_FALTAS, null, values);
        closeDatabase();
        return result;
    }

    // Métodos auxiliares
    private Nota createNotaFromCursor(Cursor cursor) {
        Nota nota = new Nota();
        nota.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NOTA_ID)));
        nota.setDisciplinaId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NOTA_DISCIPLINA_ID)));
        nota.setPeriodo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NOTA_PERIODO)));
        
        if (!cursor.isNull(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NOTA_NT1))) {
            nota.setNt1(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NOTA_NT1)));
        }
        if (!cursor.isNull(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NOTA_NT2))) {
            nota.setNt2(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NOTA_NT2)));
        }
        if (!cursor.isNull(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NOTA_NT3))) {
            nota.setNt3(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NOTA_NT3)));
        }
        
        nota.setSituacao(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NOTA_SITUACAO)));
        return nota;
    }

    private Falta createFaltaFromCursor(Cursor cursor) {
        Falta falta = new Falta();
        falta.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FALTA_ID)));
        falta.setDisciplinaId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FALTA_DISCIPLINA_ID)));
        falta.setPeriodo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FALTA_PERIODO)));
        falta.setJaneiro(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FALTA_JANEIRO)));
        falta.setFevereiro(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FALTA_FEVEREIRO)));
        falta.setMarco(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FALTA_MARCO)));
        falta.setAbril(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FALTA_ABRIL)));
        falta.setMaio(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FALTA_MAIO)));
        falta.setJunho(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FALTA_JUNHO)));
        falta.setTotalFaltas(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FALTA_TOTAL)));
        falta.setPercentual(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FALTA_PERCENTUAL)));
        return falta;
    }

    // Método para forçar reset dos dados (para debug)
    public void forceResetData() {
        openDatabase();
        
        // Limpar todas as notas e faltas
        database.delete(DatabaseHelper.TABLE_FALTAS, null, null);
        database.delete(DatabaseHelper.TABLE_NOTAS, null, null);
        
        closeDatabase();
        
        // Repovoar
        populateInitialData();
    }

    // Método para popular dados de exemplo
    public void populateInitialData() {
        openDatabase();
        
        // Verificar se já existem dados para 2025.1
        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_NOTAS + 
                                         " WHERE " + DatabaseHelper.COL_NOTA_PERIODO + " = '2025.1'", null);
        cursor.moveToFirst();
        int notasCount = cursor.getInt(0);
        cursor.close();
        
        cursor = database.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_FALTAS + 
                                  " WHERE " + DatabaseHelper.COL_FALTA_PERIODO + " = '2025.1'", null);
        cursor.moveToFirst();
        int faltasCount = cursor.getInt(0);
        cursor.close();
        
        // Se já existem dados, não inserir novamente
        if (notasCount > 0 && faltasCount > 0) {
            closeDatabase();
            return;
        }
        
        // Inserir notas de exemplo para o período 2025.1
        insertNotaExample(1, "2025.1", 10.0, 10.0, null, "CURSANDO");
        insertNotaExample(2, "2025.1", 6.0, null, null, "CURSANDO");
        insertNotaExample(3, "2025.1", null, null, null, "CURSANDO");
        insertNotaExample(4, "2025.1", 8.0, null, null, "CURSANDO");
        insertNotaExample(5, "2025.1", 7.5, null, null, "CURSANDO");
        
        // Inserir faltas de exemplo
        insertFaltaExample(1, "2025.1", 0, 12, 0, 4, 0, 0);
        insertFaltaExample(2, "2025.1", 0, 10, 2, 4, 2, 0);
        insertFaltaExample(3, "2025.1", 0, 0, 2, 0, 0, 0);
        insertFaltaExample(4, "2025.1", 0, 8, 1, 2, 1, 0);
        insertFaltaExample(5, "2025.1", 0, 5, 2, 1, 2, 0);
        
        closeDatabase();
    }

    // Método auxiliar para inserir notas de exemplo
    private void insertNotaExample(int disciplinaId, String periodo, Double nt1, Double nt2, Double nt3, String situacao) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_NOTA_DISCIPLINA_ID, disciplinaId);
        values.put(DatabaseHelper.COL_NOTA_PERIODO, periodo);
        if (nt1 != null) values.put(DatabaseHelper.COL_NOTA_NT1, nt1);
        if (nt2 != null) values.put(DatabaseHelper.COL_NOTA_NT2, nt2);
        if (nt3 != null) values.put(DatabaseHelper.COL_NOTA_NT3, nt3);
        values.put(DatabaseHelper.COL_NOTA_SITUACAO, situacao);
        
        database.insert(DatabaseHelper.TABLE_NOTAS, null, values);
    }

    // Método auxiliar para inserir faltas de exemplo
    private void insertFaltaExample(int disciplinaId, String periodo, int jan, int fev, int mar, int abr, int mai, int jun) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_FALTA_DISCIPLINA_ID, disciplinaId);
        values.put(DatabaseHelper.COL_FALTA_PERIODO, periodo);
        values.put(DatabaseHelper.COL_FALTA_JANEIRO, jan);
        values.put(DatabaseHelper.COL_FALTA_FEVEREIRO, fev);
        values.put(DatabaseHelper.COL_FALTA_MARCO, mar);
        values.put(DatabaseHelper.COL_FALTA_ABRIL, abr);
        values.put(DatabaseHelper.COL_FALTA_MAIO, mai);
        values.put(DatabaseHelper.COL_FALTA_JUNHO, jun);
        
        int total = jan + fev + mar + abr + mai + jun;
        values.put(DatabaseHelper.COL_FALTA_TOTAL, total);
        
        // Corrigir cálculo do percentual - considerando 75h como padrão
        double percentual = ((double) total / 75.0) * 100;
        values.put(DatabaseHelper.COL_FALTA_PERCENTUAL, percentual);
        
        database.insert(DatabaseHelper.TABLE_FALTAS, null, values);
    }

    // Método para resetar dados (útil para desenvolvimento/teste)
    public void resetInitialData() {
        openDatabase();
        
        // Limpar dados existentes
        database.delete(DatabaseHelper.TABLE_FALTAS, null, null);
        database.delete(DatabaseHelper.TABLE_NOTAS, null, null);
        
        closeDatabase();
        
        // Repovoar dados
        populateInitialData();
    }

    // Método para verificar se dados específicos existem
    public boolean hasDataForPeriodo(String periodo) {
        openDatabase();
        
        String query = "SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_NOTAS + 
                      " WHERE " + DatabaseHelper.COL_NOTA_PERIODO + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{periodo});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        
        closeDatabase();
        return count > 0;
    }

    // Método para atualizar percentuais de faltas existentes
    public void atualizarPercentuaisFaltas() {
        openDatabase();
        
        String query = "UPDATE " + DatabaseHelper.TABLE_FALTAS + 
                      " SET " + DatabaseHelper.COL_FALTA_PERCENTUAL + " = " +
                      "CAST(" + DatabaseHelper.COL_FALTA_TOTAL + " AS REAL) / 75.0 * 100";
        
        database.execSQL(query);
        closeDatabase();
    }

    // Método para obter faltas com cálculo correto
    public List<Falta> getFaltasComCalculoCorreto(String periodo) {
        List<Falta> faltas = getFaltasByPeriodo(periodo);
        
        for (Falta falta : faltas) {
            // Recalcular percentual com a regra correta
            falta.calcularTotalFaltas();
            falta.calcularPercentual(75.0); // CH padrão
        }
        
        return faltas;
    }

    // Método para obter histórico completo com JOIN
    public List<Historico> getHistoricoCompleto() {
        List<Historico> historico = new ArrayList<>();
        openDatabase();

        String query = "SELECT " +
                "d." + DatabaseHelper.COL_DISC_NOME + " as disciplina_nome, " +
                "d." + DatabaseHelper.COL_DISC_CODIGO + " as disciplina_codigo, " +
                "n." + DatabaseHelper.COL_NOTA_PERIODO + " as periodo, " +
                "n." + DatabaseHelper.COL_NOTA_SITUACAO + " as situacao, " +
                "n." + DatabaseHelper.COL_NOTA_FINAL + " as nota_final, " +
                "f." + DatabaseHelper.COL_FALTA_TOTAL + " as total_faltas " +
                "FROM " + DatabaseHelper.TABLE_NOTAS + " n " +
                "INNER JOIN " + DatabaseHelper.TABLE_DISCIPLINAS + " d ON n." + DatabaseHelper.COL_NOTA_DISCIPLINA_ID + " = d." + DatabaseHelper.COL_DISC_ID + " " +
                "LEFT JOIN " + DatabaseHelper.TABLE_FALTAS + " f ON n." + DatabaseHelper.COL_NOTA_DISCIPLINA_ID + " = f." + DatabaseHelper.COL_FALTA_DISCIPLINA_ID + " AND n." + DatabaseHelper.COL_NOTA_PERIODO + " = f." + DatabaseHelper.COL_FALTA_PERIODO + " " +
                "ORDER BY n." + DatabaseHelper.COL_NOTA_PERIODO + " DESC, d." + DatabaseHelper.COL_DISC_NOME;

        Cursor cursor = database.rawQuery(query, null);
        
        if (cursor.moveToFirst()) {
            do {
                String disciplinaNome = cursor.getString(cursor.getColumnIndexOrThrow("disciplina_nome"));
                String periodo = cursor.getString(cursor.getColumnIndexOrThrow("periodo"));
                String situacao = cursor.getString(cursor.getColumnIndexOrThrow("situacao"));
                
                double notaFinal = 0.0;
                if (!cursor.isNull(cursor.getColumnIndexOrThrow("nota_final"))) {
                    notaFinal = cursor.getDouble(cursor.getColumnIndexOrThrow("nota_final"));
                }
                
                int totalFaltas = 0;
                if (!cursor.isNull(cursor.getColumnIndexOrThrow("total_faltas"))) {
                    totalFaltas = cursor.getInt(cursor.getColumnIndexOrThrow("total_faltas"));
                }
                
                // Mapear período para formato de exibição
                String periodoFormatado = formatarPeriodo(periodo);
                
                Historico item = new Historico(periodoFormatado, disciplinaNome, periodo, situacao, notaFinal, totalFaltas);
                historico.add(item);
                
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        closeDatabase();
        return historico;
    }

    private String formatarPeriodo(String periodo) {
        // Converter "2021.2" para "1º PERÍODO", "2022.1" para "2º PERÍODO", etc.
        switch (periodo) {
            case "2021.2": return "1º PERÍODO";
            case "2022.1": return "2º PERÍODO";
            case "2022.2": return "3º PERÍODO";
            case "2023.1": return "4º PERÍODO";
            case "2023.2": return "5º PERÍODO";
            case "2024.1": return "6º PERÍODO";
            case "2024.2": return "7º PERÍODO";
            case "2025.1": return "8º PERÍODO";
            default: return periodo;
        }
    }
}
