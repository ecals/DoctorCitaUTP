package com.cals.doctorcita;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.cals.doctorcita.adaptador.AdaptadorMenuTab;
import com.cals.doctorcita.adaptador.UtilitarioManager;
import com.cals.doctorcita.entidad.Globales;
import com.cals.doctorcita.ui.login.LoginActivity;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    private Toolbar cToolbar;
    private ViewPager cViewPager;
    private TabLayout cTabLayout;
    static String usuario, nombre, nivel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Captura de valores de Login
        nombre = (String) getIntent().getExtras().getSerializable("s_nombre");
        nivel = (String) getIntent().getExtras().getSerializable("s_nivel");

        //Tool Bar
        cToolbar = (Toolbar) findViewById(R.id.main_pagina_toolbar);
        setSupportActionBar(cToolbar);
        getSupportActionBar().setTitle("Citas App - "+nombre);

        //Tabs
        cTabLayout = findViewById(R.id.main_tabs);
        //Contenedor Fragmentos
        cViewPager = findViewById(R.id.main_tabs_pager);


        //Administrador Sistema
        if(nivel.equals("1"))
        {
            cTabLayout.addTab(cTabLayout.newTab().setText("ESPECIAL"));
            cTabLayout.addTab(cTabLayout.newTab().setText("MEDICOS"));
            cTabLayout.addTab(cTabLayout.newTab().setText("CITAS"));
            cTabLayout.addTab(cTabLayout.newTab().setText("USUARIO"));
        }
        //Paciente
        if(nivel.equals("2"))
        {
            cTabLayout.addTab(cTabLayout.newTab().setText("ESPECIALIDAD"));
            cTabLayout.addTab(cTabLayout.newTab().setText("MEDICOS"));
            cTabLayout.addTab(cTabLayout.newTab().setText("CITAS"));
        }
        //Medico
        if(nivel.equals("3"))
        {
            cTabLayout.addTab(cTabLayout.newTab().setText("CITAS"));
        }


        final Globales objvalor=(Globales) this.getApplicationContext();
        objvalor.setValor("sadfasdasd");





        //Adaptador para instanciar Fragmentos
        cTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final AdaptadorMenuTab adapter = new AdaptadorMenuTab(this,getSupportFragmentManager(),cTabLayout.getTabCount(),nivel);
        cViewPager.setAdapter(adapter);

        //Deslizarse en el tab menu
        cViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(cTabLayout));

        cTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                cViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.opciones_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.main_salir)
        {
            logout();
        }

        /*
        if (item.getItemId() == R.id.main_usuarios)
        {

        }
        */

        return true;
    }

    private void logout() {
        UtilitarioManager.getInstance(this).logout();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }


}
