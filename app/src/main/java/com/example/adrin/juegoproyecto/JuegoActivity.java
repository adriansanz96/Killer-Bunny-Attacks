package com.example.adrin.juegoproyecto;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class JuegoActivity extends Activity  {

    RelativeLayout layout;
    Typeface TF;
    MainActivity menu;
    String idioma;
    PopupWindow popupWindow;
    Button salir, cancelar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        //Ventana completa
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String font_path = "font/gillsans.ttf";  //definimos un STRING con el valor PATH
        TF = Typeface.createFromAsset(getAssets(), font_path);

        //Recoger parametros
        Bundle bundle = getIntent().getExtras();
        int modo_de_juego=bundle.getInt("modo");
        int tiempo_nivel=bundle.getInt("tiempo_nivel");
        int verd_nivel=bundle.getInt("verd_nivel");
        int fals_nivel=bundle.getInt("fals_nivel");
        idioma=bundle.getString("idioma");

        if (findViewById(R.id.framelayoutjuego) != null) {
            if (savedInstanceState != null) {
            }


                JuegoFragmentTouch fragment2 = new JuegoFragmentTouch();

                Bundle bundle2 = new Bundle();
                bundle2.putInt("tiempo_nivel", tiempo_nivel);
                bundle2.putInt("verd_nivel", verd_nivel);
                bundle2.putInt("fals_nivel", fals_nivel);
                bundle2.putString("idioma", idioma);
                fragment2.setArguments(bundle2);

                getFragmentManager().beginTransaction().replace(R.id.framelayoutjuego, fragment2).commit();

        }
    }

    //Accion al pulsar atrás
    public void onBackPressed() {

        try{
            popupWindow.dismiss();
        }catch (Exception e){}

        final Dialog alertDialogsalir = new Dialog(JuegoActivity.this);
        alertDialogsalir.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialogsalir.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v2 = inflater.inflate(R.layout.salida_confirm, null);

        salir = (Button) v2.findViewById(R.id.botonsalir);
        cancelar = (Button) v2.findViewById(R.id.botoncancelar);
        ImageView imagen1 = (ImageView) v2.findViewById(R.id.imageView15);
        TextView texto1 = (TextView) v2.findViewById(R.id.imageView14);

        salir.setTypeface(TF);   //finalmente aplicamos TYPEFACE al TEXTVIEW
        cancelar.setTypeface(TF);   //finalmente aplicamos TYPEFACE al TEXTVIEW

        //iniciar animación
        Animation dentro;
        dentro = AnimationUtils.loadAnimation(getApplication(), R.anim.mover);
        dentro.setDuration(400);
        imagen1.startAnimation(dentro);
        texto1.startAnimation(dentro);
        salir.startAnimation(dentro);
        cancelar.startAnimation(dentro);

        cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{menu.sound.start();}catch (Exception e){}
                alertDialogsalir.cancel();
            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{menu.sound.start();}catch (Exception e){}
                finish();
            }
        });

        //Aplicar layout y lanzar ventana dialog
        alertDialogsalir.setContentView(v2);
        alertDialogsalir.show();


    }
    @Override
    protected void onPause() {
        if (menu.reproductor != null && menu.reproductor.isPlaying()) {
            menu.posicioncancion = menu.reproductor.getCurrentPosition();
            menu.reproductor.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (menu.reproductor != null && menu.reproductor.isPlaying() == false) {
            menu.reproductor.seekTo(menu.posicioncancion);
            menu.reproductor.start();
        }
        super.onResume();
    }
}


