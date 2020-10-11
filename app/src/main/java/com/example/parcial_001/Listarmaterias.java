package com.example.parcial_001;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Listarmaterias#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Listarmaterias extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Listarmaterias() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Listarmaterias.
     */
    // TODO: Rename and change types and number of parameters
    public static Listarmaterias newInstance(String param1, String param2) {
        Listarmaterias fragment = new Listarmaterias();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    ListView lista;
    ArrayList<String> listaInformacion;
    ArrayList<Asignatura> asignaturas;
    databaseHelper con;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listarmaterias, container, false);

        lista = view.findViewById(R.id.lista_asignaturas);
        con = new databaseHelper(this.getContext(),"parcial",null,1);

        consultarLista();

        ArrayAdapter adaptador = new ArrayAdapter(this.getContext(),R.layout.asignaturalayout,listaInformacion);
        lista.setAdapter(adaptador);

        return view;
    }


    public void  consultarLista(){
        SQLiteDatabase db = con.getReadableDatabase();

        Asignatura asignatura = null;

        asignaturas = new ArrayList<Asignatura>();

        Cursor cursor = db.rawQuery("SELECT * FROM asignaturas",null);

        while (cursor.moveToNext()){
            asignatura = new Asignatura();
            asignatura.setNombre(cursor.getString(1));
            asignaturas.add(asignatura);
        }
        obtenerLista();
    }

    public  void obtenerLista(){
        listaInformacion = new ArrayList<String>();

        for (int i = 0; i<asignaturas.size();i++){
            listaInformacion.add("   Nombre: "+asignaturas.get(i).Nombre);
        }
    }
}