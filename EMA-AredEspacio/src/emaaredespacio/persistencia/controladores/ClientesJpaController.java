/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.persistencia.controladores;

import emaaredespacio.persistencia.controladores.exceptions.NonexistentEntityException;
import emaaredespacio.persistencia.entidad.Clientes;
import java.io.Serializable;
import java.util.ArrayList;
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
 * @author enriq
 */
public class ClientesJpaController implements Serializable {

    public ClientesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean create(Clientes clientes) {
        boolean creado = false;
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(clientes);
            em.getTransaction().commit();
            creado = true;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return creado;
    }

    public boolean edit(Clientes cliente) {
        boolean validacion = true;
        EntityManager conexion = getEntityManager();
        EntityTransaction transaccion = null;
        Clientes clienteActual;
        
        try{
            transaccion = conexion.getTransaction();
            transaccion.begin();
            clienteActual = conexion.find(Clientes.class, cliente.getIdCliente());
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setCorreo(cliente.getCorreo());
            clienteActual.setDireccion(cliente.getDireccion());
            clienteActual.setEstado(cliente.getEstado());
            clienteActual.setImagen(cliente.getImagen());
            clienteActual.setTelefono(cliente.getTelefono());
            
            transaccion.commit();
        }catch(RollbackException ex){
            if(transaccion.isActive()){
                transaccion.rollback();
            }
            validacion = false;
        }
        
        return validacion;
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clientes clientes;
            try {
                clientes = em.getReference(Clientes.class, id);
                clientes.getIdCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
            }
            em.remove(clientes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<Clientes> buscarClientesRelacionados(String palabraClave){
        EntityManager em = getEntityManager();
            palabraClave = "%" + palabraClave + "%";
            String s = "SELECT c FROM Clientes c WHERE c.nombre like :palabraClave";
            Query q = em.createQuery(s).setParameter("palabraClave", palabraClave);
            List result = q.getResultList();
            return result;
    }
    
    public List<Clientes> buscarCliente(String palabraClave) {
        List<Clientes> clientes = new ArrayList();
        
        EntityManager conexion = getEntityManager();
    
        clientes = conexion.createQuery("SELECT a FROM Clientes a WHERE a.nombre LIKE '%"+palabraClave+"%'").getResultList();
        
        return clientes;
    }

    public List<Clientes> findClientesEntities() {
        return findClientesEntities(true, -1, -1);
    }

    public List<Clientes> findClientesEntities(int maxResults, int firstResult) {
        return findClientesEntities(false, maxResults, firstResult);
    }

    private List<Clientes> findClientesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clientes.class));
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

    public Clientes findClientes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clientes.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clientes> rt = cq.from(Clientes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
