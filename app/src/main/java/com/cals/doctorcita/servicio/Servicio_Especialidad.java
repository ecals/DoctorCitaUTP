package com.cals.doctorcita.servicio;

import com.cals.doctorcita.entidad.Especialidad;
import com.cals.doctorcita.entidad.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Servicio_Especialidad {


    @GET("EspecialidadControlador.php?")
    Call<List<Especialidad>> getEspecialidades(@Query("op") String op);


}
