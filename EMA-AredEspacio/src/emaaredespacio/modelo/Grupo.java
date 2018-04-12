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
            grupos.add(nuevoGrupo);
        }

        return grupos;
    }

    @Override
    public boolean guardarCambios(Grupo grupo) {
        boolean guardado = false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        GruposJpaController controlador = new GruposJpaController(entityManagerFactory);

        Grupos nuevoGrupo = new Grupos();
        Colaboradores colaborador = new Colaboradores();
        colaborador.setIdColaborador(grupo.getIdColaborador());
        nuevoGrupo.setTipoDeBaile(grupo.getTipoDeBaile());
        nuevoGrupo.setEstado(grupo.getEstado());
        nuevoGrupo.setCupo(grupo.getCupo());
        nuevoGrupo.setIdGrupo(grupo.getIdGrupo());
        nuevoGrupo.setIdColaborador(colaborador);
        try {
            guardado = controlador.edit(nuevoGrupo);
        } catch (Exception ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
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

            if (controlador.create(nuevoGrupo)) {
                guardado = true;
            }
        }

        return guardado;
    }

}
