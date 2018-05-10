/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MisOfertasDesktopEntities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "DETALLE_ID")
    private BigDecimal detalleId;
    @Column(name = "OFERTA_ID")
    private BigInteger ofertaId;

    public WsDetalleVentas() {
    }

    public WsDetalleVentas(BigDecimal detalleId) {
        this.detalleId = detalleId;
    }

    public BigDecimal getDetalleId() {
        return detalleId;
    }

    public void setDetalleId(BigDecimal detalleId) {
        this.detalleId = detalleId;
    }

    public BigInteger getOfertaId() {
        return ofertaId;
    }

    public void setOfertaId(BigInteger ofertaId) {
        this.ofertaId = ofertaId;
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
