/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import java.util.List;

/**
 *
 * @author Adrián Bustamante Zarate
 */
public interface ICliente {
    public boolean guardarDatos(Cliente cliente);
    public List<Cliente> buscarClienteRelacionados(String palabraClave);
}
