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
import com.example.mobiliap3.utils.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class HistoricoFragment extends Fragment {
    private RecyclerView recyclerView;
    private HistoricoAdapter adapter;
    private Repository repository;
    private PreferencesManager prefsManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new Repository(getContext());
        prefsManager = new PreferencesManager(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historico, container, false);

        initViews(view);
        initData();
        carregarHistorico(); // USAR carregarHistorico consistentemente

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_historico);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initData() {
        // REMOVER duplicação - repository já foi inicializado em onCreate()
        // repository = new Repository(getContext()); // DELETAR ESTA LINHA
    }

    private void carregarHistorico() {
        int usuarioId = prefsManager.getUserId();
        
        // Validar se usuário está logado
        if (usuarioId == -1) {
            android.util.Log.e("HistoricoFragment", "Usuário não encontrado");
            return;
        }
        
        // ADICIONAR: Debug antes de carregar dados
        repository.debugHistorico();
        repository.verificarConsistenciaDados();
        
        List<Historico> historico = repository.getHistoricoCompleto(usuarioId);
        
        android.util.Log.d("HistoricoFragment", "Total de registros no histórico: " + historico.size());
        
        // Log detalhado do que foi carregado
        for (Historico hist : historico) {
            android.util.Log.d("HistoricoFragment", String.format(
                "Histórico: %s | %s | %s | %.2f", 
                hist.getDisciplinaNome(), hist.getPeriodo(), 
                hist.getSituacao(), hist.getNotaFinal()
            ));
        }
        
        if (historico.isEmpty()) {
            // Se não há dados no banco, usar dados de exemplo para desenvolvimento
            historico = criarHistoricoExemplo();
            android.util.Log.w("HistoricoFragment", "Usando dados de exemplo - total: " + historico.size());
        }

        adapter = new HistoricoAdapter(historico, getContext());
        recyclerView.setAdapter(adapter);
    }

    private List<Historico> criarHistoricoExemplo() {
        List<Historico> historico = new ArrayList<>();

        // PROBLEMA: Construtor incorreto - o modelo Historico não tem este construtor
        // Precisa corrigir para usar o construtor correto
        
        // 1º PERÍODO (2021.2)
        historico.add(createHistoricoItem(1, "INGLÊS INSTRUMENTAL", "2021.2", "APROVADO", 9.92, 0));
        historico.add(createHistoricoItem(2, "METODOLOGIA DA PESQUISA CIENTÍFICA", "2021.2", "APROVADO", 7.83, 0));
        historico.add(createHistoricoItem(3, "LÓGICA MATEMÁTICA COMPUTACIONAL", "2021.2", "APROVADO", 9.50, 0));
        historico.add(createHistoricoItem(4, "INTRODUÇÃO À ADMINISTRAÇÃO", "2021.2", "APROVADO", 8.00, 0));
        historico.add(createHistoricoItem(5, "MATEMÁTICA BÁSICA", "2021.2", "APROVADO", 9.33, 0));
        historico.add(createHistoricoItem(6, "FUNDAMENTOS DE SISTEMAS DE INFORMAÇÃO", "2021.2", "APROVADO", 9.00, 0));

        // 2º PERÍODO (2022.1)
        historico.add(createHistoricoItem(7, "CÁLCULO I", "2022.1", "APROVADO", 8.17, 0));
        historico.add(createHistoricoItem(8, "TÓPICOS DO DIREITO PÚBLICO E PRIVADO", "2022.1", "APROVADO", 8.67, 0));
        historico.add(createHistoricoItem(9, "PROJETOS INTERDISCIPLINARES I", "2022.1", "APROVADO", 8.17, 0));
        historico.add(createHistoricoItem(10, "TÓPICOS ESPECIAIS EM SISTEMAS OPERACIONAIS", "2022.1", "APROVADO", 9.00, 0));
        historico.add(createHistoricoItem(11, "ORGANIZAÇÃO E ARQUITETURA DE COMPUTADORES", "2022.1", "APROVADO", 8.17, 0));
        historico.add(createHistoricoItem(12, "TÉCNICAS DE PROGRAMAÇÃO I", "2022.1", "APROVADO", 8.22, 0));

        // 3º PERÍODO (2022.2)
        historico.add(createHistoricoItem(13, "EMPREENDEDORISMO E INOVAÇÃO", "2022.2", "APROVADO", 7.67, 3));
        historico.add(createHistoricoItem(14, "RESPONSABILIDADE SOCIOAMBIENTAL", "2022.2", "APROVADO", 7.87, 0));
        historico.add(createHistoricoItem(15, "CÁLCULO II", "2022.2", "APROVADO", 9.00, 5));
        historico.add(createHistoricoItem(16, "GESTÃO DE SISTEMAS DE INFORMAÇÃO", "2022.2", "APROVADO", 7.60, 0));
        historico.add(createHistoricoItem(17, "TÉCNICAS DE PROGRAMAÇÃO II", "2022.2", "APROVADO", 8.00, 0));
        historico.add(createHistoricoItem(18, "ESTATÍSTICA E PROBABILIDADE", "2022.2", "APROVADO", 9.17, 2));
        historico.add(createHistoricoItem(19, "PROJETOS INTERDISCIPLINARES II", "2022.2", "APROVADO", 8.27, 2));
        historico.add(createHistoricoItem(20, "GESTÃO DE PROJETOS", "2022.2", "APROVADO", 7.83, 2));

        // 4º PERÍODO (2023.1)
        historico.add(createHistoricoItem(21, "PROJETOS INTERDISCIPLINARES III", "2023.1", "APROVADO", 7.93, 2));
        historico.add(createHistoricoItem(22, "TÓPICOS ESPECIAIS EM ROBÓTICA", "2023.1", "APROVADO", 8.00, 0));
        historico.add(createHistoricoItem(23, "BANCO DE DADOS I", "2023.1", "APROVADO", 9.00, 2));
        historico.add(createHistoricoItem(24, "REDES DE COMPUTADORES", "2023.1", "APROVADO", 8.33, 2));

        // 5º PERÍODO (2023.2)
        historico.add(createHistoricoItem(25, "BANCO DE DADOS II", "2023.2", "APROVADO", 7.83, 2));
        historico.add(createHistoricoItem(26, "PROGRAMAÇÃO ORIENTADA A OBJETOS", "2023.2", "APROVADO", 7.67, 14));
        historico.add(createHistoricoItem(27, "GESTÃO DA QUALIDADE DE SOFTWARE", "2023.2", "APROVADO", 7.00, 5));
        historico.add(createHistoricoItem(28, "TÓPICOS ESPECIAIS EM REDES DE COMPUTADORES", "2023.2", "APROVADO", 7.00, 0));
        historico.add(createHistoricoItem(29, "METODOLOGIAS DE DESENVOLVIMENTO DE SOFTWARE", "2023.2", "APROVADO", 6.34, 4));

        // 6º PERÍODO (2024.1)
        historico.add(createHistoricoItem(30, "ANÁLISE E PROJETOS DE SISTEMAS", "2024.1", "APROVADO", 5.20, 12));
        historico.add(createHistoricoItem(31, "COMPUTAÇÃO GRÁFICA", "2024.1", "APROVADO", 5.21, 10));
        historico.add(createHistoricoItem(32, "ESTRUTURA DE DADOS", "2024.1", "APROVADO", 6.24, 11));
        historico.add(createHistoricoItem(33, "TEORIA DA COMPUTAÇÃO E LINGUAGENS FORMAIS", "2024.1", "APROVADO", 7.00, 3));
        historico.add(createHistoricoItem(34, "TÓPICOS EM PROGRAMAÇÃO WEB", "2024.1", "APROVADO", 8.67, 12));

        // 7º PERÍODO (2024.2)
        historico.add(createHistoricoItem(35, "AUDITORIA E SEGURANÇA EM SISTEMAS DE INFORMAÇÃO", "2024.2", "APROVADO", 8.83, 2));
        historico.add(createHistoricoItem(36, "ENGENHARIA DE SOFTWARE", "2024.2", "APROVADO", 7.10, 6));
        historico.add(createHistoricoItem(37, "LABORATÓRIO DE PROGRAMAÇÃO I", "2024.2", "APROVADO", 5.67, 5));
        historico.add(createHistoricoItem(38, "ESTÁGIO SUPERVISIONADO I", "2024.2", "APROVADO", 10.00, 0));
        historico.add(createHistoricoItem(39, "TRABALHO DE CONCLUSÃO DE CURSO I", "2024.2", "APROVADO", 8.90, 0));
        historico.add(createHistoricoItem(40, "INTELIGÊNCIA ARTIFICIAL", "2024.2", "REPROVADO", 4.75, 14));

        // 8º PERÍODO (2025.1) - CURSANDO
        historico.add(createHistoricoItem(41, "TÓPICOS EM DESENVOLVIMENTO MOBILE", "2025.1", "CURSANDO", 0.0, 16));
        historico.add(createHistoricoItem(42, "LABORATÓRIO DE PROGRAMAÇÃO II", "2025.1", "CURSANDO", 0.0, 18));
        historico.add(createHistoricoItem(43, "TÓPICOS ESPECIAIS EM ENGENHARIA DE SOFTWARE", "2025.1", "CURSANDO", 0.0, 1));
        historico.add(createHistoricoItem(44, "ESTÁGIO SUPERVISIONADO II", "2025.1", "CURSANDO", 0.0, 21));
        historico.add(createHistoricoItem(45, "TRABALHO DE CONCLUSÃO DE CURSO II", "2025.1", "CURSANDO", 0.0, 10));

        return historico;
    }

    // ADICIONAR método auxiliar para criar itens de histórico
    private Historico createHistoricoItem(int id, String disciplinaNome, String periodo, String situacao, double notaFinal, int totalFaltas) {
        Historico historico = new Historico();
        historico.setId(id);
        historico.setDisciplinaNome(disciplinaNome);
        historico.setPeriodo(periodo);
        historico.setSituacao(situacao);
        historico.setNotaFinal(notaFinal);
        historico.setTotalFaltas(totalFaltas);
        
        // Calcular frequência baseada nas faltas (assumindo 75h como padrão)
        double frequencia = 100.0 - ((totalFaltas / 75.0) * 100.0);
        historico.setFrequencia(frequencia);
        
        return historico;
    }
}
