package com.cals.doctorcita.adaptador;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cals.doctorcita.R;
import com.cals.doctorcita.entidad.Medico;
import com.cals.doctorcita.entidad.Usuario;

import java.util.List;


public class MedicoAdaptador extends RecyclerView.Adapter<MedicoAdaptador.CustomViewHolder> implements View.OnClickListener{
    private List<Medico> medicos;
    private View.OnClickListener listener;

    public MedicoAdaptador(List<Medico> medicos) {
        this.medicos = medicos;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_medico, parent, false);

        itemView.setOnClickListener(this);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Medico medico = medicos.get(position);
        holder.medicoNombre.setText(medico.getNombre());
        holder.espeMedico.setText(medico.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return medicos.size();
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
        public TextView medicoNombre,espeMedico;
        ImageView imageView;

        public CustomViewHolder(View view) {
            super(view);
            imageView = (ImageView) itemView.findViewById(R.id.medicoIV);
            medicoNombre = (TextView) view.findViewById(R.id.nombreMedTV);
            espeMedico = (TextView) itemView.findViewById(R.id.espeMedicoTV);

        }
    }


}



