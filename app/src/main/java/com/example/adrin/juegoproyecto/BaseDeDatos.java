package com.example.adrin.juegoproyecto;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Adrián on 02/02/2016.
 */
public class BaseDeDatos {


    private static Context myContext;
    public String idioma_app="es";
    // Clase SQLiteOpenHelper para crear/actualizar la base de datos
    private MyDbHelper dbHelper;
    // Instancia de la base de datos
    private SQLiteDatabase db;
    //private String DB_PATH = "/data/data/com.example.adrin.juegoproyecto/databases/";
    private final String DB_NAME;
    private final String DATABASE_TABLE;
    private final String DATABASE_CREATE;
    private final String DATABASE_DROP;
    private final int VERSION_BASEDATOS;

    //VALORES PREGUNTAS
    private static final String PREGUNTA = "pregunta";
    private static final String RES_CORRECTA = "res_correcta";
    private static final String RES_INCORRECTA = "res_incorrecta";

    //NUEVA PREGUNTA
    public void insertarPregunta(String p, String rc, String ri){
        //Creamos un nuevo registro de valores a insertar
        ContentValues newValues = new ContentValues();
        //Asignamos los valores de cada campo
        newValues.put(PREGUNTA,p);
        newValues.put(RES_CORRECTA,rc);
        newValues.put(RES_INCORRECTA, ri);
        db.insert(DATABASE_TABLE, null,newValues);

    }

    // CONSTRUCTOR de la clase
    public BaseDeDatos (Context c,String idioma){

        if(idioma.equals("")){
            idioma="es";
        }
        this.idioma_app=idioma;

        myContext = c;

        DB_NAME = "BBDDPREGUNTAS"+idioma_app+".db";
        DATABASE_TABLE = "BDPreguntas_"+idioma_app;
        DATABASE_CREATE = "CREATE TABLE "+DATABASE_TABLE+" (_id integer primary key autoincrement, pregunta text, res_correcta text, res_incorrecta text);";
        DATABASE_DROP="DROP TABLE IF EXISTS"+DATABASE_TABLE+";";
        VERSION_BASEDATOS = 1;

        dbHelper = new MyDbHelper(myContext, DB_NAME, null, 1, idioma_app);


    }

    public void open(){

        try {
            db = dbHelper.getReadableDatabase();
        }catch (Exception e){
            dbHelper = new MyDbHelper(myContext, DB_NAME, null, 1, idioma_app);
            open();
        }

    }

    public class MyDbHelper extends SQLiteOpenHelper {

        String idiomabbdd;
        public MyDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, String idioma) {
            super(context, name, factory, version);
            if(idioma==""){
                idioma="es";
            }
        this.idiomabbdd=idioma;
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
            InputStream is=null;
            try {
                Log.d("PRUEBA", "este es el idioma: " + idiomabbdd);
                if (idioma_app.equals("es")) {
                    is = myContext.getAssets().open("BaseDatosPreguntas.sql");
                }
                if (idioma_app.equals("cat")) {
                    is = myContext.getAssets().open("BaseDatosPreguntasCAT.sql");
                }

            }catch (Exception e){}

            try {
                if (is != null) {
                    db.beginTransaction();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line = reader.readLine();
                    while (!TextUtils.isEmpty(line)) {
                        db.execSQL(line);
                        line = reader.readLine();
                    }
                    db.setTransactionSuccessful();
                }
            } catch (Exception ex) {
            }
            finally {
                db.endTransaction();
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          //  db.execSQL(DATABASE_DROP);
          //  onCreate(db);
        }
    }


    //RECUPERAR TODOS LOS REGISTROS
    public ArrayList<Preguntas> recuperarPreguntas() {
        ArrayList<Preguntas> lista_preguntas = new ArrayList<Preguntas>();
        String[] valores_recuperar = {"_id", "pregunta", "res_correcta", "res_incorrecta"};
        Cursor c=null;
        try {
            c = db.query(DATABASE_TABLE, valores_recuperar, null, null, null, null, null);
        }catch (Exception e){
            open();
        }
        if (c != null && c.moveToFirst()) {
            do {
                Preguntas preguntas = new Preguntas(c.getInt(0), c.getString(1),
                        c.getString(2), c.getString(3));
                lista_preguntas.add(preguntas);
            } while (c.moveToNext());
        }
        db.close();
        try {
            c.close();
        }catch (Exception e){}
        return lista_preguntas;
    }

}

