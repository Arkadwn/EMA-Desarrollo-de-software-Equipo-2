/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.persistencia.controladores;

import emaaredespacio.persistencia.controladores.exceptions.NonexistentEntityException;
import emaaredespacio.persistencia.entidad.Ingresos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author arkadwn
 */
public class IngresosJpaController implements Serializable {

    public IngresosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingresos ingresos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ingresos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean edit(Ingresos ingresos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        boolean modificado = false;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ingresos = em.merge(ingresos);
            em.getTransaction().commit();
            modificado = true;
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ingresos.getIdIngreso();
                if (findIngresos(id) == null) {
                    throw new NonexistentEntityException("The ingresos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return modificado;
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingresos ingresos;
            try {
                ingresos = em.getReference(Ingresos.class, id);
                ingresos.getIdIngreso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingresos with id " + id + " no longer exists.", enfe);
            }
            em.remove(ingresos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingresos> findIngresosEntities() {
        return findIngresosEntities(true, -1, -1);
    }

    public List<Ingresos> findIngresosEntities(int maxResults, int firstResult) {
        return findIngresosEntities(false, maxResults, firstResult);
    }

    private List<Ingresos> findIngresosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingresos.class));
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

    public Ingresos findIngresos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingresos.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngresosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingresos> rt = cq.from(Ingresos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Ingresos buscarIngresoDeColaborador(int idColaborador){
        EntityManager em = getEntityManager();
        Ingresos ingreso = null;
        List<Ingresos> ingresos = em.createQuery("SELECT i FROM Ingresos i WHERE i.pagoColaboradorID = :pagoColaboradorID ORDER BY i.fecha DESC").setParameter("pagoColaboradorID", idColaborador).getResultList();
        em.close();
        if(!ingresos.isEmpty()){
            ingreso = ingresos.get(0);
        }
        return ingreso;
    }
    
    public List<Ingresos> buscarIngresoPorTipo(int id,int tipo){
        EntityManager em = getEntityManager();
        Ingresos ingreso = null;
        List<Ingresos> ingresos = new ArrayList<>();
        if(tipo==1){
            ingresos = em.createQuery("SELECT i FROM Ingresos i Where i.pagoColaboradorID = :id ORDER BY i.fecha DESC").setParameter("id", id).getResultList();
        }else{
            ingresos = em.createQuery("SELECT i FROM Ingresos i Where i.pagoRentaID = :id ORDER BY i.fecha DESC").setParameter("id", id).getResultList();
        }
        return ingresos;
    }
}
