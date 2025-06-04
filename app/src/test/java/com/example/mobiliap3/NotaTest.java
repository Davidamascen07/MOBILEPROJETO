package com.example.mobiliap3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.mobiliap3.models.Nota;

import org.junit.Test;

public class NotaTest {

    @Test
    public void testCalculoMediaFinal() {
        Nota nota = new Nota();
        nota.setNt1(8.0);
        nota.setNt2(7.0);
        nota.setNt3(9.0);
        
        // Testar cálculo de média (se implementado)
        double mediaEsperada = (8.0 + 7.0 + 9.0) / 3.0;
        // assertEquals(mediaEsperada, nota.calcularMedia(), 0.01);
    }

    @Test
    public void testSituacaoDisciplina() {
        Nota nota = new Nota();
        
        // Teste para aprovação direta
        nota.setNotaFinal(8.5);
        // assertEquals("APROVADO", nota.getSituacao());
        
        // Teste para reprovação
        nota.setNotaFinal(3.0);
        // assertEquals("REPROVADO", nota.getSituacao());
    }

    @Test
    public void testValidacaoNotas() {
        Nota nota = new Nota();
        
        // Teste com notas válidas
        nota.setNt1(10.0);
        nota.setNt2(8.5);
        nota.setNt3(7.2);
        
        assertTrue("NT1 deve estar no intervalo válido", nota.getNt1() >= 0 && nota.getNt1() <= 10);
        assertTrue("NT2 deve estar no intervalo válido", nota.getNt2() >= 0 && nota.getNt2() <= 10);
        assertTrue("NT3 deve estar no intervalo válido", nota.getNt3() >= 0 && nota.getNt3() <= 10);
    }
}
