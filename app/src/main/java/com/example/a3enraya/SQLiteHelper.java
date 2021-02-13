package com.example.a3enraya;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "bd3enRaya.db";

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EstructuraBBDD.SQL_CREATE_TABLE_PARTIDAS);
        db.execSQL(EstructuraBBDD.SQL_CREATE_TABLE_USUARIOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se eliminan las tablas
        db.execSQL(EstructuraBBDD.SQL_DELETE_TABLE_PARTIDAS);
        db.execSQL(EstructuraBBDD.SQL_DELETE_TABLE_USUARIOS);

        //Se crean las tablas actualizadas
        db.execSQL(EstructuraBBDD.SQL_CREATE_TABLE_PARTIDAS);
        db.execSQL(EstructuraBBDD.SQL_CREATE_TABLE_USUARIOS);
    }

    public ArrayList llenar_lv(){
        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM PARTIDAS";
        Cursor registros = database.rawQuery(query, null);

        if (registros.moveToFirst()){
            do {
                lista.add(registros.getString(0));
            } while (registros.moveToNext());
        }

        return lista;
    }
}
