package emaaredespacio.modelo;

import java.util.List;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 6/05/2018
 * @time 12:21:28 PM
 */
public interface IPagoAColaborador {
    public boolean guardarPagoAColaborador(PagoAColaborador pago);
    public boolean editarPagoAColaborador(PagoAColaborador pago);
    public boolean guardarEntrega(Integer idPago);
    public List<PagoAColaborador> buscarPagoAColaborador(String nombreColaborador, boolean fueEntregada);
}
