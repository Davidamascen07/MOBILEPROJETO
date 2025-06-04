package com.example.mobiliap3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobiliap3.R;
import com.example.mobiliap3.database.DatabaseHelper;
import com.example.mobiliap3.utils.PreferencesManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class CadastroActivity extends AppCompatActivity {
    private TextInputEditText etNome, etMatricula, etCurso, etSenha, etConfirmarSenha;
    private MaterialButton btnCadastrar, btnVoltar;
    private DatabaseHelper dbHelper;
    private PreferencesManager prefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        initViews();
        initDatabase();
        setupListeners();
    }

    private void initViews() {
        etNome = findViewById(R.id.et_nome);
        etMatricula = findViewById(R.id.et_matricula_cadastro);
        etCurso = findViewById(R.id.et_curso);
        etSenha = findViewById(R.id.et_senha_cadastro);
        etConfirmarSenha = findViewById(R.id.et_confirmar_senha);
        btnCadastrar = findViewById(R.id.btn_cadastrar);
        btnVoltar = findViewById(R.id.btn_voltar_login);
    }

    private void initDatabase() {
        dbHelper = new DatabaseHelper(this);
        prefsManager = new PreferencesManager(this);
    }

    private void setupListeners() {
        btnCadastrar.setOnClickListener(v -> realizarCadastro());
        btnVoltar.setOnClickListener(v -> finish());
    }

    private void realizarCadastro() {
        String nome = etNome.getText().toString().trim();
        String matricula = etMatricula.getText().toString().trim();
        String curso = etCurso.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();
        String confirmarSenha = etConfirmarSenha.getText().toString().trim();

        if (nome.isEmpty() || matricula.isEmpty() || curso.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            Toast.makeText(this, "Senhas não coincidem", Toast.LENGTH_SHORT).show();
            return;
        }

        if (senha.length() < 6) {
            Toast.makeText(this, "Senha deve ter pelo menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        long userId = dbHelper.cadastrarUsuario(nome, matricula, curso, senha);
        
        if (userId != -1) {
            Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
            
            // Fazer login automático
            prefsManager.setLoggedIn(true);
            prefsManager.setUserData((int) userId, nome, matricula);
            
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Erro ao cadastrar usuário. Matrícula já existe?", Toast.LENGTH_SHORT).show();
        }
    }
}
