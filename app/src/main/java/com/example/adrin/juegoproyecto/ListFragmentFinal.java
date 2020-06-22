package com.example.adrin.juegoproyecto;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragmentFinal.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ListFragmentFinal extends Fragment {

    ListFragmentListener mCallback;
    // Container Activity must implement this interface
    public interface ListFragmentListener {
        public void onListSelected(int position, String item);


    }

    public ListFragmentFinal() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }


    @Override

    //Comprobar que implementa el Listener para que los Callbacks funcionen
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (ListFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

    }

    public void onActivityCreated (Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
        }


        //CONVERTIR ARRAY DE STRING A ARRAYLIST
        String[] menuprincipal = new String[]{"Rejugar", "Compartir", "Menu"};
        ArrayList<String> lista = new ArrayList<String>(Arrays.asList(menuprincipal));


        //Agregar el ArrayList al Adapter
        MenuAdapter adapter = new MenuAdapter(getActivity(), lista);


        //Agregar el Adapter al ListView
        final ListView listview = (ListView) getActivity().findViewById(R.id.listview);
        listview.setAdapter(adapter);
       // registerForContextMenu(listview);
        //INTERACTUANDO
        listview.setOnItemClickListener(new nuestroListener());


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null; //Cerrar para no haya errores
    }

    //INNER CLASS
    private class nuestroListener implements AdapterView.OnItemClickListener{
        public void onItemClick (AdapterView<?> parent, View view, int position, long id){
            //String de la posicion clickada
            String item = (String) parent.getItemAtPosition(position);
            //Toast.makeText(MainActivity.this,item,Toast.LENGTH_LONG).show();
            //Paso de informacion
            mCallback.onListSelected(position,item);
        }
    }


/**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
