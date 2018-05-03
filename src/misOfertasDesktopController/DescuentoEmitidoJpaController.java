/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misOfertasDesktopController;

import MisOfertasDesktopEntities.DescuentoEmitido;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MisOfertasDesktopEntities.Producto;
import MisOfertasDesktopEntities.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import misOfertasDesktopController.exceptions.exceptions.NonexistentEntityException;
import misOfertasDesktopController.exceptions.exceptions.PreexistingEntityException;

/**
 *
 * @author David
 */
public class DescuentoEmitidoJpaController implements Serializable {

    public DescuentoEmitidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DescuentoEmitido descuentoEmitido) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto productoId = descuentoEmitido.getProductoId();
            if (productoId != null) {
                productoId = em.getReference(productoId.getClass(), productoId.getProductoId());
                descuentoEmitido.setProductoId(productoId);
            }
            Usuario usuarioId = descuentoEmitido.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getIdUsuario());
                descuentoEmitido.setUsuarioId(usuarioId);
            }
            em.persist(descuentoEmitido);
            if (productoId != null) {
                productoId.getDescuentoEmitidoList().add(descuentoEmitido);
                productoId = em.merge(productoId);
            }
            if (usuarioId != null) {
                usuarioId.getDescuentoEmitidoList().add(descuentoEmitido);
                usuarioId = em.merge(usuarioId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDescuentoEmitido(descuentoEmitido.getDescuentoId()) != null) {
                throw new PreexistingEntityException("DescuentoEmitido " + descuentoEmitido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DescuentoEmitido descuentoEmitido) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DescuentoEmitido persistentDescuentoEmitido = em.find(DescuentoEmitido.class, descuentoEmitido.getDescuentoId());
            Producto productoIdOld = persistentDescuentoEmitido.getProductoId();
            Producto productoIdNew = descuentoEmitido.getProductoId();
            Usuario usuarioIdOld = persistentDescuentoEmitido.getUsuarioId();
            Usuario usuarioIdNew = descuentoEmitido.getUsuarioId();
            if (productoIdNew != null) {
                productoIdNew = em.getReference(productoIdNew.getClass(), productoIdNew.getProductoId());
                descuentoEmitido.setProductoId(productoIdNew);
            }
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getIdUsuario());
                descuentoEmitido.setUsuarioId(usuarioIdNew);
            }
            descuentoEmitido = em.merge(descuentoEmitido);
            if (productoIdOld != null && !productoIdOld.equals(productoIdNew)) {
                productoIdOld.getDescuentoEmitidoList().remove(descuentoEmitido);
                productoIdOld = em.merge(productoIdOld);
            }
            if (productoIdNew != null && !productoIdNew.equals(productoIdOld)) {
                productoIdNew.getDescuentoEmitidoList().add(descuentoEmitido);
                productoIdNew = em.merge(productoIdNew);
            }
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getDescuentoEmitidoList().remove(descuentoEmitido);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getDescuentoEmitidoList().add(descuentoEmitido);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = descuentoEmitido.getDescuentoId();
                if (findDescuentoEmitido(id) == null) {
                    throw new NonexistentEntityException("The descuentoEmitido with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DescuentoEmitido descuentoEmitido;
            try {
                descuentoEmitido = em.getReference(DescuentoEmitido.class, id);
                descuentoEmitido.getDescuentoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The descuentoEmitido with id " + id + " no longer exists.", enfe);
            }
            Producto productoId = descuentoEmitido.getProductoId();
            if (productoId != null) {
                productoId.getDescuentoEmitidoList().remove(descuentoEmitido);
                productoId = em.merge(productoId);
            }
            Usuario usuarioId = descuentoEmitido.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getDescuentoEmitidoList().remove(descuentoEmitido);
                usuarioId = em.merge(usuarioId);
            }
            em.remove(descuentoEmitido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DescuentoEmitido> findDescuentoEmitidoEntities() {
        return findDescuentoEmitidoEntities(true, -1, -1);
    }

    public List<DescuentoEmitido> findDescuentoEmitidoEntities(int maxResults, int firstResult) {
        return findDescuentoEmitidoEntities(false, maxResults, firstResult);
    }

    private List<DescuentoEmitido> findDescuentoEmitidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DescuentoEmitido.class));
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

    public DescuentoEmitido findDescuentoEmitido(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DescuentoEmitido.class, id);
        } finally {
            em.close();
        }
    }

    public int getDescuentoEmitidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DescuentoEmitido> rt = cq.from(DescuentoEmitido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
