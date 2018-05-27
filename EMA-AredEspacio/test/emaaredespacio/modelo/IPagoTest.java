package emaaredespacio.modelo;

import emaaredespacio.utilerias.EditorDeFormatos;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class IPagoTest {
    private IPago metodos;
    public IPagoTest() {
        metodos = new Pago();
    }

    /**
     * Test of guardarPagoAColaborador method, of class IPago.
     */
    @Test
    public void testGuardarPagoAColaborador_CP_01() {
        System.out.println("CP-01-guardarPagoAColaborador-Registrar Pago");
        Pago pago = new Pago();
        pago.setComentario("Primer pago");
        pago.setFecha(EditorDeFormatos.crearFormatoFecha(new Date()));
        pago.setFueEntregado(false);
        IGrupo grupo = new Grupo();
        List<Grupo> grupos = grupo.buscarGrupos();
        pago.setGrupo(grupos.get(0).getTipoDeBaile());
        pago.setIdGrupo(grupos.get(0).getIdGrupo());
        List<Colaborador> colaboradores = new Colaborador().buscarColaborador("");
        pago.setNombreColaborador(colaboradores.get(0).getNombreCompleto());
        pago.setIdColaborador(colaboradores.get(0).getIdColaborador());
        List<Alumno> alumnos = new Alumno().buscarAlumno("");
        pago.setNombreAlumno(alumnos.get(0).getNombreCompleto());
        pago.setIdAlumno(alumnos.get(0).getMatricula());
        pago.setMonto(100);
        
        boolean result = metodos.guardarPagoAColaborador(pago);
        
        boolean expResult = true;
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test of guardarPagoAColaborador method, of class IPago.
     */
    @Test
    public void testGuardarPagoAColaborador_CP_02() {
        System.out.println("CP-02-guardarPagoAColaborador-Registrar Pago");
        Pago pago = new Pago();
        pago.setComentario("Segundo pago");
        pago.setFecha(EditorDeFormatos.crearFormatoFecha(new Date()));
        pago.setFueEntregado(false);
        IGrupo grupo = new Grupo();
        List<Grupo> grupos = grupo.buscarGrupos();
        pago.setGrupo(grupos.get(0).getTipoDeBaile());
        pago.setIdGrupo(grupos.get(0).getIdGrupo());
        List<Colaborador> colaboradores = new Colaborador().buscarColaborador("");
        pago.setNombreColaborador(colaboradores.get(0).getNombreCompleto());
        pago.setIdColaborador(colaboradores.get(0).getIdColaborador());
        List<Alumno> alumnos = new Alumno().buscarAlumno("");
        pago.setNombreAlumno(alumnos.get(0).getNombreCompleto());
        pago.setIdAlumno(alumnos.get(0).getMatricula());
        pago.setMonto(300);
        
        boolean result = metodos.guardarPagoAColaborador(pago);
        
        boolean expResult = true;
        
        assertEquals(expResult, result);
    }

    /**
     * Test of editarPagoAColaborador method, of class IPago.
     */
    @Test
    public void testEditarPagoAColaborador() {
        System.out.println("CP-01-editarPagoAColaborador-Editar Pago 1");
        Pago pago = new Pago();
        pago.setIdPago(1);
        pago.setComentario("Se edito el pago");
        pago.setFecha(EditorDeFormatos.crearFormatoFecha(new Date()));
        pago.setFueEntregado(false);
        IGrupo grupo = new Grupo();
        List<Grupo> grupos = grupo.buscarGrupos();
        pago.setGrupo(grupos.get(0).getTipoDeBaile());
        pago.setIdGrupo(grupos.get(0).getIdGrupo());
        List<Colaborador> colaboradores = new Colaborador().buscarColaborador("");
        pago.setNombreColaborador(colaboradores.get(0).getNombreCompleto());
        pago.setIdColaborador(colaboradores.get(0).getIdColaborador());
        List<Alumno> alumnos = new Alumno().buscarAlumno("");
        pago.setNombreAlumno(alumnos.get(0).getNombreCompleto());
        pago.setIdAlumno(alumnos.get(0).getMatricula());
        pago.setMonto(5000);
        
        boolean result = metodos.editarPagoAColaborador(pago);
        
        boolean expResult = true;
        
        assertEquals(expResult, result);
    }

    /**
     * Test of guardarEntrega method, of class IPago.
     */
    @Test
    public void testGuardarEntrega_CP_01() {
        System.out.println("CP_01_guardarEntrega_Cambiar pago a entregado");
        Integer idPago = 1;
        boolean expResult = true;
        boolean result = metodos.guardarEntrega(idPago);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGuardarEntrega_CP_02() {
        System.out.println("CP_02_guardarEntrega_Cambiar pago a entregado a un pago que no existe");
        Integer idPago = 20;
        boolean expResult = false;
        boolean result = metodos.guardarEntrega(idPago);
        assertEquals(expResult, result);
    }

    /**
     * Test of buscarPagoAColaborador method, of class IPago.
     */
    @Test
    public void testBuscarPagoAColaborador_CP_01() {
        System.out.println("CP_01_buscarPagoAColaborador_buscar pagos que no existen");
        String nombreColaborador = "kldmalkf";
        boolean fueEntregada = false;
        List<Pago> result = metodos.buscarPagoAColaborador(nombreColaborador, fueEntregada);
        boolean expResult = true;
        assertEquals(expResult, result.isEmpty());
    }

    
}
