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
public interface IGrupo {
    public List<Grupo> buscarGrupos();
    public boolean guardarCambios(Grupo grupo);
    public boolean guardarGrupo(Grupo grupo);
}
