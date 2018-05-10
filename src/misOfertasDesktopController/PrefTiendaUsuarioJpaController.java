/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misOfertasDesktopController;

import MisOfertasDesktopEntities.PrefTiendaUsuario;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MisOfertasDesktopEntities.Tienda;
import MisOfertasDesktopEntities.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import misOfertasDesktopController.exceptions.NonexistentEntityException;
import misOfertasDesktopController.exceptions.PreexistingEntityException;

/**
 *
 * @author David
 */
public class PrefTiendaUsuarioJpaController implements Serializable {

    public PrefTiendaUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PrefTiendaUsuario prefTiendaUsuario) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tienda tiendaId = prefTiendaUsuario.getTiendaId();
            if (tiendaId != null) {
                tiendaId = em.getReference(tiendaId.getClass(), tiendaId.getIdTienda());
                prefTiendaUsuario.setTiendaId(tiendaId);
            }
            Usuario usuarioId = prefTiendaUsuario.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getIdUsuario());
                prefTiendaUsuario.setUsuarioId(usuarioId);
            }
            em.persist(prefTiendaUsuario);
            if (tiendaId != null) {
                tiendaId.getPrefTiendaUsuarioList().add(prefTiendaUsuario);
                tiendaId = em.merge(tiendaId);
            }
            if (usuarioId != null) {
                usuarioId.getPrefTiendaUsuarioList().add(prefTiendaUsuario);
                usuarioId = em.merge(usuarioId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPrefTiendaUsuario(prefTiendaUsuario.getIdPrefTienda()) != null) {
                throw new PreexistingEntityException("PrefTiendaUsuario " + prefTiendaUsuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PrefTiendaUsuario prefTiendaUsuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PrefTiendaUsuario persistentPrefTiendaUsuario = em.find(PrefTiendaUsuario.class, prefTiendaUsuario.getIdPrefTienda());
            Tienda tiendaIdOld = persistentPrefTiendaUsuario.getTiendaId();
            Tienda tiendaIdNew = prefTiendaUsuario.getTiendaId();
            Usuario usuarioIdOld = persistentPrefTiendaUsuario.getUsuarioId();
            Usuario usuarioIdNew = prefTiendaUsuario.getUsuarioId();
            if (tiendaIdNew != null) {
                tiendaIdNew = em.getReference(tiendaIdNew.getClass(), tiendaIdNew.getIdTienda());
                prefTiendaUsuario.setTiendaId(tiendaIdNew);
            }
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getIdUsuario());
                prefTiendaUsuario.setUsuarioId(usuarioIdNew);
            }
            prefTiendaUsuario = em.merge(prefTiendaUsuario);
            if (tiendaIdOld != null && !tiendaIdOld.equals(tiendaIdNew)) {
                tiendaIdOld.getPrefTiendaUsuarioList().remove(prefTiendaUsuario);
                tiendaIdOld = em.merge(tiendaIdOld);
            }
            if (tiendaIdNew != null && !tiendaIdNew.equals(tiendaIdOld)) {
                tiendaIdNew.getPrefTiendaUsuarioList().add(prefTiendaUsuario);
                tiendaIdNew = em.merge(tiendaIdNew);
            }
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getPrefTiendaUsuarioList().remove(prefTiendaUsuario);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getPrefTiendaUsuarioList().add(prefTiendaUsuario);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = prefTiendaUsuario.getIdPrefTienda();
                if (findPrefTiendaUsuario(id) == null) {
                    throw new NonexistentEntityException("The prefTiendaUsuario with id " + id + " no longer exists.");
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
            PrefTiendaUsuario prefTiendaUsuario;
            try {
                prefTiendaUsuario = em.getReference(PrefTiendaUsuario.class, id);
                prefTiendaUsuario.getIdPrefTienda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prefTiendaUsuario with id " + id + " no longer exists.", enfe);
            }
            Tienda tiendaId = prefTiendaUsuario.getTiendaId();
            if (tiendaId != null) {
                tiendaId.getPrefTiendaUsuarioList().remove(prefTiendaUsuario);
                tiendaId = em.merge(tiendaId);
            }
            Usuario usuarioId = prefTiendaUsuario.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getPrefTiendaUsuarioList().remove(prefTiendaUsuario);
                usuarioId = em.merge(usuarioId);
            }
            em.remove(prefTiendaUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PrefTiendaUsuario> findPrefTiendaUsuarioEntities() {
        return findPrefTiendaUsuarioEntities(true, -1, -1);
    }

    public List<PrefTiendaUsuario> findPrefTiendaUsuarioEntities(int maxResults, int firstResult) {
        return findPrefTiendaUsuarioEntities(false, maxResults, firstResult);
    }

    private List<PrefTiendaUsuario> findPrefTiendaUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PrefTiendaUsuario.class));
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

    public PrefTiendaUsuario findPrefTiendaUsuario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PrefTiendaUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrefTiendaUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PrefTiendaUsuario> rt = cq.from(PrefTiendaUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
