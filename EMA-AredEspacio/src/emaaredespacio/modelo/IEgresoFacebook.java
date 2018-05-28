package emaaredespacio.modelo;

import java.util.List;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 15/04/2018
 * @time 02:55:26 PM
 */
public interface IEgresoFacebook {

    /**
     * Registra un egreso de facebook en la base de datos.
     * 
     * @param egreso Egreso a registrar.
     * @return true si fue registrado, false si no fue registrada.
     */
    public boolean registrarEgreso(EgresoFacebook egreso);

    /**
     * Edita un egreso ya registrado en la base de datos.
     * 
     * @param egreso Egreso modificado.
     * @return true si fue registrado, false si no fue registrada.
     */
    public boolean editarEgresoFacebook(EgresoFacebook egreso);

    /**
     * Valida los campos de un egreso de facebook de acuerdo a las restricciones
     * en la base de datos y a las reglas de negocio.
     * 
     * @param egreso egreso a validar los campos.
     * @return Arreglo de booleanos.
     */
    public boolean[] validarCampos(EgresoFacebook egreso);

    /**
     * Busca los egresos según su crear y estado de la publicidad.
     * 
     * @param creador Nombre del colaborador que creo la publicidad.
     * @param estaActiva Esta vigente la publicidad.
     * @return Lista de egresos del colaborador.
     */
    public List<EgresoFacebook> buscarEgresosDeFacebook(String creador,boolean estaActiva);
}
