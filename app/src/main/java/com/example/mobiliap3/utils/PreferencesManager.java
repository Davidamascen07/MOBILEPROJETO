package com.example.mobiliap3.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    private static final String PREF_NAME = "mobiliap3_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NOME = "user_nome";
    private static final String KEY_USER_MATRICULA = "user_matricula";
    private static final String KEY_PERIODO_SELECIONADO = "periodo_selecionado";
    private static final String KEY_TEMA_ESCURO = "tema_escuro";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public PreferencesManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    // Login/Logout
    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Dados do usuário
    public void setUserData(int userId, String nome, String matricula) {
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NOME, nome);
        editor.putString(KEY_USER_MATRICULA, matricula);
        editor.apply();
    }

    public int getUserId() {
        return prefs.getInt(KEY_USER_ID, -1);
    }

    public String getUserNome() {
        return prefs.getString(KEY_USER_NOME, "");
    }

    public String getUserMatricula() {
        return prefs.getString(KEY_USER_MATRICULA, "");
    }

    // Período selecionado
    public void setPeriodoSelecionado(String periodo) {
        editor.putString(KEY_PERIODO_SELECIONADO, periodo);
        editor.apply();
    }

    public String getPeriodoSelecionado() {
        return prefs.getString(KEY_PERIODO_SELECIONADO, "2025.1");
    }

    // Tema
    public void setTemaEscuro(boolean temaEscuro) {
        editor.putBoolean(KEY_TEMA_ESCURO, temaEscuro);
        editor.apply();
    }

    public boolean isTemaEscuro() {
        return prefs.getBoolean(KEY_TEMA_ESCURO, false);
    }

    // Limpar dados
    public void logout() {
        editor.clear();
        editor.apply();
    }
}
