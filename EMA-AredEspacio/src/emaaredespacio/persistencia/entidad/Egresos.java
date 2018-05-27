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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author arkadwn
 */
@Entity
@Table(name = "egresos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Egresos.findAll", query = "SELECT e FROM Egresos e")
    , @NamedQuery(name = "Egresos.findByIdEgreso", query = "SELECT e FROM Egresos e WHERE e.idEgreso = :idEgreso")
    , @NamedQuery(name = "Egresos.findByDescripcion", query = "SELECT e FROM Egresos e WHERE e.descripcion = :descripcion")
    , @NamedQuery(name = "Egresos.findByMonto", query = "SELECT e FROM Egresos e WHERE e.monto = :monto")
    , @NamedQuery(name = "Egresos.findByFecha", query = "SELECT e FROM Egresos e WHERE e.fecha = :fecha")})
public class Egresos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEgreso")
    private Integer idEgreso;
    @Column(name = "descripcion")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto")
    private Double monto;
    @Column(name = "fecha")
    private String fecha;

    public Egresos() {
    }

    public Egresos(Integer idEgreso) {
        this.idEgreso = idEgreso;
    }

    public Integer getIdEgreso() {
        return idEgreso;
    }

    public void setIdEgreso(Integer idEgreso) {
        this.idEgreso = idEgreso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEgreso != null ? idEgreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Egresos)) {
            return false;
        }
        Egresos other = (Egresos) object;
        if ((this.idEgreso == null && other.idEgreso != null) || (this.idEgreso != null && !this.idEgreso.equals(other.idEgreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "emaaredespacio.persistencia.entidad.Egresos[ idEgreso=" + idEgreso + " ]";
    }
    
}
