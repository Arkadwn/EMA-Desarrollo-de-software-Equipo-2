package emaaredespacio.modelo;

import java.util.List;

/**
 *
 * @author Adrián Bustamante Zarate
 * @date 29/04/2018
 * @time 05:06:47 PM
 */
public interface IRenta {

    /**
     * Da de baja una renta de la base de datos, según el ID que se pasa por
     * parametros
     * @param idRenta ID de la renta a cancelar/dar de baja
     * @return true si se cancelo correctamente, false si no.
     */
    public boolean cancelarRenta(int idRenta);

    /**
     * Retorna una renta, según el ID que se pasa por parametros
     * @param id ID de la renta a retornar
     * @return objeto de tipo Renta que es resultado de la consulta por ID
     */
    public Renta cargarRenta(String id);

    /**
     * Retorna todas las rentas de la base de datos en una lista de tipo List
     * @return objeto de tipo List de Rentas con todas las rentas existentes
     */
    public List<Renta> cargarRentas();

    /**
     * Retorna todas las rentas relacionadas a un cliente de la base de datos
     * en una lista tipo List
     * @param cliente el cliente con el que se filtrarán las rentas
     * @return un objeto de tipo List de Rentas con todas las rentas relacionas
     * con el cliente parametro.
     */
    public List<Renta> cargarRentas(Cliente cliente);

    /**
     * Guarda los cambios aplicados a una renta
     * @param renta renta modificada a guardar
     * @return true si se guardaron los cambios sobre la renta, false si no.
     */
    public boolean guardarCambios(Renta renta);

    /**
     * Guarda una nueva renta, la cual se pasa por parametro
     * @param renta renta nueva a guardar
     * @return true si se pudo guardar la renta correctamente, false si no.
     */
    public boolean guardarNuevaRenta(Renta renta);
}
