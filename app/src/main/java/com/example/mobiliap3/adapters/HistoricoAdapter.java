package com.example.mobiliap3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobiliap3.R;
import com.example.mobiliap3.models.Historico;

import java.util.List;

public class HistoricoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_PERIODO = 0;
    private static final int TYPE_DISCIPLINA = 1;
    
    private List<Historico> historico;
    private Context context;

    public HistoricoAdapter(List<Historico> historico, Context context) {
        this.historico = historico;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        Historico item = historico.get(position);
        // Se é o primeiro item ou o período é diferente do anterior, é um header
        if (position == 0) {
            return TYPE_PERIODO;
        }
        
        Historico itemAnterior = historico.get(position - 1);
        if (!item.getPeriodo().equals(itemAnterior.getPeriodo())) {
            return TYPE_PERIODO;
        }
        
        return TYPE_DISCIPLINA;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_PERIODO) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_periodo_header, parent, false);
            return new PeriodoViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_historico_disciplina, parent, false);
            return new DisciplinaViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Historico item = historico.get(position);
        
        if (holder instanceof PeriodoViewHolder) {
            ((PeriodoViewHolder) holder).bind(item);
        } else if (holder instanceof DisciplinaViewHolder) {
            ((DisciplinaViewHolder) holder).bind(item);
        }
    }

    @Override
    public int getItemCount() {
        return historico.size();
    }

    // ViewHolder para headers de período
    class PeriodoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPeriodo;

        public PeriodoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPeriodo = itemView.findViewById(R.id.tv_periodo_header);
        }

        public void bind(Historico historico) {
            tvPeriodo.setText(historico.getPeriodo());
        }
    }

    // ViewHolder para disciplinas
    class DisciplinaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDisciplinaNome;
        private TextView tvPeriodoLetivo;
        private TextView tvSituacao;
        private TextView tvNotaFinal;
        private TextView tvFaltas;

        public DisciplinaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDisciplinaNome = itemView.findViewById(R.id.tv_disciplina_nome_historico);
            tvPeriodoLetivo = itemView.findViewById(R.id.tv_periodo_letivo);
            tvSituacao = itemView.findViewById(R.id.tv_situacao_historico);
            tvNotaFinal = itemView.findViewById(R.id.tv_nota_final_historico);
            tvFaltas = itemView.findViewById(R.id.tv_faltas_historico);
        }

        public void bind(Historico historico) {
            tvDisciplinaNome.setText(historico.getDisciplinaNome());
            tvPeriodoLetivo.setText(historico.getPeriodoLetivo());
            tvSituacao.setText(historico.getSituacao());
            
            if ("CURSANDO".equals(historico.getSituacao())) {
                tvNotaFinal.setText("--");
            } else {
                tvNotaFinal.setText(String.format("%.2f", historico.getNotaFinal()));
            }
            
            tvFaltas.setText(String.format("%d faltas", historico.getTotalFaltas()));
            
            // Configurar cor da situação
            configurarCorSituacao(tvSituacao, historico.getSituacao());
        }

        private void configurarCorSituacao(TextView textView, String situacao) {
            int color;
            switch (situacao) {
                case "APROVADO":
                    color = context.getColor(R.color.accent_green);
                    break;
                case "REPROVADO":
                    color = context.getColor(R.color.accent_red);
                    break;
                case "CURSANDO":
                    color = context.getColor(R.color.primary_blue);
                    break;
                default:
                    color = context.getColor(R.color.gray_600);
                    break;
            }
            textView.setTextColor(color);
        }
    }
}
