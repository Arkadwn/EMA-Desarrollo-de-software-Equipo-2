/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import java.util.List;

/**
 *
 * @author Adrian Bustamante Zarate
 */
public interface IIngreso {
    public List<Ingreso> cargarIngresos();
    public boolean guardarRegistro(Ingreso ingresoNuevo);
    public Ingreso buscarUltimoPagoColaborador(int idColaborador);
    public List<Ingreso> buscarPagosPorNombre(String nombre, int tipo);
    public boolean ModificarRegistro(Ingreso ingreso);
}
