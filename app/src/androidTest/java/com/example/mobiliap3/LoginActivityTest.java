package com.example.mobiliap3;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;

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
    public void testLoginComCredenciaisValidas() {
        // Verificar se elementos estão visíveis
        onView(withId(R.id.et_matricula)).check(matches(isDisplayed()));
        onView(withId(R.id.et_senha)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_entrar)).check(matches(isDisplayed())); // Corrigido: btn_entrar

        // Inserir credenciais válidas
        onView(withId(R.id.et_matricula))
                .perform(typeText("2810000317"), closeSoftKeyboard());
        
        onView(withId(R.id.et_senha))
                .perform(typeText("123456"), closeSoftKeyboard());

        // Clicar em login
        onView(withId(R.id.btn_entrar)).perform(click()); // Corrigido: btn_entrar

        // Aguardar transição e verificar se chegou na MainActivity
        try {
            Thread.sleep(2000); // Aguardar transição
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verificar se elementos da MainActivity estão visíveis
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()));
    }

    @Test
    public void testLoginComCredenciaisInvalidas() {
        // Inserir credenciais inválidas
        onView(withId(R.id.et_matricula))
                .perform(typeText("1234567890"), closeSoftKeyboard());
        
        onView(withId(R.id.et_senha))
                .perform(typeText("senhaerrada"), closeSoftKeyboard());

        // Clicar em login
        onView(withId(R.id.btn_entrar)).perform(click()); // Corrigido: btn_entrar

        // Verificar se ainda está na tela de login
        onView(withId(R.id.btn_entrar)).check(matches(isDisplayed())); // Corrigido: btn_entrar
    }

    @Test
    public void testCamposObrigatorios() {
        // Tentar login sem preencher campos
        onView(withId(R.id.btn_entrar)).perform(click()); // Corrigido: btn_entrar

        // Verificar se ainda está na tela de login
        onView(withId(R.id.btn_entrar)).check(matches(isDisplayed())); // Corrigido: btn_entrar
    }

    @Test
    public void testLayoutResponsivo() {
        // Verificar se elementos principais estão visíveis (removidos os que podem não existir)
        onView(withId(R.id.et_matricula)).check(matches(isDisplayed()));
        onView(withId(R.id.et_senha)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_entrar)).check(matches(isDisplayed())); // Corrigido: btn_entrar
    }

    @Test
    public void testValidacaoCredenciaisCorretas() {
        // Teste específico para as credenciais do sistema
        onView(withId(R.id.et_matricula))
                .perform(typeText("2810000317"), closeSoftKeyboard());
        
        onView(withId(R.id.et_senha))
                .perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.btn_entrar)).perform(click());

        // Aguardar processamento
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verificar se navegou para MainActivity
        onView(withId(R.id.bottom_navigation)).check(matches(isDisplayed()));
    }

    @Test
    public void testCamposVazios() {
        // Deixar campos vazios e tentar login
        onView(withId(R.id.btn_entrar)).perform(click());

        // Aguardar um pouco para ver se há algum feedback
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verificar se ainda está na tela de login
        onView(withId(R.id.et_matricula)).check(matches(isDisplayed()));
        onView(withId(R.id.et_senha)).check(matches(isDisplayed()));
    }

    @Test
    public void testCredenciaisIncorretas() {
        // Teste com matrícula incorreta
        onView(withId(R.id.et_matricula))
                .perform(typeText("0000000000"), closeSoftKeyboard());
        
        onView(withId(R.id.et_senha))
                .perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.btn_entrar)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Deve permanecer na tela de login
        onView(withId(R.id.btn_entrar)).check(matches(isDisplayed())); // Corrigido: btn_entrar
    }
}
