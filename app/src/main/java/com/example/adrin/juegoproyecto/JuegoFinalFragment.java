package com.example.adrin.juegoproyecto;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class JuegoFinalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Definiciones
    int contador_correcta, contador_incorrecta, tiempo_nivel, tiempo_tran, n_preg_verd, n_preg_fals;
    String tiempo_final;
    ImageView enfadado, contento,corona;
    TextView text_res,puntos;
    int monedas;
    int pos=1;
    AnimationSet animaciones;
    Thread timer;
    MainActivity menu;


    public JuegoFinalFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static JuegoFinalFragment newInstance(String param1, String param2) {
        JuegoFinalFragment fragment = new JuegoFinalFragment();
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
        n_preg_verd = args.getInt("n_preg_verd");
        n_preg_fals = args.getInt("n_preg_fals");
        contador_correcta = args.getInt("contador_correcta");
        contador_incorrecta = args.getInt("contador_incorrecta");
        tiempo_final = args.getString("tiempo_final");
        tiempo_nivel = args.getInt("tiempo_nivel");

     //   TimeUnit.MILLISECONDS.toSeconds(tiempo_total);//convertir milisegundo en segundos



        //new Thread(this).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_final_juego, container, false);

        TextView tv = (TextView) v.findViewById(R.id.textView5);
        enfadado = (ImageView) v.findViewById(R.id.imageView);
        contento = (ImageView) v.findViewById(R.id.imageView4);
        text_res = (TextView) v.findViewById(R.id.texto_result);
        corona = (ImageView) v.findViewById(R.id.imageView6);
        puntos = (TextView) v.findViewById(R.id.textView6);

        enfadado.bringToFront();
        contento.bringToFront();

        //APLICAR FUENTE PERSONALIZADA
        String font_path = "font/gillsans.ttf";  //definimos un STRING con el valor PATH
        Typeface TF = Typeface.createFromAsset(getActivity().getAssets(), font_path);
        tv.setTypeface(TF);   //finalmente aplicamos TYPEFACE al TEXTVIEW



        if(contador_correcta==n_preg_verd+1){
            tv.setText(R.string.ganador);
          //  tv.setTextColor(Color.parseColor("#DDDD10"));
            enfadado.setImageResource(R.drawable.bunny_reed);
            //RelativeLayout relativelayourfinal = (RelativeLayout) getActivity().findViewById(R.id.relativelayourfinal);
            corona.setBackground(getResources().getDrawable(R.drawable.corona));
        }
        else{
            if(contador_incorrecta==n_preg_fals+1){
                tv.setText(R.string.perdido);
              //  tv.setTextColor(Color.parseColor("#A92C0D"));
                contento.setImageResource(R.drawable.bunny_3_reed);
                //   RelativeLayout relativelayourfinal = (RelativeLayout) getActivity().findViewById(R.id.relativelayourfinal);
               // corona.setBackground(getResources().getDrawable(R.drawable.fondo));
            }
            else {
                tv.setText(R.string.perdido);
             //   tv.setTextColor(Color.parseColor("#A92C0D"));
                contento.setImageResource(R.drawable.bunny_3_reed);

                //  RelativeLayout relativelayourfinal = (RelativeLayout) getActivity().findViewById(R.id.relativelayourfinal);
              //  corona.setBackground(getResources().getDrawable(R.drawable.fondo));
            }
        }

        tiempo_tran = tiempo_nivel - Integer.parseInt(tiempo_final)+1;
        //Resultador finales



        final RelativeLayout result = (RelativeLayout) v.findViewById(R.id.relativelayoutresult);

        //iniciar animación
        Animation dentro;
        dentro = AnimationUtils.loadAnimation(getActivity(), R.anim.aumentar3);
        dentro.setDuration(700);
        tv.startAnimation(dentro);
        result.startAnimation(dentro);
        dentro.reset();

        startingUp();

        return v;

    }

    private void startingUp() {
         timer = new Thread() { //new thread
            public void run() {
                Boolean b = true;
                try {
                    do {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                if (pos < 5) {
                                    ventana_emergente(pos);
                                    ++pos;
                                }
                                else{

                                }
                            }
                        });
                    }
                    while (b == true);
                }
                catch (Exception e){

                }
            };
        };
        timer.start();
    }


    public void animacion_corona(){
        //Crear venación bien/mal
        try{menu.sound.start();}catch (Exception e){}
        Animation aumentar;
        aumentar = AnimationUtils.loadAnimation(getActivity(), R.anim.aumentar2);
        aumentar.setDuration(100);
        corona.startAnimation(aumentar);
       // puntos.startAnimation(aumentar);
        aumentar.start();
    }

    public  void ventana_emergente(int eleccion){
        //    RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.ventana_emergente, (RelativeLayout) getActivity().findViewById(R.id.ventana_emergente_id));
        //    Toast t = new Toast(getActivity().getApplicationContext());



        if (eleccion == 1) {
            text_res.setText("Puntos recibidos: "+contador_correcta+"  |  "+"-"+contador_correcta*10);
            monedas=contador_correcta*10;
            animacion_corona();
            String value = String.valueOf(monedas);
            puntos.setText(value);
        }

        if (eleccion == 2) {
            try{menu.sound.start();}catch (Exception e){}
            text_res.setText("Preguntas incorrectas: "+contador_incorrecta+"  |  "+"-"+contador_incorrecta*10);
            monedas=monedas-contador_incorrecta*10;
            animacion_corona();
            String value = String.valueOf(monedas);
            puntos.setText(value);

        }
        if (eleccion == 3) {
            try{menu.sound.start();}catch (Exception e){}
            text_res.setText("Tiempo total: "+tiempo_tran+"  |  "+"+"+tiempo_nivel);
            monedas=monedas+tiempo_nivel;
            animacion_corona();
            String value = String.valueOf(monedas);
            puntos.setText(value);
        }
        if (eleccion == 4) {
            try{menu.sound.start();}catch (Exception e){}
            text_res.setText("Tiempo transcurrido: "+tiempo_tran+"  |  "+"-"+tiempo_tran);
            monedas=monedas-tiempo_tran;
            animacion_corona();
            String value = String.valueOf(monedas);
            puntos.setText(value);

        }

        //Crear venación bien/mal
        Animation aumentar, transparentar;
        aumentar = AnimationUtils.loadAnimation(getActivity(), R.anim.aumentar);
        aumentar.setDuration(1500);
        transparentar = AnimationUtils.loadAnimation(getActivity(), R.anim.transparentar);
        transparentar.setDuration(1000);
        animaciones = new AnimationSet(true);
        animaciones.addAnimation(aumentar);
        animaciones.addAnimation(transparentar);

        text_res.startAnimation(animaciones);
        animaciones.setFillAfter(true);

        animaciones.start();



    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



}
