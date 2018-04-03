/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import emaaredespacio.persistencia.entidad.Clientes;
import java.util.List;

/**
 *
 * @author enriq
 */
public interface ICliente {
    public boolean guardarDatos(Cliente cliente);
    public List<Cliente> buscarClienteRelacionado(String nombre);
    public boolean guardarCliente(Cliente cliente);
    public List<Cliente> buscarCliente(String nombre);
}
