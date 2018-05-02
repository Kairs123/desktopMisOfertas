/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.misofertas.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David
 */
@Entity
@Table(name = "OFERTA_CONSULTADA_USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OfertaConsultadaUsuario.findAll", query = "SELECT o FROM OfertaConsultadaUsuario o")
    , @NamedQuery(name = "OfertaConsultadaUsuario.findByFechaConsulta", query = "SELECT o FROM OfertaConsultadaUsuario o WHERE o.fechaConsulta = :fechaConsulta")
    , @NamedQuery(name = "OfertaConsultadaUsuario.findByConsultaId", query = "SELECT o FROM OfertaConsultadaUsuario o WHERE o.consultaId = :consultaId")})
public class OfertaConsultadaUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "FECHA_CONSULTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaConsulta;
    @Id
    @Basic(optional = false)
    @Column(name = "CONSULTA_ID")
    private Long consultaId;
    @JoinColumn(name = "OFERTA_ID", referencedColumnName = "OFERTA_ID")
    @ManyToOne(optional = false)
    private Oferta ofertaId;
    @JoinColumn(name = "USUARIO_ID", referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)
    private Usuario usuarioId;

    public OfertaConsultadaUsuario() {
    }

    public OfertaConsultadaUsuario(Long consultaId) {
        this.consultaId = consultaId;
    }

    public OfertaConsultadaUsuario(Long consultaId, Date fechaConsulta) {
        this.consultaId = consultaId;
        this.fechaConsulta = fechaConsulta;
    }

    public Date getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(Date fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public Long getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(Long consultaId) {
        this.consultaId = consultaId;
    }

    public Oferta getOfertaId() {
        return ofertaId;
    }

    public void setOfertaId(Oferta ofertaId) {
        this.ofertaId = ofertaId;
    }

    public Usuario getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consultaId != null ? consultaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OfertaConsultadaUsuario)) {
            return false;
        }
        OfertaConsultadaUsuario other = (OfertaConsultadaUsuario) object;
        if ((this.consultaId == null && other.consultaId != null) || (this.consultaId != null && !this.consultaId.equals(other.consultaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MisOfertasDesktopEntities.OfertaConsultadaUsuario[ consultaId=" + consultaId + " ]";
    }
    
}
