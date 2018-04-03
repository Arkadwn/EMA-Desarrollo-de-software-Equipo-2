/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import emaaredespacio.persistencia.controladores.ClientesJpaController;
import emaaredespacio.persistencia.entidad.Clientes;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Adrián Bustamante Zarate
 * @date 31/03/2018
 * @time 05:41:21 PM
 */
public class Cliente implements ICliente {

    private String nombre;
    private String id;
    private String telefono;
    private String correoElectronico;

    public Cliente(String nombre, String id, String telefono, String correoElectronico) {
        this.nombre = nombre;

        this.id = id;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
    }

    public Cliente() {
        
    }

    private Cliente(String nombre, String idCliente) {
        this.id = idCliente;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    @Override
    public boolean guardarDatos(Cliente cliente) {
        boolean banderGuardoExito = false;

        return banderGuardoExito;
    }
    
    public Cliente(String id){
        this.id = id;
    }
    
    @Override
    public List<Cliente> buscarClienteRelacionados(String palabraClave) {
        List<Cliente> resultadosBusqueda = new ArrayList<>();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        ClientesJpaController controlador = new ClientesJpaController(entityManagerFactory);
        List<Clientes> clientes = controlador.buscarClientesRelacionados(palabraClave);
        for (Clientes cliente : clientes) {
            if (!"0".equals(cliente.getEstado())) {
                Cliente clienteDom = new Cliente(cliente.getNombre(), String.valueOf(cliente.getIdCliente()));
                resultadosBusqueda.add(clienteDom);
            }
        }
        return resultadosBusqueda;
    }

}
