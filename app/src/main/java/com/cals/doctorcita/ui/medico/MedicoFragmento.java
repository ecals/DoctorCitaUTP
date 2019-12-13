package com.cals.doctorcita.ui.medico;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cals.doctorcita.R;
import com.cals.doctorcita.adaptador.EspecialidadAdaptador;
import com.cals.doctorcita.adaptador.MedicoAdaptador;
import com.cals.doctorcita.entidad.Especialidad;
import com.cals.doctorcita.entidad.Globales;
import com.cals.doctorcita.entidad.Medico;
import com.cals.doctorcita.servicio.ServicioWebBase;
import com.cals.doctorcita.servicio.Servicio_Especialidad;
import com.cals.doctorcita.servicio.Servicio_Medico;
import com.cals.doctorcita.ui.citas.GeneraActivity;
import com.cals.doctorcita.ui.especialidad.EspecialidadFragmento;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MedicoFragmento extends Fragment {


    public static final String TAG ="MedicoFragmentoTag";

    ProgressDialog progressDialog;
    private RecyclerView myRecyclerView;
    RecyclerView recyclerAdapter;

    private RecyclerView recyclerView;
    private ArrayList<Medico> data;
    private MedicoAdaptador adapter;
    private ArrayList<Medico> medicoList;
    static  String idEspecialidad;


    public MedicoFragmento() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragmento_medico, container, false);

        if (root != null) {

        Servicio_Medico servicioMedico = ServicioWebBase.getClient().create(Servicio_Medico.class);

        Call<List<Medico>> call = servicioMedico.getMedicos("2");

        call.enqueue(new Callback<List<Medico>>() {
            @Override
            public void onResponse(Call<List<Medico>> call, Response<List<Medico>> response) {

                if (response.isSuccessful()) {

                    medicoList = (ArrayList<Medico>) response.body();


                    recyclerView = (RecyclerView) root.findViewById(R.id.medicoRecyclerView);

                    adapter = new MedicoAdaptador(medicoList);
                    RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(eLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);


                    adapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            int valorEspecialidad = medicoList.get(recyclerView.getChildAdapterPosition(view)).getIdEspecialidad();
                            int valorMedico = medicoList.get(recyclerView.getChildAdapterPosition(view)).getIdMedico();
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
            public void onFailure(Call<List<Medico>> call, Throwable t) {

            }
        });


    }

        return  root;




    }

}
