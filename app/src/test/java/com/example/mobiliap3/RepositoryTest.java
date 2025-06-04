package com.example.mobiliap3;

import android.content.Context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.example.mobiliap3.database.Repository;
import com.example.mobiliap3.models.Disciplina;
import com.example.mobiliap3.models.Falta;
import com.example.mobiliap3.models.Historico;
import com.example.mobiliap3.models.Nota;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class RepositoryTest {

    private Repository repository;
    private Context context;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.getApplication();
        repository = new Repository(context);
        // Limpar e repovoar dados para teste
        repository.resetInitialData();
    }

    @Test
    public void testGetAllDisciplinas() {
        List<Disciplina> disciplinas = repository.getAllDisciplinas();
        
        assertNotNull("Lista de disciplinas não deve ser nula", disciplinas);
        assertTrue("Deve haver disciplinas cadastradas", disciplinas.size() > 0);
        
        // Verificar se existe a disciplina de Mobile Development
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
        
        // Verificar cálculos de faltas com limites corretos
        for (Falta falta : faltas) {
            assertEquals("Período deve ser 2025.1", "2025.1", falta.getPeriodo());
            assertTrue("Total de faltas deve ser >= 0", falta.getTotalFaltas() >= 0);
            assertTrue("Percentual deve ser >= 0", falta.getPercentual() >= 0);
            
            // ADICIONAR: Testar status correto
            String status = falta.getStatusFaltas();
            assertTrue("Status deve ser válido", 
                status.equals("DENTRO_LIMITE") || 
                status.equals("PROXIMO_LIMITE") || 
                status.equals("ACIMA_LIMITE"));
                
            // ADICIONAR: Testar limite de reprovação
            if (falta.getPercentual() >= 25.0) {
                assertTrue("Deve reprovar com 25% ou mais", falta.podeReprovarPorFalta());
                assertEquals("Status deve ser ACIMA_LIMITE", "ACIMA_LIMITE", falta.getStatusFaltas());
            } else {
                assertFalse("Não deve reprovar com menos de 25%", falta.podeReprovarPorFalta());
            }
        }
    }

    @Test
    public void testGetDisciplinaById() {
        Disciplina disciplina = repository.getDisciplinaById(1);
        
        assertNotNull("Disciplina com ID 1 deve existir", disciplina);
        assertEquals("ID deve ser 1", 1, disciplina.getId());
    }

    @Test
    public void testHasDataForPeriodo() {
        // Verificar se há dados para o período atual
        assertTrue("Deve haver dados para 2025.1", repository.hasDataForPeriodo("2025.1"));
        
        // Verificar um período que não existe
        assertFalse("Não deve haver dados para 2030.1", repository.hasDataForPeriodo("2030.1"));
    }

    @Test
    public void testGetHistoricoCompleto() {
        List<Historico> historico = repository.getHistoricoCompleto();
        
        // Se não há dados no banco, o método pode retornar lista vazia
        assertNotNull("Lista de histórico não deve ser nula", historico);
        
        // Se houver dados, verificar estrutura
        if (historico.size() > 0) {
            Historico item = historico.get(0);
            assertNotNull("Nome da disciplina não deve ser nulo", item.getDisciplinaNome());
            assertNotNull("Período não deve ser nulo", item.getPeriodo());
            assertNotNull("Situação não deve ser nula", item.getSituacao());
        }
    }

    @Test
    public void testInsertNota() {
        Nota nota = new Nota();
        nota.setDisciplinaId(1);
        nota.setPeriodo("2025.1");
        nota.setNt1(8.5);
        nota.setSituacao("CURSANDO");
        
        long result = repository.insertNota(nota);
        assertTrue("Inserção deve retornar ID > 0", result > 0);
    }

    @Test
    public void testInsertFalta() {
        Falta falta = new Falta();
        falta.setDisciplinaId(1);
        falta.setPeriodo("2025.1");
        falta.setJaneiro(2);
        falta.setFevereiro(3);
        falta.calcularTotalFaltas();
        falta.calcularPercentual(75.0);
        
        long result = repository.insertFalta(falta);
        assertTrue("Inserção deve retornar ID > 0", result > 0);
    }
}
