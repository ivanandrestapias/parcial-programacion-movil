package com.example.parcial_001;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Registrarnotas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Registrarnotas extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Registrarnotas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Registrarnotas.
     */
    // TODO: Rename and change types and number of parameters
    public static Registrarnotas newInstance(String param1, String param2) {
        Registrarnotas fragment = new Registrarnotas();
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
    Spinner lista;
    ArrayList<String> listaInformacion;
    ArrayList<Asignatura> asignaturas;
    databaseHelper con;


    Button guardar_nota;
    Button agregarnota1,agregarnota2,agregarnota3;

    EditText nota1,porcentaje1,actividad1;
    EditText nota2,porcentaje2,actividad2;
    EditText nota3,porcentaje3,actividad3;

    int ptotal1=0;
    int ptotal2=0;
    int ptotal3=0;
    double dcorte1=0;
    double dcorte2=0;
    double dcorte3=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_registrarnotas, container, false);
        lista = view.findViewById(R.id.spinner2);
        con = new databaseHelper(this.getContext(),"parcial",null,1);
        llenarspinner();
        ArrayAdapter adaptador = new ArrayAdapter(this.getContext(),R.layout.support_simple_spinner_dropdown_item,listaInformacion);
        lista.setAdapter(adaptador);


        nota1 = view.findViewById(R.id.nc1);
        nota2 = view.findViewById(R.id.nc2);
        nota3 = view.findViewById(R.id.nc3);
        porcentaje1 = view.findViewById(R.id.pc1);
        porcentaje2 = view.findViewById(R.id.pc2);
        porcentaje3 = view.findViewById(R.id.pc3);
        actividad1 = view.findViewById(R.id.ac1);
        actividad2 = view.findViewById(R.id.ac2);
        actividad3 = view.findViewById(R.id.ac3);

        agregarnota1 = view.findViewById(R.id.anc1);
        agregarnota2 = view.findViewById(R.id.anc2);
        agregarnota3 = view.findViewById(R.id.anc3);

        agregarnota1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarnota1();
            }
        });

        agregarnota2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarnota2();
            }
        });

        agregarnota3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               agregarnota3();
            }
        });

        guardar_nota = view.findViewById(R.id.guardar_nota);

        guardar_nota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double final_ = (dcorte1*0.3+dcorte2*0.3+dcorte3*0.4);
                String asignatura = lista.getSelectedItem().toString();
                if (ptotal1==100 && ptotal2==100 && ptotal3==100){
                    databaseHelper con = new databaseHelper(getContext(),"parcial",null,1);
                    SQLiteDatabase db = con.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put("ASIGNATURA",asignatura);
                    values.put("CORTE1",dcorte1);
                    values.put("CORTE2",dcorte2);
                    values.put("CORTE3",dcorte3);
                    values.put("NOTA",final_);
                    Long result = db.insert("notas","ASIGNATURA",values);

                    Toast.makeText(getContext(), "notas guardadas corectamente: "+result, Toast.LENGTH_SHORT).show();
                    db.close();
                    //reseteo valores
                    ptotal1=0;
                    ptotal2=0;
                    ptotal3=0;
                    dcorte1=0;
                    dcorte2=0;
                    dcorte3=0;

                }else{
                    Toast.makeText(getContext(), "algunos de los porcentajes es menor  a 100  ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public void  llenarspinner(){
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
        db.close();
    }

    public  void obtenerLista(){
        listaInformacion = new ArrayList<String>();
        for (int i = 0; i<asignaturas.size();i++){
            listaInformacion.add(asignaturas.get(i).Nombre);
        }
    }


    public void agregarnota1(){
        double porcentajetemp = ptotal1+Integer.parseInt(porcentaje1.getText().toString());
        if (porcentajetemp<=100){
            if (Double.parseDouble(nota1.getText().toString())>=0 && Double.parseDouble(nota1.getText().toString())<=5){
                ptotal1 = ptotal1+Integer.parseInt(porcentaje1.getText().toString());
                double nota = Double.parseDouble(nota1.getText().toString());
                Integer porcentaje = Integer.parseInt(porcentaje1.getText().toString());

                dcorte1 = dcorte1+(nota*porcentaje/100);
                Toast.makeText(this.getContext(), "ha utilizado : "+ptotal1, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this.getContext(), "las notas son de 0 a 5 ", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this.getContext(), "porcentaje no valido  ", Toast.LENGTH_SHORT).show();
        }
    }

    public void agregarnota2(){
        double porcentajetemp = ptotal2+Integer.parseInt(porcentaje2.getText().toString());
        if (porcentajetemp<=100){
            if (Double.parseDouble(nota2.getText().toString())>=0 && Double.parseDouble(nota2.getText().toString())<=5){
                ptotal2 = ptotal2+Integer.parseInt(porcentaje2.getText().toString());
                double nota = Double.parseDouble(nota2.getText().toString());
                Integer porcentaje = Integer.parseInt(porcentaje2.getText().toString());

                dcorte2 = dcorte2+(nota*porcentaje/100);
                Toast.makeText(this.getContext(), "ha utilizado : "+ptotal2, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this.getContext(), "las notas son de 0 a 5 ", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this.getContext(), "porcentaje no valido  ", Toast.LENGTH_SHORT).show();
        }
    }

    public void agregarnota3(){
        double porcentajetem = ptotal3+Integer.parseInt(porcentaje3.getText().toString());
        if (porcentajetem<=100){
            if (Double.parseDouble(nota3.getText().toString())>=0 && Double.parseDouble(nota3.getText().toString())<=5){
                ptotal3 = ptotal3+Integer.parseInt(porcentaje3.getText().toString());
                double nota = Double.parseDouble(nota3.getText().toString());
                Integer porcentaje = Integer.parseInt(porcentaje3.getText().toString());

                dcorte3 = dcorte3+(nota*porcentaje/100);
                Toast.makeText(this.getContext(), "ha utilizado : "+ptotal3, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this.getContext(), "las notas son de 0 a 5 ", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this.getContext(), "porcentaje no valido  ", Toast.LENGTH_SHORT).show();
        }
    }
}