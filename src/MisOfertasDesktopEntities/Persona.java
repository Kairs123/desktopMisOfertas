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
@Table(name = "PERSONA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p")
    , @NamedQuery(name = "Persona.findByIdPersona", query = "SELECT p FROM Persona p WHERE p.idPersona = :idPersona")
    , @NamedQuery(name = "Persona.findByRut", query = "SELECT p FROM Persona p WHERE p.rut = :rut")
    , @NamedQuery(name = "Persona.findByDvRut", query = "SELECT p FROM Persona p WHERE p.dvRut = :dvRut")
    , @NamedQuery(name = "Persona.findByPrimerNombre", query = "SELECT p FROM Persona p WHERE p.primerNombre = :primerNombre")
    , @NamedQuery(name = "Persona.findBySegundoNombre", query = "SELECT p FROM Persona p WHERE p.segundoNombre = :segundoNombre")
    , @NamedQuery(name = "Persona.findByApellidoPaterno", query = "SELECT p FROM Persona p WHERE p.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "Persona.findByApellidoMaterno", query = "SELECT p FROM Persona p WHERE p.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "Persona.findByDireccion", query = "SELECT p FROM Persona p WHERE p.direccion = :direccion")
    , @NamedQuery(name = "Persona.findByTelefono", query = "SELECT p FROM Persona p WHERE p.telefono = :telefono")
    , @NamedQuery(name = "Persona.findByIsActive", query = "SELECT p FROM Persona p WHERE p.isActive = :isActive")
    , @NamedQuery(name = "Persona.findByEmail", query = "SELECT p FROM Persona p WHERE p.email = :email")})
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PERSONA")
    private Long idPersona;
    @Basic(optional = false)
    @Column(name = "RUT")
    private long rut;
    @Basic(optional = false)
    @Column(name = "DV_RUT")
    private Character dvRut;
    @Basic(optional = false)
    @Column(name = "PRIMER_NOMBRE")
    private String primerNombre;
    @Column(name = "SEGUNDO_NOMBRE")
    private String segundoNombre;
    @Basic(optional = false)
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "TELEFONO")
    private Long telefono;
    @Basic(optional = false)
    @Column(name = "IS_ACTIVE")
    private String isActive;
    @Column(name = "EMAIL")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personaId")
    private List<Usuario> usuarioList;
    @JoinColumn(name = "COMUNA_ID", referencedColumnName = "ID_COMUNA")
    @ManyToOne(optional = false)
    private Comuna comunaId;

    public Persona() {
    }

    public Persona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public Persona(Long idPersona, long rut, Character dvRut, String primerNombre, String apellidoPaterno, String isActive) {
        this.idPersona = idPersona;
        this.rut = rut;
        this.dvRut = dvRut;
        this.primerNombre = primerNombre;
        this.apellidoPaterno = apellidoPaterno;
        this.isActive = isActive;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public long getRut() {
        return rut;
    }

    public void setRut(long rut) {
        this.rut = rut;
    }

    public Character getDvRut() {
        return dvRut;
    }

    public void setDvRut(Character dvRut) {
        this.dvRut = dvRut;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public Comuna getComunaId() {
        return comunaId;
    }

    public void setComunaId(Comuna comunaId) {
        this.comunaId = comunaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersona != null ? idPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.idPersona == null && other.idPersona != null) || (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MisOfertasDesktopEntities.Persona[ idPersona=" + idPersona + " ]";
    }
    
}
