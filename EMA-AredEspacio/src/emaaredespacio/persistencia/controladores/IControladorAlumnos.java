package emaaredespacio.persistencia.controladores;

import emaaredespacio.persistencia.entidad.Alumnos;
import java.util.List;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 1/04/2018
 * @time 07:09:03 PM
 */
public interface IControladorAlumnos {
    public boolean guardarAlumno(Alumnos alumno);
    public boolean editarAlumno(Alumnos alumno);
    public List<Alumnos> buscarAlumno(String palabraClave);
}
