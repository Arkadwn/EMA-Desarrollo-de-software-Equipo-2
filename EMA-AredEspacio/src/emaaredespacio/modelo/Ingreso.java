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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private String fecha;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

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

    public Ingreso(Integer pagoColaboradorID, Integer pagoRentaID, Double monto, Boolean recibo, String fecha) {
        this.pagoColaboradorID = pagoColaboradorID;
        this.pagoRentaID = pagoRentaID;
        this.monto = monto;
        this.recibo = recibo;
        this.fecha = fecha;
    }

    public Ingreso(Integer idIngreso, Integer pagoColaboradorID, Integer pagoRentaID, Double monto, Boolean recibo, String fecha) {
        this.idIngreso = idIngreso;
        this.pagoColaboradorID = pagoColaboradorID;
        this.pagoRentaID = pagoRentaID;
        this.monto = monto;
        this.recibo = recibo;
        this.fecha = fecha;
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
            Ingreso ingreso = new Ingreso(ingresos.getIdIngreso(), ingresos.getPagoColaboradorID(), ingresos.getPagoRentaID(), ingresos.getMonto(), ingresos.getRecibo(), ingresos.getFecha());
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
            ingresoGuardar.setFecha(ingresoNuevo.getFecha());

            controlador.create(ingresoGuardar);

            resultado = true;
        } catch (Exception ex) {
            System.out.println("Excepcion en el metodo de guardar registro en la clase Ingreso: " + ex.getMessage());
        }

        return resultado;
    }

    @Override
    public Ingreso buscarUltimoPagoColaborador(int idColaborador) {
        Ingresos ingresos = null;
        Ingreso ingreso = null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        IngresosJpaController controlador = new IngresosJpaController(entityManagerFactory);
        ingresos = controlador.buscarIngresoDeColaborador(idColaborador);
        if(ingresos != null){
            ingreso = new Ingreso();
            ingreso.setIdIngreso(ingresos.getIdIngreso());
            ingreso.setMonto(ingresos.getMonto());
            ingreso.setFecha(ingresos.getFecha());
            ingreso.setPagoColaboradorID(ingresos.getPagoColaboradorID());
        }
        return ingreso;
    }

    @Override
    public List<Ingreso> buscarPagosPorNombre(String nombre,int tipo) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        IngresosJpaController controlador = new IngresosJpaController(entityManagerFactory);
        List<Ingresos> listaResult = new ArrayList<>();
        List<Ingreso> listaReturn = new ArrayList<>();
        if(tipo==0){
            List<Cliente> listaClientes = new Cliente().buscarClienteRelacionado(nombre);
            for(Cliente cliente : listaClientes){
                List<Renta> rentas = new Renta().cargarRentas(cliente);
                for(Renta renta : rentas){
                    List<Ingresos> ingresosRentas = controlador.buscarIngresoPorTipo(renta.getId(), tipo);
                    for(Ingresos ingreso : ingresosRentas){
                        listaResult.add(ingreso);
                    }
                }
            }
        }else{
            List<Colaborador> listaColaboradores = new Colaborador().buscarColaborador(nombre);
            for(Colaborador colaborador : listaColaboradores){
                List<Ingresos> ingresosColaborador = controlador.buscarIngresoPorTipo(colaborador.getIdColaborador(), tipo);
                for(Ingresos ingreso : ingresosColaborador){
                    listaResult.add(ingreso);
                }
            }
        }
        Ingreso ingresoInd;
        for(Ingresos ingreso : listaResult){
            ingresoInd = new Ingreso();
            ingresoInd.setIdIngreso(ingreso.getIdIngreso());
            ingresoInd.setMonto(ingreso.getMonto());
            ingresoInd.setFecha(ingreso.getFecha());
            ingresoInd.setPagoColaboradorID(ingreso.getPagoColaboradorID());
            ingresoInd.setPagoRentaID(ingreso.getPagoRentaID());
            ingresoInd.setRecibo(ingreso.getRecibo());
            listaReturn.add(ingresoInd);
        }
        return listaReturn;
    }

    @Override
    public boolean ModificarRegistro(Ingreso ingreso) {
        boolean modificado = false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        IngresosJpaController controlador = new IngresosJpaController(entityManagerFactory);
        Ingresos ingresoGuardar = new Ingresos();
        ingresoGuardar.setIdIngreso(ingreso.getIdIngreso());
        ingresoGuardar.setMonto(ingreso.getMonto());
        ingresoGuardar.setPagoColaboradorID(ingreso.getPagoColaboradorID());
        ingresoGuardar.setPagoRentaID(ingreso.getPagoRentaID());
        ingresoGuardar.setRecibo(ingreso.getRecibo());
        ingresoGuardar.setFecha(ingreso.getFecha());
        try {
            if(controlador.edit(ingresoGuardar)){
                modificado = true;
            }
        } catch (Exception ex) {
            Logger.getLogger(Ingreso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return modificado;
    }


}
