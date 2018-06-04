/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import java.util.List;

/**
 *
 * @author Jesús Enrique Flores Nestozo
 * @date 30/04/2018
 * @time 04:51:39 PM
 */
public interface IPromocion {
    
    /**
     * Guarda una promoción nueva para un colaborador
     * @param promocion creada
     * @return True si se pudo guardar la promoción, False si no se pudo guardar
     */
    public boolean crearPromocion(Promocion promocion);
    
    /**
     * Busca las promociones pertenecientes a un colaborador
     * @param idColaborador al cual pertenecen las promociones
     * @return Lista de promociones del colaborador
     */
    public List<Promocion> buscarPromocion(int idColaborador);
    
    /**
     * Guarda modificaciones realizadas a una promoción
     * @param promocion modificada
     * @return True si se guardaron los cambios, False si no se guardaron los cambios
     */
    public boolean modificarPromocion(Promocion promocion);
}
