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
        List<Falta> faltas = repository.getFaltasByPeriodo(periodoAtual);
        List<Disciplina> disciplinas = repository.getAllDisciplinas();
        
        // Debug: verificar se dados estão sendo carregados
        android.util.Log.d("FaltasFragment", "Período: " + periodoAtual);
        android.util.Log.d("FaltasFragment", "Faltas encontradas: " + faltas.size());
        android.util.Log.d("FaltasFragment", "Disciplinas encontradas: " + disciplinas.size());
        
        // Se não há dados, tentar forçar reset
        if (faltas.isEmpty()) {
            android.util.Log.d("FaltasFragment", "Forçando reset dos dados...");
            repository.forceResetData();
            faltas = repository.getFaltasByPeriodo(periodoAtual);
        }
        
        // Corrigir construtor: Context, List<Falta>, List<Disciplina>
        faltasAdapter = new FaltasAdapter(getContext(), faltas, disciplinas);
        recyclerViewFaltas.setAdapter(faltasAdapter);
    }
}
