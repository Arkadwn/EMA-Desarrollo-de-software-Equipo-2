package emaaredespacio.modelo;

import java.util.List;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 6/05/2018
 * @time 12:21:28 PM
 */
public interface IPago {
    public boolean guardarPagoAColaborador(Pago pago);
    public boolean editarPagoAColaborador(Pago pago);
    public boolean guardarEntrega(Integer idPago);
    public List<Pago> buscarPagoAColaborador(String nombreColaborador, boolean fueEntregada);
}
