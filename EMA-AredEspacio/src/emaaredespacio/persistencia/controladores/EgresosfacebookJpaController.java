package emaaredespacio.persistencia.controladores;

import emaaredespacio.persistencia.controladores.exceptions.NonexistentEntityException;
import emaaredespacio.persistencia.entidad.Egresosfacebook;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.Persistence;
import javax.persistence.TransactionRequiredException;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 15/04/2018
 * @time 06:45:26 PM
 */
public class EgresosfacebookJpaController implements Serializable {

    public EgresosfacebookJpaController() {
        this.emf = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean create(Egresosfacebook egresosfacebook) {
        boolean validacion = true;
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(egresosfacebook);
            em.getTransaction().commit();
        } catch(TransactionRequiredException ex){
            validacion = false;
        }finally {
            if (em != null) {
                em.close();
            }
        }
        
        return validacion;
    }

    public void edit(Egresosfacebook egresosfacebook) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            egresosfacebook = em.merge(egresosfacebook);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = egresosfacebook.getIdEgresoFacebook();
                if (findEgresosfacebook(id) == null) {
                    throw new NonexistentEntityException("The egresosfacebook with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Egresosfacebook> findEgresosfacebookEntities() {
        return findEgresosfacebookEntities(true, -1, -1);
    }

    public List<Egresosfacebook> findEgresosfacebookEntities(int maxResults, int firstResult) {
        return findEgresosfacebookEntities(false, maxResults, firstResult);
    }

    private List<Egresosfacebook> findEgresosfacebookEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Egresosfacebook.class));
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

    public Egresosfacebook findEgresosfacebook(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Egresosfacebook.class, id);
        } finally {
            em.close();
        }
    }
    
    public List<Egresosfacebook> buscarEgresosPorCreador(String creador, boolean activo){
        EntityManager conexion = getEntityManager();
        String palabra = "%"+creador+"%";
        List<Egresosfacebook> egresos = null;
        try{
            egresos = conexion.createQuery("SELECT e FROM Egresosfacebook e WHERE e.activa = :activo AND e.creador LIKE :palabra")
                    .setParameter("activo", activo).setParameter("palabra", palabra).getResultList();
        }finally{
            conexion.close();
        }
        
        return egresos;
    }
}
