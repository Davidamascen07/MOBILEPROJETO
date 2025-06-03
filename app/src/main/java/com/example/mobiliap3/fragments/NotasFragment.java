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
import com.example.mobiliap3.adapters.NotasAdapter;
import com.example.mobiliap3.database.Repository;
import com.example.mobiliap3.models.Nota;
import com.example.mobiliap3.models.Disciplina;
import com.example.mobiliap3.utils.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class NotasFragment extends Fragment {
    private RecyclerView recyclerViewNotas;
    private NotasAdapter notasAdapter;
    private Repository repository;
    private PreferencesManager prefsManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notas, container, false);
        
        initViews(view);
        initData();
        loadNotas();
        
        return view;
    }

    private void initViews(View view) {
        recyclerViewNotas = view.findViewById(R.id.recycler_view_notas);
        recyclerViewNotas.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initData() {
        repository = new Repository(getContext());
        prefsManager = new PreferencesManager(getContext());
    }

    private void loadNotas() {
        String periodoAtual = prefsManager.getPeriodoSelecionado();
        List<Nota> notas = repository.getNotasByPeriodo(periodoAtual);
        List<Disciplina> disciplinas = repository.getAllDisciplinas();
        
        // Combinar notas com disciplinas
        List<NotaComDisciplina> notasComDisciplinas = new ArrayList<>();
        for (Nota nota : notas) {
            Disciplina disciplina = findDisciplinaById(disciplinas, nota.getDisciplinaId());
            if (disciplina != null) {
                notasComDisciplinas.add(new NotaComDisciplina(nota, disciplina));
            }
        }
        
        notasAdapter = new NotasAdapter(notasComDisciplinas, getContext());
        recyclerViewNotas.setAdapter(notasAdapter);
    }

    private Disciplina findDisciplinaById(List<Disciplina> disciplinas, int id) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getId() == id) {
                return disciplina;
            }
        }
        return null;
    }

    // Classe auxiliar para combinar Nota e Disciplina
    public static class NotaComDisciplina {
        private Nota nota;
        private Disciplina disciplina;

        public NotaComDisciplina(Nota nota, Disciplina disciplina) {
            this.nota = nota;
            this.disciplina = disciplina;
        }

        public Nota getNota() { return nota; }
        public Disciplina getDisciplina() { return disciplina; }
    }
}
