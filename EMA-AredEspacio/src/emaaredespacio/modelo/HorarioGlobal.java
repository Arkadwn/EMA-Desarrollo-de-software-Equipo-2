/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

/**
 *
 * @author Adrián Bustamante Zarate
 * @date 14/04/2018
 * @time 08:20:44 PM
 */
public class HorarioGlobal {

    String hora;
    String lunes;
    String martes;
    String miercoles;
    String jueves;
    String viernes;
    String sabado;
    String domingo;

    public HorarioGlobal(int id) {
        switch (id) {
            case 1:
                hora = "00:01-00:30";
                break;
            case 2:
                hora = "00:31-01:00";
                break;
            case 3:
                hora = "01:01-01:30";
                break;
            case 4:
                hora = "01:31-02:00";
                break;
            case 5:
                hora = "02:01-02:30";
                break;
            case 6:
                hora = "02:31-03:00";
                break;
            case 7:
                hora = "03:01-03:30";
                break;
            case 8:
                hora = "03:31-04:00";
                break;
            case 9:
                hora = "04:01-04:30";
                break;
            case 10:
                hora = "04:31-05:00";
                break;
            case 11:
                hora = "05:01-05:30";
                break;
            case 12:
                hora = "05:31-06:00";
                break;
            case 13:
                hora = "06:01-06:30";
                break;
            case 14:
                hora = "06:31-07:00";
                break;
            case 15:
                hora = "07:01-07:30";
                break;
            case 16:
                hora = "07:31-08:00";
                break;
            case 17:
                hora = "08:01-08:30";
                break;
            case 18:
                hora = "08:31-09:00";
                break;
            case 19:
                hora = "09:01-09:30";
                break;
            case 20:
                hora = "09:31-10:00";
                break;
            case 21:
                hora = "10:01-10:30";
                break;
            case 22:
                hora = "10:31-11:00";
                break;
            case 23:
                hora = "11:01-11:30";
                break;
            case 24:
                hora = "11:31-12:00";
                break;
            case 25:
                hora = "12:01-12:30";
                break;
            case 26:
                hora = "12:31-13:00";
                break;
            case 27:
                hora = "13:01-13:30";
                break;
            case 28:
                hora = "13:31-14:00";
                break;
            case 29:
                hora = "14:01-14:30";
                break;
            case 30:
                hora = "14:31-15:00";
                break;
            case 31:
                hora = "15:01-15:30";
                break;
            case 32:
                hora = "15:30-16:00";
                break;
            case 33:
                hora = "16:01-16:30";
                break;
            case 34:
                hora = "16:30-17:00";
                break;
            case 35:
                hora = "17:01-17:30";
                break;
            case 36:
                hora = "17:30-18:00";
                break;
            case 37:
                hora = "18:01-18:30";
                break;
            case 38:
                hora = "18:30-19:00";
                break;
            case 39:
                hora = "19:01-19:30";
                break;
            case 40:
                hora = "19:30-20:00";
                break;
            case 41:
                hora = "20:01-20:30";
                break;
            case 42:
                hora = "20:30-21:00";
                break;
            case 43:
                hora = "21:01-21:30";
                break;
            case 44:
                hora = "21:30-22:00";
                break;
            case 45:
                hora = "22:01-22:30";
                break;
            case 46:
                hora = "22:30-23:00";
                break;
            case 47:
                hora = "23:01-23:30";
                break;
            case 48:
                hora = "23:30-24:00";
                break;
        }
        lunes = "";
        martes = "";
        miercoles = "";
        jueves = "";
        viernes = "";
        sabado = "";
        domingo = "";
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLunes() {
        return lunes;
    }

    public void setLunes(String lunes) {
        this.lunes = lunes;
    }

    public String getMartes() {
        return martes;
    }

    public void setMartes(String martes) {
        this.martes = martes;
    }

    public String getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(String miercoles) {
        this.miercoles = miercoles;
    }

    public String getJueves() {
        return jueves;
    }

    public void setJueves(String jueves) {
        this.jueves = jueves;
    }

    public String getViernes() {
        return viernes;
    }

    public void setViernes(String viernes) {
        this.viernes = viernes;
    }

    public String getSabado() {
        return sabado;
    }

    public void setSabado(String sabado) {
        this.sabado = sabado;
    }

    public String getDomingo() {
        return domingo;
    }

    public void setDomingo(String domingo) {
        this.domingo = domingo;
    }

}
