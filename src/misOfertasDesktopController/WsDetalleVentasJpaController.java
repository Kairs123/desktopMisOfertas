/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misOfertasDesktopController;

import MisOfertasDesktopEntities.WsDetalleVentas;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MisOfertasDesktopEntities.WsVentasRealizadas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import misOfertasDesktopController.exceptions.IllegalOrphanException;
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
        if (wsDetalleVentas.getWsVentasRealizadasList() == null) {
            wsDetalleVentas.setWsVentasRealizadasList(new ArrayList<WsVentasRealizadas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<WsVentasRealizadas> attachedWsVentasRealizadasList = new ArrayList<WsVentasRealizadas>();
            for (WsVentasRealizadas wsVentasRealizadasListWsVentasRealizadasToAttach : wsDetalleVentas.getWsVentasRealizadasList()) {
                wsVentasRealizadasListWsVentasRealizadasToAttach = em.getReference(wsVentasRealizadasListWsVentasRealizadasToAttach.getClass(), wsVentasRealizadasListWsVentasRealizadasToAttach.getVentasWsId());
                attachedWsVentasRealizadasList.add(wsVentasRealizadasListWsVentasRealizadasToAttach);
            }
            wsDetalleVentas.setWsVentasRealizadasList(attachedWsVentasRealizadasList);
            em.persist(wsDetalleVentas);
            for (WsVentasRealizadas wsVentasRealizadasListWsVentasRealizadas : wsDetalleVentas.getWsVentasRealizadasList()) {
                WsDetalleVentas oldDetalleVentaOfWsVentasRealizadasListWsVentasRealizadas = wsVentasRealizadasListWsVentasRealizadas.getDetalleVenta();
                wsVentasRealizadasListWsVentasRealizadas.setDetalleVenta(wsDetalleVentas);
                wsVentasRealizadasListWsVentasRealizadas = em.merge(wsVentasRealizadasListWsVentasRealizadas);
                if (oldDetalleVentaOfWsVentasRealizadasListWsVentasRealizadas != null) {
                    oldDetalleVentaOfWsVentasRealizadasListWsVentasRealizadas.getWsVentasRealizadasList().remove(wsVentasRealizadasListWsVentasRealizadas);
                    oldDetalleVentaOfWsVentasRealizadasListWsVentasRealizadas = em.merge(oldDetalleVentaOfWsVentasRealizadasListWsVentasRealizadas);
                }
            }
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

    public void edit(WsDetalleVentas wsDetalleVentas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            WsDetalleVentas persistentWsDetalleVentas = em.find(WsDetalleVentas.class, wsDetalleVentas.getDetalleId());
            List<WsVentasRealizadas> wsVentasRealizadasListOld = persistentWsDetalleVentas.getWsVentasRealizadasList();
            List<WsVentasRealizadas> wsVentasRealizadasListNew = wsDetalleVentas.getWsVentasRealizadasList();
            List<String> illegalOrphanMessages = null;
            for (WsVentasRealizadas wsVentasRealizadasListOldWsVentasRealizadas : wsVentasRealizadasListOld) {
                if (!wsVentasRealizadasListNew.contains(wsVentasRealizadasListOldWsVentasRealizadas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain WsVentasRealizadas " + wsVentasRealizadasListOldWsVentasRealizadas + " since its detalleVenta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<WsVentasRealizadas> attachedWsVentasRealizadasListNew = new ArrayList<WsVentasRealizadas>();
            for (WsVentasRealizadas wsVentasRealizadasListNewWsVentasRealizadasToAttach : wsVentasRealizadasListNew) {
                wsVentasRealizadasListNewWsVentasRealizadasToAttach = em.getReference(wsVentasRealizadasListNewWsVentasRealizadasToAttach.getClass(), wsVentasRealizadasListNewWsVentasRealizadasToAttach.getVentasWsId());
                attachedWsVentasRealizadasListNew.add(wsVentasRealizadasListNewWsVentasRealizadasToAttach);
            }
            wsVentasRealizadasListNew = attachedWsVentasRealizadasListNew;
            wsDetalleVentas.setWsVentasRealizadasList(wsVentasRealizadasListNew);
            wsDetalleVentas = em.merge(wsDetalleVentas);
            for (WsVentasRealizadas wsVentasRealizadasListNewWsVentasRealizadas : wsVentasRealizadasListNew) {
                if (!wsVentasRealizadasListOld.contains(wsVentasRealizadasListNewWsVentasRealizadas)) {
                    WsDetalleVentas oldDetalleVentaOfWsVentasRealizadasListNewWsVentasRealizadas = wsVentasRealizadasListNewWsVentasRealizadas.getDetalleVenta();
                    wsVentasRealizadasListNewWsVentasRealizadas.setDetalleVenta(wsDetalleVentas);
                    wsVentasRealizadasListNewWsVentasRealizadas = em.merge(wsVentasRealizadasListNewWsVentasRealizadas);
                    if (oldDetalleVentaOfWsVentasRealizadasListNewWsVentasRealizadas != null && !oldDetalleVentaOfWsVentasRealizadasListNewWsVentasRealizadas.equals(wsDetalleVentas)) {
                        oldDetalleVentaOfWsVentasRealizadasListNewWsVentasRealizadas.getWsVentasRealizadasList().remove(wsVentasRealizadasListNewWsVentasRealizadas);
                        oldDetalleVentaOfWsVentasRealizadasListNewWsVentasRealizadas = em.merge(oldDetalleVentaOfWsVentasRealizadasListNewWsVentasRealizadas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = wsDetalleVentas.getDetalleId();
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

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            List<WsVentasRealizadas> wsVentasRealizadasListOrphanCheck = wsDetalleVentas.getWsVentasRealizadasList();
            for (WsVentasRealizadas wsVentasRealizadasListOrphanCheckWsVentasRealizadas : wsVentasRealizadasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This WsDetalleVentas (" + wsDetalleVentas + ") cannot be destroyed since the WsVentasRealizadas " + wsVentasRealizadasListOrphanCheckWsVentasRealizadas + " in its wsVentasRealizadasList field has a non-nullable detalleVenta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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

    public WsDetalleVentas findWsDetalleVentas(Long id) {
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
