package com.example.lapizzautn.entidad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Nombre y versión de la base de datos
    private static final String DATABASE_NAME = "LaPizzaUTN.db";
    private static final int DATABASE_VERSION = 1;

    // Nombre de las tablas
    public static final String TABLE_CLIENTES = "Clientes";
    public static final String TABLE_PRODUCTOS = "Productos";

    // Columnas de la tabla Clientes
    public static final String CLIENTES_COL_ID = "ID";
    public static final String CLIENTES_COL_NOMBRE = "Nombre";
    public static final String CLIENTES_COL_APELLIDO = "Apellido";
    public static final String CLIENTES_COL_FECHA_NAC = "FechaNac";
    public static final String CLIENTES_COL_EMAIL = "Email";
    public static final String CLIENTES_COL_DIRECCION = "Direccion";
    public static final String CLIENTES_COL_TELEFONO = "Telefono";
    public static final String CLIENTES_COL_CLAVE = "Clave";

    // Columnas de la tabla Productos
    public static final String PRODUCTOS_COL_ID = "ID";
    public static final String PRODUCTOS_COL_NOMBRE = "Nombre";
    public static final String PRODUCTOS_COL_PRECIO = "Precio";
    public static final String PRODUCTOS_COL_DESCRIPCION = "Descripcion";
    public static final String PRODUCTOS_COL_ESTADO = "Estado";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla Clientes
        String createTableClientes = "CREATE TABLE " + TABLE_CLIENTES + " ("
                + CLIENTES_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CLIENTES_COL_NOMBRE + " TEXT, "
                + CLIENTES_COL_APELLIDO + " TEXT, "
                + CLIENTES_COL_FECHA_NAC + " TEXT, "
                + CLIENTES_COL_EMAIL + " TEXT, "
                + CLIENTES_COL_DIRECCION + " TEXT, "
                + CLIENTES_COL_TELEFONO + " TEXT, "
                + CLIENTES_COL_CLAVE + " TEXT)";
        db.execSQL(createTableClientes);

        // Crear tabla Productos
        String createTableProductos = "CREATE TABLE " + TABLE_PRODUCTOS + " ("
                + PRODUCTOS_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PRODUCTOS_COL_NOMBRE + " TEXT, "
                + PRODUCTOS_COL_PRECIO + " TEXT, "
                + PRODUCTOS_COL_DESCRIPCION + " TEXT, "
                + PRODUCTOS_COL_ESTADO + " INTEGER)";
        db.execSQL(createTableProductos);

        // Insertar usuario inicial
        insertarUsuarioInicial(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTOS);
        onCreate(db);
    }

    private void insertarUsuarioInicial(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(CLIENTES_COL_NOMBRE, "admin");
        values.put(CLIENTES_COL_APELLIDO, "admin");
        values.put(CLIENTES_COL_FECHA_NAC, "1999-02-16");
        values.put(CLIENTES_COL_EMAIL, "admin@lapizzautn.com");
        values.put(CLIENTES_COL_DIRECCION, "admin");
        values.put(CLIENTES_COL_TELEFONO, "123456789");
        values.put(CLIENTES_COL_CLAVE, "pass");
        db.insert(TABLE_CLIENTES, null, values);
    }

    public boolean verificarUsuario(String nombre, String clave) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CLIENTES + " WHERE " + CLIENTES_COL_NOMBRE + " = ? AND " + CLIENTES_COL_CLAVE + " = ?";
        String[] selectionArgs = {nombre, clave};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

}

