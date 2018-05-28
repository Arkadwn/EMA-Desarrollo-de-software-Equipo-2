/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import emaaredespacio.utilerias.Utileria;
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
public class UsuarioSistemaTest {

    public UsuarioSistemaTest() {
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
    public void testAutenticarSesion() throws Exception {
        System.out.println("Prueba de metodo de autenticar exitoso");
        UsuarioSistema user = new UsuarioSistema();
        user.setContrasenia(Utileria.encriptarContrasena("admin"));
        user.setUsuario("admin");
        boolean expResult = true;
        boolean result = new UsuarioSistema().autenticarSesion(user);
        assertEquals(expResult, result);
    }

    @Test
    public void testAutenticarSesion2() throws Exception {
        System.out.println("Prueba de metodo de autenticar fallido");
        UsuarioSistema user = new UsuarioSistema();
        user.setContrasenia(Utileria.encriptarContrasena("admin"));
        user.setUsuario("admin2");
        boolean expResult = false;
        boolean result = new UsuarioSistema().autenticarSesion(user);
        assertEquals(expResult, result);
    }

}
