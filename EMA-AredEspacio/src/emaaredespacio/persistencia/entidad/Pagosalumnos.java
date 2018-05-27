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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "pagosalumnos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagosalumnos.findAll", query = "SELECT p FROM Pagosalumnos p")
    , @NamedQuery(name = "Pagosalumnos.findByIdPago", query = "SELECT p FROM Pagosalumnos p WHERE p.idPago = :idPago")
    , @NamedQuery(name = "Pagosalumnos.findByFechaPago", query = "SELECT p FROM Pagosalumnos p WHERE p.fechaPago = :fechaPago")
    , @NamedQuery(name = "Pagosalumnos.findByMonto", query = "SELECT p FROM Pagosalumnos p WHERE p.monto = :monto")
    , @NamedQuery(name = "Pagosalumnos.findByPorcentajeDescuento", query = "SELECT p FROM Pagosalumnos p WHERE p.porcentajeDescuento = :porcentajeDescuento")
    , @NamedQuery(name = "Pagosalumnos.findByTotal", query = "SELECT p FROM Pagosalumnos p WHERE p.total = :total")
    , @NamedQuery(name = "Pagosalumnos.findByTipoPago", query = "SELECT p FROM Pagosalumnos p WHERE p.tipoPago = :tipoPago")})
public class Pagosalumnos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPago")
    private Integer idPago;
    @Column(name = "fechaPago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @Column(name = "monto")
    private String monto;
    @Column(name = "porcentajeDescuento")
    private Integer porcentajeDescuento;
    @Column(name = "total")
    private String total;
    @Column(name = "tipo_pago")
    private String tipoPago;
    @JoinColumn(name = "matricula", referencedColumnName = "matricula")
    @ManyToOne
    private Alumnos matricula;
    @JoinColumn(name = "idGrupo", referencedColumnName = "idGrupo")
    @ManyToOne
    private Grupos idGrupo;

    public Pagosalumnos() {
    }

    public Pagosalumnos(Integer idPago) {
        this.idPago = idPago;
    }

    public Integer getIdPago() {
        return idPago;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public Integer getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Integer porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public Alumnos getMatricula() {
        return matricula;
    }

    public void setMatricula(Alumnos matricula) {
        this.matricula = matricula;
    }

    public Grupos getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Grupos idGrupo) {
        this.idGrupo = idGrupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPago != null ? idPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pagosalumnos)) {
            return false;
        }
        Pagosalumnos other = (Pagosalumnos) object;
        if ((this.idPago == null && other.idPago != null) || (this.idPago != null && !this.idPago.equals(other.idPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "emaaredespacio.persistencia.entidad.Pagosalumnos[ idPago=" + idPago + " ]";
    }
    
}
