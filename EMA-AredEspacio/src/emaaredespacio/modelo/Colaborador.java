package emaaredespacio.modelo;

import emaaredespacio.utilerias.Utileria;
import emaaredespacio.persistencia.controladores.ColaboradoresJpaController;
import emaaredespacio.persistencia.controladores.IControladorColaborador;
import emaaredespacio.persistencia.entidad.Colaboradores;
import emaaredespacio.persistencia.entidad.Usuarios;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 30/03/2018
 * @time 12:26:38 PM
 */
public class Colaborador implements IColaborador {

    private String imagenPerfil;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String correo;
    private String direccion;
    private String nombreUsuario;
    private String contraseña;
    private String tipoPago;
    private String montoAPagar;
    private String estado;
    private Integer idUsuario;
    private Integer idColaborador;
    private Integer cargo;

    public Integer getCargo() {
        return cargo;
    }

    public void setCargo(Integer cargo) {
        this.cargo = cargo;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    private static final String PATRON_CORREO = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public Colaborador() {
        estado = "A";
        idColaborador = null;
        idUsuario = null;
        cargo = 1;
    }

    public Colaborador(String imagenPerfil, String nombre, String apellidos) {
        this.imagenPerfil = imagenPerfil;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public String getMontoAPagar() {
        return montoAPagar;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public void setMontoAPagar(String montoAPagar) {
        this.montoAPagar = montoAPagar;
    }

    @Override
    public boolean registrarColaborador(Colaborador colaborador) {
        boolean validacion = false;
        String ecriptada = "";
        try {
            ecriptada = Utileria.encriptarContrasena(colaborador.getContraseña());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Colaborador.class.getName()).log(Level.SEVERE, null, ex);
        }

        Usuarios usuario = new Usuarios();
        usuario.setContrasenia(ecriptada);
        usuario.setNombreUsuario(colaborador.getNombreUsuario());
        usuario.setTipo(colaborador.getCargo());
        usuario.setIdUsuario(colaborador.getIdUsuario());

        Colaboradores nuevoColaborador = new Colaboradores();
        nuevoColaborador.setApellidos(colaborador.getApellidos());
        nuevoColaborador.setNombre(colaborador.getNombre());
        nuevoColaborador.setCorreo(colaborador.getCorreo());
        nuevoColaborador.setDireccion(colaborador.getDireccion());
        nuevoColaborador.setEstado(colaborador.getEstado());
        nuevoColaborador.setIdUsuario(usuario);
        nuevoColaborador.setIdColaborador(colaborador.getIdColaborador());
        nuevoColaborador.setTelefono(colaborador.getTelefono());
        nuevoColaborador.setTipoPago(colaborador.getTipoPago());
        nuevoColaborador.setMontoApagar(colaborador.getMontoAPagar());
        nuevoColaborador.setImagen(colaborador.getImagenPerfil());

        IControladorColaborador controlador = new ColaboradoresJpaController();

        validacion = controlador.guardarColaborador(nuevoColaborador, usuario);

        return validacion;
    }

    @Override
    public boolean[] validarCampos(Colaborador colaborador, String contraseña) {
        boolean validacion[] = new boolean[9];

        //Nombre
        validacion[0] = colaborador.getNombre().trim().length() >= 2 && colaborador.getNombre().trim().length() <= 50;
        //Apellidos
        validacion[1] = colaborador.getApellidos().trim().length() >= 2 && colaborador.getApellidos().trim().length() <= 100;
        //Telefono
        validacion[2] = colaborador.getTelefono().trim().length() == 10;
        //Correo
        validacion[3] = validarFormatoCorreo(colaborador.getCorreo());
        //Direccion
        validacion[4] = colaborador.getDireccion().trim().length() >= 2 && colaborador.getDireccion().trim().length() <= 50;
        //Nombre usuario
        validacion[5] = colaborador.getNombreUsuario().trim().length() >= 2 && colaborador.getNombreUsuario().trim().length() <= 50;
        //Contraseña

        validacion[6] = colaborador.getContraseña().equals(contraseña);
        //Contraseña formato
        validacion[7] = validarFormatoContraseña(colaborador.getContraseña());
        //Campos validos
        validacion[8] = validacion[0] && validacion[1] && validacion[2] && validacion[3] && validacion[4] && validacion[5] && validacion[6] && validacion[7];

        return validacion;
    }

    private boolean validarFormatoCorreo(String email) {
        Pattern patron = Pattern.compile(PATRON_CORREO);
        Matcher concordancia = patron.matcher(email);
        return concordancia.matches();
    }

    private boolean validarFormatoContraseña(String contraseña) {
        Pattern patron = Pattern.compile("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,100}$");
        Matcher concordancia = patron.matcher(contraseña);
        return concordancia.matches();
    }

    @Override
    public List<Colaborador> buscarColaborador(String palabraClave) {
        List<Colaborador> colaboradores = null;

        IControladorColaborador controlador = new ColaboradoresJpaController();

        List<Colaboradores> lista = controlador.buscarColaborador(palabraClave);

        colaboradores = convertirListas(lista);

        return colaboradores;
    }

    public Colaborador buscarColaboradorSegunID(int idColaborador) {
        ColaboradoresJpaController controlador = new ColaboradoresJpaController();
        Colaboradores colaboradorObtendo = controlador.buscarColaborador(idColaborador);
        Colaborador colaborador = new Colaborador();
        if (colaboradorObtendo != null) {
            colaborador.setApellidos(colaboradorObtendo.getApellidos());
            colaborador.setContraseña(colaboradorObtendo.getIdUsuario().getContrasenia());
            colaborador.setCorreo(colaboradorObtendo.getCorreo());
            colaborador.setDireccion(colaboradorObtendo.getDireccion());
            colaborador.setEstado(colaboradorObtendo.getEstado());
            colaborador.setIdColaborador(idColaborador);
            colaborador.setIdUsuario(colaboradorObtendo.getIdColaborador());
            colaborador.setImagenPerfil(colaboradorObtendo.getImagen());
            colaborador.setMontoAPagar(colaboradorObtendo.getMontoApagar());
            colaborador.setNombre(colaboradorObtendo.getNombre());
            colaborador.setNombreUsuario(colaboradorObtendo.getIdUsuario().getNombreUsuario());
            colaborador.setTelefono(colaboradorObtendo.getTelefono());
            colaborador.setTipoPago(colaboradorObtendo.getTipoPago());
        } else {
            colaborador = null;
        }
        return colaborador;
    }

    private List<Colaborador> convertirListas(List<Colaboradores> lista) {
        List<Colaborador> colaboradores = new ArrayList();

        for (Colaboradores colaborador : lista) {
            Colaborador nuevoColaborador = new Colaborador();

            nuevoColaborador.setApellidos(colaborador.getApellidos());
            nuevoColaborador.setNombre(colaborador.getNombre());
            nuevoColaborador.setCorreo(colaborador.getCorreo());
            nuevoColaborador.setDireccion(colaborador.getDireccion());
            nuevoColaborador.setEstado(colaborador.getEstado());
            nuevoColaborador.setIdUsuario(colaborador.getIdUsuario().getIdUsuario());
            nuevoColaborador.setCargo(colaborador.getIdUsuario().getTipo());
            nuevoColaborador.setIdColaborador(colaborador.getIdColaborador());
            nuevoColaborador.setTelefono(colaborador.getTelefono());
            nuevoColaborador.setTipoPago(colaborador.getTipoPago());
            nuevoColaborador.setMontoAPagar(colaborador.getMontoApagar());
            nuevoColaborador.setImagenPerfil(colaborador.getImagen());
            nuevoColaborador.setNombreUsuario(colaborador.getIdUsuario().getNombreUsuario());
            nuevoColaborador.setContraseña(colaborador.getIdUsuario().getContrasenia());

            colaboradores.add(nuevoColaborador);
        }

        return colaboradores;
    }

    @Override
    public boolean editarColaborador(Colaborador colaborador, boolean nuevaContraseña) {
        boolean validacion = false;
        String encriptada = colaborador.getContraseña();

        if (nuevaContraseña) {
            try {
                encriptada = Utileria.encriptarContrasena(colaborador.getContraseña());
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Colaborador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Usuarios usuario = new Usuarios();
        usuario.setContrasenia(encriptada);
        usuario.setNombreUsuario(colaborador.getNombreUsuario());
        usuario.setTipo(colaborador.getCargo());
        usuario.setIdUsuario(colaborador.getIdUsuario());

        Colaboradores nuevoColaborador = new Colaboradores();
        nuevoColaborador.setApellidos(colaborador.getApellidos());
        nuevoColaborador.setNombre(colaborador.getNombre());
        nuevoColaborador.setCorreo(colaborador.getCorreo());
        nuevoColaborador.setDireccion(colaborador.getDireccion());
        nuevoColaborador.setEstado(colaborador.getEstado());
        nuevoColaborador.setIdUsuario(usuario);
        nuevoColaborador.setIdColaborador(colaborador.getIdColaborador());
        nuevoColaborador.setTelefono(colaborador.getTelefono());
        nuevoColaborador.setTipoPago(colaborador.getTipoPago());
        nuevoColaborador.setMontoApagar(colaborador.getMontoAPagar());
        nuevoColaborador.setImagen(colaborador.getImagenPerfil());

        IControladorColaborador controlador = new ColaboradoresJpaController();

        validacion = controlador.editarColborador(nuevoColaborador, usuario);

        return validacion;
    }

}
