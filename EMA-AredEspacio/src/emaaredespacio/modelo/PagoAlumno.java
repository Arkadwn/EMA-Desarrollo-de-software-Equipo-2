package emaaredespacio.modelo;

import emaaredespacio.persistencia.controladores.PagosalumnosJpaController;
import emaaredespacio.persistencia.entidad.Alumnos;
import emaaredespacio.persistencia.entidad.Grupos;
import emaaredespacio.persistencia.entidad.Pagosalumnos;
import emaaredespacio.utilerias.EditorDeFormatos;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class PagoAlumno implements IPagoAlumno {

    private int idPagoAlumno = 0;
    private int matricula = 0;
    private int idGrupo = 0;
    private String fechaPago = "";
    private String monto = "";
    private int porcentajeDescuento;
    private String total = "";
    private String tipoPago = "";

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
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
        boolean registrado = false;
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

    public Date crearFecha(String cadena) {
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
        boolean registrado = false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        PagosalumnosJpaController controlador = new PagosalumnosJpaController(entityManagerFactory);
        Pagosalumnos pagos = new Pagosalumnos();
        Grupos grupo = new Grupos();
        grupo.setIdGrupo(pago.getIdGrupo());
        pagos.setIdPago(pago.getIdPagoAlumno());
        pagos.setIdGrupo(grupo);
        pagos.setFechaPago(EditorDeFormatos.crearFecha(pago.getFechaPago()));
        Alumnos alumno = new Alumnos();
        alumno.setMatricula(pago.getMatricula());
        pagos.setMatricula(alumno);
        pagos.setMonto(pago.getMonto());
        pagos.setPorcentajeDescuento(pago.getPorcentajeDescuento());
        pagos.setTipoPago(pago.getTipoPago());
        pagos.setTotal(pago.getTotal());
        registrado = controlador.edit(pagos);
        return registrado;
    }

    @Override
    public PagoAlumno buscarUltimoPago() {
        PagoAlumno pago = new PagoAlumno();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        PagosalumnosJpaController pagosJPA = new PagosalumnosJpaController(entityManagerFactory);
        List<Pagosalumnos> pagosAlumnos = pagosJPA.findPagosalumnosEntities();
        int idPagoUltimo = pagosAlumnos.size();
        pago.setIdPagoAlumno(idPagoUltimo);
        pago.setMatricula(pagosAlumnos.get(idPagoUltimo-1).getMatricula().getMatricula());
        pago.setIdGrupo(pagosAlumnos.get(idPagoUltimo-1).getIdGrupo().getIdGrupo());
        pago.setFechaPago(EditorDeFormatos.crearFormatoFecha(pagosAlumnos.get(idPagoUltimo-1).getFechaPago()));
        pago.setMonto(pagosAlumnos.get(idPagoUltimo-1).getMonto());
        pago.setPorcentajeDescuento(pagosAlumnos.get(idPagoUltimo-1).getPorcentajeDescuento());
        pago.setTipoPago(pagosAlumnos.get(idPagoUltimo-1).getTipoPago());
        pago.setTotal(pagosAlumnos.get(idPagoUltimo-1).getTotal());

        return pago;
    }

    @Override
    public List<PagoAlumno> buscarPagosVencidos(Colaborador colaborador) {
        List<PagoAlumno> pagos= null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        PagosalumnosJpaController controlador = new PagosalumnosJpaController(entityManagerFactory);
        pagos = controlador.buscarPagosVencidos(colaborador);
        return pagos;
    }
    
    @Override
    public List<PagoAlumno> cargarListaPagosDeAlumnosDeGrupo(int matricula,int idGrupo) {
        List<PagoAlumno> pagos = null;
        List<Pagosalumnos> resultadoBusqueda = null;
        PagosalumnosJpaController controlador = new PagosalumnosJpaController();
        
        resultadoBusqueda = controlador.buscarPagosDeAlumno(matricula, idGrupo);
        pagos = convertirLista(resultadoBusqueda);
        
        return pagos;
    }
    
    private List<PagoAlumno> convertirLista(List<Pagosalumnos> lista){
        List<PagoAlumno> pagos = new ArrayList();
        
        for(Pagosalumnos pago: lista){
            PagoAlumno pagoAlumno = new PagoAlumno();
            pagoAlumno.setFechaPago(EditorDeFormatos.crearFormatoFecha(pago.getFechaPago()));
            pagoAlumno.setIdGrupo(pago.getIdGrupo().getIdGrupo());
            pagoAlumno.setMatricula(pago.getMatricula().getMatricula());
            pagoAlumno.setMonto(pago.getMonto());
            pagoAlumno.setIdPagoAlumno(pago.getIdPago());
            pagoAlumno.setPorcentajeDescuento(pago.getPorcentajeDescuento());
            pagoAlumno.setTotal(pago.getTotal());
            pagoAlumno.setTipoPago(pago.getTipoPago());
            pagos.add(pagoAlumno);
        }
        
        return pagos;
    }
}
