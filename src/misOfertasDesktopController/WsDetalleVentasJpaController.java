/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misOfertasDesktopController;

import MisOfertasDesktopEntities.WsDetalleVentas;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class WsDetalleVentasJpaController implements Serializable {

    public WsDetalleVentasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(WsDetalleVentas wsDetalleVentas) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(wsDetalleVentas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findWsDetalleVentas(wsDetalleVentas.getDetalleId()) != null) {
                throw new PreexistingEntityException("WsDetalleVentas " + wsDetalleVentas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(WsDetalleVentas wsDetalleVentas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            wsDetalleVentas = em.merge(wsDetalleVentas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = wsDetalleVentas.getDetalleId();
                if (findWsDetalleVentas(id) == null) {
                    throw new NonexistentEntityException("The wsDetalleVentas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            WsDetalleVentas wsDetalleVentas;
            try {
                wsDetalleVentas = em.getReference(WsDetalleVentas.class, id);
                wsDetalleVentas.getDetalleId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The wsDetalleVentas with id " + id + " no longer exists.", enfe);
            }
            em.remove(wsDetalleVentas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<WsDetalleVentas> findWsDetalleVentasEntities() {
        return findWsDetalleVentasEntities(true, -1, -1);
    }

    public List<WsDetalleVentas> findWsDetalleVentasEntities(int maxResults, int firstResult) {
        return findWsDetalleVentasEntities(false, maxResults, firstResult);
    }

    private List<WsDetalleVentas> findWsDetalleVentasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(WsDetalleVentas.class));
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

    public WsDetalleVentas findWsDetalleVentas(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(WsDetalleVentas.class, id);
        } finally {
            em.close();
        }
    }

    public int getWsDetalleVentasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<WsDetalleVentas> rt = cq.from(WsDetalleVentas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
