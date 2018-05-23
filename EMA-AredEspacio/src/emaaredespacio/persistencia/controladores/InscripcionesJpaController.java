package emaaredespacio.persistencia.controladores;

import java.io.Serializable;
import emaaredespacio.persistencia.entidad.Alumnos;
import emaaredespacio.persistencia.entidad.Grupos;
import emaaredespacio.persistencia.entidad.Inscripciones;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 30/04/2018
 * @time 01:13:25 AM
 */
public class InscripcionesJpaController implements Serializable {

    public InscripcionesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean create(Inscripciones inscripciones) {
        EntityManager em = null;
        boolean validacion = true;

        if (buscarInscripcionPorGrupo(inscripciones.getIdAlumno().getMatricula(), inscripciones.getIdGrupo().getIdGrupo()) == null) {
            try {
                em = getEntityManager();
                em.getTransaction().begin();
                Alumnos idAlumno = inscripciones.getIdAlumno();
                if (idAlumno != null) {
                    idAlumno = em.getReference(idAlumno.getClass(), idAlumno.getMatricula());
                    inscripciones.setIdAlumno(idAlumno);
                }
                Grupos idGrupo = inscripciones.getIdGrupo();
                if (idGrupo != null) {
                    idGrupo = em.getReference(idGrupo.getClass(), idGrupo.getIdGrupo());
                    inscripciones.setIdGrupo(idGrupo);
                }
                em.persist(inscripciones);
                if (idAlumno != null) {
                    idAlumno.getInscripcionesList().add(inscripciones);
                    idAlumno = em.merge(idAlumno);
                }
                if (idGrupo != null) {
                    idGrupo.setEspacioDisponible(idGrupo.getEspacioDisponible() - 1);
                    idGrupo.getInscripcionesList().add(inscripciones);
                    idGrupo = em.merge(idGrupo);
                }
                em.getTransaction().commit();
            } catch (Exception ex) {
                validacion = false;
            } finally {
                if (em != null) {
                    em.close();
                }
            }
        } else {
            validacion = false;
        }
        return validacion;
    }

    public Inscripciones findInscripciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Inscripciones.class, id);
        } finally {
            em.close();
        }
    }

    public Grupos buscarGrupoPorId(int idGrupo) {
        Grupos grupo = null;
        EntityManager conexion = getEntityManager();

        try {
            grupo = conexion.find(Grupos.class, idGrupo);
        } finally {
            if (conexion != null) {
                conexion.close();
            }
        }

        return grupo;
    }

    public Alumnos buscarAlumnoPorId(int idAlumno) {
        EntityManager conexion = getEntityManager();
        Alumnos alumno = null;
        try {
            alumno = conexion.find(Alumnos.class, idAlumno);
        } finally {
            if (conexion != null) {
                conexion.close();
            }
        }

        return alumno;
    }

    public Inscripciones buscarInscripcionPorGrupo(int idAlumno, int idGrupo) {
        EntityManager conexion = getEntityManager();
        Inscripciones inscripcion = null;

        try {
            inscripcion = (Inscripciones) conexion.createQuery("SELECT inscripcion FROM Inscripciones inscripcion WHERE inscripcion.idAlumno.matricula = :idAlumno AND inscripcion.idGrupo.idGrupo AND inscripcion.estado=1"
                    + " = :idGrupo and inscripcion.estado = true").setParameter("idAlumno", idAlumno).setParameter("idGrupo", idGrupo).getSingleResult();
        } catch (NoResultException ex) {
            inscripcion = null;
        } finally {
            if (conexion != null) {
                conexion.close();
            }
        }

        return inscripcion;
    }

    public boolean darDeBajaAlumno(int idGrupo, int idAlumno) {
        boolean validacion = true;
        EntityTransaction transaccion = null;

        Inscripciones inscipcion = buscarInscripcionPorGrupo(idAlumno, idGrupo);
        if (inscipcion != null) {
            EntityManager conexion = getEntityManager();
            try {
                transaccion = conexion.getTransaction();
                Grupos grupo = conexion.find(Grupos.class, idGrupo);
                inscipcion = conexion.find(Inscripciones.class, inscipcion.getIdInscripcion());
                transaccion.begin();
                grupo.setEspacioDisponible(grupo.getEspacioDisponible()+1);
                inscipcion.setEstado(false);

                transaccion.commit();
            } catch (Exception ex) {
                if (transaccion != null) {
                    transaccion.rollback();
                }
                validacion = false;
            } finally {
                if (conexion != null) {
                    conexion.close();
                }
            }
        } else {
            validacion = false;
        }

        return validacion;
    }

    public List<Alumnos> buscarAlumnosInscritos(int idGrupo) {
        List<Alumnos> alumnos = null;
        EntityManager conexion = getEntityManager();

        try {
            alumnos = conexion.createQuery("SELECT a FROM Alumnos a, Grupos g, Inscripciones i WHERE i.idGrupo.idGrupo = :idGrupo AND i.idGrupo.idGrupo = g.idGrupo AND i.idAlumno.matricula = a.matricula AND i.estado = true").setParameter("idGrupo", idGrupo).getResultList();
        } finally {
            if (conexion != null) {
                conexion.close();
            }
        }

        return alumnos;
    }

    public List<Grupos> buscarGruposDeColaborador(int idColaborador) {
        List<Grupos> grupos = null;
        EntityManager conexion = getEntityManager();

        try {
            grupos = conexion.createQuery("SELECT g FROM Grupos g WHERE g.idColaborador.idColaborador = :idColaborador").setParameter("idColaborador", idColaborador).getResultList();
        } finally {
            if (conexion != null) {
                conexion.close();
            }
        }

        return grupos;
    }
}
