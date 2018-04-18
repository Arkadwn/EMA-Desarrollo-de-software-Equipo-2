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
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 17/04/2018
 * @time 06:29:06 PM
 */
@Entity
@Table(name = "rentas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rentas.findAll", query = "SELECT r FROM Rentas r")
    , @NamedQuery(name = "Rentas.findByIdRenta", query = "SELECT r FROM Rentas r WHERE r.idRenta = :idRenta")
    , @NamedQuery(name = "Rentas.findByMonto", query = "SELECT r FROM Rentas r WHERE r.monto = :monto")
    , @NamedQuery(name = "Rentas.findByFecha", query = "SELECT r FROM Rentas r WHERE r.fecha = :fecha")
    , @NamedQuery(name = "Rentas.findByHoraIni", query = "SELECT r FROM Rentas r WHERE r.horaIni = :horaIni")
    , @NamedQuery(name = "Rentas.findByHoraFin", query = "SELECT r FROM Rentas r WHERE r.horaFin = :horaFin")
    , @NamedQuery(name = "Rentas.findByEstado", query = "SELECT r FROM Rentas r WHERE r.estado = :estado")})
public class Rentas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRenta")
    private Integer idRenta;
    @Column(name = "monto")
    private Integer monto;
    @Column(name = "fecha")
    private String fecha;
    @Column(name = "horaIni")
    private Integer horaIni;
    @Column(name = "horaFin")
    private Integer horaFin;
    @Column(name = "estado")
    private Boolean estado;
    @JoinColumn(name = "idCliente", referencedColumnName = "idCliente")
    @ManyToOne
    private Clientes idCliente;

    public Rentas() {
    }

    public Rentas(Integer idRenta) {
        this.idRenta = idRenta;
    }

    public Integer getIdRenta() {
        return idRenta;
    }

    public void setIdRenta(Integer idRenta) {
        this.idRenta = idRenta;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(Integer horaIni) {
        this.horaIni = horaIni;
    }

    public Integer getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Integer horaFin) {
        this.horaFin = horaFin;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Clientes getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Clientes idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRenta != null ? idRenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rentas)) {
            return false;
        }
        Rentas other = (Rentas) object;
        if ((this.idRenta == null && other.idRenta != null) || (this.idRenta != null && !this.idRenta.equals(other.idRenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "emaaredespacio.persistencia.entidad.Rentas[ idRenta=" + idRenta + " ]";
    }

}
