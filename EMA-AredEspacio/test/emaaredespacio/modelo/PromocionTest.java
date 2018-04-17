/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author enriq
 */
public class PromocionTest {
    private IPromocion controlador;
    public PromocionTest() {
        controlador = new Promocion();
    }

    /**
     * Test of crearPromocion method, of class Promocion.
     */
    @Test
    public void testCrearPromocion_CP_01() {
        System.out.println("RegistrarPromocion_CP_01");
        Promocion promocion = new Promocion();
        promocion.setNombrePromocion("Danza contemporanea");
        promocion.setAplicaDescuento(false);
        promocion.setFechaInicio("2018-04-17");
        promocion.setFechaFin("2018-04-30");
        promocion.setPorcentajeDescuento("0");
        promocion.setIdColaborador(1);
        boolean expResult = true;
        boolean result = controlador.crearPromocion(promocion);
        assertEquals(expResult, result);
        System.out.println(expResult+"-"+result);
    }
    
    /**
     * Test of crearPromocion method, of class Promocion.
     */
    @Test
    public void testCrearPromocion_CP_02() {
        System.out.println("RegistrarPromocion_CP_02");
        Promocion promocion = new Promocion();
        promocion.setNombrePromocion("Danza contemporanea abril");
        promocion.setAplicaDescuento(false);
        promocion.setFechaInicio("2018-04-17");
        promocion.setPorcentajeDescuento("0");
        promocion.setIdColaborador(1);
        boolean expResult = false;
        boolean result = controlador.crearPromocion(promocion);
        assertEquals(expResult, result);
        System.out.println(expResult+"-"+result);
    }
    
    /**
     * Test of crearPromocion method, of class Promocion.
     */
    @Test
    public void testCrearPromocion_CP_03() {
        System.out.println("RegistrarPromocion_CP_03");
        Promocion promocion = new Promocion();
        promocion.setNombrePromocion("Danza contemporanea abril");
        promocion.setAplicaDescuento(false);
        
        promocion.setFechaFin("2018-04-30");
        promocion.setPorcentajeDescuento("0");
        promocion.setIdColaborador(1);
        boolean expResult = false;
        boolean result = controlador.crearPromocion(promocion);
        assertEquals(expResult, result);
        System.out.println(expResult+"-"+result);
    }
    
    /**
     * Test of crearPromocion method, of class Promocion.
     */
    @Test
    public void testCrearPromocion_CP_04() {
        System.out.println("RegistrarPromocion_CP_04");
        Promocion promocion = new Promocion();
        promocion.setAplicaDescuento(false);
        promocion.setFechaInicio("2018-04-17");
        promocion.setFechaFin("2018-04-30");
        promocion.setPorcentajeDescuento("0");
        promocion.setIdColaborador(1);
        boolean expResult = false;
        boolean result = controlador.crearPromocion(promocion);
        assertEquals(expResult, result);
        System.out.println(expResult+"-"+result);
    }

    /**
     * Test of buscarPromocion method, of class Promocion.
     */
    @Test
    public void testBuscarPromocion_CP_01() {
        System.out.println("buscarPromocion-CP_01");
        int idColaborador = 1;
        int expResult = 6;
        List<Promocion> result = controlador.buscarPromocion(idColaborador);
        assertEquals(expResult, result.size());
        System.out.println(expResult+"-"+result);
    }

//    /**
//     * Test of convertirLista method, of class Promocion.
//     */
//    @Test
//    public void testConvertirLista() {
//        System.out.println("convertirLista");
//        List<Promociones> listaPromociones = null;
//        Promocion instance = new Promocion();
//        List<Promocion> expResult = null;
//        List<Promocion> result = instance.convertirLista(listaPromociones);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of darDeBajaPromocion method, of class Promocion.
//     */
//    @Test
//    public void testDarDeBajaPromocion() {
//        System.out.println("darDeBajaPromocion");
//        Promocion promocion = null;
//        Promocion instance = new Promocion();
//        boolean expResult = false;
//        boolean result = instance.darDeBajaPromocion(promocion);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of modificarPromocion method, of class Promocion.
     */
    @Test
    public void testModificarPromocion_CP_01() {
        System.out.println("modificarPromocion_CP_01");
        Promocion promocion = new Promocion();
        promocion.setNombrePromocion("Danza contemporanea abril");
        promocion.setIdPromocion(7);
        promocion.setFechaInicio("2018-04-20");
        promocion.setFechaFin("2018-05-10");
        promocion.setIdColaborador(1);
        boolean expResult = true;
        boolean result = controlador.modificarPromocion(promocion);
        assertEquals(expResult, result);
        System.out.println(expResult+"-"+result);
    }
    
    /**
     * Test of modificarPromocion method, of class Promocion.
     */
    @Test
    public void testModificarPromocion_CP_02() {
        System.out.println("modificarPromocion_CP_02");
        Promocion promocion = new Promocion();
        promocion.setNombrePromocion("Danza contemporanea abril");
        promocion.setIdPromocion(7);
        promocion.setFechaInicio("2018-04-20");
        promocion.setIdColaborador(1);
        boolean expResult = false;
        boolean result = controlador.modificarPromocion(promocion);
        assertEquals(expResult, result);
        System.out.println(expResult+"-"+result);
    }
    
    /**
     * Test of modificarPromocion method, of class Promocion.
     */
    @Test
    public void testModificarPromocion_CP_03() {
        System.out.println("modificarPromocion_CP_03");
        Promocion promocion = new Promocion();
        promocion.setNombrePromocion("Danza contemporanea abril");
        promocion.setIdPromocion(7);
        promocion.setFechaFin("2018-05-10");
        promocion.setIdColaborador(1);
        boolean expResult = false;
        boolean result = controlador.modificarPromocion(promocion);
        assertEquals(expResult, result);
        System.out.println(expResult+"-"+result);
    }
    
    
    
}
