/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misofertasdesktop;

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
@Table(name = "DESCUENTO_EMITIDO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DescuentoEmitido.findAll", query = "SELECT d FROM DescuentoEmitido d")
    , @NamedQuery(name = "DescuentoEmitido.findByDescuentoId", query = "SELECT d FROM DescuentoEmitido d WHERE d.descuentoId = :descuentoId")})
public class DescuentoEmitido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "DESCUENTO_ID")
    private Long descuentoId;
    @JoinColumn(name = "PRODUCTO_ID", referencedColumnName = "PRODUCTO_ID")
    @ManyToOne(optional = false)
    private Producto productoId;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

    public DescuentoEmitido() {
    }

    public DescuentoEmitido(Long descuentoId) {
        this.descuentoId = descuentoId;
    }

    public Long getDescuentoId() {
        return descuentoId;
    }

    public void setDescuentoId(Long descuentoId) {
        this.descuentoId = descuentoId;
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
        hash += (descuentoId != null ? descuentoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DescuentoEmitido)) {
            return false;
        }
        DescuentoEmitido other = (DescuentoEmitido) object;
        if ((this.descuentoId == null && other.descuentoId != null) || (this.descuentoId != null && !this.descuentoId.equals(other.descuentoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "misofertasdesktop.DescuentoEmitido[ descuentoId=" + descuentoId + " ]";
    }
    
}
