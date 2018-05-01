package emaaredespacio.modelo;

import emaaredespacio.persistencia.controladores.AlumnosJpaController;
import emaaredespacio.persistencia.controladores.IControladorAlumnos;
import emaaredespacio.persistencia.entidad.Alumnos;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 1/04/2018
 * @time 06:35:03 PM
 */
public class Alumno implements IAlumno{
    private String nombre;
    private String apellidos;
    private String imagenPerfil;
    private String direccion;
    private String telefono;
    private String estado;
    private String correo;
    private Integer matricula;
    private static final String PATRON_CORREO = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public Alumno(){
        estado="A";
        matricula = null;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }
    
    public String getNombreCompleto(){
        return nombre + " " +apellidos;
    }
    
    private boolean validarFormatoCorreo(String email) {
        Pattern patron = Pattern.compile(PATRON_CORREO);
        Matcher concordancia = patron.matcher(email);
        return concordancia.matches();
    }

    @Override
    public boolean guardarAlumno(Alumno alumno) {
        boolean validacion = false;
        IControladorAlumnos controlador = new AlumnosJpaController();
        
        Alumnos nuevoAlumno = new Alumnos();
        
        nuevoAlumno.setImagen(alumno.getImagenPerfil());
        nuevoAlumno.setApellidos(alumno.getApellidos());
        nuevoAlumno.setNombre(alumno.getNombre());
        nuevoAlumno.setCorreo(alumno.getCorreo());
        nuevoAlumno.setEstado(alumno.getEstado());
        nuevoAlumno.setDireccion(alumno.getDireccion());
        nuevoAlumno.setMatricula(alumno.getMatricula());
        nuevoAlumno.setTelefono(alumno.getTelefono());
        
        validacion = controlador.guardarAlumno(nuevoAlumno);
        
        return validacion;
    }

    @Override
    public boolean[] validarCampos(Alumno alumno) {
        boolean[] validaciones = new boolean[6];
        
        //Nombre
        validaciones[0] = alumno.getNombre().length() >= 2 && alumno.getNombre().length() <= 50;
        //Apellidos
        validaciones[1] = alumno.getApellidos().length() >= 2 && alumno.getApellidos().length() <= 100;
        //Correo
        validaciones[2] = validarFormatoCorreo(alumno.getCorreo());
        //Direccion
        validaciones[3] = alumno.getDireccion().length() >= 2 && alumno.getDireccion().length() <= 50;
        //Telefono
        validaciones[4] = alumno.getTelefono().length() >= 2 && alumno.getTelefono().length() <= 50;
        
        validaciones[5] = validaciones[0] && validaciones[1] && validaciones[2] && validaciones[3] && validaciones[4];
        
        return validaciones;
    }

    @Override
    public boolean editarAlumo(Alumno alumno) {
        boolean validacion = false;
        
        IControladorAlumnos contrlador = new AlumnosJpaController();
        
        Alumnos nuevoAlumno = new Alumnos();
        
        nuevoAlumno.setApellidos(alumno.getApellidos());
        nuevoAlumno.setNombre(alumno.getNombre());
        nuevoAlumno.setEstado(alumno.getEstado());
        nuevoAlumno.setTelefono(alumno.getTelefono());
        nuevoAlumno.setCorreo(alumno.getCorreo());
        nuevoAlumno.setImagen(alumno.getImagenPerfil());
        nuevoAlumno.setMatricula(alumno.getMatricula());
        nuevoAlumno.setDireccion(alumno.getDireccion());
        
        validacion = contrlador.editarAlumno(nuevoAlumno);
        
        return validacion;
    }

    @Override
    public List<Alumno> buscarAlumno(String palabraClave) {
        IControladorAlumnos controlador = new AlumnosJpaController();
        List<Alumno> alumnos = null;
        
        List<Alumnos> lista = controlador.buscarAlumno(palabraClave);
        
        alumnos = convertirLista(lista);
        
        return alumnos;
    }
    
    private List<Alumno> convertirLista(List<Alumnos> lista){
        List<Alumno> alumnos = new ArrayList();
        
        for(Alumnos alumno: lista){
            Alumno nuevoAlumno = new Alumno();
            nuevoAlumno.setApellidos(alumno.getApellidos());
            nuevoAlumno.setNombre(alumno.getNombre());
            nuevoAlumno.setCorreo(alumno.getCorreo());
            nuevoAlumno.setDireccion(alumno.getDireccion());
            nuevoAlumno.setEstado(alumno.getEstado());
            nuevoAlumno.setImagenPerfil(alumno.getImagen());
            nuevoAlumno.setMatricula(alumno.getMatricula());
            nuevoAlumno.setTelefono(alumno.getTelefono());
            alumnos.add(nuevoAlumno);
        }
        
        return alumnos;
    }

    @Override
    public List<Alumno> buscarAlumnosNoInscritos(int idGrupo) {
        List<Alumnos> resultadoBusqueda = null;
        List<Alumno> alumnos = null;
        
        AlumnosJpaController controlador = new AlumnosJpaController();
        resultadoBusqueda = controlador.sacarAlumnosNoInscritos(idGrupo);
        
        alumnos = convertirLista(resultadoBusqueda);
        
        return alumnos;
    }
    
}
