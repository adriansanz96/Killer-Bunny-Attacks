package com.example.adrin.juegoproyecto;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class JuegoNivelesActivity extends Activity implements ListFragmentNiveles.ListFragmentListener{

    //Definiciones
    CheckBox acelerometro, mano;
    int modo_de_juego=0,tiempo_nivel,verd_nivel,fals_nivel, mano_elegida=0;
    MainActivity menu;
    Typeface TF;
    EditText p_verd,p_fals,minutos;
    String idioma;
    AdView mAdView;
    AdRequest adRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_niveles);

        //Ventana completa
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Recoger parametros
        Bundle bundle = getIntent().getExtras();
        idioma=bundle.getString("idioma");


        //************************acelerometro = (CheckBox) findViewById(R.id.checkBox);
        //************************mano = (CheckBox) findViewById(R.id.checkBox2);

        //fuente
//**********************          String font_path = "font/gillsans.ttf";  //definimos un STRING con el valor PATH
//**********************          TF = Typeface.createFromAsset(getAssets(), font_path);
//**********************        acelerometro.setTypeface(TF);   //finalmente aplicamos TYPEFACE al TEXTVIEW
//**********************          mano.setTypeface(TF);

        //ad
        mAdView = (AdView) findViewById(R.id.adView2);
        adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                       //  .addTestDevice("4F2D49C5F7799114ED2DA613E8504894")  // An example device ID
                .build();
        mAdView.loadAd(adRequest);

        //iniciar animación
//**********************        Animation dentro;
//**********************        dentro = AnimationUtils.loadAnimation(getApplication(), R.anim.aumentar3);
//**********************        dentro.setDuration(700);
//**********************          acelerometro.startAnimation(dentro);
//**********************          mano.startAnimation(dentro);


        //creo un Listener para bloquear el segundo checkbox en caso de que no este marcado el primero
//**********************        acelerometro.setOnClickListener(new View.OnClickListener() {
//**********************            public void onClick(View v) {
//**********************                if (acelerometro.isChecked()) {
//**********************                    mano.setEnabled(true);
//**********************                } else {
//**********************                    mano.setEnabled(false);
//**********************                    mano.setChecked(false);
        //**********************               }
//**********************            }
//**********************        });
    }

    protected void onDestroy(){
        super.onDestroy();
        try {
           // menu.reproductor.stop();
            //    menu = new MainActivity();
            //    menu.ma.finish();
        }
        catch (Exception e){}
        if (mAdView != null) {
            mAdView.destroy();
        }
    }



    //Elección ListView
    @Override
    public void onListSelected(int position, String item) {
        //**********************    if (acelerometro.isChecked()) { //Si CheckBox está marcado, pasará por parametro "1" que después servirá para elegir entre JuegoFragmentAcelerador o JuegoFragmentTouch
        //**********************      modo_de_juego = 1;
        //**********************  }
        //**********************   if (mano.isChecked()) { //Si CheckBox está marcado, pasará por parametro "1" que después servirá para elegir entre JuegoFragmentAcelerador o JuegoFragmentTouch
        //**********************      mano_elegida = 1;
        //**********************  }



        if (item == "Nivel1") {
            try{menu.sound.start();}catch (Exception e){}
            //parametros para nivel1
            tiempo_nivel=40;
            verd_nivel=5;
            fals_nivel=5;

            Intent intent = new Intent(this, JuegoActivity.class);
            intent.putExtra("modo", modo_de_juego);//pasar parametro para saber si checkbox esta checado o no
            intent.putExtra("mano", mano_elegida);
            intent.putExtra("tiempo_nivel", tiempo_nivel);
            intent.putExtra("verd_nivel", verd_nivel);
            intent.putExtra("fals_nivel", fals_nivel);
            intent.putExtra("idioma", idioma);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
            finish();
        }
        if (item == "Nivel2") {
            try{menu.sound.start();}catch (Exception e){}
            //parametros nivel2
            tiempo_nivel=100;
            verd_nivel=8;
            fals_nivel=5;

            Intent intent2 = new Intent(this, JuegoActivity.class);
            intent2.putExtra("modo", modo_de_juego);//pasar parametro para saber si checkbox esta checado o no
            intent2.putExtra("mano", mano_elegida);
            intent2.putExtra("tiempo_nivel", tiempo_nivel);
            intent2.putExtra("verd_nivel", verd_nivel);
            intent2.putExtra("fals_nivel", fals_nivel);
            intent2.putExtra("idioma", idioma);
            startActivity(intent2);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
            finish();
        }
        if (item == "Nivel3") {
            try{menu.sound.start();}catch (Exception e){}
            //parametros nivel3
            tiempo_nivel=250;
            verd_nivel=30;
            fals_nivel=4;

            Intent intent3 = new Intent(this, JuegoActivity.class);
            intent3.putExtra("modo", modo_de_juego);//pasar parametro para saber si checkbox esta checado o no
            intent3.putExtra("mano", mano_elegida);
            intent3.putExtra("tiempo_nivel", tiempo_nivel);
            intent3.putExtra("verd_nivel", verd_nivel);
            intent3.putExtra("fals_nivel", fals_nivel);
            intent3.putExtra("idioma", idioma);
            startActivity(intent3);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
            finish();
        }

        if (item == "Personalizar") {
            try{menu.sound.start();}catch (Exception e){}
            final Dialog alertDialogsalir = new Dialog(JuegoNivelesActivity.this);
            alertDialogsalir.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialogsalir.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View v=inflater.inflate(R.layout.crear_nivel, null);

            Button cancelar = (Button) v.findViewById(R.id.button3);
            Button jugar = (Button) v.findViewById(R.id.button4);

             p_verd = (EditText) v.findViewById(R.id.editText2);
             p_fals = (EditText) v.findViewById(R.id.editText3);
             minutos = (EditText) v.findViewById(R.id.editText4);

            cancelar.setTypeface(TF);   // aplicamos TYPEFACE al TEXTVIEW
            jugar.setTypeface(TF);   // aplicamos TYPEFACE al TEXTVIEW
            p_verd.setTypeface(TF);   // aplicamos TYPEFACE al TEXTVIEW
            p_fals.setTypeface(TF);   // aplicamos TYPEFACE al TEXTVIEW
            minutos.setTypeface(TF);   // aplicamos TYPEFACE al TEXTVIEW

            cancelar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    alertDialogsalir.cancel();
                }

            });
            jugar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    tiempo_nivel = Integer.parseInt(minutos.getText().toString());
                    verd_nivel = Integer.parseInt(p_verd.getText().toString());
                    fals_nivel = Integer.parseInt(p_fals.getText().toString());

                    if (tiempo_nivel > 0) {
                        Intent intent2 = new Intent(JuegoNivelesActivity.this, JuegoActivity.class);
                        intent2.putExtra("modo", modo_de_juego);//pasar parametro para saber si checkbox esta checado o no
                        intent2.putExtra("mano", mano_elegida);
                        intent2.putExtra("tiempo_nivel", tiempo_nivel);
                        intent2.putExtra("verd_nivel", verd_nivel-1);
                        intent2.putExtra("fals_nivel", fals_nivel-1);
                        intent2.putExtra("idioma", idioma);
                        startActivity(intent2);
                        finish();
                    }
                }

            });
            alertDialogsalir.setContentView(v);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
            alertDialogsalir.show();
        }
    }

    //Crear un ContextMenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Personalización de nivel");

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pers_niveles, menu);

    }

    @Override
    protected void onPause() {
      //  if (menu.reproductor != null && menu.reproductor.isPlaying()) {
     //       menu.posicioncancion = menu.reproductor.getCurrentPosition();
     //       menu.reproductor.pause();
     //   }
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
     //   if (menu.reproductor != null && menu.reproductor.isPlaying() == false) {
      //      menu.reproductor.seekTo(menu.posicioncancion+200);
      //      menu.reproductor.start();
     //   }
        if (mAdView != null) {
            mAdView.resume();
        }
        super.onResume();
    }




}
