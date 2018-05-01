/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package emaaredespacio.persistencia.entidad;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 17/04/2018
 * @time 06:29:07 PM
 */
@Entity
@Table(name = "alumnos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alumnos.findAll", query = "SELECT a FROM Alumnos a")
    , @NamedQuery(name = "Alumnos.findByMatricula", query = "SELECT a FROM Alumnos a WHERE a.matricula = :matricula")
    , @NamedQuery(name = "Alumnos.findByNombre", query = "SELECT a FROM Alumnos a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Alumnos.findByApellidos", query = "SELECT a FROM Alumnos a WHERE a.apellidos = :apellidos")
    , @NamedQuery(name = "Alumnos.findByDireccion", query = "SELECT a FROM Alumnos a WHERE a.direccion = :direccion")
    , @NamedQuery(name = "Alumnos.findByCorreo", query = "SELECT a FROM Alumnos a WHERE a.correo = :correo")
    , @NamedQuery(name = "Alumnos.findByTelefono", query = "SELECT a FROM Alumnos a WHERE a.telefono = :telefono")
    , @NamedQuery(name = "Alumnos.findByEstado", query = "SELECT a FROM Alumnos a WHERE a.estado = :estado")
    , @NamedQuery(name = "Alumnos.findByImagen", query = "SELECT a FROM Alumnos a WHERE a.imagen = :imagen")})
public class Alumnos implements Serializable {

    @OneToMany(mappedBy = "matricula")
    private List<Pagosalumnos> pagosalumnosList;

    @OneToMany(mappedBy = "idAlumno")
    private List<Inscripciones> inscripcionesList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "matricula")
    private Integer matricula;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "correo")
    private String correo;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "estado")
    private String estado;
    @Column(name = "imagen")
    private String imagen;

    public Alumnos() {
    }

    public Alumnos(Integer matricula) {
        this.matricula = matricula;
    }

    public Alumnos(Integer matricula, String nombre, String apellidos) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matricula != null ? matricula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alumnos)) {
            return false;
        }
        Alumnos other = (Alumnos) object;
        if ((this.matricula == null && other.matricula != null) || (this.matricula != null && !this.matricula.equals(other.matricula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "emaaredespacio.persistencia.entidad.Alumnos[ matricula=" + matricula + " ]";
    }

    @XmlTransient
    public List<Inscripciones> getInscripcionesList() {
        return inscripcionesList;
    }

    public void setInscripcionesList(List<Inscripciones> inscripcionesList) {
        this.inscripcionesList = inscripcionesList;
    }

    @XmlTransient
    public List<Pagosalumnos> getPagosalumnosList() {
        return pagosalumnosList;
    }

    public void setPagosalumnosList(List<Pagosalumnos> pagosalumnosList) {
        this.pagosalumnosList = pagosalumnosList;
    }

}
