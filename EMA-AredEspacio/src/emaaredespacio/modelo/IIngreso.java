package emaaredespacio.modelo;

import java.util.List;

/**
 *
 * @author Adrian Bustamante Zarate
 * @date 29/04/2018
 * @time 05:06:47 PM
 */
public interface IIngreso {

    /**
     * Retorna una lista de todos los ingresos registrados actualmente en la
     * base de datos
     * @return objeto de tipo List de Ingresos
     */
    public List<Ingreso> cargarIngresos();

    /**
     * Guarda nuevo registro de ingreso en la base de datos
     * @param ingresoNuevo ingreso a guardar
     * @return True si se guardo correctamente, false si no es así.
     */
    public boolean guardarRegistro(Ingreso ingresoNuevo);

    /**
     * Retorna el ultimo Ingreso de un colaborador según su id
     * @param idColaborador id del colaborador
     * @return ultimo Ingreso del colaborador indicado
     */
    public Ingreso buscarUltimoPagoColaborador(int idColaborador);

    /**
     * Retorna un objeto tipo List de Ingresos relacionados por nombre de un
     * cliente o un colaborador y el tipo, según la el parametro tipo.
     * @param nombre cadena de caracteres del nombre del cliente o colaborador
     * @param tipo   entero para indicar el tipo de consulta 1 para colaborador 
     *  y 0 para cliente
     * @return Objeto de tipo List de ingresos resultante de la consulta
     */
    public List<Ingreso> buscarPagosPorNombre(String nombre, int tipo);

    /**
     * Modfica un registro de la base de datos
     * @param ingreso ingreso que se modificará 
     * @return True si modifico el ingreso, false si no se pudo modificar el 
     * ingreso correctamente.
     */
    public boolean modificarRegistro(Ingreso ingreso);
}
