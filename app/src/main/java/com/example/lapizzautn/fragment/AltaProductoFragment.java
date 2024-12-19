package com.example.lapizzautn.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lapizzautn.R;
import com.example.lapizzautn.entidad.Productos;

public class AltaProductoFragment extends Fragment {

    private EditText etNombre, etPrecio, etDescripcion;
    private Spinner spEstado;
    private Button btnCrearPizza;
    private TextView tvId;
    private String estadoSeleccionado = "Activo"; // Valor por defecto

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alta_producto, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar vistas
        etNombre = view.findViewById(R.id.etNombre);
        etPrecio = view.findViewById(R.id.etPrecio);
        etDescripcion = view.findViewById(R.id.etDescripcion);
        spEstado = view.findViewById(R.id.spEstado);
        btnCrearPizza = view.findViewById(R.id.btnCrearPizza);
        tvId = view.findViewById(R.id.tvId); // TextView para mostrar el ID

        // Configurar Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.estado_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstado.setAdapter(adapter);

        spEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estadoSeleccionado = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                estadoSeleccionado = "Activo"; // Valor por defecto
            }
        });

        // Configurar botón
        btnCrearPizza.setOnClickListener(v -> crearPizza());
    }

    /**
     * Método para crear una nueva pizza en la base de datos.
     */
    private void crearPizza() {
        String nombre = etNombre.getText().toString().trim();
        String precioStr = etPrecio.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();

        if (nombre.isEmpty() || precioStr.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double precio = Double.parseDouble(precioStr);

            // Convertir estado
            boolean estado = estadoSeleccionado.equals("Activo");

            // Llamar al método estático de la clase Productos
            long idProducto = Productos.insertarProducto(requireContext(), nombre, String.valueOf(precio), descripcion, estado);

            if (idProducto != -1) {
                // Mostrar el ID recién generado en el TextView
                tvId.setText("ID: " + idProducto);

                Toast.makeText(requireContext(), "Pizza creada exitosamente.", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            } else {
                Toast.makeText(requireContext(), "Error al crear la pizza. Inténtelo nuevamente.", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Ingrese un precio válido.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Limpia los campos después de crear un producto.
     */
    private void limpiarCampos() {
        etNombre.setText("");
        etPrecio.setText("");
        etDescripcion.setText("");
        spEstado.setSelection(0); // Volver a "Activo"
    }
}
