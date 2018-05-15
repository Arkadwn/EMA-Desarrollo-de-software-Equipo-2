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
 * @date 14/05/2018
 * @time 09:54:11 PM
 */
@Entity
@Table(name = "pagos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagos.findAll", query = "SELECT p FROM Pagos p")
    , @NamedQuery(name = "Pagos.findByIdPago", query = "SELECT p FROM Pagos p WHERE p.idPago = :idPago")
    , @NamedQuery(name = "Pagos.findByComentario", query = "SELECT p FROM Pagos p WHERE p.comentario = :comentario")
    , @NamedQuery(name = "Pagos.findByNombreAlumno", query = "SELECT p FROM Pagos p WHERE p.nombreAlumno = :nombreAlumno")
    , @NamedQuery(name = "Pagos.findByIdAlumno", query = "SELECT p FROM Pagos p WHERE p.idAlumno = :idAlumno")
    , @NamedQuery(name = "Pagos.findByIdGrupo", query = "SELECT p FROM Pagos p WHERE p.idGrupo = :idGrupo")
    , @NamedQuery(name = "Pagos.findByIdColaborador", query = "SELECT p FROM Pagos p WHERE p.idColaborador = :idColaborador")
    , @NamedQuery(name = "Pagos.findByNombreGrupo", query = "SELECT p FROM Pagos p WHERE p.nombreGrupo = :nombreGrupo")
    , @NamedQuery(name = "Pagos.findByNombreColaborador", query = "SELECT p FROM Pagos p WHERE p.nombreColaborador = :nombreColaborador")
    , @NamedQuery(name = "Pagos.findByFueEntregado", query = "SELECT p FROM Pagos p WHERE p.fueEntregado = :fueEntregado")
    , @NamedQuery(name = "Pagos.findByMonto", query = "SELECT p FROM Pagos p WHERE p.monto = :monto")
    , @NamedQuery(name = "Pagos.findByFechaDePago", query = "SELECT p FROM Pagos p WHERE p.fechaDePago = :fechaDePago")})
public class Pagos implements Serializable {

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
    @Column(name = "idAlumno")
    private Integer idAlumno;
    @Column(name = "idGrupo")
    private Integer idGrupo;
    @Column(name = "idColaborador")
    private Integer idColaborador;
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

    public Pagos() {
    }

    public Pagos(Integer idPago) {
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

    public Integer getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
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
        if (!(object instanceof Pagos)) {
            return false;
        }
        Pagos other = (Pagos) object;
        if ((this.idPago == null && other.idPago != null) || (this.idPago != null && !this.idPago.equals(other.idPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "emaaredespacio.persistencia.entidad.Pagos[ idPago=" + idPago + " ]";
    }

}
