package emaaredespacio.modelo;

import java.util.List;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 30/04/2018
 * @time 12:39:16 AM
 */
public interface IInscripcion {

    /**
     * Inscribe un alumno a un grupo.
     * 
     * @param inscripcion Incripcion a un grupo.
     * 
     * @return true si fue inscrito, false si no fue entregado.
     */
    public boolean inscribirAlumno(Inscripcion inscripcion);

    /**
     * Das de baja a un alumno de un grupo.
     * 
     * @param idGrupo Identificador del grupo.
     * @param idAlumno Identificador del alumno.
     * 
     * @return true si fue dado de baja, false sin no fue dado de baja.
     */
    public boolean darDeBajaAlumno(int idGrupo, int idAlumno);

    /**
     * Busca los alumnos inscritos a un grupo.
     * 
     * @param idGrupo Identificadar del grupo.
     * @return Lista de alumnos inscritos.
     */
    public List<Alumno> sacarInscripcionesDeGrupo(int idGrupo);
}
