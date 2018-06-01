/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.persistencia.controladores;

import emaaredespacio.modelo.Colaborador;
import emaaredespacio.modelo.PagoAlumno;
import emaaredespacio.persistencia.controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import emaaredespacio.persistencia.entidad.Alumnos;
import emaaredespacio.persistencia.entidad.Grupos;
import emaaredespacio.persistencia.entidad.Inscripciones;
import emaaredespacio.persistencia.entidad.Pagosalumnos;
import emaaredespacio.utilerias.EditorDeFormatos;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author enriq
 */
public class PagosalumnosJpaController implements Serializable {

    public PagosalumnosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public PagosalumnosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean create(Pagosalumnos pagosalumnos) {
        boolean creado = false;
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumnos matricula = pagosalumnos.getMatricula();
            if (matricula != null) {
                matricula = em.getReference(matricula.getClass(), matricula.getMatricula());
                pagosalumnos.setMatricula(matricula);
            }
            Grupos idGrupo = pagosalumnos.getIdGrupo();
            if (idGrupo != null) {
                idGrupo = em.getReference(idGrupo.getClass(), idGrupo.getIdGrupo());
                pagosalumnos.setIdGrupo(idGrupo);
            }
            em.persist(pagosalumnos);
            if (matricula != null) {
                matricula.getPagosalumnosList().add(pagosalumnos);
                matricula = em.merge(matricula);
            }
            if (idGrupo != null) {
                idGrupo.getPagosalumnosList().add(pagosalumnos);
                idGrupo = em.merge(idGrupo);
            }
            em.getTransaction().commit();
            creado = true;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return creado;
    }

    public boolean edit(Pagosalumnos pagosalumnos) {
        boolean validacion = true;
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagosalumnos persistentPagosalumnos = em.find(Pagosalumnos.class, pagosalumnos.getIdPago());
            Alumnos matriculaOld = persistentPagosalumnos.getMatricula();
            Alumnos matriculaNew = pagosalumnos.getMatricula();
            Grupos idGrupoOld = persistentPagosalumnos.getIdGrupo();
            Grupos idGrupoNew = pagosalumnos.getIdGrupo();
            if (matriculaNew != null) {
                matriculaNew = em.getReference(matriculaNew.getClass(), matriculaNew.getMatricula());
                pagosalumnos.setMatricula(matriculaNew);
            }
            if (idGrupoNew != null) {
                idGrupoNew = em.getReference(idGrupoNew.getClass(), idGrupoNew.getIdGrupo());
                pagosalumnos.setIdGrupo(idGrupoNew);
            }
            pagosalumnos = em.merge(pagosalumnos);
            if (matriculaOld != null && !matriculaOld.equals(matriculaNew)) {
                matriculaOld.getPagosalumnosList().remove(pagosalumnos);
                matriculaOld = em.merge(matriculaOld);
            }
            if (matriculaNew != null && !matriculaNew.equals(matriculaOld)) {
                matriculaNew.getPagosalumnosList().add(pagosalumnos);
                matriculaNew = em.merge(matriculaNew);
            }
            if (idGrupoOld != null && !idGrupoOld.equals(idGrupoNew)) {
                idGrupoOld.getPagosalumnosList().remove(pagosalumnos);
                idGrupoOld = em.merge(idGrupoOld);
            }
            if (idGrupoNew != null && !idGrupoNew.equals(idGrupoOld)) {
                idGrupoNew.getPagosalumnosList().add(pagosalumnos);
                idGrupoNew = em.merge(idGrupoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            validacion = false;
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return validacion;
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagosalumnos pagosalumnos;
            try {
                pagosalumnos = em.getReference(Pagosalumnos.class, id);
                pagosalumnos.getIdPago();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagosalumnos with id " + id + " no longer exists.", enfe);
            }
            Alumnos matricula = pagosalumnos.getMatricula();
            if (matricula != null) {
                matricula.getPagosalumnosList().remove(pagosalumnos);
                matricula = em.merge(matricula);
            }
            Grupos idGrupo = pagosalumnos.getIdGrupo();
            if (idGrupo != null) {
                idGrupo.getPagosalumnosList().remove(pagosalumnos);
                idGrupo = em.merge(idGrupo);
            }
            em.remove(pagosalumnos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pagosalumnos> findPagosalumnosEntities() {
        return findPagosalumnosEntities(true, -1, -1);
    }

    public List<Pagosalumnos> findPagosalumnosEntities(int maxResults, int firstResult) {
        return findPagosalumnosEntities(false, maxResults, firstResult);
    }

    private List<Pagosalumnos> findPagosalumnosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pagosalumnos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Pagosalumnos findPagosalumnos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pagosalumnos.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagosalumnosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pagosalumnos> rt = cq.from(Pagosalumnos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<PagoAlumno> buscarPagosVencidos(Colaborador colaborador) {
        List<Inscripciones> inscripcionesVencidas = null;
        List<Inscripciones> inscripciones = null;
        List<Pagosalumnos> pagosRealizados = null;
        List<PagoAlumno> pagosVencidosInscripcion = null;
        List<PagoAlumno> pagos = new ArrayList<>();
        List<Grupos> grupos = null;
        PagoAlumno pago = null;
        EntityManager conexion = getEntityManager();
        grupos = new InscripcionesJpaController().buscarGruposDeColaborador(colaborador.getIdColaborador());
        for (Grupos grupo : grupos) {
            inscripciones = conexion.createQuery("SELECT i FROM Inscripciones i WHERE i.estado=1 AND i.idGrupo= :idGrupo").setParameter("idGrupo", grupo).getResultList();
            for (Inscripciones inscripcion : inscripciones) {
                //pagos mensuales vencidos
                LocalDate date = null;
                pagosRealizados = conexion.createQuery("SELECT p FROM Pagosalumnos p, Inscripciones i WHERE p.idGrupo=i.idGrupo AND p.matricula = i.idAlumno AND p.tipoPago= 'Mensualidad' ORDER BY p.fechaPago DESC").getResultList();
                if (pagosRealizados.size() > 0) {
                    date = pagosRealizados.get(0).getFechaPago().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    System.out.println("date " + date);
                    if (date.plusMonths(1).isBefore(LocalDate.now()) || date.plusMonths(1).isEqual(LocalDate.now())) {
//                    System.out.println("Ya venció el mes");
                        pago = new PagoAlumno();
                        pago.setMatricula(pagosRealizados.get(0).getMatricula().getMatricula());
                        pago.setMonto(pagosRealizados.get(0).getMonto());
                        pago.setIdGrupo(pagosRealizados.get(0).getIdGrupo().getIdGrupo());
                        pago.setTipoPago(pagosRealizados.get(0).getTipoPago());
                        pago.setPorcentajeDescuento(pagosRealizados.get(0).getPorcentajeDescuento());
                        pago.setTotal(pagosRealizados.get(0).getTotal());
                        pago.setFechaPago(EditorDeFormatos.crearFormatoFecha(pagosRealizados.get(0).getFechaPago()));
                        pagos.add(pago);
//                    System.out.println("pago añadido");
                    }
                }
                //pagos inscripcion vencida
                pagosRealizados.clear();

                pagosRealizados = conexion.createQuery("SELECT p FROM Pagosalumnos p, Inscripciones i WHERE p.idGrupo=i.idGrupo AND p.matricula = i.idAlumno AND p.tipoPago= 'Inscripcion' ORDER BY p.fechaPago DESC").getResultList();
                if (pagosRealizados.size() > 0) {
                    date = pagosRealizados.get(0).getFechaPago().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    System.out.println(date);
                    if (date.plusYears(1).isBefore(LocalDate.now()) || date.plusYears(1).isEqual(LocalDate.now())) {
//                    System.out.println("Ya venció el año");
                        pago = new PagoAlumno();
                        pago.setIdPagoAlumno(pagosRealizados.get(0).getIdPago().intValue());
                        pago.setMatricula(pagosRealizados.get(0).getMatricula().getMatricula());
                        pago.setMonto(pagosRealizados.get(0).getMonto());
                        pago.setIdGrupo(pagosRealizados.get(0).getIdGrupo().getIdGrupo());
                        pago.setTipoPago(pagosRealizados.get(0).getTipoPago());
                        pago.setPorcentajeDescuento(pagosRealizados.get(0).getPorcentajeDescuento());
                        pago.setTotal(pagosRealizados.get(0).getTotal());
                        pago.setFechaPago(EditorDeFormatos.crearFormatoFecha(pagosRealizados.get(0).getFechaPago()));
                        pagos.add(pago);
                    }
                }
//              
            }
        }
        return pagos;
    }

    public List<Pagosalumnos> buscarPagosDeAlumno(Integer matricula, int idGrupo) {
        EntityManager conexion = null;
        List<Pagosalumnos> resultadosBusqueda = null;
        try {
            conexion = getEntityManager();
            resultadosBusqueda = conexion.createQuery("SELECT p FROM Pagosalumnos p WHERE p.matricula.matricula = :matricula AND p.idGrupo.idGrupo = :idGrupo ORDER BY p.fechaPago ASC").setParameter("matricula", matricula).setParameter("idGrupo", idGrupo).getResultList();
        } catch (Exception e) {
            resultadosBusqueda = new ArrayList();
        } finally {
            if (conexion != null) {
                conexion.close();
            }
        }

        return resultadosBusqueda;
    }

}
