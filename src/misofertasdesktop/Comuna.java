/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misofertasdesktop;

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
@Table(name = "COMUNA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comuna.findAll", query = "SELECT c FROM Comuna c")
    , @NamedQuery(name = "Comuna.findByIdComuna", query = "SELECT c FROM Comuna c WHERE c.idComuna = :idComuna")
    , @NamedQuery(name = "Comuna.findByNombreComuna", query = "SELECT c FROM Comuna c WHERE c.nombreComuna = :nombreComuna")
    , @NamedQuery(name = "Comuna.findByIsActive", query = "SELECT c FROM Comuna c WHERE c.isActive = :isActive")})
public class Comuna implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_COMUNA")
    private Long idComuna;
    @Basic(optional = false)
    @Column(name = "NOMBRE_COMUNA")
    private String nombreComuna;
    @Basic(optional = false)
    @Column(name = "IS_ACTIVE")
    private String isActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comuna")
    private List<Tienda> tiendaList;
    @JoinColumn(name = "REGION_ID", referencedColumnName = "ID_REGION")
    @ManyToOne(optional = false)
    private Region regionId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comunaId")
    private List<Persona> personaList;

    public Comuna() {
    }

    public Comuna(Long idComuna) {
        this.idComuna = idComuna;
    }

    public Comuna(Long idComuna, String nombreComuna, String isActive) {
        this.idComuna = idComuna;
        this.nombreComuna = nombreComuna;
        this.isActive = isActive;
    }

    public Long getIdComuna() {
        return idComuna;
    }

    public void setIdComuna(Long idComuna) {
        this.idComuna = idComuna;
    }

    public String getNombreComuna() {
        return nombreComuna;
    }

    public void setNombreComuna(String nombreComuna) {
        this.nombreComuna = nombreComuna;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @XmlTransient
    public List<Tienda> getTiendaList() {
        return tiendaList;
    }

    public void setTiendaList(List<Tienda> tiendaList) {
        this.tiendaList = tiendaList;
    }

    public Region getRegionId() {
        return regionId;
    }

    public void setRegionId(Region regionId) {
        this.regionId = regionId;
    }

    @XmlTransient
    public List<Persona> getPersonaList() {
        return personaList;
    }

    public void setPersonaList(List<Persona> personaList) {
        this.personaList = personaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComuna != null ? idComuna.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comuna)) {
            return false;
        }
        Comuna other = (Comuna) object;
        if ((this.idComuna == null && other.idComuna != null) || (this.idComuna != null && !this.idComuna.equals(other.idComuna))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "misofertasdesktop.Comuna[ idComuna=" + idComuna + " ]";
    }
    
}
