package com.example.mobiliap3.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobiliap3.R;
import com.example.mobiliap3.models.Disciplina;
import com.example.mobiliap3.models.Falta;

import java.util.List;

public class FaltasAdapter extends RecyclerView.Adapter<FaltasAdapter.FaltaViewHolder> {
    
    private List<Falta> faltas;
    private List<Disciplina> disciplinas;
    private Context context;

    public FaltasAdapter(Context context, List<Falta> faltas, List<Disciplina> disciplinas) {
        this.context = context;
        this.faltas = faltas;
        this.disciplinas = disciplinas;
    }

    @NonNull
    @Override
    public FaltaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_falta, parent, false);
        return new FaltaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaltaViewHolder holder, int position) {
        Falta falta = faltas.get(position);
        Disciplina disciplina = getDisciplinaById(falta.getDisciplinaId());
        
        if (disciplina != null) {
            holder.tvDisciplinaNome.setText(disciplina.getNome());
            holder.tvDisciplinaCodigo.setText(disciplina.getCodigo());
        }
        
        // Configurar faltas mensais
        holder.tvJaneiro.setText(String.valueOf(falta.getJaneiro()));
        holder.tvFevereiro.setText(String.valueOf(falta.getFevereiro()));
        holder.tvMarco.setText(String.valueOf(falta.getMarco()));
        holder.tvAbril.setText(String.valueOf(falta.getAbril()));
        holder.tvMaio.setText(String.valueOf(falta.getMaio()));
        holder.tvJunho.setText(String.valueOf(falta.getJunho()));
        
        // Configurar totais
        holder.tvTotalFaltas.setText(String.valueOf(falta.getTotalFaltas()));
        holder.tvPercentual.setText(String.format("%.1f%%", falta.getPercentual()));
        
        // Configurar status com background e texto corretos
        configurarStatusFaltas(holder.tvStatusFaltas, falta.getStatusFaltas());
    }

    private void configurarStatusFaltas(TextView textView, String status) {
        // SEMPRE manter o texto branco
        textView.setTextColor(Color.WHITE);
        
        // Mudar o BACKGROUND baseado no status usando os drawables
        switch (status) {
            case "DENTRO_LIMITE":
                textView.setText(context.getString(R.string.faltas_dentro_limite));
                textView.setBackground(ContextCompat.getDrawable(context, R.drawable.status_badge_dentro_limite));
                break;
            case "PROXIMO_LIMITE":
                textView.setText(context.getString(R.string.faltas_proximo_limite));
                textView.setBackground(ContextCompat.getDrawable(context, R.drawable.status_badge_proximo_limite));
                break;
            case "ACIMA_LIMITE":
                textView.setText(context.getString(R.string.faltas_acima_limite));
                textView.setBackground(ContextCompat.getDrawable(context, R.drawable.status_badge_acima_limite));
                break;
            default:
                textView.setText("STATUS INDEFINIDO");
                textView.setBackground(ContextCompat.getDrawable(context, R.drawable.status_badge_acima_limite));
                break;
        }
        
        // Adicionar padding e cantos arredondados programaticamente
        textView.setPadding(24, 12, 24, 12);
    }

    private Disciplina getDisciplinaById(int id) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getId() == id) {
                return disciplina;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return faltas.size();
    }

    public void updateData(List<Falta> novasFaltas) {
        this.faltas = novasFaltas;
        notifyDataSetChanged();
    }

    static class FaltaViewHolder extends RecyclerView.ViewHolder {
        TextView tvDisciplinaNome, tvDisciplinaCodigo, tvStatusFaltas;
        TextView tvJaneiro, tvFevereiro, tvMarco, tvAbril, tvMaio, tvJunho;
        TextView tvTotalFaltas, tvPercentual;

        public FaltaViewHolder(@NonNull View itemView) {
            super(itemView);
            
            tvDisciplinaNome = itemView.findViewById(R.id.tv_disciplina_nome_falta);
            tvDisciplinaCodigo = itemView.findViewById(R.id.tv_disciplina_codigo_falta);
            tvStatusFaltas = itemView.findViewById(R.id.tv_status_faltas);
            
            tvJaneiro = itemView.findViewById(R.id.tv_janeiro);
            tvFevereiro = itemView.findViewById(R.id.tv_fevereiro);
            tvMarco = itemView.findViewById(R.id.tv_marco);
            tvAbril = itemView.findViewById(R.id.tv_abril);
            tvMaio = itemView.findViewById(R.id.tv_maio);
            tvJunho = itemView.findViewById(R.id.tv_junho);
            
            tvTotalFaltas = itemView.findViewById(R.id.tv_total_faltas_item);
            tvPercentual = itemView.findViewById(R.id.tv_percentual_item);
        }
    }
}
