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
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 30/04/2018
 * @time 01:12:07 AM
 */
@Entity
@Table(name = "inscripciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inscripciones.findAll", query = "SELECT i FROM Inscripciones i")
    , @NamedQuery(name = "Inscripciones.findByIdInscripcion", query = "SELECT i FROM Inscripciones i WHERE i.idInscripcion = :idInscripcion")
    , @NamedQuery(name = "Inscripciones.findByFechaInscripcion", query = "SELECT i FROM Inscripciones i WHERE i.fechaInscripcion = :fechaInscripcion")
    , @NamedQuery(name = "Inscripciones.findByEstado", query = "SELECT i FROM Inscripciones i WHERE i.estado = :estado")
    , @NamedQuery(name = "Inscripciones.findByPrecioInscripcion", query = "SELECT i FROM Inscripciones i WHERE i.precioInscripcion = :precioInscripcion")
    , @NamedQuery(name = "Inscripciones.findByPagoMensual", query = "SELECT i FROM Inscripciones i WHERE i.pagoMensual = :pagoMensual")})
public class Inscripciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idInscripcion")
    private Integer idInscripcion;
    @Column(name = "fechaInscripcion")
    @Temporal(TemporalType.DATE)
    private Date fechaInscripcion;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "precioInscripcion")
    private Integer precioInscripcion;
    @Column(name = "pagoMensual")
    private Integer pagoMensual;
    @JoinColumn(name = "idAlumno", referencedColumnName = "matricula")
    @ManyToOne
    private Alumnos idAlumno;
    @JoinColumn(name = "idGrupo", referencedColumnName = "idGrupo")
    @ManyToOne
    private Grupos idGrupo;

    public Inscripciones() {
    }

    public Inscripciones(Integer idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public Integer getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(Integer idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Integer getPrecioInscripcion() {
        return precioInscripcion;
    }

    public void setPrecioInscripcion(Integer precioInscripcion) {
        this.precioInscripcion = precioInscripcion;
    }

    public Integer getPagoMensual() {
        return pagoMensual;
    }

    public void setPagoMensual(Integer pagoMensual) {
        this.pagoMensual = pagoMensual;
    }

    public Alumnos getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Alumnos idAlumno) {
        this.idAlumno = idAlumno;
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
        hash += (idInscripcion != null ? idInscripcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inscripciones)) {
            return false;
        }
        Inscripciones other = (Inscripciones) object;
        if ((this.idInscripcion == null && other.idInscripcion != null) || (this.idInscripcion != null && !this.idInscripcion.equals(other.idInscripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "emaaredespacio.persistencia.entidad.Inscripciones[ idInscripcion=" + idInscripcion + " ]";
    }

}
