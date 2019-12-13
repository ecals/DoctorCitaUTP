package com.cals.doctorcita.servicio;

import com.cals.doctorcita.entidad.Especialidad;
import com.cals.doctorcita.entidad.Horario;
import com.cals.doctorcita.entidad.Medico;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Servicio_Horario {


    @GET("HorarioControlador.php?")
    Call<List<Horario>> getHorario(@Query("op") String op, @Query("idMedico") String idMedico, @Query("fechaHoraInicio") String fechaHoraInicio);


}
