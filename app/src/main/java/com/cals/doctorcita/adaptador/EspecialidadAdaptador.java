package com.cals.doctorcita.adaptador;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.cals.doctorcita.R;
import com.cals.doctorcita.entidad.Especialidad;

import java.util.List;


public class EspecialidadAdaptador extends RecyclerView.Adapter<EspecialidadAdaptador.CustomViewHolder> implements View.OnClickListener{
    private List<Especialidad> especialidades;
    private View.OnClickListener listener;

    public EspecialidadAdaptador(List<Especialidad> especialidades) {
        this.especialidades = especialidades;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_especialidad, parent, false);

        itemView.setOnClickListener(this);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Especialidad especialidad = especialidades.get(position);
        holder.especialidadNombre.setText(especialidad.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return especialidades.size();
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
        public TextView especialidadNombre, cant_med;
        ImageView imageView;

        public CustomViewHolder(View view) {
            super(view);
            especialidadNombre = (TextView) view.findViewById(R.id.espe_nombre);
            cant_med = (TextView) view.findViewById(R.id.cant_med);
            //imageView = (ImageView) itemView.findViewById(R.id.cant_med);

        }
    }

}



