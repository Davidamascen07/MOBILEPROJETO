package com.example.mobiliap3.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobiliap3.R;
import com.example.mobiliap3.adapters.FaltasAdapter;
import com.example.mobiliap3.database.Repository;
import com.example.mobiliap3.models.Falta;
import com.example.mobiliap3.models.Disciplina;
import com.example.mobiliap3.utils.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class FaltasFragment extends Fragment {
    
    private static final boolean MOSTRAR_PERIODO_ANTERIOR = true;
    
    private RecyclerView recyclerViewFaltas;
    private FaltasAdapter faltasAdapter;
    private Repository repository;
    private PreferencesManager prefsManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faltas, container, false);
        
        initViews(view);
        initData();
        loadFaltas();
        
        return view;
    }

    private void initViews(View view) {
        recyclerViewFaltas = view.findViewById(R.id.recycler_view_faltas);
        recyclerViewFaltas.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initData() {
        repository = new Repository(getContext());
        prefsManager = new PreferencesManager(getContext());
        
        // Forçar população dos dados para debug
        repository.populateInitialData();
    }

    private void loadFaltas() {
        String periodoAtual = prefsManager.getPeriodoSelecionado();
        int usuarioId = prefsManager.getUserId();
        
        // Validar se usuário está logado
        if (usuarioId == -1) {
            android.util.Log.e("FaltasFragment", "Usuário não encontrado");
            return;
        }
        
        // CORRIGIR: Atualizar percentuais antes de carregar dados
        repository.atualizarPercentuaisComCargaCorreta();
        
        // USAR método que recalcula status corretamente
        List<Falta> faltas = repository.getFaltasComCalculoCorreto(periodoAtual, usuarioId);
        
        if (MOSTRAR_PERIODO_ANTERIOR && faltas.isEmpty()) {
            // Se não há dados para o período atual, tentar período anterior
            String periodoAnterior = "2024.2";
            faltas = repository.getFaltasComCalculoCorreto(periodoAnterior, usuarioId);
        }
        
        List<Disciplina> disciplinas = repository.getAllDisciplinas();
        
        // Debug: verificar status das faltas com carga horária
        for (Falta falta : faltas) {
            Disciplina disciplina = null;
            for (Disciplina d : disciplinas) {
                if (d.getId() == falta.getDisciplinaId()) {
                    disciplina = d;
                    break;
                }
            }
            
            if (disciplina != null) {
                android.util.Log.d("FaltasFragment", String.format(
                    "%s: %s", disciplina.getNome(), 
                    falta.getDebugInfo(disciplina.getCargaHoraria())
                ));
            }
        }
        
        // Construtor correto: Context, List<Falta>, List<Disciplina>
        faltasAdapter = new FaltasAdapter(getContext(), faltas, disciplinas);
        recyclerViewFaltas.setAdapter(faltasAdapter);
    }
}
