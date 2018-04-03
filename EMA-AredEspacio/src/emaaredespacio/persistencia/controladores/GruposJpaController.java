/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.persistencia.controladores;

import emaaredespacio.persistencia.entidad.Grupos;
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
           
            transaccion.commit();
        }catch(RollbackException ex){
            if(transaccion.isActive()){
                transaccion.rollback();
            }
            validacion = false;
        }
        
        return validacion;
    }

    
    public List<Grupos> buscarGrupo(int palabraClave) {
        List<Grupos> grupos = new ArrayList();
        
        EntityManager conexion = getEntityManager();
    
        grupos = conexion.createQuery("SELECT a FROM grupos a WHERE a.idColaborador LIKE '%"+palabraClave+"%'").getResultList();
        
        return grupos;
    }
    
}
