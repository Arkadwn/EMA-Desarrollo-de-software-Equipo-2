package emaaredespacio.modelo;

import emaaredespacio.persistencia.controladores.InscripcionesJpaController;
import emaaredespacio.persistencia.entidad.Alumnos;
import emaaredespacio.persistencia.entidad.Colaboradores;
import emaaredespacio.persistencia.entidad.Grupos;
import emaaredespacio.persistencia.entidad.Inscripciones;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 30/04/2018
 * @time 12:36:15 AM
 */
public class Inscripcion implements IInscripcion {

    private int idGrupo;
    private int idAlumno;
    private int pagoMensualidad;
    private int pagoInscripcion;
    private String fechaIncripcion;

    public Inscripcion(int idGrupo, int idAlumno, int pagoMensualidad, int pagoInscripcion, String fechaIncripcion) {
        this.idGrupo = idGrupo;
        this.idAlumno = idAlumno;
        this.pagoMensualidad = pagoMensualidad;
        this.pagoInscripcion = pagoInscripcion;
        this.fechaIncripcion = fechaIncripcion;
    }

    public Inscripcion() {

    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public int getPagoMensualidad() {
        return pagoMensualidad;
    }

    public int getPagoInscripcion() {
        return pagoInscripcion;
    }

    public String getFechaIncripcion() {
        return fechaIncripcion;
    }

    @Override
    public boolean inscribirAlumno(Inscripcion inscripcion) {
        boolean validacion = false;

        InscripcionesJpaController controlador = new InscripcionesJpaController();
        Inscripciones nuevaInscripcion = new Inscripciones();
        Grupos grupo = controlador.buscarGrupoPorId(inscripcion.getIdGrupo());
        Alumnos alumno = controlador.buscarAlumnoPorId(inscripcion.getIdAlumno());

        if (grupo != null && alumno != null) {
            nuevaInscripcion.setEstado(true);
            nuevaInscripcion.setFechaInscripcion(crearFecha(inscripcion.getFechaIncripcion()));
            nuevaInscripcion.setIdAlumno(alumno);
            nuevaInscripcion.setIdGrupo(grupo);
            nuevaInscripcion.setPagoMensual(inscripcion.getPagoMensualidad());
            nuevaInscripcion.setPrecioInscripcion(inscripcion.getPagoInscripcion());

            validacion = controlador.create(nuevaInscripcion);
        }

        return validacion;
    }

    @Override
    public boolean darDeBajaAlumno(int idGrupo, int idAlumno) {
        boolean validacion = false;
        InscripcionesJpaController controlador = new InscripcionesJpaController();

        validacion = controlador.darDeBajaAlumno(idGrupo, idAlumno);

        return validacion;
    }

    @Override
    public List<Alumno> sacarInscripcionesDeGrupo(int idGrupo) {
        List<Alumnos> resultadoBusqueda = null;
        List<Alumno> inscripciones = null;

        InscripcionesJpaController controlador = new InscripcionesJpaController();

        resultadoBusqueda = controlador.buscarAlumnosInscritos(idGrupo);

        inscripciones = convertirLista(resultadoBusqueda);

        return inscripciones;
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

    private List<Alumno> convertirLista(List<Alumnos> lista) {
        List<Alumno> alumnos = new ArrayList();

        for (Alumnos alumno : lista) {
            Alumno nuevoAlumno = new Alumno();
            nuevoAlumno.setApellidos(alumno.getApellidos());
            nuevoAlumno.setNombre(alumno.getNombre());
            nuevoAlumno.setCorreo(alumno.getCorreo());
            nuevoAlumno.setDireccion(alumno.getDireccion());
            nuevoAlumno.setEstado(alumno.getEstado());
            nuevoAlumno.setImagenPerfil(alumno.getImagen());
            nuevoAlumno.setMatricula(alumno.getMatricula());
            nuevoAlumno.setTelefono(alumno.getTelefono());
            alumnos.add(nuevoAlumno);
        }

        return alumnos;
    }

    public List<Grupo> buscarGruposDeColaborador(int idColaborador) {
        List<Grupos> resultadoBusqueda = null;
        List<Grupo> grupos = null;
        InscripcionesJpaController controlador = new InscripcionesJpaController();

        resultadoBusqueda = controlador.buscarGruposDeColaborador(idColaborador);

        grupos = convertirListaGrupo(resultadoBusqueda);

        return grupos;
    }

    private List<Grupo> convertirListaGrupo(List<Grupos> lista) {
        List<Grupo> grupos = new ArrayList();

        for (Grupos grupo : lista) {
            Grupo nuevoGrupo = new Grupo();
            Colaboradores nuevoC = new Colaboradores();
            nuevoC = grupo.getIdColaborador();
            nuevoGrupo.setIdColaborador(nuevoC.getIdColaborador());
            nuevoGrupo.setTipoDeBaile(grupo.getTipoDeBaile());
            nuevoGrupo.setCupo(grupo.getCupo());
            nuevoGrupo.setEstado(grupo.getEstado());
            nuevoGrupo.setIdGrupo(grupo.getIdGrupo());
            nuevoGrupo.setHorarioAsignado(grupo.getHorarioAsignado());
            grupos.add(nuevoGrupo);
        }

        return grupos;
    }
}
