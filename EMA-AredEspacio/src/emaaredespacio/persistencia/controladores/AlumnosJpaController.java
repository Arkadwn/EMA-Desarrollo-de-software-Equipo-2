package emaaredespacio.persistencia.controladores;

import emaaredespacio.persistencia.entidad.Alumnos;
import emaaredespacio.utilerias.Imagen;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 1/04/2018
 * @time 07:08:52 PM
 */
public class AlumnosJpaController implements IControladorAlumnos {

    public AlumnosJpaController() {
        this.fabricaEntidad = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
    }
    private EntityManagerFactory fabricaEntidad = null;

    /**
     * Getter de la variable fabricaEntidad.
     *
     * @return fabricaEntidad.
     */
    private EntityManager getEntityManager() {
        return fabricaEntidad.createEntityManager();
    }

    @Override
    public boolean guardarAlumno(Alumnos alumno, File imagen) {
        boolean validacion = true;

        EntityManager conexion = getEntityManager();
        EntityTransaction transaccion = null;
        try {
            transaccion = conexion.getTransaction();

            transaccion.begin();

            conexion.persist(alumno);

            transaccion.commit();
            
            transaccion = conexion.getTransaction();
            transaccion.begin();
            alumno.setImagen(alumno.getMatricula()+".jpg");
            transaccion.commit();
            
            Imagen.moverImagen(imagen, ""+alumno.getImagen(), Imagen.ALUMNO);
            
        } catch (RollbackException ex) {
            Logger.getLogger(AlumnosJpaController.class.getName()).log(Level.SEVERE, null, ex);
            if (transaccion.isActive()) {
                transaccion.rollback();
            }
            validacion = false;
        } finally {
            if (conexion != null) {
                conexion.close();
            }
        }

        return validacion;
    }

    @Override
    public boolean editarAlumno(Alumnos alumno) {
        boolean validacion = true;
        EntityManager conexion = getEntityManager();
        EntityTransaction transaccion = null;
        Alumnos alumnoActual;

        try {
            transaccion = conexion.getTransaction();
            transaccion.begin();
            alumnoActual = conexion.find(Alumnos.class, alumno.getMatricula());

            alumnoActual.setApellidos(alumno.getApellidos());
            alumnoActual.setNombre(alumno.getNombre());
            alumnoActual.setCorreo(alumno.getCorreo());
            alumnoActual.setDireccion(alumno.getDireccion());
            alumnoActual.setEstado(alumno.getEstado());
            alumnoActual.setImagen(alumno.getImagen());
            alumnoActual.setTelefono(alumno.getTelefono());

            transaccion.commit();
        } catch (Exception ex) {
            if (transaccion.isActive()) {
                transaccion.rollback();
            }
            validacion = false;
        } finally {
            if (conexion != null) {
                conexion.close();
            }
        }

        return validacion;
    }

    @Override
    public List<Alumnos> buscarAlumno(String palabraClave) {
        List<Alumnos> alumnos = new ArrayList();
        String palabra = "%" + palabraClave + "%";
        EntityManager conexion = getEntityManager();
        try {
            alumnos = conexion.createQuery("SELECT a FROM Alumnos a WHERE a.nombre LIKE :palabra OR a.apellidos LIKE :palabra").setParameter("palabra", palabra).getResultList();
        } finally {
            if (conexion != null) {
                conexion.close();
            }
        }
        return alumnos;
    }

    public List<Alumnos> sacarAlumnosNoInscritos(int idGrupo) {
        List<Alumnos> alumnos = new ArrayList();
        EntityManager conexion = getEntityManager();
        try {
            alumnos = conexion.createQuery("SELECT a FROM Alumnos a where a not in (select a from Alumnos a, Inscripciones i where a = i.idAlumno and i.idGrupo.idGrupo = :idGrupo) ")
                    .setParameter("idGrupo", idGrupo).getResultList();
        } finally {
            if (conexion != null) {
                conexion.close();
            }
        }
        return alumnos;
    }
    
    public Alumnos buscarAlumnoPorId(int idAlumno){
        Alumnos alumno = null;
        EntityManager conexion = getEntityManager();
        try{
            alumno = conexion.find(Alumnos.class, idAlumno);
        }finally{
            if(conexion != null){
                conexion.close();
            }
        }
        
        return alumno;
    }
}
