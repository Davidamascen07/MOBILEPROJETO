package com.example.mobiliap3.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    private static final String PREF_NAME = "app_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_MATRICULA = "user_matricula";
    private static final String KEY_PERIODO_SELECIONADO = "periodo_selecionado";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Login/Logout
    public void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Dados do usuário
    public void setUserData(int userId, String nome, String matricula) {
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NAME, nome);
        editor.putString(KEY_USER_MATRICULA, matricula);
        editor.apply();
    }

    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }

    public String getUserNome() {
        return sharedPreferences.getString(KEY_USER_NAME, "");
    }

    public String getUserMatricula() {
        return sharedPreferences.getString(KEY_USER_MATRICULA, "");
    }

    // Período selecionado
    public void setPeriodoSelecionado(String periodo) {
        editor.putString(KEY_PERIODO_SELECIONADO, periodo);
        editor.apply();
    }

    public String getPeriodoSelecionado() {
        return sharedPreferences.getString(KEY_PERIODO_SELECIONADO, "2025.1");
    }

    // Limpar dados
    public void logout() {
        editor.clear();
        editor.apply();
    }
}
