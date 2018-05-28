/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import emaaredespacio.gui.controlador.FXMLRegistrarPagoDeAlumnosController;
import emaaredespacio.utilerias.EditorDeFormatos;
import static emaaredespacio.utilerias.EditorDeFormatos.crearFecha;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author enriq
 */
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PagoAlumnoTest {
    private IPagoAlumno controlador;
    
    public PagoAlumnoTest(){
        this.controlador = new PagoAlumno();
    }
    /**
     * Test of registrarPago method, of class PagoAlumno.
     */
    @Test
    public void testRegistrarPago_CP_01() {
        System.out.println("registrarPago_CP_01");
        PagoAlumno pago = new PagoAlumno();
        FXMLRegistrarPagoDeAlumnosController registro = new FXMLRegistrarPagoDeAlumnosController();
        pago.setIdGrupo(1);
        pago.setMatricula(1);
        pago.setMonto("300");
        pago.setPorcentajeDescuento(0);
        pago.setTipoPago("Mensualidad");
        pago.setFechaPago(EditorDeFormatos.crearFormatoFecha(new Date()));
        pago.setTotal("300");
        boolean expResult = true;
        boolean result = controlador.registrarPago(pago);
        assertEquals(expResult, result);
        System.out.println(expResult+" - " + result);
    }

    /**
     * Test of buscarUltimoPago method, of class PagoAlumno.
     */
    @Test
    public void testBuscarUltimoPago_CP_02() {
        System.out.println("buscarUltimoPago_CP_02");
        FXMLRegistrarPagoDeAlumnosController registro = new FXMLRegistrarPagoDeAlumnosController();
        String expResult = EditorDeFormatos.crearFormatoFecha(new Date());
        PagoAlumno result = controlador.buscarUltimoPago();
        assertEquals(expResult, result.getFechaPago());
        System.out.println(expResult+" - " + result.getFechaPago());
    }

    /**
     * Test of buscarPagosVencidos method, of class PagoAlumno.
     */
    @Test
    public void testBuscarPagosVencidos_CP_03() {
        System.out.println("buscarPagosVencidos_CP_03");
        Colaborador colaborador = new Colaborador();
        colaborador.setIdColaborador(2);
        List<PagoAlumno> expResult = new ArrayList();
        List<PagoAlumno> result = controlador.buscarPagosVencidos(colaborador);
        assertEquals(expResult.size(), result.size());
        System.out.println(expResult.size()+" - " + result.size());
    }

    /**
     * Test of cargarListaPagosDeAlumnosDeGrupo method, of class PagoAlumno.
     */
    @Test
    public void testCargarListaPagosDeAlumnosDeGrupo_CP_04() {
        System.out.println("cargarListaPagosDeAlumnosDeGrupo_CP_04");
        int matricula = 1;
        int idGrupo = 1;
        PagoAlumno alumno = new PagoAlumno();
        List<PagoAlumno> expResult = new ArrayList();
        expResult.add(alumno);
        List<PagoAlumno> result = controlador.cargarListaPagosDeAlumnosDeGrupo(matricula, idGrupo);
        assertEquals(expResult.size(), result.size());
        System.out.println(expResult.size()+" - " + result.size());
    }
    
    /**
     * Test of editarPago method, of class PagoAlumno.
     */
    @Test
    public void testEditarPago_CP_05(){
        System.out.println("EditarPago_CP_04");
        int idPagoAlumno = 1;
        PagoAlumno pago = new PagoAlumno();
        pago.setIdPagoAlumno(idPagoAlumno);
        FXMLRegistrarPagoDeAlumnosController registro = new FXMLRegistrarPagoDeAlumnosController();
        pago.setIdGrupo(1);
        pago.setMatricula(1);
        pago.setMonto("500");
        pago.setPorcentajeDescuento(0);
        pago.setTipoPago("Inscripcion");
        pago.setFechaPago(EditorDeFormatos.crearFormatoFecha(new Date()));
        pago.setTotal("500");
        boolean expResult = true;
        boolean result = controlador.editarPago(pago);
        assertEquals(expResult, result);
        System.out.println(expResult+" - " + result);
    }
    
}
