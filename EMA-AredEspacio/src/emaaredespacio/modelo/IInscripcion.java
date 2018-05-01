package emaaredespacio.modelo;

import java.util.List;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 30/04/2018
 * @time 12:39:16 AM
 */
public interface IInscripcion {
    public boolean inscribirAlumno(Inscripcion inscripcion);
    public boolean darDeBajaAlumno(int idGrupo, int idAlumno);
    public List<Alumno> sacarInscripcionesDeGrupo(int idGrupo);
}
