/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import emaaredespacio.persistencia.controladores.ClientesJpaController;
import emaaredespacio.persistencia.controladores.RentasJpaController;
import emaaredespacio.persistencia.controladores.exceptions.NonexistentEntityException;
import emaaredespacio.persistencia.entidad.Clientes;
import emaaredespacio.persistencia.entidad.Rentas;
import emaaredespacio.utilerias.ReciboPago;
import java.util.ArrayList;
import java.util.Calendar;
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
    private Calendar fecha;
    private int horaInicio;
    private int horaFin;
    private Cliente cliente;
    private boolean estado;
    private boolean pagoRealizado;

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isPagoRealizado() {
        return pagoRealizado;
    }

    public void setPagoRealizado(boolean pagoRealizado) {
        this.pagoRealizado = pagoRealizado;
    }

    public Renta(int id, int monto, Calendar fecha, int horaInicio, int horaFin, Cliente cliente, boolean estado) {
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

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
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
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
            banderaGuardado = new RentasJpaController(entityManagerFactory).cancelarRenta(idRenta);
            entityManagerFactory.close();
        } catch (NonexistentEntityException ex) {
            System.out.println("Error en metodo cancelarRenta de entidad Renta: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Renta inexistente");
            banderaGuardado = false;
        }
        return banderaGuardado;
    }

    @Override
    public Renta cargarRenta(String id) {
        Renta rentaCargada = new Renta();
        if (!"".equals(id)) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
            Rentas renta = new RentasJpaController(entityManagerFactory).buscarRenta(Integer.parseInt(id));

            rentaCargada.setCliente(new Cliente(String.valueOf(renta.getIdCliente().getIdCliente())));
            rentaCargada.setEstado(true);
            Calendar fechaCalendar = Calendar.getInstance();
            fechaCalendar.set(Integer.parseInt(renta.getFecha().split("/")[2]), Integer.parseInt(renta.getFecha().split("/")[1]), Integer.parseInt(renta.getFecha().split("/")[0]));
            //Date fechaRecibida = new Date(Integer.parseInt(renta.getFecha().split("/")[2]) - 1900, Integer.parseInt(renta.getFecha().split("/")[1]), Integer.parseInt(renta.getFecha().split("/")[0]));
            rentaCargada.setFecha(fechaCalendar);
            rentaCargada.setHoraFin(renta.getHoraFin());
            rentaCargada.setHoraInicio(renta.getHoraIni());
            rentaCargada.setId(Integer.parseInt(id));
            rentaCargada.setMonto(renta.getMonto());
            entityManagerFactory.close();
        } else {
            rentaCargada = null;
        }
        return rentaCargada;
    }

    @Override
    public List<Renta> cargarRentas() {
        List<Renta> rentasCargadas = null;
        try {
            rentasCargadas = new ArrayList<>();
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
            List<Rentas> rentas = new RentasJpaController(entityManagerFactory).buscarTodasRentas();
            for (Rentas renta : rentas) {
                Renta rentaCargada = new Renta();
                if (renta.getEstado()) {
                    rentaCargada.setCliente(new Cliente(String.valueOf(renta.getIdCliente().getIdCliente())));
                    rentaCargada.setEstado(true);
                    rentaCargada.setPagoRealizado(renta.getPagoRealizado());
                    Calendar fechaCalendar = Calendar.getInstance();
                    fechaCalendar.set(Integer.parseInt(renta.getFecha().split("/")[2]), Integer.parseInt(renta.getFecha().split("/")[1]), Integer.parseInt(renta.getFecha().split("/")[0]));
                    rentaCargada.setFecha(fechaCalendar);
                    rentaCargada.setHoraFin(renta.getHoraFin());
                    rentaCargada.setHoraInicio(renta.getHoraIni());
                    rentaCargada.setId(renta.getIdRenta());
                    rentaCargada.setMonto(renta.getMonto());
                    rentasCargadas.add(rentaCargada);
                }
            }
            entityManagerFactory.close();
        } catch (Exception ex) {
            System.out.println("Excepcion al cargar rentas");
        }

        return rentasCargadas;
    }

    @Override
    public List<Renta> cargarRentas(Cliente cliente) {
        List<Renta> rentasCliente = new ArrayList<>();
        if (cliente == null) {
            rentasCliente = null;
        } else {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
            List<Rentas> rentas = new RentasJpaController(entityManagerFactory).buscarRentasSegunCliente(Integer.parseInt(cliente.getId()));
            for (Rentas renta : rentas) {
                Renta rentaCargada = new Renta();

                if (renta.getEstado()) {
                    rentaCargada.setCliente(new Cliente(String.valueOf(renta.getIdCliente().getIdCliente())));
                    rentaCargada.setEstado(true);
                    rentaCargada.setPagoRealizado(renta.getPagoRealizado());
                    Calendar fechaCalendar = Calendar.getInstance();
                    fechaCalendar.set(Integer.parseInt(renta.getFecha().split("/")[2]), Integer.parseInt(renta.getFecha().split("/")[1]), Integer.parseInt(renta.getFecha().split("/")[0]));
                    rentaCargada.setFecha(fechaCalendar);
                    rentaCargada.setHoraFin(renta.getHoraFin());
                    rentaCargada.setHoraInicio(renta.getHoraIni());
                    rentaCargada.setId(renta.getIdRenta());
                    rentaCargada.setMonto(renta.getMonto());
                    rentasCliente.add(rentaCargada);
                }
            }
            entityManagerFactory.close();
        }
        return rentasCliente;
    }

    @Override
    public boolean guardarCambios(Renta renta) {
        boolean banderaCambiosGuardados = false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        try {
            Rentas rentaModif = new Rentas();
            rentaModif.setEstado(renta.getEstado());
            rentaModif.setIdRenta(renta.getId());
            rentaModif.setPagoRealizado(renta.getEstado());
            rentaModif.setFecha(renta.getFecha().get(Calendar.DAY_OF_MONTH) + "/" + renta.getFecha().get(Calendar.MONTH) + "/" + renta.getFecha().get(Calendar.YEAR));
            rentaModif.setHoraFin(renta.getHoraFin());
            rentaModif.setHoraIni(renta.getHoraInicio());
            rentaModif.setMonto(renta.getMonto());

            banderaCambiosGuardados = new RentasJpaController(entityManagerFactory).modificarRenta(rentaModif);
        } catch (Exception ex) {
            System.out.println("La renta viene vacia: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            entityManagerFactory.close();
        }
        return banderaCambiosGuardados;
    }

    @Override
    public boolean guardarNuevaRenta(Renta renta) {
        boolean banderaGuardarNuevaRenta = false;
        if (renta == null) {
            banderaGuardarNuevaRenta = false;
        } else {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
            Clientes cliente = new ClientesJpaController(entityManagerFactory).findClientes(Integer.parseInt(renta.getCliente().getId()));

            Rentas rentaModif = new Rentas();
            rentaModif.setFecha(renta.getFecha().get(Calendar.DAY_OF_MONTH) + "/" + renta.getFecha().get(Calendar.MONTH) + "/" + renta.getFecha().get(Calendar.YEAR));
            rentaModif.setHoraFin(renta.getHoraFin());
            rentaModif.setHoraIni(renta.getHoraInicio());
            rentaModif.setMonto(renta.getMonto());

            banderaGuardarNuevaRenta = new RentasJpaController(entityManagerFactory).crearRenta(rentaModif, cliente);
            entityManagerFactory.close();
        }
        return banderaGuardarNuevaRenta;
    }

    public Renta() {
    }

    @Override
    public String toString() {
        return "Renta de un monto de $" + this.monto + " de " + ReciboPago.mapeoHoras(this.horaInicio)
                + " a las " + ReciboPago.mapeoHoras(this.horaFin) + " el dia " + this.fecha.get(Calendar.DAY_OF_MONTH) + "/"
                + (this.fecha.get(Calendar.MONTH) + 1) + "/" + this.fecha.get(Calendar.YEAR);
    }
}
