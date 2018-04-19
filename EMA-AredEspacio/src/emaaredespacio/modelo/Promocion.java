/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import emaaredespacio.persistencia.controladores.PromocionesJpaController;
import emaaredespacio.persistencia.entidad.Colaboradores;
import emaaredespacio.persistencia.entidad.Promociones;
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
public class Promocion implements IPromocion {

    private String nombrePromocion;
    private boolean aplicaDescuento;
    private String porcentajeDescuento;
    private int idColaborador;
    private String fechaInicio;
    private String fechaFin;
    private int idPromocion;

    public int getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(int idPromocion) {
        this.idPromocion = idPromocion;
    }

    public String getNombrePromocion() {
        return nombrePromocion;
    }

    public void setNombrePromocion(String nombrePromocion) {
        this.nombrePromocion = nombrePromocion;
    }

    public boolean isAplicaDescuento() {
        return aplicaDescuento;
    }

    public void setAplicaDescuento(boolean aplicaDescuento) {
        this.aplicaDescuento = aplicaDescuento;
    }

    public String getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(String porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
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

    @Override
    public boolean crearPromocion(Promocion promocion) {
        boolean guardadoExitoso = false;
        if (promocion.getFechaInicio() != null && promocion.getFechaFin() != null && promocion.getNombrePromocion() != null) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
            PromocionesJpaController controlador = new PromocionesJpaController(entityManagerFactory);
            Promociones nuevapromocion = new Promociones();
            Colaboradores colaborador = new Colaboradores();
            colaborador.setIdColaborador(promocion.getIdColaborador());
            nuevapromocion.setIdColaborador(colaborador);
            nuevapromocion.setNombrePromocion(promocion.getNombrePromocion());
            if (promocion.aplicaDescuento) {
                nuevapromocion.setAplicaDescuento(1);
            } else {
                nuevapromocion.setAplicaDescuento(0);
            }
            nuevapromocion.setPorcentajeDescuento(promocion.getPorcentajeDescuento());
            nuevapromocion.setFechaIni(promocion.getFechaInicio());
            nuevapromocion.setFechaFin(promocion.getFechaFin());
            
            if (controlador.create(nuevapromocion)) {
                guardadoExitoso = true;
            }
        }

        return guardadoExitoso;
    }

    @Override
    public List<Promocion> buscarPromocion(int idColaborador) {
        List<Promocion> promociones = new ArrayList();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        PromocionesJpaController controlador = new PromocionesJpaController(entityManagerFactory);
        List<Promociones> listaPromociones = controlador.buscarPromociones(idColaborador);
        promociones = convertirLista(listaPromociones);
        return promociones;
    }

    public List<Promocion> convertirLista(List<Promociones> listaPromociones) {
        List<Promocion> promociones = new ArrayList();
        for (Promociones promocion : listaPromociones) {
            Promocion nuevaPromocion = new Promocion();
            nuevaPromocion.setNombrePromocion(promocion.getNombrePromocion());
            nuevaPromocion.setPorcentajeDescuento(promocion.getPorcentajeDescuento());
            nuevaPromocion.setFechaInicio(promocion.getFechaIni());
            nuevaPromocion.setFechaFin(promocion.getFechaFin());
            if (promocion.getAplicaDescuento() == 1) {
                nuevaPromocion.setAplicaDescuento(true);
            } else {
                nuevaPromocion.setAplicaDescuento(false);
            }
            nuevaPromocion.setIdPromocion(promocion.getIdPromocion());
            Colaborador colaborador = new Colaborador();
//            colaborador.setIdColaborador(promocion.getIdColaborador());
//            nuevaPromocion.setIdColaborador(colaborador);
            promociones.add(nuevaPromocion);
        }
        return promociones;
    }

    @Override
    public boolean modificarPromocion(Promocion promocion) {
        boolean modificada = false;
        if (promocion.getFechaInicio() != null && promocion.getFechaFin() != null && promocion.getNombrePromocion() != null) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
            PromocionesJpaController controlador = new PromocionesJpaController(entityManagerFactory);
            Promociones nuevapromocion = new Promociones();
            Colaboradores colaborador = new Colaboradores();
            colaborador.setIdColaborador(promocion.getIdColaborador());
            nuevapromocion.setNombrePromocion(promocion.getNombrePromocion());
            if (promocion.aplicaDescuento) {
                nuevapromocion.setAplicaDescuento(1);
            } else {
                nuevapromocion.setAplicaDescuento(0);
            }
            nuevapromocion.setPorcentajeDescuento(promocion.getPorcentajeDescuento());
            nuevapromocion.setFechaIni(promocion.getFechaInicio());
            nuevapromocion.setFechaFin(promocion.getFechaFin());
//            nuevapromocion.setIdColaborador(promocion.getIdColaborador());
            nuevapromocion.setIdPromocion(promocion.getIdPromocion());
            try {
                if (controlador.edit(nuevapromocion)) {
                    modificada = true;
                }
            } catch (Exception ex) {
                Logger.getLogger(Promocion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return modificada;
    }

}
