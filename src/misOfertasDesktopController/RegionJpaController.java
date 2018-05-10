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
import MisOfertasDesktopEntities.Pais;
import MisOfertasDesktopEntities.Comuna;
import MisOfertasDesktopEntities.Region;
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
public class RegionJpaController implements Serializable {

    public RegionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Region region) throws PreexistingEntityException, Exception {
        if (region.getComunaList() == null) {
            region.setComunaList(new ArrayList<Comuna>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais paisId = region.getPaisId();
            if (paisId != null) {
                paisId = em.getReference(paisId.getClass(), paisId.getIdPais());
                region.setPaisId(paisId);
            }
            List<Comuna> attachedComunaList = new ArrayList<Comuna>();
            for (Comuna comunaListComunaToAttach : region.getComunaList()) {
                comunaListComunaToAttach = em.getReference(comunaListComunaToAttach.getClass(), comunaListComunaToAttach.getIdComuna());
                attachedComunaList.add(comunaListComunaToAttach);
            }
            region.setComunaList(attachedComunaList);
            em.persist(region);
            if (paisId != null) {
                paisId.getRegionList().add(region);
                paisId = em.merge(paisId);
            }
            for (Comuna comunaListComuna : region.getComunaList()) {
                Region oldRegionIdOfComunaListComuna = comunaListComuna.getRegionId();
                comunaListComuna.setRegionId(region);
                comunaListComuna = em.merge(comunaListComuna);
                if (oldRegionIdOfComunaListComuna != null) {
                    oldRegionIdOfComunaListComuna.getComunaList().remove(comunaListComuna);
                    oldRegionIdOfComunaListComuna = em.merge(oldRegionIdOfComunaListComuna);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRegion(region.getIdRegion()) != null) {
                throw new PreexistingEntityException("Region " + region + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Region region) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Region persistentRegion = em.find(Region.class, region.getIdRegion());
            Pais paisIdOld = persistentRegion.getPaisId();
            Pais paisIdNew = region.getPaisId();
            List<Comuna> comunaListOld = persistentRegion.getComunaList();
            List<Comuna> comunaListNew = region.getComunaList();
            List<String> illegalOrphanMessages = null;
            for (Comuna comunaListOldComuna : comunaListOld) {
                if (!comunaListNew.contains(comunaListOldComuna)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comuna " + comunaListOldComuna + " since its regionId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (paisIdNew != null) {
                paisIdNew = em.getReference(paisIdNew.getClass(), paisIdNew.getIdPais());
                region.setPaisId(paisIdNew);
            }
            List<Comuna> attachedComunaListNew = new ArrayList<Comuna>();
            for (Comuna comunaListNewComunaToAttach : comunaListNew) {
                comunaListNewComunaToAttach = em.getReference(comunaListNewComunaToAttach.getClass(), comunaListNewComunaToAttach.getIdComuna());
                attachedComunaListNew.add(comunaListNewComunaToAttach);
            }
            comunaListNew = attachedComunaListNew;
            region.setComunaList(comunaListNew);
            region = em.merge(region);
            if (paisIdOld != null && !paisIdOld.equals(paisIdNew)) {
                paisIdOld.getRegionList().remove(region);
                paisIdOld = em.merge(paisIdOld);
            }
            if (paisIdNew != null && !paisIdNew.equals(paisIdOld)) {
                paisIdNew.getRegionList().add(region);
                paisIdNew = em.merge(paisIdNew);
            }
            for (Comuna comunaListNewComuna : comunaListNew) {
                if (!comunaListOld.contains(comunaListNewComuna)) {
                    Region oldRegionIdOfComunaListNewComuna = comunaListNewComuna.getRegionId();
                    comunaListNewComuna.setRegionId(region);
                    comunaListNewComuna = em.merge(comunaListNewComuna);
                    if (oldRegionIdOfComunaListNewComuna != null && !oldRegionIdOfComunaListNewComuna.equals(region)) {
                        oldRegionIdOfComunaListNewComuna.getComunaList().remove(comunaListNewComuna);
                        oldRegionIdOfComunaListNewComuna = em.merge(oldRegionIdOfComunaListNewComuna);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = region.getIdRegion();
                if (findRegion(id) == null) {
                    throw new NonexistentEntityException("The region with id " + id + " no longer exists.");
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
            Region region;
            try {
                region = em.getReference(Region.class, id);
                region.getIdRegion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The region with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Comuna> comunaListOrphanCheck = region.getComunaList();
            for (Comuna comunaListOrphanCheckComuna : comunaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Region (" + region + ") cannot be destroyed since the Comuna " + comunaListOrphanCheckComuna + " in its comunaList field has a non-nullable regionId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pais paisId = region.getPaisId();
            if (paisId != null) {
                paisId.getRegionList().remove(region);
                paisId = em.merge(paisId);
            }
            em.remove(region);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Region> findRegionEntities() {
        return findRegionEntities(true, -1, -1);
    }

    public List<Region> findRegionEntities(int maxResults, int firstResult) {
        return findRegionEntities(false, maxResults, firstResult);
    }

    private List<Region> findRegionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Region.class));
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

    public Region findRegion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Region.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Region> rt = cq.from(Region.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
