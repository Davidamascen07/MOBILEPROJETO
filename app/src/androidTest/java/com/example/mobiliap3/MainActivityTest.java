package com.example.mobiliap3;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

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
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(createLoggedInIntent());

    private static Intent createLoggedInIntent() {
        Context context = ApplicationProvider.getApplicationContext();
        PreferencesManager prefsManager = new PreferencesManager(context);
        prefsManager.setLoggedIn(true);
        prefsManager.setUserData(1, "David", "2810000317");
        prefsManager.setPeriodoSelecionado("2025.1");
        return new Intent(context, MainActivity.class);
    }

    @Test
    public void testBottomNavigation() {
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()));
    }

    @Test
    public void testNavigationTabs() {
        // Testes rápidos de navegação
        onView(withId(R.id.nav_notas)).perform(click());
        try { Thread.sleep(200); } catch (InterruptedException e) {}

        onView(withId(R.id.nav_faltas)).perform(click());
        try { Thread.sleep(200); } catch (InterruptedException e) {}

        onView(withId(R.id.nav_historico)).perform(click());
        try { Thread.sleep(200); } catch (InterruptedException e) {}

        onView(withId(R.id.nav_perfil)).perform(click());
        try { Thread.sleep(200); } catch (InterruptedException e) {}

        onView(withId(R.id.nav_dashboard)).perform(click());
        try { Thread.sleep(200); } catch (InterruptedException e) {}
    }
}
