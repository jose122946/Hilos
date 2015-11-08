package com.example.beto.hilos;

import android.content.Context;
import android.view.View;

/**
 * Created by beto on 28/07/15.
 */
public class variables {
int x,y,yMax,tiempo=0,dt=10;
    float velocidad=5.0f;
    float velocidad2=0.5f;



    boolean izquierda = true;



    boolean decabeza=true;

    public variables(int x, int y) {

        this.x = x;
        this.y = y;
        //this.velocidad = velocidad;

    }

    public float getVelocidad2() {
        return velocidad2;
    }

    public void setVelocidad2(float velocidad2) {
        this.velocidad2 = velocidad2;
    }

    public boolean isDecabeza() {
        return decabeza;
    }
    public boolean isIzquierda() {
        return izquierda;
    }

    public void setIzquierda(boolean izquierda) {
        this.izquierda = izquierda;
    }
    public void setDecabeza(boolean decabeza) {
        this.decabeza = decabeza;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public float getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getyMax() {
        return yMax;
    }

    public void setyMax(int yMax) {
        this.yMax = yMax;
    }


}
