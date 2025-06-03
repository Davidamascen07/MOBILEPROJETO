package com.example.mobiliap3.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobiliap3.R;
import com.example.mobiliap3.database.Repository;
import com.example.mobiliap3.models.Disciplina;
import com.example.mobiliap3.models.Nota;
import com.example.mobiliap3.models.Falta;
import com.example.mobiliap3.utils.PreferencesManager;

import java.util.List;

public class DashboardFragment extends Fragment {
    private TextView tvBemVindo;
    private TextView tvPeriodoAtual;
    private TextView tvTotalDisciplinas;
    private TextView tvDisciplinasCursando;
    private TextView tvMediaGeral;

    private Repository repository;
    private PreferencesManager prefsManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        
        initViews(view);
        initData();
        loadDashboardData();
        
        return view;
    }

    private void initViews(View view) {
        tvBemVindo = view.findViewById(R.id.tv_bem_vindo);
        tvPeriodoAtual = view.findViewById(R.id.tv_periodo_atual);
        tvTotalDisciplinas = view.findViewById(R.id.tv_total_disciplinas);
        tvDisciplinasCursando = view.findViewById(R.id.tv_disciplinas_cursando);
        tvMediaGeral = view.findViewById(R.id.tv_media_geral);
    }

    private void initData() {
        repository = new Repository(getContext());
        prefsManager = new PreferencesManager(getContext());
        
        // Popular dados iniciais se necessário
        repository.populateInitialData();
    }

    private void loadDashboardData() {
        String nomeUsuario = prefsManager.getUserNome();
        String periodoAtual = prefsManager.getPeriodoSelecionado();

        // Configurar dados básicos
        tvBemVindo.setText(getString(R.string.bem_vindo) + ", " + nomeUsuario.split(" ")[0]);
        tvPeriodoAtual.setText(getString(R.string.periodo_atual, periodoAtual));

        // Carregar disciplinas
        List<Disciplina> disciplinas = repository.getAllDisciplinas();
        tvTotalDisciplinas.setText(String.valueOf(disciplinas.size()));

        // Carregar notas do período atual
        List<Nota> notas = repository.getNotasByPeriodo(periodoAtual);
        long cursando = notas.stream().filter(nota -> "CURSANDO".equals(nota.getSituacao())).count();
        tvDisciplinasCursando.setText(String.valueOf(cursando));

        // Calcular média geral (simplificado)
        double soma = 0;
        int count = 0;
        for (Nota nota : notas) {
            if (nota.getNt1() != null) {
                soma += nota.getNt1();
                count++;
            }
            if (nota.getNt2() != null) {
                soma += nota.getNt2();
                count++;
            }
        }
        
        if (count > 0) {
            double media = soma / count;
            tvMediaGeral.setText(String.format("%.1f", media));
        } else {
            tvMediaGeral.setText("--");
        }
    }
}
