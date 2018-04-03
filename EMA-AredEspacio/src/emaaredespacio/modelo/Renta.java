/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import emaaredespacio.persistencia.controladores.ClientesJpaController;
import emaaredespacio.persistencia.controladores.RentasJpaController;
import emaaredespacio.persistencia.controladorentidad.exceptions.NonexistentEntityException;
import emaaredespacio.persistencia.entidad.Clientes;
import emaaredespacio.persistencia.entidad.Rentas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Adrián Bustamante Zarate
 * @date 22/03/2018
 * @time 05:39:38 PM
 */
public class Renta implements IRenta {

    private int id;
    private int monto;
    private String fecha;
    private int horaInicio;
    private int horaFin;
    private Cliente cliente;
    private boolean estado;

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Renta(int id, int monto, String fecha, int horaInicio, int horaFin, Cliente cliente, boolean estado) {
        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.cliente = cliente;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(int horaFin) {
        this.horaFin = horaFin;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean cancelarRenta(int idRenta) {
        boolean banderaGuardado = false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        try {
            banderaGuardado = new RentasJpaController(entityManagerFactory).cancelarRenta(idRenta);
        } catch (NonexistentEntityException ex) {
            System.out.println("Error en cancelar renta dom: " + ex.getMessage());
        }finally{
            entityManagerFactory.close();
        }

        return banderaGuardado;
    }

    @Override
    public Renta cargarRenta(String id) {
        Renta rentaCargada = new Renta();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        Rentas renta = new RentasJpaController(entityManagerFactory).buscarRenta(Integer.parseInt(id));

        rentaCargada.setCliente(new Cliente(String.valueOf(renta.getIdCliente().getIdCliente())));
        rentaCargada.setEstado(true);
        rentaCargada.setFecha(renta.getFecha());
        rentaCargada.setHoraFin(renta.getHoraFin());
        rentaCargada.setHoraInicio(renta.getHoraIni());
        rentaCargada.setId(Integer.parseInt(id));
        rentaCargada.setMonto(renta.getMonto());
        entityManagerFactory.close();
        return rentaCargada;
    }

    @Override
    public List<Renta> cargarRentas() {
        List<Renta> rentasCargadas = new ArrayList<>();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        List<Rentas> rentas = new RentasJpaController(entityManagerFactory).buscarTodasRentas();
        for (Rentas renta : rentas) {
            Renta rentaCargada = new Renta();
            if (renta.getEstado()) {
            rentaCargada.setCliente(new Cliente(String.valueOf(renta.getIdCliente().getIdCliente())));
            rentaCargada.setEstado(true);
            rentaCargada.setFecha(renta.getFecha());
            rentaCargada.setHoraFin(renta.getHoraFin());
            rentaCargada.setHoraInicio(renta.getHoraIni());
            rentaCargada.setId(renta.getIdRenta());
            rentaCargada.setMonto(renta.getMonto());
            rentasCargadas.add(rentaCargada);
            }
        }
        entityManagerFactory.close();
        return rentasCargadas;
    }

    @Override
    public List<Renta> cargarRentas(Cliente cliente) {
        List<Renta> rentasCliente = new ArrayList<>();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        List<Rentas> rentas = new RentasJpaController(entityManagerFactory).buscarRentasSegunCliente(Integer.parseInt(cliente.getId()));
        for (Rentas renta : rentas) {
            Renta rentaCargada = new Renta();
            
            if (renta.getEstado()) {
                rentaCargada.setCliente(new Cliente(String.valueOf(renta.getIdCliente().getIdCliente())));
                rentaCargada.setEstado(true);
                rentaCargada.setFecha(renta.getFecha());
                rentaCargada.setHoraFin(renta.getHoraFin());
                rentaCargada.setHoraInicio(renta.getHoraIni());
                rentaCargada.setId(renta.getIdRenta());
                rentaCargada.setMonto(renta.getMonto());
                rentasCliente.add(rentaCargada);
            }
        }
        entityManagerFactory.close();
        return rentasCliente;
    }

    @Override
    public boolean guardarCambios(Renta renta) {
        boolean banderaCambiosGuardados = false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        try {
            System.out.println(renta);
            banderaCambiosGuardados = new RentasJpaController(entityManagerFactory).modificarRenta(renta);
            
        } catch (NonexistentEntityException ex) {
            System.out.println("Error al guardar cambios dom: "+ex.getMessage());
        }
        entityManagerFactory.close();
        return banderaCambiosGuardados;
    }

    @Override
    public boolean guardarNuevaRenta(Renta renta) {
        boolean banderaGuardarNuevaRenta = false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        Clientes cliente = new ClientesJpaController(entityManagerFactory).findClientes(Integer.parseInt(renta.getCliente().getId()));
        banderaGuardarNuevaRenta = new RentasJpaController(entityManagerFactory).crearRenta(renta, cliente);
        entityManagerFactory.close();
        return banderaGuardarNuevaRenta;
    }

    public Renta() {
    }
}
