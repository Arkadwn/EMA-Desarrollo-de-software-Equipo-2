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
 * @date 6/04/2018
 * @time 10:51:52 PM
 */
@Entity
@Table(name = "grupos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupos.findAll", query = "SELECT g FROM Grupos g")
    , @NamedQuery(name = "Grupos.findByIdGrupo", query = "SELECT g FROM Grupos g WHERE g.idGrupo = :idGrupo")
    , @NamedQuery(name = "Grupos.findByTipoDeBaile", query = "SELECT g FROM Grupos g WHERE g.tipoDeBaile = :tipoDeBaile")
    , @NamedQuery(name = "Grupos.findByCupo", query = "SELECT g FROM Grupos g WHERE g.cupo = :cupo")
    , @NamedQuery(name = "Grupos.findByEstado", query = "SELECT g FROM Grupos g WHERE g.estado = :estado")})
public class Grupos implements Serializable {

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
    @Column(name = "estado")
    private String estado;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

}
