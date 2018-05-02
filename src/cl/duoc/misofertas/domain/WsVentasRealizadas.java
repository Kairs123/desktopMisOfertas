/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.misofertas.domain;

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
@Table(name = "WS_VENTAS_REALIZADAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WsVentasRealizadas.findAll", query = "SELECT w FROM WsVentasRealizadas w")
    , @NamedQuery(name = "WsVentasRealizadas.findByVentasWsId", query = "SELECT w FROM WsVentasRealizadas w WHERE w.ventasWsId = :ventasWsId")
    , @NamedQuery(name = "WsVentasRealizadas.findByUsuario", query = "SELECT w FROM WsVentasRealizadas w WHERE w.usuario = :usuario")
    , @NamedQuery(name = "WsVentasRealizadas.findByTotal", query = "SELECT w FROM WsVentasRealizadas w WHERE w.total = :total")})
public class WsVentasRealizadas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "VENTAS_WS_ID")
    private Long ventasWsId;
    @Basic(optional = false)
    @Column(name = "USUARIO")
    private long usuario;
    @Basic(optional = false)
    @Column(name = "TOTAL")
    private long total;
    @JoinColumn(name = "DETALLE_VENTA", referencedColumnName = "DETALLE_ID")
    @ManyToOne(optional = false)
    private WsDetalleVentas detalleVenta;

    public WsVentasRealizadas() {
    }

    public WsVentasRealizadas(Long ventasWsId) {
        this.ventasWsId = ventasWsId;
    }

    public WsVentasRealizadas(Long ventasWsId, long usuario, long total) {
        this.ventasWsId = ventasWsId;
        this.usuario = usuario;
        this.total = total;
    }

    public Long getVentasWsId() {
        return ventasWsId;
    }

    public void setVentasWsId(Long ventasWsId) {
        this.ventasWsId = ventasWsId;
    }

    public long getUsuario() {
        return usuario;
    }

    public void setUsuario(long usuario) {
        this.usuario = usuario;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public WsDetalleVentas getDetalleVenta() {
        return detalleVenta;
    }

    public void setDetalleVenta(WsDetalleVentas detalleVenta) {
        this.detalleVenta = detalleVenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ventasWsId != null ? ventasWsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WsVentasRealizadas)) {
            return false;
        }
        WsVentasRealizadas other = (WsVentasRealizadas) object;
        if ((this.ventasWsId == null && other.ventasWsId != null) || (this.ventasWsId != null && !this.ventasWsId.equals(other.ventasWsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MisOfertasDesktopEntities.WsVentasRealizadas[ ventasWsId=" + ventasWsId + " ]";
    }
    
}
