package emaaredespacio.modelo;

import java.util.List;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 30/03/2018
 * @time 05:45:38 PM
 */
public interface IColaborador {
    public boolean registrarColaborador(Colaborador colaborador);
    public boolean[] validarCampos(Colaborador colaborador, String contraseña);
    public List<Colaborador> buscarColaborador(String palabraClave);
    public boolean editarColaborador(Colaborador colaborador, boolean nuevaContraseña);
}
