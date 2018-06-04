package emaaredespacio.modelo;

import java.util.List;

/**
 *
 * @author Jesús Enrique Flores Nestozo
 * @date 08/05/2018
 * @time 09:22:21 AM
 */
public interface IPagoAlumno {
    
    /**
     * Registra un pago de un alumno a un colaborador
     * @param pago del alumno
     * @return True si se pudo registrar el pago, False si no se registró
     */
    public boolean registrarPago(PagoAlumno pago);
    
    /**
     * Guarda un pago de un alumno editado
     * @param pago editado del alumno
     * @return True si se pudo guardar la edición del pago, False si no se pudo guardar
     */
    public boolean editarPago(PagoAlumno pago);
    
    /**
     * Carga los pagos de un alumno pertenecientes a un grupo
     * @param matricula del alumno al cual pertenecen los pagos
     * @param idGrupo del cual se buscan los pagos
     * @return Lista de los pagos realizados por el alumno
     */
    public List<PagoAlumno> cargarListaPagosDeAlumnosDeGrupo(int matricula,int idGrupo);
    
    /**
     * Buscar el último pago realizado.
     * @return PagoAlumno realizado.
     */
    public PagoAlumno buscarUltimoPago();
    
    /**
     * Busca los pagos vencidos de los alumnos
     * @param colaborador al cual pertenecen los pagos vencidos de los alumnos
     * @return Lista de pagos de alumnos vencidos
     */
    public List<PagoAlumno> buscarPagosVencidos(Colaborador colaborador);
}
