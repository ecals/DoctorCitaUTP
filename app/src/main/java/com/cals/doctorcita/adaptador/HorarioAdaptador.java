package com.cals.doctorcita.adaptador;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cals.doctorcita.R;
import com.cals.doctorcita.entidad.Horario;
import com.cals.doctorcita.entidad.Medico;

import java.util.List;


public class HorarioAdaptador extends RecyclerView.Adapter<HorarioAdaptador.CustomViewHolder> implements View.OnClickListener{
    private List<Horario> horarios;
    private View.OnClickListener listener;

    public HorarioAdaptador(List<Horario> horarios) {
        this.horarios = horarios;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_horario, parent, false);

        itemView.setOnClickListener(this);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Horario horario = horarios.get(position);
        holder.fechaHora.setText(horario.getFechaHora());
        //holder.idHorario.setText(horario.getIdHorario());
        //holder.FechaHoraInicio.setText(horario.getFechaHoraInicio());
        //holder.FechaHoraFin.setText(horario.getFechaHoraFin());
    }

    @Override
    public int getItemCount() {
        return horarios.size();
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
        public TextView idHorario, fechaHora  /*FechaHoraInicio, FechaHoraFin*/;
        //ImageView imageView;

        public CustomViewHolder(View view) {
            super(view);
            //FechaHoraInicio = (TextView) view.findViewById(R.id.horaInicio);
            //FechaHoraFin = (TextView) view.findViewById(R.id.horaFin);
            //FechaHoraFin = (ImageView) itemView.findViewById(R.id.horaFin);
            //espeMedico = (TextView) itemView.findViewById(R.id.espeMedicoTV);

            fechaHora = (TextView) view.findViewById(R.id.fechaHora);

        }
    }


}



