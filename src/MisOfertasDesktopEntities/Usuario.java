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
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario")
    , @NamedQuery(name = "Usuario.findByUsername", query = "SELECT u FROM Usuario u WHERE u.username = :username")
    , @NamedQuery(name = "Usuario.findByPassw", query = "SELECT u FROM Usuario u WHERE u.passw = :passw")
    , @NamedQuery(name = "Usuario.findByPuntosAcumulados", query = "SELECT u FROM Usuario u WHERE u.puntosAcumulados = :puntosAcumulados")
    , @NamedQuery(name = "Usuario.findByTiendaId", query = "SELECT u FROM Usuario u WHERE u.tiendaId = :tiendaId")
    , @NamedQuery(name = "Usuario.findByUserIsActive", query = "SELECT u FROM Usuario u WHERE u.userIsActive = :userIsActive")
    , @NamedQuery(name = "Usuario.findByRecibirInformacion", query = "SELECT u FROM Usuario u WHERE u.recibirInformacion = :recibirInformacion")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_USUARIO")
    private Long idUsuario;
    @Basic(optional = false)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @Column(name = "PASSW")
    private String passw;
    @Column(name = "PUNTOS_ACUMULADOS")
    private Long puntosAcumulados;
    @Column(name = "TIENDA_ID")
    private Long tiendaId;
    @Basic(optional = false)
    @Column(name = "USER_IS_ACTIVE")
    private String userIsActive;
    @Column(name = "RECIBIR_INFORMACION")
    private Short recibirInformacion;
    @JoinColumn(name = "PERSONA_ID", referencedColumnName = "ID_PERSONA")
    @ManyToOne(optional = false)
    private Persona personaId;
    @JoinColumn(name = "TIPO_USUARIO_ID", referencedColumnName = "ID_TIPO_USUARIO")
    @ManyToOne(optional = false)
    private TipoUsuario tipoUsuarioId;
    @OneToMany(mappedBy = "usuarioId")
    private List<PrefTiendaUsuario> prefTiendaUsuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioId")
    private List<Valoracion> valoracionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioId")
    private List<PrefRubroUsuario> prefRubroUsuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioId")
    private List<DescuentoEmitido> descuentoEmitidoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioId")
    private List<OfertaConsultadaUsuario> ofertaConsultadaUsuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioId")
    private List<UsuarioPuntosAcumulados> usuarioPuntosAcumuladosList;

    public Usuario() {
    }

    public Usuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(Long idUsuario, String username, String passw, String userIsActive) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.passw = passw;
        this.userIsActive = userIsActive;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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

    public Long getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public void setPuntosAcumulados(Long puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }

    public Long getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(Long tiendaId) {
        this.tiendaId = tiendaId;
    }

    public String getUserIsActive() {
        return userIsActive;
    }

    public void setUserIsActive(String userIsActive) {
        this.userIsActive = userIsActive;
    }

    public Short getRecibirInformacion() {
        return recibirInformacion;
    }

    public void setRecibirInformacion(Short recibirInformacion) {
        this.recibirInformacion = recibirInformacion;
    }

    public Persona getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Persona personaId) {
        this.personaId = personaId;
    }

    public TipoUsuario getTipoUsuarioId() {
        return tipoUsuarioId;
    }

    public void setTipoUsuarioId(TipoUsuario tipoUsuarioId) {
        this.tipoUsuarioId = tipoUsuarioId;
    }

    @XmlTransient
    public List<PrefTiendaUsuario> getPrefTiendaUsuarioList() {
        return prefTiendaUsuarioList;
    }

    public void setPrefTiendaUsuarioList(List<PrefTiendaUsuario> prefTiendaUsuarioList) {
        this.prefTiendaUsuarioList = prefTiendaUsuarioList;
    }

    @XmlTransient
    public List<Valoracion> getValoracionList() {
        return valoracionList;
    }

    public void setValoracionList(List<Valoracion> valoracionList) {
        this.valoracionList = valoracionList;
    }

    @XmlTransient
    public List<PrefRubroUsuario> getPrefRubroUsuarioList() {
        return prefRubroUsuarioList;
    }

    public void setPrefRubroUsuarioList(List<PrefRubroUsuario> prefRubroUsuarioList) {
        this.prefRubroUsuarioList = prefRubroUsuarioList;
    }

    @XmlTransient
    public List<DescuentoEmitido> getDescuentoEmitidoList() {
        return descuentoEmitidoList;
    }

    public void setDescuentoEmitidoList(List<DescuentoEmitido> descuentoEmitidoList) {
        this.descuentoEmitidoList = descuentoEmitidoList;
    }

    @XmlTransient
    public List<OfertaConsultadaUsuario> getOfertaConsultadaUsuarioList() {
        return ofertaConsultadaUsuarioList;
    }

    public void setOfertaConsultadaUsuarioList(List<OfertaConsultadaUsuario> ofertaConsultadaUsuarioList) {
        this.ofertaConsultadaUsuarioList = ofertaConsultadaUsuarioList;
    }

    @XmlTransient
    public List<UsuarioPuntosAcumulados> getUsuarioPuntosAcumuladosList() {
        return usuarioPuntosAcumuladosList;
    }

    public void setUsuarioPuntosAcumuladosList(List<UsuarioPuntosAcumulados> usuarioPuntosAcumuladosList) {
        this.usuarioPuntosAcumuladosList = usuarioPuntosAcumuladosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MisOfertasDesktopEntities.Usuario[ idUsuario=" + idUsuario + " ]";
    }
    
}
