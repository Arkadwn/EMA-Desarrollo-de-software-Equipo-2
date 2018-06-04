package emaaredespacio.modelo;

import java.util.List;

/**
 *
 * @author Jesús Enrique Flores Nestozo
 * @date 29/04/2018
 * @time 05:06:47 PM
 */
public interface IGrupo {
    
    /**
     *Busca los grupos registrados en la base de datos
     * @return  Lista con todos los grupos existentes.
     */
    public List<Grupo> buscarGrupos();
    
    /**
     * Guarda los cambios realizados a un grupo
     * @param grupo al que se le han realizado cambios
     * @return True si se pudieron guardar los cambios en la base de datos, False si no se pudieron guardar.
     */
    public boolean guardarCambios(Grupo grupo);
    
    /**
     * Guarda un grupo nuevo en la base de datos
     * @param grupo que va a ser registrado en la base de datos
     * @return True si se pudo guardar el nuevo grupo, False si no se pudo guardar el grupo.
     */
    public boolean guardarGrupo(Grupo grupo);
    
    /**
     * Realiza una busqueda de un grupo según el id del grupo proporcionado
     * @param id del grupo a buscar
     * @return Grupo encontrado dado el id
     */
    public Grupo buscarGrupoPorId(int id);
    
    /**
     * Da de baja a todos los alumnos inscritos a un grupo
     * @param grupo que se va a dar de baja
     * @return True si se dieron de baja todos los alumnos, False si no se pudieron dar de baja
     */
    public boolean darDeBajaAlumnosDeGrupo(Grupo grupo);
}
