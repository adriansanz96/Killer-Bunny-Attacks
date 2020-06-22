package com.example.adrin.juegoproyecto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Adrian S on 19/04/2016.
 */
public class BDResultados {


    // Definiciones y constantes
    private static final String DATABASE_NAME = "BDRESULTADOFINAL2.db";
    private static final String DATABASE_TABLE = "resultados";
    private static final int DATABASE_VERSION = 1;

    private static final String NOMBRE = "nombre";
    private static final String TIEMPO = "tiempo";
    private static final String PREG_ACERT = "preg_acert";
    private static final String PREG_FALLADAS = "preg_falladas";
    private static final String TIEMPO_TRANS = "tiempo_trans";

    String nombre1;
    int tiempo_nivel1, preguntas_correctas1, preguntas_incorrectas1,tiempo_trans1;

    MainActivity menu;

    private static final String DATABASE_CREATE = "CREATE TABLE "+DATABASE_TABLE+" (_id integer primary key autoincrement, nombre text, tiempo integer, preg_acert integer, preg_falladas integer, tiempo_trans integer);";
    private static final String DATABASE_DROP = "DROP TABLE IF EXISTS "+DATABASE_TABLE+";";

    // Contexto de la aplicación que usa la base de datos
    private final Context context;
    // Clase SQLiteOpenHelper para crear/actualizar la base de datos
    private MyDbHelper dbHelper;
    // Instancia de la base de datos
    private SQLiteDatabase db;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


    public BDResultados (Context c){
        context = c;
        dbHelper = new MyDbHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open(){
        try{
            db = dbHelper.getWritableDatabase();
        }catch(SQLiteException e){
            db = dbHelper.getReadableDatabase();
        }
    }

    public void insertarResultado(String n, int t, int c, int i, int tt){
        //Creamos un nuevo registro de valores a insertar
        ContentValues newValues = new ContentValues();
        //Asignamos los valores de cada campo
        newValues.put(NOMBRE,n);
        newValues.put(TIEMPO, t);
        newValues.put(PREG_ACERT, c);
        newValues.put(PREG_FALLADAS,i);
        newValues.put(TIEMPO_TRANS,tt);

        db.insert(DATABASE_TABLE,null,newValues);
    }

    public ArrayList<String> recuperarTodo(){
        ArrayList<String> resultados = new ArrayList<String>();
                DatabaseReference nombre = ref.child("ranking/b03f06e5-f9b3-446f-9245-c0c013eedf61/nombre_usuario");


        nombre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nombre1 = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                ;
            }
        });


                resultados.add("Nombre: "+nombre1);


        return resultados;
    }



    public void eliminar(){
        db.execSQL("DELETE FROM "+DATABASE_TABLE+" WHERE 1");
       // db.close();
    }

    public void eliminarResultado(int posicion){
        db.execSQL("DELETE FROM "+DATABASE_TABLE+" WHERE _id="+posicion);

       // db.delete(DATABASE_TABLE, null, newValues);
    }

    private static class MyDbHelper extends SQLiteOpenHelper {

        public MyDbHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context,name,factory,version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DATABASE_DROP);
            onCreate(db);
        }

    }
}

