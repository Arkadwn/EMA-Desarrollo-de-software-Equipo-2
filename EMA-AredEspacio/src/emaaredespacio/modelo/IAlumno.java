package emaaredespacio.modelo;

import java.util.List;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 1/04/2018
 * @time 07:13:03 PM
 */
public interface IAlumno {

    /**
     *
     * @param alumno
     * @return
     */
    public boolean guardarAlumno(Alumno alumno);

    /**
     *
     * @param alumno
     * @return
     */
    public boolean[] validarCampos(Alumno alumno);

    /**
     *
     * @param alumno
     * @return
     */
    public boolean editarAlumo(Alumno alumno);

    /**
     *
     * @param palabraClave
     * @return
     */
    public List<Alumno> buscarAlumno(String palabraClave);

    /**
     *
     * @param idGrupo
     * @return
     */
    public List<Alumno> buscarAlumnosNoInscritos(int idGrupo);
}
