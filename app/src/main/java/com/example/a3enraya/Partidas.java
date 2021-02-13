package com.example.a3enraya;

public class Partidas {

    private int id;
    private String jugador1;
    private String jugador2;
    private String dificultad;
    private String resultado;

    public String getJugador1() {
        return jugador1;
    }

    public String getJugador2() {
        return jugador2;
    }

    public String getDificultad() {
        return dificultad;
    }

    public int getId() {
        return id;
    }

    public String getResultado() {
        return resultado;
    }

    public void setJugador1(String jugador1) {
        this.jugador1 = jugador1;
    }

    public void setJugador2(String jugador2) {
        this.jugador2 = jugador2;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public void setId(int id) {
        this.id = id;
    }

}
