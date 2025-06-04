package com.example.mobiliap3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.mobiliap3.models.Nota;

import org.junit.Test;

public class NotaTest {

    @Test
    public void testNotaCreationAndGetters() {
        Nota nota = new Nota();
        nota.setDisciplinaId(1);
        nota.setUsuarioId(1); // ADICIONAR usuarioId
        nota.setPeriodo("2025.1");
        nota.setNt1(8.0);
        nota.setNt2(7.0);
        nota.setSituacao("CURSANDO");
        
        assertEquals("Disciplina ID correto", 1, nota.getDisciplinaId());
        assertEquals("Usuario ID correto", 1, nota.getUsuarioId()); // ADICIONAR teste
        assertEquals("Período correto", "2025.1", nota.getPeriodo());
        assertEquals("NT1 correta", Double.valueOf(8.0), nota.getNt1());
        assertEquals("NT2 correta", Double.valueOf(7.0), nota.getNt2());
        assertEquals("Situação correta", "CURSANDO", nota.getSituacao());
    }

    @Test
    public void testCalcularMedia() {
        Nota nota = new Nota();
        nota.setNt1(8.0);
        nota.setNt2(7.0);
        nota.setNt3(9.0);
        
        nota.calcularMedia();
        
        assertNotNull("Média deve ser calculada", nota.getMediaFinal());
        assertEquals("Média correta", 8.0, nota.getMediaFinal(), 0.01);
    }

    @Test
    public void testCalculoMedia() {
        Nota nota = new Nota();
        nota.setNt1(8.0);
        nota.setNt2(7.0);
        
        nota.calcularMedia();
        
        assertEquals("Média deve ser 7.5", Double.valueOf(7.5), nota.getMediaFinal());
    }

    @Test
    public void testValidacaoNotas() {
        Nota nota = new Nota();
        nota.setNt1(10.0);
        nota.setNt2(8.5);
        nota.setNt3(7.2);
        
        assertTrue("NT1 válida", nota.getNt1() >= 0 && nota.getNt1() <= 10);
        assertTrue("NT2 válida", nota.getNt2() >= 0 && nota.getNt2() <= 10);
        assertTrue("NT3 válida", nota.getNt3() >= 0 && nota.getNt3() <= 10);
    }

    @Test
    public void testCalcularNotaFinal() {
        Nota nota = new Nota();
        nota.setMediaFinal(8.5);
        
        nota.calcularNotaFinal();
        
        assertEquals("Nota final deve ser igual à média (>=7)", 8.5, nota.getNotaFinal(), 0.01);
    }

    // ADICIONAR: Teste para determinação de situação
    @Test
    public void testDeterminacaoSituacao() {
        Nota nota = new Nota();
        
        // Teste aprovação direta
        nota.setNotaFinal(8.0);
        nota.determinarSituacao();
        assertEquals("Deve ser aprovado com nota 8.0", "APROVADO", nota.getSituacao());
        
        // Teste reprovação
        nota.setNotaFinal(3.0);
        nota.determinarSituacao();
        assertEquals("Deve ser reprovado com nota 3.0", "REPROVADO", nota.getSituacao());
        
        // Teste cursando (sem nota final)
        nota.setNotaFinal(null);
        nota.determinarSituacao();
        assertEquals("Deve estar cursando sem nota final", "CURSANDO", nota.getSituacao());
    }
}
