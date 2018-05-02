/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import emaaredespacio.persistencia.controladores.PagosalumnosJpaController;
import emaaredespacio.persistencia.entidad.Alumnos;
import emaaredespacio.persistencia.entidad.Grupos;
import emaaredespacio.persistencia.entidad.Pagosalumnos;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author enriq
 */
public class PagoAlumno implements IPagoAlumno{
    private int idPagoAlumno=0;
    private int matricula=0;
    private int idGrupo=0;
    private String fechaPago="";
    private String monto="";
    private int porcentajeDescuento;
    private String total = "";
    private int tipoPago=0;

    public int getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(int tipoPago) {
        this.tipoPago = tipoPago;
    }

    public int getIdPagoAlumno() {
        return idPagoAlumno;
    }

    public void setIdPagoAlumno(int idPagoAlumno) {
        this.idPagoAlumno = idPagoAlumno;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public int getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(int porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    
    @Override
    public boolean registrarPago(PagoAlumno pago) {
        boolean registrado=false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        PagosalumnosJpaController controlador = new PagosalumnosJpaController(entityManagerFactory);
        Pagosalumnos pagos = new Pagosalumnos();
        pagos.setIdPago(null);
        Grupos grupo = new Grupos();
        grupo.setIdGrupo(pago.getIdGrupo());
        pagos.setIdGrupo(grupo);
        Alumnos alumno = new Alumnos();
        alumno.setMatricula(pago.getMatricula());
        pagos.setMatricula(alumno);
        pagos.setFechaPago(crearFecha(pago.getFechaPago()));
        pagos.setMonto(pago.getMonto());
        pagos.setTipoPago(pago.getTipoPago());
        pagos.setPorcentajeDescuento(pago.getPorcentajeDescuento());
        pagos.setTotal(pago.getTotal());
        registrado = controlador.create(pagos);
        return registrado;
    }
    
    private Date crearFecha(String cadena){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = null;
        try {
            fecha = formato.parse(cadena);
        } catch (ParseException ex) {
            Logger.getLogger(EgresoFacebook.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fecha;
    }

    @Override
    public boolean editarPago(PagoAlumno pago) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PagoAlumno> cargarListaPagos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
