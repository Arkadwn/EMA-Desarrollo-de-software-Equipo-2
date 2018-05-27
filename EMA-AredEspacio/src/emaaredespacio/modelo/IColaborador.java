package emaaredespacio.modelo;

import java.util.List;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 30/03/2018
 * @time 05:45:38 PM
 */
public interface IColaborador {

    /**
     * Registra un colaborador en la base de datos.
     * 
     * @param colaborador colaborador a registrar
     * @return true o false de acuerdo al resultado de la operacion.
     */
    public boolean registrarColaborador(Colaborador colaborador);

    /**
     * Valida los atributos aguardar del colaborador si corresponden con las
     * restricciones de base de datos y las reglas de negocio.
     * 
     * @param colaborador El colaborador al que se le validaran los atributos.
     * @param contraseña confirmacion de la contraseña del usuario del
     * colaborador.
     * @return arreglo de booleanos con el resulatado de los campos un campo para cada atributo
     * la ultima posicion del arreglo tiene la validacion de todos los atributos.
     * 0.- Nombre del colaborador.
     * 1.- Apellidos del colaborador.
     * 2.- Telefono del colaborador.
     * 3.- Correo del colaborador.
     * 4.- Direccion del colaborador.
     * 5.- Nombre de usuario del colaborador.
     * 6.- Igualdad entre las doscontraseña.
     * 7.- Formato valido de las contraseñas.
     */
    public boolean[] validarCampos(Colaborador colaborador, String contraseña);

    /**
     * Busca los colaboradores que hagan match con la palabra ingresada. Puede ser
     * con el nombre o apellido del colaborador.
     * 
     * @param palabraClave Palabra con palabras referentes a los apellidos o nombre
     * del colaborador.
     * @return Lista con los colaboradores que hicieron match con la palabra ingresada.
     */
    public List<Colaborador> buscarColaborador(String palabraClave);

    /**
     * Edita un colaborador ya registrado en la base de datos.
     * 
     * @param colaborador Colaborador ya registrado con los nuevos atributos.
     * @param nuevaContraseña Nueva contraseña si fue modificada.
     * @return true o false de acuerdo al resultado de la operacion.
     */
    public boolean editarColaborador(Colaborador colaborador, boolean nuevaContraseña);

    /**
     * Busca colaboradores de acuerdo al estado dentro de la base de datos.
     * 
     * @param estado Estado de los colaboradores en la base de datos. "A" activo,
     * "B" dado de baja.
     * 
     * @return Lista de colaboradores con el estado ingresado.
     */
    public List<Colaborador> buscarColaboradoresEstados(String estado);

    /**
     * Busca un colaborador de acuerdo al identificador dentro de la base de datos.
     * 
     * @param idColaborador Identificador de Colaborador.
     * @return Colaborador que corresponde al colaborador. Retorna null si no se
     * encontro el colaborador.
     */
    public Colaborador buscarColaboradorSegunID(int idColaborador);

    /**
     * Valida que el nombre de usuario no este repido en la base de datos.
     * 
     * @param nombreUsuario Nombre de usuario que se desea ingresar para el colaborador.
     * @return true si no existe, false si existe.
     */
    public boolean validarNombreUsuario(String nombreUsuario);
}
