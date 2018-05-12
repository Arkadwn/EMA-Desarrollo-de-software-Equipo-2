/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import emaaredespacio.persistencia.controladores.IngresosJpaController;
import emaaredespacio.persistencia.entidad.Ingresos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Adrian Bustamante Zarate
 */
public class Ingreso implements IIngreso {

    private Integer idIngreso;
    private Integer pagoColaboradorID;
    private Integer pagoRentaID;
    private Double monto;
    private Boolean recibo;

    public Integer getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(Integer idIngreso) {
        this.idIngreso = idIngreso;
    }

    public Integer getPagoColaboradorID() {
        return pagoColaboradorID;
    }

    public void setPagoColaboradorID(Integer pagoColaboradorID) {
        this.pagoColaboradorID = pagoColaboradorID;
    }

    public Integer getPagoRentaID() {
        return pagoRentaID;
    }

    public void setPagoRentaID(Integer pagoRentaID) {
        this.pagoRentaID = pagoRentaID;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Boolean getRecibo() {
        return recibo;
    }

    public void setRecibo(Boolean recibo) {
        this.recibo = recibo;
    }

    public Ingreso(Integer pagoColaboradorID, Integer pagoRentaID, Double monto, Boolean recibo) {
        this.pagoColaboradorID = pagoColaboradorID;
        this.pagoRentaID = pagoRentaID;
        this.monto = monto;
        this.recibo = recibo;
    }

    public Ingreso(Integer idIngreso, Integer pagoColaboradorID, Integer pagoRentaID, Double monto, Boolean recibo) {
        this.idIngreso = idIngreso;
        this.pagoColaboradorID = pagoColaboradorID;
        this.pagoRentaID = pagoRentaID;
        this.monto = monto;
        this.recibo = recibo;
    }

    public Ingreso() {

    }

    @Override
    public List<Ingreso> cargarIngresos() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        IngresosJpaController controlador = new IngresosJpaController(entityManagerFactory);
        List<Ingresos> listaResult = controlador.findIngresosEntities();
        List<Ingreso> listaReturn = new ArrayList<>();
        for (Ingresos ingresos : listaResult) {
            Ingreso ingreso = new Ingreso(ingresos.getIdIngreso(), ingresos.getPagoColaboradorID(), ingresos.getPagoRentaID(), ingresos.getMonto(), ingresos.getRecibo());
            listaReturn.add(ingreso);
        }

        return listaReturn;
    }

    @Override
    public boolean guardarRegistro(Ingreso ingresoNuevo) {
        boolean resultado = false;
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
            IngresosJpaController controlador = new IngresosJpaController(entityManagerFactory);
            
            Ingresos ingresoGuardar = new Ingresos();
            ingresoGuardar.setMonto(ingresoNuevo.getMonto());
            ingresoGuardar.setPagoColaboradorID(ingresoNuevo.getPagoColaboradorID());
            ingresoGuardar.setPagoRentaID(ingresoNuevo.getPagoRentaID());
            ingresoGuardar.setRecibo(ingresoNuevo.getRecibo());
            
            controlador.create(ingresoGuardar);
            
            resultado = true;
        } catch (Exception ex) {
            System.out.println("Excepcion en el metodo de guardar registro en la clase Ingreso: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        return resultado;
    }

}
