/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.persistencia.controladores;

import emaaredespacio.modelo.PagoAlumno;
import emaaredespacio.persistencia.controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import emaaredespacio.persistencia.entidad.Alumnos;
import emaaredespacio.persistencia.entidad.Grupos;
import emaaredespacio.persistencia.entidad.Pagosalumnos;
import emaaredespacio.utilerias.EditorDeFormatos;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author enriq
 */
public class PagosalumnosJpaController implements Serializable {

    public PagosalumnosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

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

    public void edit(Pagosalumnos pagosalumnos) throws NonexistentEntityException, Exception {
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
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagosalumnos.getIdPago();
                if (findPagosalumnos(id) == null) {
                    throw new NonexistentEntityException("The pagosalumnos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
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
    
}
