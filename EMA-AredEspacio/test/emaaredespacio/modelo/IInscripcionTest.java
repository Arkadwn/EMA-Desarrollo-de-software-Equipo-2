package emaaredespacio.modelo;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class IInscripcionTest {
    
    private IInscripcion controlador;
    
    public IInscripcionTest() {
        controlador = new Inscripcion();
    }

    /**
     * Test of inscribirAlumno method, of class IInscripcion.
     */
    @Test
    public void testInscribirAlumno_CP_01() {
        System.out.println("inscribirAlumno_Inscripcion exitosa");
        Inscripcion inscripcion = new Inscripcion(1, 3, 100, 500, "29/01/2018");
        boolean expResult = false;
        boolean result = controlador.inscribirAlumno(inscripcion);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of inscribirAlumno method, of class IInscripcion.
     */
    @Test
    public void testInscribirAlumno_CP_02() {
        System.out.println("InscribirAlumno_Alumno que no existe");
        Inscripcion inscripcion = new Inscripcion(19, 100, 100, 500, "29/01/2018");
        boolean expResult = false;
        boolean result = controlador.inscribirAlumno(inscripcion);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of inscribirAlumno method, of class IInscripcion.
     */
    @Test
    public void testInscribirAlumno_CP_03() {
        System.out.println("InscribirAlumno_inscribir un alumno de un grupo que no existe");
        Inscripcion inscripcion = new Inscripcion(1, 3, 100, 500, "29/01/2018");
        boolean expResult = false;
        boolean result = controlador.inscribirAlumno(inscripcion);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of inscribirAlumno method, of class IInscripcion.
     */
    @Test
    public void testInscribirAlumno_CP_04() {
        System.out.println("InscribirAlumno_Inscribr alumno que ya esta inscrito");
        Inscripcion inscripcion = new Inscripcion(18, 2, 100, 500, "29/01/2018");
        boolean expResult = false;
        boolean result = controlador.inscribirAlumno(inscripcion);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of darDeBajaAlumno method, of class IInscripcion.
     */
    @Test
    public void testDarDeBajaAlumno_CP_01() {
        System.out.println("darDeBajaAlumno_Dar de baja un alumno");
        boolean expResult = false;
        boolean result = controlador.darDeBajaAlumno(1, 3);
        assertEquals(expResult, result);
    }

    /**
     * Test of darDeBajaAlumno method, of class IInscripcion.
     */
    @Test
    public void testDarDeBajaAlumno_CP_02() {
        System.out.println("darDeBajaAlumno_Dar de baja un alumno que ya esta dado de baja");
        IInscripcion instance = new Inscripcion();
        boolean expResult = false;
        boolean result = instance.darDeBajaAlumno(1, 1);
        assertEquals(expResult, result);
    }

    /**
     * Test of sacarInscripcionesDeGrupo method, of class IInscripcion.
     */
    @Test
    public void testSacarInscripcionesDeGrupo() {
        System.out.println("sacarInscripcionesDeGrupo");
        int idGrupo = 0;
        List<Alumno> expResult = new ArrayList<>();
        List<Alumno> result = controlador.sacarInscripcionesDeGrupo(idGrupo);
        assertEquals(expResult.isEmpty(), result.isEmpty());
    } 
}
