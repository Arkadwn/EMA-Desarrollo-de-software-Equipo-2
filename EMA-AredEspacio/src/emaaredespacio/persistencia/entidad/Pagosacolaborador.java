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
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 6/05/2018
 * @time 02:53:29 PM
 */
@Entity
@Table(name = "pagosacolaborador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagosacolaborador.findAll", query = "SELECT p FROM Pagosacolaborador p")
    , @NamedQuery(name = "Pagosacolaborador.findByIdPago", query = "SELECT p FROM Pagosacolaborador p WHERE p.idPago = :idPago")
    , @NamedQuery(name = "Pagosacolaborador.findByComentario", query = "SELECT p FROM Pagosacolaborador p WHERE p.comentario = :comentario")
    , @NamedQuery(name = "Pagosacolaborador.findByNombreAlumno", query = "SELECT p FROM Pagosacolaborador p WHERE p.nombreAlumno = :nombreAlumno")
    , @NamedQuery(name = "Pagosacolaborador.findByNombreGrupo", query = "SELECT p FROM Pagosacolaborador p WHERE p.nombreGrupo = :nombreGrupo")
    , @NamedQuery(name = "Pagosacolaborador.findByNombreColaborador", query = "SELECT p FROM Pagosacolaborador p WHERE p.nombreColaborador = :nombreColaborador")
    , @NamedQuery(name = "Pagosacolaborador.findByFueEntregado", query = "SELECT p FROM Pagosacolaborador p WHERE p.fueEntregado = :fueEntregado")
    , @NamedQuery(name = "Pagosacolaborador.findByMonto", query = "SELECT p FROM Pagosacolaborador p WHERE p.monto = :monto")
    , @NamedQuery(name = "Pagosacolaborador.findByFechaDePago", query = "SELECT p FROM Pagosacolaborador p WHERE p.fechaDePago = :fechaDePago")})
public class Pagosacolaborador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPago")
    private Integer idPago;
    @Column(name = "comentario")
    private String comentario;
    @Column(name = "nombreAlumno")
    private String nombreAlumno;
    @Column(name = "nombreGrupo")
    private String nombreGrupo;
    @Column(name = "nombreColaborador")
    private String nombreColaborador;
    @Column(name = "fueEntregado")
    private Boolean fueEntregado;
    @Column(name = "monto")
    private Integer monto;
    @Column(name = "fechaDePago")
    @Temporal(TemporalType.DATE)
    private Date fechaDePago;

    public Pagosacolaborador() {
    }

    public Pagosacolaborador(Integer idPago) {
        this.idPago = idPago;
    }

    public Integer getIdPago() {
        return idPago;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getNombreColaborador() {
        return nombreColaborador;
    }

    public void setNombreColaborador(String nombreColaborador) {
        this.nombreColaborador = nombreColaborador;
    }

    public Boolean getFueEntregado() {
        return fueEntregado;
    }

    public void setFueEntregado(Boolean fueEntregado) {
        this.fueEntregado = fueEntregado;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public Date getFechaDePago() {
        return fechaDePago;
    }

    public void setFechaDePago(Date fechaDePago) {
        this.fechaDePago = fechaDePago;
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
        if (!(object instanceof Pagosacolaborador)) {
            return false;
        }
        Pagosacolaborador other = (Pagosacolaborador) object;
        if ((this.idPago == null && other.idPago != null) || (this.idPago != null && !this.idPago.equals(other.idPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "emaaredespacio.persistencia.entidad.Pagosacolaborador[ idPago=" + idPago + " ]";
    }

}
