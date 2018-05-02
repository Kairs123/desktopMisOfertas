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
import MisOfertasDesktopEntities.WsDetalleVentas;
import MisOfertasDesktopEntities.WsVentasRealizadas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
            WsDetalleVentas detalleVenta = wsVentasRealizadas.getDetalleVenta();
            if (detalleVenta != null) {
                detalleVenta = em.getReference(detalleVenta.getClass(), detalleVenta.getDetalleId());
                wsVentasRealizadas.setDetalleVenta(detalleVenta);
            }
            em.persist(wsVentasRealizadas);
            if (detalleVenta != null) {
                detalleVenta.getWsVentasRealizadasList().add(wsVentasRealizadas);
                detalleVenta = em.merge(detalleVenta);
            }
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
            WsVentasRealizadas persistentWsVentasRealizadas = em.find(WsVentasRealizadas.class, wsVentasRealizadas.getVentasWsId());
            WsDetalleVentas detalleVentaOld = persistentWsVentasRealizadas.getDetalleVenta();
            WsDetalleVentas detalleVentaNew = wsVentasRealizadas.getDetalleVenta();
            if (detalleVentaNew != null) {
                detalleVentaNew = em.getReference(detalleVentaNew.getClass(), detalleVentaNew.getDetalleId());
                wsVentasRealizadas.setDetalleVenta(detalleVentaNew);
            }
            wsVentasRealizadas = em.merge(wsVentasRealizadas);
            if (detalleVentaOld != null && !detalleVentaOld.equals(detalleVentaNew)) {
                detalleVentaOld.getWsVentasRealizadasList().remove(wsVentasRealizadas);
                detalleVentaOld = em.merge(detalleVentaOld);
            }
            if (detalleVentaNew != null && !detalleVentaNew.equals(detalleVentaOld)) {
                detalleVentaNew.getWsVentasRealizadasList().add(wsVentasRealizadas);
                detalleVentaNew = em.merge(detalleVentaNew);
            }
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
            WsDetalleVentas detalleVenta = wsVentasRealizadas.getDetalleVenta();
            if (detalleVenta != null) {
                detalleVenta.getWsVentasRealizadasList().remove(wsVentasRealizadas);
                detalleVenta = em.merge(detalleVenta);
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
