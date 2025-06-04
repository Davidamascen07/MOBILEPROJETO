package com.example.mobiliap3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mobiliap3.R;
import com.example.mobiliap3.fragments.DashboardFragment;
import com.example.mobiliap3.fragments.NotasFragment;
import com.example.mobiliap3.fragments.FaltasFragment;
import com.example.mobiliap3.fragments.HistoricoFragment;
import com.example.mobiliap3.fragments.PerfilFragment;
import com.example.mobiliap3.utils.PreferencesManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;
    private PreferencesManager prefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // TEMPORÁRIO: Reset dados para debug (remover em produção)
        // Repository repository = new Repository(this);
        // repository.forceRecreateDatabase();
        
        setContentView(R.layout.activity_main);

        initViews();
        setupBottomNavigation();
        
        // Carregar fragment inicial
        if (savedInstanceState == null) {
            loadFragment(new DashboardFragment());
        }
    }

    private void initViews() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
        prefsManager = new PreferencesManager(this);
        
        // Configurar ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
            getSupportActionBar().setElevation(4);
        }
    }

    private void setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            
            int itemId = item.getItemId();
            if (itemId == R.id.nav_dashboard) {
                fragment = new DashboardFragment();
                setTitle(getString(R.string.dashboard_title));
            } else if (itemId == R.id.nav_notas) {
                fragment = new NotasFragment();
                setTitle(getString(R.string.notas_title));
            } else if (itemId == R.id.nav_faltas) {
                fragment = new FaltasFragment();
                setTitle(getString(R.string.faltas_title));
            } else if (itemId == R.id.nav_historico) {
                fragment = new HistoricoFragment();
                setTitle(getString(R.string.historico_title));
            } else if (itemId == R.id.nav_perfil) {
                fragment = new PerfilFragment();
                setTitle("Perfil");
            }
            
            return loadFragment(fragment);
        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        
        if (itemId == R.id.action_logout) {
            logout();
            return true;
        } else if (itemId == R.id.action_share) {
            shareApp();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        prefsManager.logout();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void shareApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "MobilIA P3 - App Acadêmico");
        shareIntent.putExtra(Intent.EXTRA_TEXT, 
            "Confira o MobilIA P3, aplicativo para consulta de notas, faltas e histórico acadêmico!");
        startActivity(Intent.createChooser(shareIntent, "Compartilhar via"));
    }
}
