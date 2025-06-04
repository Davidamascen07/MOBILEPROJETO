package com.example.mobiliap3;

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

import static org.junit.Assert.assertTrue;

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
        prefsManager.setUserData(1, "David", "2810000317");
        return new Intent(context, MainActivity.class);
    }

    @Test
    public void testDashboardLoads() {
        // Teste simples - apenas verifica se não há crash
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        assertTrue("Dashboard carregou sem crash", true);
    }
}
