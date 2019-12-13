package com.cals.doctorcita.adaptador;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cals.doctorcita.ui.citas.CitasFragmento;
import com.cals.doctorcita.ui.especialidad.EspecialidadFragmento;
import com.cals.doctorcita.ui.medico.MedicoFragmento;
import com.cals.doctorcita.ui.usuario.UsuarioFragmento;

public class AdaptadorMenuTab extends FragmentPagerAdapter {

    Context context;
    int totalTabs;
    String nivelUsuario;

    public AdaptadorMenuTab(Context c, FragmentManager fm, int totalTabs, String nivel) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
        nivelUsuario = nivel;
    }



    @Override
    public Fragment getItem(int position) {

        //Administrador del Sistema
        if(nivelUsuario.equals("1"))
        {
            switch (position) {
                case 0:
                    EspecialidadFragmento especialidadFragmento = new EspecialidadFragmento();
                    return especialidadFragmento;
                case 1:
                    MedicoFragmento medicoFragmento = new MedicoFragmento();
                    return medicoFragmento;
                case 2:
                    CitasFragmento citasFragmento = new CitasFragmento();
                    return citasFragmento;
                case 3:
                    UsuarioFragmento usuarioFragmento = new UsuarioFragmento();
                    return usuarioFragmento;
                default:
                    return null;
            }
        }

        //Paciente
        if(nivelUsuario.equals("2"))
        {
            switch (position) {
                case 0:
                    EspecialidadFragmento especialidadFragmento = new EspecialidadFragmento();
                    return especialidadFragmento;
                case 1:
                    MedicoFragmento medicoFragmento = new MedicoFragmento();
                    return medicoFragmento;
                case 2:
                    CitasFragmento citasFragmento = new CitasFragmento();
                    return citasFragmento;
                default:
                    return null;
            }
        }

        //Medico
        if(nivelUsuario.equals("3"))
        {
            switch (position) {

                case 0:
                    CitasFragmento citasFragmento = new CitasFragmento();
                    return citasFragmento;

                default:
                    return null;
            }
        }

        return null;
    }



    @Override
    public int getCount() {
        return totalTabs;
    }




}