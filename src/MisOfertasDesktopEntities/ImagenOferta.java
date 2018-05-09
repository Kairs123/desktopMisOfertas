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
import javax.persistence.Lob;
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
@Table(name = "IMAGEN_OFERTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ImagenOferta.findAll", query = "SELECT i FROM ImagenOferta i")
    , @NamedQuery(name = "ImagenOferta.findByIdImagen", query = "SELECT i FROM ImagenOferta i WHERE i.idImagen = :idImagen")})
public class ImagenOferta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Lob
    @Column(name = "IMAGEN")
    private Serializable imagen;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_IMAGEN")
    private Long idImagen;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "imagenId")
    private List<Oferta> ofertaList;

    public ImagenOferta() {
    }

    public ImagenOferta(Long idImagen) {
        this.idImagen = idImagen;
    }

    public ImagenOferta(Long idImagen, Serializable imagen) {
        this.idImagen = idImagen;
        this.imagen = imagen;
    }

    public Serializable getImagen() {
        return imagen;
    }

    public void setImagen(Serializable imagen) {
        this.imagen = imagen;
    }

    public Long getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Long idImagen) {
        this.idImagen = idImagen;
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
        hash += (idImagen != null ? idImagen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImagenOferta)) {
            return false;
        }
        ImagenOferta other = (ImagenOferta) object;
        if ((this.idImagen == null && other.idImagen != null) || (this.idImagen != null && !this.idImagen.equals(other.idImagen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MisOfertasDesktopEntities.ImagenOferta[ idImagen=" + idImagen + " ]";
    }
    
}
