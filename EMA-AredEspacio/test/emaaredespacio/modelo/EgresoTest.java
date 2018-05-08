package emaaredespacio.modelo;

import java.util.ArrayList;
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
public class EgresoTest {

    private Egreso egresoNuevo;
    public EgresoTest() {
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
    public void testGuardarNuevoEgreso() {
        System.out.println("guardarNuevoEgreso");
        this.egresoNuevo = new Egreso("Este egreso es del test", "00/00/0000", 100.0);
        boolean expResult = true;
        boolean result = new Egreso().guardarNuevoEgreso(egresoNuevo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void testGuardarNuevoEgreso2() {
        System.out.println("guardarNuevoEgreso fallido");
        Egreso egresoNuevo = new Egreso(); /* Egreso vacio */
        boolean expResult = false;
        boolean result = new Egreso().guardarNuevoEgreso(egresoNuevo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void testGuardarCambios() {
        System.out.println("guardarCambios");
        this.egresoNuevo = new Egreso().cargarEgresos().get(1);
        this.egresoNuevo.setDescripcion("Este egreso es del test modificado, cambie a 150 el monto");
        this.egresoNuevo.setFecha("01/00/0000");
        this.egresoNuevo.setMonto(150.0);
        System.out.println(egresoNuevo.getIdEgreso());
        
        boolean expResult = true;
        boolean result = new Egreso().guardarCambios(this.egresoNuevo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void testGuardarCambios2() {
        System.out.println("guardarCambios fallido");
        Egreso egreso = new Egreso();
        boolean expResult = false;
        boolean result = new Egreso().guardarCambios(egreso);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void testCargarEgresos() {
        System.out.println("cargarEgresos");
        ArrayList<Egreso> resultLista = new Egreso().cargarEgresos();
        int result = resultLista.size();
        boolean expResult = true;
        if(result > 1){
            expResult = false;
        }
        assertEquals(expResult, true);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

//    @Test
//    public void testCargarEgresos2() {
//        System.out.println("cargarEgresos fallido");
//        Egreso instance = new Egreso();
//        ArrayList<Egreso> expResult = null;
//        ArrayList<Egreso> result = instance.cargarEgresos();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

}
