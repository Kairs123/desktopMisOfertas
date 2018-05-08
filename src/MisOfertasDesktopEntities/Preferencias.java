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
@Table(name = "PREFERENCIAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preferencias.findAll", query = "SELECT p FROM Preferencias p")
    , @NamedQuery(name = "Preferencias.findByPreferenciaId", query = "SELECT p FROM Preferencias p WHERE p.preferenciaId = :preferenciaId")})
public class Preferencias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PREFERENCIA_ID")
    private Long preferenciaId;
    @JoinColumn(name = "RUBRO_ID", referencedColumnName = "ID_RUBRO")
    @ManyToOne(optional = false)
    private Rubro rubroId;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

    public Preferencias() {
    }

    public Preferencias(Long preferenciaId) {
        this.preferenciaId = preferenciaId;
    }

    public Long getPreferenciaId() {
        return preferenciaId;
    }

    public void setPreferenciaId(Long preferenciaId) {
        this.preferenciaId = preferenciaId;
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
        hash += (preferenciaId != null ? preferenciaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preferencias)) {
            return false;
        }
        Preferencias other = (Preferencias) object;
        if ((this.preferenciaId == null && other.preferenciaId != null) || (this.preferenciaId != null && !this.preferenciaId.equals(other.preferenciaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MisOfertasDesktopEntities.Preferencias[ preferenciaId=" + preferenciaId + " ]";
    }
    
}
