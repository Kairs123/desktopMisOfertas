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
@Table(name = "PREF_TIENDA_USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrefTiendaUsuario.findAll", query = "SELECT p FROM PrefTiendaUsuario p")
    , @NamedQuery(name = "PrefTiendaUsuario.findByIdPrefTienda", query = "SELECT p FROM PrefTiendaUsuario p WHERE p.idPrefTienda = :idPrefTienda")})
public class PrefTiendaUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PREF_TIENDA")
    private Long idPrefTienda;
    @JoinColumn(name = "TIENDA_ID", referencedColumnName = "ID_TIENDA")
    @ManyToOne
    private Tienda tiendaId;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID_USUARIO")
    @ManyToOne
    private Usuario usuarioId;

    public PrefTiendaUsuario() {
    }

    public PrefTiendaUsuario(Long idPrefTienda) {
        this.idPrefTienda = idPrefTienda;
    }

    public Long getIdPrefTienda() {
        return idPrefTienda;
    }

    public void setIdPrefTienda(Long idPrefTienda) {
        this.idPrefTienda = idPrefTienda;
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
        hash += (idPrefTienda != null ? idPrefTienda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrefTiendaUsuario)) {
            return false;
        }
        PrefTiendaUsuario other = (PrefTiendaUsuario) object;
        if ((this.idPrefTienda == null && other.idPrefTienda != null) || (this.idPrefTienda != null && !this.idPrefTienda.equals(other.idPrefTienda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MisOfertasDesktopEntities.PrefTiendaUsuario[ idPrefTienda=" + idPrefTienda + " ]";
    }
    
}
