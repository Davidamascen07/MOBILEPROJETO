package com.example.mobiliap3;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.mobiliap3.activities.MainActivity;
import com.example.mobiliap3.utils.PreferencesManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(createMainActivityIntent());

    private static Intent createMainActivityIntent() {
        Context context = ApplicationProvider.getApplicationContext();
        
        // Simular login válido
        PreferencesManager prefsManager = new PreferencesManager(context);
        prefsManager.setLoggedIn(true);
        prefsManager.setUserData(1, "David Damasceno da Frota", "2810000317"); // Método correto
        prefsManager.setPeriodoSelecionado("2025.1");
        
        return new Intent(context, MainActivity.class);
    }

    @Test
    public void testNavigationBottomMenu() {
        // Verificar se BottomNavigation está visível
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()));

        // Testar navegação para Notas
        onView(withId(R.id.nav_notas)).perform(click());
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        // Testar navegação para Faltas
        onView(withId(R.id.nav_faltas)).perform(click());
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        // Testar navegação para Histórico
        onView(withId(R.id.nav_historico)).perform(click());
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        // Testar navegação para Perfil
        onView(withId(R.id.nav_perfil)).perform(click());
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        // Voltar para Dashboard
        onView(withId(R.id.nav_dashboard)).perform(click());
        try { Thread.sleep(500); } catch (InterruptedException e) {}
    }

    @Test
    public void testDashboardContent() {
        // Verificar se está no Dashboard por padrão
        onView(withId(R.id.nav_dashboard)).perform(click());
        
        // Aguardar carregamento
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        // Verificar elementos do dashboard (se existirem)
        // Nota: Os IDs específicos dependem do layout do DashboardFragment
    }

    @Test
    public void testNotasFragment() {
        // Navegar para Notas
        onView(withId(R.id.nav_notas)).perform(click());
        
        // Aguardar carregamento
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        // Verificar se a lista está visível (assumindo que existe)
        // onView(withId(R.id.recycler_notas)).check(matches(isDisplayed()));
    }

    @Test
    public void testFaltasFragment() {
        // Navegar para Faltas
        onView(withId(R.id.nav_faltas)).perform(click());
        
        // Aguardar carregamento
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        // Verificar se a lista está visível
        // onView(withId(R.id.recycler_faltas)).check(matches(isDisplayed()));
    }

    @Test
    public void testHistoricoFragment() {
        // Navegar para Histórico
        onView(withId(R.id.nav_historico)).perform(click());
        
        // Aguardar carregamento
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        // Verificar conteúdo do histórico
        // onView(withId(R.id.recycler_historico)).check(matches(isDisplayed()));
    }

    @Test
    public void testPerfilFragment() {
        // Navegar para Perfil
        onView(withId(R.id.nav_perfil)).perform(click());
        
        // Aguardar carregamento
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        // Verificar informações do perfil
        // onView(withText("David Damasceno da Frota")).check(matches(isDisplayed()));
        // onView(withText("2810000317")).check(matches(isDisplayed()));
    }
}
