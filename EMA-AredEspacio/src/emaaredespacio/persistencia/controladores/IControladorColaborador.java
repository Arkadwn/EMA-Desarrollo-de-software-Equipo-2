package emaaredespacio.persistencia.controladores;

import emaaredespacio.persistencia.entidad.Colaboradores;
import emaaredespacio.persistencia.entidad.Usuarios;
import java.util.List;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 1/04/2018
 * @time 02:04:47 PM
 */
public interface IControladorColaborador {

    /**
     *
     * @param colaborador
     * @param usuario
     * @return
     */
    public boolean guardarColaborador(Colaboradores colaborador, Usuarios usuario);

    /**
     *
     * @param palabraClave
     * @return
     */
    public List<Colaboradores> buscarColaborador(String palabraClave);
    
    public boolean editarColborador(Colaboradores colaborador, Usuarios usuario);
}
