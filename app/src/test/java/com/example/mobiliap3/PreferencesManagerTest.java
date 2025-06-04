package com.example.mobiliap3;

import android.content.Context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.mobiliap3.utils.PreferencesManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28) // Fixar SDK para evitar problemas de compatibilidade
public class PreferencesManagerTest {

    private PreferencesManager preferencesManager;
    private Context context;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.getApplication();
        preferencesManager = new PreferencesManager(context);
    }

    @Test
    public void testLoginLogout() {
        preferencesManager.setLoggedIn(true);
        assertTrue("Deve estar logado", preferencesManager.isLoggedIn());

        preferencesManager.setLoggedIn(false);
        assertFalse("Não deve estar logado", preferencesManager.isLoggedIn());
    }

    @Test
    public void testUserData() {
        preferencesManager.setUserData(1, "David", "2810000317");

        assertEquals("ID correto", 1, preferencesManager.getUserId());
        assertEquals("Nome correto", "David", preferencesManager.getUserNome());
        assertEquals("Matrícula correta", "2810000317", preferencesManager.getUserMatricula());
    }

    @Test
    public void testPeriodoSelecionado() {
        preferencesManager.setPeriodoSelecionado("2025.1");
        assertEquals("Período correto", "2025.1", preferencesManager.getPeriodoSelecionado());
    }

    @Test
    public void testTemaEscuro() {
        preferencesManager.setTemaEscuro(true);
        assertTrue("Tema escuro ativo", preferencesManager.isTemaEscuro());

        preferencesManager.setTemaEscuro(false);
        assertFalse("Tema escuro inativo", preferencesManager.isTemaEscuro());
    }

    @Test
    public void testLogout() {
        // Setup dados
        preferencesManager.setLoggedIn(true);
        preferencesManager.setUserData(1, "David", "2810000317");
        
        // Logout
        preferencesManager.logout();
        
        // Verificar limpeza
        assertFalse("Deve estar deslogado", preferencesManager.isLoggedIn());
        assertEquals("ID resetado", -1, preferencesManager.getUserId());
        assertEquals("Nome limpo", "", preferencesManager.getUserNome());
        assertEquals("Matrícula limpa", "", preferencesManager.getUserMatricula());
    }
}
