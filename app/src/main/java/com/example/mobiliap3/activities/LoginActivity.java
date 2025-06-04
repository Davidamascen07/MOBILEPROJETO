package com.example.mobiliap3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobiliap3.R;
import com.example.mobiliap3.database.DatabaseHelper;
import com.example.mobiliap3.models.Usuario;
import com.example.mobiliap3.utils.PreferencesManager;

public class LoginActivity extends AppCompatActivity {
    private EditText etMatricula, etSenha;
    private Button btnEntrar;
    private DatabaseHelper dbHelper;
    private PreferencesManager prefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initDatabase();
        checkIfLoggedIn();
        setupListeners();
    }

    private void initViews() {
        etMatricula = findViewById(R.id.et_matricula);
        etSenha = findViewById(R.id.et_senha);
        btnEntrar = findViewById(R.id.btn_entrar);
        
        // Adicionar botão de cadastro com verificação de nulidade
        Button btnCadastro = findViewById(R.id.btn_cadastrar_novo);
        if (btnCadastro != null) {
            btnCadastro.setOnClickListener(v -> {
                Intent intent = new Intent(this, CadastroActivity.class);
                startActivity(intent);
            });
        }
    }

    private void initDatabase() {
        dbHelper = new DatabaseHelper(this);
        prefsManager = new PreferencesManager(this);
    }

    private void checkIfLoggedIn() {
        if (prefsManager.isLoggedIn()) {
            navigateToMain();
        }
    }

    private void setupListeners() {
        btnEntrar.setOnClickListener(v -> realizarLogin());
    }

    private void realizarLogin() {
        String matricula = etMatricula.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();

        if (matricula.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Autenticar usuário
        Usuario usuario = dbHelper.autenticarUsuario(matricula, senha);
        
        if (usuario != null) {
            // Salvar dados do usuário nas preferências
            prefsManager.setLoggedIn(true);
            prefsManager.setUserData(usuario.getId(), usuario.getNome(), usuario.getMatricula());
            
            // Criar dados iniciais para o usuário se necessário
            dbHelper.criarDadosInicaisUsuario(usuario.getId());
            
            // Ir para MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Matrícula ou senha incorreta", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
