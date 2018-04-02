package emaaredespacio.modelo;

import java.util.List;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 1/04/2018
 * @time 07:13:03 PM
 */
public interface IAlumno {
    public boolean guardarAlumno(Alumno alumno);
    public boolean[] validarCampos(Alumno alumno);
    public boolean editarAlumo(Alumno alumno);
    public List<Alumno> buscarAlumno(String palabraClave);
}
