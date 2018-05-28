/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import java.util.Calendar;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Adrián Bustamante Zarate
 */
public class RentaIT {

    public RentaIT() {
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

    /**
     * Test of guardarNuevaRenta method, of class Renta.
     */
    @Test
    public void testGuardarNuevaRenta_CP01() {
        System.out.println("guardarNuevaRenta CP01 Exitoso");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 3, 21);
        Renta renta = new Renta(6, 14000, calendar, 1000, 13000, new Cliente("1"), true);

        boolean expResult = true;
        boolean result = new Renta().guardarNuevaRenta(renta);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void testGuardarNuevaRenta_CP_02() {
        System.out.println("guardarNuevaRentaCP02 Fallido");
        Renta renta = null;

        boolean expResult = false;
        boolean result = new Renta().guardarNuevaRenta(renta);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of cancelarRenta method, of class Renta.
     */
    @Test
    public void testCancelarRenta_CP_01() {
        System.out.println("Empieza el test de CancelarRenta CP01 Exitoso");
        int idRenta = 6;
        boolean expResult = true;
        boolean result = new Renta().cancelarRenta(idRenta);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void testCancelarRenta_CP_02() {
        System.out.println("Empieza el test de CancelarRenta CP02 Fallido");
        int idRenta = 0;
        boolean expResult = false;
        boolean result = new Renta().cancelarRenta(idRenta);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of cargarRenta method, of class Renta.
     */
    @Test
    public void testCargarRenta_CP_01() {
        System.out.println("Empieza el test de CargarRenta CP01 Exitoso");
        String id = "7";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 3, 18);
        Renta objectExpResult = new Renta(7, 16000, calendar, 12000, 14000, new Cliente("2"), true);
        new Renta().guardarNuevaRenta(objectExpResult);
        Renta objectResult = new Renta().cargarRenta(id);
        boolean expResult = true;
        boolean result = false;
        if (Integer.parseInt(id) == objectResult.getId()) {
            result = true;
        }
        assertEquals(expResult, result);
    }

    @Test
    public void testCargarRenta_CP_02() {
        System.out.println("Empieza el test de CargarRenta CP01 Fallido");
        String id = "";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 3, 18);
        Renta objectExpResult = new Renta(7, 16000, calendar, 12000, 14000, new Cliente("2"), true);
        Renta objectResult = new Renta().cargarRenta(id);
        boolean expResult = false;
        boolean result = false;
        if (objectResult != null) {
            if (objectExpResult.getId() == objectResult.getId()) {
                result = true;
            }
        } else {
            result = false;
        }
        assertEquals(expResult, result);
    }

    /**
     * Test of cargarRentas method, of class Renta.
     */
    @Test
    public void testCargarRentas_0args() {
        System.out.println("cargarRentas");
        Renta instance = new Renta();
        boolean result = false;
        List<Renta> resultList = instance.cargarRentas();
        if (resultList != null) {
            result = true;
        }
        assertEquals(true, result);
    }

    /**
     * Test of cargarRentas method, of class Renta.
     */
    @Test
    public void testCargarRentas_Cliente_CP_01() {
        System.out.println("cargarRentas CP01 Fallido");
        Cliente cliente = null;

        List<Renta> listResult = new Renta().cargarRentas(cliente);
        boolean result = false;
        if (listResult != null) {
            result = true;
        }
        assertEquals(false, result);
    }

    @Test
    public void testCargarRentas_Cliente_CP_02() {
        System.out.println("cargarRentas CP02 Exitoso");
        Cliente cliente = new Cliente("1");

        List<Renta> listResult = new Renta().cargarRentas(cliente);
        boolean result = false;
        if (listResult != null) {
            result = true;
        }
        assertEquals(true, result);
    }

    /**
     * Test of guardarCambios method, of class Renta.
     */
    @Test
    public void testGuardarCambios_CP_01() {
        System.out.println("guardarCambios CP01 Exitoso");
        Renta renta = new Renta().cargarRenta("6");
        renta.setHoraFin(500);
        boolean expResult = true;
        boolean result = new Renta().guardarCambios(renta);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void testGuardarCambios_CP_02() {
        System.out.println("guardarCambios CP02 Fallido");
        Renta renta = null;
        boolean expResult = false;
        boolean result = new Renta().guardarCambios(renta);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}
