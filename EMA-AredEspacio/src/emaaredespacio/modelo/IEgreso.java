package emaaredespacio.modelo;

import java.util.ArrayList;

/**
 * 
 * @author Adrian Bustamante Zarate
 * @date 29/04/2018
 * @time 05:06:47 PM
 */
public interface IEgreso {

    /**
     * Guarda un nuevo egreso en la base de datos
     * @param egresoNuevo Egreso que se desea guardar en la base de datos
     * @return True si se pudo guardar, false si no se pudo guardar el nuevo 
     *  egreso
     */
    public boolean guardarNuevoEgreso(Egreso egresoNuevo);

    /**
     * Guarda los cambios aplicados a un egreso en la base de datos
     * @param egreso Egreso del que se desea guardar cambios
     * @return True si se pudo guardar, false si no se pudo guardar los cambios
     */
    public boolean guardarCambios(Egreso egreso);

    /**
     * Retorna los todos los egresos registrados en la base de datos
     * @return ArrayList de tipo Egreso con todos los egresos existentes
     */
    public ArrayList<Egreso> cargarEgresos();
}
