package com.example.lapizzautn.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lapizzautn.R;
import com.example.lapizzautn.adapter.PizzaAdapter;
import com.example.lapizzautn.entidad.Productos;

import java.util.List;

public class ListadoProductoFragment extends Fragment {

    private RecyclerView recyclerView;
    private PizzaAdapter pizzaAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listado_producto, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        // Configurar el RecyclerView con GridLayoutManager (2 columnas)
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Obtener los productos desde la base de datos
        List<Productos> listaPizzas = obtenerPizzasDesdeBD();

        if (listaPizzas != null && !listaPizzas.isEmpty()) {
            pizzaAdapter = new PizzaAdapter(getContext(), listaPizzas);
            recyclerView.setAdapter(pizzaAdapter);
        } else {
            Toast.makeText(getContext(), "No hay pizzas disponibles.", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Productos> obtenerPizzasDesdeBD() {
        // Aquí obtienes la lista de pizzas desde la base de datos (puedes modificar esto según cómo lo tengas)
        return Productos.obtenerTodasLasPizzas(getContext());
    }
}
