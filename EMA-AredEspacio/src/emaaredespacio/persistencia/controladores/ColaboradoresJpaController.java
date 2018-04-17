package emaaredespacio.persistencia.controladores;

import emaaredespacio.persistencia.entidad.Colaboradores;
import emaaredespacio.persistencia.entidad.Usuarios;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 31/03/2018
 * @time 05:45:14 PM
 */
public class ColaboradoresJpaController implements IControladorColaborador{

    public ColaboradoresJpaController(){
        this.fabricaEntidad = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
    }
    private EntityManagerFactory fabricaEntidad = null;

    /**
     * Getter de la variable fabricaEntidad.
     *
     * @return fabricaEntidad.
     */
    private EntityManager getEntityManager() {
        return fabricaEntidad.createEntityManager();
    }
    
    @Override
    public boolean guardarColaborador(Colaboradores colaborador, Usuarios usuario) {
        boolean validacion = true;
        EntityManager conexion = getEntityManager();
        EntityTransaction transaccion = null;
        try{
            transaccion  = conexion.getTransaction();
            
            transaccion.begin();
            
            conexion.persist(usuario);
            conexion.persist(colaborador);
            
            transaccion.commit();
        }catch(RollbackException ex){
            if(transaccion.isActive()){
                transaccion.rollback();
            }
            validacion = false;
        }finally{
            conexion.close();
        }
        return validacion;
    }

    @Override
    public List<Colaboradores> buscarColaborador(String palabraClave) {
        List<Colaboradores> colaboradores = new ArrayList();
        
        String palabra = "%"+palabraClave+"%";
        EntityManager conexion = getEntityManager();
    
        colaboradores = conexion.createQuery("SELECT c FROM Colaboradores c WHERE c.nombre LIKE :palabra OR c.apellidos LIKE :palabra").setParameter("palabra", palabra).getResultList();
        
        return colaboradores;
    }

    @Override
    public boolean editarColborador(Colaboradores colaborador, Usuarios usuario) {
        boolean validacion = true;
        EntityManager conexion = getEntityManager();
        EntityTransaction transaccion = null;
        Usuarios usuarioActual;
        Colaboradores colaboradoresActual;
        try{
            transaccion  = conexion.getTransaction();
            transaccion.begin();
            
            usuarioActual = conexion.find(Usuarios.class, usuario.getIdUsuario());
            colaboradoresActual = conexion.find(Colaboradores.class, colaborador.getIdColaborador());
            
            
            usuarioActual.setContrasenia(usuario.getContrasenia());
            usuarioActual.setNombreUsuario(usuario.getNombreUsuario());
            
            colaboradoresActual.setApellidos(colaborador.getApellidos());
            colaboradoresActual.setNombre(colaborador.getNombre());
            colaboradoresActual.setCorreo(colaborador.getCorreo());
            colaboradoresActual.setDireccion(colaborador.getDireccion());
            colaboradoresActual.setEstado(colaborador.getEstado());
            colaboradoresActual.setTelefono(colaborador.getTelefono());
            colaboradoresActual.setTipoPago(colaborador.getTipoPago());
            colaboradoresActual.setMontoApagar(colaborador.getMontoApagar());
            colaboradoresActual.setImagen(colaborador.getImagen());
            
            transaccion.commit();
        }catch(RollbackException ex){
            Logger.getLogger(ColaboradoresJpaController.class.getName()).log(Level.SEVERE, null, ex);
            if(transaccion.isActive()){
                transaccion.rollback();
            }
            validacion = false;
        }finally{
            conexion.close();
        }
        return validacion;
    }
    
    
    
}
