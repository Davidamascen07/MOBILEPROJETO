package com.example.mobiliap3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.mobiliap3.models.Falta;

import org.junit.Test;

public class FaltaTest {

    @Test
    public void testCalculoTotalFaltas() {
        Falta falta = new Falta();
        falta.setJaneiro(2);
        falta.setFevereiro(5);
        falta.setMarco(1);
        falta.setAbril(3);
        falta.setMaio(0);
        falta.setJunho(1);
        
        falta.calcularTotalFaltas();
        
        assertEquals("Total correto", 12, falta.getTotalFaltas());
    }

    @Test
    public void testCalculoPercentual() {
        Falta falta = new Falta();
        falta.setTotalFaltas(15);
        
        falta.calcularPercentual(75.0);
        
        assertEquals("Percentual correto", 20.0, falta.getPercentual(), 0.1);
    }

    @Test
    public void testStatusFaltas() {
        Falta falta = new Falta();
        
        falta.setPercentual(15.0);
        assertEquals("DENTRO_LIMITE", falta.getStatusFaltas());
        
        falta.setPercentual(22.0);
        assertEquals("PROXIMO_LIMITE", falta.getStatusFaltas());
        
        falta.setPercentual(26.0);
        assertEquals("ACIMA_LIMITE", falta.getStatusFaltas());
    }

    @Test
    public void testReprovarPorFalta() {
        Falta falta = new Falta();
        
        falta.setPercentual(25.0);
        assertTrue("Deve reprovar com 25%", falta.podeReprovarPorFalta());
        
        falta.setPercentual(20.0);
        assertFalse("Não deve reprovar com 20%", falta.podeReprovarPorFalta());
    }

    @Test
    public void testDescricaoStatus() {
        Falta falta = new Falta();
        
        falta.setPercentual(10.0);
        assertEquals("Dentro do limite permitido", falta.getDescricaoStatus());
        
        falta.setPercentual(23.0);
        assertEquals("Próximo ao limite (25%)", falta.getDescricaoStatus());
        
        falta.setPercentual(30.0);
        assertEquals("Acima do limite - Reprovação por falta", falta.getDescricaoStatus());
    }
}
