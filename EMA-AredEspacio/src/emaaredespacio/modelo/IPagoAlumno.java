/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import java.util.List;

/**
 *
 * @author enriq
 */
public interface IPagoAlumno {
    public boolean registrarPago(PagoAlumno pago);
    public boolean editarPago(PagoAlumno pago);
    public List<PagoAlumno> cargarListaPagos();
    public List<PagoAlumno> cargarListaPagosDeAlumnosDeGrupo(int matricula,int idGrupo);
    public PagoAlumno buscarUltimoPago();
    public List<PagoAlumno> buscarPagosVencidos(Colaborador colaborador);
}
