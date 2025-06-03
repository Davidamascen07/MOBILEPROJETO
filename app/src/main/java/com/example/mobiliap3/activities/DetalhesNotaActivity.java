package com.example.mobiliap3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobiliap3.R;
import com.example.mobiliap3.database.Repository;
import com.example.mobiliap3.models.Disciplina;
import com.example.mobiliap3.models.Nota;
import com.example.mobiliap3.models.Falta;
import com.example.mobiliap3.utils.PreferencesManager;

import java.util.List;

public class DetalhesNotaActivity extends AppCompatActivity {
    private TextView tvDisciplinaNome;
    private TextView tvDisciplinaCodigo;
    private TextView tvCreditos;
    private TextView tvCargaHoraria;
    private TextView tvSituacao;
    private TextView tvNt1, tvNt2, tvNt3;
    private TextView tvTotalFaltas;
    private TextView tvPercentualFaltas;
    
    private Repository repository;
    private PreferencesManager prefsManager;
    private int disciplinaId;
    private String disciplinaNome;
    private String disciplinaCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_nota);

        initViews();
        initData();
        getIntentExtras();
        loadDetalhes();
        setupActionBar();
    }

    private void initViews() {
        tvDisciplinaNome = findViewById(R.id.tv_disciplina_nome);
        tvDisciplinaCodigo = findViewById(R.id.tv_disciplina_codigo);
        tvCreditos = findViewById(R.id.tv_creditos);
        tvCargaHoraria = findViewById(R.id.tv_carga_horaria);
        tvSituacao = findViewById(R.id.tv_situacao);
        tvNt1 = findViewById(R.id.tv_nt1_detalhe);
        tvNt2 = findViewById(R.id.tv_nt2_detalhe);
        tvNt3 = findViewById(R.id.tv_nt3_detalhe);
        tvTotalFaltas = findViewById(R.id.tv_total_faltas);
        tvPercentualFaltas = findViewById(R.id.tv_percentual_faltas);
    }

    private void initData() {
        repository = new Repository(this);
        prefsManager = new PreferencesManager(this);
    }

    private void getIntentExtras() {
        Intent intent = getIntent();
        disciplinaId = intent.getIntExtra("disciplina_id", -1);
        disciplinaNome = intent.getStringExtra("disciplina_nome");
        disciplinaCodigo = intent.getStringExtra("disciplina_codigo");
        String situacao = intent.getStringExtra("situacao");
        
        // Configurar dados básicos
        tvDisciplinaNome.setText(disciplinaNome);
        tvDisciplinaCodigo.setText(disciplinaCodigo);
        tvSituacao.setText(situacao);
        configurarCorSituacao(tvSituacao, situacao);
    }

    private void loadDetalhes() {
        // Carregar dados da disciplina
        Disciplina disciplina = repository.getDisciplinaById(disciplinaId);
        if (disciplina != null) {
            tvCreditos.setText(String.valueOf(disciplina.getCreditos()));
            tvCargaHoraria.setText(String.format("%.0fh", disciplina.getCargaHoraria()));
        }

        // Carregar notas
        String periodoAtual = prefsManager.getPeriodoSelecionado();
        List<Nota> notas = repository.getNotasByPeriodo(periodoAtual);
        
        for (Nota nota : notas) {
            if (nota.getDisciplinaId() == disciplinaId) {
                tvNt1.setText(nota.getNt1() != null ? String.format("%.1f", nota.getNt1()) : "--");
                tvNt2.setText(nota.getNt2() != null ? String.format("%.1f", nota.getNt2()) : "--");
                tvNt3.setText(nota.getNt3() != null ? String.format("%.1f", nota.getNt3()) : "--");
                break;
            }
        }

        // Carregar faltas
        List<Falta> faltas = repository.getFaltasByPeriodo(periodoAtual);
        for (Falta falta : faltas) {
            if (falta.getDisciplinaId() == disciplinaId) {
                tvTotalFaltas.setText(String.valueOf(falta.getTotalFaltas()));
                tvPercentualFaltas.setText(String.format("%.1f%%", falta.getPercentual()));
                configurarCorFaltas(tvPercentualFaltas, falta.getStatusFaltas());
                break;
            }
        }
    }

    private void setupActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Detalhes da Disciplina");
        }
    }

    private void configurarCorSituacao(TextView textView, String situacao) {
        int color;
        switch (situacao) {
            case "APROVADO":
                color = getColor(R.color.accent_green);
                break;
            case "REPROVADO":
                color = getColor(R.color.accent_red);
                break;
            case "CURSANDO":
                color = getColor(R.color.primary_blue);
                break;
            default:
                color = getColor(R.color.gray_600);
                break;
        }
        textView.setTextColor(color);
    }

    private void configurarCorFaltas(TextView textView, String status) {
        int color;
        switch (status) {
            case "DENTRO_LIMITE":
                color = getColor(R.color.accent_green);
                break;
            case "PROXIMO_LIMITE":
                color = getColor(R.color.accent_orange);
                break;
            case "ACIMA_LIMITE":
                color = getColor(R.color.accent_red);
                break;
            default:
                color = getColor(R.color.gray_600);
                break;
        }
        textView.setTextColor(color);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detalhes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        
        if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (itemId == R.id.action_share_disciplina) {
            compartilharDetalhes();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    private void compartilharDetalhes() {
        String conteudo = String.format(
            "📚 %s (%s)\n\n" +
            "📊 Situação: %s\n" +
            "📝 Créditos: %s\n" +
            "⏰ Carga Horária: %s\n" +
            "📈 Notas: NT1: %s | NT2: %s | NT3: %s\n" +
            "❌ Faltas: %s (%s)\n\n" +
            "Enviado pelo MobilIA P3",
            disciplinaNome,
            disciplinaCodigo,
            tvSituacao.getText().toString(),
            tvCreditos.getText().toString(),
            tvCargaHoraria.getText().toString(),
            tvNt1.getText().toString(),
            tvNt2.getText().toString(),
            tvNt3.getText().toString(),
            tvTotalFaltas.getText().toString(),
            tvPercentualFaltas.getText().toString()
        );

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Detalhes da Disciplina - " + disciplinaNome);
        shareIntent.putExtra(Intent.EXTRA_TEXT, conteudo);
        startActivity(Intent.createChooser(shareIntent, "Compartilhar via"));
    }
}
