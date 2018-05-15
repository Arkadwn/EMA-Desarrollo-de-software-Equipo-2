/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;


import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class AlumnoTest {
    private IAlumno controlador;
    public AlumnoTest() {
        controlador = new Alumno();
    }
    

    /**
     * Test of guardarAlumno method, of class Alumno.
     */
    @Test
    public void testGuardarAlumno_CP_01() {
        System.out.println("guardarAlumno-CP-01");
        Alumno alumno = new Alumno();
        
        alumno.setNombre("Roberto");
        alumno.setApellidos("Arteaga Camacho");
        alumno.setCorreo("roj@gmail.com");
        alumno.setTelefono("2282270732");
        alumno.setDireccion("Rancho Viejo");
        alumno.setImagenPerfil("41109390.jpg");
        
        boolean resultadoEsperado = true;
        boolean resultado = controlador.guardarAlumno(alumno);
        assertEquals(resultadoEsperado, resultado);
        System.out.println(resultadoEsperado+" - " + resultado);
    }
    
    /**
     * Test of guardarAlumno method, of class Alumno.
     */
    @Test
    public void testGuardarAlumno_CP_02() {
        System.out.println("guardarAlumno-CP-02");
        Alumno alumno = new Alumno();
        
        alumno.setNombre("Roberto");
        alumno.setApellidos("Arteaga Camacho");
        alumno.setCorreo("rojgmail.com");
        alumno.setTelefono("2282270732");
        alumno.setDireccion("Rancho Viejo");
        alumno.setImagenPerfil("41109390.jpg");
        
        boolean resultadoEsperado = false;
        boolean[] resultado = controlador.validarCampos(alumno);
        assertEquals(resultadoEsperado, resultado[2]);
        System.out.println(resultadoEsperado+" - " + resultado[2]);
    }
    
    /**
     * Test of guardarAlumno method, of class Alumno.
     */
    @Test
    public void testGuardarAlumno_CP_03() {
        System.out.println("guardarAlumno-CP-03");
        Alumno alumno = new Alumno();
        
        alumno.setNombre("Roberto");
        alumno.setApellidos("Bustamante Zarate");
        alumno.setCorreo("arkwn@gmail.com");
        alumno.setTelefono("2282270732");
        alumno.setDireccion("Xalapa");
        alumno.setImagenPerfil("41109390.jpg");
        
        boolean resultadoEsperado = true;
        boolean resultado = controlador.guardarAlumno(alumno);
        assertEquals(resultadoEsperado, resultado);
        System.out.println(resultadoEsperado+" - " + resultado);
    }


    /**
     * Test of editarAlumo method, of class Alumno.
     */
    @Test
    public void testEditarAlumo_CP_01() {
        System.out.println("editarAlumo-CP-01");
        Alumno alumno = new Alumno();
        
        alumno.setNombre("Mauricio");
        alumno.setApellidos("Lima Sánchez");
        alumno.setCorreo("roj@gmail.com");
        alumno.setTelefono("2282270732");
        alumno.setDireccion("Rancho Viejo");
        alumno.setImagenPerfil("41109390.jpg");
        alumno.setEstado("A");
        alumno.setMatricula(1);
        boolean resultadoEsperado = true;
        boolean resultado = controlador.editarAlumo(alumno);
        assertEquals(resultadoEsperado, resultado);
        System.out.println(resultadoEsperado+" - " + resultado);
    }
    
    @Test
    public void testEditarAlumo_CP_02() {
        System.out.println("editarAlumo-CP-02");
        Alumno alumno = new Alumno();
        
        alumno.setNombre("Roberto");
        alumno.setApellidos("Arteaga Camacho");
        alumno.setCorreo("acdcmljj@gmail.com");
        alumno.setTelefono("2282270732");
        alumno.setDireccion("Rancho Viejo");
        alumno.setImagenPerfil("41109390.jpg");
        alumno.setEstado("A");
        alumno.setMatricula(2);
        boolean resultadoEsperado = true;
        boolean resultado = controlador.editarAlumo(alumno);
        assertEquals(resultadoEsperado, resultado);
        System.out.println(resultadoEsperado+" - " + resultado);
    }
    
    @Test
    public void testEditarAlumo_CP_03() {
        System.out.println("editarAlumo-CP-03");
        Alumno alumno = new Alumno();
        
        alumno.setNombre("Roberto");
        alumno.setApellidos("Arteaga Camacho");
        alumno.setCorreo("roj@gmail.com");
        alumno.setTelefono("2282270732");
        alumno.setDireccion("Rancho Viejo");
        alumno.setImagenPerfil("41109390.jpg");
        alumno.setEstado("B");
        alumno.setMatricula(1);
        boolean resultadoEsperado = true;
        boolean resultado = controlador.editarAlumo(alumno);
        assertEquals(resultadoEsperado, resultado);
        System.out.println(resultadoEsperado+" - " + resultado);
    }
}
