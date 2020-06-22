package com.example.adrin.juegoproyecto;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by filipp on 6/28/2016.
 */
public class Chat_Room_resultados extends AppCompatActivity {

    private Button btn_send_msg;
    ArrayAdapter<String> adapter;
    private ArrayList<String> list_of_rooms = new ArrayList<>();
    private TextView chat_conversation;
    ListView listViewresultados;
    ImageView volver;
    private String user_name,room_name,contador_correctaS;

    int contador_correcta;
    private DatabaseReference root ;
    private String temp_key;
    MainActivity menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room_resultados);

        //Ventana completa
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        chat_conversation = (TextView) findViewById(R.id.textView22);
        listViewresultados = (ListView) findViewById(R.id.listviewres);
        volver = (ImageView) findViewById(R.id.imageViewvolver2);

        adapter = new ArrayAdapter<String>(Chat_Room_resultados.this, android.R.layout.simple_list_item_1, list_of_rooms);
        listViewresultados.setAdapter(adapter);
        //adapter.notifyDataSetChanged();


       // registerForContextMenu(listViewresultados);

        // user_name = getIntent().getExtras().get("user_name").toString();

        //contador_correcta = Integer.parseInt(contador_correctaS);

        chat_conversation.setText("\uD83C\uDFC6"+" RANKING "+"\uD83C\uDFC6");

        root = FirebaseDatabase.getInstance().getReference().child("ranking");



        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                append_chat_conversation(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private String contador1,chat_user_name;

    int contadorint;
    private void append_chat_conversation(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()){

            contador1 = (String) ((DataSnapshot)i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot)i.next()).getValue();

           // contadorint = Integer.parseInt(contador1);

           // Collections.sort(list_of_rooms);
            list_of_rooms.add("\uD83C\uDFC6" + contador1 + "  \uD83D\uDC30" + chat_user_name + " \n");

            Collections.sort(list_of_rooms);
            Collections.reverse(list_of_rooms);
            adapter.notifyDataSetChanged();
        }

//BOTON volver
        volver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{menu.sound.start();}catch (Exception e){}
                Intent intent = new Intent(Chat_Room_resultados.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                finish();
            }
        });
    }


}
