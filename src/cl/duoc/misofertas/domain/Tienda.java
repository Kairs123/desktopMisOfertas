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
@Table(name = "TIENDA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tienda.findAll", query = "SELECT t FROM Tienda t")
    , @NamedQuery(name = "Tienda.findByIdTienda", query = "SELECT t FROM Tienda t WHERE t.idTienda = :idTienda")
    , @NamedQuery(name = "Tienda.findByNombre", query = "SELECT t FROM Tienda t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "Tienda.findByDireccion", query = "SELECT t FROM Tienda t WHERE t.direccion = :direccion")
    , @NamedQuery(name = "Tienda.findByIsActive", query = "SELECT t FROM Tienda t WHERE t.isActive = :isActive")})
public class Tienda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TIENDA")
    private Long idTienda;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "IS_ACTIVE")
    private String isActive;
    @JoinColumn(name = "COMUNA", referencedColumnName = "ID_COMUNA")
    @ManyToOne(optional = false)
    private Comuna comuna;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tiendaId")
    private List<UsuarioTienda> usuarioTiendaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tiendaId")
    private List<Rubro> rubroList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tiendaId")
    private List<Oferta> ofertaList;

    public Tienda() {
    }

    public Tienda(Long idTienda) {
        this.idTienda = idTienda;
    }

    public Tienda(Long idTienda, String nombre, String direccion, String isActive) {
        this.idTienda = idTienda;
        this.nombre = nombre;
        this.direccion = direccion;
        this.isActive = isActive;
    }

    public Long getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(Long idTienda) {
        this.idTienda = idTienda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Comuna getComuna() {
        return comuna;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }

    @XmlTransient
    public List<UsuarioTienda> getUsuarioTiendaList() {
        return usuarioTiendaList;
    }

    public void setUsuarioTiendaList(List<UsuarioTienda> usuarioTiendaList) {
        this.usuarioTiendaList = usuarioTiendaList;
    }

    @XmlTransient
    public List<Rubro> getRubroList() {
        return rubroList;
    }

    public void setRubroList(List<Rubro> rubroList) {
        this.rubroList = rubroList;
    }

    @XmlTransient
    public List<Oferta> getOfertaList() {
        return ofertaList;
    }

    public void setOfertaList(List<Oferta> ofertaList) {
        this.ofertaList = ofertaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTienda != null ? idTienda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tienda)) {
            return false;
        }
        Tienda other = (Tienda) object;
        if ((this.idTienda == null && other.idTienda != null) || (this.idTienda != null && !this.idTienda.equals(other.idTienda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MisOfertasDesktopEntities.Tienda[ idTienda=" + idTienda + " ]";
    }
    
}
