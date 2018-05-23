package emaaredespacio.persistencia.controladores;

import emaaredespacio.persistencia.entidad.Grupos;
import emaaredespacio.persistencia.entidad.Inscripciones;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

/**
 *
 * @author enriq
 */
public class GruposJpaController implements Serializable{
    
    public GruposJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public boolean create(Grupos grupo) {
        boolean creado = false;
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(grupo);
            em.getTransaction().commit();
            creado = true;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return creado;
    }
    
    public boolean edit(Grupos grupo) {
        boolean validacion = true;
        EntityManager conexion = getEntityManager();
        EntityTransaction transaccion = null;
        Grupos grupoActual;
        
        try{
            transaccion = conexion.getTransaction();
            transaccion.begin();
            grupoActual = conexion.find(Grupos.class, grupo.getIdGrupo());
            grupoActual.setTipoDeBaile(grupo.getTipoDeBaile());
            grupoActual.setEstado(grupo.getEstado());
            grupoActual.setCupo(grupo.getCupo());
            grupoActual.setMensualidad(grupo.getMensualidad());
            grupoActual.setInscripcion(grupo.getInscripcion());
            grupoActual.setIdColaborador(grupo.getIdColaborador());
            grupoActual.setHorarioAsignado(grupo.getHorarioAsignado());
            transaccion.commit();
        }catch(RollbackException ex){
            if(transaccion.isActive()){
                transaccion.rollback();
            }
            validacion = false;
        }
        
        return validacion;
    }
    
    public List<Grupos> buscarGrupos(){
        List<Grupos> grupos = new ArrayList();
        EntityManager conexion = getEntityManager();
        grupos = conexion.createQuery("SELECT a FROM Grupos a").getResultList();
        return grupos;
    }
    
    public List<Grupos> buscarGrupoPorId(int id){
        List<Grupos> grupo = new ArrayList<>();
        System.out.println("id" + id);
        EntityManager conexion = getEntityManager();
        grupo = conexion.createNamedQuery("Grupos.findByIdGrupo").setParameter("idGrupo", id).getResultList();
        return grupo;
    }
    
    public void DarDeBajaAlumnosDeGrupo(int grupo){
        EntityManager conexion = getEntityManager();
        EntityTransaction transaccion = null;
        Grupos grupos = new Grupos();
        grupos.setIdGrupo(grupo);
        List<Inscripciones> inscripciones = conexion.createQuery("SELECT a FROM Inscripciones a WHERE a.estado=1 AND a.idGrupo= :grupo").setParameter("grupo", grupos).getResultList();
        for(Inscripciones inscripcion : inscripciones){
            transaccion = conexion.getTransaction();
            transaccion.begin();
            inscripcion.setEstado(false);
            transaccion.commit();
        }
    }
    
}
