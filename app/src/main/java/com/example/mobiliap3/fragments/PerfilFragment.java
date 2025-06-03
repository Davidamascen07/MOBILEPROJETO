package com.example.mobiliap3.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobiliap3.R;
import com.example.mobiliap3.activities.LoginActivity;
import com.example.mobiliap3.utils.PreferencesManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class PerfilFragment extends Fragment {
    private TextView tvNomeUsuario;
    private TextView tvMatricula;
    private TextView tvCurso;
    private TextView tvPeriodoAtual;
    private MaterialCardView cardCompartilhar;
    private MaterialCardView cardConfiguracoes;
    private MaterialButton btnLogout;
    
    private PreferencesManager prefsManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        
        initViews(view);
        initData();
        loadUserData();
        setupListeners();
        
        return view;
    }

    private void initViews(View view) {
        tvNomeUsuario = view.findViewById(R.id.tv_nome_usuario);
        tvMatricula = view.findViewById(R.id.tv_matricula_perfil);
        tvCurso = view.findViewById(R.id.tv_curso_perfil);
        tvPeriodoAtual = view.findViewById(R.id.tv_periodo_atual_perfil);
        cardCompartilhar = view.findViewById(R.id.card_compartilhar);
        cardConfiguracoes = view.findViewById(R.id.card_configuracoes);
        btnLogout = view.findViewById(R.id.btn_logout);
    }

    private void initData() {
        prefsManager = new PreferencesManager(getContext());
    }

    private void loadUserData() {
        tvNomeUsuario.setText(prefsManager.getUserNome());
        tvMatricula.setText(prefsManager.getUserMatricula());
        tvCurso.setText("Sistema de Informação - Bacharelado");
        tvPeriodoAtual.setText(prefsManager.getPeriodoSelecionado());
    }

    private void setupListeners() {
        cardCompartilhar.setOnClickListener(v -> compartilharApp());
        cardConfiguracoes.setOnClickListener(v -> abrirConfiguracoes());
        btnLogout.setOnClickListener(v -> realizarLogout());
    }

    private void compartilharApp() {
        String conteudo = String.format(
            "📱 MobilIA P3 - Portal Acadêmico Móvel\n\n" +
            "👤 %s\n" +
            "🎓 %s\n" +
            "📋 Matrícula: %s\n" +
            "📅 Período: %s\n\n" +
            "Um aplicativo completo para acompanhar seu desempenho acadêmico!",
            prefsManager.getUserNome(),
            "Sistema de Informação - Bacharelado",
            prefsManager.getUserMatricula(),
            prefsManager.getPeriodoSelecionado()
        );

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "MobilIA P3 - Meu Perfil Acadêmico");
        shareIntent.putExtra(Intent.EXTRA_TEXT, conteudo);
        startActivity(Intent.createChooser(shareIntent, "Compartilhar perfil via"));
    }

    private void abrirConfiguracoes() {
        // Intent para activity de configurações (futuro)
        // Por enquanto, apenas mostra uma mensagem
    }

    private void realizarLogout() {
        prefsManager.logout();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}
