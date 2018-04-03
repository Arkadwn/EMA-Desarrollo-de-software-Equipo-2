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
public interface IRenta {
    public boolean cancelarRenta(int idRenta);
    public Renta cargarRenta(String id);
    public List<Renta> cargarRentas();
    public List<Renta> cargarRentas(Cliente cliente);
    public boolean guardarCambios(Renta renta);
    public boolean guardarNuevaRenta(Renta renta);
}
