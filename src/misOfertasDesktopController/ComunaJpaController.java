/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misOfertasDesktopController;

import MisOfertasDesktopEntities.Comuna;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MisOfertasDesktopEntities.Region;
import MisOfertasDesktopEntities.Tienda;
import java.util.ArrayList;
import java.util.List;
import MisOfertasDesktopEntities.Persona;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import misOfertasDesktopController.exceptions.IllegalOrphanException;
import misOfertasDesktopController.exceptions.NonexistentEntityException;
import misOfertasDesktopController.exceptions.PreexistingEntityException;

/**
 *
 * @author David
 */
public class ComunaJpaController implements Serializable {

    public ComunaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comuna comuna) throws PreexistingEntityException, Exception {
        if (comuna.getTiendaList() == null) {
            comuna.setTiendaList(new ArrayList<Tienda>());
        }
        if (comuna.getPersonaList() == null) {
            comuna.setPersonaList(new ArrayList<Persona>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Region regionId = comuna.getRegionId();
            if (regionId != null) {
                regionId = em.getReference(regionId.getClass(), regionId.getIdRegion());
                comuna.setRegionId(regionId);
            }
            List<Tienda> attachedTiendaList = new ArrayList<Tienda>();
            for (Tienda tiendaListTiendaToAttach : comuna.getTiendaList()) {
                tiendaListTiendaToAttach = em.getReference(tiendaListTiendaToAttach.getClass(), tiendaListTiendaToAttach.getIdTienda());
                attachedTiendaList.add(tiendaListTiendaToAttach);
            }
            comuna.setTiendaList(attachedTiendaList);
            List<Persona> attachedPersonaList = new ArrayList<Persona>();
            for (Persona personaListPersonaToAttach : comuna.getPersonaList()) {
                personaListPersonaToAttach = em.getReference(personaListPersonaToAttach.getClass(), personaListPersonaToAttach.getIdPersona());
                attachedPersonaList.add(personaListPersonaToAttach);
            }
            comuna.setPersonaList(attachedPersonaList);
            em.persist(comuna);
            if (regionId != null) {
                regionId.getComunaList().add(comuna);
                regionId = em.merge(regionId);
            }
            for (Tienda tiendaListTienda : comuna.getTiendaList()) {
                Comuna oldComunaOfTiendaListTienda = tiendaListTienda.getComuna();
                tiendaListTienda.setComuna(comuna);
                tiendaListTienda = em.merge(tiendaListTienda);
                if (oldComunaOfTiendaListTienda != null) {
                    oldComunaOfTiendaListTienda.getTiendaList().remove(tiendaListTienda);
                    oldComunaOfTiendaListTienda = em.merge(oldComunaOfTiendaListTienda);
                }
            }
            for (Persona personaListPersona : comuna.getPersonaList()) {
                Comuna oldComunaIdOfPersonaListPersona = personaListPersona.getComunaId();
                personaListPersona.setComunaId(comuna);
                personaListPersona = em.merge(personaListPersona);
                if (oldComunaIdOfPersonaListPersona != null) {
                    oldComunaIdOfPersonaListPersona.getPersonaList().remove(personaListPersona);
                    oldComunaIdOfPersonaListPersona = em.merge(oldComunaIdOfPersonaListPersona);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComuna(comuna.getIdComuna()) != null) {
                throw new PreexistingEntityException("Comuna " + comuna + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comuna comuna) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comuna persistentComuna = em.find(Comuna.class, comuna.getIdComuna());
            Region regionIdOld = persistentComuna.getRegionId();
            Region regionIdNew = comuna.getRegionId();
            List<Tienda> tiendaListOld = persistentComuna.getTiendaList();
            List<Tienda> tiendaListNew = comuna.getTiendaList();
            List<Persona> personaListOld = persistentComuna.getPersonaList();
            List<Persona> personaListNew = comuna.getPersonaList();
            List<String> illegalOrphanMessages = null;
            for (Tienda tiendaListOldTienda : tiendaListOld) {
                if (!tiendaListNew.contains(tiendaListOldTienda)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tienda " + tiendaListOldTienda + " since its comuna field is not nullable.");
                }
            }
            for (Persona personaListOldPersona : personaListOld) {
                if (!personaListNew.contains(personaListOldPersona)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Persona " + personaListOldPersona + " since its comunaId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (regionIdNew != null) {
                regionIdNew = em.getReference(regionIdNew.getClass(), regionIdNew.getIdRegion());
                comuna.setRegionId(regionIdNew);
            }
            List<Tienda> attachedTiendaListNew = new ArrayList<Tienda>();
            for (Tienda tiendaListNewTiendaToAttach : tiendaListNew) {
                tiendaListNewTiendaToAttach = em.getReference(tiendaListNewTiendaToAttach.getClass(), tiendaListNewTiendaToAttach.getIdTienda());
                attachedTiendaListNew.add(tiendaListNewTiendaToAttach);
            }
            tiendaListNew = attachedTiendaListNew;
            comuna.setTiendaList(tiendaListNew);
            List<Persona> attachedPersonaListNew = new ArrayList<Persona>();
            for (Persona personaListNewPersonaToAttach : personaListNew) {
                personaListNewPersonaToAttach = em.getReference(personaListNewPersonaToAttach.getClass(), personaListNewPersonaToAttach.getIdPersona());
                attachedPersonaListNew.add(personaListNewPersonaToAttach);
            }
            personaListNew = attachedPersonaListNew;
            comuna.setPersonaList(personaListNew);
            comuna = em.merge(comuna);
            if (regionIdOld != null && !regionIdOld.equals(regionIdNew)) {
                regionIdOld.getComunaList().remove(comuna);
                regionIdOld = em.merge(regionIdOld);
            }
            if (regionIdNew != null && !regionIdNew.equals(regionIdOld)) {
                regionIdNew.getComunaList().add(comuna);
                regionIdNew = em.merge(regionIdNew);
            }
            for (Tienda tiendaListNewTienda : tiendaListNew) {
                if (!tiendaListOld.contains(tiendaListNewTienda)) {
                    Comuna oldComunaOfTiendaListNewTienda = tiendaListNewTienda.getComuna();
                    tiendaListNewTienda.setComuna(comuna);
                    tiendaListNewTienda = em.merge(tiendaListNewTienda);
                    if (oldComunaOfTiendaListNewTienda != null && !oldComunaOfTiendaListNewTienda.equals(comuna)) {
                        oldComunaOfTiendaListNewTienda.getTiendaList().remove(tiendaListNewTienda);
                        oldComunaOfTiendaListNewTienda = em.merge(oldComunaOfTiendaListNewTienda);
                    }
                }
            }
            for (Persona personaListNewPersona : personaListNew) {
                if (!personaListOld.contains(personaListNewPersona)) {
                    Comuna oldComunaIdOfPersonaListNewPersona = personaListNewPersona.getComunaId();
                    personaListNewPersona.setComunaId(comuna);
                    personaListNewPersona = em.merge(personaListNewPersona);
                    if (oldComunaIdOfPersonaListNewPersona != null && !oldComunaIdOfPersonaListNewPersona.equals(comuna)) {
                        oldComunaIdOfPersonaListNewPersona.getPersonaList().remove(personaListNewPersona);
                        oldComunaIdOfPersonaListNewPersona = em.merge(oldComunaIdOfPersonaListNewPersona);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = comuna.getIdComuna();
                if (findComuna(id) == null) {
                    throw new NonexistentEntityException("The comuna with id " + id + " no longer exists.");
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
            Comuna comuna;
            try {
                comuna = em.getReference(Comuna.class, id);
                comuna.getIdComuna();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comuna with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Tienda> tiendaListOrphanCheck = comuna.getTiendaList();
            for (Tienda tiendaListOrphanCheckTienda : tiendaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Comuna (" + comuna + ") cannot be destroyed since the Tienda " + tiendaListOrphanCheckTienda + " in its tiendaList field has a non-nullable comuna field.");
            }
            List<Persona> personaListOrphanCheck = comuna.getPersonaList();
            for (Persona personaListOrphanCheckPersona : personaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Comuna (" + comuna + ") cannot be destroyed since the Persona " + personaListOrphanCheckPersona + " in its personaList field has a non-nullable comunaId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Region regionId = comuna.getRegionId();
            if (regionId != null) {
                regionId.getComunaList().remove(comuna);
                regionId = em.merge(regionId);
            }
            em.remove(comuna);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comuna> findComunaEntities() {
        return findComunaEntities(true, -1, -1);
    }

    public List<Comuna> findComunaEntities(int maxResults, int firstResult) {
        return findComunaEntities(false, maxResults, firstResult);
    }

    private List<Comuna> findComunaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comuna.class));
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

    public Comuna findComuna(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comuna.class, id);
        } finally {
            em.close();
        }
    }

    public int getComunaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comuna> rt = cq.from(Comuna.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
