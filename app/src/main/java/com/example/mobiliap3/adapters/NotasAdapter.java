package com.example.mobiliap3.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobiliap3.R;
import com.example.mobiliap3.activities.DetalhesNotaActivity;
import com.example.mobiliap3.fragments.NotasFragment.NotaComDisciplina;

import java.util.List;

public class NotasAdapter extends RecyclerView.Adapter<NotasAdapter.NotaViewHolder> {
    private List<NotaComDisciplina> notasComDisciplinas;
    private Context context;

    public NotasAdapter(List<NotaComDisciplina> notasComDisciplinas, Context context) {
        this.notasComDisciplinas = notasComDisciplinas;
        this.context = context;
    }

    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nota, parent, false);
        return new NotaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaViewHolder holder, int position) {
        NotaComDisciplina item = notasComDisciplinas.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return notasComDisciplinas.size();
    }

    class NotaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDisciplinaNome;
        private TextView tvDisciplinaCodigo;
        private TextView tvNt1;
        private TextView tvNt2;
        private TextView tvNt3;
        private TextView tvSituacao;

        public NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDisciplinaNome = itemView.findViewById(R.id.tv_disciplina_nome);
            tvDisciplinaCodigo = itemView.findViewById(R.id.tv_disciplina_codigo);
            tvNt1 = itemView.findViewById(R.id.tv_nt1);
            tvNt2 = itemView.findViewById(R.id.tv_nt2);
            tvNt3 = itemView.findViewById(R.id.tv_nt3);
            tvSituacao = itemView.findViewById(R.id.tv_situacao);
        }

        public void bind(NotaComDisciplina item) {
            tvDisciplinaNome.setText(item.getDisciplina().getNome());
            tvDisciplinaCodigo.setText(item.getDisciplina().getCodigo());
            
            // Configurar notas
            tvNt1.setText(item.getNota().getNt1() != null ? 
                String.format("%.1f", item.getNota().getNt1()) : "--");
            tvNt2.setText(item.getNota().getNt2() != null ? 
                String.format("%.1f", item.getNota().getNt2()) : "--");
            tvNt3.setText(item.getNota().getNt3() != null ? 
                String.format("%.1f", item.getNota().getNt3()) : "--");
            
            // Configurar situação
            tvSituacao.setText(item.getNota().getSituacao());
            configurarStatusSituacao(tvSituacao, item.getNota().getSituacao());
            
            // Click listener para Intent com extras
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetalhesNotaActivity.class);
                intent.putExtra("disciplina_id", item.getDisciplina().getId());
                intent.putExtra("disciplina_nome", item.getDisciplina().getNome());
                intent.putExtra("disciplina_codigo", item.getDisciplina().getCodigo());
                intent.putExtra("situacao", item.getNota().getSituacao());
                context.startActivity(intent);
            });
        }

        private void configurarStatusSituacao(TextView textView, String situacao) {
            // SEMPRE manter o texto branco
            textView.setTextColor(Color.WHITE);
            
            // Mudar o BACKGROUND baseado na situação usando os drawables
            switch (situacao) {
                case "CURSANDO":
                    textView.setText("CURSANDO");
                    textView.setBackground(ContextCompat.getDrawable(context, R.drawable.status_badge_cursando));
                    break;
                case "APROVADO":
                    textView.setText("APROVADO");
                    textView.setBackground(ContextCompat.getDrawable(context, R.drawable.status_badge_aprovado));
                    break;
                case "REPROVADO":
                    textView.setText("REPROVADO");
                    textView.setBackground(ContextCompat.getDrawable(context, R.drawable.status_badge_reprovado));
                    break;
                default:
                    textView.setText("INDEFINIDO");
                    textView.setBackground(ContextCompat.getDrawable(context, R.drawable.status_badge_cursando));
                    break;
            }
        }
    }
}
