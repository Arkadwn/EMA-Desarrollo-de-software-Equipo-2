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
 * @author enriq
 */
@Entity
@Table(name = "promociones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Promociones.findAll", query = "SELECT p FROM Promociones p")
    , @NamedQuery(name = "Promociones.findByIdPromocion", query = "SELECT p FROM Promociones p WHERE p.idPromocion = :idPromocion")
    , @NamedQuery(name = "Promociones.findByNombrePromocion", query = "SELECT p FROM Promociones p WHERE p.nombrePromocion = :nombrePromocion")
    , @NamedQuery(name = "Promociones.findByAplicaDescuento", query = "SELECT p FROM Promociones p WHERE p.aplicaDescuento = :aplicaDescuento")
    , @NamedQuery(name = "Promociones.findByPorcentajeDescuento", query = "SELECT p FROM Promociones p WHERE p.porcentajeDescuento = :porcentajeDescuento")
    , @NamedQuery(name = "Promociones.findByFechaIni", query = "SELECT p FROM Promociones p WHERE p.fechaIni = :fechaIni")
    , @NamedQuery(name = "Promociones.findByFechaFin", query = "SELECT p FROM Promociones p WHERE p.fechaFin = :fechaFin")})
public class Promociones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPromocion")
    private Integer idPromocion;
    @Basic(optional = false)
    @Column(name = "nombre_promocion")
    private String nombrePromocion;
    @Column(name = "aplica_descuento")
    private Integer aplicaDescuento;
    @Column(name = "porcentaje_descuento")
    private String porcentajeDescuento;
    @Column(name = "fecha_ini")
    private String fechaIni;
    @Column(name = "fecha_fin")
    private String fechaFin;
    @Column(name = "idColaborador")
    private Integer idColaborador;

    public Promociones() {
    }

    public Promociones(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }

    public Promociones(Integer idPromocion, String nombrePromocion) {
        this.idPromocion = idPromocion;
        this.nombrePromocion = nombrePromocion;
    }

    public Integer getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Integer idPromocion) {
        this.idPromocion = idPromocion;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getNombrePromocion() {
        return nombrePromocion;
    }

    public void setNombrePromocion(String nombrePromocion) {
        this.nombrePromocion = nombrePromocion;
    }

    public Integer getAplicaDescuento() {
        return aplicaDescuento;
    }

    public void setAplicaDescuento(Integer aplicaDescuento) {
        this.aplicaDescuento = aplicaDescuento;
    }

    public String getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(String porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public String getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPromocion != null ? idPromocion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promociones)) {
            return false;
        }
        Promociones other = (Promociones) object;
        if ((this.idPromocion == null && other.idPromocion != null) || (this.idPromocion != null && !this.idPromocion.equals(other.idPromocion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "emaaredespacio.persistencia.entidad.Promociones[ idPromocion=" + idPromocion + " ]";
    }

}
