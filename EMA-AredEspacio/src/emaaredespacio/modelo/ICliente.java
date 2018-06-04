package emaaredespacio.modelo;

import emaaredespacio.persistencia.entidad.Clientes;
import java.util.List;

/**
 *
 * @author Jesús Enrique Flores Nestozo
 * @date 3/04/2018
 * @time 09:43:32 PM
 */
public interface ICliente {
    
    /**
     * Guarda los datos modificados de un cliente en la base de datos.
     * 
     * @param cliente a guardar.
     * @return True si se realizó el guardado correctamente, False si no se guardó.
     */
    public boolean guardarDatos(Cliente cliente);
    
    /**
     * Busca los clientes por nombre
     * 
     * @param nombre del cliente
     * @return Lista de clientes con nombre parecido al ingresado.
     */
    public List<Cliente> buscarClienteRelacionado(String nombre);
    
    /**
     * Guarda un nuevo Cliente en la base de datos.
     * 
     * @param cliente a guardar.
     * @return True si se realizó el guardado correctamente, False si no se guardó.
     */
    public boolean guardarCliente(Cliente cliente);
    
    /**
     * Busca un cliente por su nombre
     * 
     * @param nombre del cliente
     * @return Lista de clientes con nombre similar.
     */
    public List<Cliente> buscarCliente(String nombre);
    
    /**
     * Valida que el correo ingresado sea un correo con formato válido.
     * 
     * @param correo del cliente
     * @return True si es un correo válido, False si no lo es.
     */
    public boolean validarFormatoCorreo(String correo);
    
    /**
     * Busca todos los clientes existentes en la base de datos
     * 
     * @return Lista de todos los clientes.
     */
    public List<Cliente> buscarClientes();
}
