/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.persistencia.controladores;

import emaaredespacio.exceptions.NonexistentEntityException;
import emaaredespacio.persistencia.entidad.Clientes;
import emaaredespacio.persistencia.entidad.Egresos;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author arkadwn
 */
public class EgresosJpaController implements Serializable {

    public EgresosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Egresos egresos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(egresos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Egresos egresos) throws NonexistentEntityException, Exception {
        EntityManager conexion = getEntityManager();
        EntityTransaction transaccion = null;
        Egresos egresoActual;
        
        try{
            transaccion = conexion.getTransaction();
            transaccion.begin();
            egresoActual = conexion.find(Egresos.class, egresos.getIdEgreso());
            egresoActual.setDescripcion(egresos.getDescripcion());
            egresoActual.setFecha(egresos.getFecha());
            egresoActual.setMonto(egresos.getMonto());
            
            transaccion.commit();
        }catch(RollbackException ex){
            if(transaccion.isActive()){
                transaccion.rollback();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Egresos egresos;
            try {
                egresos = em.getReference(Egresos.class, id);
                egresos.getIdEgreso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The egresos with id " + id + " no longer exists.", enfe);
            }
            em.remove(egresos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Egresos> findEgresosEntities() {
        return findEgresosEntities(true, -1, -1);
    }

    public List<Egresos> findEgresosEntities(int maxResults, int firstResult) {
        return findEgresosEntities(false, maxResults, firstResult);
    }

    private List<Egresos> findEgresosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Egresos.class));
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

    public Egresos findEgresos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Egresos.class, id);
        } finally {
            em.close();
        }
    }

    public int getEgresosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Egresos> rt = cq.from(Egresos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
