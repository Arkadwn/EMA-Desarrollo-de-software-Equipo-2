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
     * Guarda un alumno en la base de datos.
     * 
     * @param alumno Alumno a guardar.
     * @return True si fue guardado, false si no fue guardado.
     */
    public boolean guardarAlumno(Alumno alumno);

    /**
     * Valida los campos de un alumno de acuerdo la restricciones de la base de datos
     * y la las reglas de negocio.
     * 
     * @param alumno Alumno a validar los campos.
     * @return Arreglo de booleanos con la validacion de los campos.
     */
    public boolean[] validarCampos(Alumno alumno);

    /**
     * Edita un alumno en la base de datos.
     * 
     * @param alumno Alumno con los campos editados.
     * @return True si fue entregado, false si no fue entregado.
     */
    public boolean editarAlumo(Alumno alumno);

    /**
     * Busca alumnos por nombre o apellidos.
     * 
     * @param palabraClave Una seccion o completo del nombre o apellido.
     * @return Lista de los alumnos que hicieron match.
     */
    public List<Alumno> buscarAlumno(String palabraClave);

    /**
     * Busca los alumnos que no estan inscritos a un grupo.
     * 
     * @param idGrupo Identificador del grupo.
     * @return Lista de los alumnos que no estan inscritos a dicho grupo.
     */
    public List<Alumno> buscarAlumnosNoInscritos(int idGrupo);
    
    /**
     * Busca un alumno por identificador.
     * 
     * @param idAlumno Identificador del alumno.
     * @return Alumno con dicho identificador.
     */
    public Alumno buscarAlumnoPorId(int idAlumno);
}
