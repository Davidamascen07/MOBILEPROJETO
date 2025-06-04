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
        // Carregar histórico real do banco de dados
        List<Historico> historico = repository.getHistoricoCompleto();
        
        if (historico.isEmpty()) {
            // Se não há dados, usar dados de exemplo para desenvolvimento
            historico = criarHistoricoExemplo();
        }
        
        historicoAdapter = new HistoricoAdapter(historico, getContext());
        recyclerViewHistorico.setAdapter(historicoAdapter);
    }

    private List<Historico> criarHistoricoExemplo() {
        List<Historico> historico = new ArrayList<>();
        
        // 1º PERÍODO (2021.2)
        historico.add(new Historico("1º PERÍODO", "INGLÊS INSTRUMENTAL", "2021.2", "APROVADO", 9.92, 0));
        historico.add(new Historico("1º PERÍODO", "METODOLOGIA DA PESQUISA CIENTÍFICA", "2021.2", "APROVADO", 7.83, 0));
        historico.add(new Historico("1º PERÍODO", "LÓGICA MATEMÁTICA COMPUTACIONAL", "2021.2", "APROVADO", 9.50, 0));
        historico.add(new Historico("1º PERÍODO", "INTRODUÇÃO À ADMINISTRAÇÃO", "2021.2", "APROVADO", 8.00, 0));
        historico.add(new Historico("1º PERÍODO", "MATEMÁTICA BÁSICA", "2021.2", "APROVADO", 9.33, 0));
        historico.add(new Historico("1º PERÍODO", "FUNDAMENTOS DE SISTEMAS DE INFORMAÇÃO", "2021.2", "APROVADO", 9.00, 0));
        
        // 2º PERÍODO (2022.1)
        historico.add(new Historico("2º PERÍODO", "CÁLCULO I", "2022.1", "APROVADO", 8.17, 0));
        historico.add(new Historico("2º PERÍODO", "TÓPICOS DO DIREITO PÚBLICO E PRIVADO", "2022.1", "APROVADO", 8.67, 0));
        historico.add(new Historico("2º PERÍODO", "PROJETOS INTERDISCIPLINARES I", "2022.1", "APROVADO", 8.17, 0));
        historico.add(new Historico("2º PERÍODO", "TÓPICOS ESPECIAIS EM SISTEMAS OPERACIONAIS", "2022.1", "APROVADO", 9.00, 0));
        historico.add(new Historico("2º PERÍODO", "ORGANIZAÇÃO E ARQUITETURA DE COMPUTADORES", "2022.1", "APROVADO", 8.17, 0));
        historico.add(new Historico("2º PERÍODO", "TÉCNICAS DE PROGRAMAÇÃO I", "2022.1", "APROVADO", 8.22, 0));
        
        // 3º PERÍODO (2022.2)
        historico.add(new Historico("3º PERÍODO", "EMPREENDEDORISMO E INOVAÇÃO", "2022.2", "APROVADO", 7.67, 3));
        historico.add(new Historico("3º PERÍODO", "RESPONSABILIDADE SOCIOAMBIENTAL", "2022.2", "APROVADO", 7.87, 0));
        historico.add(new Historico("3º PERÍODO", "CÁLCULO II", "2022.2", "APROVADO", 9.00, 5));
        historico.add(new Historico("3º PERÍODO", "GESTÃO DE SISTEMAS DE INFORMAÇÃO", "2022.2", "APROVADO", 7.60, 0));
        historico.add(new Historico("3º PERÍODO", "TÉCNICAS DE PROGRAMAÇÃO II", "2022.2", "APROVADO", 8.00, 0));
        historico.add(new Historico("3º PERÍODO", "ESTATÍSTICA E PROBABILIDADE", "2022.2", "APROVADO", 9.17, 2));
        historico.add(new Historico("3º PERÍODO", "PROJETOS INTERDISCIPLINARES II", "2022.2", "APROVADO", 8.27, 2));
        historico.add(new Historico("3º PERÍODO", "GESTÃO DE PROJETOS", "2022.2", "APROVADO", 7.83, 2));
        
        // 4º PERÍODO (2023.1)
        historico.add(new Historico("4º PERÍODO", "PROJETOS INTERDISCIPLINARES III", "2023.1", "APROVADO", 7.93, 2));
        historico.add(new Historico("4º PERÍODO", "TÓPICOS ESPECIAIS EM ROBÓTICA", "2023.1", "APROVADO", 8.00, 0));
        historico.add(new Historico("4º PERÍODO", "BANCO DE DADOS I", "2023.1", "APROVADO", 9.00, 2));
        historico.add(new Historico("4º PERÍODO", "REDES DE COMPUTADORES", "2023.1", "APROVADO", 8.33, 2));
        
        // 5º PERÍODO (2023.2)
        historico.add(new Historico("5º PERÍODO", "BANCO DE DADOS II", "2023.2", "APROVADO", 7.83, 2));
        historico.add(new Historico("5º PERÍODO", "PROGRAMAÇÃO ORIENTADA A OBJETOS", "2023.2", "APROVADO", 7.67, 14));
        historico.add(new Historico("5º PERÍODO", "GESTÃO DA QUALIDADE DE SOFTWARE", "2023.2", "APROVADO", 7.00, 5));
        historico.add(new Historico("5º PERÍODO", "TÓPICOS ESPECIAIS EM REDES DE COMPUTADORES", "2023.2", "APROVADO", 7.00, 0));
        historico.add(new Historico("5º PERÍODO", "METODOLOGIAS DE DESENVOLVIMENTO DE SOFTWARE", "2023.2", "APROVADO", 6.34, 4));
        
        // 6º PERÍODO (2024.1)
        historico.add(new Historico("6º PERÍODO", "ANÁLISE E PROJETOS DE SISTEMAS", "2024.1", "APROVADO", 5.20, 12));
        historico.add(new Historico("6º PERÍODO", "COMPUTAÇÃO GRÁFICA", "2024.1", "APROVADO", 5.21, 10));
        historico.add(new Historico("6º PERÍODO", "ESTRUTURA DE DADOS", "2024.1", "APROVADO", 6.24, 11));
        historico.add(new Historico("6º PERÍODO", "TEORIA DA COMPUTAÇÃO E LINGUAGENS FORMAIS", "2024.1", "APROVADO", 7.00, 3));
        historico.add(new Historico("6º PERÍODO", "TÓPICOS EM PROGRAMAÇÃO WEB", "2024.1", "APROVADO", 8.67, 12));
        
        // 7º PERÍODO (2024.2)
        historico.add(new Historico("7º PERÍODO", "AUDITORIA E SEGURANÇA EM SISTEMAS DE INFORMAÇÃO", "2024.2", "APROVADO", 8.83, 2));
        historico.add(new Historico("7º PERÍODO", "ENGENHARIA DE SOFTWARE", "2024.2", "APROVADO", 7.10, 6));
        historico.add(new Historico("7º PERÍODO", "LABORATÓRIO DE PROGRAMAÇÃO I", "2024.2", "APROVADO", 5.67, 5));
        historico.add(new Historico("7º PERÍODO", "ESTÁGIO SUPERVISIONADO I", "2024.2", "APROVADO", 10.00, 0));
        historico.add(new Historico("7º PERÍODO", "TRABALHO DE CONCLUSÃO DE CURSO I", "2024.2", "APROVADO", 8.90, 0));
        historico.add(new Historico("7º PERÍODO", "INTELIGÊNCIA ARTIFICIAL", "2024.2", "REPROVADO", 4.75, 14));
        
        // 8º PERÍODO (2025.1) - CURSANDO
        historico.add(new Historico("8º PERÍODO", "TÓPICOS EM DESENVOLVIMENTO MOBILE", "2025.1", "CURSANDO", 0.0, 16));
        historico.add(new Historico("8º PERÍODO", "LABORATÓRIO DE PROGRAMAÇÃO II", "2025.1", "CURSANDO", 0.0, 18));
        historico.add(new Historico("8º PERÍODO", "TÓPICOS ESPECIAIS EM ENGENHARIA DE SOFTWARE", "2025.1", "CURSANDO", 0.0, 1));
        historico.add(new Historico("8º PERÍODO", "ESTÁGIO SUPERVISIONADO II", "2025.1", "CURSANDO", 0.0, 21));
        historico.add(new Historico("8º PERÍODO", "TRABALHO DE CONCLUSÃO DE CURSO II", "2025.1", "CURSANDO", 0.0, 10));
        
        return historico;
    }
}
