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
@Table(name = "MENU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m")
    , @NamedQuery(name = "Menu.findByMenuId", query = "SELECT m FROM Menu m WHERE m.menuId = :menuId")
    , @NamedQuery(name = "Menu.findByDescripcion", query = "SELECT m FROM Menu m WHERE m.descripcion = :descripcion")
    , @NamedQuery(name = "Menu.findByParentId", query = "SELECT m FROM Menu m WHERE m.parentId = :parentId")
    , @NamedQuery(name = "Menu.findByTitulo", query = "SELECT m FROM Menu m WHERE m.titulo = :titulo")
    , @NamedQuery(name = "Menu.findByRuta", query = "SELECT m FROM Menu m WHERE m.ruta = :ruta")})
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "MENU_ID")
    private Long menuId;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "PARENT_ID")
    private long parentId;
    @Basic(optional = false)
    @Column(name = "TITULO")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "RUTA")
    private String ruta;
    @JoinColumn(name = "TIPO_USUARIO_ID", referencedColumnName = "TIPO_USER_ID")
    @ManyToOne
    private TipoUsuario tipoUsuarioId;

    public Menu() {
    }

    public Menu(Long menuId) {
        this.menuId = menuId;
    }

    public Menu(Long menuId, String descripcion, long parentId, String titulo, String ruta) {
        this.menuId = menuId;
        this.descripcion = descripcion;
        this.parentId = parentId;
        this.titulo = titulo;
        this.ruta = ruta;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public TipoUsuario getTipoUsuarioId() {
        return tipoUsuarioId;
    }

    public void setTipoUsuarioId(TipoUsuario tipoUsuarioId) {
        this.tipoUsuarioId = tipoUsuarioId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (menuId != null ? menuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.menuId == null && other.menuId != null) || (this.menuId != null && !this.menuId.equals(other.menuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "misofertasdesktop.Menu[ menuId=" + menuId + " ]";
    }
    
}
