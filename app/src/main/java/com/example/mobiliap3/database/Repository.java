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
import com.example.mobiliap3.models.DisciplinaCompleta;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public Repository(Context context) {
        dbHelper = new DatabaseHelper(context);
        
        // TEMPORÁRIO: Forçar reset a cada inicialização (remover após teste)
        // forceRecreateDatabase();
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

    // Métodos para Notas - FILTRADOS POR USUÁRIO
    public List<Nota> getNotasByPeriodo(String periodo, int usuarioId) {
        List<Nota> notas = new ArrayList<>();
        openDatabase();

        String selection = DatabaseHelper.COL_NOTA_PERIODO + "=? AND " + 
                          DatabaseHelper.COL_NOTA_USUARIO_ID + "=?";
        String[] selectionArgs = {periodo, String.valueOf(usuarioId)};

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

    // Métodos para Faltas - FILTRADOS POR USUÁRIO
    public List<Falta> getFaltasByPeriodo(String periodo, int usuarioId) {
        List<Falta> faltas = new ArrayList<>();
        openDatabase();

        String selection = DatabaseHelper.COL_FALTA_PERIODO + "=? AND " + 
                          DatabaseHelper.COL_FALTA_USUARIO_ID + "=?";
        String[] selectionArgs = {periodo, String.valueOf(usuarioId)};

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
        
        // USAR usuarioId padrão (1) para dados de exemplo
        int usuarioIdPadrao = 1;
        
        // Inserir notas de exemplo para o período 2025.1
        insertNotaExample(1, "2025.1", 10.0, 10.0, null, "CURSANDO", usuarioIdPadrao);
        insertNotaExample(2, "2025.1", 6.0, null, null, "CURSANDO", usuarioIdPadrao);
        insertNotaExample(3, "2025.1", null, null, null, "CURSANDO", usuarioIdPadrao);
        insertNotaExample(4, "2025.1", 8.0, null, null, "CURSANDO", usuarioIdPadrao);
        insertNotaExample(5, "2025.1", 7.5, null, null, "CURSANDO", usuarioIdPadrao);
        
        // Inserir faltas de exemplo
        insertFaltaExample(1, "2025.1", 0, 12, 0, 4, 0, 0, usuarioIdPadrao);
        insertFaltaExample(2, "2025.1", 0, 10, 2, 4, 2, 0, usuarioIdPadrao);
        insertFaltaExample(3, "2025.1", 0, 0, 2, 0, 0, 0, usuarioIdPadrao);
        insertFaltaExample(4, "2025.1", 0, 8, 1, 2, 1, 0, usuarioIdPadrao);
        insertFaltaExample(5, "2025.1", 0, 5, 2, 1, 2, 0, usuarioIdPadrao);
        
        closeDatabase();
    }

    // Método auxiliar para inserir notas de exemplo - CORRIGIDO
    private void insertNotaExample(int disciplinaId, String periodo, Double nt1, Double nt2, Double nt3, String situacao, int usuarioId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_NOTA_DISCIPLINA_ID, disciplinaId);
        values.put(DatabaseHelper.COL_NOTA_PERIODO, periodo);
        values.put(DatabaseHelper.COL_NOTA_USUARIO_ID, usuarioId); // ADICIONAR usuarioId
        if (nt1 != null) values.put(DatabaseHelper.COL_NOTA_NT1, nt1);
        if (nt2 != null) values.put(DatabaseHelper.COL_NOTA_NT2, nt2);
        if (nt3 != null) values.put(DatabaseHelper.COL_NOTA_NT3, nt3);
        values.put(DatabaseHelper.COL_NOTA_SITUACAO, situacao);
        
        database.insert(DatabaseHelper.TABLE_NOTAS, null, values);
    }

    // Método auxiliar para inserir faltas de exemplo - CORRIGIDO
    private void insertFaltaExample(int disciplinaId, String periodo, int jan, int fev, int mar, int abr, int mai, int jun, int usuarioId) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_FALTA_DISCIPLINA_ID, disciplinaId);
        values.put(DatabaseHelper.COL_FALTA_PERIODO, periodo);
        values.put(DatabaseHelper.COL_FALTA_USUARIO_ID, usuarioId); // ADICIONAR usuarioId
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

    // ADICIONAR método para popular dados para usuário específico
    public void populateInitialDataForUser(int usuarioId) {
        openDatabase();
        
        // Verificar se já existem dados para este usuário
        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_NOTAS + 
                                         " WHERE " + DatabaseHelper.COL_NOTA_USUARIO_ID + " = ? AND " +
                                         DatabaseHelper.COL_NOTA_PERIODO + " = '2025.1'", 
                                         new String[]{String.valueOf(usuarioId)});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        
        // Se já existem dados para este usuário, não inserir novamente
        if (count > 0) {
            closeDatabase();
            return;
        }
        
        // Inserir dados para o usuário específico
        insertNotaExample(1, "2025.1", 10.0, 10.0, null, "CURSANDO", usuarioId);
        insertNotaExample(2, "2025.1", 6.0, null, null, "CURSANDO", usuarioId);
        insertNotaExample(3, "2025.1", null, null, null, "CURSANDO", usuarioId);
        insertNotaExample(4, "2025.1", 8.0, null, null, "CURSANDO", usuarioId);
        insertNotaExample(5, "2025.1", 7.5, null, null, "CURSANDO", usuarioId);
        
        insertFaltaExample(1, "2025.1", 0, 12, 0, 4, 0, 0, usuarioId);
        insertFaltaExample(2, "2025.1", 0, 10, 2, 4, 2, 0, usuarioId);
        insertFaltaExample(3, "2025.1", 0, 0, 2, 0, 0, 0, usuarioId);
        insertFaltaExample(4, "2025.1", 0, 8, 1, 2, 1, 0, usuarioId);
        insertFaltaExample(5, "2025.1", 0, 5, 2, 1, 2, 0, usuarioId);
        
        closeDatabase();
    }

    // Corrigir método insertNota para incluir usuarioId
    public long insertNota(Nota nota) {
        openDatabase();
        ContentValues values = new ContentValues();
        
        values.put(DatabaseHelper.COL_NOTA_DISCIPLINA_ID, nota.getDisciplinaId());
        values.put(DatabaseHelper.COL_NOTA_PERIODO, nota.getPeriodo());
        values.put(DatabaseHelper.COL_NOTA_USUARIO_ID, nota.getUsuarioId()); // ADICIONAR usuarioId
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

    // Corrigir método insertFalta para incluir usuarioId
    public long insertFalta(Falta falta) {
        openDatabase();
        ContentValues values = new ContentValues();
        
        values.put(DatabaseHelper.COL_FALTA_DISCIPLINA_ID, falta.getDisciplinaId());
        values.put(DatabaseHelper.COL_FALTA_PERIODO, falta.getPeriodo());
        values.put(DatabaseHelper.COL_FALTA_USUARIO_ID, falta.getUsuarioId()); // ADICIONAR usuarioId
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

    // Corrigir método createNotaFromCursor para incluir usuarioId
    private Nota createNotaFromCursor(Cursor cursor) {
        Nota nota = new Nota();
        nota.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NOTA_ID)));
        nota.setDisciplinaId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NOTA_DISCIPLINA_ID)));
        nota.setPeriodo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NOTA_PERIODO)));
        nota.setUsuarioId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_NOTA_USUARIO_ID))); // ADICIONAR usuarioId
        
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

    // Corrigir método createFaltaFromCursor para incluir usuarioId
    private Falta createFaltaFromCursor(Cursor cursor) {
        Falta falta = new Falta();
        falta.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FALTA_ID)));
        falta.setDisciplinaId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FALTA_DISCIPLINA_ID)));
        falta.setPeriodo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FALTA_PERIODO)));
        falta.setUsuarioId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FALTA_USUARIO_ID))); // ADICIONAR usuarioId
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

    // ATUALIZAR: Método para obter faltas com cálculo correto baseado na disciplina
    public List<Falta> getFaltasComCalculoCorreto(String periodo, int usuarioId) {
        List<Falta> faltas = getFaltasByPeriodo(periodo, usuarioId);
        
        for (Falta falta : faltas) {
            // Recalcular percentual com a carga horária REAL da disciplina
            double cargaHoraria = getCargaHorariaDisciplina(falta.getDisciplinaId());
            falta.calcularTotalFaltas();
            falta.calcularPercentual(cargaHoraria);
            
            android.util.Log.d("Repository", String.format(
                "Disciplina ID: %d, CH: %.0fh, Faltas: %d, Percentual: %.2f%%", 
                falta.getDisciplinaId(), cargaHoraria, falta.getTotalFaltas(), falta.getPercentual()
            ));
        }
        
        return faltas;
    }

    // ADICIONAR: Método para obter carga horária por disciplina
    private double getCargaHorariaDisciplina(int disciplinaId) {
        openDatabase();
        
        String query = "SELECT " + DatabaseHelper.COL_DISC_CH + 
                      " FROM " + DatabaseHelper.TABLE_DISCIPLINAS + 
                      " WHERE " + DatabaseHelper.COL_DISC_ID + " = ?";
        
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(disciplinaId)});
        
        double cargaHoraria = 75.0; // Padrão
        if (cursor.moveToFirst()) {
            cargaHoraria = cursor.getDouble(0);
        }
        
        cursor.close();
        closeDatabase();
        return cargaHoraria;
    }

    // ADICIONAR: Método para atualizar percentuais existentes no banco
    public void atualizarPercentuaisComCargaCorreta() {
        openDatabase();
        
        // Buscar todas as faltas
        String query = "SELECT f.*, d." + DatabaseHelper.COL_DISC_CH + 
                      " FROM " + DatabaseHelper.TABLE_FALTAS + " f " +
                      " INNER JOIN " + DatabaseHelper.TABLE_DISCIPLINAS + " d " +
                      " ON f." + DatabaseHelper.COL_FALTA_DISCIPLINA_ID + " = d." + DatabaseHelper.COL_DISC_ID;
        
        Cursor cursor = database.rawQuery(query, null);
        
        if (cursor.moveToFirst()) {
            do {
                int faltaId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FALTA_ID));
                int totalFaltas = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FALTA_TOTAL));
                double cargaHoraria = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DISC_CH));
                
                // Recalcular percentual correto
                double novoPercentual = (totalFaltas / cargaHoraria) * 100.0;
                
                // Atualizar no banco
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COL_FALTA_PERCENTUAL, novoPercentual);
                
                database.update(DatabaseHelper.TABLE_FALTAS, values, 
                               DatabaseHelper.COL_FALTA_ID + "=?", 
                               new String[]{String.valueOf(faltaId)});
                
                android.util.Log.d("Repository", String.format(
                    "Atualizado Falta ID: %d, CH: %.0fh, Faltas: %d, Novo %%: %.2f%%", 
                    faltaId, cargaHoraria, totalFaltas, novoPercentual
                ));
                
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        closeDatabase();
    }

    // Histórico filtrado por usuário - MÉTODO PRINCIPAL CORRIGIDO
    public List<Historico> getHistoricoCompleto(int usuarioId) {
        List<Historico> historico = new ArrayList<>();
        
        // Validar parâmetro
        if (usuarioId <= 0) {
            android.util.Log.e("Repository", "usuarioId inválido: " + usuarioId);
            return historico;
        }
        
        openDatabase();

        String query = "SELECT " +
                "d." + DatabaseHelper.COL_DISC_ID + " as disciplina_id, " +
                "d." + DatabaseHelper.COL_DISC_NOME + " as disciplina_nome, " +
                "n." + DatabaseHelper.COL_NOTA_PERIODO + " as periodo, " +
                "n." + DatabaseHelper.COL_NOTA_SITUACAO + " as situacao, " +
                "n." + DatabaseHelper.COL_NOTA_FINAL + " as nota_final, " +
                "f." + DatabaseHelper.COL_FALTA_TOTAL + " as total_faltas " +
                "FROM " + DatabaseHelper.TABLE_NOTAS + " n " +
                "INNER JOIN " + DatabaseHelper.TABLE_DISCIPLINAS + " d ON n." + DatabaseHelper.COL_NOTA_DISCIPLINA_ID + " = d." + DatabaseHelper.COL_DISC_ID + " " +
                "LEFT JOIN " + DatabaseHelper.TABLE_FALTAS + " f ON n." + DatabaseHelper.COL_NOTA_DISCIPLINA_ID + " = f." + DatabaseHelper.COL_FALTA_DISCIPLINA_ID + " AND n." + DatabaseHelper.COL_NOTA_PERIODO + " = f." + DatabaseHelper.COL_FALTA_PERIODO + " AND n." + DatabaseHelper.COL_NOTA_USUARIO_ID + " = f." + DatabaseHelper.COL_FALTA_USUARIO_ID + " " +
                "WHERE n." + DatabaseHelper.COL_NOTA_USUARIO_ID + " = ? " +
                "ORDER BY n." + DatabaseHelper.COL_NOTA_PERIODO + " DESC, d." + DatabaseHelper.COL_DISC_NOME;

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(usuarioId)});
        
        if (cursor.moveToFirst()) {
            do {
                Historico hist = new Historico();
                
                // INCLUIR disciplinaId
                int disciplinaIdIndex = cursor.getColumnIndex("disciplina_id");
                if (disciplinaIdIndex != -1) {
                    hist.setDisciplinaId(cursor.getInt(disciplinaIdIndex));
                }
                
                String disciplinaNome = cursor.getString(cursor.getColumnIndexOrThrow("disciplina_nome"));
                hist.setDisciplinaNome(disciplinaNome);
                
                String periodo = cursor.getString(cursor.getColumnIndexOrThrow("periodo"));
                hist.setPeriodo(periodo);
                
                String situacao = cursor.getString(cursor.getColumnIndexOrThrow("situacao"));
                hist.setSituacao(situacao);
                
                int notaFinalIndex = cursor.getColumnIndex("nota_final");
                if (notaFinalIndex != -1 && !cursor.isNull(notaFinalIndex)) {
                    double notaFinal = cursor.getDouble(notaFinalIndex);
                    hist.setNotaFinal(notaFinal);
                }
                
                int totalFaltasIndex = cursor.getColumnIndex("total_faltas");
                if (totalFaltasIndex != -1 && !cursor.isNull(totalFaltasIndex)) {
                    int totalFaltas = cursor.getInt(totalFaltasIndex);
                    hist.setTotalFaltas(totalFaltas);
                }
                
                historico.add(hist);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        closeDatabase();
        return historico;
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

    // CORRIGIR método para limpar completamente o banco
    public void limparTodosOsDados() {
        openDatabase();
        
        // Limpar todas as tabelas
        database.delete(DatabaseHelper.TABLE_FALTAS, null, null);
        database.delete(DatabaseHelper.TABLE_NOTAS, null, null);
        database.delete(DatabaseHelper.TABLE_DISCIPLINAS, null, null);
        database.delete(DatabaseHelper.TABLE_USUARIOS, null, null);
        
        closeDatabase();
        
        // Recriar dados iniciais usando método público
        dbHelper.insertInitialData(dbHelper.getWritableDatabase());
    }

    // Método para forçar recriação do banco (chama o método do DatabaseHelper)
    public void forceRecreateDatabase() {
        closeDatabase();
        dbHelper.forceRecreateDatabase();
    }

    // NOVO: Método unificado para obter dados completos por período
    public List<DisciplinaCompleta> getDisciplinasCompletasByPeriodo(String periodo, int usuarioId) {
        List<DisciplinaCompleta> disciplinasCompletas = new ArrayList<>();
        openDatabase();

        String query = "SELECT " +
                "d." + DatabaseHelper.COL_DISC_ID + " as disciplina_id, " +
                "d." + DatabaseHelper.COL_DISC_CODIGO + " as codigo, " +
                "d." + DatabaseHelper.COL_DISC_NOME + " as nome, " +
                "d." + DatabaseHelper.COL_DISC_CREDITOS + " as creditos, " +
                "d." + DatabaseHelper.COL_DISC_CH + " as carga_horaria, " +
                "n." + DatabaseHelper.COL_NOTA_NT1 + " as nt1, " +
                "n." + DatabaseHelper.COL_NOTA_NT2 + " as nt2, " +
                "n." + DatabaseHelper.COL_NOTA_NT3 + " as nt3, " +
                "n." + DatabaseHelper.COL_NOTA_SITUACAO + " as situacao, " +
                "f." + DatabaseHelper.COL_FALTA_JANEIRO + " as janeiro, " +
                "f." + DatabaseHelper.COL_FALTA_FEVEREIRO + " as fevereiro, " +
                "f." + DatabaseHelper.COL_FALTA_MARCO + " as marco, " +
                "f." + DatabaseHelper.COL_FALTA_ABRIL + " as abril, " +
                "f." + DatabaseHelper.COL_FALTA_MAIO + " as maio, " +
                "f." + DatabaseHelper.COL_FALTA_JUNHO + " as junho, " +
                "f." + DatabaseHelper.COL_FALTA_TOTAL + " as total_faltas, " +
                "f." + DatabaseHelper.COL_FALTA_PERCENTUAL + " as percentual " +
                "FROM " + DatabaseHelper.TABLE_DISCIPLINAS + " d " +
                "INNER JOIN " + DatabaseHelper.TABLE_NOTAS + " n ON d." + DatabaseHelper.COL_DISC_ID + " = n." + DatabaseHelper.COL_NOTA_DISCIPLINA_ID + " " +
                "LEFT JOIN " + DatabaseHelper.TABLE_FALTAS + " f ON d." + DatabaseHelper.COL_DISC_ID + " = f." + DatabaseHelper.COL_FALTA_DISCIPLINA_ID + " AND n." + DatabaseHelper.COL_NOTA_PERIODO + " = f." + DatabaseHelper.COL_FALTA_PERIODO + " AND n." + DatabaseHelper.COL_NOTA_USUARIO_ID + " = f." + DatabaseHelper.COL_FALTA_USUARIO_ID + " " +
                "WHERE n." + DatabaseHelper.COL_NOTA_PERIODO + " = ? AND n." + DatabaseHelper.COL_NOTA_USUARIO_ID + " = ? " +
                "ORDER BY d." + DatabaseHelper.COL_DISC_ID + " ASC";

        android.util.Log.d("Repository", "Query: " + query);
        android.util.Log.d("Repository", "Parâmetros: periodo=" + periodo + ", usuarioId=" + usuarioId);

        Cursor cursor = database.rawQuery(query, new String[]{periodo, String.valueOf(usuarioId)});
        
        if (cursor.moveToFirst()) {
            do {
                DisciplinaCompleta dc = new DisciplinaCompleta();
                
                // Disciplina
                dc.setDisciplinaId(cursor.getInt(cursor.getColumnIndexOrThrow("disciplina_id")));
                dc.setCodigo(cursor.getString(cursor.getColumnIndexOrThrow("codigo")));
                dc.setNome(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
                dc.setCreditos(cursor.getInt(cursor.getColumnIndexOrThrow("creditos")));
                dc.setCargaHoraria(cursor.getDouble(cursor.getColumnIndexOrThrow("carga_horaria")));
                
                // Notas
                if (!cursor.isNull(cursor.getColumnIndexOrThrow("nt1"))) {
                    dc.setNt1(cursor.getDouble(cursor.getColumnIndexOrThrow("nt1")));
                }
                if (!cursor.isNull(cursor.getColumnIndexOrThrow("nt2"))) {
                    dc.setNt2(cursor.getDouble(cursor.getColumnIndexOrThrow("nt2")));
                }
                if (!cursor.isNull(cursor.getColumnIndexOrThrow("nt3"))) {
                    dc.setNt3(cursor.getDouble(cursor.getColumnIndexOrThrow("nt3")));
                }
                dc.setSituacao(cursor.getString(cursor.getColumnIndexOrThrow("situacao")));
                
                // Faltas
                dc.setJaneiro(cursor.getInt(cursor.getColumnIndexOrThrow("janeiro")));
                dc.setFevereiro(cursor.getInt(cursor.getColumnIndexOrThrow("fevereiro")));
                dc.setMarco(cursor.getInt(cursor.getColumnIndexOrThrow("marco")));
                dc.setAbril(cursor.getInt(cursor.getColumnIndexOrThrow("abril")));
                dc.setMaio(cursor.getInt(cursor.getColumnIndexOrThrow("maio")));
                dc.setJunho(cursor.getInt(cursor.getColumnIndexOrThrow("junho")));
                dc.setTotalFaltas(cursor.getInt(cursor.getColumnIndexOrThrow("total_faltas")));
                dc.setPercentual(cursor.getDouble(cursor.getColumnIndexOrThrow("percentual")));
                
                android.util.Log.d("Repository", "Disciplina: " + dc.getNome() + " (ID: " + dc.getDisciplinaId() + ")");
                android.util.Log.d("Repository", "Notas: NT1=" + dc.getNt1() + ", NT2=" + dc.getNt2() + ", NT3=" + dc.getNt3());
                android.util.Log.d("Repository", "Faltas: Total=" + dc.getTotalFaltas() + ", Percentual=" + dc.getPercentual());
                
                disciplinasCompletas.add(dc);
            } while (cursor.moveToNext());
        } else {
            android.util.Log.w("Repository", "Nenhum resultado encontrado para período: " + periodo + ", usuário: " + usuarioId);
        }
        
        cursor.close();
        closeDatabase();
        return disciplinasCompletas;
    }

    // Método para converter DisciplinaCompleta em Nota
    public Nota disciplinaCompletaToNota(DisciplinaCompleta dc) {
        Nota nota = new Nota();
        nota.setDisciplinaId(dc.getDisciplinaId());
        nota.setNt1(dc.getNt1());
        nota.setNt2(dc.getNt2());
        nota.setNt3(dc.getNt3());
        nota.setSituacao(dc.getSituacao());
        return nota;
    }

    // Método para converter DisciplinaCompleta em Falta
    public Falta disciplinaCompletaToFalta(DisciplinaCompleta dc) {
        Falta falta = new Falta();
        falta.setDisciplinaId(dc.getDisciplinaId());
        falta.setJaneiro(dc.getJaneiro());
        falta.setFevereiro(dc.getFevereiro());
        falta.setMarco(dc.getMarco());
        falta.setAbril(dc.getAbril());
        falta.setMaio(dc.getMaio());
        falta.setJunho(dc.getJunho());
        falta.setTotalFaltas(dc.getTotalFaltas());
        falta.setPercentual(dc.getPercentual());
        return falta;
    }

    // ADICIONAR: Método para debug do histórico
    public void debugHistorico() {
        openDatabase();
        
        String query = "SELECT " +
                "d." + DatabaseHelper.COL_DISC_ID + " as disciplina_id, " +
                "d." + DatabaseHelper.COL_DISC_NOME + " as disciplina_nome, " +
                "n." + DatabaseHelper.COL_NOTA_PERIODO + " as periodo, " +
                "n." + DatabaseHelper.COL_NOTA_SITUACAO + " as situacao " +
                "FROM " + DatabaseHelper.TABLE_NOTAS + " n " +
                "INNER JOIN " + DatabaseHelper.TABLE_DISCIPLINAS + " d ON n." + DatabaseHelper.COL_NOTA_DISCIPLINA_ID + " = d." + DatabaseHelper.COL_DISC_ID + " " +
                "WHERE d." + DatabaseHelper.COL_DISC_NOME + " LIKE '%ESTÁGIO%' " +
                "ORDER BY n." + DatabaseHelper.COL_NOTA_PERIODO + " DESC";

        Cursor cursor = database.rawQuery(query, null);
        
        android.util.Log.d("Repository", "=== DEBUG HISTÓRICO ESTÁGIOS ===");
        
        if (cursor.moveToFirst()) {
            do {
                int disciplinaId = cursor.getInt(cursor.getColumnIndexOrThrow("disciplina_id"));
                String disciplinaNome = cursor.getString(cursor.getColumnIndexOrThrow("disciplina_nome"));
                String periodo = cursor.getString(cursor.getColumnIndexOrThrow("periodo"));
                String situacao = cursor.getString(cursor.getColumnIndexOrThrow("situacao"));
                
                android.util.Log.d("Repository", String.format(
                    "ID: %d | %s | %s | %s", 
                    disciplinaId, disciplinaNome, periodo, situacao
                ));
                
            } while (cursor.moveToNext());
        } else {
            android.util.Log.w("Repository", "Nenhuma disciplina de ESTÁGIO encontrada");
        }
        
        cursor.close();
        closeDatabase();
    }

    // ADICIONAR: Método para verificar consistência dos dados
    public void verificarConsistenciaDados() {
        openDatabase();
        
        // Verificar disciplinas duplicadas
        String queryDuplicadas = "SELECT " + DatabaseHelper.COL_DISC_NOME + ", COUNT(*) as count " +
                                "FROM " + DatabaseHelper.TABLE_DISCIPLINAS + " " +
                                "GROUP BY " + DatabaseHelper.COL_DISC_NOME + " " +
                                "HAVING COUNT(*) > 1";
        
        Cursor cursor = database.rawQuery(queryDuplicadas, null);
        
        if (cursor.moveToFirst()) {
            android.util.Log.w("Repository", "=== DISCIPLINAS DUPLICADAS ===");
            do {
                String nome = cursor.getString(0);
                int count = cursor.getInt(1);
                android.util.Log.w("Repository", nome + " aparece " + count + " vezes");
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        
        // Verificar total de disciplinas
        String queryTotal = "SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_DISCIPLINAS;
        cursor = database.rawQuery(queryTotal, null);
        cursor.moveToFirst();
        int totalDisciplinas = cursor.getInt(0);
        cursor.close();
        
        android.util.Log.d("Repository", "Total de disciplinas: " + totalDisciplinas);
        
        closeDatabase();
    }
}
