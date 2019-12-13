package com.cals.doctorcita.adaptador;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cals.doctorcita.R;
import com.cals.doctorcita.entidad.Usuario;
import java.util.ArrayList;
import java.util.List;



public class UsuarioAdaptador extends RecyclerView.Adapter<UsuarioAdaptador.CustomViewHolder> implements View.OnClickListener{
    private List<Usuario> usuarios;
    private View.OnClickListener listener;

    public UsuarioAdaptador(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_usuario, parent, false);

        itemView.setOnClickListener(this);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        holder.usuarioNombre.setText(usuario.getNombre());
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView usuarioNombre;
        ImageView imageView;

        public CustomViewHolder(View view) {
            super(view);
            usuarioNombre = (TextView) view.findViewById(R.id.nombreTV);
            imageView = (ImageView) itemView.findViewById(R.id.usuarioIV);


        }
    }


}



