package com.example.lapizzautn.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lapizzautn.R;
import com.example.lapizzautn.entidad.DatabaseHelper;

public class LoginFragment extends Fragment {
    private EditText et_username;
    private EditText et_clave;
    private Button btn_login;
    private TextView tv_registrarse;
    private TextView tv_olvidaste_clave;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Cargamos el layout (fragment_login.xml)
        View vista = inflater.inflate(R.layout.fragment_login, container, false);

        // Inicializar la base de datos
        dbHelper = new DatabaseHelper(requireContext());

        // Enlazamos los elementos de la interfaz con variables
        et_username = vista.findViewById(R.id.et_username);
        et_clave = vista.findViewById(R.id.et_clave);
        btn_login = vista.findViewById(R.id.btn_login);
        tv_registrarse = vista.findViewById(R.id.tv_registrarse);
        tv_olvidaste_clave = vista.findViewById(R.id.tv_olvidaste_clave);

        // Configuramos el comportamiento del botón de inicio de sesión
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = et_username.getText().toString().trim();
                String clave = et_clave.getText().toString().trim();

                if (usuario.isEmpty() || clave.isEmpty()) {
                    Toast.makeText(getActivity(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean usuarioValido = verificarUsuario(usuario, clave);
                if (usuarioValido) {
                    Toast.makeText(getActivity(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                    // Crear instancia de HomeFragment y pasar argumentos
                    HomeFragment homeFragment = new HomeFragment();
                    Bundle args = new Bundle();
                    args.putBoolean("isAdmin", "admin".equals(usuario)); // Verificar si el usuario es "admin"
                    homeFragment.setArguments(args);

                    // Redirigir al HomeFragment
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, homeFragment);
                    transaction.commit();
                } else {
                    Toast.makeText(getActivity(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configuramos el comportamiento del enlace "Registrarse"
        tv_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRegistrarClienteFragment();
            }
        });

        // Configuramos el comportamiento del enlace "Olvidé mi contraseña"
        tv_olvidaste_clave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRegistrarClienteFragment();
            }
        });

        return vista;
    }

    /**
     * Método para abrir el fragmento de registro de cliente.
     */
    private void abrirRegistrarClienteFragment() {
        RegistrarClienteFragment registrarClienteFragment = new RegistrarClienteFragment();
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, registrarClienteFragment); // Reemplaza el contenedor actual
        transaction.addToBackStack(null); // Agrega la transacción a la pila para poder regresar
        transaction.commit();
    }

    /**
     * Método para verificar si el usuario existe en la base de datos y la contraseña es correcta.
     */
    private boolean verificarUsuario(String usuario, String contrasena) {
        return dbHelper.verificarUsuario(usuario, contrasena);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close(); // Cierra la conexión con la base de datos
        }
    }
}
