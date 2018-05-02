/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.misofertas.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author David
 */
@Entity
@Table(name = "TIPO_USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoUsuario.findAll", query = "SELECT t FROM TipoUsuario t")
    , @NamedQuery(name = "TipoUsuario.findByTipoUserId", query = "SELECT t FROM TipoUsuario t WHERE t.tipoUserId = :tipoUserId")
    , @NamedQuery(name = "TipoUsuario.findByDescripcion", query = "SELECT t FROM TipoUsuario t WHERE t.descripcion = :descripcion")})
public class TipoUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TIPO_USER_ID")
    private Long tipoUserId;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoUser")
    private List<Usuario> usuarioList;
    @OneToMany(mappedBy = "tipoUsuarioId")
    private List<Menu> menuList;

    public TipoUsuario() {
    }

    public TipoUsuario(Long tipoUserId) {
        this.tipoUserId = tipoUserId;
    }

    public Long getTipoUserId() {
        return tipoUserId;
    }

    public void setTipoUserId(Long tipoUserId) {
        this.tipoUserId = tipoUserId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @XmlTransient
    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoUserId != null ? tipoUserId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoUsuario)) {
            return false;
        }
        TipoUsuario other = (TipoUsuario) object;
        if ((this.tipoUserId == null && other.tipoUserId != null) || (this.tipoUserId != null && !this.tipoUserId.equals(other.tipoUserId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MisOfertasDesktopEntities.TipoUsuario[ tipoUserId=" + tipoUserId + " ]";
    }
    
}
