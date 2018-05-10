/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MisOfertasDesktopEntities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "RUBRO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rubro.findAll", query = "SELECT r FROM Rubro r")
    , @NamedQuery(name = "Rubro.findByIdRubro", query = "SELECT r FROM Rubro r WHERE r.idRubro = :idRubro")
    , @NamedQuery(name = "Rubro.findByNombreRubro", query = "SELECT r FROM Rubro r WHERE r.nombreRubro = :nombreRubro")
    , @NamedQuery(name = "Rubro.findByIsActive", query = "SELECT r FROM Rubro r WHERE r.isActive = :isActive")})
public class Rubro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_RUBRO")
    private Long idRubro;
    @Basic(optional = false)
    @Column(name = "NOMBRE_RUBRO")
    private String nombreRubro;
    @Basic(optional = false)
    @Column(name = "IS_ACTIVE")
    private String isActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rubro")
    private List<Producto> productoList;
    @OneToMany(mappedBy = "rubroId")
    private List<PrefRubroUsuario> prefRubroUsuarioList;
    @JoinColumn(name = "TIENDA_ID", referencedColumnName = "ID_TIENDA")
    @ManyToOne(optional = false)
    private Tienda tiendaId;

    public Rubro() {
    }

    public Rubro(Long idRubro) {
        this.idRubro = idRubro;
    }

    public Rubro(Long idRubro, String nombreRubro, String isActive) {
        this.idRubro = idRubro;
        this.nombreRubro = nombreRubro;
        this.isActive = isActive;
    }

    public Long getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(Long idRubro) {
        this.idRubro = idRubro;
    }

    public String getNombreRubro() {
        return nombreRubro;
    }

    public void setNombreRubro(String nombreRubro) {
        this.nombreRubro = nombreRubro;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @XmlTransient
    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }

    @XmlTransient
    public List<PrefRubroUsuario> getPrefRubroUsuarioList() {
        return prefRubroUsuarioList;
    }

    public void setPrefRubroUsuarioList(List<PrefRubroUsuario> prefRubroUsuarioList) {
        this.prefRubroUsuarioList = prefRubroUsuarioList;
    }

    public Tienda getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(Tienda tiendaId) {
        this.tiendaId = tiendaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRubro != null ? idRubro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rubro)) {
            return false;
        }
        Rubro other = (Rubro) object;
        if ((this.idRubro == null && other.idRubro != null) || (this.idRubro != null && !this.idRubro.equals(other.idRubro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MisOfertasDesktopEntities.Rubro[ idRubro=" + idRubro + " ]";
    }
    
}
