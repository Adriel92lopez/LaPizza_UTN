package com.example.lapizzautn.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lapizzautn.R;

public class HomeFragment extends Fragment {

    private boolean isAdmin; // Variable para identificar si es administrador

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false); // Fragmento vacío de transición
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Simulación de la lógica para determinar si es administrador
        // Cambia esta lógica según cómo determines el rol del usuario
        isAdmin = verificarSiEsAdmin();

        if (isAdmin) {
            // Redirigir al fragmento de alta de productos
            abrirFragmento(new AltaProductoFragment());
        } else {
            // Redirigir al fragmento de Listado de producto
            abrirFragmento(new ListadoProductoFragment());
        }
    }

    /**
     * Método para determinar si el usuario es administrador.
     * Cambiar por la lógica real que uses (SharedPreferences, BD, etc.).
     */
    private boolean verificarSiEsAdmin() {
        // Lógica de prueba: aquí decides si es admin o no
        // Por ejemplo, puedes recibir este dato desde argumentos del fragmento
        Bundle args = getArguments();
        return args != null && args.getBoolean("isAdmin", false);
    }

    /**
     * Método para abrir un fragmento.
     */
    private void abrirFragmento(Fragment fragment) {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
