/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.persistencia.controladores;

import emaaredespacio.modelo.Cliente;
import emaaredespacio.persistencia.entidad.Clientes;
import java.util.List;

/**
 *
 * @author enriq
 */
public interface IControladorCliente {
    public boolean guardarCliente(Clientes alumno);
    public boolean editarCliente(Clientes alumno);
    public List<Clientes> buscarClientesRelacionados(String palabraClave);
}
