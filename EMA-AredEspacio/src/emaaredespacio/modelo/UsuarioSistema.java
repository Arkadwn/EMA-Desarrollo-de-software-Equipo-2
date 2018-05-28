package emaaredespacio.modelo;

import emaaredespacio.persistencia.controladores.UsuariosJpaController;
import emaaredespacio.persistencia.entidad.Colaboradores;
import emaaredespacio.persistencia.entidad.Usuarios;
import emaaredespacio.utilerias.Utileria;
import java.security.NoSuchAlgorithmException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Adrián Bustamante Zarate
 * @date 31/03/2018
 * @time 06:57:24 PM
 */
public class UsuarioSistema {

    private String usuario;
    private String contrasenia;
    private Colaborador colaborador;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public boolean autenticarSesion(UsuarioSistema user) throws NoSuchAlgorithmException {
        boolean banderaAutenticacion = false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        UsuariosJpaController controlador = new UsuariosJpaController(entityManagerFactory);
        Usuarios resultadoCuenta = controlador.verificarAutenticacion(user.getUsuario());
        if (resultadoCuenta != null) {
            if (Utileria.encriptarContrasena(user.getContrasenia()).equals(resultadoCuenta.getContrasenia())) {
                Colaboradores resultado = resultadoCuenta.getColaboradoresList().get(0);
                colaborador = new Colaborador();
                colaborador.setCargo(resultado.getIdUsuario().getTipo());
                colaborador.setApellidos(resultado.getApellidos());
                colaborador.setNombre(resultado.getNombre());
                colaborador.setIdColaborador(resultado.getIdColaborador());
                banderaAutenticacion = true;
            }
        }
        return banderaAutenticacion;
    }
}
