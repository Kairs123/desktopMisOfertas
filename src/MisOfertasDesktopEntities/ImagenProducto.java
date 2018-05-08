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
import javax.persistence.Lob;
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
@Table(name = "IMAGEN_PRODUCTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ImagenProducto.findAll", query = "SELECT i FROM ImagenProducto i")
    , @NamedQuery(name = "ImagenProducto.findByImagenId", query = "SELECT i FROM ImagenProducto i WHERE i.imagenId = :imagenId")})
public class ImagenProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Lob
    @Column(name = "IMAGEN")
    private Serializable imagen;
    @Id
    @Basic(optional = false)
    @Column(name = "IMAGEN_ID")
    private Long imagenId;
    @JoinColumn(name = "PRODUCTO_ID", referencedColumnName = "PRODUCTO_ID")
    @ManyToOne(optional = false)
    private Producto productoId;
    @JoinColumn(name = "ENCARGADO", referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)
    private Usuario encargado;

    public ImagenProducto() {
    }

    public ImagenProducto(Long imagenId) {
        this.imagenId = imagenId;
    }

    public ImagenProducto(Long imagenId, Serializable imagen) {
        this.imagenId = imagenId;
        this.imagen = imagen;
    }

    public Serializable getImagen() {
        return imagen;
    }

    public void setImagen(Serializable imagen) {
        this.imagen = imagen;
    }

    public Long getImagenId() {
        return imagenId;
    }

    public void setImagenId(Long imagenId) {
        this.imagenId = imagenId;
    }

    public Producto getProductoId() {
        return productoId;
    }

    public void setProductoId(Producto productoId) {
        this.productoId = productoId;
    }

    public Usuario getEncargado() {
        return encargado;
    }

    public void setEncargado(Usuario encargado) {
        this.encargado = encargado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (imagenId != null ? imagenId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImagenProducto)) {
            return false;
        }
        ImagenProducto other = (ImagenProducto) object;
        if ((this.imagenId == null && other.imagenId != null) || (this.imagenId != null && !this.imagenId.equals(other.imagenId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MisOfertasDesktopEntities.ImagenProducto[ imagenId=" + imagenId + " ]";
    }
    
}
