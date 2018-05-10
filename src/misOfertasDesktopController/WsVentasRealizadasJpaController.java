/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misOfertasDesktopController;

import MisOfertasDesktopEntities.WsVentasRealizadas;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import misOfertasDesktopController.exceptions.NonexistentEntityException;
import misOfertasDesktopController.exceptions.PreexistingEntityException;

/**
 *
 * @author David
 */
public class WsVentasRealizadasJpaController implements Serializable {

    public WsVentasRealizadasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(WsVentasRealizadas wsVentasRealizadas) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(wsVentasRealizadas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findWsVentasRealizadas(wsVentasRealizadas.getVentasWsId()) != null) {
                throw new PreexistingEntityException("WsVentasRealizadas " + wsVentasRealizadas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(WsVentasRealizadas wsVentasRealizadas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            wsVentasRealizadas = em.merge(wsVentasRealizadas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = wsVentasRealizadas.getVentasWsId();
                if (findWsVentasRealizadas(id) == null) {
                    throw new NonexistentEntityException("The wsVentasRealizadas with id " + id + " no longer exists.");
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
            WsVentasRealizadas wsVentasRealizadas;
            try {
                wsVentasRealizadas = em.getReference(WsVentasRealizadas.class, id);
                wsVentasRealizadas.getVentasWsId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The wsVentasRealizadas with id " + id + " no longer exists.", enfe);
            }
            em.remove(wsVentasRealizadas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<WsVentasRealizadas> findWsVentasRealizadasEntities() {
        return findWsVentasRealizadasEntities(true, -1, -1);
    }

    public List<WsVentasRealizadas> findWsVentasRealizadasEntities(int maxResults, int firstResult) {
        return findWsVentasRealizadasEntities(false, maxResults, firstResult);
    }

    private List<WsVentasRealizadas> findWsVentasRealizadasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(WsVentasRealizadas.class));
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

    public WsVentasRealizadas findWsVentasRealizadas(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(WsVentasRealizadas.class, id);
        } finally {
            em.close();
        }
    }

    public int getWsVentasRealizadasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<WsVentasRealizadas> rt = cq.from(WsVentasRealizadas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
