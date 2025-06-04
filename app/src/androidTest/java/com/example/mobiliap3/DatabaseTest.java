package com.example.mobiliap3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mobiliap3.database.Repository;
import com.example.mobiliap3.models.Disciplina;
import com.example.mobiliap3.models.Nota;
import com.example.mobiliap3.models.Falta;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    private Repository repository;
    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        repository = new Repository(context);
    }

    @Test
    public void testGetAllDisciplinas() {
        List<Disciplina> disciplinas = repository.getAllDisciplinas();
        
        assertNotNull("Lista de disciplinas não deve ser nula", disciplinas);
        assertTrue("Deve haver disciplinas cadastradas", disciplinas.size() > 0);
        
        // Verificar se alguma disciplina específica existe
        boolean foundMobile = false;
        for (Disciplina d : disciplinas) {
            if (d.getNome().contains("DESENVOLVIMENTO MOBILE")) {
                foundMobile = true;
                assertEquals("Disciplina deve ter 5 créditos", 5, d.getCreditos());
                assertEquals("Disciplina deve ter 75h", 75.0, d.getCargaHoraria(), 0.1);
                break;
            }
        }
        assertTrue("Disciplina de Mobile deve existir", foundMobile);
    }

    @Test
    public void testGetNotasByPeriodo() {
        List<Nota> notas = repository.getNotasByPeriodo("2025.1");
        
        assertNotNull("Lista de notas não deve ser nula", notas);
        assertTrue("Deve haver notas para 2025.1", notas.size() > 0);
        
        // Verificar se todas as notas são do período correto
        for (Nota nota : notas) {
            assertEquals("Todas as notas devem ser de 2025.1", "2025.1", nota.getPeriodo());
        }
    }

    @Test
    public void testGetFaltasByPeriodo() {
        // CORRIGIR: Usar usuarioId válido
        List<Falta> faltas = repository.getFaltasByPeriodo("2025.1", 1);
        
        assertNotNull("Lista de faltas não deve ser nula", faltas);
        assertTrue("Deve haver faltas para 2025.1", faltas.size() > 0);
        
        // Verificar cálculos de faltas com REGRAS CORRETAS
        for (Falta falta : faltas) {
            assertEquals("Período deve ser 2025.1", "2025.1", falta.getPeriodo());
            assertTrue("Total de faltas deve ser >= 0", falta.getTotalFaltas() >= 0);
            assertTrue("Percentual deve ser >= 0", falta.getPercentual() >= 0);
            
            // ADICIONAR: Verificar regra dos 25%
            if (falta.getPercentual() >= 25.0) {
                assertEquals("25% ou mais deve ser ACIMA_LIMITE", "ACIMA_LIMITE", falta.getStatusFaltas());
                assertTrue("25% ou mais deve reprovar", falta.podeReprovarPorFalta());
            } else if (falta.getPercentual() >= 20.0) {
                assertEquals("20-24% deve ser PROXIMO_LIMITE", "PROXIMO_LIMITE", falta.getStatusFaltas());
                assertFalse("20-24% não deve reprovar", falta.podeReprovarPorFalta());
            } else {
                assertEquals("Menos de 20% deve ser DENTRO_LIMITE", "DENTRO_LIMITE", falta.getStatusFaltas());
                assertFalse("Menos de 20% não deve reprovar", falta.podeReprovarPorFalta());
            }
        }
    }

    // ADICIONAR: Teste específico para validar dados de 24%
    @Test
    public void testFalta24PorcentoStatus() {
        List<Falta> faltas = repository.getFaltasByPeriodo("2025.1", 1);
        
        // Procurar falta com aproximadamente 24%
        for (Falta falta : faltas) {
            if (Math.abs(falta.getPercentual() - 24.0) < 1.0) { // Aproximadamente 24%
                assertEquals("24% deve ser PROXIMO_LIMITE", "PROXIMO_LIMITE", falta.getStatusFaltas());
                assertFalse("24% não deve reprovar", falta.podeReprovarPorFalta());
                assertEquals("Descrição deve indicar próximo ao limite", 
                    "Próximo ao limite (25%)", falta.getDescricaoStatus());
                break;
            }
        }
    }

    @Test
    public void testHistoricoCompleto() {
        // Verificar se o histórico possui dados de períodos anteriores
        List<Nota> notasHistoricas = repository.getNotasByPeriodo("2024.2");
        assertNotNull("Deve haver notas históricas", notasHistoricas);
        
        // Verificar disciplinas aprovadas
        boolean hasAprovado = false;
        for (Nota nota : notasHistoricas) {
            if ("APROVADO".equals(nota.getSituacao())) {
                hasAprovado = true;
                break;
            }
        }
        assertTrue("Deve haver disciplinas aprovadas no histórico", hasAprovado);
    }
}
