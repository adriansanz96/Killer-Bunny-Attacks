package com.example.adrin.juegoproyecto;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class JuegoFragmentTouch extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Definiciones
    int tiempo, n_preg_verd, n_preg_fals;

    TextView respuesta1,respuesta2,textpregunta, preg_restantes_cor, preg_restantes_inc;
    int contador_correcta=0;
    int contador_incorrecta=0;
    int preg=0;

    ImageButton boton1,boton2;
    ArrayList<Preguntas> lista_preguntas;
    int pr_aleatoria;
    ArrayList <Integer> al;
    SeekBar barraProgreso, barraProgreso2;
    MainActivity menu;


    //tiempo
    private MiCountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    private TextView text;
    private long startTime=0;//tiempo del cronometro(en milisegundos)
    private final long interval = 1000;

    //tiempo zanahoria
    private int saltos=30;

    ImageView imagen, salida_izq, salida_der, cont_preg, imagen_puntos, imagen2;
    String idioma;




    public JuegoFragmentTouch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JuegoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JuegoFragmentTouch newInstance(String param1, String param2) {
        JuegoFragmentTouch fragment = new JuegoFragmentTouch();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //recibir argumentos
        Bundle args = getArguments();
        tiempo = args.getInt("tiempo_nivel");
        n_preg_verd = args.getInt("verd_nivel");
        n_preg_fals = args.getInt("fals_nivel");
        idioma = args.getString("idioma");

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_juego_touch, container, false);

        text = (TextView)v.findViewById(R.id.timer);

        barraProgreso = (SeekBar) v.findViewById(R.id.progressBar2);
        barraProgreso2 = (SeekBar) v.findViewById(R.id.progressBar1);


        barraProgreso.setMax(tiempo);
        barraProgreso.setEnabled(false);

        barraProgreso2.setMax(24);
        barraProgreso2.setEnabled(false);

        //Iniciar cuenta atras
        startTime = tiempo*1000;

        countDownTimer = new MiCountDownTimer(startTime, interval);

        text.setText(text.getText() + String.valueOf(startTime));

        if (!timerHasStarted){
            countDownTimer.start();
            timerHasStarted = true;
        }else {
            countDownTimer.cancel();
            timerHasStarted = false;
        }

        // Esto es para inicializar la BD
        BaseDeDatos db2 = new BaseDeDatos(getActivity(),idioma);
        db2.open();


        //Definir
        respuesta1 = (TextView) v.findViewById(R.id.textView3);
        respuesta2 = (TextView) v.findViewById(R.id.textView4);
        textpregunta = (TextView) v.findViewById(R.id.textView2);

        //APLICAR FUENTE PERSONALIZADA
        String font_path = "font/gillsans.ttf";  //definimos un STRING con el valor PATH
        Typeface TF = Typeface.createFromAsset(getActivity().getAssets(), font_path);
        respuesta1.setTypeface(TF);   //finalmente aplicamos TYPEFACE al TEXTVIEW
        respuesta2.setTypeface(TF);   //finalmente aplicamos TYPEFACE al TEXTVIEW
        textpregunta.setTypeface(TF);   //finalmente aplicamos TYPEFACE al TEXTVIEW

        boton1 = (ImageButton) v.findViewById(R.id.button);
        boton2 = (ImageButton) v.findViewById(R.id.button2);

        cont_preg = (ImageView) v.findViewById(R.id.imageView5);

        preg_restantes_cor = (TextView) v.findViewById(R.id.textView16);
        preg_restantes_inc = (TextView) v.findViewById(R.id.textView17);

        preg_restantes_cor.setText(String.valueOf(n_preg_verd+1));
        preg_restantes_inc.setText(String.valueOf(n_preg_fals+1));

        lista_preguntas = db2.recuperarPreguntas();

        AleatorioSinRepeticion();

        accionrespuesta();//lanza la clase donde estan definidas las acciones
        return v;
    }

    public void AleatorioSinRepeticion() {
        int pos;
        int npreguntas = lista_preguntas.size();
        al = new ArrayList<>();
        for (int i = 0; i < npreguntas; i++) {
            pos = (int) Math.floor(Math.random() * npreguntas) + 0;
            while (al.contains(pos)) {
                pos = (int) Math.floor(Math.random() * npreguntas) + 0;
            }
            al.add(pos);
        }
        Log.d("NUM_ALEATORIOS", al.toString());
    }

    public void fondoaleatorio() {
        int pos2;
        pos2 = (int) Math.floor(Math.random() * 5) + 1;

        RelativeLayout layout = (RelativeLayout) getActivity().findViewById(R.id.absolutelayout_touch);

        if (pos2 == 1) {
            layout.setBackgroundResource(R.drawable.fondo2);
        }
        if (pos2 == 2) {
            layout.setBackgroundResource(R.drawable.fondo3);
        }
        if (pos2 == 3) {
            layout.setBackgroundResource(R.drawable.fondo4);
        }
        if (pos2 == 4) {
            layout.setBackgroundResource(R.drawable.fondo5);
        }
        if (pos2 == 5) {
            layout.setBackgroundResource(R.drawable.fondo6);
        }
        if (pos2 == 6) {
            layout.setBackgroundResource(R.drawable.fondo7);
        }
    }

    public void accionrespuesta() {//random para elegir la pregunta y sus respectivas respuestas
       // Log.d("TOTAL", Integer.toString(lista_preguntas.size()));



        int[] ids = new int[lista_preguntas.size()];
        String[] preguntas = new String[lista_preguntas.size()];
        String[] verd = new String[lista_preguntas.size()];
        String[] fals = new String[lista_preguntas.size()];

        //Coje todos los valores de los registros
        for (int i = 0; i < lista_preguntas.size(); i++) {
            ids[i] = lista_preguntas.get(i).getID();
            preguntas[i] = lista_preguntas.get(i).getPregunta();
            verd[i] = lista_preguntas.get(i).getRes_verdadera();
            fals[i] = lista_preguntas.get(i).getRes_incorrecta();
        }
        try {
            int aleternar_respuesta = (int) (Math.random() * 2) + 1; //random para alternar la posicion de las respuestas entre los dos layouts

            pr_aleatoria = al.get(preg);

            textpregunta.setText(preguntas[pr_aleatoria]);//pregunta aleatoria

            if (contador_correcta <= n_preg_verd) { //cantidad de preguntas que realizará si acierta
            if (contador_incorrecta <= n_preg_fals) { //cantidad de preguntas que realizará si falla
                    if (aleternar_respuesta == 1) { //SI SALE UN 1
                        respuesta1.setText(verd[pr_aleatoria]);
                        boton1.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                try{menu.sound.start();}catch (Exception e){}
                                respuesta1.getContext();
                                ++preg;
                                ventana_emergente(1);
                                fondoaleatorio();

                                //saltos conejo
                                saltos=saltos-1;
                                barraProgreso2.incrementProgressBy(1);

                                //   animacion_preg();
                                preg_restantes_cor.setText(String.valueOf(n_preg_verd - contador_correcta ));
                                ++contador_correcta;
                                accionrespuesta();
                            }
                        });


                    } else {
                        respuesta2.setText(verd[pr_aleatoria]);
                        boton2.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                try{menu.sound.start();}catch (Exception e){}
                                respuesta2.getContext();
                                ++preg;
                                ventana_emergente(1);
                                fondoaleatorio();
                                saltos=saltos-1;
                                barraProgreso2.incrementProgressBy(1);
                                //  animacion_preg();


                                preg_restantes_cor.setText(String.valueOf(n_preg_verd - contador_correcta));
                                ++contador_correcta;
                                accionrespuesta();
                            }
                        });
                    }


                    if (aleternar_respuesta == 2) { //SI SALE UN 2
                        respuesta1.setText(fals[pr_aleatoria]);
                        boton1.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                try{menu.sound.start();}catch (Exception e){}

                                ++preg;
                                ventana_emergente(2);
                                fondoaleatorio();
                                saltos=saltos+1;
                                barraProgreso2.incrementProgressBy(-1);
                                //   animacion_preg();
                                preg_restantes_inc.setText(String.valueOf(n_preg_fals - contador_incorrecta));
                                ++contador_incorrecta;
                                accionrespuesta();

                            }
                        });


                    } else {
                        respuesta2.setText(fals[pr_aleatoria]);
                        boton2.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                try{menu.sound.start();}catch (Exception e){}


                                ++preg;
                                ventana_emergente(2);
                                fondoaleatorio();
                                saltos=saltos+1;
                                barraProgreso2.incrementProgressBy(-1);

                                //  animacion_preg();
                                preg_restantes_inc.setText(String.valueOf(n_preg_fals - contador_incorrecta));
                                ++contador_incorrecta;
                                accionrespuesta();
                            }
                        });

                    }

                } else { //cantidad acertadas superada
                    lanzarFinalJuego();
                }
            } else { //cantidad incorrectas superada
                lanzarFinalJuego();
            }
        }
        catch (Exception e){}
   }



    private void animacion_preg(){
        //animacion caja pregunta
        final Animation aumentar = AnimationUtils.loadAnimation(getActivity(), R.anim.aumentar2);
        aumentar.setDuration(100);
        final Animation siguiente_fuera = AnimationUtils.loadAnimation(getActivity(), R.anim.right_out);
        siguiente_fuera.setDuration(300);

        AnimationSet animaciones = new AnimationSet(true);
        animaciones.addAnimation(aumentar);
        animaciones.addAnimation(siguiente_fuera);

        cont_preg.startAnimation(animaciones);
        textpregunta.startAnimation(animaciones);
        animaciones.start();
    }



    //Clase CountDownTimer modificada
    public class MiCountDownTimer extends CountDownTimer {

        public MiCountDownTimer(long starTime, long interval) {
            super(starTime, interval);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onFinish() {

                lanzarFinalJuego();


        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub

            barraProgreso.incrementProgressBy(1);


            text.setText("" + TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));//Para que convierta los milisegundos en segundos

            int cuenta_atras=Integer.parseInt((String) text.getText());
            try {
                //Crear animación zoom cuanta atrás
                Animation aumentar;
                aumentar = AnimationUtils.loadAnimation(getActivity(), R.anim.aumentar2);

                //SI EL CONEJO ALCANZA A LA ZANAHORIA
                if(cuenta_atras <= saltos){
                    lanzarFinalJuego();
                }

                if (cuenta_atras <= 10 && cuenta_atras > 3) {
                    text.setTextColor(Color.YELLOW);
                    text.startAnimation(aumentar);
                    aumentar.reset();
                }

                if (cuenta_atras <= 3) {
                    text.setTextColor(Color.RED);
                    text.startAnimation(aumentar);
                    aumentar.reset();
                }
            }
            catch (Exception e){}
        }
    }

    public void lanzarFinalJuego(){
        try {
            Intent intent = new Intent(getActivity(), JuegoFinalActivity.class);
            intent.putExtra("n_preg_verd", n_preg_verd);
            intent.putExtra("n_preg_fals", n_preg_fals);
            intent.putExtra("contador_correcta", contador_correcta);
            intent.putExtra("contador_incorrecta", contador_incorrecta);
            intent.putExtra("tiempo_nivel", tiempo);
            intent.putExtra("tiempo_final",text.getText());
            intent.putExtra("idioma",idioma);
            startActivity(intent);
            getActivity().finish(); //cerrar anterior para no poder volver atras
            getFragmentManager().beginTransaction().remove(JuegoFragmentTouch.this).commit();//cerrar fragment
        }
        catch (Exception e){

        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public  void ventana_emergente(int eleccion){
    //    RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.ventana_emergente, (RelativeLayout) getActivity().findViewById(R.id.ventana_emergente_id));
    //    Toast t = new Toast(getActivity().getApplicationContext());
        imagen = (ImageView) getActivity().findViewById(R.id.imageVieweleccion1);
        imagen2 = (ImageView) getActivity().findViewById(R.id.imageVieweleccion2);
   //     imagen_puntos = (ImageView) getActivity().findViewById(R.id.imageView23);

        //Crear venación bien/mal
        Animation aumentar, transparentar, aumentar2;
        aumentar = AnimationUtils.loadAnimation(getActivity(), R.anim.aumentar);
        transparentar = AnimationUtils.loadAnimation(getActivity(), R.anim.transparentar);
        aumentar2 = AnimationUtils.loadAnimation(getActivity(), R.anim.aumentar2);


        aumentar.setFillAfter(true);
        aumentar.start();

        AnimationSet animaciones = new AnimationSet(true);
        animaciones.addAnimation(aumentar);
        animaciones.addAnimation(transparentar);
        animaciones.setDuration(500);


            if (eleccion == 1) {
                imagen.setBackgroundResource(R.drawable.ic_bien);
             //   imagen_puntos.setImageResource(R.drawable.bien);
            //    imagen_puntos.startAnimation(aumentar2);
             //   imagen_puntos.startAnimation(animaciones);
                imagen.startAnimation(animaciones);

            } else {
                imagen2.setBackgroundResource(R.drawable.ic_mal);
                imagen2.startAnimation(animaciones);
            }

        animaciones.setFillAfter(true);
        animaciones.start();

    }
}