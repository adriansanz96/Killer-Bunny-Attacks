package com.example.adrin.juegoproyecto;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


public class MainActivity extends AppCompatActivity  {

    //Declarar variables
    LinearLayout layout;
    private View viewToBeCaptured;

    //Control sonido
    int musica = 1;
    int efectos= 0;
    public static MediaPlayer reproductor;
    public static MediaPlayer sound;
    static int posicioncancion = 0;

    SharedPreferences pref;
    ImageButton cat, es;
    Button limpiartodo, volver;
    ImageView melodiaview,tonosview,idiomaView, infoview, resultview, melodia, tonos, info, resultados, idiomas, play, compartir, salir2;
    Button salir, cancelar;

    private BDResultados bdResultados;
    File sd, f;
    int item_pos;
    ListView listViewresultados;
    private ArrayList<String> list_of_rooms = new ArrayList<>();
    ArrayAdapter<String> adapter;
    String item;
    public static Activity ma;
    Typeface TF;
    PopupWindow popupWindow;
    int popup_abierto=0;
    String idioma, prefIdioma;
    TextView tv1;

    public static String uniqueID = null;
    public static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

    DatabaseReference root;


    //ID UNICO DE NUETRO MOVIL AL INSTALAR
    public synchronized static String id(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.commit();
            }
        }
        return uniqueID;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        root = FirebaseDatabase.getInstance().getReference().child("ranking");

        String deviceID = id(MainActivity.this);

        //Ventana completa
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String font_path = "font/gillsans.ttf";  //definimos un STRING con el valor PATH
        TF = Typeface.createFromAsset(getAssets(), font_path);

        ma = this;

        cargarlocale();




        //Crear animación mover
        // RelativeLayout menu;
        //  menu = (RelativeLayout) findViewById(R.id.layoutmenu);
        //  mover = AnimationUtils.loadAnimation(this, R.anim.mover);
        // menu.startAnimation(mover);
        // mover.reset();

        pref = getSharedPreferences("Sai", Context.MODE_PRIVATE);
        musica = pref.getInt("check", 1);


        melodia = (ImageView) findViewById(R.id.imageButton);
        melodiaview = (ImageView) findViewById(R.id.imageView10);
        tonos = (ImageView) findViewById(R.id.imageButton3);
        tonosview = (ImageView) findViewById(R.id.imageView11);
        idiomas = (ImageView) findViewById(R.id.imageButton8);
        idiomaView = (ImageView) findViewById(R.id.imageView17);
        info = (ImageView) findViewById(R.id.imageButton2);
        infoview = (ImageView) findViewById(R.id.imageView12);
        resultados = (ImageView) findViewById(R.id.imageButton4);
        resultview = (ImageView) findViewById(R.id.imageView13);

        play = (ImageView) findViewById(R.id.imageView3);
        compartir = (ImageView) findViewById(R.id.imageView18);
        salir2 = (ImageView) findViewById(R.id.imageView19);
        ImageView c1 = (ImageView) findViewById(R.id.imageView35);
        ImageView s1 = (ImageView) findViewById(R.id.imageView24);
        tv1 = (TextView) findViewById(R.id.textView18);
        ImageView zanahoria = (ImageView) findViewById(R.id.imageView22);
        ImageView bunny = (ImageView) findViewById(R.id.imageView21);

        String mensaje = tv1.getText().toString();

        //animaciones inicio
        Animation dentro;
        dentro = AnimationUtils.loadAnimation(getApplication(), R.anim.aumentar2);
        dentro.setDuration(600);
      //  melodia.startAnimation(dentro);
      //  melodiaview.startAnimation(dentro);
      //  tonos.startAnimation(dentro);
       // tonosview.startAnimation(dentro);
       // idiomas.startAnimation(dentro);
      //  idiomaView.startAnimation(dentro);
      //  info.startAnimation(dentro);
      //  infoview.startAnimation(dentro);
      //  resultados.startAnimation(dentro);
      //  resultview.startAnimation(dentro);
      //  play.startAnimation(dentro);
       // compartir.startAnimation(dentro);
      //  salir2.startAnimation(dentro);
     //   c1.startAnimation(dentro);
      //  s1.startAnimation(dentro);
      //  tv1.startAnimation(dentro);
        zanahoria.startAnimation(dentro);
        bunny.startAnimation(dentro);



        //menuprincipal
        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    sound.start();
                } catch (Exception e) {
                }
                Intent intent = new Intent(MainActivity.this, JuegoNivelesActivity.class);
                intent.putExtra("idioma", idioma);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        });
        compartir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{sound.start();}catch (Exception e){}
                capturaview();
                Uri uri= Uri.fromFile(f);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getText(R.string.texto_compartir_menu));
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_STREAM,uri);
                startActivity(Intent.createChooser(intent, "Compartir"));
            }
        });
        salir2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    sound.start();
                } catch (Exception e) {
                }
                finish();
            }
        });


        //EFECTOS DE SONIDO
        if (efectos == 0) {
            sound = MediaPlayer.create(MainActivity.this, R.raw.efectoboton);
        }
        if (efectos == 0) {
            tonosview.setBackgroundResource(R.drawable.ic_social_notifications_off);
        } else {
            tonosview.setBackgroundResource(R.drawable.ic_social_notifications);
        }



        //MUSICA DE FONDO
        if (musica == 0) {
            reproductor = MediaPlayer.create(MainActivity.this, R.raw.musicadefondo);
            reproductor.setLooping(true);
           // reproductor.start();
        }

        if (musica == 0) {
            melodiaview.setBackgroundResource(R.drawable.ic_av_volume_off);
        } else {
            melodiaview.setBackgroundResource(R.drawable.ic_av_volume_up);
        }

        //BOTON SILENCIAR MELODIA
        melodia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (musica == 1) {
                    try {
                        reproductor = MediaPlayer.create(MainActivity.this, R.raw.musicadefondo);
                        reproductor.setLooping(true);
                        //     reproductor.start();
                        musica = 0;
                        melodiaview.setBackgroundResource(R.drawable.ic_av_volume_off);
                    } catch (Exception e) {
                    }
                } else {
                    try {
                        reproductor.stop();
                        musica = 1;
                        melodiaview.setBackgroundResource(R.drawable.ic_av_volume_up);
                    } catch (Exception e) {
                    }
                }
                ;
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("check", musica);
                editor.commit();
            }
        });

        //BOTON SILENCIAR TONOS
        tonos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (efectos == 1) {
                    try {
                        sound = MediaPlayer.create(MainActivity.this, R.raw.efectoboton);
                        sound.start();
                        efectos = 0;
                        tonosview.setBackgroundResource(R.drawable.ic_social_notifications_off);
                    } catch (Exception e) {
                    }
                } else {
                    try {
                        sound.stop();
                        efectos = 1;
                        tonosview.setBackgroundResource(R.drawable.ic_social_notifications);
                    } catch (Exception e) {
                    }
                }
                ;
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("check", musica);
                editor.commit();
            }
        });

        //BOTON INSTRUCCIONES JUEGO
        info.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{sound.start();}catch (Exception e){}
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.instrucciones_titulo);
                //builder.setView(R.layout.activity_twitter);
                builder.setMessage(Html.fromHtml(getString(R.string.instrucciones)));
                builder.setNegativeButton("Cerrar", null);
                builder.show();
            }
        });

        //BOTON RESULTADOS
        resultados.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{sound.start();}catch (Exception e){}

                Intent intent = new Intent(MainActivity.this, Chat_Room_resultados.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                finish();
                }});






        //BOTON IDIOMA
        idiomas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(popup_abierto==0) {
                    View popupView;
                    LayoutInflater layout;

                    layout = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    popupView = layout.inflate(R.layout.popup_idioma, null);
                    popupWindow = new PopupWindow(popupView, RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);

                    try {
                        sound.start();
                    } catch (Exception e) {
                    }


                    popupWindow.showAsDropDown(idiomas, 0, 50);


                    cat = (ImageButton) popupView.findViewById(R.id.imageButton10);
                    es = (ImageButton) popupView.findViewById(R.id.imageButton9);

                    cat.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            String nuevolocale;
                            nuevolocale = "cat";
                            guardarlocale(nuevolocale);
                        }
                    });
                    es.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            String nuevolocale;
                            nuevolocale = "es";
                            guardarlocale(nuevolocale);
                        }
                    });
                    popup_abierto=1;
                }else{
                    popupWindow.dismiss();
                    popup_abierto=0;
                }
            }

        });
    }




    private void cargarlocale() {
        //Buscar si hay idioma guardado
        prefIdioma = "idioma";
        idioma = leeIdioma(prefIdioma, this);
        cambiarlocale(idioma);
    }

    private void cambiarlocale(String idioma) {
        //Si está vacío se queda el idioma por defecto
        if (idioma ==""){
            return;
        }
        else {
            Locale locale = new Locale(idioma);
            Locale.setDefault(locale);

            android.content.res.Configuration config = new android.content.res.Configuration();
            config.locale=locale;

            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


        }
    }
    private void guardarlocale(String locale){
        //Guardar idioma seleccionado
        String prefIdioma = "idioma";
        guardaIdioma(prefIdioma, locale, this);
        //reiniciar aplicacación para cambiar los datos
        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(i);
    }


    public static void guardaIdioma(String key, String value, Context context){
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();

    }
    public static String leeIdioma(String key, Context context){
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(key, "");
    }


    public void onDestroy() {
        super.onDestroy();
        try {
            if (reproductor.isPlaying()) {
              //  reproductor.stop();
              //  reproductor.release();
            }
        } catch (Exception e) {
        }
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }



    //METODO PARA SABER QUE ITEM ES EL SELECCIONADO
    public class listener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            item_pos = position;
            item = parent.getItemAtPosition(position).toString();
            parent = parent;
            return false;
        }
    }

    private void capturaview() {
        //CAPTURAR VIEW DEL RESULTADO
        viewToBeCaptured = this.findViewById(R.id.pantalla);
        viewToBeCaptured.setDrawingCacheEnabled(true);
        Bitmap b = viewToBeCaptured.getDrawingCache();

        // Carpeta dónde guardamos la captura
        // En este caso, la raíz de la SD Card
        sd = Environment.getExternalStorageDirectory().getAbsoluteFile();

        // El archivo que contendrá la captura
        f = new File(sd, "capturamenu.png");
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

    //Accion al pulsar atrás
    public void onBackPressed() {

        try{
            popupWindow.dismiss();
        }catch (Exception e){}

        final Dialog alertDialogsalir = new Dialog(MainActivity.this);
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
        dentro.setDuration(1000);
        imagen1.startAnimation(dentro);
        texto1.startAnimation(dentro);
        salir.startAnimation(dentro);
        cancelar.startAnimation(dentro);

        cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{sound.start();}catch (Exception e){}
                alertDialogsalir.cancel();
            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{sound.start();}catch (Exception e){}
                finish();
            }
        });

        //Aplicar layout y lanzar ventana dialog
        alertDialogsalir.setContentView(v2);
        alertDialogsalir.show();


    }


    @Override
    protected void onPause() {
       // if (reproductor != null && reproductor.isPlaying()) {
       //     posicioncancion = reproductor.getCurrentPosition();
       //     reproductor.pause();
       // }
        super.onPause();
    }

    @Override
    protected void onResume() {
      //  if (reproductor != null && reproductor.isPlaying() == false) {
      //      reproductor.seekTo(posicioncancion+200);
      //      reproductor.start();
      //  }
        super.onResume();
    }
}


