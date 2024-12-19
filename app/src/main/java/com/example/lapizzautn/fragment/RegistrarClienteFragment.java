package com.example.lapizzautn.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lapizzautn.R;
import com.example.lapizzautn.entidad.Clientes;

import java.util.Calendar;
import java.util.Locale;

public class RegistrarClienteFragment extends Fragment {

    private EditText etNombre, etApellido, etFechaNac, etCorreo, etDireccion, etTelefono, etClave, etReClave;
    private Button btnRegister;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registrar_cliente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar los elementos de la vista
        etNombre = view.findViewById(R.id.et_nombre);
        etApellido = view.findViewById(R.id.et_apellido);
        etFechaNac = view.findViewById(R.id.et_birthdate);
        etCorreo = view.findViewById(R.id.et_correo);
        etDireccion = view.findViewById(R.id.et_Direccion);
        etTelefono = view.findViewById(R.id.et_telefono);
        etClave = view.findViewById(R.id.et_clave);
        etReClave = view.findViewById(R.id.et_re_clave);
        btnRegister = view.findViewById(R.id.btn_register);

        // Mostrar el DatePicker cuando el usuario haga clic en el campo de fecha de nacimiento
        etFechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatePicker();
            }
        });

        // Configurar el botón de registro
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }

    /**
     * Método para registrar un usuario en la base de datos.
     */
    private void registrarUsuario() {
        // Obtener los datos ingresados por el usuario
        String nombre = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String fechaNac = etFechaNac.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String direccion = etDireccion.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String clave = etClave.getText().toString().trim();
        String reClave = etReClave.getText().toString().trim();

        // Validar los datos
        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(apellido) || TextUtils.isEmpty(fechaNac) ||
                TextUtils.isEmpty(correo) || TextUtils.isEmpty(direccion) || TextUtils.isEmpty(telefono) ||
                TextUtils.isEmpty(clave) || TextUtils.isEmpty(reClave)) {
            Toast.makeText(requireContext(), "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!clave.equals(reClave)) {
            Toast.makeText(requireContext(), "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Usar el método estático de la clase Clientes para insertar el cliente
        boolean resultado = Clientes.insertarCliente(requireContext(), nombre, apellido, fechaNac, correo, direccion, telefono, clave);

        if (resultado) {
            Toast.makeText(requireContext(), "Usuario registrado exitosamente.", Toast.LENGTH_SHORT).show();
            // Redirigir a la vista de Login o Home si es necesario
            requireActivity().onBackPressed(); // O reemplaza con la lógica de navegación deseada
        } else {
            Toast.makeText(requireContext(), "Error al registrar el usuario. Inténtelo nuevamente.", Toast.LENGTH_SHORT).show();
        }
    }


     //Muestra un cuadro de diálogo de calendario para seleccionar la fecha de nacimiento.

    private void mostrarDatePicker() {
        // Obtener la fecha actual para usar como predeterminada
        final Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) -> {
                    // Ajustar el formato de la fecha a Año-Mes-Día
                    String fechaSeleccionada = year + "-" +
                            String.format(Locale.getDefault(), "%02d", month + 1) + "-" +
                            String.format(Locale.getDefault(), "%02d", dayOfMonth);
                    etFechaNac.setText(fechaSeleccionada);
                },
                anio, mes, dia);

        // Mostrar el DatePicker
        datePickerDialog.show();
    }

}
