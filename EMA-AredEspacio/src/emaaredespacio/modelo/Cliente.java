/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import emaaredespacio.persistencia.controladores.ClientesJpaController;
import emaaredespacio.persistencia.controladores.IControladorCliente;
import emaaredespacio.persistencia.entidad.Clientes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author enriq
 */
public class Cliente implements ICliente {

    private String nombre;
    private String imagenPerfil;
    private String direccion;
    private String telefono;
    private String estado;
    private String correoElectronico;
    private String id;
    private static final String PATRON_CORREO = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private Cliente(String nombre, String idCliente) {
        this.id = idCliente;
        this.nombre = nombre;
    }

    public Cliente(String id) {
        this.id = id;
    }

    public Cliente() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correo) {
        this.correoElectronico = correo;
    }

    @Override
    public boolean guardarDatos(Cliente cliente) {
        boolean guardado = false;
        if (cliente.getNombre().length() > 0 && cliente.getNombre().length() <= 400 && cliente.getDireccion().length() > 0 && cliente.getDireccion().length() <= 50 && cliente.getImagenPerfil() != null && cliente.getTelefono().length() == 10 && validarFormatoCorreo(cliente.getCorreoElectronico())) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
            ClientesJpaController controlador = new ClientesJpaController(entityManagerFactory);

            Clientes nuevoCliente = new Clientes();
            nuevoCliente.setIdCliente(Integer.parseInt(cliente.getId()));
            nuevoCliente.setNombre(cliente.getNombre());
            nuevoCliente.setEstado(cliente.getEstado());
            nuevoCliente.setTelefono(cliente.getTelefono());
            nuevoCliente.setCorreo(cliente.getCorreoElectronico());
            nuevoCliente.setImagen(cliente.getImagenPerfil());
            nuevoCliente.setDireccion(cliente.getDireccion());

            try {
                guardado = controlador.edit(nuevoCliente);
            } catch (Exception ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return guardado;
    }

    @Override
    public List<Cliente> buscarClienteRelacionado(String nombre) {
        List<Cliente> resultadosBusqueda = new ArrayList<>();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        ClientesJpaController controlador = new ClientesJpaController(entityManagerFactory);
        List<Clientes> clientes = controlador.buscarClientesRelacionados(nombre);
        for (Clientes cliente : clientes) {
            if (!"0".equals(cliente.getEstado())) {
                Cliente clienteDom = new Cliente(cliente.getNombre(), String.valueOf(cliente.getIdCliente()));
                resultadosBusqueda.add(clienteDom);
            }
        }
        return resultadosBusqueda;

    }

    public List<Cliente> buscarCliente(String nombre) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        ClientesJpaController controlador = new ClientesJpaController(entityManagerFactory);
        List<Cliente> clientes = null;

        List<Clientes> lista = controlador.buscarCliente(nombre);

        clientes = convertirLista(lista);

        return clientes;
    }

    public Cliente obtenerClienteID(String idCliente) {
        Cliente cliente = new Cliente();
        if (!"".equals(idCliente) && idCliente != null) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
            ClientesJpaController controlador = new ClientesJpaController(entityManagerFactory);
            Clientes clienteObtenido = controlador.findClientes(Integer.parseInt(idCliente));
            cliente.setCorreoElectronico(clienteObtenido.getCorreo());
            cliente.setDireccion(clienteObtenido.getDireccion());
            cliente.setEstado(clienteObtenido.getEstado());
            cliente.setId(idCliente);
            cliente.setImagenPerfil(clienteObtenido.getImagen());
            cliente.setNombre(clienteObtenido.getNombre());
            cliente.setTelefono(clienteObtenido.getTelefono());
        } else {
            cliente = null;
        }
        return cliente;
    }

    private List<Cliente> convertirLista(List<Clientes> lista) {
        List<Cliente> clientes = new ArrayList();

        for (Clientes cliente : lista) {
            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setId(String.valueOf(cliente.getIdCliente()));
            nuevoCliente.setNombre(cliente.getNombre());
            nuevoCliente.setCorreoElectronico(cliente.getCorreo());
            nuevoCliente.setDireccion(cliente.getDireccion());
            nuevoCliente.setEstado(cliente.getEstado());
            nuevoCliente.setImagenPerfil(cliente.getImagen());
            nuevoCliente.setTelefono(cliente.getTelefono());
            clientes.add(nuevoCliente);
        }

        return clientes;
    }

    @Override
    public boolean guardarCliente(Cliente cliente) {
        boolean guardado = false;
        if (cliente.getNombre() != null && cliente.getTelefono() != null && cliente.getDireccion() != null && cliente.getImagenPerfil() != null && cliente.getCorreoElectronico() != null) {
            if (cliente.getNombre().length() > 0 && cliente.getNombre().length() <= 400 && cliente.getDireccion().length() > 0 && cliente.getDireccion().length() <= 50 && cliente.getImagenPerfil() != null && cliente.getTelefono().length() == 10 && validarFormatoCorreo(cliente.getCorreoElectronico())) {
                EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
                ClientesJpaController controlador = new ClientesJpaController(entityManagerFactory);
                Clientes nuevoCliente = new Clientes();
                nuevoCliente.setNombre(cliente.getNombre());
                nuevoCliente.setDireccion(cliente.getDireccion());
                nuevoCliente.setCorreo(cliente.getCorreoElectronico());
                nuevoCliente.setImagen(cliente.getImagenPerfil());
                nuevoCliente.setTelefono(cliente.getTelefono());
                nuevoCliente.setEstado("A");
                if (controlador.create(nuevoCliente)) {
                    guardado = true;
                }
            }
        }

        return guardado;
    }

    @Override
    public boolean validarFormatoCorreo(String email) {
        Pattern patron = Pattern.compile(PATRON_CORREO);
        Matcher concordancia = patron.matcher(email);
        return concordancia.matches();
    }

}
