package com.example.beto.hilos;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends Activity {

    boolean continuar=true; //Bandera para "pausar" el Thread
    float velocidad=5.0f;  // v = d / t      d = v * t
    float velocidad2=5.0f;
    int dt=20;   // Delta T (Incremento de Tiempo)
    int tiempo=0;  // Contador
    int x,y,ymax,xMax;
    Thread hilo=null;
    Rebotar rebotar;
    MediaPlayer mediaPlayer;
    SoundPool efecto;
    int flujo;
    ArrayList<variables> pelota= new ArrayList<variables>();//Objeto que implementa la intefaz runnable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        rebotar = new Rebotar(this);
        hilo = new Thread(rebotar);
        hilo.start();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(rebotar);
        mediaPlayer = MediaPlayer.create(this, R.raw.musica);
        mediaPlayer.setLooping(true);
        //mediaPlayer.start();
        efecto = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        flujo=efecto.load(this,R.raw.pong,1);
        variables obj = new variables(100,0);
        pelota.add(obj);


    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
// Esto es lo que hace mi botón al pulsar ir a atrás
            mediaPlayer.stop();
            continuar=false;

            finish();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onPause() {
        super.onPause();
        // continuar=false;   //Detener el hilo (Destruye porque termina)
       try {
           mediaPlayer.stop();
            continuar=false;
       }catch (Exception e)
       {
           Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
       }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(hilo==null) {
            hilo = new Thread(rebotar);
            hilo.start();
        }
        mediaPlayer.start();
        continuar=true;


    }


    public class Rebotar extends View implements Runnable {

        int x, y, yMax; //Coordenadas y límite
        Paint pincelFondo, pincelLetra, pincelPelota;
        Canvas canvas;
        Bitmap bmp, bmpRotated,bmparriizq,bmparrider,bmpabader,bmpabaizq;
        Boolean decabeza=true;
        Boolean izquerda=false;
        //izquierda=
        Matrix matrix = new Matrix();
        Matrix matrix2 = new Matrix();
        Matrix matrix3 = new Matrix();
        Matrix matrix4 = new Matrix();

        public Rebotar(Context context) {
            super(context);
            pincelFondo = new Paint();
            pincelLetra = new Paint();
            pincelPelota = new Paint();
            pincelFondo.setColor(Color.BLACK);
            pincelLetra.setColor(Color.BLACK);
            pincelPelota.setColor(Color.RED);
            pincelLetra.setTextSize(50);
            matrix.postRotate(45.0f);
            matrix2.postRotate(135.0f);

            matrix3.postRotate(225.0f);
            matrix4.postRotate(315.0f);


            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.space);
            //bmpRotated = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            bmparriizq=Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix4, true);
            bmparrider=Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            bmpabaizq=Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix3, true);
            bmpabader=Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix2, true);
        }


        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            x=w/2;
            y=0;
            yMax=h;
            xMax=w;


        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPaint(pincelFondo);
            //canvas.drawCircle(x, y, 100, pincelPelota);



                for (int i =0;i<pelota.size();i++) {
                    x=pelota.get(i).getX();
                    y=pelota.get(i).getY();
                    decabeza = pelota.get(i).isDecabeza();
                    izquerda = pelota.get(i).isIzquierda();

                    if (decabeza&&izquerda)
                    {
                        canvas.drawBitmap(bmpabader, x, y, null);
                    }
                    if (decabeza&&!izquerda)
                    {
                        canvas.drawBitmap(bmpabaizq, x, y, null);
                    }
                    if (!decabeza&&izquerda)
                    {
                        canvas.drawBitmap(bmparrider, x, y, null);
                    }
                    if (!decabeza&&!izquerda)
                    {
                        canvas.drawBitmap(bmparriizq, x, y, null);
                    }

                }

          //}
            //canvas.drawText("y=" + y, 50, 50, pincelLetra);
            //canvas.drawText("tiempo=" + tiempo, 50, 90, pincelLetra);

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                for (int i = 0; i <= 1000; i++) {
                    int random = (int) Math.round(Math.random() * getWidth());
                    int random2 = (int) Math.round(Math.random() * getHeight() - 100);
                    variables obj = new variables(random, random2);
                    pelota.add(obj);


                }

            }
            return true;
        }


        @Override
        public void run() {
//Aquí está la rutina del Thread
            while (continuar) {

                for (int i =0;i<pelota.size();i++) {
                    x = pelota.get(i).getX();
                    y = pelota.get(i).getY();
                    velocidad = pelota.get(i).getVelocidad();
                    velocidad2 = pelota.get(i).getVelocidad2();
                    decabeza=pelota.get(i).isDecabeza();
                    izquerda=pelota.get(i).isIzquierda();

                    tiempo += dt;  //Tiempo transcurrido del hilo.
                    //velocidad = distancia / tiempo    distancia (y) = y + v * dt
                    y = y + (int) (velocidad);
                    x=x+ (int) (velocidad2*10);
                    //Si llega abajo, invertimos la velocidad
                    if (y > yMax) {
                        velocidad = -velocidad;
                        decabeza = false;
                        efecto.play(flujo, 1, 1, 0, 0, 1);
                        efecto.stop(flujo);
                    }
                    if (y < 0) {
                        velocidad = -velocidad;
                        decabeza = true;
                        efecto.play(flujo,1,1,0,0,1);
                        efecto.stop(flujo);
                    }
                    if (x > xMax) {
                        velocidad2 = -velocidad2;
                        izquerda = false;
                        efecto.play(flujo,1,1,0,0,1);
                        efecto.stop(flujo);
                    }
                    if (x < 0) {
                        velocidad2 = -velocidad2;
                        izquerda = true;
                        efecto.play(flujo,1,1,0,0,1);
                        efecto.stop(flujo);
                    }
                    pelota.get(i).setX(x);
                    pelota.get(i).setY(y);
                    pelota.get(i).setVelocidad(velocidad);
                    pelota.get(i).setVelocidad2(velocidad2);
                    pelota.get(i).setDecabeza(decabeza);
                    pelota.get(i).setIzquierda(izquerda);

                   // pelota.get(i).setTiempo(tiempo);
                    postInvalidate();



                    //Similar a invalidate(). postInvalidate() se usa fuera del View

                }
                try {
                    Thread.sleep(dt);
                } catch (InterruptedException ex) {

                }
                }
            }

    }//Fin class Rebotar





}
