package emaaredespacio.persistencia.controladores;

import emaaredespacio.persistencia.controladores.exceptions.NonexistentEntityException;
import emaaredespacio.persistencia.entidad.Pagosacolaborador;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 6/05/2018
 * @time 02:54:16 PM
 */
public class PagosacolaboradorJpaController implements Serializable {

    public PagosacolaboradorJpaController() {
        this.emf = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean create(Pagosacolaborador pagosacolaborador) {
        boolean validacion = true;
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(pagosacolaborador);
            em.getTransaction().commit();
        } catch(Exception ex){
            validacion = false;
        }finally {
            if (em != null) {
                em.close();
            }
        }
        
        return validacion;
    }

    public boolean edit(Pagosacolaborador pagosacolaborador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        boolean validacion = true;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            pagosacolaborador = em.merge(pagosacolaborador);
            em.getTransaction().commit();
        } catch (Exception ex) {
            validacion = false;
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagosacolaborador.getIdPago();
                if (buscarPagoPorId(id) == null) {
                    throw new NonexistentEntityException("The pagosacolaborador with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        return validacion;
    }
    
    public Pagosacolaborador buscarPagoPorId(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pagosacolaborador.class, id);
        } finally {
            em.close();
        }
    }

    public List<Pagosacolaborador> buscarPagosAColaborador(String nombreColaborador, boolean estado){
        List<Pagosacolaborador> pagos = null;
        EntityManager conexion = null;
        String entrada = "%"+nombreColaborador+"%";
        
        try{
            conexion = getEntityManager();
            pagos = conexion.createQuery("SELECT p FROM Pagosacolaborador p WHERE p.nombreColaborador LIKE :entrada AND p.fueEntregado = :estado").setParameter("entrada", entrada).setParameter("estado", estado).getResultList();
        }finally{
            if(conexion != null){
                conexion.close();
            }
        }
        
        return pagos;        
    }
    
    public boolean guardarEntrega(Integer idPago){
        EntityManager conexion = null;
        EntityTransaction transaccion = null;
        boolean validacion = true;
        try{
            conexion = getEntityManager();
            
            transaccion = conexion.getTransaction();
            
            transaccion.begin();
            Pagosacolaborador pago = conexion.find(Pagosacolaborador.class, idPago);
            pago.setFueEntregado(true);
            
            transaccion.commit();
        }catch(Exception ex){
            validacion = false;
        }finally{
            if(conexion != null){
                conexion.close();
            }
        }
        
        return validacion;
    }
}
