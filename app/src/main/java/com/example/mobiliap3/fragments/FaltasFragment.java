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
    }

    private void loadFaltas() {
        String periodoAtual = prefsManager.getPeriodoSelecionado();
        List<Falta> faltas = repository.getFaltasByPeriodo(periodoAtual);
        List<Disciplina> disciplinas = repository.getAllDisciplinas();
        
        // Combinar faltas com disciplinas
        List<FaltaComDisciplina> faltasComDisciplinas = new ArrayList<>();
        for (Falta falta : faltas) {
            Disciplina disciplina = findDisciplinaById(disciplinas, falta.getDisciplinaId());
            if (disciplina != null) {
                faltasComDisciplinas.add(new FaltaComDisciplina(falta, disciplina));
            }
        }
        
        faltasAdapter = new FaltasAdapter(faltasComDisciplinas, getContext());
        recyclerViewFaltas.setAdapter(faltasAdapter);
    }

    private Disciplina findDisciplinaById(List<Disciplina> disciplinas, int id) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getId() == id) {
                return disciplina;
            }
        }
        return null;
    }

    // Classe auxiliar para combinar Falta e Disciplina
    public static class FaltaComDisciplina {
        private Falta falta;
        private Disciplina disciplina;

        public FaltaComDisciplina(Falta falta, Disciplina disciplina) {
            this.falta = falta;
            this.disciplina = disciplina;
        }

        public Falta getFalta() { return falta; }
        public Disciplina getDisciplina() { return disciplina; }
    }
}
