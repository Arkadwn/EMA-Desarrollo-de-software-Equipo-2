/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package emaaredespacio.persistencia.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Adrián Bustamante Zarate
 * @date 1/04/2018
 * @time 11:18:21 PM
 */
@Entity
@Table(name = "directores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Directores.findAll", query = "SELECT d FROM Directores d")
    , @NamedQuery(name = "Directores.findByIdDirector", query = "SELECT d FROM Directores d WHERE d.idDirector = :idDirector")
    , @NamedQuery(name = "Directores.findByNombre", query = "SELECT d FROM Directores d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "Directores.findByApellidoP", query = "SELECT d FROM Directores d WHERE d.apellidoP = :apellidoP")
    , @NamedQuery(name = "Directores.findByApellidoM", query = "SELECT d FROM Directores d WHERE d.apellidoM = :apellidoM")
    , @NamedQuery(name = "Directores.findByCorreo", query = "SELECT d FROM Directores d WHERE d.correo = :correo")
    , @NamedQuery(name = "Directores.findByDireccion", query = "SELECT d FROM Directores d WHERE d.direccion = :direccion")
    , @NamedQuery(name = "Directores.findByTelefono", query = "SELECT d FROM Directores d WHERE d.telefono = :telefono")})
public class Directores implements Serializable {

    @Column(name = "imagen")
    private String imagen;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDirector")
    private Integer idDirector;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellidoP")
    private String apellidoP;
    @Basic(optional = false)
    @Column(name = "apellidoM")
    private String apellidoM;
    @Column(name = "correo")
    private String correo;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private String telefono;
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuarios idUsuario;

    public Directores() {
    }

    public Directores(Integer idDirector) {
        this.idDirector = idDirector;
    }

    public Directores(Integer idDirector, String nombre, String apellidoP, String apellidoM) {
        this.idDirector = idDirector;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
    }

    public Integer getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(Integer idDirector) {
        this.idDirector = idDirector;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public Usuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDirector != null ? idDirector.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Directores)) {
            return false;
        }
        Directores other = (Directores) object;
        if ((this.idDirector == null && other.idDirector != null) || (this.idDirector != null && !this.idDirector.equals(other.idDirector))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "emaaredespacio.persistencia.entidad.Directores[ idDirector=" + idDirector + " ]";
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
