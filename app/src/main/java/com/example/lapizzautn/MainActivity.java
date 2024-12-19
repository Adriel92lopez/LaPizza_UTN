package com.example.lapizzautn;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.lapizzautn.entidad.Clientes;
import com.example.lapizzautn.fragment.ListadoProductoFragment;
import com.example.lapizzautn.fragment.LoginFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Clientes usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar Toolbar
        setSupportActionBar(findViewById(R.id.toolbar));

        // Configurar DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Cargar datos del usuario (verificar si hay usuario logueado)
        usuarioActual = cargarDatosUsuario();

        // Mostrar datos del usuario en el encabezado
        actualizarDatosUsuario();

        // Configurar navegación con if-else
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_principal) {
                cargarFragmento(new LoginFragment());
            } else if (id == R.id.nav_listado_productos) {
                cargarFragmento(new ListadoProductoFragment());
            } else if (id == R.id.nav_cerrar_sesion) {
                cerrarSesion();
            }

            drawerLayout.closeDrawers();
            return true;
        });

        // Cargar el LoginFragment al iniciar si no hay estado guardado
        if (savedInstanceState == null) {
            cargarFragmento(new LoginFragment());
        }
    }

    private void cargarFragmento(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private Clientes cargarDatosUsuario() {
        // Usamos SharedPreferences para obtener los datos del usuario logueado
        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        int usuarioID = sharedPreferences.getInt("user_id", -1); // Default: -1 si no está logueado

        if (usuarioID != -1) {
            // Si hay un usuario logueado, buscamos sus datos
            return obtenerClientePorID(usuarioID); // Esta función simula la consulta a la base de datos
        } else {
            // Si no hay usuario logueado, retornamos null
            return null;
        }
    }

    private Clientes obtenerClientePorID(int id) {
        // Aquí debes buscar al cliente por ID en la base de datos
        // Simulamos un cliente para este ejemplo
        return new Clientes(id, "Juan", "Perez", "01/01/1990", "juan.perez@example.com", "Calle Falsa 123", "123456789", "password");
    }

    private void actualizarDatosUsuario() {
        if (usuarioActual != null) {
            // Obtener el header del NavigationView
            TextView tvNombre = navigationView.getHeaderView(0).findViewById(R.id.tvUsuarioNombre);
            TextView tvEmail = navigationView.getHeaderView(0).findViewById(R.id.tvUsuarioEmail);
            TextView tvDireccion = navigationView.getHeaderView(0).findViewById(R.id.tvUsuarioDireccion);
            TextView tvTelefono = navigationView.getHeaderView(0).findViewById(R.id.tvUsuarioTelefono);

            // Asignar datos del usuario
            tvNombre.setText(usuarioActual.getNombre() + " " + usuarioActual.getApellido());
            tvEmail.setText(usuarioActual.getEmail());
            tvDireccion.setText(usuarioActual.getDireccion());
            tvTelefono.setText(usuarioActual.getTelefono());
        } else {
            // Si no hay usuario logueado, puedes mostrar un mensaje predeterminado
            TextView tvNombre = navigationView.getHeaderView(0).findViewById(R.id.tvUsuarioNombre);
            tvNombre.setText("Usuario no logueado");

            // Si deseas, puedes hacer lo mismo con los otros campos o dejarlos vacíos
        }
    }

    private void cerrarSesion() {
        // Eliminar los datos de sesión
        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("user_id"); // Eliminar el ID del usuario
        editor.apply();

        // Redirigir a LoginFragment
        cargarFragmento(new LoginFragment());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
