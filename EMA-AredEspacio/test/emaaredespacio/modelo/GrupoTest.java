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

    /**
     * Test of buscarGrupos method, of class Grupo.
     */
    @Test
    public void testBuscarGrupoPorId_CP_01() {
        System.out.println("buscarGrupoPorId_CP_01");
        int expResult = 1;
        int id=1;
        Grupo result = controlador.buscarGrupoPorId(id);
        assertEquals(expResult, result.getIdGrupo());
        // TODO review the generated test code and remove the default call to fail.
        System.out.println(expResult + "-" + result);
    }


    /**
     * Test of guardarCambios method, of class Grupo.
     */
    @Test
    public void testGuardarCambios_CP_01() {
        System.out.println("guardarCambios_CP-01");
        Grupo grupo = new Grupo();
        grupo.setIdColaborador(1);
        grupo.setTipoDeBaile("Salsa3");
        grupo.setCupo(10);
        grupo.setIdGrupo(1);
        grupo.setEstado("B");
        boolean expResult = true;
        boolean result = controlador.guardarCambios(grupo);
        assertEquals(expResult, result);
        System.out.println(expResult+"-"+result);
    }
    
    /**
     * Test of guardarCambios method, of class Grupo.
     */
    @Test
    public void testGuardarCambios_CP_02() {
        System.out.println("guardarCambios_CP-02");
        Grupo grupo = new Grupo();
        grupo.setIdColaborador(1);
        grupo.setIdGrupo(1);
        grupo.setEstado("A");
        boolean expResult = false;
        boolean result = controlador.guardarCambios(grupo);
        assertEquals(expResult, result);
        System.out.println(expResult+"-"+result);
    }

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
