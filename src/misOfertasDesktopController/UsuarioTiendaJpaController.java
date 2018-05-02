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
import MisOfertasDesktopEntities.Tienda;
import MisOfertasDesktopEntities.Usuario;
import MisOfertasDesktopEntities.UsuarioTienda;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import misOfertasDesktopController.exceptions.NonexistentEntityException;
import misOfertasDesktopController.exceptions.PreexistingEntityException;

/**
 *
 * @author David
 */
public class UsuarioTiendaJpaController implements Serializable {

    public UsuarioTiendaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuarioTienda usuarioTienda) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tienda tiendaId = usuarioTienda.getTiendaId();
            if (tiendaId != null) {
                tiendaId = em.getReference(tiendaId.getClass(), tiendaId.getIdTienda());
                usuarioTienda.setTiendaId(tiendaId);
            }
            Usuario usuarioId = usuarioTienda.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getUsuarioId());
                usuarioTienda.setUsuarioId(usuarioId);
            }
            em.persist(usuarioTienda);
            if (tiendaId != null) {
                tiendaId.getUsuarioTiendaList().add(usuarioTienda);
                tiendaId = em.merge(tiendaId);
            }
            if (usuarioId != null) {
                usuarioId.getUsuarioTiendaList().add(usuarioTienda);
                usuarioId = em.merge(usuarioId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuarioTienda(usuarioTienda.getUsuarioTiendaId()) != null) {
                throw new PreexistingEntityException("UsuarioTienda " + usuarioTienda + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuarioTienda usuarioTienda) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsuarioTienda persistentUsuarioTienda = em.find(UsuarioTienda.class, usuarioTienda.getUsuarioTiendaId());
            Tienda tiendaIdOld = persistentUsuarioTienda.getTiendaId();
            Tienda tiendaIdNew = usuarioTienda.getTiendaId();
            Usuario usuarioIdOld = persistentUsuarioTienda.getUsuarioId();
            Usuario usuarioIdNew = usuarioTienda.getUsuarioId();
            if (tiendaIdNew != null) {
                tiendaIdNew = em.getReference(tiendaIdNew.getClass(), tiendaIdNew.getIdTienda());
                usuarioTienda.setTiendaId(tiendaIdNew);
            }
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getUsuarioId());
                usuarioTienda.setUsuarioId(usuarioIdNew);
            }
            usuarioTienda = em.merge(usuarioTienda);
            if (tiendaIdOld != null && !tiendaIdOld.equals(tiendaIdNew)) {
                tiendaIdOld.getUsuarioTiendaList().remove(usuarioTienda);
                tiendaIdOld = em.merge(tiendaIdOld);
            }
            if (tiendaIdNew != null && !tiendaIdNew.equals(tiendaIdOld)) {
                tiendaIdNew.getUsuarioTiendaList().add(usuarioTienda);
                tiendaIdNew = em.merge(tiendaIdNew);
            }
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getUsuarioTiendaList().remove(usuarioTienda);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getUsuarioTiendaList().add(usuarioTienda);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = usuarioTienda.getUsuarioTiendaId();
                if (findUsuarioTienda(id) == null) {
                    throw new NonexistentEntityException("The usuarioTienda with id " + id + " no longer exists.");
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
            UsuarioTienda usuarioTienda;
            try {
                usuarioTienda = em.getReference(UsuarioTienda.class, id);
                usuarioTienda.getUsuarioTiendaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioTienda with id " + id + " no longer exists.", enfe);
            }
            Tienda tiendaId = usuarioTienda.getTiendaId();
            if (tiendaId != null) {
                tiendaId.getUsuarioTiendaList().remove(usuarioTienda);
                tiendaId = em.merge(tiendaId);
            }
            Usuario usuarioId = usuarioTienda.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getUsuarioTiendaList().remove(usuarioTienda);
                usuarioId = em.merge(usuarioId);
            }
            em.remove(usuarioTienda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UsuarioTienda> findUsuarioTiendaEntities() {
        return findUsuarioTiendaEntities(true, -1, -1);
    }

    public List<UsuarioTienda> findUsuarioTiendaEntities(int maxResults, int firstResult) {
        return findUsuarioTiendaEntities(false, maxResults, firstResult);
    }

    private List<UsuarioTienda> findUsuarioTiendaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuarioTienda.class));
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

    public UsuarioTienda findUsuarioTienda(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuarioTienda.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioTiendaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuarioTienda> rt = cq.from(UsuarioTienda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
