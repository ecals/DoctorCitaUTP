package com.cals.doctorcita.servicio;

import com.cals.doctorcita.entidad.Cita;
import com.cals.doctorcita.entidad.Especialidad;
import com.cals.doctorcita.entidad.Horario;
import com.cals.doctorcita.entidad.Usuario;
import com.cals.doctorcita.entidad.Usuarios;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Servicio_Cita {


    /*
    @GET("citas.php")




    Call<List<Cita>> getCitaPaciente(@Query("idPaciente") int idPaciente, @Query("fechaCita") String fechaCita);

    @GET("citas.php")
    Call<List<Cita>> getCitaMedico(@Query("idMedico") int idMedico,@Query("fechaCita") String fechaCita);
    */

    /*@GET("CitasControlador.php?")
    Call<List<Cita>> getCitas(@Query("op") String op, @Query("idPaciente") String idPaciente, @Query("idMedico") String idMedico);
    */

    @GET("CitasControlador.php?")
    Call<List<Cita>> getCitas(@Query("op") String op, @Query("idPaciente") String idPaciente);


    @GET("CitasControlador.php?")
    Call<List<Cita>> getCitasPaciente(@Query("op") String op, @Query("idPaciente") String idPaciente);

    @GET("CitasControlador.php?")
    Call<List<Cita>> getCitasMedico(@Query("op") String op, @Query("idMedico") String idMedico);




    @FormUrlEncoded
    @POST("CitasControlador.php?")
    Call <Cita> GrabaCita(

            @Query("op") String op,
            @Field("idHorario") String idHorario,
            @Field("idPaciente") String idPaciente

    );

    @FormUrlEncoded
    @POST("CitasControlador.php?")
    Call <Cita> AnularCita(
            @Query("op") String op,
            @Field("idCita") String idCita
    );


}
