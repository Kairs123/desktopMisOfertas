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
@Table(name = "PRODUCTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")
    , @NamedQuery(name = "Producto.findByProductoId", query = "SELECT p FROM Producto p WHERE p.productoId = :productoId")
    , @NamedQuery(name = "Producto.findByNombreProducto", query = "SELECT p FROM Producto p WHERE p.nombreProducto = :nombreProducto")
    , @NamedQuery(name = "Producto.findByEsPerecible", query = "SELECT p FROM Producto p WHERE p.esPerecible = :esPerecible")
    , @NamedQuery(name = "Producto.findByFechaVencimiento", query = "SELECT p FROM Producto p WHERE p.fechaVencimiento = :fechaVencimiento")
    , @NamedQuery(name = "Producto.findByIsActive", query = "SELECT p FROM Producto p WHERE p.isActive = :isActive")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PRODUCTO_ID")
    private Long productoId;
    @Basic(optional = false)
    @Column(name = "NOMBRE_PRODUCTO")
    private String nombreProducto;
    @Basic(optional = false)
    @Column(name = "ES_PERECIBLE")
    private long esPerecible;
    @Column(name = "FECHA_VENCIMIENTO")
    private String fechaVencimiento;
    @Basic(optional = false)
    @Column(name = "IS_ACTIVE")
    private String isActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoId")
    private List<ImagenProducto> imagenProductoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoId")
    private List<Valoracion> valoracionList;
    @JoinColumn(name = "RUBRO", referencedColumnName = "ID_RUBRO")
    @ManyToOne(optional = false)
    private Rubro rubro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private List<Oferta> ofertaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoId")
    private List<DescuentoEmitido> descuentoEmitidoList;

    public Producto() {
    }

    public Producto(Long productoId) {
        this.productoId = productoId;
    }

    public Producto(Long productoId, String nombreProducto, long esPerecible, String isActive) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.esPerecible = esPerecible;
        this.isActive = isActive;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public long getEsPerecible() {
        return esPerecible;
    }

    public void setEsPerecible(long esPerecible) {
        this.esPerecible = esPerecible;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @XmlTransient
    public List<ImagenProducto> getImagenProductoList() {
        return imagenProductoList;
    }

    public void setImagenProductoList(List<ImagenProducto> imagenProductoList) {
        this.imagenProductoList = imagenProductoList;
    }

    @XmlTransient
    public List<Valoracion> getValoracionList() {
        return valoracionList;
    }

    public void setValoracionList(List<Valoracion> valoracionList) {
        this.valoracionList = valoracionList;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    @XmlTransient
    public List<Oferta> getOfertaList() {
        return ofertaList;
    }

    public void setOfertaList(List<Oferta> ofertaList) {
        this.ofertaList = ofertaList;
    }

    @XmlTransient
    public List<DescuentoEmitido> getDescuentoEmitidoList() {
        return descuentoEmitidoList;
    }

    public void setDescuentoEmitidoList(List<DescuentoEmitido> descuentoEmitidoList) {
        this.descuentoEmitidoList = descuentoEmitidoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoId != null ? productoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.productoId == null && other.productoId != null) || (this.productoId != null && !this.productoId.equals(other.productoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MisOfertasDesktopEntities.Producto[ productoId=" + productoId + " ]";
    }
    
}
