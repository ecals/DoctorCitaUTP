package com.cals.doctorcita.ui.citas;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cals.doctorcita.MainActivity;
import com.cals.doctorcita.R;
import com.cals.doctorcita.entidad.Cita;
import com.cals.doctorcita.entidad.ComboBox;
import com.cals.doctorcita.entidad.Globales;
import com.cals.doctorcita.servicio.ServicioWebBase;
import com.cals.doctorcita.servicio.Servicio_Cita;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_spinner_item;


public class GrabaCitaFragmento extends Fragment {

    public static GrabaCitaFragmento newInstance(Bundle bundle) {
        GrabaCitaFragmento fragment = new GrabaCitaFragmento();
        fragment.setArguments(bundle);
        return fragment;
    }

    EditText t0, t1,t2,t3,t4,t5;
    Spinner spnEstado;

    ProgressDialog progressDialog;

    public static List<ComboBox> objEstado = new ArrayList<>();
    private ArrayList<String> arrayListEstado = new ArrayList<String>();

    Button btnGrabar, btnAnular, btnAtender;
    Bundle valores;

    private String nivelUsuario, codigoCita;


    public GrabaCitaFragmento() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View root = inflater.inflate(R.layout.fragmento_graba_cita, container, false);


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Por favor espere...");


        if(root != null) {

            t0 = root.findViewById(R.id.p_txtCita);
            t1 = root.findViewById(R.id.p_txtPaciente);
            t2 = root.findViewById(R.id.p_txtEspecialidad);
            t3 = root.findViewById(R.id.p_txtMedico);
            t4 = root.findViewById(R.id.p_txtFecha);
            t5 = root.findViewById(R.id.p_txtHora);

            spnEstado = (Spinner) root.findViewById(R.id.p_spnEstado);
            btnGrabar = root.findViewById(R.id.btnGrabar);
            btnAnular = root.findViewById(R.id.btnAnular);
            btnAtender = root.findViewById(R.id.btnAtender);

            if (getArguments() != null) {


                valores = getArguments();

                t0.setText(String.valueOf(valores.getString("s_idCita")));
                t1.setText(String.valueOf(valores.getString("s_nombrePaciente")));
                t2.setText(String.valueOf(valores.getString("s_nombreEspecialidad")));
                t3.setText(String.valueOf(valores.getString("s_nombreMedico")));
                t4.setText(String.valueOf(valores.getString("s_fecha")));
                t5.setText(String.valueOf(valores.getString("s_hora")));


                t0.setEnabled(false);
                t1.setEnabled(false);
                t2.setEnabled(false);
                t3.setEnabled(false);
                t4.setEnabled(false);
                t5.setEnabled(false);
                spnEstado.setEnabled(false);


                LlenaEstado(Integer.valueOf(valores.getString("s_estado")));


                final Globales objvalor = (Globales) getActivity().getApplicationContext();
                nivelUsuario = String.valueOf(objvalor.getG_nivelUsuario());
                codigoCita = String.valueOf(t0.getText());


                if (codigoCita.equals("-1")) {
                    btnGrabar.setVisibility(View.VISIBLE);
                    btnAnular.setVisibility(View.GONE);
                    btnAtender.setVisibility(View.GONE);

                } else {
                    btnGrabar.setVisibility(View.GONE);


                    if (nivelUsuario.equals("1")) {
                        btnAnular.setVisibility(View.VISIBLE);
                        btnAtender.setVisibility(View.VISIBLE);


                    } else if (nivelUsuario.equals("2")) {
                        btnAnular.setVisibility(View.VISIBLE);
                        btnAtender.setVisibility(View.GONE);


                    } else if (nivelUsuario.equals("3")) {
                        btnAnular.setVisibility(View.GONE);
                        btnAtender.setVisibility(View.VISIBLE);

                    }

                }

                //Registrar Cita
                btnGrabar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        final Globales objvalor = (Globales) getActivity().getApplicationContext();

                        GrabarCita(String.valueOf(valores.getString("s_idHorario")), objvalor.getG_idPacienteUsuario());
                    }
                });

            }

            //Anular Cita
            btnAnular.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AnularCita(String.valueOf(t0.getText()));


                }
            });


            //Atender Cita
            btnAtender.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AtenderCita(String.valueOf(t0.getText()));
                }
            });

        }

        return root;

    }



    private void AtenderCita(String idCita)
    {

        progressDialog.show();

        Servicio_Cita servicioCita = ServicioWebBase.getClient().create(Servicio_Cita.class);

        Call<Cita> call = servicioCita.AnularCita("4",idCita);

        call.enqueue(new Callback<Cita>() {
            @Override
            public void onResponse(Call<Cita> call, Response<Cita> response) {

                if(response.isSuccessful() && response.body() !=null )
                {

                    Boolean success = response.body().getSuccess();

                    if(success)
                    {

                        Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                        getActivity().onBackPressed();


                    }else
                    {
                        Toast.makeText(getContext(),""+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }


                }else
                {

                    Toast.makeText(getContext(),"No se pudo completar la operación",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }


            }

            @Override
            public void onFailure(Call<Cita> call, Throwable t) {
                Toast.makeText(getContext(),""+t.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }


    private void GrabarCita(final String idHorario, final String idPaciente)
    {
        progressDialog.show();

        Servicio_Cita servicioProyectoDetalle = ServicioWebBase.getClient().create(Servicio_Cita.class);

        Call<Cita> call = servicioProyectoDetalle.GrabaCita("2",idHorario,idPaciente);

        call.enqueue(new Callback<Cita>() {
            @Override
            public void onResponse(Call<Cita> call, Response<Cita> response) {

                if(response.isSuccessful() && response.body() !=null )
                {

                    Boolean success = response.body().getSuccess();

                    if(success)
                    {
                        Toast.makeText(getContext(),""+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        getActivity().onBackPressed();



                    }else
                    {
                        Toast.makeText(getContext(),""+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }



                }else
                {

                    Toast.makeText(getContext(),"No se pudo completar la operación",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }


            }

            @Override
            public void onFailure(Call<Cita> call, Throwable t) {
                Toast.makeText(getContext(),""+t.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }



    private void AnularCita(String idCita)
    {
        progressDialog.show();

        Servicio_Cita servicioCita = ServicioWebBase.getClient().create(Servicio_Cita.class);

        Call<Cita> call = servicioCita.AnularCita("3",idCita);

        call.enqueue(new Callback<Cita>() {
            @Override
            public void onResponse(Call<Cita> call, Response<Cita> response) {

                if(response.isSuccessful() && response.body() !=null )
                {

                    Boolean success = response.body().getSuccess();

                    if(success)
                    {


                        Toast.makeText(getContext(),""+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        getActivity().onBackPressed();

                    }else
                    {
                        Toast.makeText(getContext(),""+response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }


                }else
                {

                    Toast.makeText(getContext(),"No se pudo completar la operación",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }


            }

            @Override
            public void onFailure(Call<Cita> call, Throwable t) {
                Toast.makeText(getContext(),""+t.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }


    private void LlenaEstado(int _valorEntrante)
    {
        arrayListEstado.clear();
        objEstado.clear();

        ComboBox fila1 = new ComboBox();
        fila1.setCodigo(2);
        fila1.setDescripcion("Generado");
        objEstado.add(fila1);

        ComboBox fila2 = new ComboBox();
        fila2.setCodigo(3);
        fila2.setDescripcion("Anulado");
        objEstado.add(fila2);

        ComboBox fila3 = new ComboBox();
        fila3.setCodigo(4);
        fila3.setDescripcion("Atendido");
        objEstado.add(fila3);


        for (int i = 0; i < objEstado.size(); i++){

            arrayListEstado.add(objEstado.get(i).getDescripcion().toString());

        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), simple_spinner_item, arrayListEstado);
        if (getActivity()!=null) {
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view

            spnEstado.setAdapter(spinnerArrayAdapter);
        }
        int valorEntrante =  _valorEntrante;
        int posicion = 0;
        for (int i = 0; i < objEstado.size(); i++){
            if(valorEntrante ==  objEstado.get(i).getCodigo()){
                posicion = i;
                break;
            }
        }

        spnEstado.setSelection(posicion);

    }




    /*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_crud,menu);

        super.onCreateOptionsMenu(menu, inflater);

        actionMenu = menu;



        if (getArguments() == null) {

            actionMenu.findItem(R.id.MenuGuarda).setVisible(true);
            actionMenu.findItem(R.id.MenuEdita).setVisible(false);
            actionMenu.findItem(R.id.MenuCancela).setVisible(false);

        }else
        {
            actionMenu.findItem(R.id.MenuGuarda).setVisible(false);
            actionMenu.findItem(R.id.MenuEdita).setVisible(true);
            actionMenu.findItem(R.id.MenuCancela).setVisible(false);

        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();

        if(id==R.id.MenuGuarda)
        {

            if(tipoAccion=="nuevo")
            {


            }

            if(tipoAccion=="edita")
            {


            }

            return true;

        }

        if(id==R.id.MenuEdita) {


            actionMenu.findItem(R.id.MenuGuarda).setVisible(true);
            actionMenu.findItem(R.id.MenuEdita).setVisible(false);
            actionMenu.findItem(R.id.MenuCancela).setVisible(true);

            tipoAccion ="edita";

            return true;
        }



        if(id==R.id.MenuCancela) {

            tipoAccion ="cancela";

            //this.getActivity().onBackPressed();

            actionMenu.findItem(R.id.MenuGuarda).setVisible(false);
            actionMenu.findItem(R.id.MenuEdita).setVisible(true);
            actionMenu.findItem(R.id.MenuCancela).setVisible(false);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/



}
