/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.persistencia.controladores;

import emaaredespacio.modelo.UsuarioSistema;
import emaaredespacio.persistencia.controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import emaaredespacio.persistencia.entidad.Colaboradores;
import java.util.ArrayList;
import java.util.List;
import emaaredespacio.persistencia.entidad.Directores;
import emaaredespacio.persistencia.entidad.Usuarios;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 *
 * @author Adrián Bustamante Zarate
 * @date 31/03/2018
 * @time 09:17:42 PM
 */
public class UsuariosJpaController implements Serializable {

    /**
     * Constructor sobrecargado.
     *
     * @param fabricaEntidad Referencia a la persistenacia.
     */
    public UsuariosJpaController(EntityManagerFactory fabricaEntidad) {
        this.fabricaEntidad = fabricaEntidad;
    }
    private EntityManagerFactory fabricaEntidad = null;

    /**
     * Getter de la variable fabricaEntidad.
     *
     * @return fabricaEntidad.
     */
    public EntityManager getEntityManager() {
        return fabricaEntidad.createEntityManager();
    }

    /**
     * Busca los datos de un usuario que desea ingresar al sistema.
     *
     * @param nombreUsuario Identificador de la cuenta del usuario.
     * @return Cuenta del usuario.
     */
    public UsuarioSistema verificarAutenticacion(String nombreUsuario) {
        EntityManager entidad = getEntityManager();
        Query consulta = entidad.createQuery("SELECT u FROM Usuarios u WHERE u.nombreUsuario = :nombreUser").setParameter("nombreUser", nombreUsuario);
        Usuarios cuentaEntidadResultado;
        UsuarioSistema cuentaResultado = new UsuarioSistema();
        try {
            cuentaEntidadResultado = (Usuarios) consulta.getSingleResult();
            cuentaResultado.setContrasenia(cuentaEntidadResultado.getContrasenia());
            //cuentaResultado.setEstadoSesion(cuentaEntidadResultado.getEstadoSesion());
        } catch (NoResultException ex) {
            System.out.println("Error en el metodo verificarAutenticacion(): "+ex.getMessage());
        } finally {
            entidad.close();
        }
        return cuentaResultado;
    }

    public void create(Usuarios usuarios) {
        if (usuarios.getColaboradoresList() == null) {
            usuarios.setColaboradoresList(new ArrayList<Colaboradores>());
        }
        if (usuarios.getDirectoresList() == null) {
            usuarios.setDirectoresList(new ArrayList<Directores>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Colaboradores> attachedColaboradoresList = new ArrayList<Colaboradores>();
            for (Colaboradores colaboradoresListColaboradoresToAttach : usuarios.getColaboradoresList()) {
                colaboradoresListColaboradoresToAttach = em.getReference(colaboradoresListColaboradoresToAttach.getClass(), colaboradoresListColaboradoresToAttach.getIdColaborador());
                attachedColaboradoresList.add(colaboradoresListColaboradoresToAttach);
            }
            usuarios.setColaboradoresList(attachedColaboradoresList);
            List<Directores> attachedDirectoresList = new ArrayList<Directores>();
            for (Directores directoresListDirectoresToAttach : usuarios.getDirectoresList()) {
                directoresListDirectoresToAttach = em.getReference(directoresListDirectoresToAttach.getClass(), directoresListDirectoresToAttach.getIdDirector());
                attachedDirectoresList.add(directoresListDirectoresToAttach);
            }
            usuarios.setDirectoresList(attachedDirectoresList);
            em.persist(usuarios);
            for (Colaboradores colaboradoresListColaboradores : usuarios.getColaboradoresList()) {
                Usuarios oldIdUsuarioOfColaboradoresListColaboradores = colaboradoresListColaboradores.getIdUsuario();
                colaboradoresListColaboradores.setIdUsuario(usuarios);
                colaboradoresListColaboradores = em.merge(colaboradoresListColaboradores);
                if (oldIdUsuarioOfColaboradoresListColaboradores != null) {
                    oldIdUsuarioOfColaboradoresListColaboradores.getColaboradoresList().remove(colaboradoresListColaboradores);
                    oldIdUsuarioOfColaboradoresListColaboradores = em.merge(oldIdUsuarioOfColaboradoresListColaboradores);
                }
            }
            for (Directores directoresListDirectores : usuarios.getDirectoresList()) {
                Usuarios oldIdUsuarioOfDirectoresListDirectores = directoresListDirectores.getIdUsuario();
                directoresListDirectores.setIdUsuario(usuarios);
                directoresListDirectores = em.merge(directoresListDirectores);
                if (oldIdUsuarioOfDirectoresListDirectores != null) {
                    oldIdUsuarioOfDirectoresListDirectores.getDirectoresList().remove(directoresListDirectores);
                    oldIdUsuarioOfDirectoresListDirectores = em.merge(oldIdUsuarioOfDirectoresListDirectores);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getIdUsuario());
            List<Colaboradores> colaboradoresListOld = persistentUsuarios.getColaboradoresList();
            List<Colaboradores> colaboradoresListNew = usuarios.getColaboradoresList();
            List<Directores> directoresListOld = persistentUsuarios.getDirectoresList();
            List<Directores> directoresListNew = usuarios.getDirectoresList();
            List<Colaboradores> attachedColaboradoresListNew = new ArrayList<Colaboradores>();
            for (Colaboradores colaboradoresListNewColaboradoresToAttach : colaboradoresListNew) {
                colaboradoresListNewColaboradoresToAttach = em.getReference(colaboradoresListNewColaboradoresToAttach.getClass(), colaboradoresListNewColaboradoresToAttach.getIdColaborador());
                attachedColaboradoresListNew.add(colaboradoresListNewColaboradoresToAttach);
            }
            colaboradoresListNew = attachedColaboradoresListNew;
            usuarios.setColaboradoresList(colaboradoresListNew);
            List<Directores> attachedDirectoresListNew = new ArrayList<Directores>();
            for (Directores directoresListNewDirectoresToAttach : directoresListNew) {
                directoresListNewDirectoresToAttach = em.getReference(directoresListNewDirectoresToAttach.getClass(), directoresListNewDirectoresToAttach.getIdDirector());
                attachedDirectoresListNew.add(directoresListNewDirectoresToAttach);
            }
            directoresListNew = attachedDirectoresListNew;
            usuarios.setDirectoresList(directoresListNew);
            usuarios = em.merge(usuarios);
            for (Colaboradores colaboradoresListOldColaboradores : colaboradoresListOld) {
                if (!colaboradoresListNew.contains(colaboradoresListOldColaboradores)) {
                    colaboradoresListOldColaboradores.setIdUsuario(null);
                    colaboradoresListOldColaboradores = em.merge(colaboradoresListOldColaboradores);
                }
            }
            for (Colaboradores colaboradoresListNewColaboradores : colaboradoresListNew) {
                if (!colaboradoresListOld.contains(colaboradoresListNewColaboradores)) {
                    Usuarios oldIdUsuarioOfColaboradoresListNewColaboradores = colaboradoresListNewColaboradores.getIdUsuario();
                    colaboradoresListNewColaboradores.setIdUsuario(usuarios);
                    colaboradoresListNewColaboradores = em.merge(colaboradoresListNewColaboradores);
                    if (oldIdUsuarioOfColaboradoresListNewColaboradores != null && !oldIdUsuarioOfColaboradoresListNewColaboradores.equals(usuarios)) {
                        oldIdUsuarioOfColaboradoresListNewColaboradores.getColaboradoresList().remove(colaboradoresListNewColaboradores);
                        oldIdUsuarioOfColaboradoresListNewColaboradores = em.merge(oldIdUsuarioOfColaboradoresListNewColaboradores);
                    }
                }
            }
            for (Directores directoresListOldDirectores : directoresListOld) {
                if (!directoresListNew.contains(directoresListOldDirectores)) {
                    directoresListOldDirectores.setIdUsuario(null);
                    directoresListOldDirectores = em.merge(directoresListOldDirectores);
                }
            }
            for (Directores directoresListNewDirectores : directoresListNew) {
                if (!directoresListOld.contains(directoresListNewDirectores)) {
                    Usuarios oldIdUsuarioOfDirectoresListNewDirectores = directoresListNewDirectores.getIdUsuario();
                    directoresListNewDirectores.setIdUsuario(usuarios);
                    directoresListNewDirectores = em.merge(directoresListNewDirectores);
                    if (oldIdUsuarioOfDirectoresListNewDirectores != null && !oldIdUsuarioOfDirectoresListNewDirectores.equals(usuarios)) {
                        oldIdUsuarioOfDirectoresListNewDirectores.getDirectoresList().remove(directoresListNewDirectores);
                        oldIdUsuarioOfDirectoresListNewDirectores = em.merge(oldIdUsuarioOfDirectoresListNewDirectores);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarios.getIdUsuario();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            List<Colaboradores> colaboradoresList = usuarios.getColaboradoresList();
            for (Colaboradores colaboradoresListColaboradores : colaboradoresList) {
                colaboradoresListColaboradores.setIdUsuario(null);
                colaboradoresListColaboradores = em.merge(colaboradoresListColaboradores);
            }
            List<Directores> directoresList = usuarios.getDirectoresList();
            for (Directores directoresListDirectores : directoresList) {
                directoresListDirectores.setIdUsuario(null);
                directoresListDirectores = em.merge(directoresListDirectores);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
