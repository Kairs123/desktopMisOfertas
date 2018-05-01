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
@Table(name = "OFERTA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Oferta.findAll", query = "SELECT o FROM Oferta o")
    , @NamedQuery(name = "Oferta.findByOfertaId", query = "SELECT o FROM Oferta o WHERE o.ofertaId = :ofertaId")
    , @NamedQuery(name = "Oferta.findByPctDescuento", query = "SELECT o FROM Oferta o WHERE o.pctDescuento = :pctDescuento")
    , @NamedQuery(name = "Oferta.findByStock", query = "SELECT o FROM Oferta o WHERE o.stock = :stock")
    , @NamedQuery(name = "Oferta.findByPrecio", query = "SELECT o FROM Oferta o WHERE o.precio = :precio")
    , @NamedQuery(name = "Oferta.findByDosPorUno", query = "SELECT o FROM Oferta o WHERE o.dosPorUno = :dosPorUno")
    , @NamedQuery(name = "Oferta.findByIsActive", query = "SELECT o FROM Oferta o WHERE o.isActive = :isActive")})
public class Oferta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "OFERTA_ID")
    private Long ofertaId;
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
    @JoinColumn(name = "PRODUCTO", referencedColumnName = "PRODUCTO_ID")
    @ManyToOne(optional = false)
    private Producto producto;
    @JoinColumn(name = "TIENDA_ID", referencedColumnName = "ID_TIENDA")
    @ManyToOne(optional = false)
    private Tienda tiendaId;

    public Oferta() {
    }

    public Oferta(Long ofertaId) {
        this.ofertaId = ofertaId;
    }

    public Oferta(Long ofertaId, long pctDescuento, long stock, long precio, String isActive) {
        this.ofertaId = ofertaId;
        this.pctDescuento = pctDescuento;
        this.stock = stock;
        this.precio = precio;
        this.isActive = isActive;
    }

    public Long getOfertaId() {
        return ofertaId;
    }

    public void setOfertaId(Long ofertaId) {
        this.ofertaId = ofertaId;
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

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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
        hash += (ofertaId != null ? ofertaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Oferta)) {
            return false;
        }
        Oferta other = (Oferta) object;
        if ((this.ofertaId == null && other.ofertaId != null) || (this.ofertaId != null && !this.ofertaId.equals(other.ofertaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "misofertasdesktop.Oferta[ ofertaId=" + ofertaId + " ]";
    }
    
}
