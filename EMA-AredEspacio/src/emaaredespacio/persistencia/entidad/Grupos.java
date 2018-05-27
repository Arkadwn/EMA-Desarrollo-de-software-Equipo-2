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
 * @author arkadwn
 */
@Entity
@Table(name = "grupos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupos.findAll", query = "SELECT g FROM Grupos g")
    , @NamedQuery(name = "Grupos.findByIdGrupo", query = "SELECT g FROM Grupos g WHERE g.idGrupo = :idGrupo")
    , @NamedQuery(name = "Grupos.findByTipoDeBaile", query = "SELECT g FROM Grupos g WHERE g.tipoDeBaile = :tipoDeBaile")
    , @NamedQuery(name = "Grupos.findByCupo", query = "SELECT g FROM Grupos g WHERE g.cupo = :cupo")
    , @NamedQuery(name = "Grupos.findByMensualidad", query = "SELECT g FROM Grupos g WHERE g.mensualidad = :mensualidad")
    , @NamedQuery(name = "Grupos.findByEspacioDisponible", query = "SELECT g FROM Grupos g WHERE g.espacioDisponible = :espacioDisponible")
    , @NamedQuery(name = "Grupos.findByInscripcion", query = "SELECT g FROM Grupos g WHERE g.inscripcion = :inscripcion")
    , @NamedQuery(name = "Grupos.findByEstado", query = "SELECT g FROM Grupos g WHERE g.estado = :estado")
    , @NamedQuery(name = "Grupos.findByHorarioAsignado", query = "SELECT g FROM Grupos g WHERE g.horarioAsignado = :horarioAsignado")})
public class Grupos implements Serializable {

    @OneToMany(mappedBy = "idGrupo")
    private List<Inscripciones> inscripcionesList;

    @OneToMany(mappedBy = "idGrupo")
    private List<Pagosalumnos> pagosalumnosList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idGrupo")
    private Integer idGrupo;
    @Column(name = "tipoDeBaile")
    private String tipoDeBaile;
    @Column(name = "cupo")
    private Integer cupo;
    @Column(name = "mensualidad")
    private Integer mensualidad;
    @Column(name = "espacioDisponible")
    private Integer espacioDisponible;
    @Column(name = "inscripcion")
    private Integer inscripcion;
    @Column(name = "estado")
    private String estado;
    @Column(name = "horario_asignado")
    private Integer horarioAsignado;
    @JoinColumn(name = "idColaborador", referencedColumnName = "idColaborador")
    @ManyToOne
    private Colaboradores idColaborador;

    public Grupos() {
    }

    public Grupos(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getTipoDeBaile() {
        return tipoDeBaile;
    }

    public void setTipoDeBaile(String tipoDeBaile) {
        this.tipoDeBaile = tipoDeBaile;
    }

    public Integer getCupo() {
        return cupo;
    }

    public void setCupo(Integer cupo) {
        this.cupo = cupo;
    }

    public Integer getMensualidad() {
        return mensualidad;
    }

    public void setMensualidad(Integer mensualidad) {
        this.mensualidad = mensualidad;
    }

    public Integer getEspacioDisponible() {
        return espacioDisponible;
    }

    public void setEspacioDisponible(Integer espacioDisponible) {
        this.espacioDisponible = espacioDisponible;
    }

    public Integer getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Integer inscripcion) {
        this.inscripcion = inscripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getHorarioAsignado() {
        return horarioAsignado;
    }

    public void setHorarioAsignado(Integer horarioAsignado) {
        this.horarioAsignado = horarioAsignado;
    }

    public Colaboradores getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Colaboradores idColaborador) {
        this.idColaborador = idColaborador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrupo != null ? idGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupos)) {
            return false;
        }
        Grupos other = (Grupos) object;
        if ((this.idGrupo == null && other.idGrupo != null) || (this.idGrupo != null && !this.idGrupo.equals(other.idGrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "emaaredespacio.persistencia.entidad.Grupos[ idGrupo=" + idGrupo + " ]";
    }

    @XmlTransient
    public List<Pagosalumnos> getPagosalumnosList() {
        return pagosalumnosList;
    }

    public void setPagosalumnosList(List<Pagosalumnos> pagosalumnosList) {
        this.pagosalumnosList = pagosalumnosList;
    }

    @XmlTransient
    public List<Inscripciones> getInscripcionesList() {
        return inscripcionesList;
    }

    public void setInscripcionesList(List<Inscripciones> inscripcionesList) {
        this.inscripcionesList = inscripcionesList;
    }
    
}
