package com.cals.doctorcita.ui.citas;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cals.doctorcita.R;
import com.cals.doctorcita.adaptador.CitaAdaptador;
import com.cals.doctorcita.adaptador.EspecialidadAdaptador;
import com.cals.doctorcita.entidad.Cita;
import com.cals.doctorcita.entidad.Especialidad;
import com.cals.doctorcita.entidad.Globales;
import com.cals.doctorcita.servicio.ServicioWebBase;
import com.cals.doctorcita.servicio.Servicio_Cita;
import com.cals.doctorcita.servicio.Servicio_Especialidad;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CitasFragmento extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Cita> data;
    private CitaAdaptador adapter;
    private ArrayList<Cita> citaList;
    Call<List<Cita>> call;
    private String valor_idPaciente;

    public CitasFragmento() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       final View root = inflater.inflate(R.layout.fragmento_citas, container, false);

        if(root != null) {
            final Globales objvalor = (Globales) getActivity().getApplicationContext();

            Servicio_Cita servicioCita = ServicioWebBase.getClient().create(Servicio_Cita.class);

            valor_idPaciente = String.valueOf(objvalor.getG_idPacienteUsuario());
            if (valor_idPaciente == "null") {

                call = servicioCita.getCitasMedico("6", objvalor.getG_idMedicoUsuario());
            } else {

                call = servicioCita.getCitasPaciente("5", objvalor.getG_idPacienteUsuario());
            }


            call.enqueue(new Callback<List<Cita>>() {
                @Override
                public void onResponse(Call<List<Cita>> call, Response<List<Cita>> response) {

                    if (response.isSuccessful()) {

                        citaList = (ArrayList<Cita>) response.body();


                        recyclerView = (RecyclerView) root.findViewById(R.id.citaRecyclerView);

                        adapter = new CitaAdaptador(citaList);
                        RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(eLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);


                        adapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                int valor = citaList.get(recyclerView.getChildAdapterPosition(view)).getIdCita();

                                Bundle args = new Bundle();
                                args.putString("s_idCita", String.valueOf(valor));
                                args.putString("s_nombrePaciente", "Nombre del paciente");
                                args.putString("s_fecha", String.valueOf(citaList.get(recyclerView.getChildAdapterPosition(view)).getFechaHoraInicio()));
                                args.putString("s_hora", String.valueOf(citaList.get(recyclerView.getChildAdapterPosition(view)).getFechaHora()));
                                args.putInt("s_valorMedico", citaList.get(recyclerView.getChildAdapterPosition(view)).getIdMedico());
                                args.putString("s_nombreMedico", String.valueOf(citaList.get(recyclerView.getChildAdapterPosition(view)).getMedico()));
                                args.putInt("s_valorEspecialidad", citaList.get(recyclerView.getChildAdapterPosition(view)).getIdEspecialidad());
                                args.putString("s_nombreEspecialidad", String.valueOf(citaList.get(recyclerView.getChildAdapterPosition(view)).getDescripcion()));
                                args.putString("s_estado", String.valueOf(citaList.get(recyclerView.getChildAdapterPosition(view)).getEstado()));


                                final Globales objvalor = (Globales) getActivity().getApplicationContext();
                                objvalor.setFlg_fragmento("2");

                                Intent intent = new Intent(getActivity(), GeneraActivity.class);
                                intent.putExtras(args);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                getActivity().startActivity(intent);


                            }
                        });
                    }

                }

                @Override
                public void onFailure(Call<List<Cita>> call, Throwable t) {

                }
            });


        }

        return root;

    }

}
