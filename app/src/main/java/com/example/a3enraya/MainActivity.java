package com.example.a3enraya;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class MainActivity extends Activity {

    MediaPlayer ficha, fin;
    Button btnRanking, btnPartidas;
    int dificultad;
    ArrayList<Partidas> listaPartidas;
    int partidasj1 = 1;
    int puntosj1 = 0;
    ImageButton btnMutear;
    int mutear = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Botón a actividad partidas jugadas */
        btnPartidas = (Button) findViewById(R.id.btnPartidas);
        btnPartidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), PartidasJugadas.class);
                startActivityForResult(intent, 0);
            }
        });


        /* Botón a actividad ranking usuarios */
        btnRanking = (Button) findViewById(R.id.btnRanking);
        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent (v.getContext(), RankingUsuarios.class);
                startActivityForResult(intent2, 0);
            }
        });

        /* Botón a mutear/desmutear sonido */
        btnMutear = (ImageButton) findViewById(R.id.btnMutear);
        btnMutear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mutear == 0) {
                    btnMutear.setImageResource(R.drawable.desmutear);
                    mutear = 1;
                } else if (mutear == 1){
                    btnMutear.setImageResource(R.drawable.mutear);
                    mutear = 0;
                }
            }
        });


        //Inicializamos el array con cada casilla del tablero
        CASILLAS= new int[9];
        CASILLAS[0]=R.id.a1;
        CASILLAS[1]=R.id.a2;
        CASILLAS[2]=R.id.a3;
        CASILLAS[3]=R.id.b1;
        CASILLAS[4]=R.id.b2;
        CASILLAS[5]=R.id.b3;
        CASILLAS[6]=R.id.c1;
        CASILLAS[7]=R.id.c2;
        CASILLAS[8]=R.id.c3;

        //Sonidos
        ficha = MediaPlayer.create(this, R.raw.fichas);
        fin = MediaPlayer.create(this, R.raw.fin);

        //Consulta ultima partida
        SQLiteOpenHelper helper = new SQLiteHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        //Mensaje toast con el resultado de la ultima partida jugada
        Partidas partidas = null;
        listaPartidas = new ArrayList<Partidas>();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + EstructuraBBDD.EstructuraPartidas.TABLE_NAME_PARTIDAS, null);
            cursor.moveToPosition(cursor.getCount() - 1);

            partidas = new Partidas();
            partidas.setId(cursor.getInt(0));
            partidas.setJugador1(cursor.getString(1));
            partidas.setJugador2(cursor.getString(2));
            partidas.setResultado(cursor.getString(3));
            partidas.setDificultad(cursor.getString(4));


            Toast.makeText(this, partidas.getJugador1() + " | " + partidas.getJugador2() + " | " + partidas.getDificultad() + " | " + partidas.getResultado(), Toast.LENGTH_LONG).show();

        } catch (Exception e){
            Toast.makeText(this, "No hay partidas", Toast.LENGTH_LONG).show();
        }


    }

    //Método recarga el menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }

    //Método establece funciones a las opciones del menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.consultar:
                Intent intent = new Intent(this, Partidas.class);
                startActivity(intent);
                break;

            case R.id.modifcar:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Jugar(View v){

        //reseteamos el tablero
        ImageView imagen;

        for (int casilla:CASILLAS){
            imagen=(ImageView)findViewById(casilla);

            imagen.setImageResource(R.drawable.casilla);
        }

        //establecemos los jugadores que van a jugar (1 o 2 jugadores)
        jugadores=1;
        //el metodo Jugar será llamado tanto en el botón de un jugador como en el de dos
        //por eso comprobamos la vista que entra como parámetro
        if(v.getId()==R.id.dosjugadores){
            jugadores=2;
        }

        //evaluamos la dificultad
        RadioGroup configDificultad=(RadioGroup)findViewById(R.id.configD);

        int id=configDificultad.getCheckedRadioButtonId();

        dificultad=0;

        if(id==R.id.normal){
            dificultad=1;
        }else if(id==R.id.imposible){
            dificultad=2;
        }

        partida=new Partida(dificultad);

        //deshabilitamos los botones del tablero
        ((Button)findViewById(R.id.unjugador)).setEnabled(false);
        ((Button)findViewById(R.id.dosjugadores)).setEnabled(false);
        ((RadioGroup)findViewById(R.id.configD)).setAlpha(0);

    }

    //creamos el método que se lanza al pulsar cada casilla
    public void toqueCasilla(View v){

        //hacemos que sólo se ejecute cuando la variable partida no sea null
        if(partida==null){
            return;
        }else{
            if (mutear == 1){
                //Sonido ficha
                ficha.start();
            }

            int casilla=0;
            //recorremos el array donde tenemos almacenada cada casilla
            for(int i=0;i<9;i++){
                if(CASILLAS[i]==v.getId()){
                    casilla=i;
                    break;
                }
            }


            //si la casilla pulsada ya está ocupada salimos del método
            if(partida.casilla_libre(casilla)==false){
                return;
            }
            //llamamos al método para marcar la casilla que se ha tocado
            marcaCasilla(casilla);

            int resultado=partida.turno();

            if(resultado>0){
                terminar_partida(resultado);
                return;
            }

            if (jugadores==1){
                //realizamos el marcado de la casilla que elige el programa
                casilla=partida.ia();

                while (partida.casilla_libre(casilla)!=true){
                    casilla=partida.ia();
                }

                marcaCasilla(casilla);

                resultado=partida.turno();

                if(resultado>0){
                    terminar_partida(resultado);
                }
            }
        }
    }

    private void terminar_partida(int res){
        if (mutear == 1) {
            //Sonido fin partida
            fin.start();
        }

        String mensaje;

        if(res==1) mensaje="Han ganado los círculos";

        else if(res==2) mensaje="Han ganado las aspas";

        else mensaje="Empate";

        Toast toast= Toast.makeText(this,mensaje,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

        //terminamos el juego
        partida=null;

        //habilitamos los botones del tablero
        ((Button)findViewById(R.id.unjugador)).setEnabled(true);
        ((Button)findViewById(R.id.dosjugadores)).setEnabled(true);
        ((RadioGroup)findViewById(R.id.configD)).setAlpha(1);

        //Inserta en la tabla Partidas de BBDD el resultado de las partidas jugadas, la dificultad y los jugadores
        SQLiteOpenHelper helper = new SQLiteHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        if (jugadores == 1){
            ContentValues registro = new ContentValues();
            registro.put(EstructuraBBDD.EstructuraPartidas.COLUMN_NAME_JUGADOR1, "Jugador 1 (O)");
            registro.put(EstructuraBBDD.EstructuraPartidas.COLUMN_NAME_JUGADOR2, "Máquina (X)");
            if (dificultad == 0){
                registro.put(EstructuraBBDD.EstructuraPartidas.COLUMN_NAME_DIFICULTAD, "Fácil");
            }else if (dificultad == 1) {
                registro.put(EstructuraBBDD.EstructuraPartidas.COLUMN_NAME_DIFICULTAD, "Normal");
            }else if (dificultad == 2){
                registro.put(EstructuraBBDD.EstructuraPartidas.COLUMN_NAME_DIFICULTAD, "Extremo");
            }
            registro.put(EstructuraBBDD.EstructuraPartidas.COLUMN_NAME_RESULTADO, mensaje);
            db.insert(EstructuraBBDD.EstructuraPartidas.TABLE_NAME_PARTIDAS, null, registro);
        } else{
            ContentValues registro = new ContentValues();
            registro.put(EstructuraBBDD.EstructuraPartidas.COLUMN_NAME_JUGADOR1, "Jugador 1 (O)");
            registro.put(EstructuraBBDD.EstructuraPartidas.COLUMN_NAME_JUGADOR2, "Jugador 2 (X)");
            registro.put(EstructuraBBDD.EstructuraPartidas.COLUMN_NAME_DIFICULTAD, "-");
            registro.put(EstructuraBBDD.EstructuraPartidas.COLUMN_NAME_RESULTADO, mensaje);
            db.insert(EstructuraBBDD.EstructuraPartidas.TABLE_NAME_PARTIDAS, null, registro);
        }
        db.close();

        SQLiteOpenHelper helper1 = new SQLiteHelper(this);
        SQLiteDatabase db1 = helper1.getWritableDatabase();
        //Inserta en la tabla usuarios de BBDD el nombre, las partidas jugadas y los puntos por cada usuario
        String usuario1 = "Jugador 1 (O)";


        ContentValues registroUsuarios = new ContentValues();
        registroUsuarios.put(EstructuraBBDD.EstructuraUsuarios.COLUMN_NAME_USUARIO, usuario1);
        registroUsuarios.put(EstructuraBBDD.EstructuraUsuarios.COLUMN_NAME_PARTIDAS, partidasj1);
        registroUsuarios.put(EstructuraBBDD.EstructuraUsuarios.COLUMN_NAME_PUNTOS, puntosj1);

        int existe = db1.update("usuarios", registroUsuarios, "usuario='"+usuario1+"'",null);

        if (existe == 1){
            partidasj1 = partidasj1 + 1;
            if (mensaje.equals("Han ganado los círculos")){
                puntosj1 = puntosj1 + 3;
            } else if(mensaje.equals("Han ganado las aspas")){
                puntosj1 = puntosj1 - 2;
            }
        } else{
            db1.insert(EstructuraBBDD.EstructuraUsuarios.TABLE_NAME_USUARIOS, null, registroUsuarios);
        }


    }

    //metodo para marcar las casillas
    private void marcaCasilla(int casilla){
        ImageView imagen;
        imagen=(ImageView)findViewById(CASILLAS[casilla]);

        if(partida.jugador==1){
            imagen.setImageResource(R.drawable.circulo);
        }else{
            imagen.setImageResource(R.drawable.aspa);
        }
    }



    //creamos un campo de clase para almacenar cuantos jugadores hay
    private int jugadores;
    //para guardar la casilla pulsada
    private int[] CASILLAS;

    private Partida partida;

}