package com.cals.doctorcita.adaptador;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cals.doctorcita.R;
import com.cals.doctorcita.entidad.Cita;
import com.cals.doctorcita.entidad.Especialidad;

import java.util.List;


public class CitaAdaptador extends RecyclerView.Adapter<CitaAdaptador.CustomViewHolder> implements View.OnClickListener{
    private List<Cita> citas;
    private View.OnClickListener listener;

    public CitaAdaptador(List<Cita> citas) {
        this.citas = citas;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_cita, parent, false);

        itemView.setOnClickListener(this);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Cita cita = citas.get(position);
        holder.especialidad.setText("Especialidad: "+cita.getDescripcion());
        holder.medico.setText("Médico: "+cita.getMedico());
        holder.fechaHora.setText("Fecha: "+cita.getFechaHoraInicio());
        holder.paciente.setText("Paciente: "+cita.getPaciente());
        holder.hora.setText("Hora: "+cita.getFechaHora());
        holder.estado.setText("Estado: "+cita.getTxtEstado());


    }

    @Override
    public int getItemCount() {
        return citas.size();
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
        public TextView especialidad, medico, fechaHora, paciente, hora, estado;
        ImageView imageView;

        public CustomViewHolder(View view) {
            super(view);
            especialidad = (TextView) view.findViewById(R.id.c_especialidad);
            medico = (TextView) view.findViewById(R.id.c_medico);
            fechaHora = (TextView) view.findViewById(R.id.c_fecha);
            paciente = (TextView) view.findViewById(R.id.c_paciente);
            hora = (TextView) view.findViewById(R.id.c_hora);
            estado = (TextView) view.findViewById(R.id.c_estado);

            //imageView = (ImageView) itemView.findViewById(R.id.cant_med);

        }
    }

}



