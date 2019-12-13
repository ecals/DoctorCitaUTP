package com.cals.doctorcita.servicio;

import com.cals.doctorcita.entidad.Usuario;
import com.cals.doctorcita.entidad.Usuarios;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Servicio_Usuario {


    @GET("UsuariosControlador.php?")
    Call<List<Usuario>> getUsuarios(@Query("op") String op);

    @GET("UsuariosControlador.php?")
    Call<List<Usuario>> getUsuario(@Query("op") String op, @Query("usuario") String usuario, @Query("password") String password);

    /*
    @GET("login.php")
    Call <Usuario> getUser(@Query("usuario") String usuario, @Query("contrasena") String contrasena);
    */

    @FormUrlEncoded
    @POST("guardar.php")
    Call <Usuarios> GuardarUsuario(
                                    @Field("idPersona") String idPersona,
                                    @Field("usuario") String usuario,
                                    @Field("contrasena") String contrasena,
                                    @Field("nivel") String nivel,
                                    @Field("estado") String estado
    );

}

//https://citamedicautp.000webhostapp.com/especialidad.php