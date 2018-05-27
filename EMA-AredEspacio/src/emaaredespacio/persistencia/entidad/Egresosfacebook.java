/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.persistencia.entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author arkadwn
 */
@Entity
@Table(name = "egresosfacebook")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Egresosfacebook.findAll", query = "SELECT e FROM Egresosfacebook e")
    , @NamedQuery(name = "Egresosfacebook.findByIdEgresoFacebook", query = "SELECT e FROM Egresosfacebook e WHERE e.idEgresoFacebook = :idEgresoFacebook")
    , @NamedQuery(name = "Egresosfacebook.findByCreador", query = "SELECT e FROM Egresosfacebook e WHERE e.creador = :creador")
    , @NamedQuery(name = "Egresosfacebook.findByFechaInicio", query = "SELECT e FROM Egresosfacebook e WHERE e.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Egresosfacebook.findByFechaFin", query = "SELECT e FROM Egresosfacebook e WHERE e.fechaFin = :fechaFin")
    , @NamedQuery(name = "Egresosfacebook.findByDescripcion", query = "SELECT e FROM Egresosfacebook e WHERE e.descripcion = :descripcion")
    , @NamedQuery(name = "Egresosfacebook.findByLink", query = "SELECT e FROM Egresosfacebook e WHERE e.link = :link")
    , @NamedQuery(name = "Egresosfacebook.findByCosto", query = "SELECT e FROM Egresosfacebook e WHERE e.costo = :costo")
    , @NamedQuery(name = "Egresosfacebook.findByActiva", query = "SELECT e FROM Egresosfacebook e WHERE e.activa = :activa")})
public class Egresosfacebook implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEgresoFacebook")
    private Integer idEgresoFacebook;
    @Column(name = "creador")
    private String creador;
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fechaFin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "link")
    private String link;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "costo")
    private Double costo;
    @Column(name = "activa")
    private Boolean activa;

    public Egresosfacebook() {
    }

    public Egresosfacebook(Integer idEgresoFacebook) {
        this.idEgresoFacebook = idEgresoFacebook;
    }

    public Integer getIdEgresoFacebook() {
        return idEgresoFacebook;
    }

    public void setIdEgresoFacebook(Integer idEgresoFacebook) {
        this.idEgresoFacebook = idEgresoFacebook;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEgresoFacebook != null ? idEgresoFacebook.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Egresosfacebook)) {
            return false;
        }
        Egresosfacebook other = (Egresosfacebook) object;
        if ((this.idEgresoFacebook == null && other.idEgresoFacebook != null) || (this.idEgresoFacebook != null && !this.idEgresoFacebook.equals(other.idEgresoFacebook))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "emaaredespacio.persistencia.entidad.Egresosfacebook[ idEgresoFacebook=" + idEgresoFacebook + " ]";
    }
    
}
