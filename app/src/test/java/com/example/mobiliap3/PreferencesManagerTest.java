package com.example.mobiliap3;

import android.content.Context;
import android.content.SharedPreferences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.mobiliap3.utils.PreferencesManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

@RunWith(RobolectricTestRunner.class)
public class PreferencesManagerTest {

    private PreferencesManager preferencesManager;
    private Context context;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.getApplication();
        preferencesManager = new PreferencesManager(context);
    }

    @Test
    public void testSetAndGetLoggedIn() {
        // Test setting logged in to true
        preferencesManager.setLoggedIn(true);
        assertTrue("User should be logged in", preferencesManager.isLoggedIn());

        // Test setting logged in to false
        preferencesManager.setLoggedIn(false);
        assertFalse("User should not be logged in", preferencesManager.isLoggedIn());
    }

    @Test
    public void testSetAndGetUserData() {
        // Set user data
        preferencesManager.setUserData(1, "David Damasceno da Frota", "2810000317");

        // Verify user data
        assertEquals("User ID should match", 1, preferencesManager.getUserId());
        assertEquals("User name should match", "David Damasceno da Frota", preferencesManager.getUserNome());
        assertEquals("User matricula should match", "2810000317", preferencesManager.getUserMatricula());
    }

    @Test
    public void testSetAndGetPeriodoSelecionado() {
        // Test setting period
        preferencesManager.setPeriodoSelecionado("2025.1");
        assertEquals("Period should match", "2025.1", preferencesManager.getPeriodoSelecionado());

        // Test with different period
        preferencesManager.setPeriodoSelecionado("2024.2");
        assertEquals("Period should match updated value", "2024.2", preferencesManager.getPeriodoSelecionado());
    }

    @Test
    public void testDefaultPeriodoSelecionado() {
        // Test default period (without setting)
        PreferencesManager newPrefsManager = new PreferencesManager(context);
        assertEquals("Default period should be 2025.1", "2025.1", newPrefsManager.getPeriodoSelecionado());
    }

    @Test
    public void testSetAndGetTemaEscuro() {
        // Test setting dark theme
        preferencesManager.setTemaEscuro(true);
        assertTrue("Dark theme should be enabled", preferencesManager.isTemaEscuro());

        // Test setting light theme
        preferencesManager.setTemaEscuro(false);
        assertFalse("Dark theme should be disabled", preferencesManager.isTemaEscuro());
    }

    @Test
    public void testDefaultTemaEscuro() {
        // Test default theme (should be false)
        PreferencesManager newPrefsManager = new PreferencesManager(context);
        assertFalse("Default theme should be light", newPrefsManager.isTemaEscuro());
    }

    @Test
    public void testLogout() {
        // Set some data first
        preferencesManager.setLoggedIn(true);
        preferencesManager.setUserData(1, "David Damasceno da Frota", "2810000317");
        preferencesManager.setPeriodoSelecionado("2025.1");
        preferencesManager.setTemaEscuro(true);

        // Verify data is set
        assertTrue("Should be logged in before logout", preferencesManager.isLoggedIn());

        // Logout
        preferencesManager.logout();

        // Verify all data is cleared
        assertFalse("Should not be logged in after logout", preferencesManager.isLoggedIn());
        assertEquals("User ID should be reset", -1, preferencesManager.getUserId());
        assertEquals("User name should be empty", "", preferencesManager.getUserNome());
        assertEquals("User matricula should be empty", "", preferencesManager.getUserMatricula());
        assertEquals("Period should be reset to default", "2025.1", preferencesManager.getPeriodoSelecionado());
        assertFalse("Theme should be reset to default", preferencesManager.isTemaEscuro());
    }

    @Test
    public void testGetUserIdDefault() {
        // Test default user ID (should be -1)
        PreferencesManager newPrefsManager = new PreferencesManager(context);
        assertEquals("Default user ID should be -1", -1, newPrefsManager.getUserId());
    }

    @Test
    public void testGetUserNomeDefault() {
        // Test default user name (should be empty)
        PreferencesManager newPrefsManager = new PreferencesManager(context);
        assertEquals("Default user name should be empty", "", newPrefsManager.getUserNome());
    }

    @Test
    public void testGetUserMatriculaDefault() {
        // Test default user matricula (should be empty)
        PreferencesManager newPrefsManager = new PreferencesManager(context);
        assertEquals("Default user matricula should be empty", "", newPrefsManager.getUserMatricula());
    }
}
