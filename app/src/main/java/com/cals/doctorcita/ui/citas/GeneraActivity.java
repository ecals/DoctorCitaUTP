package com.cals.doctorcita.ui.citas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.cals.doctorcita.R;
import com.cals.doctorcita.entidad.Globales;
import com.cals.doctorcita.ui.login.LoginActivity;

public class GeneraActivity extends AppCompatActivity {

    //static String usuario, nombre, nivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genera);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /*
        final Globales objvalor=(Globales)getActivity().getApplicationContext();
        objvalor.setFlg_fragmento("2");
        */



        final Globales objvalor=(Globales)getApplication().getApplicationContext();

        if(objvalor.getFlg_fragmento()=="1")
        {
            MuestraFragmento1();
        }else
        {
            MuestraFragmento2();
        }





    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    private void MuestraFragmento1()
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, GeneraFragmento.newInstance(getIntent().getExtras()));
        ft.commit();

    }


    private void MuestraFragmento2()
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment, GrabaCitaFragmento.newInstance(getIntent().getExtras()));
        ft.commit();

    }


}
