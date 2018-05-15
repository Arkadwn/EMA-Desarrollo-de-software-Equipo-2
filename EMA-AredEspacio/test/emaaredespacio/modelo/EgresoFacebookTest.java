package emaaredespacio.modelo;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class EgresoFacebookTest {
    
    private IEgresoFacebook metodos;
    
    public EgresoFacebookTest() {
        metodos = new EgresoFacebook();
    }

    

    /**
     * Test of registrarEgreso method, of class EgresoFacebook.
     */
    @Test
    public void testRegistrarEgreso_CP_01() {
        System.out.println("registrarEgreso_CP_01");
        EgresoFacebook egreso = new EgresoFacebook();
        
        egreso.setCreador("Miguel Leonardo Jimenez Jimenez");
        egreso.setCosto(300.0);
        egreso.setDescripcion("Se promocionaron los nuevos cursos de danza árabe.");
        egreso.setFechaInicio("16/4/2018");
        egreso.setFechaFin("18/4/2018");
        
        egreso.setActiva(egreso.validarDisponibilidadDelEgreso(egreso.getFechaFin()));
        egreso.setLink("https://www.facebook.com/AredEspacio");
        egreso.setIdEgresoFacebook(null);
        boolean expResult = true;
        boolean result = metodos.registrarEgreso(egreso);
        assertEquals(expResult, result);
        System.out.println(expResult+"-"+result);
    }

    /**
     * Test of registrarEgreso method, of class EgresoFacebook.
     */
    @Test
    public void testRegistrarEgreso_CP_02() {
        System.out.println("registrarEgreso_CP_02");
        EgresoFacebook egreso = new EgresoFacebook();
        
        egreso.setCreador("Roberto Jimenez Jimenez");
        egreso.setCosto(3500.0);
        egreso.setDescripcion("Se promocionaron los nuevos cursos de danza contemporánea.");
        egreso.setFechaInicio("16/4/2018");
        egreso.setFechaFin("18/4/2018");
        
        egreso.setActiva(egreso.validarDisponibilidadDelEgreso(egreso.getFechaFin()));
        egreso.setLink("https://www.facebook.com/AredEspacio/comtemporanea");
        egreso.setIdEgresoFacebook(null);
        boolean expResult = true;
        boolean result = metodos.registrarEgreso(egreso);
        assertEquals(expResult, result);
        System.out.println(expResult+"-"+result);
    }
    
    /**
     * Test of registrarEgreso method, of class EgresoFacebook.
     */
    @Test
    public void testRegistrarEgreso_CP_03() {
        System.out.println("registrarEgreso_CP_03");
        EgresoFacebook egreso = new EgresoFacebook();
        
        egreso.setCreador("Mauricio Jiménez Jiménez");
        egreso.setCosto(1400.0);
        egreso.setDescripcion("Se promocionaron los nuevos cursos de valet.");
        egreso.setFechaInicio("20/5/2018");
        egreso.setFechaFin("1/7/2018");
        
        egreso.setActiva(egreso.validarDisponibilidadDelEgreso(egreso.getFechaFin()));
        egreso.setLink("https://www.facebook.com/AredEspacio/vale");
        egreso.setIdEgresoFacebook(null);
        boolean expResult = true;
        boolean result = metodos.registrarEgreso(egreso);
        assertEquals(expResult, result);
        System.out.println(expResult+"-"+result);
    }
    
    /**
     * Test of registrarEgreso method, of class EgresoFacebook.
     */
    @Test
    public void testRegistrarEgreso_CP_04() {
        System.out.println("registrarEgreso_CP_04");
        EgresoFacebook egreso = new EgresoFacebook();
        
        egreso.setCreador("Mauricio Jiménez Jiménez nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
        egreso.setCosto(1400.0);
        egreso.setDescripcion("Se promocionaron los nuevos cursos de valet.");
        egreso.setFechaInicio("20/5/2018");
        egreso.setFechaFin("1/7/2018");
        
        egreso.setActiva(egreso.validarDisponibilidadDelEgreso(egreso.getFechaFin()));
        egreso.setLink("https://www.facebook.com/AredEspacio/vale");
        egreso.setIdEgresoFacebook(null);
        boolean expResult = false;
        boolean result = metodos.registrarEgreso(egreso);
        assertEquals(expResult, result);
        System.out.println(expResult+"-"+result);
    }
    
    /**
     * Test of editarEgresoFacebook method, of class EgresoFacebook.
     */
    @Test
    public void testEditarEgresoFacebook_CP_01() {
        System.out.println("editarEgresoFacebook_CP_01");
        EgresoFacebook egreso = new EgresoFacebook();
        
        egreso.setCreador("Miguel Leonardo Jimenez Jimenez");
        egreso.setCosto(1300.0);
        egreso.setDescripcion("Se promocionaron los nuevos cursos de danza árabe.");
        
        egreso.setFechaInicio("16/4/2018");
        egreso.setFechaFin("19/4/2018");
        egreso.setActiva(egreso.validarDisponibilidadDelEgreso(egreso.getFechaFin()));
        egreso.setLink("https://www.facebook.com/AredEspacio");
        egreso.setIdEgresoFacebook(1);
        boolean expResult = true;
        boolean result = metodos.editarEgresoFacebook(egreso);
        assertEquals(expResult, result);
        System.out.println(expResult+"-"+result);
    }
    
    /**
     * Test of editarEgresoFacebook method, of class EgresoFacebook.
     */
    @Test
    public void testEditarEgresoFacebook_CP_02() {
        System.out.println("editarEgresoFacebook_CP_02");
        EgresoFacebook egreso = new EgresoFacebook();
        
        egreso.setCreador("Roberto Jimenez Perez");
        egreso.setCosto(4500.0);
        egreso.setDescripcion("Se promocionaron los nuevos cursos de danza contemporánea.");
        
        egreso.setFechaInicio("18/5/2018");
        egreso.setFechaFin("27/5/2018");
        egreso.setActiva(egreso.validarDisponibilidadDelEgreso(egreso.getFechaFin()));
        egreso.setLink("https://www.facebook.com/AredEspacio/comtemporanea/23");
        egreso.setIdEgresoFacebook(2);
        boolean expResult = true;
        boolean result = metodos.editarEgresoFacebook(egreso);
        assertEquals(expResult, result);
        System.out.println(expResult+"-"+result);
    }
    
    /**
     * Test of editarEgresoFacebook method, of class EgresoFacebook.
     */
    @Test
    public void testEditarEgresoFacebook_CP_03() {
        System.out.println("editarEgresoFacebook_CP_03");
        EgresoFacebook egreso = new EgresoFacebook();
        
        egreso.setCreador("Mauricio Jiménez Jiménez");
        egreso.setCosto(1400.0);
        egreso.setDescripcion("Se promocionaron los nuevos cursos de valet.");
        
        egreso.setFechaInicio("12/5/2018");
        egreso.setFechaFin("20/5/2018");
        egreso.setActiva(egreso.validarDisponibilidadDelEgreso(egreso.getFechaFin()));
        egreso.setLink("https://www.facebook.com/AredEspacio/valet");
        egreso.setIdEgresoFacebook(3);
        boolean expResult = true;
        boolean result = metodos.editarEgresoFacebook(egreso);
        assertEquals(expResult, result);
        System.out.println(expResult+"-"+result);
    }
    
    /**
     * Test of editarEgresoFacebook method, of class EgresoFacebook.
     */
    @Test
    public void testEditarEgresoFacebook_CP_04() {
        System.out.println("editarEgresoFacebook_CP_04");
        EgresoFacebook egreso = new EgresoFacebook();
        
        egreso.setCreador("Mauricio Jiménez Jiménez");
        egreso.setCosto(1400.0);
        egreso.setDescripcion("Se promocionaron los nuevos cursos de valet.");
        
        egreso.setFechaInicio("12/1/2018");
        egreso.setFechaFin("20/1/2018");
        egreso.setActiva(egreso.validarDisponibilidadDelEgreso(egreso.getFechaFin()));
        egreso.setLink("https://www.facebook.com/AredEspacio/valet");
        egreso.setIdEgresoFacebook(3);
        boolean expResult = true;
        boolean result = metodos.editarEgresoFacebook(egreso);
        assertEquals(expResult, result);
        System.out.println(expResult+"-"+result);
    }

    /**
     * Test of validarCampos method, of class EgresoFacebook.
     */
    @Test
    public void testValidarCampos_CP_01() {
        System.out.println("validarCampos_CP_01");
        EgresoFacebook egreso = new EgresoFacebook();
        
        egreso.setCreador("                   ");
        egreso.setCosto(1400.0);
        egreso.setDescripcion("Se promocionaron los nuevos cursos de valet.");
        
        egreso.setFechaInicio("12/1/2018");
        egreso.setFechaFin("20/1/2018");
        egreso.setActiva(egreso.validarDisponibilidadDelEgreso(egreso.getFechaFin()));
        egreso.setLink("https://www.facebook.com/AredEspacio/valet");
        egreso.setIdEgresoFacebook(3);
        
        
        boolean expResult = false;
        boolean[] result = metodos.validarCampos(egreso);
        assertEquals(expResult, result[2]);
        System.out.println(expResult+"-"+result[2]);
    }   
    
    /**
     * Test of validarCampos method, of class EgresoFacebook.
     */
    @Test
    public void testValidarCampos_CP_02() {
        System.out.println("validarCampos_CP_02");
        EgresoFacebook egreso = new EgresoFacebook();
        
        egreso.setCreador("Roberto Jimenez Perez");
        egreso.setCosto(-1.0);
        egreso.setDescripcion("Se p romocionaron los nuevos cursos de valet.");
        
        egreso.setFechaInicio("12/1/2018");
        egreso.setFechaFin("20/1/2018");
        egreso.setActiva(egreso.validarDisponibilidadDelEgreso(egreso.getFechaFin()));
        egreso.setLink("https://www.facebook.com/AredEspacio/valet");
        egreso.setIdEgresoFacebook(3);
        
        
        boolean expResult = false;
        boolean[] result = metodos.validarCampos(egreso);
        assertEquals(expResult, result[3]);
        System.out.println(expResult+"-"+result[3]);
    }
    
    /**
     * Test of validarCampos method, of class EgresoFacebook.
     */
    @Test
    public void testValidarCampos_CP_03() {
        System.out.println("validarCampos_CP_03");
        EgresoFacebook egreso = new EgresoFacebook();
        
        egreso.setCreador("Mauricio Jiménez Jiménez");
        egreso.setCosto(1400.0);
        egreso.setDescripcion("                ");
        
        egreso.setFechaInicio("12/1/2018");
        egreso.setFechaFin("20/1/2018");
        egreso.setActiva(egreso.validarDisponibilidadDelEgreso(egreso.getFechaFin()));
        egreso.setLink("https://www.facebook.com/AredEspacio/valet");
        egreso.setIdEgresoFacebook(3);
        
        
        boolean expResult = false;
        boolean[] result = metodos.validarCampos(egreso);
        assertEquals(expResult, result[0]);
        System.out.println(expResult+"-"+result[0]);
    }
    
    /**
     * Test of validarCampos method, of class EgresoFacebook.
     */
    @Test
    public void testValidarCampos_CP_04() {
        System.out.println("validarCampos_CP_04");
        EgresoFacebook egreso = new EgresoFacebook();
        
        egreso.setCreador("Mauricio Jiménez Jiménez");
        egreso.setCosto(1400.0);
        egreso.setDescripcion("Se promocionaron los nuevos cursos de valet.");
        
        egreso.setFechaInicio("12/1/2018");
        egreso.setFechaFin("20/1/2018");
        egreso.setActiva(egreso.validarDisponibilidadDelEgreso(egreso.getFechaFin()));
        egreso.setLink("                          ");
        egreso.setIdEgresoFacebook(3);
        
        
        boolean expResult = false;
        boolean[] result = metodos.validarCampos(egreso);
        assertEquals(expResult, result[1]);
        System.out.println(expResult+"-"+result[1]);
    }
    
}
