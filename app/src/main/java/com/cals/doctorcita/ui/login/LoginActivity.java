package com.cals.doctorcita.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cals.doctorcita.MainActivity;
import com.cals.doctorcita.R;
import com.cals.doctorcita.entidad.Globales;
import com.cals.doctorcita.entidad.Usuario;
import com.cals.doctorcita.servicio.ServicioWebBase;
import com.cals.doctorcita.servicio.Servicio_Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    ProgressBar loadingProgressBar;
    List<Usuario> servicioLogin;
    String usuario, nombre, nivel, estado, idPaciente, idMedico;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);


        try {
            loginButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if( ValidarDatos()==true)
                    {
                       loginButton.setVisibility(View.GONE);
                       loadingProgressBar.setVisibility(View.VISIBLE);

                        Servicio_Usuario ServicioLogin = ServicioWebBase.getClient().create(Servicio_Usuario.class);

                        Call call = ServicioLogin.getUsuario("1",usernameEditText.getText().toString(),passwordEditText.getText().toString());


                        call.enqueue(new Callback() {
                            @Override
                            public void onResponse(Call call, Response response) {


                                List<Usuario> objlogin = (List<Usuario>) response.body();
                                if(objlogin.size()>0)
                                {

                                servicioLogin = (List<Usuario>) response.body();

                                for (int i = 0; i < servicioLogin.size(); i++) {
                                    usuario = servicioLogin.get(i).getUsuario();
                                    nombre= servicioLogin.get(i).getNombre();
                                    nivel= servicioLogin.get(i).getNivel();
                                    estado= servicioLogin.get(i).getEstado();

                                    idPaciente= servicioLogin.get(i).getIdPaciente();
                                    idMedico= servicioLogin.get(i).getIdMedico();

                                }

                                if(estado.equals("failed"))
                                {
                                    Toast.makeText(LoginActivity.this,"Usuario o contraseña incorrecto!",Toast.LENGTH_SHORT).show();
                                    loadingProgressBar.setVisibility(View.INVISIBLE);
                                    loginButton.setVisibility(View.VISIBLE);
                                }else
                                {

                                    //Envio de variable global medico y paciente del usuario
                                    final Globales objvalor=(Globales)getApplication().getApplicationContext();
                                    objvalor.setG_idPacienteUsuario(idPaciente);
                                    objvalor.setG_idMedicoUsuario(String.valueOf(idMedico));
                                    objvalor.setG_nivelUsuario(String.valueOf(nivel));



                                    Intent intent = new Intent (LoginActivity.this, MainActivity.class);
                                    intent.putExtra("s_usuario",String.valueOf(usuario));
                                    intent.putExtra("s_nombre",String.valueOf(nombre));
                                    intent.putExtra("s_nivel",String.valueOf(nivel));
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                    loginButton.setVisibility(View.GONE);

                                }

                                }else
                                {
                                    Toast.makeText(LoginActivity.this,"Usuario o contraseña incorrecto!",Toast.LENGTH_SHORT).show();
                                    loadingProgressBar.setVisibility(View.INVISIBLE);
                                    loginButton.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                Toast.makeText(LoginActivity.this, "Error al conectarse con el servicio web!",Toast.LENGTH_SHORT).show();
                                loadingProgressBar.setVisibility(View.INVISIBLE);
                                loginButton.setVisibility(View.VISIBLE);
                            }
                        });

                    }
                }









            });


        }catch (Exception e)
        {

            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();

        }


    }


    private boolean ValidarDatos()
    {

        String _usernameEditText =usernameEditText.getText().toString().trim();
        String _passwordEditText =passwordEditText.getText().toString().trim();
        if(_usernameEditText.isEmpty())
        {
            usernameEditText.requestFocus();
            usernameEditText.setError("Ingrese Usuario");
            return false;
        }else

        if(_passwordEditText.isEmpty())
        {
            passwordEditText.requestFocus();
            passwordEditText.setError("Ingrese su Contraseña");
            return false;
        }else
        {
            return true;
        }

    }



}
