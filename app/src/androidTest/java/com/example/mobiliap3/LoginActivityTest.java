package com.example.mobiliap3;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.mobiliap3.activities.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testLoginElements() {
        onView(withId(R.id.et_matricula)).check(matches(isDisplayed()));
        onView(withId(R.id.et_senha)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_entrar)).check(matches(isDisplayed()));
    }

    @Test
    public void testLoginValid() {
        onView(withId(R.id.et_matricula))
                .perform(typeText("2810000317"), closeSoftKeyboard());
        onView(withId(R.id.et_senha))
                .perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.btn_entrar)).perform(click());

        // Aguardar menos tempo
        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()));
    }

    @Test
    public void testLoginInvalid() {
        onView(withId(R.id.et_matricula))
                .perform(typeText("0000000000"), closeSoftKeyboard());
        onView(withId(R.id.et_senha))
                .perform(typeText("wrong"), closeSoftKeyboard());
        onView(withId(R.id.btn_entrar)).perform(click());

        try { Thread.sleep(500); } catch (InterruptedException e) {}

        onView(withId(R.id.btn_entrar)).check(matches(isDisplayed()));
    }
}
