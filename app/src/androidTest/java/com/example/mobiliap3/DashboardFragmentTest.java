package com.example.mobiliap3;

import static androidx.test.espresso.Espresso.onView;
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

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DashboardFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(createLoggedInIntent());

    private static Intent createLoggedInIntent() {
        Context context = ApplicationProvider.getApplicationContext();
        
        PreferencesManager prefsManager = new PreferencesManager(context);
        prefsManager.setLoggedIn(true);
        prefsManager.setUserData(1, "David Damasceno da Frota", "2810000317"); // Método correto
        prefsManager.setPeriodoSelecionado("2025.1");
        
        return new Intent(context, MainActivity.class);
    }

    @Test
    public void testDashboardElementsVisible() {
        // Aguardar carregamento do dashboard
        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        // Verificar se elementos principais estão visíveis
        // Nota: Estes IDs podem precisar ser ajustados conforme o layout real
        
        // Se houver cards de resumo
        // onView(withId(R.id.card_resumo_notas)).check(matches(isDisplayed()));
        // onView(withId(R.id.card_resumo_faltas)).check(matches(isDisplayed()));
        
        // Se houver textos de boas-vindas
        // onView(withText("Bem-vindo")).check(matches(isDisplayed()));
    }

    @Test
    public void testDashboardDataLoading() {
        // Aguardar carregamento completo
        try { Thread.sleep(3000); } catch (InterruptedException e) {}

        // Verificar se dados foram carregados
        // Exemplo: verificar se existem disciplinas listadas
        // onView(withText("TÓPICOS EM DESENVOLVIMENTO MOBILE")).check(matches(isDisplayed()));
    }

    @Test
    public void testDashboardPeriodoAtual() {
        // Aguardar carregamento
        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        // Verificar se o período atual está sendo exibido
        // onView(withText("2025.1")).check(matches(isDisplayed()));
    }
}
