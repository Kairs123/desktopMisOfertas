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
@Table(name = "PREF_RUBRO_USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrefRubroUsuario.findAll", query = "SELECT p FROM PrefRubroUsuario p")
    , @NamedQuery(name = "PrefRubroUsuario.findByIdPrefRubro", query = "SELECT p FROM PrefRubroUsuario p WHERE p.idPrefRubro = :idPrefRubro")})
public class PrefRubroUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PREF_RUBRO")
    private Long idPrefRubro;
    @JoinColumn(name = "RUBRO_ID", referencedColumnName = "ID_RUBRO")
    @ManyToOne(optional = false)
    private Rubro rubroId;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID_USUARIO")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

    public PrefRubroUsuario() {
    }

    public PrefRubroUsuario(Long idPrefRubro) {
        this.idPrefRubro = idPrefRubro;
    }

    public Long getIdPrefRubro() {
        return idPrefRubro;
    }

    public void setIdPrefRubro(Long idPrefRubro) {
        this.idPrefRubro = idPrefRubro;
    }

    public Rubro getRubroId() {
        return rubroId;
    }

    public void setRubroId(Rubro rubroId) {
        this.rubroId = rubroId;
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
        hash += (idPrefRubro != null ? idPrefRubro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrefRubroUsuario)) {
            return false;
        }
        PrefRubroUsuario other = (PrefRubroUsuario) object;
        if ((this.idPrefRubro == null && other.idPrefRubro != null) || (this.idPrefRubro != null && !this.idPrefRubro.equals(other.idPrefRubro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MisOfertasDesktopEntities.PrefRubroUsuario[ idPrefRubro=" + idPrefRubro + " ]";
    }
    
}
