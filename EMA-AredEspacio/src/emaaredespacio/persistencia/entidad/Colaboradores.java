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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 6/04/2018
 * @time 10:51:51 PM
 */
@Entity
@Table(name = "colaboradores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Colaboradores.findAll", query = "SELECT c FROM Colaboradores c")
    , @NamedQuery(name = "Colaboradores.findByIdColaborador", query = "SELECT c FROM Colaboradores c WHERE c.idColaborador = :idColaborador")
    , @NamedQuery(name = "Colaboradores.findByNombre", query = "SELECT c FROM Colaboradores c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Colaboradores.findByApellidos", query = "SELECT c FROM Colaboradores c WHERE c.apellidos = :apellidos")
    , @NamedQuery(name = "Colaboradores.findByCorreo", query = "SELECT c FROM Colaboradores c WHERE c.correo = :correo")
    , @NamedQuery(name = "Colaboradores.findByDireccion", query = "SELECT c FROM Colaboradores c WHERE c.direccion = :direccion")
    , @NamedQuery(name = "Colaboradores.findByTelefono", query = "SELECT c FROM Colaboradores c WHERE c.telefono = :telefono")
    , @NamedQuery(name = "Colaboradores.findByImagen", query = "SELECT c FROM Colaboradores c WHERE c.imagen = :imagen")
    , @NamedQuery(name = "Colaboradores.findByMontoApagar", query = "SELECT c FROM Colaboradores c WHERE c.montoApagar = :montoApagar")
    , @NamedQuery(name = "Colaboradores.findByTipoPago", query = "SELECT c FROM Colaboradores c WHERE c.tipoPago = :tipoPago")
    , @NamedQuery(name = "Colaboradores.findByEstado", query = "SELECT c FROM Colaboradores c WHERE c.estado = :estado")})
public class Colaboradores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idColaborador")
    private Integer idColaborador;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "correo")
    private String correo;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "montoApagar")
    private String montoApagar;
    @Column(name = "tipoPago")
    private String tipoPago;
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuarios idUsuario;
    @OneToMany(mappedBy = "idColaborador")
    private List<Grupos> gruposList;

    public Colaboradores() {
    }

    public Colaboradores(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public Colaboradores(Integer idColaborador, String nombre, String apellidos) {
        this.idColaborador = idColaborador;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getMontoApagar() {
        return montoApagar;
    }

    public void setMontoApagar(String montoApagar) {
        this.montoApagar = montoApagar;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Usuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuarios idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public List<Grupos> getGruposList() {
        return gruposList;
    }

    public void setGruposList(List<Grupos> gruposList) {
        this.gruposList = gruposList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idColaborador != null ? idColaborador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Colaboradores)) {
            return false;
        }
        Colaboradores other = (Colaboradores) object;
        if ((this.idColaborador == null && other.idColaborador != null) || (this.idColaborador != null && !this.idColaborador.equals(other.idColaborador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "emaaredespacio.persistencia.entidad.Colaboradores[ idColaborador=" + idColaborador + " ]";
    }

}
