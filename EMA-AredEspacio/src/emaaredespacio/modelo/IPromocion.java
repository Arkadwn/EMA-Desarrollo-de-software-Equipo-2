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
public interface IPromocion {
    public boolean crearPromocion(Promocion promocion);
    public List<Promocion> buscarPromocion(int idColaborador);
    public boolean darDeBajaPromocion(Promocion promocion);
    public boolean modificarPromocion(Promocion promocion);
}
