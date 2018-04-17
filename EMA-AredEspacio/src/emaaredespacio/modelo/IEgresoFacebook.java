/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package emaaredespacio.modelo;

import java.util.List;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 15/04/2018
 * @time 02:55:26 PM
 */
public interface IEgresoFacebook {
    public boolean registrarEgreso(EgresoFacebook egreso);
    public boolean editarEgresoFacebook(EgresoFacebook egreso);
    public boolean[] validarCampos(EgresoFacebook egreso);
    public List<EgresoFacebook> buscarEgresosDeFacebook(String creador,boolean estaActiva);
}
