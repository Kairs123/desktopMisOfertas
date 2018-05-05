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
import MisOfertasDesktopEntities.ImagenOferta;
import MisOfertasDesktopEntities.Oferta;
import MisOfertasDesktopEntities.Producto;
import MisOfertasDesktopEntities.Tienda;
import MisOfertasDesktopEntities.OfertaConsultadaUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import misOfertasDesktopController.exceptions.IllegalOrphanException;
import misOfertasDesktopController.exceptions.NonexistentEntityException;
import misOfertasDesktopController.exceptions.PreexistingEntityException;

/**
 *
 * @author David
 */
public class OfertaJpaController implements Serializable {

    public OfertaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Oferta oferta) throws PreexistingEntityException, Exception {
        if (oferta.getOfertaConsultadaUsuarioList() == null) {
            oferta.setOfertaConsultadaUsuarioList(new ArrayList<OfertaConsultadaUsuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ImagenOferta imagenId = oferta.getImagenId();
            if (imagenId != null) {
                imagenId = em.getReference(imagenId.getClass(), imagenId.getIdImagen());
                oferta.setImagenId(imagenId);
            }
            Producto producto = oferta.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getIdProducto());
                oferta.setProducto(producto);
            }
            Tienda tiendaId = oferta.getTiendaId();
            if (tiendaId != null) {
                tiendaId = em.getReference(tiendaId.getClass(), tiendaId.getIdTienda());
                oferta.setTiendaId(tiendaId);
            }
            List<OfertaConsultadaUsuario> attachedOfertaConsultadaUsuarioList = new ArrayList<OfertaConsultadaUsuario>();
            for (OfertaConsultadaUsuario ofertaConsultadaUsuarioListOfertaConsultadaUsuarioToAttach : oferta.getOfertaConsultadaUsuarioList()) {
                ofertaConsultadaUsuarioListOfertaConsultadaUsuarioToAttach = em.getReference(ofertaConsultadaUsuarioListOfertaConsultadaUsuarioToAttach.getClass(), ofertaConsultadaUsuarioListOfertaConsultadaUsuarioToAttach.getConsultaId());
                attachedOfertaConsultadaUsuarioList.add(ofertaConsultadaUsuarioListOfertaConsultadaUsuarioToAttach);
            }
            oferta.setOfertaConsultadaUsuarioList(attachedOfertaConsultadaUsuarioList);
            em.persist(oferta);
            if (imagenId != null) {
                imagenId.getOfertaList().add(oferta);
                imagenId = em.merge(imagenId);
            }
            if (producto != null) {
                producto.getOfertaList().add(oferta);
                producto = em.merge(producto);
            }
            if (tiendaId != null) {
                tiendaId.getOfertaList().add(oferta);
                tiendaId = em.merge(tiendaId);
            }
            for (OfertaConsultadaUsuario ofertaConsultadaUsuarioListOfertaConsultadaUsuario : oferta.getOfertaConsultadaUsuarioList()) {
                Oferta oldOfertaIdOfOfertaConsultadaUsuarioListOfertaConsultadaUsuario = ofertaConsultadaUsuarioListOfertaConsultadaUsuario.getOfertaId();
                ofertaConsultadaUsuarioListOfertaConsultadaUsuario.setOfertaId(oferta);
                ofertaConsultadaUsuarioListOfertaConsultadaUsuario = em.merge(ofertaConsultadaUsuarioListOfertaConsultadaUsuario);
                if (oldOfertaIdOfOfertaConsultadaUsuarioListOfertaConsultadaUsuario != null) {
                    oldOfertaIdOfOfertaConsultadaUsuarioListOfertaConsultadaUsuario.getOfertaConsultadaUsuarioList().remove(ofertaConsultadaUsuarioListOfertaConsultadaUsuario);
                    oldOfertaIdOfOfertaConsultadaUsuarioListOfertaConsultadaUsuario = em.merge(oldOfertaIdOfOfertaConsultadaUsuarioListOfertaConsultadaUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOferta(oferta.getOfertaId()) != null) {
                throw new PreexistingEntityException("Oferta " + oferta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Oferta oferta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Oferta persistentOferta = em.find(Oferta.class, oferta.getOfertaId());
            ImagenOferta imagenIdOld = persistentOferta.getImagenId();
            ImagenOferta imagenIdNew = oferta.getImagenId();
            Producto productoOld = persistentOferta.getProducto();
            Producto productoNew = oferta.getProducto();
            Tienda tiendaIdOld = persistentOferta.getTiendaId();
            Tienda tiendaIdNew = oferta.getTiendaId();
            List<OfertaConsultadaUsuario> ofertaConsultadaUsuarioListOld = persistentOferta.getOfertaConsultadaUsuarioList();
            List<OfertaConsultadaUsuario> ofertaConsultadaUsuarioListNew = oferta.getOfertaConsultadaUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (OfertaConsultadaUsuario ofertaConsultadaUsuarioListOldOfertaConsultadaUsuario : ofertaConsultadaUsuarioListOld) {
                if (!ofertaConsultadaUsuarioListNew.contains(ofertaConsultadaUsuarioListOldOfertaConsultadaUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OfertaConsultadaUsuario " + ofertaConsultadaUsuarioListOldOfertaConsultadaUsuario + " since its ofertaId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (imagenIdNew != null) {
                imagenIdNew = em.getReference(imagenIdNew.getClass(), imagenIdNew.getIdImagen());
                oferta.setImagenId(imagenIdNew);
            }
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getIdProducto());
                oferta.setProducto(productoNew);
            }
            if (tiendaIdNew != null) {
                tiendaIdNew = em.getReference(tiendaIdNew.getClass(), tiendaIdNew.getIdTienda());
                oferta.setTiendaId(tiendaIdNew);
            }
            List<OfertaConsultadaUsuario> attachedOfertaConsultadaUsuarioListNew = new ArrayList<OfertaConsultadaUsuario>();
            for (OfertaConsultadaUsuario ofertaConsultadaUsuarioListNewOfertaConsultadaUsuarioToAttach : ofertaConsultadaUsuarioListNew) {
                ofertaConsultadaUsuarioListNewOfertaConsultadaUsuarioToAttach = em.getReference(ofertaConsultadaUsuarioListNewOfertaConsultadaUsuarioToAttach.getClass(), ofertaConsultadaUsuarioListNewOfertaConsultadaUsuarioToAttach.getConsultaId());
                attachedOfertaConsultadaUsuarioListNew.add(ofertaConsultadaUsuarioListNewOfertaConsultadaUsuarioToAttach);
            }
            ofertaConsultadaUsuarioListNew = attachedOfertaConsultadaUsuarioListNew;
            oferta.setOfertaConsultadaUsuarioList(ofertaConsultadaUsuarioListNew);
            oferta = em.merge(oferta);
            if (imagenIdOld != null && !imagenIdOld.equals(imagenIdNew)) {
                imagenIdOld.getOfertaList().remove(oferta);
                imagenIdOld = em.merge(imagenIdOld);
            }
            if (imagenIdNew != null && !imagenIdNew.equals(imagenIdOld)) {
                imagenIdNew.getOfertaList().add(oferta);
                imagenIdNew = em.merge(imagenIdNew);
            }
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getOfertaList().remove(oferta);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getOfertaList().add(oferta);
                productoNew = em.merge(productoNew);
            }
            if (tiendaIdOld != null && !tiendaIdOld.equals(tiendaIdNew)) {
                tiendaIdOld.getOfertaList().remove(oferta);
                tiendaIdOld = em.merge(tiendaIdOld);
            }
            if (tiendaIdNew != null && !tiendaIdNew.equals(tiendaIdOld)) {
                tiendaIdNew.getOfertaList().add(oferta);
                tiendaIdNew = em.merge(tiendaIdNew);
            }
            for (OfertaConsultadaUsuario ofertaConsultadaUsuarioListNewOfertaConsultadaUsuario : ofertaConsultadaUsuarioListNew) {
                if (!ofertaConsultadaUsuarioListOld.contains(ofertaConsultadaUsuarioListNewOfertaConsultadaUsuario)) {
                    Oferta oldOfertaIdOfOfertaConsultadaUsuarioListNewOfertaConsultadaUsuario = ofertaConsultadaUsuarioListNewOfertaConsultadaUsuario.getOfertaId();
                    ofertaConsultadaUsuarioListNewOfertaConsultadaUsuario.setOfertaId(oferta);
                    ofertaConsultadaUsuarioListNewOfertaConsultadaUsuario = em.merge(ofertaConsultadaUsuarioListNewOfertaConsultadaUsuario);
                    if (oldOfertaIdOfOfertaConsultadaUsuarioListNewOfertaConsultadaUsuario != null && !oldOfertaIdOfOfertaConsultadaUsuarioListNewOfertaConsultadaUsuario.equals(oferta)) {
                        oldOfertaIdOfOfertaConsultadaUsuarioListNewOfertaConsultadaUsuario.getOfertaConsultadaUsuarioList().remove(ofertaConsultadaUsuarioListNewOfertaConsultadaUsuario);
                        oldOfertaIdOfOfertaConsultadaUsuarioListNewOfertaConsultadaUsuario = em.merge(oldOfertaIdOfOfertaConsultadaUsuarioListNewOfertaConsultadaUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = oferta.getOfertaId();
                if (findOferta(id) == null) {
                    throw new NonexistentEntityException("The oferta with id " + id + " no longer exists.");
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
            Oferta oferta;
            try {
                oferta = em.getReference(Oferta.class, id);
                oferta.getOfertaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The oferta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<OfertaConsultadaUsuario> ofertaConsultadaUsuarioListOrphanCheck = oferta.getOfertaConsultadaUsuarioList();
            for (OfertaConsultadaUsuario ofertaConsultadaUsuarioListOrphanCheckOfertaConsultadaUsuario : ofertaConsultadaUsuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Oferta (" + oferta + ") cannot be destroyed since the OfertaConsultadaUsuario " + ofertaConsultadaUsuarioListOrphanCheckOfertaConsultadaUsuario + " in its ofertaConsultadaUsuarioList field has a non-nullable ofertaId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ImagenOferta imagenId = oferta.getImagenId();
            if (imagenId != null) {
                imagenId.getOfertaList().remove(oferta);
                imagenId = em.merge(imagenId);
            }
            Producto producto = oferta.getProducto();
            if (producto != null) {
                producto.getOfertaList().remove(oferta);
                producto = em.merge(producto);
            }
            Tienda tiendaId = oferta.getTiendaId();
            if (tiendaId != null) {
                tiendaId.getOfertaList().remove(oferta);
                tiendaId = em.merge(tiendaId);
            }
            em.remove(oferta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Oferta> findOfertaEntities() {
        return findOfertaEntities(true, -1, -1);
    }

    public List<Oferta> findOfertaEntities(int maxResults, int firstResult) {
        return findOfertaEntities(false, maxResults, firstResult);
    }

    private List<Oferta> findOfertaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Oferta.class));
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

    public Oferta findOferta(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Oferta.class, id);
        } finally {
            em.close();
        }
    }

    public int getOfertaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Oferta> rt = cq.from(Oferta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
