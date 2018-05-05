/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misOfertasDesktopController;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MisOfertasDesktopEntities.Rubro;
import MisOfertasDesktopEntities.Valoracion;
import java.util.ArrayList;
import java.util.List;
import MisOfertasDesktopEntities.Oferta;
import MisOfertasDesktopEntities.DescuentoEmitido;
import MisOfertasDesktopEntities.Producto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.StoredProcedureQuery;
import misOfertasDesktopController.exceptions.IllegalOrphanException;
import misOfertasDesktopController.exceptions.NonexistentEntityException;
import misOfertasDesktopController.exceptions.PreexistingEntityException;

/**
 *
 * @author David
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) throws PreexistingEntityException, Exception {
        if (producto.getValoracionList() == null) {
            producto.setValoracionList(new ArrayList<Valoracion>());
        }
        if (producto.getOfertaList() == null) {
            producto.setOfertaList(new ArrayList<Oferta>());
        }
        if (producto.getDescuentoEmitidoList() == null) {
            producto.setDescuentoEmitidoList(new ArrayList<DescuentoEmitido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rubro rubro = producto.getRubro();
            if (rubro != null) {
                rubro = em.getReference(rubro.getClass(), rubro.getIdRubro());
                producto.setRubro(rubro);
            }
            List<Valoracion> attachedValoracionList = new ArrayList<Valoracion>();
            for (Valoracion valoracionListValoracionToAttach : producto.getValoracionList()) {
                valoracionListValoracionToAttach = em.getReference(valoracionListValoracionToAttach.getClass(), valoracionListValoracionToAttach.getValoracionId());
                attachedValoracionList.add(valoracionListValoracionToAttach);
            }
            producto.setValoracionList(attachedValoracionList);
            List<Oferta> attachedOfertaList = new ArrayList<Oferta>();
            for (Oferta ofertaListOfertaToAttach : producto.getOfertaList()) {
                ofertaListOfertaToAttach = em.getReference(ofertaListOfertaToAttach.getClass(), ofertaListOfertaToAttach.getOfertaId());
                attachedOfertaList.add(ofertaListOfertaToAttach);
            }
            producto.setOfertaList(attachedOfertaList);
            List<DescuentoEmitido> attachedDescuentoEmitidoList = new ArrayList<DescuentoEmitido>();
            for (DescuentoEmitido descuentoEmitidoListDescuentoEmitidoToAttach : producto.getDescuentoEmitidoList()) {
                descuentoEmitidoListDescuentoEmitidoToAttach = em.getReference(descuentoEmitidoListDescuentoEmitidoToAttach.getClass(), descuentoEmitidoListDescuentoEmitidoToAttach.getDescuentoId());
                attachedDescuentoEmitidoList.add(descuentoEmitidoListDescuentoEmitidoToAttach);
            }
            producto.setDescuentoEmitidoList(attachedDescuentoEmitidoList);
            StoredProcedureQuery createProducto = em.createNamedStoredProcedureQuery("createProducto");
            createProducto.setParameter("p_producto_id", producto.getIdProducto());
            createProducto.setParameter("p_nombre_producto", producto.getNombreProducto());
            createProducto.setParameter("p_rubro_id", producto.getRubro().getIdRubro());
            createProducto.setParameter("p_es_perecible", producto.getEsPerecible());
            createProducto.setParameter("p_fecha_venc", producto.getFechaVencimiento());
            createProducto.setParameter("p_activo", producto.getIsActive());
            createProducto.execute();
            if (rubro != null) {
                rubro.getProductoList().add(producto);
                rubro = em.merge(rubro);
            }
            for (Valoracion valoracionListValoracion : producto.getValoracionList()) {
                Producto oldProductoIdOfValoracionListValoracion = valoracionListValoracion.getProductoId();
                valoracionListValoracion.setProductoId(producto);
                valoracionListValoracion = em.merge(valoracionListValoracion);
                if (oldProductoIdOfValoracionListValoracion != null) {
                    oldProductoIdOfValoracionListValoracion.getValoracionList().remove(valoracionListValoracion);
                    oldProductoIdOfValoracionListValoracion = em.merge(oldProductoIdOfValoracionListValoracion);
                }
            }
            for (Oferta ofertaListOferta : producto.getOfertaList()) {
                Producto oldProductoOfOfertaListOferta = ofertaListOferta.getProducto();
                ofertaListOferta.setProducto(producto);
                ofertaListOferta = em.merge(ofertaListOferta);
                if (oldProductoOfOfertaListOferta != null) {
                    oldProductoOfOfertaListOferta.getOfertaList().remove(ofertaListOferta);
                    oldProductoOfOfertaListOferta = em.merge(oldProductoOfOfertaListOferta);
                }
            }
            for (DescuentoEmitido descuentoEmitidoListDescuentoEmitido : producto.getDescuentoEmitidoList()) {
                Producto oldProductoIdOfDescuentoEmitidoListDescuentoEmitido = descuentoEmitidoListDescuentoEmitido.getProductoId();
                descuentoEmitidoListDescuentoEmitido.setProductoId(producto);
                descuentoEmitidoListDescuentoEmitido = em.merge(descuentoEmitidoListDescuentoEmitido);
                if (oldProductoIdOfDescuentoEmitidoListDescuentoEmitido != null) {
                    oldProductoIdOfDescuentoEmitidoListDescuentoEmitido.getDescuentoEmitidoList().remove(descuentoEmitidoListDescuentoEmitido);
                    oldProductoIdOfDescuentoEmitidoListDescuentoEmitido = em.merge(oldProductoIdOfDescuentoEmitidoListDescuentoEmitido);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProducto(producto.getIdProducto()) != null) {
                throw new PreexistingEntityException("Producto " + producto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getIdProducto());
            Rubro rubroOld = persistentProducto.getRubro();
            Rubro rubroNew = producto.getRubro();
            List<Valoracion> valoracionListOld = persistentProducto.getValoracionList();
            List<Valoracion> valoracionListNew = producto.getValoracionList();
            List<Oferta> ofertaListOld = persistentProducto.getOfertaList();
            List<Oferta> ofertaListNew = producto.getOfertaList();
            List<DescuentoEmitido> descuentoEmitidoListOld = persistentProducto.getDescuentoEmitidoList();
            List<DescuentoEmitido> descuentoEmitidoListNew = producto.getDescuentoEmitidoList();
            List<String> illegalOrphanMessages = null;
            for (Valoracion valoracionListOldValoracion : valoracionListOld) {
                if (!valoracionListNew.contains(valoracionListOldValoracion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Valoracion " + valoracionListOldValoracion + " since its productoId field is not nullable.");
                }
            }
            for (Oferta ofertaListOldOferta : ofertaListOld) {
                if (!ofertaListNew.contains(ofertaListOldOferta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Oferta " + ofertaListOldOferta + " since its producto field is not nullable.");
                }
            }
            for (DescuentoEmitido descuentoEmitidoListOldDescuentoEmitido : descuentoEmitidoListOld) {
                if (!descuentoEmitidoListNew.contains(descuentoEmitidoListOldDescuentoEmitido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DescuentoEmitido " + descuentoEmitidoListOldDescuentoEmitido + " since its productoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (rubroNew != null) {
                rubroNew = em.getReference(rubroNew.getClass(), rubroNew.getIdRubro());
                producto.setRubro(rubroNew);
            }
            List<Valoracion> attachedValoracionListNew = new ArrayList<Valoracion>();
            for (Valoracion valoracionListNewValoracionToAttach : valoracionListNew) {
                valoracionListNewValoracionToAttach = em.getReference(valoracionListNewValoracionToAttach.getClass(), valoracionListNewValoracionToAttach.getValoracionId());
                attachedValoracionListNew.add(valoracionListNewValoracionToAttach);
            }
            valoracionListNew = attachedValoracionListNew;
            producto.setValoracionList(valoracionListNew);
            List<Oferta> attachedOfertaListNew = new ArrayList<Oferta>();
            for (Oferta ofertaListNewOfertaToAttach : ofertaListNew) {
                ofertaListNewOfertaToAttach = em.getReference(ofertaListNewOfertaToAttach.getClass(), ofertaListNewOfertaToAttach.getOfertaId());
                attachedOfertaListNew.add(ofertaListNewOfertaToAttach);
            }
            ofertaListNew = attachedOfertaListNew;
            producto.setOfertaList(ofertaListNew);
            List<DescuentoEmitido> attachedDescuentoEmitidoListNew = new ArrayList<DescuentoEmitido>();
            for (DescuentoEmitido descuentoEmitidoListNewDescuentoEmitidoToAttach : descuentoEmitidoListNew) {
                descuentoEmitidoListNewDescuentoEmitidoToAttach = em.getReference(descuentoEmitidoListNewDescuentoEmitidoToAttach.getClass(), descuentoEmitidoListNewDescuentoEmitidoToAttach.getDescuentoId());
                attachedDescuentoEmitidoListNew.add(descuentoEmitidoListNewDescuentoEmitidoToAttach);
            }
            descuentoEmitidoListNew = attachedDescuentoEmitidoListNew;
            producto.setDescuentoEmitidoList(descuentoEmitidoListNew);
            /*producto = em.merge(producto);*/
            StoredProcedureQuery editProducto = em.createNamedStoredProcedureQuery("editProducto");
            editProducto.setParameter("p_id_prod", producto.getIdProducto());
            editProducto.setParameter("p_nombre_producto", producto.getNombreProducto());
            editProducto.setParameter("p_fecha_venc", producto.getFechaVencimiento());
            editProducto.executeUpdate();
            
            if (rubroOld != null && !rubroOld.equals(rubroNew)) {
                rubroOld.getProductoList().remove(producto);
                rubroOld = em.merge(rubroOld);
            }
            if (rubroNew != null && !rubroNew.equals(rubroOld)) {
                rubroNew.getProductoList().add(producto);
                rubroNew = em.merge(rubroNew);
            }
            for (Valoracion valoracionListNewValoracion : valoracionListNew) {
                if (!valoracionListOld.contains(valoracionListNewValoracion)) {
                    Producto oldProductoIdOfValoracionListNewValoracion = valoracionListNewValoracion.getProductoId();
                    valoracionListNewValoracion.setProductoId(producto);
                    valoracionListNewValoracion = em.merge(valoracionListNewValoracion);
                    if (oldProductoIdOfValoracionListNewValoracion != null && !oldProductoIdOfValoracionListNewValoracion.equals(producto)) {
                        oldProductoIdOfValoracionListNewValoracion.getValoracionList().remove(valoracionListNewValoracion);
                        oldProductoIdOfValoracionListNewValoracion = em.merge(oldProductoIdOfValoracionListNewValoracion);
                    }
                }
            }
            for (Oferta ofertaListNewOferta : ofertaListNew) {
                if (!ofertaListOld.contains(ofertaListNewOferta)) {
                    Producto oldProductoOfOfertaListNewOferta = ofertaListNewOferta.getProducto();
                    ofertaListNewOferta.setProducto(producto);
                    ofertaListNewOferta = em.merge(ofertaListNewOferta);
                    if (oldProductoOfOfertaListNewOferta != null && !oldProductoOfOfertaListNewOferta.equals(producto)) {
                        oldProductoOfOfertaListNewOferta.getOfertaList().remove(ofertaListNewOferta);
                        oldProductoOfOfertaListNewOferta = em.merge(oldProductoOfOfertaListNewOferta);
                    }
                }
            }
            for (DescuentoEmitido descuentoEmitidoListNewDescuentoEmitido : descuentoEmitidoListNew) {
                if (!descuentoEmitidoListOld.contains(descuentoEmitidoListNewDescuentoEmitido)) {
                    Producto oldProductoIdOfDescuentoEmitidoListNewDescuentoEmitido = descuentoEmitidoListNewDescuentoEmitido.getProductoId();
                    descuentoEmitidoListNewDescuentoEmitido.setProductoId(producto);
                    descuentoEmitidoListNewDescuentoEmitido = em.merge(descuentoEmitidoListNewDescuentoEmitido);
                    if (oldProductoIdOfDescuentoEmitidoListNewDescuentoEmitido != null && !oldProductoIdOfDescuentoEmitidoListNewDescuentoEmitido.equals(producto)) {
                        oldProductoIdOfDescuentoEmitidoListNewDescuentoEmitido.getDescuentoEmitidoList().remove(descuentoEmitidoListNewDescuentoEmitido);
                        oldProductoIdOfDescuentoEmitidoListNewDescuentoEmitido = em.merge(oldProductoIdOfDescuentoEmitidoListNewDescuentoEmitido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = producto.getIdProducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getIdProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Valoracion> valoracionListOrphanCheck = producto.getValoracionList();
            for (Valoracion valoracionListOrphanCheckValoracion : valoracionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Valoracion " + valoracionListOrphanCheckValoracion + " in its valoracionList field has a non-nullable productoId field.");
            }
            List<Oferta> ofertaListOrphanCheck = producto.getOfertaList();
            for (Oferta ofertaListOrphanCheckOferta : ofertaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Oferta " + ofertaListOrphanCheckOferta + " in its ofertaList field has a non-nullable producto field.");
            }
            List<DescuentoEmitido> descuentoEmitidoListOrphanCheck = producto.getDescuentoEmitidoList();
            for (DescuentoEmitido descuentoEmitidoListOrphanCheckDescuentoEmitido : descuentoEmitidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the DescuentoEmitido " + descuentoEmitidoListOrphanCheckDescuentoEmitido + " in its descuentoEmitidoList field has a non-nullable productoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Rubro rubro = producto.getRubro();
            if (rubro != null) {
                rubro.getProductoList().remove(producto);
                rubro = em.merge(rubro);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Producto findProducto(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
