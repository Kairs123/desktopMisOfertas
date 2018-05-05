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
@Table(name = "USUARIO_PUNTOS_ACUMULADOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioPuntosAcumulados.findAll", query = "SELECT u FROM UsuarioPuntosAcumulados u")
    , @NamedQuery(name = "UsuarioPuntosAcumulados.findByIdPuntaje", query = "SELECT u FROM UsuarioPuntosAcumulados u WHERE u.idPuntaje = :idPuntaje")})
public class UsuarioPuntosAcumulados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PUNTAJE")
    private Long idPuntaje;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "ID_USUARIO")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

    public UsuarioPuntosAcumulados() {
    }

    public UsuarioPuntosAcumulados(Long idPuntaje) {
        this.idPuntaje = idPuntaje;
    }

    public Long getIdPuntaje() {
        return idPuntaje;
    }

    public void setIdPuntaje(Long idPuntaje) {
        this.idPuntaje = idPuntaje;
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
        hash += (idPuntaje != null ? idPuntaje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioPuntosAcumulados)) {
            return false;
        }
        UsuarioPuntosAcumulados other = (UsuarioPuntosAcumulados) object;
        if ((this.idPuntaje == null && other.idPuntaje != null) || (this.idPuntaje != null && !this.idPuntaje.equals(other.idPuntaje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MisOfertasDesktopEntities.UsuarioPuntosAcumulados[ idPuntaje=" + idPuntaje + " ]";
    }
    
}
