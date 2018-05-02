/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misOfertasDesktopController;

import MisOfertasDesktopEntities.Preferencias;
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
public class PreferenciasJpaController implements Serializable {

    public PreferenciasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Preferencias preferencias) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rubro rubroId = preferencias.getRubroId();
            if (rubroId != null) {
                rubroId = em.getReference(rubroId.getClass(), rubroId.getIdRubro());
                preferencias.setRubroId(rubroId);
            }
            Usuario usuarioId = preferencias.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getUsuarioId());
                preferencias.setUsuarioId(usuarioId);
            }
            em.persist(preferencias);
            if (rubroId != null) {
                rubroId.getPreferenciasList().add(preferencias);
                rubroId = em.merge(rubroId);
            }
            if (usuarioId != null) {
                usuarioId.getPreferenciasList().add(preferencias);
                usuarioId = em.merge(usuarioId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPreferencias(preferencias.getPreferenciaId()) != null) {
                throw new PreexistingEntityException("Preferencias " + preferencias + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Preferencias preferencias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Preferencias persistentPreferencias = em.find(Preferencias.class, preferencias.getPreferenciaId());
            Rubro rubroIdOld = persistentPreferencias.getRubroId();
            Rubro rubroIdNew = preferencias.getRubroId();
            Usuario usuarioIdOld = persistentPreferencias.getUsuarioId();
            Usuario usuarioIdNew = preferencias.getUsuarioId();
            if (rubroIdNew != null) {
                rubroIdNew = em.getReference(rubroIdNew.getClass(), rubroIdNew.getIdRubro());
                preferencias.setRubroId(rubroIdNew);
            }
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getUsuarioId());
                preferencias.setUsuarioId(usuarioIdNew);
            }
            preferencias = em.merge(preferencias);
            if (rubroIdOld != null && !rubroIdOld.equals(rubroIdNew)) {
                rubroIdOld.getPreferenciasList().remove(preferencias);
                rubroIdOld = em.merge(rubroIdOld);
            }
            if (rubroIdNew != null && !rubroIdNew.equals(rubroIdOld)) {
                rubroIdNew.getPreferenciasList().add(preferencias);
                rubroIdNew = em.merge(rubroIdNew);
            }
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getPreferenciasList().remove(preferencias);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getPreferenciasList().add(preferencias);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = preferencias.getPreferenciaId();
                if (findPreferencias(id) == null) {
                    throw new NonexistentEntityException("The preferencias with id " + id + " no longer exists.");
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
            Preferencias preferencias;
            try {
                preferencias = em.getReference(Preferencias.class, id);
                preferencias.getPreferenciaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preferencias with id " + id + " no longer exists.", enfe);
            }
            Rubro rubroId = preferencias.getRubroId();
            if (rubroId != null) {
                rubroId.getPreferenciasList().remove(preferencias);
                rubroId = em.merge(rubroId);
            }
            Usuario usuarioId = preferencias.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getPreferenciasList().remove(preferencias);
                usuarioId = em.merge(usuarioId);
            }
            em.remove(preferencias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Preferencias> findPreferenciasEntities() {
        return findPreferenciasEntities(true, -1, -1);
    }

    public List<Preferencias> findPreferenciasEntities(int maxResults, int firstResult) {
        return findPreferenciasEntities(false, maxResults, firstResult);
    }

    private List<Preferencias> findPreferenciasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Preferencias.class));
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

    public Preferencias findPreferencias(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Preferencias.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreferenciasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Preferencias> rt = cq.from(Preferencias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
