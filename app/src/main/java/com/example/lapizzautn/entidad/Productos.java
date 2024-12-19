package com.example.lapizzautn.entidad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Productos {
    private int id;
    private String nombre;
    private String precio;
    private String descripcion;
    private boolean estado;


    //-----Contructor------
    public Productos(int id, String nombre, String precio, String descripcion, boolean estado) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    // ------Getters and Setters-------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Método para insertar producto en la base de datos
    public static long insertarProducto(Context context, String nombre, String precio, String descripcion, boolean estado) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.PRODUCTOS_COL_NOMBRE, nombre);
        values.put(DatabaseHelper.PRODUCTOS_COL_PRECIO, precio);
        values.put(DatabaseHelper.PRODUCTOS_COL_DESCRIPCION, descripcion);
        values.put(DatabaseHelper.PRODUCTOS_COL_ESTADO, estado ? 1 : 0);

        // Insertar y obtener el ID autoincrementable
        long result = db.insert(DatabaseHelper.TABLE_PRODUCTOS, null, values);
        db.close();
        return result; // Devolver el ID autoincrementado o -1 si hubo un error
    }

    //funcion para optener todas las pizzas cargadas
    public static List<Productos> obtenerTodasLasPizzas(Context context) {
        List<Productos> listaPizzas = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Consulta para obtener todas las pizzas
        Cursor cursor = db.query(DatabaseHelper.TABLE_PRODUCTOS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PRODUCTOS_COL_ID));
                String nombre = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCTOS_COL_NOMBRE));
                String precio = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCTOS_COL_PRECIO));
                String descripcion = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PRODUCTOS_COL_DESCRIPCION));
                boolean estado = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PRODUCTOS_COL_ESTADO)) == 1;

                Productos pizza = new Productos(id, nombre, precio, descripcion, estado);
                listaPizzas.add(pizza);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return listaPizzas;
    }
}
