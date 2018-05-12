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
@Table(name = "ingresos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingresos.findAll", query = "SELECT i FROM Ingresos i")
    , @NamedQuery(name = "Ingresos.findByIdIngreso", query = "SELECT i FROM Ingresos i WHERE i.idIngreso = :idIngreso")
    , @NamedQuery(name = "Ingresos.findByPagoColaboradorID", query = "SELECT i FROM Ingresos i WHERE i.pagoColaboradorID = :pagoColaboradorID")
    , @NamedQuery(name = "Ingresos.findByPagoRentaID", query = "SELECT i FROM Ingresos i WHERE i.pagoRentaID = :pagoRentaID")
    , @NamedQuery(name = "Ingresos.findByMonto", query = "SELECT i FROM Ingresos i WHERE i.monto = :monto")
    , @NamedQuery(name = "Ingresos.findByRecibo", query = "SELECT i FROM Ingresos i WHERE i.recibo = :recibo")})
public class Ingresos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idIngreso")
    private Integer idIngreso;
    @Column(name = "pagoColaboradorID")
    private Integer pagoColaboradorID;
    @Column(name = "pagoRentaID")
    private Integer pagoRentaID;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto")
    private Double monto;
    @Column(name = "recibo")
    private Boolean recibo;

    public Ingresos() {
    }

    public Ingresos(Integer idIngreso) {
        this.idIngreso = idIngreso;
    }

    public Integer getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(Integer idIngreso) {
        this.idIngreso = idIngreso;
    }

    public Integer getPagoColaboradorID() {
        return pagoColaboradorID;
    }

    public void setPagoColaboradorID(Integer pagoColaboradorID) {
        this.pagoColaboradorID = pagoColaboradorID;
    }

    public Integer getPagoRentaID() {
        return pagoRentaID;
    }

    public void setPagoRentaID(Integer pagoRentaID) {
        this.pagoRentaID = pagoRentaID;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Boolean getRecibo() {
        return recibo;
    }

    public void setRecibo(Boolean recibo) {
        this.recibo = recibo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idIngreso != null ? idIngreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingresos)) {
            return false;
        }
        Ingresos other = (Ingresos) object;
        if ((this.idIngreso == null && other.idIngreso != null) || (this.idIngreso != null && !this.idIngreso.equals(other.idIngreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "emaaredespacio.persistencia.entidad.Ingresos[ idIngreso=" + idIngreso + " ]";
    }
    
}
