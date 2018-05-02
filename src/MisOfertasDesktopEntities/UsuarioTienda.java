/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MisOfertasDesktopEntities;

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
@Table(name = "USUARIO_TIENDA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioTienda.findAll", query = "SELECT u FROM UsuarioTienda u")
    , @NamedQuery(name = "UsuarioTienda.findByUsuarioTiendaId", query = "SELECT u FROM UsuarioTienda u WHERE u.usuarioTiendaId = :usuarioTiendaId")})
public class UsuarioTienda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "USUARIO_TIENDA_ID")
    private Long usuarioTiendaId;
    @JoinColumn(name = "TIENDA_ID", referencedColumnName = "ID_TIENDA")
    @ManyToOne(optional = false)
    private Tienda tiendaId;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

    public UsuarioTienda() {
    }

    public UsuarioTienda(Long usuarioTiendaId) {
        this.usuarioTiendaId = usuarioTiendaId;
    }

    public Long getUsuarioTiendaId() {
        return usuarioTiendaId;
    }

    public void setUsuarioTiendaId(Long usuarioTiendaId) {
        this.usuarioTiendaId = usuarioTiendaId;
    }

    public Tienda getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(Tienda tiendaId) {
        this.tiendaId = tiendaId;
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
        hash += (usuarioTiendaId != null ? usuarioTiendaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioTienda)) {
            return false;
        }
        UsuarioTienda other = (UsuarioTienda) object;
        if ((this.usuarioTiendaId == null && other.usuarioTiendaId != null) || (this.usuarioTiendaId != null && !this.usuarioTiendaId.equals(other.usuarioTiendaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MisOfertasDesktopEntities.UsuarioTienda[ usuarioTiendaId=" + usuarioTiendaId + " ]";
    }
    
}
