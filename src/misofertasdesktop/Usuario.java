/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misofertasdesktop;

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
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByUsuarioId", query = "SELECT u FROM Usuario u WHERE u.usuarioId = :usuarioId")
    , @NamedQuery(name = "Usuario.findByUsername", query = "SELECT u FROM Usuario u WHERE u.username = :username")
    , @NamedQuery(name = "Usuario.findByPassw", query = "SELECT u FROM Usuario u WHERE u.passw = :passw")
    , @NamedQuery(name = "Usuario.findByPuntosAcumulados", query = "SELECT u FROM Usuario u WHERE u.puntosAcumulados = :puntosAcumulados")
    , @NamedQuery(name = "Usuario.findByTiendaId", query = "SELECT u FROM Usuario u WHERE u.tiendaId = :tiendaId")
    , @NamedQuery(name = "Usuario.findByUserIsActive", query = "SELECT u FROM Usuario u WHERE u.userIsActive = :userIsActive")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "USUARIO_ID")
    private Long usuarioId;
    @Basic(optional = false)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @Column(name = "PASSW")
    private String passw;
    @Basic(optional = false)
    @Column(name = "PUNTOS_ACUMULADOS")
    private long puntosAcumulados;
    @Basic(optional = false)
    @Column(name = "TIENDA_ID")
    private long tiendaId;
    @Basic(optional = false)
    @Column(name = "USER_IS_ACTIVE")
    private String userIsActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encargado")
    private List<ImagenProducto> imagenProductoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioId")
    private List<Preferencias> preferenciasList;
    @JoinColumn(name = "PERSONA_ID", referencedColumnName = "PERSONA_ID")
    @ManyToOne(optional = false)
    private Persona personaId;
    @JoinColumn(name = "TIPO_USER", referencedColumnName = "TIPO_USER_ID")
    @ManyToOne(optional = false)
    private TipoUsuario tipoUser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioId")
    private List<Valoracion> valoracionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioId")
    private List<DescuentoEmitido> descuentoEmitidoList;

    public Usuario() {
    }

    public Usuario(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Usuario(Long usuarioId, String username, String passw, long puntosAcumulados, long tiendaId, String userIsActive) {
        this.usuarioId = usuarioId;
        this.username = username;
        this.passw = passw;
        this.puntosAcumulados = puntosAcumulados;
        this.tiendaId = tiendaId;
        this.userIsActive = userIsActive;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public long getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public void setPuntosAcumulados(long puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }

    public long getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(long tiendaId) {
        this.tiendaId = tiendaId;
    }

    public String getUserIsActive() {
        return userIsActive;
    }

    public void setUserIsActive(String userIsActive) {
        this.userIsActive = userIsActive;
    }

    @XmlTransient
    public List<ImagenProducto> getImagenProductoList() {
        return imagenProductoList;
    }

    public void setImagenProductoList(List<ImagenProducto> imagenProductoList) {
        this.imagenProductoList = imagenProductoList;
    }

    @XmlTransient
    public List<Preferencias> getPreferenciasList() {
        return preferenciasList;
    }

    public void setPreferenciasList(List<Preferencias> preferenciasList) {
        this.preferenciasList = preferenciasList;
    }

    public Persona getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Persona personaId) {
        this.personaId = personaId;
    }

    public TipoUsuario getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(TipoUsuario tipoUser) {
        this.tipoUser = tipoUser;
    }

    @XmlTransient
    public List<Valoracion> getValoracionList() {
        return valoracionList;
    }

    public void setValoracionList(List<Valoracion> valoracionList) {
        this.valoracionList = valoracionList;
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
        hash += (usuarioId != null ? usuarioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuarioId == null && other.usuarioId != null) || (this.usuarioId != null && !this.usuarioId.equals(other.usuarioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "misofertasdesktop.Usuario[ usuarioId=" + usuarioId + " ]";
    }
    
}
