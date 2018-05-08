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
import MisOfertasDesktopEntities.Producto;
import MisOfertasDesktopEntities.Usuario;
import MisOfertasDesktopEntities.Valoracion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import misOfertasDesktopController.exceptions.NonexistentEntityException;
import misOfertasDesktopController.exceptions.PreexistingEntityException;

/**
 *
 * @author David
 */
public class ValoracionJpaController implements Serializable {

    public ValoracionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Valoracion valoracion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto productoId = valoracion.getProductoId();
            if (productoId != null) {
                productoId = em.getReference(productoId.getClass(), productoId.getIdProducto());
                valoracion.setProductoId(productoId);
            }
            Usuario usuarioId = valoracion.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getIdUsuario());
                valoracion.setUsuarioId(usuarioId);
            }
            em.persist(valoracion);
            if (productoId != null) {
                productoId.getValoracionList().add(valoracion);
                productoId = em.merge(productoId);
            }
            if (usuarioId != null) {
                usuarioId.getValoracionList().add(valoracion);
                usuarioId = em.merge(usuarioId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findValoracion(valoracion.getValoracionId()) != null) {
                throw new PreexistingEntityException("Valoracion " + valoracion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Valoracion valoracion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Valoracion persistentValoracion = em.find(Valoracion.class, valoracion.getValoracionId());
            Producto productoIdOld = persistentValoracion.getProductoId();
            Producto productoIdNew = valoracion.getProductoId();
            Usuario usuarioIdOld = persistentValoracion.getUsuarioId();
            Usuario usuarioIdNew = valoracion.getUsuarioId();
            if (productoIdNew != null) {
                productoIdNew = em.getReference(productoIdNew.getClass(), productoIdNew.getIdProducto());
                valoracion.setProductoId(productoIdNew);
            }
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getIdUsuario());
                valoracion.setUsuarioId(usuarioIdNew);
            }
            valoracion = em.merge(valoracion);
            if (productoIdOld != null && !productoIdOld.equals(productoIdNew)) {
                productoIdOld.getValoracionList().remove(valoracion);
                productoIdOld = em.merge(productoIdOld);
            }
            if (productoIdNew != null && !productoIdNew.equals(productoIdOld)) {
                productoIdNew.getValoracionList().add(valoracion);
                productoIdNew = em.merge(productoIdNew);
            }
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getValoracionList().remove(valoracion);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getValoracionList().add(valoracion);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = valoracion.getValoracionId();
                if (findValoracion(id) == null) {
                    throw new NonexistentEntityException("The valoracion with id " + id + " no longer exists.");
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
            Valoracion valoracion;
            try {
                valoracion = em.getReference(Valoracion.class, id);
                valoracion.getValoracionId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The valoracion with id " + id + " no longer exists.", enfe);
            }
            Producto productoId = valoracion.getProductoId();
            if (productoId != null) {
                productoId.getValoracionList().remove(valoracion);
                productoId = em.merge(productoId);
            }
            Usuario usuarioId = valoracion.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getValoracionList().remove(valoracion);
                usuarioId = em.merge(usuarioId);
            }
            em.remove(valoracion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Valoracion> findValoracionEntities() {
        return findValoracionEntities(true, -1, -1);
    }

    public List<Valoracion> findValoracionEntities(int maxResults, int firstResult) {
        return findValoracionEntities(false, maxResults, firstResult);
    }

    private List<Valoracion> findValoracionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Valoracion.class));
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

    public Valoracion findValoracion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Valoracion.class, id);
        } finally {
            em.close();
        }
    }

    public int getValoracionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Valoracion> rt = cq.from(Valoracion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}