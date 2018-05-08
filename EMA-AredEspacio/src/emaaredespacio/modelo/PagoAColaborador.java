package emaaredespacio.modelo;

import emaaredespacio.persistencia.controladores.PagosacolaboradorJpaController;
import emaaredespacio.persistencia.entidad.Pagosacolaborador;
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
public class PagoAColaborador implements IPagoAColaborador{
    private String fecha;
    private String nombreColaborador;
    private String nombreAlumno;
    private String grupo;
    private Integer monto;
    private boolean fueEntregado;
    private Integer idPago;
    private String comentario;

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

    public PagoAColaborador(String nombreColaborador, String nombreAlumno, String grupo, Integer monto, String comentario) {
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
    
    public PagoAColaborador(){
        fueEntregado = true;
        comentario = "";
        idPago = null;
    }

    @Override
    public boolean guardarPagoAColaborador(PagoAColaborador pago) {
        boolean validacion = false;
        
        PagosacolaboradorJpaController controlador = new PagosacolaboradorJpaController();
        
        Pagosacolaborador nuevoPago = new Pagosacolaborador();
        
        nuevoPago.setComentario(pago.getComentario());
        nuevoPago.setFechaDePago(new Date());
        nuevoPago.setFueEntregado(false);
        nuevoPago.setIdPago(pago.getIdPago());
        nuevoPago.setMonto(pago.getMonto());
        nuevoPago.setNombreGrupo(pago.getGrupo());
        nuevoPago.setNombreAlumno(pago.getNombreAlumno());
        nuevoPago.setNombreColaborador(pago.getNombreColaborador());
        
        validacion = controlador.create(nuevoPago);
        
        return validacion;
    }

    @Override
    public boolean editarPagoAColaborador(PagoAColaborador pago) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean guardarEntrega(Integer idPago) {
        boolean validacion = true;
        
        PagosacolaboradorJpaController controlador = new PagosacolaboradorJpaController();
        
        validacion = controlador.guardarEntrega(idPago);
        
        return validacion;
    }

    @Override
    public List<PagoAColaborador> buscarPagoAColaborador(String nombreColaborador, boolean fueEntregado) {
        List<PagoAColaborador> pagos = null;
        List<Pagosacolaborador> resultadoBusqueda = null;
        PagosacolaboradorJpaController controlador = new PagosacolaboradorJpaController();
        
        resultadoBusqueda = controlador.buscarPagosAColaborador(nombreColaborador, fueEntregado);
        
        pagos = convertirLista(resultadoBusqueda);
        
        return pagos;
    }
    
    private List<PagoAColaborador> convertirLista(List<Pagosacolaborador> lista){
        List<PagoAColaborador> nuevaLista = new ArrayList();
        
        for(Pagosacolaborador pago: lista){
            PagoAColaborador nuevoPago = new PagoAColaborador();
            
            nuevoPago.setComentario(pago.getComentario());
            nuevoPago.setFecha(EditorDeFormatos.crearFormatoFecha(pago.getFechaDePago()));
            nuevoPago.setFueEntregado(false);
            nuevoPago.setIdPago(pago.getIdPago());
            nuevoPago.setMonto(pago.getMonto());
            nuevoPago.setGrupo(pago.getNombreGrupo());
            nuevoPago.setNombreAlumno(pago.getNombreAlumno());
            nuevoPago.setNombreColaborador(pago.getNombreColaborador());
            
            nuevaLista.add(nuevoPago);
        }
        
        return nuevaLista;
    } 
    
    
}
