package com.example.a3enraya;

import android.provider.BaseColumns;

public class EstructuraBBDD {

    public static final String SQL_CREATE_TABLE_PARTIDAS =
            "CREATE TABLE IF NOT EXISTS " + EstructuraPartidas.TABLE_NAME_PARTIDAS +
            "(" + EstructuraPartidas._ID + " integer PRIMARY KEY, "
            + EstructuraPartidas.COLUMN_NAME_JUGADOR1 + " text, "
            + EstructuraPartidas.COLUMN_NAME_JUGADOR2 + " text, "
            + EstructuraPartidas.COLUMN_NAME_DIFICULTAD + " text, "
            + EstructuraPartidas.COLUMN_NAME_RESULTADO + " text);";

    public static final String SQL_DELETE_TABLE_PARTIDAS =
            "DROP TABLE IF EXISTS " + EstructuraPartidas.TABLE_NAME_PARTIDAS;


    public static final String SQL_CREATE_TABLE_USUARIOS =
            "CREATE TABLE IF NOT EXISTS " + EstructuraUsuarios.TABLE_NAME_USUARIOS +
                    "(" + EstructuraUsuarios._ID + " integer PRIMARY KEY, "
                    + EstructuraUsuarios.COLUMN_NAME_USUARIO + " text, "
                    + EstructuraUsuarios.COLUMN_NAME_PARTIDAS + " integer, "
                    + EstructuraUsuarios.COLUMN_NAME_PUNTOS + " integer);";

    public static final String SQL_DELETE_TABLE_USUARIOS =
            "DROP TABLE IF EXISTS " + EstructuraUsuarios.TABLE_NAME_USUARIOS;


    private EstructuraBBDD(){}

    /*Clase interna que define la estructura de la tabla de partidas*/
    public static class EstructuraPartidas implements BaseColumns{
        public static final String TABLE_NAME_PARTIDAS = "partidas";
        public static final String COLUMN_NAME_JUGADOR1 = "jugador1";
        public static final String COLUMN_NAME_JUGADOR2 = "jugador2";
        public static final String COLUMN_NAME_DIFICULTAD = "dificultad";
        public static final String COLUMN_NAME_RESULTADO = "resultado";
    }

    /*Clase interna que define la estructura de la tabla de usuarios*/
    public static class EstructuraUsuarios implements BaseColumns{
        public static final String TABLE_NAME_USUARIOS = "usuarios";
        public static final String COLUMN_NAME_USUARIO = "usuario";
        public static final String COLUMN_NAME_PARTIDAS = "partidas";
        public static final String COLUMN_NAME_PUNTOS = "puntos";
    }
}
