/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author enriq
 */
public class GrupoTest {
    private IGrupo controlador;
    
    public GrupoTest() {
        controlador = new Grupo();
    }

//    /**
//     * Test of buscarGrupos method, of class Grupo.
//     */
//    @Test
//    public void testBuscarGrupos() {
//        System.out.println("buscarGrupos");
//        Grupo instance = new Grupo();
//        List<Grupo> expResult = null;
//        List<Grupo> result = instance.buscarGrupos();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        System.out.println(expResult + "-" + result);
//    }

//    /**
//     * Test of guardarCambios method, of class Grupo.
//     */
//    @Test
//    public void testGuardarCambios() {
//        System.out.println("guardarCambios");
//        Grupo grupo = null;
//        Grupo instance = new Grupo();
//        boolean expResult = false;
//        boolean result = instance.guardarCambios(grupo);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of guardarGrupo method, of class Grupo.
     */
    @Test
    public void testGuardarGrupo_CP_03() {
        System.out.println("guardarGrupo_CP_03");
        Grupo grupo = new Grupo();

        grupo.setTipoDeBaile("Salsa cubana");
        grupo.setIdColaborador(2);
        boolean expResult = false;
        boolean result = controlador.guardarGrupo(grupo);
        assertEquals(expResult, result);
        System.out.println(expResult + "-" + result);
    }
    
    /**
     * Test of guardarGrupo method, of class Grupo.
     */
    @Test
    public void testGuardarGrupo_CP_02() {
        System.out.println("guardarGrupo_CP_02");
        Grupo grupo = new Grupo();
        grupo.setCupo(40);

        grupo.setIdColaborador(2);
        boolean expResult = false;
        boolean result = controlador.guardarGrupo(grupo);
        assertEquals(expResult, result);
        System.out.println(expResult + "-" + result);
    }
    
    /**
     * Test of guardarGrupo method, of class Grupo.
     */
    @Test
    public void testGuardarGrupo_CP_01() {
        System.out.println("guardarGrupo_CP_01");
        Grupo grupo = new Grupo();
        grupo.setCupo(40);
        grupo.setTipoDeBaile("Salsa cubana");
        boolean expResult = false;
        boolean result = controlador.guardarGrupo(grupo);
        assertEquals(expResult, result);
        System.out.println(expResult + "-" + result);
    }
    
    /**
     * Test of guardarGrupo method, of class Grupo.
     */
    @Test
    public void testGuardarGrupo_CP_04() {
        System.out.println("guardarGrupo_CP_04");
        Grupo grupo = new Grupo();
        grupo.setIdColaborador(1);
        grupo.setCupo(40);
        grupo.setTipoDeBaile("Salsa cubana");
        boolean expResult = true;
        boolean result = controlador.guardarGrupo(grupo);
        assertEquals(expResult, result);
        System.out.println(expResult + "-" + result);
    }
    
}
