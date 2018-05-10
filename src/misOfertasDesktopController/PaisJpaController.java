/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misOfertasDesktopController;

import MisOfertasDesktopEntities.Pais;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class PaisJpaController implements Serializable {

    public PaisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pais pais) throws PreexistingEntityException, Exception {
        if (pais.getRegionList() == null) {
            pais.setRegionList(new ArrayList<Region>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Region> attachedRegionList = new ArrayList<Region>();
            for (Region regionListRegionToAttach : pais.getRegionList()) {
                regionListRegionToAttach = em.getReference(regionListRegionToAttach.getClass(), regionListRegionToAttach.getIdRegion());
                attachedRegionList.add(regionListRegionToAttach);
            }
            pais.setRegionList(attachedRegionList);
            em.persist(pais);
            for (Region regionListRegion : pais.getRegionList()) {
                Pais oldPaisIdOfRegionListRegion = regionListRegion.getPaisId();
                regionListRegion.setPaisId(pais);
                regionListRegion = em.merge(regionListRegion);
                if (oldPaisIdOfRegionListRegion != null) {
                    oldPaisIdOfRegionListRegion.getRegionList().remove(regionListRegion);
                    oldPaisIdOfRegionListRegion = em.merge(oldPaisIdOfRegionListRegion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPais(pais.getIdPais()) != null) {
                throw new PreexistingEntityException("Pais " + pais + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pais pais) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais persistentPais = em.find(Pais.class, pais.getIdPais());
            List<Region> regionListOld = persistentPais.getRegionList();
            List<Region> regionListNew = pais.getRegionList();
            List<String> illegalOrphanMessages = null;
            for (Region regionListOldRegion : regionListOld) {
                if (!regionListNew.contains(regionListOldRegion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Region " + regionListOldRegion + " since its paisId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Region> attachedRegionListNew = new ArrayList<Region>();
            for (Region regionListNewRegionToAttach : regionListNew) {
                regionListNewRegionToAttach = em.getReference(regionListNewRegionToAttach.getClass(), regionListNewRegionToAttach.getIdRegion());
                attachedRegionListNew.add(regionListNewRegionToAttach);
            }
            regionListNew = attachedRegionListNew;
            pais.setRegionList(regionListNew);
            pais = em.merge(pais);
            for (Region regionListNewRegion : regionListNew) {
                if (!regionListOld.contains(regionListNewRegion)) {
                    Pais oldPaisIdOfRegionListNewRegion = regionListNewRegion.getPaisId();
                    regionListNewRegion.setPaisId(pais);
                    regionListNewRegion = em.merge(regionListNewRegion);
                    if (oldPaisIdOfRegionListNewRegion != null && !oldPaisIdOfRegionListNewRegion.equals(pais)) {
                        oldPaisIdOfRegionListNewRegion.getRegionList().remove(regionListNewRegion);
                        oldPaisIdOfRegionListNewRegion = em.merge(oldPaisIdOfRegionListNewRegion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = pais.getIdPais();
                if (findPais(id) == null) {
                    throw new NonexistentEntityException("The pais with id " + id + " no longer exists.");
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
            Pais pais;
            try {
                pais = em.getReference(Pais.class, id);
                pais.getIdPais();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pais with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Region> regionListOrphanCheck = pais.getRegionList();
            for (Region regionListOrphanCheckRegion : regionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pais (" + pais + ") cannot be destroyed since the Region " + regionListOrphanCheckRegion + " in its regionList field has a non-nullable paisId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pais);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pais> findPaisEntities() {
        return findPaisEntities(true, -1, -1);
    }

    public List<Pais> findPaisEntities(int maxResults, int firstResult) {
        return findPaisEntities(false, maxResults, firstResult);
    }

    private List<Pais> findPaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pais.class));
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

    public Pais findPais(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pais.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pais> rt = cq.from(Pais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
