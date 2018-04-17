package emaaredespacio.modelo;

import emaaredespacio.persistencia.controladores.EgresosfacebookJpaController;
import emaaredespacio.persistencia.entidad.Egresosfacebook;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 15/04/2018
 * @time 02:51:50 PM
 */
public class EgresoFacebook implements IEgresoFacebook {

    private String fechaInicio;
    private String fechaFin;
    private Double costo;
    private String link;
    private String descripcion;
    private String creador;
    private Integer idEgresoFacebook;
    private boolean activa;

    public boolean getActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public EgresoFacebook() {
        idEgresoFacebook = null;
        fechaFin = "";
        fechaInicio = "";
        costo = -1.0;
        link = "";
        descripcion = "";
        creador = "";
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdEgresoFacebook() {
        return idEgresoFacebook;
    }

    public void setIdEgresoFacebook(Integer idEgresoFacebook) {
        this.idEgresoFacebook = idEgresoFacebook;
    }

    @Override
    public boolean registrarEgreso(EgresoFacebook egreso) {
        boolean validacion = false;

        Egresosfacebook egresoNuevo = new Egresosfacebook();

        egresoNuevo.setCosto(egreso.getCosto());
        egresoNuevo.setCreador(egreso.getCreador());
        egresoNuevo.setLink(egreso.getLink());
        egresoNuevo.setActiva(true);
        egresoNuevo.setDescripcion(egreso.getDescripcion());
        egresoNuevo.setFechaFin(crearFecha(egreso.getFechaFin()));
        egresoNuevo.setFechaInicio(crearFecha(egreso.getFechaInicio()));
        egresoNuevo.setIdEgresoFacebook(egreso.getIdEgresoFacebook());

        EgresosfacebookJpaController controlador = new EgresosfacebookJpaController();

        validacion = controlador.create(egresoNuevo);

        return validacion;
    }

    @Override
    public boolean editarEgresoFacebook(EgresoFacebook egreso) {
        boolean validacion = true;

        Egresosfacebook egresoNuevo = new Egresosfacebook();

        egresoNuevo.setCosto(egreso.getCosto());
        egresoNuevo.setCreador(egreso.getCreador());
        egresoNuevo.setLink(egreso.getLink());
        egresoNuevo.setDescripcion(egreso.getDescripcion());
        egresoNuevo.setFechaFin(crearFecha(egreso.getFechaFin()));
        egresoNuevo.setFechaInicio(crearFecha(egreso.getFechaInicio()));
        egresoNuevo.setIdEgresoFacebook(egreso.getIdEgresoFacebook());
        egresoNuevo.setActiva(validarDisponibilidadDelEgreso(egreso.getFechaFin()));

        EgresosfacebookJpaController controlador = new EgresosfacebookJpaController();

        try {
            controlador.edit(egresoNuevo);
        } catch (Exception ex) {
            validacion = false;
        }

        return validacion;
    }

    @Override
    public boolean[] validarCampos(EgresoFacebook egreso) {
        boolean[] validaciones = new boolean[5];

        //Descripcion
        validaciones[0] = egreso.getDescripcion().trim().length() >= 2 && egreso.getDescripcion().trim().length() <= 40000;
        //Link
        validaciones[1] = egreso.getLink().trim().length() >= 10 && egreso.getFechaInicio().trim().length() <= 10000;
        //Creador
        validaciones[2] = egreso.getCreador().trim().length() >= 2 && egreso.getCreador().trim().length() <= 200;
        //Costo mayor a 0
        validaciones[3] = egreso.getCosto() >= 0;
        //
        validaciones[4] = validaciones[0] && validaciones[1] && validaciones[2] && validaciones[3];

        return validaciones;
    }

    private Date crearFecha(String cadena) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = null;
        try {
            fecha = formato.parse(cadena);
        } catch (ParseException ex) {
            Logger.getLogger(EgresoFacebook.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fecha;
    }

    public boolean validarDisponibilidadDelEgreso(String fechaFin) {
        boolean validacion = false;

        //String[] partesInicio = fechaInicio.split("/");
        String[] partesFin = fechaFin.split("/");

        //LocalDate inicio = LocalDate.of(Integer.parseInt(partesInicio[2]), Integer.parseInt(partesInicio[1]), Integer.parseInt(partesInicio[0]));
        LocalDate fin = LocalDate.of(Integer.parseInt(partesFin[2]), Integer.parseInt(partesFin[1]), Integer.parseInt(partesFin[0]));
        LocalDate actual = LocalDate.now();

        validacion = fin.isAfter(actual) || fin.isEqual(actual);

        return validacion;
    }

    private String sacarFecha(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        return formato.format(fecha);
    }

    @Override
    public List<EgresoFacebook> buscarEgresosDeFacebook(String creador, boolean estaActiva) {
        List<EgresoFacebook> egresos = null;

        EgresosfacebookJpaController controlador = new EgresosfacebookJpaController();

        egresos = covertirListas(controlador.buscarEgresosPorCreador(creador, estaActiva));

        return egresos;
    }

    private List<EgresoFacebook> covertirListas(List<Egresosfacebook> lista) {
        List<EgresoFacebook> egresos = new ArrayList();
        EgresoFacebook egresoNuevo = null;

        if (lista != null) {
            for (Egresosfacebook egreso : lista) {
                egresoNuevo = new EgresoFacebook();
                egresoNuevo.setCosto(egreso.getCosto());
                egresoNuevo.setCreador(egreso.getCreador());
                egresoNuevo.setLink(egreso.getLink());
                egresoNuevo.setDescripcion(egreso.getDescripcion());
                egresoNuevo.setFechaFin(sacarFecha(egreso.getFechaFin()));
                egresoNuevo.setFechaInicio(sacarFecha(egreso.getFechaInicio()));
                egresoNuevo.setIdEgresoFacebook(egreso.getIdEgresoFacebook());
                egresoNuevo.setActiva(egreso.getActiva());

                egresos.add(egresoNuevo);
            }
        }

        return egresos;
    }
}
