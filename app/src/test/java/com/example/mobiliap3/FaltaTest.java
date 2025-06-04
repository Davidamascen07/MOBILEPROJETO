package com.example.mobiliap3;

import static org.junit.Assert.assertEquals;
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
        
        assertEquals("Total de faltas deve ser 12", 12, falta.getTotalFaltas());
    }

    @Test
    public void testCalculoPercentual() {
        Falta falta = new Falta();
        falta.setTotalFaltas(15);
        
        falta.calcularPercentual(75.0); // 75 horas totais
        
        assertEquals("Percentual deve ser 20%", 20.0, falta.getPercentual(), 0.1);
    }

    @Test
    public void testStatusFaltas() {
        Falta falta = new Falta();
        
        // Teste dentro do limite (< 20%)
        falta.setPercentual(15.0);
        assertEquals("DENTRO_LIMITE", falta.getStatusFaltas());
        
        // Teste próximo ao limite (20-24%)
        falta.setPercentual(22.0);
        assertEquals("PROXIMO_LIMITE", falta.getStatusFaltas());
        
        // Teste acima do limite (>= 25%)
        falta.setPercentual(26.0);
        assertEquals("ACIMA_LIMITE", falta.getStatusFaltas());
    }

    @Test
    public void testReprovarPorFalta() {
        Falta falta = new Falta();
        
        // Teste reprovação por falta
        falta.setPercentual(25.0);
        assertTrue("Deve reprovar com 25% de faltas", falta.podeReprovarPorFalta());
        
        // Teste aprovação
        falta.setPercentual(20.0);
        assertTrue("Não deve reprovar com 20% de faltas", !falta.podeReprovarPorFalta());
    }
}
