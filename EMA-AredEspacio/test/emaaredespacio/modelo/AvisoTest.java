/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import emaaredespacio.gui.controlador.FXMLMenuPrincipalController;
import emaaredespacio.utilerias.EditorDeFormatos;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author enriq
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AvisoTest {
    public AvisoTest() {
    }

    /**
     * Test of buscarPagosVencidos method, of class Aviso.
     */
    @Test
    public void test_CP_01_BuscarPagosVencidos() {
        System.out.println("CP_01_buscarPagosVencidos");
        PagoAlumno instance = new PagoAlumno();
        Colaborador colaborador = new Colaborador();
        colaborador.setIdColaborador(1);
        List<PagoAlumno> result = new ArrayList();
        result = instance.buscarPagosVencidos(colaborador);
        int expResult = 0;
        assertEquals(expResult, result.size());
        System.out.println(expResult + " - " + result.size());
    }

    /**
     * Test of buscarPagosVencidos method, of class Aviso.
     */
    @Test
    public void test_CP_02_BuscarPagosVencidos() {
        System.out.println("CP_02_buscarPagosVencidos");
        IPagoAlumno instance = new PagoAlumno();
        PagoAlumno pagoVencido = new PagoAlumno();
        Date date = new GregorianCalendar(2018, Calendar.APRIL, 23).getTime();
        pagoVencido.setFechaPago(EditorDeFormatos.crearFormatoFecha(date));
        pagoVencido.setIdGrupo(1);
        pagoVencido.setMatricula(1);
        pagoVencido.setTipoPago("Mensualidad");
        pagoVencido.setTotal("500");
        pagoVencido.setMonto("500");
        pagoVencido.setPorcentajeDescuento(0);
        new PagoAlumno().registrarPago(pagoVencido);
        Colaborador colaborador = new Colaborador();
        colaborador.setIdColaborador(2);
        List<PagoAlumno> result = new ArrayList();
        result = instance.buscarPagosVencidos(colaborador);
        int expResult = 1;
        assertEquals(expResult, result.size());
        System.out.println(expResult + " - " + result.size());
    }
    
    /**
     * Test of buscarPagosVencidos method, of class Aviso.
     */
    @Test
    public void test_CP_03_BuscarPagosVencidos() {
        System.out.println("CP_03_buscarPagosVencidos");
        IPagoAlumno instance = new PagoAlumno();
        PagoAlumno pagoVencido = new PagoAlumno();
        Date date = new GregorianCalendar(2018, Calendar.JUNE,31).getTime();
        pagoVencido.setFechaPago(EditorDeFormatos.crearFormatoFecha(date));
        pagoVencido.setIdGrupo(1);
        pagoVencido.setMatricula(2);
        pagoVencido.setTipoPago("Mensualidad");
        pagoVencido.setTotal("500");
        pagoVencido.setMonto("500");
        pagoVencido.setPorcentajeDescuento(0);
        new PagoAlumno().registrarPago(pagoVencido);
        Colaborador colaborador = new Colaborador();
        colaborador.setIdColaborador(2);
        List<PagoAlumno> result = new ArrayList();
        result = instance.buscarPagosVencidos(colaborador);
        int expResult = 1;
        assertEquals(expResult, result.size());
        System.out.println(expResult + " - " + result.size());
    }

    /**
     * Test of buscarNombreDePagosVencidos method, of class Aviso.
     */
    @Test
    public void test_CP_04_BuscarNombreDePagosVencidos() {
        System.out.println("CP_04_buscarNombreDePagosVencidos");
        List<PagoAlumno> listaPago = new ArrayList();
        PagoAlumno pagoVencido = new PagoAlumno();
        Colaborador colaborador = new Colaborador();
        colaborador.setIdColaborador(2);
        listaPago = pagoVencido.buscarPagosVencidos(colaborador);
        int expResult = listaPago.get(0).getMatricula();
        Alumno alumno = new Alumno();
        IAlumno instance = new Alumno();
        alumno = instance.buscarAlumnoPorId(listaPago.get(0).getMatricula());
        int result = alumno.getMatricula();
        assertEquals(expResult,result);
        System.out.println(expResult + " - " + result);
    }

    /**
     * Test of buscarGruposDePagosVencidos method, of class Aviso.
     */
    @Test
    public void test_CP_05_BuscarGruposDePagosVencidos() {
        System.out.println("CP_05_buscarGruposDePagosVencidos");
        Colaborador colaborador = new Colaborador();
        colaborador.setIdColaborador(2);
        List<PagoAlumno> listaPago = new ArrayList();
        PagoAlumno pagoVencido = new PagoAlumno(); 
        listaPago = pagoVencido.buscarPagosVencidos(colaborador);
        int expResult = listaPago.get(0).getIdGrupo();
        Grupo grupo = new Grupo();
        IGrupo controlador = new Grupo();
        grupo = controlador.buscarGrupoPorId(expResult);
        int result = grupo.getIdGrupo();
        assertEquals(expResult,result);
        System.out.println(expResult + " - " + result);
    }

    /**
     * Test of buscarPagosVencidosDeMaestros method, of class Aviso.
     */
    @Test
    public void test_CP_06_BuscarPagosVencidosDeMaestros() {
        System.out.println("CP_06_buscarPagosVencidosDeMaestros");
        List<Colaborador> colaboradoresActivos = new Colaborador().buscarColaboradoresEstados("A");
        Ingreso ingreso;
        Aviso aviso;
        List<Aviso> listaDeAvisos = new ArrayList();
        for (int i = 0; i < colaboradoresActivos.size(); i++) {
            ingreso = new Ingreso().buscarUltimoPagoColaborador(colaboradoresActivos.get(i).getIdColaborador());
            if (ingreso != null) {
                Date fecha = null;
                try {
                    fecha = new SimpleDateFormat("yyyy-MM-dd").parse(ingreso.getFecha());
                } catch (ParseException ex) {
                    Logger.getLogger(Aviso.class.getName()).log(Level.SEVERE, null, ex);
                }
                LocalDate date = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (date.plusMonths(1).isBefore(LocalDate.now()) || date.plusMonths(1).isEqual(LocalDate.now())) {
                    aviso = new Aviso();
                    aviso.setNombre(colaboradoresActivos.get(i).getNombreCompleto());
                    aviso.setTipoDePago("Mensualidad");
                    listaDeAvisos.add(aviso);
                    System.out.println(aviso.getNombre());
                }
            }
        }
        int expResult = 0;
        int result = listaDeAvisos.size();
        assertEquals(expResult,result);
        System.out.println(expResult + " - " + result);
    }
}
