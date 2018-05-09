/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misOfertasDesktopController;

import MisOfertasDesktopEntities.PrefRubroUsuario;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MisOfertasDesktopEntities.Rubro;
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
public class PrefRubroUsuarioJpaController implements Serializable {

    public PrefRubroUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PrefRubroUsuario prefRubroUsuario) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rubro rubroId = prefRubroUsuario.getRubroId();
            if (rubroId != null) {
                rubroId = em.getReference(rubroId.getClass(), rubroId.getIdRubro());
                prefRubroUsuario.setRubroId(rubroId);
            }
            Usuario usuarioId = prefRubroUsuario.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getIdUsuario());
                prefRubroUsuario.setUsuarioId(usuarioId);
            }
            em.persist(prefRubroUsuario);
            if (rubroId != null) {
                rubroId.getPrefRubroUsuarioList().add(prefRubroUsuario);
                rubroId = em.merge(rubroId);
            }
            if (usuarioId != null) {
                usuarioId.getPrefRubroUsuarioList().add(prefRubroUsuario);
                usuarioId = em.merge(usuarioId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPrefRubroUsuario(prefRubroUsuario.getIdPrefRubro()) != null) {
                throw new PreexistingEntityException("PrefRubroUsuario " + prefRubroUsuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PrefRubroUsuario prefRubroUsuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PrefRubroUsuario persistentPrefRubroUsuario = em.find(PrefRubroUsuario.class, prefRubroUsuario.getIdPrefRubro());
            Rubro rubroIdOld = persistentPrefRubroUsuario.getRubroId();
            Rubro rubroIdNew = prefRubroUsuario.getRubroId();
            Usuario usuarioIdOld = persistentPrefRubroUsuario.getUsuarioId();
            Usuario usuarioIdNew = prefRubroUsuario.getUsuarioId();
            if (rubroIdNew != null) {
                rubroIdNew = em.getReference(rubroIdNew.getClass(), rubroIdNew.getIdRubro());
                prefRubroUsuario.setRubroId(rubroIdNew);
            }
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getIdUsuario());
                prefRubroUsuario.setUsuarioId(usuarioIdNew);
            }
            prefRubroUsuario = em.merge(prefRubroUsuario);
            if (rubroIdOld != null && !rubroIdOld.equals(rubroIdNew)) {
                rubroIdOld.getPrefRubroUsuarioList().remove(prefRubroUsuario);
                rubroIdOld = em.merge(rubroIdOld);
            }
            if (rubroIdNew != null && !rubroIdNew.equals(rubroIdOld)) {
                rubroIdNew.getPrefRubroUsuarioList().add(prefRubroUsuario);
                rubroIdNew = em.merge(rubroIdNew);
            }
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getPrefRubroUsuarioList().remove(prefRubroUsuario);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getPrefRubroUsuarioList().add(prefRubroUsuario);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = prefRubroUsuario.getIdPrefRubro();
                if (findPrefRubroUsuario(id) == null) {
                    throw new NonexistentEntityException("The prefRubroUsuario with id " + id + " no longer exists.");
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
            PrefRubroUsuario prefRubroUsuario;
            try {
                prefRubroUsuario = em.getReference(PrefRubroUsuario.class, id);
                prefRubroUsuario.getIdPrefRubro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prefRubroUsuario with id " + id + " no longer exists.", enfe);
            }
            Rubro rubroId = prefRubroUsuario.getRubroId();
            if (rubroId != null) {
                rubroId.getPrefRubroUsuarioList().remove(prefRubroUsuario);
                rubroId = em.merge(rubroId);
            }
            Usuario usuarioId = prefRubroUsuario.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getPrefRubroUsuarioList().remove(prefRubroUsuario);
                usuarioId = em.merge(usuarioId);
            }
            em.remove(prefRubroUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PrefRubroUsuario> findPrefRubroUsuarioEntities() {
        return findPrefRubroUsuarioEntities(true, -1, -1);
    }

    public List<PrefRubroUsuario> findPrefRubroUsuarioEntities(int maxResults, int firstResult) {
        return findPrefRubroUsuarioEntities(false, maxResults, firstResult);
    }

    private List<PrefRubroUsuario> findPrefRubroUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PrefRubroUsuario.class));
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

    public PrefRubroUsuario findPrefRubroUsuario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PrefRubroUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrefRubroUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PrefRubroUsuario> rt = cq.from(PrefRubroUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
