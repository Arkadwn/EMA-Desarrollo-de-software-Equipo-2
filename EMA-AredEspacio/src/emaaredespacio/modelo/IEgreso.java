package emaaredespacio.modelo;

import java.util.ArrayList;

/**
 *
 * @author Adrian Bustamante Zarate
 */
public interface IEgreso {
    public boolean guardarNuevoEgreso(Egreso egresoNuevo);
    public boolean guardarCambios(Egreso egreso);
    public ArrayList<Egreso> cargarEgresos();
}
