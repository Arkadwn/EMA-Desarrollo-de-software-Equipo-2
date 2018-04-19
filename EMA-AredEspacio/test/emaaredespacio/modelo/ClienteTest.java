/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author enriq
 */
public class ClienteTest {

    private ICliente controlador;

    public ClienteTest() {
        controlador = new Cliente();
    }
    
    /**
     * Test of guardarDatos method, of class Cliente.
     */
    @Test
    public void testGuardarDatos_CP_01() {
        System.out.println("guardarDatos_CP_01");
        Cliente cliente = new Cliente();
        cliente.setId("4");
        cliente.setNombre("Carla Flores");
        cliente.setTelefono("2281337698");
        cliente.setCorreoElectronico("carlaj@hotmail.com");
        cliente.setDireccion("Av. Xalapa 233");
        cliente.setEstado("B");
        cliente.setImagenPerfil("big_thumb.jpg");
        boolean expResult = true;
        boolean result = controlador.guardarDatos(cliente);
        assertEquals(expResult, result);
        System.out.println(expResult + "-" + result);
        
    }
    
     /**
     * Test of guardarDatos method, of class Cliente.
     */
    @Test
    public void testGuardarDatos_CP_02() {
        System.out.println("guardarDatos_CP_02");
        Cliente cliente = new Cliente();
        cliente.setId("2");
        cliente.setNombre("Monica Jimenez");
        cliente.setTelefono("92115533540");
        cliente.setCorreoElectronico("moji@hotmail.com");
        cliente.setDireccion("Veracruz 110 Col. indeco");
        cliente.setEstado("B");
        cliente.setImagenPerfil("big_thumb.jpg");
        boolean expResult = false;
        boolean result = controlador.guardarDatos(cliente);
        assertEquals(expResult, result);
        System.out.println(expResult + "-" + result);
        
    }


    /**
     * Test of buscarCliente method, of class Cliente.
     */
    @Test
    public void testBuscarCliente_CP_01() {
        System.out.println("buscarCliente_CP_01");
        String nombre = "carla";
        String id = "4";
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setId(id);
        List<Cliente> expResult = new ArrayList();
        expResult.add(cliente);
        List<Cliente> result = controlador.buscarCliente(nombre);
        assertEquals(expResult.get(0).getId(), result.get(0).getId());
        // TODO review the generated test code and remove the default call to fail.
        System.out.println(expResult.get(0).getId() + "-" + result.get(0).getId());
    }
    
    /**
     * Test of buscarCliente method, of class Cliente.
     */
    @Test
    public void testBuscarCliente_CP_02() {
        System.out.println("buscarCliente_CP_02");
        String nombre = "pepe";
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        List<Cliente> expResult = new ArrayList();
        List<Cliente> result = controlador.buscarCliente(nombre);
        assertEquals(expResult.size(), result.size());
        System.out.println(expResult.size() + "-" + result.size());
    }

    /**
     * Test of guardarCliente method, of class Cliente.
     */
    @Test
    public void testGuardarCliente_CP_01() {
        System.out.println("guardarCliente_CP_01");
        Cliente cliente = new Cliente();
        cliente.setNombre("Daniel Barroso Perez");
        cliente.setEstado("A");
        cliente.setCorreoElectronico("danielbp@hotmail.com");
        cliente.setImagenPerfil("41109390.jpg");
        cliente.setTelefono("22888132000");
        cliente.setDireccion("Plutarco Elias Calles 9A");
        boolean expResult = false;
        boolean result = controlador.guardarCliente(cliente);
        assertEquals(expResult, result);
        System.out.println(expResult + "-" + result);
    }

    /**
     * Test of guardarCliente method, of class Cliente.
     */
    @Test
    public void testGuardarCliente_CP_02() {
        System.out.println("guardarCliente_CP_02");
        Cliente cliente = new Cliente();
        cliente.setEstado("A");
        cliente.setCorreoElectronico("danielbp@hotmail.com");
        cliente.setImagenPerfil("41109390.jpg");
        cliente.setTelefono("2288813200");
        cliente.setDireccion("Plutarco Elias Calles 9A");
        boolean expResult = false;
        boolean result = controlador.guardarCliente(cliente);
        assertEquals(expResult, result);
        System.out.println(expResult + "-" + result);
    }

    /**
     * Test of guardarCliente method, of class Cliente.
     */
    @Test
    public void testGuardarCliente_CP_03() {
        System.out.println("guardarCliente_CP_03");
        Cliente cliente = new Cliente();
        cliente.setNombre("Daniel Barroso Perez");

        cliente.setImagenPerfil("41109390.jpg");
        cliente.setTelefono("2288813200");
        cliente.setDireccion("Plutarco Elias Calles 9A");
        boolean expResult = false;
        boolean result = controlador.guardarCliente(cliente);
        assertEquals(expResult, result);
        System.out.println(expResult + "-" + result);
    }
    
    /**
     * Test of guardarCliente method, of class Cliente.
     */
    @Test
    public void testGuardarCliente_CP_04() {
        System.out.println("guardarCliente_CP_04");
        Cliente cliente = new Cliente();
        cliente.setNombre("Daniel Barroso Perez");
        cliente.setCorreoElectronico("danielbp@hotmail.com");
        
        cliente.setTelefono("2288813200");
        cliente.setDireccion("Plutarco Elias Calles 9A");
        boolean expResult = false;
        boolean result = controlador.guardarCliente(cliente);
        assertEquals(expResult, result);
        System.out.println(expResult + "-" + result);
    }
    
       /**
     * Test of guardarCliente method, of class Cliente.
     */
    @Test
    public void testGuardarCliente_CP_05() {
        System.out.println("guardarCliente_CP_05");
        Cliente cliente = new Cliente();
        cliente.setNombre("Daniel Barroso Perez");
        cliente.setCorreoElectronico("danielbp@hotmail.com");
        cliente.setImagenPerfil("41109390.jpg");

        cliente.setDireccion("Plutarco Elias Calles 9A");
        boolean expResult = false;
        boolean result = controlador.guardarCliente(cliente);
        assertEquals(expResult, result);
        System.out.println(expResult + "-" + result);
    }
    
       /**
     * Test of guardarCliente method, of class Cliente.
     */
    @Test
    public void testGuardarCliente_CP_06() {
        System.out.println("guardarCliente_CP_06");
        Cliente cliente = new Cliente();
        cliente.setNombre("Daniel Barroso Perez");
        cliente.setCorreoElectronico("danielbp@hotmail.com");
        cliente.setImagenPerfil("41109390.jpg");
        cliente.setTelefono("2288813200");

        boolean expResult = false;
        boolean result = controlador.guardarCliente(cliente);
        assertEquals(expResult, result);
        System.out.println(expResult + "-" + result);
    }
    
       /**
     * Test of guardarCliente method, of class Cliente.
     */
    @Test
    public void testGuardarCliente_CP_07() {
        System.out.println("guardarCliente_CP_07");
        Cliente cliente = new Cliente();
        cliente.setNombre("Daniel Barroso Perez");
        cliente.setCorreoElectronico("danielbp@hotmail.com");
        cliente.setImagenPerfil("41109390.jpg");
        cliente.setTelefono("2288813200");
        cliente.setDireccion("Plutarco Elias Calles 9A");
        boolean expResult = true;
        boolean result = controlador.guardarCliente(cliente);
        assertEquals(expResult, result);
        System.out.println(expResult + "-" + result);
    }
    
       /**
     * Test of buscarClienteRelacionado method, of class Cliente.
     */
    @Test
    public void testBuscarClienteRelacionado_CP_01(){
        System.out.println("buscarClienteRelacionado_CP_01");
        Cliente cliente = new Cliente();
        boolean expResult = true;
        String nombre = "Daniel";
        List<Cliente> result = controlador.buscarClienteRelacionado(nombre);
        assertEquals(expResult,!result.isEmpty());
        System.out.println(expResult+"-"+!result.isEmpty());
    }
    
       /**
     * Test of buscarClienteRelacionado method, of class Cliente.
     */
    @Test
    public void testBuscarClienteRelacionado_CP_02(){
        System.out.println("buscarClienteRelacionado_CP_02");
        Cliente cliente = new Cliente();
        boolean expResult = true;
        String nombre = "aasddasdasd";
        List<Cliente> result = controlador.buscarClienteRelacionado(nombre);
        assertEquals(expResult,result.isEmpty());
        System.out.println(expResult+"-"+result.isEmpty());
    }
    
       /**
     * Test of buscarClienteRelacionado method, of class Cliente.
     */
    @Test
    public void testValidarFormatoCorreo_CP_01(){
        System.out.println("validarFormatoCorreo_CP_01");
        String correo = "oaskdaosd";
        boolean expResult = false;
        boolean result = controlador.validarFormatoCorreo(correo);
        assertEquals(expResult,result);
        System.out.println(expResult+"-"+result);
    }
    
       /**
     * Test of buscarClienteRelacionado method, of class Cliente.
     */
    @Test
    public void testValidarFormatoCorreo_CP_02(){
        System.out.println("validarFormatoCorreo_CP_02");
        String correo = "emilio@gmail.com";
        boolean expResult = true;
        boolean result = controlador.validarFormatoCorreo(correo);
        assertEquals(expResult,result);
        System.out.println(expResult+"-"+result);
    }
    
}
