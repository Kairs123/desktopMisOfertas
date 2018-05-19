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
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
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
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p ORDER BY p.idProducto")
    , @NamedQuery(name = "Producto.findByIdProducto", query = "SELECT p FROM Producto p WHERE p.idProducto = :idProducto")
    , @NamedQuery(name = "Producto.findByNombreProducto", query = "SELECT p FROM Producto p WHERE p.nombreProducto = :nombreProducto")
    , @NamedQuery(name = "Producto.getMaxId", query = "SELECT MAX(p.idProducto) FROM Producto p")
    , @NamedQuery(name = "Producto.findByEsPerecible", query = "SELECT p FROM Producto p WHERE p.esPerecible = :esPerecible")
    , @NamedQuery(name = "Producto.findByFechaVencimiento", query = "SELECT p FROM Producto p WHERE p.fechaVencimiento = :fechaVencimiento")
    , @NamedQuery(name = "Producto.findByIsActive", query = "SELECT p FROM Producto p WHERE p.isActive = :isActive")})
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
            name = "createProducto",
            procedureName = "PKG_CRUD_PRODUCTO.PRC_CREATE_PRODUCTO",
            parameters = {
                @javax.persistence.StoredProcedureParameter(
                        name = "p_producto_id",
                        type = Long.class,
                        mode = ParameterMode.IN
                )
                ,
                @javax.persistence.StoredProcedureParameter(
                        name = "p_nombre_producto",
                        type = String.class,
                        mode = ParameterMode.IN
                )
                ,
                @javax.persistence.StoredProcedureParameter(
                        name = "p_rubro_id",
                        type = Long.class,
                        mode = ParameterMode.IN
                )
                ,
                @javax.persistence.StoredProcedureParameter(
                        name = "p_es_perecible",
                        type = Integer.class,
                        mode = ParameterMode.IN
                )
                ,
                @javax.persistence.StoredProcedureParameter(
                        name = "p_fecha_venc",
                        type = String.class,
                        mode = ParameterMode.IN
                )
            }
    )
    ,
    @NamedStoredProcedureQuery(
            name = "editProducto",
            procedureName = "PKG_CRUD_PRODUCTO.PRC_UPDATE_PRODUCTO",
            parameters = {
                @javax.persistence.StoredProcedureParameter(
                        name = "p_id_prod",
                        type = Long.class,
                        mode = ParameterMode.IN
                )
                ,
                 @javax.persistence.StoredProcedureParameter(
                        name = "p_nombre_producto",
                        type = String.class,
                        mode = ParameterMode.IN
                )
            }
    )
    ,
    @NamedStoredProcedureQuery(
            name = "listAll",
            resultClasses = Producto.class,
            procedureName = "PKG_CRUD_PRODUCTO.PRC_LISTAR_TODOS",
            parameters = {
                @javax.persistence.StoredProcedureParameter(
                        name = "v_cursor",
                        type = Producto.class,
                        mode = ParameterMode.REF_CURSOR
                )
            }
    )
})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PRODUCTO")
    private Long idProducto;
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
    private List<Valoracion> valoracionList;
    @JoinColumn(name = "RUBRO_ID", referencedColumnName = "ID_RUBRO")
    @ManyToOne(optional = false)
    private Rubro rubro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoId")
    private List<Oferta> ofertaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoId")
    private List<DescuentoEmitido> descuentoEmitidoList;

    public Producto() {
    }

    public Producto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Producto(Long idProducto, String nombreProducto, long esPerecible, String isActive) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.esPerecible = esPerecible;
        this.isActive = isActive;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
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
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MisOfertasDesktopEntities.Producto[ idProducto=" + idProducto + " ]";
    }

}
