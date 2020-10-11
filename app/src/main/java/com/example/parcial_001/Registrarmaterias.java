package com.example.parcial_001;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Registrarmaterias#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Registrarmaterias extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Registrarmaterias() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Registrarmaterias.
     */
    // TODO: Rename and change types and number of parameters
    public static Registrarmaterias newInstance(String param1, String param2) {
        Registrarmaterias fragment = new Registrarmaterias();
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
    EditText nombre;
    Button  guardar;
    databaseHelper con;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_registrarmaterias, container, false);

        nombre = view.findViewById(R.id.nombre_asignatura);
        guardar = view.findViewById(R.id.guardar_materia);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar_materia();
            }
        });
        return view;
    }

    public  void  guardar_materia(){
        con = new databaseHelper(this.getContext(),"parcial",null,1);
        SQLiteDatabase db = con.getWritableDatabase();
        if (nombre.getText().toString().equals("")){
            Toast.makeText(this.getContext(),
                    "llene elcampo nombre", Toast.LENGTH_SHORT).show();
        }else{
            ContentValues values = new ContentValues();
            values.put("NOMBRE",nombre.getText().toString());
            Long id_resultado = db.insert("asignaturas","NOMBRE",values);
            Toast.makeText(this.getContext(),
                    "registrado correctamente: "+id_resultado, Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}