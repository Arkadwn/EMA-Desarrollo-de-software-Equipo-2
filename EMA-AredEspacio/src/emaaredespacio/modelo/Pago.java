package emaaredespacio.modelo;

import emaaredespacio.persistencia.controladores.PagosJpaController;
import emaaredespacio.persistencia.entidad.Pagos;
import emaaredespacio.utilerias.EditorDeFormatos;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 6/05/2018
 * @time 12:18:17 PM
 */
public class Pago implements IPago {

    private String fecha;
    private String nombreColaborador;
    private String nombreAlumno;
    private String grupo;
    private Integer monto;
    private boolean fueEntregado;
    private Integer idPago;
    private String comentario;
    private int idAlumno;
    private int idGrupo;
    private int idColaborador;

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setNombreColaborador(String nombreColaborador) {
        this.nombreColaborador = nombreColaborador;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public void setFueEntregado(boolean fueEntregado) {
        this.fueEntregado = fueEntregado;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getComentario() {
        return comentario;
    }

    public Integer getIdPago() {
        return idPago;
    }

    public boolean isFueEntregado() {
        return fueEntregado;
    }

    public Pago(String nombreColaborador, String nombreAlumno, String grupo, Integer monto, String comentario) {
        this.nombreColaborador = nombreColaborador;
        this.nombreAlumno = nombreAlumno;
        this.grupo = grupo;
        this.monto = monto;
        this.comentario = comentario;
    }

    public String getFecha() {
        return fecha;
    }

    public String getNombreColaborador() {
        return nombreColaborador;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public String getGrupo() {
        return grupo;
    }

    public Integer getMonto() {
        return monto;
    }

    public Pago() {
        fueEntregado = true;
        comentario = "";
        idPago = null;
    }

    @Override
    public boolean guardarPagoAColaborador(Pago pago) {
        boolean validacion = false;

        PagosJpaController controlador = new PagosJpaController();

        Pagos nuevoPago = new Pagos();

        nuevoPago.setComentario(pago.getComentario());
        nuevoPago.setFechaDePago(new Date());
        nuevoPago.setFueEntregado(false);
        nuevoPago.setIdPago(pago.getIdPago());
        nuevoPago.setMonto(pago.getMonto());
        nuevoPago.setNombreGrupo(pago.getGrupo());
        nuevoPago.setNombreAlumno(pago.getNombreAlumno());
        nuevoPago.setNombreColaborador(pago.getNombreColaborador());
        nuevoPago.setIdColaborador(pago.getIdColaborador());
        nuevoPago.setIdAlumno(pago.getIdAlumno());
        nuevoPago.setIdGrupo(pago.getIdGrupo());

        validacion = controlador.create(nuevoPago);

        return validacion;
    }

    @Override
    public boolean editarPagoAColaborador(Pago pago) {
        boolean validacion = true;
        Pagos pagoEditado = new Pagos();
        
        pagoEditado.setComentario(pago.getComentario());
        pagoEditado.setFechaDePago(EditorDeFormatos.crearFecha(pago.getFecha()));
        pagoEditado.setFueEntregado(pago.isFueEntregado());
        pagoEditado.setIdPago(pago.getIdPago());
        pagoEditado.setMonto(pago.getMonto());
        pagoEditado.setNombreGrupo(pago.getGrupo());
        pagoEditado.setNombreAlumno(pago.getNombreAlumno());
        pagoEditado.setNombreColaborador(pago.getNombreColaborador());
        pagoEditado.setIdColaborador(pago.getIdColaborador());
        pagoEditado.setIdAlumno(pago.getIdAlumno());
        pagoEditado.setIdGrupo(pago.getIdGrupo());
        
        PagosJpaController controlador = new PagosJpaController();
        
        validacion = controlador.edit(pagoEditado);
        
        return validacion;
    }

    @Override
    public boolean guardarEntrega(Integer idPago) {
        boolean validacion = true;

        PagosJpaController controlador = new PagosJpaController();

        validacion = controlador.guardarEntrega(idPago);

        return validacion;
    }

    @Override
    public List<Pago> buscarPagoAColaborador(String nombreColaborador, boolean fueEntregado) {
        List<Pago> pagos = null;
        List<Pagos> resultadoBusqueda = null;
        PagosJpaController controlador = new PagosJpaController();

        resultadoBusqueda = controlador.buscarPagosAColaborador(nombreColaborador, fueEntregado);

        pagos = convertirLista(resultadoBusqueda);

        return pagos;
    }

    private List<Pago> convertirLista(List<Pagos> lista) {
        List<Pago> nuevaLista = new ArrayList();

        for (Pagos pago : lista) {
            Pago nuevoPago = new Pago();

            nuevoPago.setComentario(pago.getComentario());
            nuevoPago.setFecha(EditorDeFormatos.crearFormatoFecha(pago.getFechaDePago()));
            nuevoPago.setFueEntregado(pago.getFueEntregado());
            nuevoPago.setIdPago(pago.getIdPago());
            nuevoPago.setMonto(pago.getMonto());
            nuevoPago.setGrupo(pago.getNombreGrupo());
            nuevoPago.setNombreAlumno(pago.getNombreAlumno());
            nuevoPago.setNombreColaborador(pago.getNombreColaborador());
            nuevoPago.setIdColaborador(pago.getIdColaborador());
            nuevoPago.setIdAlumno(pago.getIdAlumno());
            nuevoPago.setIdGrupo(pago.getIdGrupo());

            nuevaLista.add(nuevoPago);
        }

        return nuevaLista;
    }

}
