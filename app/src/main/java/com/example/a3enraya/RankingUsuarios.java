package com.example.a3enraya;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RankingUsuarios extends AppCompatActivity {
    ListView lvUsuarios;
    SQLiteHelper helper;
    ArrayList<Usuarios> listaUsuarios;
    ArrayList<String> listaInfUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_usuarios);

        helper = new SQLiteHelper(this);

        lvUsuarios = (ListView) findViewById(R.id.lvUsuarios);

        consultar();

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInfUsuarios);
        lvUsuarios.setAdapter(adaptador);
    }


    //metodo que recoge los datos de la consulta
    public void consultar(){
        SQLiteDatabase bd = helper.getReadableDatabase();

        Usuarios usuario = null;
        listaUsuarios = new ArrayList<Usuarios>();
        Cursor cursor = bd.rawQuery("SELECT usuario, partidas, puntos FROM " + EstructuraBBDD.EstructuraUsuarios.TABLE_NAME_USUARIOS, null);

        while (cursor.moveToNext()){
            usuario = new Usuarios();
            usuario.setUsuario(cursor.getString(0));
            usuario.setPartidas(cursor.getInt(1));
            usuario.setPuntos(cursor.getInt(2));

            listaUsuarios.add(usuario);
        }
        obtenerLista();
    }

    //obtiene la lista
    private void obtenerLista() {
        listaInfUsuarios=new ArrayList<String>();

        for (int i=0; i<listaUsuarios.size(); i++){
            listaInfUsuarios.add(listaUsuarios.get(i).getUsuario()+"     |     "+listaUsuarios.get(i).getPartidas()+"     |     "+listaUsuarios.get(i).getPuntos());
        }
    }
}