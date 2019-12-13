package com.cals.doctorcita.entidad;

import android.app.Application;

public class Globales extends Application {

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    private  String valor;

    private  String g_idEspecialidad;
    private  String g_idMedico;

    public String getG_idMedicoUsuario() {
        return g_idMedicoUsuario;
    }

    public void setG_idMedicoUsuario(String g_idMedicoUsuario) {
        this.g_idMedicoUsuario = g_idMedicoUsuario;
    }

    private  String g_idMedicoUsuario;

    public String getG_idPacienteUsuario() {
        return g_idPacienteUsuario;
    }

    public void setG_idPacienteUsuario(String g_idPacienteUsuario) {
        this.g_idPacienteUsuario = g_idPacienteUsuario;
    }

    private  String g_idPacienteUsuario;


    public String getG_idPaciente() {
        return g_idPaciente;
    }

    public void setG_idPaciente(String g_idPaciente) {
        this.g_idPaciente = g_idPaciente;
    }

    private  String g_idPaciente;

    public String getG_idEspecialidad() {
        return g_idEspecialidad;
    }

    public void setG_idEspecialidad(String g_idEspecialidad) {
        this.g_idEspecialidad = g_idEspecialidad;
    }

    public String getG_idMedico() {
        return g_idMedico;
    }

    public void setG_idMedico(String g_idMedico) {
        this.g_idMedico = g_idMedico;
    }

    public String getG_idCita() {
        return g_idCita;
    }

    public void setG_idCita(String g_idCita) {
        this.g_idCita = g_idCita;
    }

    private  String g_idCita;


    public String getFlg_fragmento() {
        return flg_fragmento;
    }

    public void setFlg_fragmento(String flg_fragmento) {
        this.flg_fragmento = flg_fragmento;
    }

    private  String flg_fragmento;

    public String getG_nivelUsuario() {
        return g_nivelUsuario;
    }

    public void setG_nivelUsuario(String g_nivelUsuario) {
        this.g_nivelUsuario = g_nivelUsuario;
    }

    private  String g_nivelUsuario;


}
