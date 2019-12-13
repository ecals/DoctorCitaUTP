package com.cals.doctorcita.ui.usuario;


import android.app.ProgressDialog;
import android.graphics.Movie;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.cals.doctorcita.MainActivity;
import com.cals.doctorcita.R;
//import com.cals.doctorcita.adaptador.UsuarioAdaptador;
//import com.cals.doctorcita.adaptador.UsuarioAdaptador;
import com.cals.doctorcita.adaptador.UsuarioAdaptador;
import com.cals.doctorcita.entidad.Usuario;
//import com.cals.doctorcita.servicio.CustomItemClickListener;
import com.cals.doctorcita.servicio.ServicioWebBase;
import com.cals.doctorcita.servicio.Servicio_Usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioFragmento extends Fragment {

    public static final String TAG ="UsuarioFragmentoTag";

    ProgressDialog progressDialog;
    private RecyclerView myRecyclerView;
    RecyclerView recyclerAdapter;

    private RecyclerView recyclerView;
    private ArrayList<Usuario> data;
    private UsuarioAdaptador adapter;
    //private ArrayList<Usuario> userList;
    private ArrayList<Usuario> usuarioList;

    public UsuarioFragmento() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View root = inflater.inflate(R.layout.fragmento_usuario, container, false);

        Servicio_Usuario servicioUsuario = ServicioWebBase.getClient().create(Servicio_Usuario.class);

        Call<List<Usuario>> call = servicioUsuario.getUsuarios("2");

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {

                if (response.isSuccessful()) {

                    usuarioList = (ArrayList<Usuario>) response.body();


                    recyclerView = (RecyclerView)root.findViewById(R.id.usuarioRecyclerView);

                    adapter = new UsuarioAdaptador(usuarioList);
                    RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(eLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {

            }
        });




        return  root;
    }


}
