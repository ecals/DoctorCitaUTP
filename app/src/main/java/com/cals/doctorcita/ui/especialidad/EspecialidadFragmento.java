package com.cals.doctorcita.ui.especialidad;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.cals.doctorcita.MainActivity;
import com.cals.doctorcita.R;
import com.cals.doctorcita.adaptador.EspecialidadAdaptador;

import com.cals.doctorcita.entidad.Especialidad;

import com.cals.doctorcita.entidad.Globales;
import com.cals.doctorcita.servicio.ServicioWebBase;
import com.cals.doctorcita.servicio.Servicio_Especialidad;
import com.cals.doctorcita.ui.citas.GeneraActivity;
import com.cals.doctorcita.ui.citas.GeneraFragmento;
import com.cals.doctorcita.ui.login.LoginActivity;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EspecialidadFragmento extends Fragment {

    public static final String TAG ="EspecialidadFragmentoTag";

    ProgressDialog progressDialog;
    private RecyclerView myRecyclerView;
    RecyclerView recyclerAdapter;

    private RecyclerView recyclerView;
    private ArrayList<Especialidad> data;
    private EspecialidadAdaptador adapter;
    private ArrayList<Especialidad> especialidadList;

    public static ViewPager cViewPager;

    public final static String EXTRA_NOMBRE = "";



    public EspecialidadFragmento() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragmento_especialidad, container, false);

        if(root != null) {
            cViewPager = root.findViewById(R.id.main_tabs_pager);


            Servicio_Especialidad servicioEspecialidad = ServicioWebBase.getClient().create(Servicio_Especialidad.class);

            Call<List<Especialidad>> call = servicioEspecialidad.getEspecialidades("1");

            call.enqueue(new Callback<List<Especialidad>>() {
                @Override
                public void onResponse(Call<List<Especialidad>> call, Response<List<Especialidad>> response) {

                    if (response.isSuccessful()) {

                        especialidadList = (ArrayList<Especialidad>) response.body();


                        recyclerView = (RecyclerView) root.findViewById(R.id.especialidadRecyclerView);

                        adapter = new EspecialidadAdaptador(especialidadList);
                        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(eLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);


                        adapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int valorEspecialidad = especialidadList.get(recyclerView.getChildAdapterPosition(view)).getIdEspecialidad();
                                int valorMedico = 0;
                                final Globales objvalor = (Globales) getActivity().getApplicationContext();
                                objvalor.setG_idEspecialidad(String.valueOf(valorEspecialidad));
                                objvalor.setG_idMedico(String.valueOf(valorMedico));
                                objvalor.setFlg_fragmento("1");
                                Intent intent = new Intent(getActivity(), GeneraActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                getActivity().startActivity(intent);


                            }
                        });


                    }

                }

                @Override
                public void onFailure(Call<List<Especialidad>> call, Throwable t) {

                }
            });


        }

        return  root;



    }



}
