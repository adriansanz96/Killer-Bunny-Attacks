package com.example.adrin.juegoproyecto;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class JuegoFinalActivity extends Activity {

    //Definiciones
    private View viewToBeCaptured;
    private BDResultados bdResultados;
    private BaseDeDatos bdPreguntas;
    int contador_correcta, contador_incorrecta, tiempo_nivel, n_preg_verd, n_preg_fals;
    String tiempo_final, idioma;
    File sd,f;
    MainActivity menu;
    ImageView resultado,nuevapregunta;
    Typeface TF;
    AdView mAdView;
    AdRequest adRequest;
    EditText editTextResultado;
    String resultado_usuario;
    int monedas;
    private Button btn_send_msg,cancel;
    private EditText input_msg;
    private TextView chat_conversation;

    private String user_name,room_name,contador_correctastr;

    private DatabaseReference root ;
    private String temp_key;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juegofinal);






        //menu
        ImageView replay = (ImageView) findViewById(R.id.imageView9);
        ImageView compartir = (ImageView) findViewById(R.id.imageView25);
        ImageView salir2 = (ImageView) findViewById(R.id.imageView28);

        //menuprincipal
        replay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{menu.sound.start();}catch (Exception e){}
                Intent intent = new Intent(JuegoFinalActivity.this, JuegoNivelesActivity.class);
                intent.putExtra("idioma", idioma);
                startActivity(intent);
                finish();
            }
        });
        compartir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{menu.sound.start();}catch (Exception e){}
                capturaview();
                Uri uri= Uri.fromFile(f);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getText(R.string.texto_compartir_resultado));
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_STREAM,uri);
                startActivity(Intent.createChooser(intent, "Compartir"));
            }
        });
        salir2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    menu.sound.start();
                } catch (Exception e) {
                }

                //  Intent intent = new Intent(this, MainActivity.class);
                //  startActivity(intent);
                //  overridePendingTransition(R.anim.left_in,R.anim.left_out);
                finish();//*Como no había cerrado MainActivity no lo vuelvo a lanzar, si no se duplicará*
            }
        });




        //Ventana completa
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        nuevapregunta = (ImageView) findViewById(R.id.imageButton5);
        resultado = (ImageView) findViewById(R.id.imageButton4);
        ImageView nuevapreguntaiv = (ImageView) findViewById(R.id.imageView30);
        ImageView resultadoiv = (ImageView) findViewById(R.id.imageView31);

        //iniciar animación
        Animation dentro;
        dentro = AnimationUtils.loadAnimation(getApplication(), R.anim.aumentar3);
        dentro.setDuration(700);
        nuevapregunta.startAnimation(dentro);
        resultado.startAnimation(dentro);
        nuevapreguntaiv.startAnimation(dentro);
        resultadoiv.startAnimation(dentro);
        dentro.reset();


        String font_path = "font/gillsans.ttf";  //definimos un STRING con el valor PATH
        TF = Typeface.createFromAsset(getAssets(), font_path);
        //resultado.setTypeface(TF);   //finalmente aplicamos TYPEFACE al TEXTVIEW
        //nuevapregunta.setTypeface(TF);   //finalmente aplicamos TYPEFACE al TEXTVIEW


        //ad
        //mAdView = (AdView) findViewById(R.id.adView);
       // adRequest = new AdRequest.Builder()
       // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
             //   .addTestDevice("4F2D49C5F7799114ED2DA613E8504894")  // An example device ID
      //          .build();
       // mAdView.loadAd(adRequest);

        //recibir argumentos

        n_preg_verd=getIntent().getExtras().getInt("n_preg_verd");
        n_preg_fals=getIntent().getExtras().getInt("n_preg_fals");
        contador_correcta=getIntent().getExtras().getInt("contador_correcta");
        contador_incorrecta=getIntent().getExtras().getInt("contador_incorrecta");
        tiempo_nivel=getIntent().getExtras().getInt("tiempo_nivel");
        tiempo_final=getIntent().getExtras().getString("tiempo_final");
        idioma=getIntent().getExtras().getString("idioma");

        if(contador_correcta==n_preg_verd+1){
            resultado_usuario="ganador";
        }
        else{
            if(contador_incorrecta==n_preg_fals+1){
                resultado_usuario="perdedor";
            }
            else {
                resultado_usuario="perdedor";
            }
        }

            if (findViewById(R.id.framelayoutjuegofinal) != null) {
                if (savedInstanceState != null) {
                }

                    JuegoFinalFragment fragment = new JuegoFinalFragment();

                    //pasar argumentos
                    Bundle bundle = new Bundle();
                    bundle.putInt("n_preg_verd", n_preg_verd);
                    bundle.putInt("n_preg_fals", n_preg_fals);
                    bundle.putInt("contador_correcta", contador_correcta);
                    bundle.putInt("contador_incorrecta", contador_incorrecta);
                    bundle.putInt("tiempo_nivel",tiempo_nivel);
                    bundle.putString("tiempo_final", tiempo_final);

                    fragment.setArguments(bundle);

                    getFragmentManager().beginTransaction().replace(R.id.framelayoutjuegofinal, fragment).commit();
            }




        //BOTON GUARDAR RESULTADO

        resultado.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try{menu.sound.start();}catch (Exception e){}

                if(resultado_usuario=="ganador") {
                    calculo_monedas();

                    final Dialog alertDialogsalir = new Dialog(JuegoFinalActivity.this);
                    alertDialogsalir.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    alertDialogsalir.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View v3=inflater.inflate(R.layout.nombreresultado, null);

                    btn_send_msg = (Button)  v3.findViewById(R.id.button7);
                    cancel = (Button)  v3.findViewById(R.id.button8);
                    input_msg = (EditText)  v3.findViewById(R.id.editTextresultado);
                    //  chat_conversation = (TextView) findViewById(R.id.textView);

                    // user_name = getIntent().getExtras().get("user_name").toString();
                    room_name = "ranking";


                    root = FirebaseDatabase.getInstance().getReference().child(room_name);

                    btn_send_msg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try{menu.sound.start();}catch (Exception e){}
                            Map<String,Object> map = new HashMap<String, Object>();
                            temp_key = root.push().getKey();
                            root.updateChildren(map);

                            DatabaseReference message_root = root.child(temp_key);
                            Map<String,Object> map2 = new HashMap<String, Object>();
                            map2.put("name", input_msg.getText().toString());
                            String monedasstring;
                            if(monedas<10){
                                monedasstring= "0"+String.valueOf(monedas);
                            }
                            else{
                                monedasstring= String.valueOf(monedas);
                            }

                            map2.put("monedas", monedasstring);


                            message_root.updateChildren(map2);


                            //para no duplicar el valor obtenido
                            Intent intent = new Intent(JuegoFinalActivity.this, MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.left_in, R.anim.left_out);
                            finish();
                        }

                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try{menu.sound.start();}catch (Exception e){}
                            alertDialogsalir.cancel();
                        }
                    });
                    //Aplicar layout y lanzar ventana dialog
                    alertDialogsalir.setContentView(v3);
                    alertDialogsalir.show();
                }

                else{
                    Toast toast1 = Toast.makeText(getApplicationContext(),
                            R.string.has_perdido, Toast.LENGTH_SHORT);

                    toast1.show();
                }



            }
        });




        //BOTON CREAR NUEVA PREGUNTA
        nuevapregunta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try{menu.sound.start();}catch (Exception e){}

                final Dialog alertDialogsalir = new Dialog(JuegoFinalActivity.this);
                alertDialogsalir.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertDialogsalir.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View v3=inflater.inflate(R.layout.nueva_pregunta, null);

                TextView preg=(TextView) v3.findViewById(R.id.textView11);
                TextView corr=(TextView) v3.findViewById(R.id.textView12);
                TextView incorr=(TextView) v3.findViewById(R.id.textView13);



                final EditText ETpregunta=(EditText) v3.findViewById(R.id.editText);
                final EditText ETres_corr=(EditText) v3.findViewById(R.id.editText5);
                final EditText ETres_inc=(EditText) v3.findViewById(R.id.editText6);

                Button crear_preg=(Button) v3.findViewById(R.id.button5);
                Button cancelar_preg=(Button) v3.findViewById(R.id.button6);

                ETpregunta.setTypeface(TF);
                ETres_corr.setTypeface(TF);
                ETres_inc.setTypeface(TF);
                preg.setTypeface(TF);
                corr.setTypeface(TF);
                incorr.setTypeface(TF);

                crear_preg.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        try{menu.sound.start();}catch (Exception e){}
                        String pregunta = ETpregunta.getText().toString();
                        String res_correcta = ETres_corr.getText().toString();
                        String res_incorrecta = ETres_inc.getText().toString();

                        bdPreguntas = new BaseDeDatos(JuegoFinalActivity.this,idioma);
                        bdPreguntas.open();
                        bdPreguntas.insertarPregunta(pregunta, res_correcta, res_incorrecta);

                        alertDialogsalir.cancel();
                    }
                });
                cancelar_preg.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        try{menu.sound.start();}catch (Exception e){}
                        alertDialogsalir.cancel();
                    }
                });
                //Aplicar layout y lanzar ventana dialog
                alertDialogsalir.setContentView(v3);
                alertDialogsalir.show();
            }
        });

        }

    public void calculo_monedas(){
        int tiempo_tran = tiempo_nivel - Integer.parseInt(tiempo_final)+1;
        monedas=contador_correcta*10;
        monedas=monedas-contador_incorrecta*10;
        monedas=monedas+tiempo_nivel;
        monedas=monedas-tiempo_tran;
    }

    private void capturaview() {
        //CAPTURAR VIEW DEL RESULTADO
        viewToBeCaptured = this.findViewById(R.id.framelayoutjuegofinal);
        viewToBeCaptured.setDrawingCacheEnabled(true);
        Bitmap b = viewToBeCaptured.getDrawingCache();

        // Carpeta dónde guardamos la captura
        // En este caso, la raíz de la SD Card
        sd = Environment.getExternalStorageDirectory().getAbsoluteFile();

        // El archivo que contendrá la captura
        f = new File(sd, "capturaresultados.png");

        try {
            if (sd.canWrite()) {
                f.createNewFile();
                OutputStream os = new FileOutputStream(f);
                b.compress(Bitmap.CompressFormat.PNG, 90, os);
                os.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewToBeCaptured.setDrawingCacheEnabled(false);
        //FIN CAPTURA
    }

    @Override
    protected void onPause() {
       // if (menu.reproductor != null && menu.reproductor.isPlaying()) {
      //      menu.posicioncancion = menu.reproductor.getCurrentPosition();
      //      menu.reproductor.pause();
      //  }
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
      // if (menu.reproductor != null && menu.reproductor.isPlaying() == false) {
      //      menu.reproductor.seekTo(menu.posicioncancion+200);
      //      menu.reproductor.start();
      //  }
        if (mAdView != null) {
            mAdView.resume();
        }
        super.onResume();
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
}






