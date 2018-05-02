/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.misofertas.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David
 */
@Entity
@Table(name = "VALORACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Valoracion.findAll", query = "SELECT v FROM Valoracion v")
    , @NamedQuery(name = "Valoracion.findByCalificacion", query = "SELECT v FROM Valoracion v WHERE v.calificacion = :calificacion")
    , @NamedQuery(name = "Valoracion.findByValoracionId", query = "SELECT v FROM Valoracion v WHERE v.valoracionId = :valoracionId")})
public class Valoracion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "CALIFICACION")
    private long calificacion;
    @Id
    @Basic(optional = false)
    @Column(name = "VALORACION_ID")
    private Long valoracionId;
    @JoinColumn(name = "PRODUCTO_ID", referencedColumnName = "PRODUCTO_ID")
    @ManyToOne(optional = false)
    private Producto productoId;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

    public Valoracion() {
    }

    public Valoracion(Long valoracionId) {
        this.valoracionId = valoracionId;
    }

    public Valoracion(Long valoracionId, long calificacion) {
        this.valoracionId = valoracionId;
        this.calificacion = calificacion;
    }

    public long getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(long calificacion) {
        this.calificacion = calificacion;
    }

    public Long getValoracionId() {
        return valoracionId;
    }

    public void setValoracionId(Long valoracionId) {
        this.valoracionId = valoracionId;
    }

    public Producto getProductoId() {
        return productoId;
    }

    public void setProductoId(Producto productoId) {
        this.productoId = productoId;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (valoracionId != null ? valoracionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Valoracion)) {
            return false;
        }
        Valoracion other = (Valoracion) object;
        if ((this.valoracionId == null && other.valoracionId != null) || (this.valoracionId != null && !this.valoracionId.equals(other.valoracionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MisOfertasDesktopEntities.Valoracion[ valoracionId=" + valoracionId + " ]";
    }
    
}
