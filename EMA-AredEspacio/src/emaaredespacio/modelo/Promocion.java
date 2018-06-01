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
    private boolean tipoDescuento;
    private String porcentajeDescuento;
    private int idColaborador;
    private int idPromocion;
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

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

    public boolean getTipoDescuento() {
        return tipoDescuento;
    }

    public void setTipoDescuento(boolean tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
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

    @Override
    public boolean crearPromocion(Promocion promocion) {
        boolean guardadoExitoso = false;
        if (promocion.getNombrePromocion() != null) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
            PromocionesJpaController controlador = new PromocionesJpaController(entityManagerFactory);
            Promociones nuevapromocion = new Promociones();
            Colaboradores colaborador = new Colaboradores();
            colaborador.setIdColaborador(promocion.getIdColaborador());
            nuevapromocion.setIdColaborador(colaborador);
            nuevapromocion.setNombrePromocion(promocion.getNombrePromocion());
            nuevapromocion.setEstado("A");
            if (promocion.tipoDescuento) {
                nuevapromocion.setTipoDescuento(1);
            } else {
                nuevapromocion.setTipoDescuento(0);
            }
            nuevapromocion.setPorcentajeDescuento(promocion.getPorcentajeDescuento());
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
            if (promocion.getTipoDescuento().equals(1)) {
                nuevaPromocion.setTipoDescuento(true);
            } else {
                nuevaPromocion.setTipoDescuento(false);
            }
            nuevaPromocion.setEstado(promocion.getEstado());
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
        if (promocion.getNombrePromocion() != null) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
            PromocionesJpaController controlador = new PromocionesJpaController(entityManagerFactory);
            Promociones nuevapromocion = new Promociones();
            Colaboradores colaborador = new Colaboradores();
            colaborador.setIdColaborador(promocion.getIdColaborador());
            nuevapromocion.setIdColaborador(colaborador);
            nuevapromocion.setNombrePromocion(promocion.getNombrePromocion());
            nuevapromocion.setEstado(promocion.getEstado());
            if (promocion.tipoDescuento) {
                nuevapromocion.setTipoDescuento(1);
            } else {
                nuevapromocion.setTipoDescuento(0);
            }
            nuevapromocion.setPorcentajeDescuento(promocion.getPorcentajeDescuento());
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
