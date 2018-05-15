/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class ColaboradorTest {

    private IColaborador controlador;

    public ColaboradorTest() {
        controlador = new Colaborador();
    }

    /**
     * Test of registrarColaborador method, of class Colaborador.
     */
    @Test
    public void testRegistrarColaborador_CP_01() {
        System.out.println("registrarColaborador-CP-01");
        
        Colaborador colaborador = new Colaborador();
        colaborador.setNombre("Manuel");
        colaborador.setApellidos("Jimenez Jimenez");
        colaborador.setContraseña("manuel64Mella");
        colaborador.setTelefono("2281301414");
        colaborador.setCorreo("manuel@gmail.com");
        colaborador.setDireccion("Rancho Viejo");
        colaborador.setImagenPerfil("20180319_094545.jpg");
        colaborador.setTipoPago("Quinsenal");
        colaborador.setMontoAPagar("400");
        colaborador.setNombreUsuario("Manu");
        boolean resultadoEsperado = true;
        boolean resultado = controlador.registrarColaborador(colaborador);
        assertEquals(resultadoEsperado, resultado);
        System.out.println(resultadoEsperado+" - " + resultado);
    }
    /**
     * Test of validarCampos method, of class Colaborador.
     */
    @Test
    public void testRegistrarColaborador_CP_02() {
        System.out.println("registrarColaborador-CP-02");
        Colaborador colaborador = new Colaborador();
        colaborador.setNombre("Manuel");
        colaborador.setApellidos("Jimenez Jimenez");
        colaborador.setContraseña("manuel64Mella");
        colaborador.setTelefono("2281301414");
        colaborador.setCorreo("manuel@gmail.com");
        colaborador.setDireccion("Rancho Viejo");
        colaborador.setImagenPerfil("20180319_094545.jpg");
        colaborador.setTipoPago("Quinsenal");
        colaborador.setMontoAPagar("400");
        colaborador.setNombreUsuario("Manu");
        String contraseña = "miguelLeonardo64";
        Colaborador instance = new Colaborador();
        boolean resultadoEsperado = false;
        boolean[] resultado = instance.validarCampos(colaborador, contraseña);
        assertEquals(resultadoEsperado, resultado[6]);
        System.out.println(resultadoEsperado + " - " + resultado[6]);

    }

    /**
     * Test of validarCampos method, of class Colaborador.
     */
    @Test
    public void testRegistrarColaborador_CP_03() {
        System.out.println("registrarColaborador-CP-03");
        Colaborador colaborador = new Colaborador();
        colaborador.setNombre("Manuel");
        colaborador.setApellidos("Jimenez Jimenez");
        colaborador.setContraseña("miguel64");
        colaborador.setTelefono("2281301414");
        colaborador.setCorreo("manuel@gmail.com");
        colaborador.setDireccion("Rancho Viejo");
        colaborador.setImagenPerfil("20180319_094545.jpg");
        colaborador.setTipoPago("Quinsenal");
        colaborador.setMontoAPagar("400");
        colaborador.setNombreUsuario("Manu");
        String contraseña = "miguelLeonardo64";
        Colaborador instance = new Colaborador();
        boolean resultadoEsperado = false;
        boolean[] resultado = instance.validarCampos(colaborador, contraseña);
        assertEquals(resultadoEsperado, resultado[7]);
        System.out.println(resultadoEsperado + " - " + resultado[7]);

    }

    /**
     * Test of validarCampos method, of class Colaborador.
     */
    @Test
    public void testRegistrarColaborador_CP_04() {
        System.out.println("registrarColaborador-CP-04");
        Colaborador colaborador = new Colaborador();
        colaborador.setNombre("Manuel");
        colaborador.setApellidos("Jimenez Jimenez");
        colaborador.setContraseña("miguel64");
        colaborador.setTelefono("2281301414");
        colaborador.setCorreo("manuelgmail.com");
        colaborador.setDireccion("Rancho Viejo");
        colaborador.setImagenPerfil("20180319_094545.jpg");
        colaborador.setTipoPago("Quinsenal");
        colaborador.setMontoAPagar("400");
        colaborador.setNombreUsuario("Manu");
        String contraseña = "miguelLeonardo64";
        Colaborador instance = new Colaborador();
        boolean resultadoEsperado = false;
        boolean[] resultado = instance.validarCampos(colaborador, contraseña);
        assertEquals(resultadoEsperado, resultado[3]);
        System.out.println(resultadoEsperado + " - " + resultado[3]);
    }
    
    /**
     * Test of registrarColaborador method, of class Colaborador.
     */
    @Test
    public void testRegistrarColaborador_CP_05() {
        System.out.println("registrarColaborador-CP-05");
        
        Colaborador colaborador = new Colaborador();
        colaborador.setNombre("Andres");
        colaborador.setApellidos("Zarate Flores");
        colaborador.setContraseña("s150qweqasda");
        colaborador.setTelefono("2281301414213121231");
        colaborador.setCorreo("arkwn@gmail.com");
        colaborador.setDireccion("Xalapa");
        colaborador.setImagenPerfil("20180319_094545.jpg");
        colaborador.setTipoPago("Quinsenal");
        colaborador.setMontoAPagar("400");
        colaborador.setNombreUsuario("Andres");
        boolean resultadoEsperado = false;
        boolean resultado = controlador.registrarColaborador(colaborador);
        assertEquals(resultadoEsperado, resultado);
        System.out.println(resultadoEsperado+" - " + resultado);
    }

    /**
     * Test of registrarColaborador method, of class Colaborador.
     */
    @Test
    public void testRegistrarColaborador_CP_06() {
        System.out.println("registrarColaborador-CP-06");
        
        Colaborador colaborador = new Colaborador();
        colaborador.setNombre("Andres");
        colaborador.setApellidos("Zarate Flores");
        colaborador.setContraseña("s150qweqasda");
        colaborador.setTelefono("2281301414");
        colaborador.setCorreo("arkwn@gmail.com");
        colaborador.setDireccion("Xalapa");
        colaborador.setImagenPerfil("20180319_094545.jpg");
        colaborador.setTipoPago("Quinsenal");
        colaborador.setMontoAPagar("400");
        colaborador.setNombreUsuario("Andres");
        boolean resultadoEsperado = true;
        boolean resultado = controlador.registrarColaborador(colaborador);
        assertEquals(resultadoEsperado, resultado);
        System.out.println(resultadoEsperado+" - " + resultado);
    }
    
    /**
     * Test of registrarColaborador method, of class Colaborador.
     */
    @Test
    public void testRegistrarColaborador_CP_07() {
        System.out.println("registrarColaborador-CP-07");
        
        Colaborador colaborador = new Colaborador();
        colaborador.setNombre("Sarai");
        colaborador.setApellidos("Castillo Hernandez");
        colaborador.setContraseña("Amarillo123");
        colaborador.setTelefono("2281152023");
        colaborador.setCorreo("zara@gmail.com");
        colaborador.setDireccion("Miradores");
        colaborador.setImagenPerfil("20180319_094545.jpg");
        colaborador.setTipoPago("Mensual");
        colaborador.setMontoAPagar("1200");
        colaborador.setNombreUsuario("Zara");
        boolean resultadoEsperado = true;
        boolean resultado = controlador.registrarColaborador(colaborador);
        assertEquals(resultadoEsperado, resultado);
        System.out.println(resultadoEsperado+" - " + resultado);
    }
    
    /**
     * Test of buscarColaborador method, of class Colaborador.
     */
    @Test
    public void testBuscarColaborador_CP_01() {
        System.out.println("buscarColaborador_CP_01");
        
        boolean resultadoEsperado = true;
        List<Colaborador> resultado = controlador.buscarColaborador("lksdjflsdklnsdjksjdnks");
        assertEquals(resultadoEsperado, resultado.isEmpty());
    }

    /**
     * Test of buscarColaborador method, of class Colaborador.
     */
    @Test
    public void testBuscarColaborador_CP_02() {
        System.out.println("buscarColaborador_CP_01");
        
        boolean resultadoEsperado = true;
        List<Colaborador> resultado = controlador.buscarColaborador("Sarai");
        assertEquals(resultadoEsperado, resultado.isEmpty());
    }
    /**
     * Test of editarColaborador method, of class Colaborador.
     */
    @Test
    public void testEditarColaborador_CP_01() {
        System.out.println("editarColaborador-CP-01");
        Colaborador colaborador = new Colaborador();
        colaborador.setNombre("Eduardo");
        colaborador.setApellidos("Jiménez Mella");
        colaborador.setContraseña("manuel64Mella");
        colaborador.setTelefono("2281301414");
        colaborador.setCorreo("manuel@gmail.com");
        colaborador.setDireccion("Rancho Viejo");
        colaborador.setImagenPerfil("20180319_094545.jpg");
        colaborador.setTipoPago("Quinsenal");
        colaborador.setMontoAPagar("400");
        colaborador.setNombreUsuario("Manu");
        colaborador.setIdUsuario(1);
        colaborador.setIdColaborador(1);
        colaborador.setEstado("A");
        boolean nuevaContraseña = true;
        boolean resultadoEsperado = true;
        boolean resultado = controlador.editarColaborador(colaborador, nuevaContraseña);
        assertEquals(resultadoEsperado, resultado);
        System.out.println(resultadoEsperado+" - " + resultado);
    }
        

}
