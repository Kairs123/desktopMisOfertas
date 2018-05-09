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
@Table(name = "WS_DETALLE_VENTAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WsDetalleVentas.findAll", query = "SELECT w FROM WsDetalleVentas w")
    , @NamedQuery(name = "WsDetalleVentas.findByDetalleId", query = "SELECT w FROM WsDetalleVentas w WHERE w.detalleId = :detalleId")
    , @NamedQuery(name = "WsDetalleVentas.findByOfertaId", query = "SELECT w FROM WsDetalleVentas w WHERE w.ofertaId = :ofertaId")})
public class WsDetalleVentas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "DETALLE_ID")
    private Long detalleId;
    @Basic(optional = false)
    @Column(name = "OFERTA_ID")
    private long ofertaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "detalleVenta")
    private List<WsVentasRealizadas> wsVentasRealizadasList;

    public WsDetalleVentas() {
    }

    public WsDetalleVentas(Long detalleId) {
        this.detalleId = detalleId;
    }

    public WsDetalleVentas(Long detalleId, long ofertaId) {
        this.detalleId = detalleId;
        this.ofertaId = ofertaId;
    }

    public Long getDetalleId() {
        return detalleId;
    }

    public void setDetalleId(Long detalleId) {
        this.detalleId = detalleId;
    }

    public long getOfertaId() {
        return ofertaId;
    }

    public void setOfertaId(long ofertaId) {
        this.ofertaId = ofertaId;
    }

    @XmlTransient
    public List<WsVentasRealizadas> getWsVentasRealizadasList() {
        return wsVentasRealizadasList;
    }

    public void setWsVentasRealizadasList(List<WsVentasRealizadas> wsVentasRealizadasList) {
        this.wsVentasRealizadasList = wsVentasRealizadasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detalleId != null ? detalleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WsDetalleVentas)) {
            return false;
        }
        WsDetalleVentas other = (WsDetalleVentas) object;
        if ((this.detalleId == null && other.detalleId != null) || (this.detalleId != null && !this.detalleId.equals(other.detalleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MisOfertasDesktopEntities.WsDetalleVentas[ detalleId=" + detalleId + " ]";
    }
    
}
