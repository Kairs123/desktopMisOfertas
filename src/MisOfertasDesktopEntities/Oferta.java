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
@Table(name = "OFERTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Oferta.findAll", query = "SELECT o FROM Oferta o")
    , @NamedQuery(name = "Oferta.findByIdOferta", query = "SELECT o FROM Oferta o WHERE o.idOferta = :idOferta")
    , @NamedQuery(name = "Oferta.findByPctDescuento", query = "SELECT o FROM Oferta o WHERE o.pctDescuento = :pctDescuento")
    , @NamedQuery(name = "Oferta.findByStock", query = "SELECT o FROM Oferta o WHERE o.stock = :stock")
    , @NamedQuery(name = "Oferta.findByPrecio", query = "SELECT o FROM Oferta o WHERE o.precio = :precio")
    , @NamedQuery(name = "Oferta.findByDosPorUno", query = "SELECT o FROM Oferta o WHERE o.dosPorUno = :dosPorUno")
    , @NamedQuery(name = "Oferta.findByIsActive", query = "SELECT o FROM Oferta o WHERE o.isActive = :isActive")})
public class Oferta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_OFERTA")
    private Long idOferta;
    @Basic(optional = false)
    @Column(name = "PCT_DESCUENTO")
    private long pctDescuento;
    @Basic(optional = false)
    @Column(name = "STOCK")
    private long stock;
    @Basic(optional = false)
    @Column(name = "PRECIO")
    private long precio;
    @Column(name = "DOS_POR_UNO")
    private String dosPorUno;
    @Basic(optional = false)
    @Column(name = "IS_ACTIVE")
    private String isActive;
    @JoinColumn(name = "IMAGEN_ID", referencedColumnName = "ID_IMAGEN")
    @ManyToOne(optional = false)
    private ImagenOferta imagenId;
    @JoinColumn(name = "PRODUCTO_ID", referencedColumnName = "ID_PRODUCTO")
    @ManyToOne(optional = false)
    private Producto productoId;
    @JoinColumn(name = "TIENDA_ID", referencedColumnName = "ID_TIENDA")
    @ManyToOne(optional = false)
    private Tienda tiendaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ofertaId")
    private List<OfertaConsultadaUsuario> ofertaConsultadaUsuarioList;

    public Oferta() {
    }

    public Oferta(Long idOferta) {
        this.idOferta = idOferta;
    }

    public Oferta(Long idOferta, long pctDescuento, long stock, long precio, String isActive) {
        this.idOferta = idOferta;
        this.pctDescuento = pctDescuento;
        this.stock = stock;
        this.precio = precio;
        this.isActive = isActive;
    }

    public Long getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(Long idOferta) {
        this.idOferta = idOferta;
    }

    public long getPctDescuento() {
        return pctDescuento;
    }

    public void setPctDescuento(long pctDescuento) {
        this.pctDescuento = pctDescuento;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public long getPrecio() {
        return precio;
    }

    public void setPrecio(long precio) {
        this.precio = precio;
    }

    public String getDosPorUno() {
        return dosPorUno;
    }

    public void setDosPorUno(String dosPorUno) {
        this.dosPorUno = dosPorUno;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public ImagenOferta getImagenId() {
        return imagenId;
    }

    public void setImagenId(ImagenOferta imagenId) {
        this.imagenId = imagenId;
    }

    public Producto getProductoId() {
        return productoId;
    }

    public void setProductoId(Producto productoId) {
        this.productoId = productoId;
    }

    public Tienda getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(Tienda tiendaId) {
        this.tiendaId = tiendaId;
    }

    @XmlTransient
    public List<OfertaConsultadaUsuario> getOfertaConsultadaUsuarioList() {
        return ofertaConsultadaUsuarioList;
    }

    public void setOfertaConsultadaUsuarioList(List<OfertaConsultadaUsuario> ofertaConsultadaUsuarioList) {
        this.ofertaConsultadaUsuarioList = ofertaConsultadaUsuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOferta != null ? idOferta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Oferta)) {
            return false;
        }
        Oferta other = (Oferta) object;
        if ((this.idOferta == null && other.idOferta != null) || (this.idOferta != null && !this.idOferta.equals(other.idOferta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MisOfertasDesktopEntities.Oferta[ idOferta=" + idOferta + " ]";
    }
    
}
