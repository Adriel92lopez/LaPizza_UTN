package com.example.lapizzautn;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.lapizzautn.fragment.ListadoProductoFragment;
import com.example.lapizzautn.fragment.AltaProductoFragment;
import com.example.lapizzautn.fragment.LoginFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar Toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().hide(); // Ocultar el Toolbar inicialmente

        // Configurar DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Configurar navegación
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment selectedFragment = null;

            if (id == R.id.nav_principal) {
                selectedFragment = new LoginFragment();
            } else if (id == R.id.nav_listado_productos) {
                selectedFragment = new ListadoProductoFragment();
            } else if (id == R.id.nav_cerrar_sesion) {
                cerrarSesion();
            }

            if (selectedFragment != null) {
                cargarFragmento(selectedFragment);
            }

            drawerLayout.closeDrawers();
            return true;
        });

        // Cargar el LoginFragment al iniciar si no hay estado guardado
        if (savedInstanceState == null) {
            cargarFragmento(new LoginFragment());
        }

        // Registrar un callback para monitorear los cambios de fragmento
        getSupportFragmentManager().registerFragmentLifecycleCallbacks(
                new FragmentManager.FragmentLifecycleCallbacks() {
                    @Override
                    public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                        super.onFragmentResumed(fm, f);
                        // Mostrar u ocultar el Toolbar según el fragmento actual
                        if (f instanceof ListadoProductoFragment || f instanceof AltaProductoFragment) {
                            getSupportActionBar().show();
                        } else {
                            getSupportActionBar().hide();
                        }
                    }
                }, true);
    }

    private void cargarFragmento(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void cerrarSesion() {
        // Eliminar los datos de sesión
        getSharedPreferences("app_prefs", MODE_PRIVATE)
                .edit()
                .remove("user_id")
                .apply();

        // Redirigir a LoginFragment
        cargarFragmento(new LoginFragment());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
