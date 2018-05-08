/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import emaaredespacio.persistencia.controladores.GruposJpaController;
import emaaredespacio.persistencia.entidad.Colaboradores;
import emaaredespacio.persistencia.entidad.Grupos;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author enriq
 */
public class Grupo implements IGrupo {

    private int idGrupo = 0;
    private int idColaborador = 0;
    private String tipoDeBaile = "";
    private int cupo = 0;
    private String estado = "";
    private String dias = "";
    private String horas = "";
    private String horaInicio = "";
    private String horaFin = "";
    private String fechaInicio = "";
    private String fechaFin = "";
    private int horarioAsignado = 0;

    public int getHorarioAsignado() {
        return horarioAsignado;
    }

    public void setHorarioAsignado(int horarioAsignado) {
        this.horarioAsignado = horarioAsignado;
    }

   
    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }
    
    private int horario_asignado = 0;

    public int getHorario_asignado() {
        return horario_asignado;
    }

    public void setHorario_asignado(int horario_asignado) {
        this.horario_asignado = horario_asignado;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getTipoDeBaile() {
        return tipoDeBaile;
    }

    public void setTipoDeBaile(String tipoDeBaile) {
        this.tipoDeBaile = tipoDeBaile;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public List<Grupo> buscarGrupos() {
        List<Grupo> grupos = null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        GruposJpaController controlador = new GruposJpaController(entityManagerFactory);

        List<Grupos> lista = controlador.buscarGrupos();

        grupos = convertirLista(lista);

        return grupos;
    }

    @Override
    public Grupo buscarGrupoPorId(int id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        GruposJpaController controlador = new GruposJpaController(entityManagerFactory);
        List<Grupos> grupoBuscado = controlador.buscarGrupoPorId(id);
        List<Grupo> grupos = null;
        grupos = convertirLista(grupoBuscado);
        Grupo grup = new Grupo();
        grup = grupos.get(0);
        return grup;
    }

    private List<Grupo> convertirLista(List<Grupos> lista) {
        List<Grupo> grupos = new ArrayList();

        for (Grupos grupo : lista) {
            Grupo nuevoGrupo = new Grupo();
            Colaboradores nuevoC = new Colaboradores();
            nuevoC = grupo.getIdColaborador();
            nuevoGrupo.setIdColaborador(nuevoC.getIdColaborador());
            nuevoGrupo.setTipoDeBaile(grupo.getTipoDeBaile());
            nuevoGrupo.setCupo(grupo.getCupo());
            nuevoGrupo.setEstado(grupo.getEstado());
            nuevoGrupo.setIdGrupo(grupo.getIdGrupo());
            nuevoGrupo.setHorarioAsignado(grupo.getHorarioAsignado());
            nuevoGrupo.setHorario_asignado(grupo.getHorarioAsignado());
            grupos.add(nuevoGrupo);
        }

        return grupos;
    }

    @Override
    public boolean guardarCambios(Grupo grupo) {
        boolean guardado = false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        GruposJpaController controlador = new GruposJpaController(entityManagerFactory);
        if (grupo.getCupo() != 0 && grupo.getEstado() != null && grupo.getTipoDeBaile() != null) {
            Grupos nuevoGrupo = new Grupos();
            Colaboradores colaborador = new Colaboradores();
            colaborador.setIdColaborador(grupo.getIdColaborador());
            nuevoGrupo.setTipoDeBaile(grupo.getTipoDeBaile());
            nuevoGrupo.setEstado(grupo.getEstado());
            nuevoGrupo.setCupo(grupo.getCupo());
            nuevoGrupo.setIdGrupo(grupo.getIdGrupo());
            nuevoGrupo.setIdColaborador(colaborador);
            nuevoGrupo.setHorarioAsignado(grupo.getHorarioAsignado());
            nuevoGrupo.setHorarioAsignado(grupo.getHorario_asignado());
            try {
                guardado = controlador.edit(nuevoGrupo);
                guardado = true;
            } catch (Exception ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return guardado;
    }

    @Override
    public boolean guardarGrupo(Grupo grupo) {
        boolean guardado = false;
        if (grupo.getCupo() != 0 && grupo.getTipoDeBaile() != "" && grupo.getIdColaborador() != 0) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
            GruposJpaController controlador = new GruposJpaController(entityManagerFactory);
            Grupos nuevoGrupo = new Grupos();
            Colaboradores colaborador = new Colaboradores();
            colaborador.setIdColaborador(Integer.valueOf(grupo.getIdColaborador()));
            nuevoGrupo.setIdColaborador(colaborador);
            nuevoGrupo.setTipoDeBaile(grupo.getTipoDeBaile());
            nuevoGrupo.setCupo(grupo.getCupo());
            nuevoGrupo.setEstado("A");
            nuevoGrupo.setHorarioAsignado(0);
            if (controlador.create(nuevoGrupo)) {
                guardado = true;
            }
        }

        return guardado;
    }

    @Override
    public String toString(){
        return tipoDeBaile;
    }
}
