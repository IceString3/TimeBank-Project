package com.example.timebank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "USUARIOS";

    // Table columns
    public static final String _ID = "_id";
    public static final String NOMBRE = "nombre";
    public static final String APELLIDO1 = "apellido1";
    public static final String APELLIDO2 = "apellido2";
    public static final String TLF_FIJO = "tlf_fijo";
    public static final String TLF_MOVIL = "tlf_movil";
    public static final String LOCALIDAD = "localidad";
    public static final String PROFESION = "profesion";
    public static final String SERVICIO1 = "servicio1";
    public static final String SERVICIO2 = "servicio2";
    public static final String CREDITOS = "creditos";
    public static final String TOTALHORAS = "totalhoras";
    public static final String N_AYUDAS = "n_ayudas";
    public static final String N_AYUDADO = "n_ayudado";

    // Database Information
    static final String DB_NAME = "USERS.DB";

    // database version
    static final int DB_VERSION = 1;

    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " String PRIMARY KEY, " + NOMBRE + " TEXT NOT NULL, " + APELLIDO1 + " TEXT NOT NULL, "
            + APELLIDO2 + ", " + TLF_FIJO + ", " + TLF_MOVIL + ", " + LOCALIDAD + " TEXT NOT NULL, "
            + PROFESION + " TEXT NOT NULL, " + SERVICIO1 + " TEXT NOT NULL, " + SERVICIO2 + ");";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
