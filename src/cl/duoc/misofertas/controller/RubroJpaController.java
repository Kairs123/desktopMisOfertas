/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.misofertas.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.duoc.misofertas.domain.Tienda;
import cl.duoc.misofertas.domain.Preferencias;
import java.util.ArrayList;
import java.util.List;
import cl.duoc.misofertas.domain.Producto;
import cl.duoc.misofertas.domain.Rubro;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import cl.duoc.misofertas.controller.exceptions.IllegalOrphanException;
import cl.duoc.misofertas.controller.exceptions.NonexistentEntityException;
import cl.duoc.misofertas.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author David
 */
public class RubroJpaController implements Serializable {

    public RubroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rubro rubro) throws PreexistingEntityException, Exception {
        if (rubro.getPreferenciasList() == null) {
            rubro.setPreferenciasList(new ArrayList<Preferencias>());
        }
        if (rubro.getProductoList() == null) {
            rubro.setProductoList(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tienda tiendaId = rubro.getTiendaId();
            if (tiendaId != null) {
                tiendaId = em.getReference(tiendaId.getClass(), tiendaId.getIdTienda());
                rubro.setTiendaId(tiendaId);
            }
            List<Preferencias> attachedPreferenciasList = new ArrayList<Preferencias>();
            for (Preferencias preferenciasListPreferenciasToAttach : rubro.getPreferenciasList()) {
                preferenciasListPreferenciasToAttach = em.getReference(preferenciasListPreferenciasToAttach.getClass(), preferenciasListPreferenciasToAttach.getPreferenciaId());
                attachedPreferenciasList.add(preferenciasListPreferenciasToAttach);
            }
            rubro.setPreferenciasList(attachedPreferenciasList);
            List<Producto> attachedProductoList = new ArrayList<Producto>();
            for (Producto productoListProductoToAttach : rubro.getProductoList()) {
                productoListProductoToAttach = em.getReference(productoListProductoToAttach.getClass(), productoListProductoToAttach.getProductoId());
                attachedProductoList.add(productoListProductoToAttach);
            }
            rubro.setProductoList(attachedProductoList);
            em.persist(rubro);
            if (tiendaId != null) {
                tiendaId.getRubroList().add(rubro);
                tiendaId = em.merge(tiendaId);
            }
            for (Preferencias preferenciasListPreferencias : rubro.getPreferenciasList()) {
                Rubro oldRubroIdOfPreferenciasListPreferencias = preferenciasListPreferencias.getRubroId();
                preferenciasListPreferencias.setRubroId(rubro);
                preferenciasListPreferencias = em.merge(preferenciasListPreferencias);
                if (oldRubroIdOfPreferenciasListPreferencias != null) {
                    oldRubroIdOfPreferenciasListPreferencias.getPreferenciasList().remove(preferenciasListPreferencias);
                    oldRubroIdOfPreferenciasListPreferencias = em.merge(oldRubroIdOfPreferenciasListPreferencias);
                }
            }
            for (Producto productoListProducto : rubro.getProductoList()) {
                Rubro oldRubroOfProductoListProducto = productoListProducto.getRubro();
                productoListProducto.setRubro(rubro);
                productoListProducto = em.merge(productoListProducto);
                if (oldRubroOfProductoListProducto != null) {
                    oldRubroOfProductoListProducto.getProductoList().remove(productoListProducto);
                    oldRubroOfProductoListProducto = em.merge(oldRubroOfProductoListProducto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRubro(rubro.getIdRubro()) != null) {
                throw new PreexistingEntityException("Rubro " + rubro + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rubro rubro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rubro persistentRubro = em.find(Rubro.class, rubro.getIdRubro());
            Tienda tiendaIdOld = persistentRubro.getTiendaId();
            Tienda tiendaIdNew = rubro.getTiendaId();
            List<Preferencias> preferenciasListOld = persistentRubro.getPreferenciasList();
            List<Preferencias> preferenciasListNew = rubro.getPreferenciasList();
            List<Producto> productoListOld = persistentRubro.getProductoList();
            List<Producto> productoListNew = rubro.getProductoList();
            List<String> illegalOrphanMessages = null;
            for (Preferencias preferenciasListOldPreferencias : preferenciasListOld) {
                if (!preferenciasListNew.contains(preferenciasListOldPreferencias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Preferencias " + preferenciasListOldPreferencias + " since its rubroId field is not nullable.");
                }
            }
            for (Producto productoListOldProducto : productoListOld) {
                if (!productoListNew.contains(productoListOldProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Producto " + productoListOldProducto + " since its rubro field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tiendaIdNew != null) {
                tiendaIdNew = em.getReference(tiendaIdNew.getClass(), tiendaIdNew.getIdTienda());
                rubro.setTiendaId(tiendaIdNew);
            }
            List<Preferencias> attachedPreferenciasListNew = new ArrayList<Preferencias>();
            for (Preferencias preferenciasListNewPreferenciasToAttach : preferenciasListNew) {
                preferenciasListNewPreferenciasToAttach = em.getReference(preferenciasListNewPreferenciasToAttach.getClass(), preferenciasListNewPreferenciasToAttach.getPreferenciaId());
                attachedPreferenciasListNew.add(preferenciasListNewPreferenciasToAttach);
            }
            preferenciasListNew = attachedPreferenciasListNew;
            rubro.setPreferenciasList(preferenciasListNew);
            List<Producto> attachedProductoListNew = new ArrayList<Producto>();
            for (Producto productoListNewProductoToAttach : productoListNew) {
                productoListNewProductoToAttach = em.getReference(productoListNewProductoToAttach.getClass(), productoListNewProductoToAttach.getProductoId());
                attachedProductoListNew.add(productoListNewProductoToAttach);
            }
            productoListNew = attachedProductoListNew;
            rubro.setProductoList(productoListNew);
            rubro = em.merge(rubro);
            if (tiendaIdOld != null && !tiendaIdOld.equals(tiendaIdNew)) {
                tiendaIdOld.getRubroList().remove(rubro);
                tiendaIdOld = em.merge(tiendaIdOld);
            }
            if (tiendaIdNew != null && !tiendaIdNew.equals(tiendaIdOld)) {
                tiendaIdNew.getRubroList().add(rubro);
                tiendaIdNew = em.merge(tiendaIdNew);
            }
            for (Preferencias preferenciasListNewPreferencias : preferenciasListNew) {
                if (!preferenciasListOld.contains(preferenciasListNewPreferencias)) {
                    Rubro oldRubroIdOfPreferenciasListNewPreferencias = preferenciasListNewPreferencias.getRubroId();
                    preferenciasListNewPreferencias.setRubroId(rubro);
                    preferenciasListNewPreferencias = em.merge(preferenciasListNewPreferencias);
                    if (oldRubroIdOfPreferenciasListNewPreferencias != null && !oldRubroIdOfPreferenciasListNewPreferencias.equals(rubro)) {
                        oldRubroIdOfPreferenciasListNewPreferencias.getPreferenciasList().remove(preferenciasListNewPreferencias);
                        oldRubroIdOfPreferenciasListNewPreferencias = em.merge(oldRubroIdOfPreferenciasListNewPreferencias);
                    }
                }
            }
            for (Producto productoListNewProducto : productoListNew) {
                if (!productoListOld.contains(productoListNewProducto)) {
                    Rubro oldRubroOfProductoListNewProducto = productoListNewProducto.getRubro();
                    productoListNewProducto.setRubro(rubro);
                    productoListNewProducto = em.merge(productoListNewProducto);
                    if (oldRubroOfProductoListNewProducto != null && !oldRubroOfProductoListNewProducto.equals(rubro)) {
                        oldRubroOfProductoListNewProducto.getProductoList().remove(productoListNewProducto);
                        oldRubroOfProductoListNewProducto = em.merge(oldRubroOfProductoListNewProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = rubro.getIdRubro();
                if (findRubro(id) == null) {
                    throw new NonexistentEntityException("The rubro with id " + id + " no longer exists.");
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
            Rubro rubro;
            try {
                rubro = em.getReference(Rubro.class, id);
                rubro.getIdRubro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rubro with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Preferencias> preferenciasListOrphanCheck = rubro.getPreferenciasList();
            for (Preferencias preferenciasListOrphanCheckPreferencias : preferenciasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rubro (" + rubro + ") cannot be destroyed since the Preferencias " + preferenciasListOrphanCheckPreferencias + " in its preferenciasList field has a non-nullable rubroId field.");
            }
            List<Producto> productoListOrphanCheck = rubro.getProductoList();
            for (Producto productoListOrphanCheckProducto : productoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rubro (" + rubro + ") cannot be destroyed since the Producto " + productoListOrphanCheckProducto + " in its productoList field has a non-nullable rubro field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tienda tiendaId = rubro.getTiendaId();
            if (tiendaId != null) {
                tiendaId.getRubroList().remove(rubro);
                tiendaId = em.merge(tiendaId);
            }
            em.remove(rubro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rubro> findRubroEntities() {
        return findRubroEntities(true, -1, -1);
    }

    public List<Rubro> findRubroEntities(int maxResults, int firstResult) {
        return findRubroEntities(false, maxResults, firstResult);
    }

    private List<Rubro> findRubroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rubro.class));
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

    public Rubro findRubro(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rubro.class, id);
        } finally {
            em.close();
        }
    }

    public int getRubroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rubro> rt = cq.from(Rubro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}