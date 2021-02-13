package com.example.a3enraya;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class PartidasJugadas extends AppCompatActivity {
    ListView lvPartidas;
    SQLiteHelper helper;
    ArrayList<Partidas> listaPartidas;
    ArrayList<String> listaInformacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partidas_jugadas);

        helper = new SQLiteHelper(this);

        lvPartidas = (ListView) findViewById(R.id.lvPartidas);

        consultar();

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
        lvPartidas.setAdapter(adaptador);
    }


    //metodo que recoge los datos de la consulta
    public void consultar(){
        SQLiteDatabase bd = helper.getReadableDatabase();

        Partidas partidas = null;
        listaPartidas = new ArrayList<Partidas>();
        Cursor cursor = bd.rawQuery("SELECT jugador1, jugador2, dificultad, resultado FROM " + EstructuraBBDD.EstructuraPartidas.TABLE_NAME_PARTIDAS, null);

        while (cursor.moveToNext()){
            partidas = new Partidas();
            partidas.setJugador1(cursor.getString(0));
            partidas.setJugador2(cursor.getString(1));
            partidas.setDificultad(cursor.getString(2));
            partidas.setResultado(cursor.getString(3));

            listaPartidas.add(partidas);
        }
        obtenerLista();
    }

    //obtiene la lista
    private void obtenerLista() {
        listaInformacion=new ArrayList<String>();

        for (int i=0; i<listaPartidas.size(); i++){
            listaInformacion.add(listaPartidas.get(i).getJugador1()+"     |     "+listaPartidas.get(i).getJugador2()+"     |     "+listaPartidas.get(i).getDificultad()+"     |     "+listaPartidas.get(i).getResultado());
        }
    }
}