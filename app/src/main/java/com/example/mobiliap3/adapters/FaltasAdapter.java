package com.example.mobiliap3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobiliap3.R;
import com.example.mobiliap3.fragments.FaltasFragment.FaltaComDisciplina;

import java.util.List;

public class FaltasAdapter extends RecyclerView.Adapter<FaltasAdapter.FaltaViewHolder> {
    private List<FaltaComDisciplina> faltasComDisciplinas;
    private Context context;

    public FaltasAdapter(List<FaltaComDisciplina> faltasComDisciplinas, Context context) {
        this.faltasComDisciplinas = faltasComDisciplinas;
        this.context = context;
    }

    @NonNull
    @Override
    public FaltaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_falta, parent, false);
        return new FaltaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaltaViewHolder holder, int position) {
        FaltaComDisciplina item = faltasComDisciplinas.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return faltasComDisciplinas.size();
    }

    class FaltaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDisciplinaNome;
        private TextView tvDisciplinaCodigo;
        private TextView tvJaneiro, tvFevereiro, tvMarco, tvAbril, tvMaio, tvJunho;
        private TextView tvTotalFaltas;
        private TextView tvPercentual;
        private TextView tvStatus;

        public FaltaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDisciplinaNome = itemView.findViewById(R.id.tv_disciplina_nome_falta);
            tvDisciplinaCodigo = itemView.findViewById(R.id.tv_disciplina_codigo_falta);
            tvJaneiro = itemView.findViewById(R.id.tv_janeiro);
            tvFevereiro = itemView.findViewById(R.id.tv_fevereiro);
            tvMarco = itemView.findViewById(R.id.tv_marco);
            tvAbril = itemView.findViewById(R.id.tv_abril);
            tvMaio = itemView.findViewById(R.id.tv_maio);
            tvJunho = itemView.findViewById(R.id.tv_junho);
            tvTotalFaltas = itemView.findViewById(R.id.tv_total_faltas_item);
            tvPercentual = itemView.findViewById(R.id.tv_percentual_item);
            tvStatus = itemView.findViewById(R.id.tv_status_faltas);
        }

        public void bind(FaltaComDisciplina item) {
            tvDisciplinaNome.setText(item.getDisciplina().getNome());
            tvDisciplinaCodigo.setText(item.getDisciplina().getCodigo());
            
            // Configurar faltas por mês
            tvJaneiro.setText(String.valueOf(item.getFalta().getJaneiro()));
            tvFevereiro.setText(String.valueOf(item.getFalta().getFevereiro()));
            tvMarco.setText(String.valueOf(item.getFalta().getMarco()));
            tvAbril.setText(String.valueOf(item.getFalta().getAbril()));
            tvMaio.setText(String.valueOf(item.getFalta().getMaio()));
            tvJunho.setText(String.valueOf(item.getFalta().getJunho()));
            
            // Total e percentual
            tvTotalFaltas.setText(String.valueOf(item.getFalta().getTotalFaltas()));
            tvPercentual.setText(String.format("%.1f%%", item.getFalta().getPercentual()));
            
            // Status das faltas
            String status = item.getFalta().getStatusFaltas();
            String statusText = getStatusText(status);
            tvStatus.setText(statusText);
            configurarCorStatus(tvStatus, status);
            configurarCorPercentual(tvPercentual, status);
        }

        private String getStatusText(String status) {
            switch (status) {
                case "DENTRO_LIMITE":
                    return context.getString(R.string.faltas_dentro_limite);
                case "PROXIMO_LIMITE":
                    return context.getString(R.string.faltas_proximo_limite);
                case "ACIMA_LIMITE":
                    return context.getString(R.string.faltas_acima_limite);
                default:
                    return status;
            }
        }

        private void configurarCorStatus(TextView textView, String status) {
            int color;
            switch (status) {
                case "DENTRO_LIMITE":
                    color = context.getColor(R.color.accent_green);
                    break;
                case "PROXIMO_LIMITE":
                    color = context.getColor(R.color.accent_orange);
                    break;
                case "ACIMA_LIMITE":
                    color = context.getColor(R.color.accent_red);
                    break;
                default:
                    color = context.getColor(R.color.gray_600);
                    break;
            }
            textView.setTextColor(color);
        }

        private void configurarCorPercentual(TextView textView, String status) {
            int color;
            switch (status) {
                case "DENTRO_LIMITE":
                    color = context.getColor(R.color.accent_green);
                    break;
                case "PROXIMO_LIMITE":
                    color = context.getColor(R.color.accent_orange);
                    break;
                case "ACIMA_LIMITE":
                    color = context.getColor(R.color.accent_red);
                    break;
                default:
                    color = context.getColor(R.color.gray_800);
                    break;
            }
            textView.setTextColor(color);
        }
    }
}
