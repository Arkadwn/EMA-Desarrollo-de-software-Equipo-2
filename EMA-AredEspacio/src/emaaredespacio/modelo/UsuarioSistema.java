/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package emaaredespacio.modelo;

import emaaredespacio.persistencia.controladores.UsuariosJpaController;
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
    private int id;
    //private Colaborador colaborador;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    
    public boolean autenticarSesion(UsuarioSistema user) throws NoSuchAlgorithmException{
        boolean banderaAutenticacion = false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
        UsuariosJpaController controlador = new UsuariosJpaController(entityManagerFactory);
        UsuarioSistema resultadoCuenta = controlador.verificarAutenticacion(user.getUsuario());
        
        if(Utileria.encriptarContrasena(user.getContrasenia()).equals(resultadoCuenta.getContrasenia())){
            banderaAutenticacion = true;
        }
        return banderaAutenticacion;
    }
}
