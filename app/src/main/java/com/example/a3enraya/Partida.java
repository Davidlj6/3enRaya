package com.example.a3enraya;

import java.util.Random;

public class Partida {

    public Partida(int dificultad) {

        this.dificultad = dificultad;
        jugador=1;
        casillas_usadas=new int[9];

        for(int i=0;i<9;i++){
            casillas_usadas[i]=0;
        }
    }

    //metodo para jugar, devolverá la casilla siguiente a marcar al azar
    public int ia(){
        int casilla;

        //programamos lo que hará la máquina en el nivel normal
        casilla=dosEnRaya(2);//miramos si el jugador dos tiene posibilidad de hacer tres en raya

        if(casilla!=-1) return casilla;

        //comprobamos si el jugador tiene posibilidad de hacer tres en raya para impedirlo
        if(dificultad>0){
            casilla=dosEnRaya(1);

            if(casilla!=-1){
                return casilla;
            }
        }

        if(dificultad==2){
            //si las casillas de las esquinas no están marcadas las elijo
            if(casillas_usadas[0]==0) return 0;
            if(casillas_usadas[2]==0) return 2;
            if(casillas_usadas[6]==0) return 6;
            if(casillas_usadas[8]==0) return 8;
        }

        //con esta opción el nivel de dificultad fácil estaría
        Random casilla_next=new Random();
        casilla=casilla_next.nextInt(9);
        return casilla;

    }

    //metodo para cambiar de jugador
    //hacemos que devuelva un int depediendo si la va empate o cual jugador gana
    //empate:3
    //jugador1 gana:1
    //jugador2 gana:2
    //ninguna de las anteirores:0 (se sigue con el juego)
    public int turno(){

        boolean empate=true;
        boolean ult_movimiento=true;

        for (int i=0;i<COMBINACIONES.length;i++) {

            for(int pos:COMBINACIONES[i]) {
                System.out.println("valor en posicion " + pos + " " + casillas_usadas[pos]);

                if(casillas_usadas[pos]!=jugador){
                    ult_movimiento=false;
                }

                if(casillas_usadas[pos]==0){
                    empate=false;
                }

            }
            System.out.println("----------------------------------------------------------");

            if(ult_movimiento==true){
                return jugador;
            }

            ult_movimiento=true;
        }

        if(empate){
            return 3;
        }

        jugador++;

        if(jugador>2){
            jugador=1;
        }
        return 0;
    }

    //metodo para que nos diga la casilla que falta a la máquina para hacer tres en raya
    public int dosEnRaya(int jugador_en_turno){

        int casilla=-1;

        //variable que nos dirá si ha conseguido 2 en raya
        int cuantas=0;

        for (int i=0;i<COMBINACIONES.length;i++) {

            for(int pos:COMBINACIONES[i]) {

                //evaluo si la casilla está marcada o no por el jugador en turno
                if(casillas_usadas[pos]==jugador_en_turno){
                    cuantas++;
                }

                if(casillas_usadas[pos]==0) casilla=pos;

            }

            if((cuantas==2) && casilla!=-1) return casilla;

            casilla=-1;
            cuantas=0;

        }

        return -1;

    }

    //metodo que evalúa si la casilla está o no ocupada
    public boolean casilla_libre(int casilla){
        if(casillas_usadas[casilla]!=0){
            return false;
        }else {
            casillas_usadas[casilla]=jugador;
        }
        return true;
    }

    //creamos una constante de clase para almacenar la dificultad
    public final int dificultad;
    public int jugador;
    private int [] casillas_usadas;
    private final int[][] COMBINACIONES= {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

}
