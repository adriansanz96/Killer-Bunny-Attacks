package com.example.adrin.juegoproyecto;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Adrian S on 02/02/2016.
 */
public class MenuAdapter extends ArrayAdapter<String>{

    ImageView imagen;
    TextView texto;

    private Context context;
    private ArrayList<String> datos;
    public MenuAdapter(Context context, ArrayList<String> datos) {
        super(context, 0, datos);
        this.context=context;
        this.datos=datos;
    }

    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_lista_perfil,null,true);

        }
        imagen=(ImageView) convertView.findViewById(R.id.imageViewperfil);
        texto=(TextView) convertView.findViewById(R.id.textView);

        //fuente
        String font_path = "font/gillsans.ttf";  //definimos un STRING con el valor PATH
        Typeface TF = Typeface.createFromAsset(getContext().getAssets(), font_path);
        texto.setTypeface(TF);   //finalmente aplicamos TYPEFACE al TEXTVIEW

        //animaciones inicio
        Animation dentro;
        Animation dentrorapido;
        dentro = AnimationUtils.loadAnimation(getContext(), R.anim.aumentar3);
        dentro.setDuration(700);
        dentrorapido= AnimationUtils.loadAnimation(getContext(), R.anim.aumentar3);
        dentrorapido.setDuration(1000);
        imagen.startAnimation(dentro);
        texto.startAnimation(dentrorapido);

        //APLICAR LAS DISTINTAS IMAGENES SEGUN LA POSICION
        switch(datos.get(position)) {
            case "Jugar": //imagen.setImageResource (R.drawable.ic_videogame_asset_black_24dp); //insertar icono
                texto.setText(R.string.jugar);
                // texto.setBackgroundColor(Color.parseColor("#FFDD86")); //cambiar el color del fondo pantalla
                //  imagen.setBackgroundColor(Color.parseColor("#C0A663")); //cambiar el color del fondo de imagen
                break;
            case "Salir": //imagen.setImageResource (R.drawable.ic_build_black_24dp);
                texto.setText(R.string.salir);
                //   texto.setBackgroundColor(Color.parseColor("#F7A58C"));
                //   imagen.setBackgroundColor(Color.parseColor("#B76147"));
                break;
            case "Compartir": //imagen.setImageResource (R.drawable.ic_share_black_24dp);
                texto.setText(R.string.compartir);
                //   texto.setBackgroundColor(Color.parseColor("#A7F589"));
                //   imagen.setBackgroundColor(Color.parseColor("#6DA458"));
                break;
            case "Rejugar": //imagen.setImageResource (R.drawable.ic_videogame_asset_black_24dp);
                texto.setText(R.string.rejugar);
                //  texto.setBackgroundColor(Color.parseColor("#8C49AE"));
                //  imagen.setBackgroundColor(Color.parseColor("#370650"));
                break;
            case "Menu": //imagen.setImageResource (R.drawable.ic_share_black_24dp);
                texto.setText(R.string.menu_principal);
                //   texto.setBackgroundColor(Color.parseColor("#FFE288"));
                //   imagen.setBackgroundColor(Color.parseColor("#F2B809"));
                break;
            case "Nivel1": //imagen.setImageResource (R.drawable.ic_videogame_asset_black_24dp); //insertar icono
                texto.setText(R.string.nivel1);
                //   texto.setBackgroundColor(Color.parseColor("#FFDD86")); //cambiar el color del fondo pantalla
                //   imagen.setBackgroundColor(Color.parseColor("#C0A663")); //cambiar el color del fondo de imagen
                break;
            case "Nivel2": //imagen.setImageResource (R.drawable.ic_videogame_asset_black_24dp); //insertar icono
                texto.setText(R.string.nivel2);
                //   texto.setBackgroundColor(Color.parseColor("#FFDD86")); //cambiar el color del fondo pantalla
                //   imagen.setBackgroundColor(Color.parseColor("#C0A663")); //cambiar el color del fondo de imagen
                break;
            case "Nivel3": //imagen.setImageResource(R.drawable.ic_videogame_asset_black_24dp); //insertar icono
                texto.setText(R.string.nivel3);
                //   texto.setBackgroundColor(Color.parseColor("#FFDD86")); //cambiar el color del fondo pantalla
                //   imagen.setBackgroundColor(Color.parseColor("#C0A663")); //cambiar el color del fondo de imagen
                break;
            case "Personalizar": //imagen.setImageResource(R.drawable.ic_videogame_asset_black_24dp); //insertar icono
                texto.setText(R.string.personalizar);
                //   texto.setBackgroundColor(Color.parseColor("#FFDD86")); //cambiar el color del fondo pantalla
                //   imagen.setBackgroundColor(Color.parseColor("#C0A663")); //cambiar el color del fondo de imagen
                break;
        }


        return convertView;
    }



}
