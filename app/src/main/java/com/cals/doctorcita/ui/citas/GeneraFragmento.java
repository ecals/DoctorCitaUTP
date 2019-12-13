package com.cals.doctorcita.ui.citas;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cals.doctorcita.R;

import com.cals.doctorcita.adaptador.HorarioAdaptador;
import com.cals.doctorcita.entidad.Especialidad;
import com.cals.doctorcita.entidad.Globales;
import com.cals.doctorcita.entidad.Horario;
import com.cals.doctorcita.entidad.Medico;
import com.cals.doctorcita.servicio.ServicioWebBase;
import com.cals.doctorcita.servicio.Servicio_Especialidad;
import com.cals.doctorcita.servicio.Servicio_Horario;
import com.cals.doctorcita.servicio.Servicio_Medico;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_spinner_item;



public class GeneraFragmento extends Fragment {

    public static GeneraFragmento newInstance(Bundle bundle) {
        GeneraFragmento fragment = new GeneraFragmento();
        fragment.setArguments(bundle);
        return fragment;
    }

    private static String idEspecialidad, idMedico, idPaciente, fecha, hora;

    public GeneraFragmento() {

    }

    //Especialidad3es
    private ArrayList<Especialidad> EspecialidadModelArrayList;
    private ArrayList<String> arrayListEspecialidad = new ArrayList<String>();
    List<Especialidad> servicioEspecialidades;
    Spinner spnEspecialidades, spnMedicos;


    //Medicos
    private ArrayList<Medico> MedicoModelArrayList;
    private ArrayList<String> arrayListMedico = new ArrayList<String>();
    List<Medico> servicioMedicos;


    DatePickerDialog picker;
    EditText txtFecha;
    Button btnBuscar;
    TextView tvw;


    private RecyclerView recyclerViewHorario;
    //private ArrayList<Horario> data;
    private HorarioAdaptador adapterHorario;
    private ArrayList<Horario> horarioList;
    String ItemSeleccionadaMedico, ItemSeleccionadaEspecialidad;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View root = inflater.inflate(R.layout.fragmento_genera, container, false);

        if(root != null) {

            try {
            final Globales objvalor = (Globales) getActivity().getApplicationContext();
            idEspecialidad = objvalor.getG_idEspecialidad();
            idMedico = objvalor.getG_idMedico();
            idPaciente = objvalor.getG_idPacienteUsuario();


            recyclerViewHorario = (RecyclerView) root.findViewById(R.id.horariodRecyclerView);


            spnEspecialidades = (Spinner) root.findViewById(R.id.cboEspecialidad);
            spnMedicos = (Spinner) root.findViewById(R.id.cboMedico);


            LlenaEspecialidad(Integer.valueOf(idEspecialidad));


            LlenaMedico(Integer.valueOf(idMedico));


            String date = String.valueOf(android.text.format.DateFormat.format("dd/MM/yyyy", new java.util.Date()));


            txtFecha = root.findViewById(R.id.ediFecha);
            txtFecha.setInputType(InputType.TYPE_NULL);


            txtFecha.setText(date);


            txtFecha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);

                    picker = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    txtFecha.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                }
                            }, year, month, day);
                    picker.show();
                }
            });

            btnBuscar = root.findViewById(R.id.btnBuscar);

            btnBuscar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ItemSeleccionadaMedico = spnMedicos.getSelectedItem().toString();

                    int valorArea = 0;
                    for (int i = 0; i < arrayListMedico.size(); i++) {
                        if (ItemSeleccionadaMedico == servicioMedicos.get(i).getNombre()) {
                            valorArea = servicioMedicos.get(i).getIdMedico();
                            break;
                        }
                    }

                    LLenaHorario(String.valueOf(valorArea), String.valueOf(txtFecha.getText()));

                }
            });

            }catch (Exception ex)
            {

            }

        }
        return root;
    }



    private void LLenaHorario(final String idMedico, String fechaBuscar)
    {

        if (getActivity()!=null) {

            //horarioList.clear();

            Servicio_Horario servicioHorario = ServicioWebBase.getClient().create(Servicio_Horario.class);

            Call<List<Horario>> call = servicioHorario.getHorario("1", idMedico, fechaBuscar);

            call.enqueue(new Callback<List<Horario>>() {
                @Override
                public void onResponse(Call<List<Horario>> call, Response<List<Horario>> response) {

                    if (response.isSuccessful()) {

                        horarioList = (ArrayList<Horario>) response.body();

                        if (horarioList.size() > 0) {

                            if (getActivity() != null) {

                                adapterHorario = new HorarioAdaptador(horarioList);
                                RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getContext());
                                recyclerViewHorario.setLayoutManager(eLayoutManager);
                                recyclerViewHorario.setItemAnimator(new DefaultItemAnimator());
                                recyclerViewHorario.setAdapter(adapterHorario);

                                adapterHorario.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        int valor = horarioList.get(recyclerViewHorario.getChildAdapterPosition(view)).getIdHorario();
                                        final Globales objvalor = (Globales) getActivity().getApplicationContext();

                                        fecha = String.valueOf(txtFecha.getText());
                                        hora = String.valueOf(horarioList.get(recyclerViewHorario.getChildAdapterPosition(view)).getFechaHora());
                                        EnviarDatos(String.valueOf(horarioList.get(recyclerViewHorario.getChildAdapterPosition(view)).getIdHorario()), fecha, hora);
                                    }
                                });
                            }

                        } else {
                            Toast.makeText(getContext(), "No hay cita programada", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(getContext(), "No se pudo completar la operación", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<Horario>> call, Throwable t) {
                    Toast.makeText(getContext(), "No existe horario programado", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    //Captura y envio de Datos
    private void EnviarDatos(String idHorario, String fecha, String hora)
    {

        try {

            Bundle args = new Bundle();

            args.putString("s_idHorario", idHorario);
            args.putString("s_idCita", "-1");
            args.putString("s_nombrePaciente","Nombre del paciente");

            args.putString("s_fecha",fecha);
            args.putString("s_hora",hora);

            ItemSeleccionadaMedico = spnMedicos.getSelectedItem().toString();
            if(ItemSeleccionadaMedico.isEmpty())
            {
                Toast.makeText(getContext(),"Debe seleccionar Medico",Toast.LENGTH_SHORT).show();
                return;
            }
            int valorMedico = 0;
            String nombreMedico = "";
            for (int i = 0; i < arrayListMedico.size(); i++){
                if(ItemSeleccionadaMedico == servicioMedicos.get(i).getNombre()){
                    valorMedico = servicioMedicos.get(i).getIdMedico();
                    nombreMedico = servicioMedicos.get(i).getNombre();
                    break;
                }
            }

            args.putInt("s_valorMedico",valorMedico);
            args.putString("s_nombreMedico",nombreMedico);



            ItemSeleccionadaEspecialidad = spnEspecialidades.getSelectedItem().toString();
            int valorEspecialidad = 0;
            String nombreEspecialidad = "";
            for (int j = 0; j < arrayListEspecialidad.size(); j++){
                if(ItemSeleccionadaEspecialidad == servicioEspecialidades.get(j).getDescripcion()){
                    valorEspecialidad = servicioEspecialidades.get(j).getIdEspecialidad();
                    nombreEspecialidad = servicioEspecialidades.get(j).getDescripcion();
                    break;
                }
            }

            args.putInt("s_valorEspecialidad",valorEspecialidad);
            args.putString("s_nombreEspecialidad",nombreEspecialidad);

            args.putString("s_estado","1");


            GrabaCitaFragmento fr=new GrabaCitaFragmento();
            fr.setArguments(args);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment,fr)
                    .addToBackStack(null)
                    .commit();


        }catch (Exception ex)
        {
            Toast.makeText(getContext(), "Se ha producido algún error!", Toast.LENGTH_SHORT).show();
        }

    }


    private void LlenaEspecialidad(final int _valorEntrante)
    {

        if (getActivity()!=null) {

            arrayListEspecialidad.clear();

            Servicio_Especialidad servicioEspecialidad = ServicioWebBase.getClient().create(Servicio_Especialidad.class);

            Call<List<Especialidad>> call = servicioEspecialidad.getEspecialidades("1");

            call.enqueue(new Callback<List<Especialidad>>() {
                @Override
                public void onResponse(Call<List<Especialidad>> call, Response<List<Especialidad>> response) {

                    if (response.isSuccessful()) {


                        servicioEspecialidades = (List<Especialidad>) response.body();

                        EspecialidadModelArrayList = new ArrayList<Especialidad>();

                        for (int i = 0; i < servicioEspecialidades.size(); i++) {

                            Especialidad objAmbiente = new Especialidad();

                            objAmbiente.setIdEspecialidad(servicioEspecialidades.get(i).getIdEspecialidad());
                            objAmbiente.setDescripcion(servicioEspecialidades.get(i).getDescripcion());
                            EspecialidadModelArrayList.add(objAmbiente);
                        }

                        for (int i = 0; i < EspecialidadModelArrayList.size(); i++) {

                            arrayListEspecialidad.add(EspecialidadModelArrayList.get(i).getDescripcion().toString());

                        }

                        if (getActivity()!=null) {
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), simple_spinner_item, arrayListEspecialidad);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            spnEspecialidades.setAdapter(spinnerArrayAdapter);
                        }

                        int valorEntrante = _valorEntrante;

                        int posicion = 0;

                        for (int i = 0; i < servicioEspecialidades.size(); i++) {

                            if (valorEntrante == servicioEspecialidades.get(i).getIdEspecialidad()) {
                                posicion = i;
                                break;
                            }
                        }

                        spnEspecialidades.setSelection(posicion);



                    }else
                    {
                        Toast.makeText( getContext(),"No se pudo recuperar la data",Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<Especialidad>> call, Throwable t) {
                    Toast.makeText( getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    private void LlenaMedico(final int _valorEntrante)
    {

        if (getActivity()!=null) {

            arrayListMedico.clear();

            Servicio_Medico servicioMedico = ServicioWebBase.getClient().create(Servicio_Medico.class);

            Call<List<Medico>> call = servicioMedico.getMedico("1", idEspecialidad);

            call.enqueue(new Callback<List<Medico>>() {
                @Override
                public void onResponse(Call<List<Medico>> call, Response<List<Medico>> response) {

                    if (response.isSuccessful()) {

                        servicioMedicos = (List<Medico>) response.body();

                        MedicoModelArrayList = new ArrayList<Medico>();

                        for (int i = 0; i < servicioMedicos.size(); i++) {

                            Medico objAmbiente = new Medico();

                            objAmbiente.setIdMedico(servicioMedicos.get(i).getIdMedico());
                            objAmbiente.setNombre(servicioMedicos.get(i).getNombre());
                            MedicoModelArrayList.add(objAmbiente);
                        }

                        for (int i = 0; i < MedicoModelArrayList.size(); i++) {

                            arrayListMedico.add(MedicoModelArrayList.get(i).getNombre().toString());

                        }

                        if (getActivity()!=null) {
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), simple_spinner_item, arrayListMedico);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            spnMedicos.setAdapter(spinnerArrayAdapter);
                        }

                        int valorEntrante = _valorEntrante;

                        int posicion = 0;

                        for (int i = 0; i < servicioMedicos.size(); i++) {

                            if (valorEntrante == servicioMedicos.get(i).getIdMedico()) {
                                posicion = i;
                                break;
                            }
                        }

                       spnMedicos.setSelection(posicion);

                    }else
                    {
                        Toast.makeText( getContext(),"No se pudo recuperar la data",Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<Medico>> call, Throwable t) {
                    Toast.makeText( getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
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
