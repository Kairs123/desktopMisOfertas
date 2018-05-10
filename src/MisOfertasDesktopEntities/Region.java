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
@Table(name = "REGION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Region.findAll", query = "SELECT r FROM Region r")
    , @NamedQuery(name = "Region.findByIdRegion", query = "SELECT r FROM Region r WHERE r.idRegion = :idRegion")
    , @NamedQuery(name = "Region.findByNombreRegion", query = "SELECT r FROM Region r WHERE r.nombreRegion = :nombreRegion")
    , @NamedQuery(name = "Region.findByIsActive", query = "SELECT r FROM Region r WHERE r.isActive = :isActive")})
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_REGION")
    private Long idRegion;
    @Basic(optional = false)
    @Column(name = "NOMBRE_REGION")
    private String nombreRegion;
    @Basic(optional = false)
    @Column(name = "IS_ACTIVE")
    private String isActive;
    @JoinColumn(name = "PAIS_ID", referencedColumnName = "ID_PAIS")
    @ManyToOne(optional = false)
    private Pais paisId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "regionId")
    private List<Comuna> comunaList;

    public Region() {
    }

    public Region(Long idRegion) {
        this.idRegion = idRegion;
    }

    public Region(Long idRegion, String nombreRegion, String isActive) {
        this.idRegion = idRegion;
        this.nombreRegion = nombreRegion;
        this.isActive = isActive;
    }

    public Long getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(Long idRegion) {
        this.idRegion = idRegion;
    }

    public String getNombreRegion() {
        return nombreRegion;
    }

    public void setNombreRegion(String nombreRegion) {
        this.nombreRegion = nombreRegion;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Pais getPaisId() {
        return paisId;
    }

    public void setPaisId(Pais paisId) {
        this.paisId = paisId;
    }

    @XmlTransient
    public List<Comuna> getComunaList() {
        return comunaList;
    }

    public void setComunaList(List<Comuna> comunaList) {
        this.comunaList = comunaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegion != null ? idRegion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Region)) {
            return false;
        }
        Region other = (Region) object;
        if ((this.idRegion == null && other.idRegion != null) || (this.idRegion != null && !this.idRegion.equals(other.idRegion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MisOfertasDesktopEntities.Region[ idRegion=" + idRegion + " ]";
    }
    
}
