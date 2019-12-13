package com.cals.doctorcita.servicio;

import com.cals.doctorcita.entidad.Especialidad;
import com.cals.doctorcita.entidad.Medico;
import com.cals.doctorcita.entidad.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Servicio_Medico {


    @GET("MedicoControlador.php?")
    Call<List<Medico>> getMedico(@Query("op") String op, @Query("idEspecialidad") String idEspecialidad );



    @GET("MedicoControlador.php?")
    Call<List<Medico>> getMedicos(@Query("op") String op);




}
