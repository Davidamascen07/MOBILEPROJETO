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
import com.example.mobiliap3.adapters.HistoricoAdapter;
import com.example.mobiliap3.database.Repository;
import com.example.mobiliap3.models.Historico;

import java.util.ArrayList;
import java.util.List;

public class HistoricoFragment extends Fragment {
    private RecyclerView recyclerViewHistorico;
    private HistoricoAdapter historicoAdapter;
    private Repository repository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historico, container, false);
        
        initViews(view);
        initData();
        loadHistorico();
        
        return view;
    }

    private void initViews(View view) {
        recyclerViewHistorico = view.findViewById(R.id.recycler_view_historico);
        recyclerViewHistorico.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initData() {
        repository = new Repository(getContext());
    }

    private void loadHistorico() {
        // Criar dados de exemplo do histórico baseados nas especificações
        List<Historico> historico = criarHistoricoExemplo();
        
        historicoAdapter = new HistoricoAdapter(historico, getContext());
        recyclerViewHistorico.setAdapter(historicoAdapter);
    }

    private List<Historico> criarHistoricoExemplo() {
        List<Historico> historico = new ArrayList<>();
        
        // 6º PERÍODO
        historico.add(new Historico("6º PERÍODO", "ANÁLISE E PROJETOS DE SISTEMAS", "2024.1", "APROVADO", 5.20, 14));
        historico.add(new Historico("6º PERÍODO", "COMPUTAÇÃO GRÁFICA", "2024.1", "APROVADO", 5.21, 12));
        historico.add(new Historico("6º PERÍODO", "LABORATÓRIO DE PROGRAMAÇÃO I", "2024.2", "APROVADO", 5.67, 6));
        historico.add(new Historico("6º PERÍODO", "INTELIGÊNCIA ARTIFICIAL", "2024.2", "REPROVADO", 4.75, 14));
        
        // 7º PERÍODO
        historico.add(new Historico("7º PERÍODO", "ESTÁGIO SUPERVISIONADO I", "2024.2", "APROVADO", 10.00, 0));
        historico.add(new Historico("7º PERÍODO", "TRABALHO DE CONCLUSÃO DE CURSO I", "2024.2", "APROVADO", 8.90, 0));
        historico.add(new Historico("7º PERÍODO", "LABORATÓRIO DE PROGRAMAÇÃO II", "2025.1", "CURSANDO", 0.0, 16));
        historico.add(new Historico("7º PERÍODO", "TÓPICOS EM DESENVOLVIMENTO MOBILE", "2025.1", "CURSANDO", 0.0, 18));
        
        return historico;
    }
}
