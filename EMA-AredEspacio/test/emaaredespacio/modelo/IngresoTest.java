/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Adrian Bustamante Zarate
 */
public class IngresoTest {

    public IngresoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGuardarRegistro() {
        System.out.println("guardarRegistro");
        Ingreso ingresoNuevo = new Ingreso(1, null, 500.0, false, "02/06/2018");
        boolean expResult = true;
        boolean result = new Ingreso().guardarRegistro(ingresoNuevo);
        assertEquals(expResult, result);
    }

    @Test
    public void testGuardarRegistro2() {
        System.out.println("guardarRegistro fallido");
        Ingreso ingresoNuevo = new Ingreso(1, null, 500.0, false, "02/06/2018/");
        boolean expResult = false;
        boolean result = new Ingreso().guardarRegistro(ingresoNuevo);
        assertEquals(expResult, result);
    }

    @Test
    public void testCargarIngresos() {
        System.out.println("cargarIngresos");

        List<Ingreso> result = new Ingreso().cargarIngresos();

        boolean resultado = false;
        if (result != null) {
            resultado = true;
        }

        assertEquals(true, resultado);
    }

    @Test
    public void testBuscarUltimoPagoColaborador() {
        System.out.println("buscarUltimoPagoColaborador");
        int idColaborador = 1;
        Ingreso ingresoEsperado = new Ingreso();
        ingresoEsperado.setPagoColaboradorID(1);
        Ingreso ingresoResult = new Ingreso().buscarUltimoPagoColaborador(idColaborador);
        boolean result = false;
        if (ingresoEsperado.getPagoColaboradorID() == ingresoResult.getPagoColaboradorID()) {
            result = true;
        }
        assertEquals(true, result);
    }

    @Test
    public void testBuscarUltimoPagoColaborador2() {
        System.out.println("buscarUltimoPagoColaborador fallido");
        int idColaborador = 0;
        Ingreso expResult = null;
        Ingreso result = new Ingreso().buscarUltimoPagoColaborador(idColaborador);
        assertEquals(expResult, result);
    }

}
