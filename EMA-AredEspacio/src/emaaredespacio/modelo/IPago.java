package emaaredespacio.modelo;

import java.util.List;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 6/05/2018
 * @time 12:21:28 PM
 */
public interface IPago {

    /**
     * Guarda un pago de un alumno al director en la base de datos.
     * 
     * @param pago Pago entregado al director.
     * @return true si fue guardado, false si fallo al guardar.
     */
    public boolean guardarPagoAColaborador(Pago pago);

    /**
     * Edita un pago de un alumno al director en la base de datos.
     * @param pago Pago modificado.
     * @return true si fue guardado, false si fallo al guardar.
     */
    public boolean editarPagoAColaborador(Pago pago);

    /**
     * Marca como entrega un pago en la base de datos.
     * 
     * @param idPago Identificador del pago.
     * @return true si fue guardado, false si fallo al guardar.
     */
    public boolean guardarEntrega(Integer idPago);

    /**
     * Busca un pagos que son para un colaborador, de acuerdo del estado del pago.
     * 
     * @param nombreColaborador Nombre del colaborador para el cual es el pago.
     * @param fueEntregada Estado del pago true si fue entregado, false no ha sido
     * entregado.
     * 
     * @return Lista de pagos del colaborador de acuerdo al estado del pago.
     */
    public List<Pago> buscarPagoAColaborador(String nombreColaborador, boolean fueEntregada);
}
