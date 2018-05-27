package emaaredespacio.persistencia.controladores;

import emaaredespacio.persistencia.controladores.exceptions.NonexistentEntityException;
import emaaredespacio.persistencia.entidad.Pagos;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 9/05/2018
 * @time 10:30:30 AM
 */
public class PagosJpaController implements Serializable {

    public PagosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean create(Pagos pagos) {
        boolean validacion = true;
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(pagos);
            em.getTransaction().commit();
        } catch (Exception ex) {
            validacion = false;
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return validacion;
    }

    public boolean edit(Pagos pagos) {
        boolean validacion = true;
        EntityManager em = null;
        Pagos pago = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            pagos = em.merge(pagos);
            em.getTransaction().commit();
            
        } catch (Exception ex) {
            validacion = false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        
        return validacion;
    }

    public Pagos buscarPagoPorId(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pagos.class, id);
        } finally {
            em.close();
        }
    }

    public List<Pagos> buscarPagosAColaborador(String nombreColaborador, boolean estado) {
        List<Pagos> pagos = null;
        EntityManager conexion = null;
        String entrada = "%" + nombreColaborador + "%";

        try {
            conexion = getEntityManager();
            pagos = conexion.createQuery("SELECT p FROM Pagos p WHERE p.nombreColaborador LIKE :entrada AND p.fueEntregado = :estado").setParameter("entrada", entrada).setParameter("estado", estado).getResultList();
        } finally {
            if (conexion != null) {
                conexion.close();
            }
        }

        return pagos;
    }

    public boolean guardarEntrega(Integer idPago) {
        EntityManager conexion = null;
        EntityTransaction transaccion = null;
        boolean validacion = true;
        try {
            conexion = getEntityManager();

            transaccion = conexion.getTransaction();

            transaccion.begin();
            Pagos pago = conexion.find(Pagos.class, idPago);
            pago.setFueEntregado(true);

            transaccion.commit();
        } catch (Exception ex) {
            validacion = false;
        } finally {
            if (conexion != null) {
                conexion.close();
            }
        }

        return validacion;
    }
}
