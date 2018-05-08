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
import MisOfertasDesktopEntities.Oferta;
import MisOfertasDesktopEntities.OfertaConsultadaUsuario;
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
public class OfertaConsultadaUsuarioJpaController implements Serializable {

    public OfertaConsultadaUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OfertaConsultadaUsuario ofertaConsultadaUsuario) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Oferta ofertaId = ofertaConsultadaUsuario.getOfertaId();
            if (ofertaId != null) {
                ofertaId = em.getReference(ofertaId.getClass(), ofertaId.getOfertaId());
                ofertaConsultadaUsuario.setOfertaId(ofertaId);
            }
            Usuario usuarioId = ofertaConsultadaUsuario.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getIdUsuario());
                ofertaConsultadaUsuario.setUsuarioId(usuarioId);
            }
            em.persist(ofertaConsultadaUsuario);
            if (ofertaId != null) {
                ofertaId.getOfertaConsultadaUsuarioList().add(ofertaConsultadaUsuario);
                ofertaId = em.merge(ofertaId);
            }
            if (usuarioId != null) {
                usuarioId.getOfertaConsultadaUsuarioList().add(ofertaConsultadaUsuario);
                usuarioId = em.merge(usuarioId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOfertaConsultadaUsuario(ofertaConsultadaUsuario.getConsultaId()) != null) {
                throw new PreexistingEntityException("OfertaConsultadaUsuario " + ofertaConsultadaUsuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OfertaConsultadaUsuario ofertaConsultadaUsuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OfertaConsultadaUsuario persistentOfertaConsultadaUsuario = em.find(OfertaConsultadaUsuario.class, ofertaConsultadaUsuario.getConsultaId());
            Oferta ofertaIdOld = persistentOfertaConsultadaUsuario.getOfertaId();
            Oferta ofertaIdNew = ofertaConsultadaUsuario.getOfertaId();
            Usuario usuarioIdOld = persistentOfertaConsultadaUsuario.getUsuarioId();
            Usuario usuarioIdNew = ofertaConsultadaUsuario.getUsuarioId();
            if (ofertaIdNew != null) {
                ofertaIdNew = em.getReference(ofertaIdNew.getClass(), ofertaIdNew.getOfertaId());
                ofertaConsultadaUsuario.setOfertaId(ofertaIdNew);
            }
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getIdUsuario());
                ofertaConsultadaUsuario.setUsuarioId(usuarioIdNew);
            }
            ofertaConsultadaUsuario = em.merge(ofertaConsultadaUsuario);
            if (ofertaIdOld != null && !ofertaIdOld.equals(ofertaIdNew)) {
                ofertaIdOld.getOfertaConsultadaUsuarioList().remove(ofertaConsultadaUsuario);
                ofertaIdOld = em.merge(ofertaIdOld);
            }
            if (ofertaIdNew != null && !ofertaIdNew.equals(ofertaIdOld)) {
                ofertaIdNew.getOfertaConsultadaUsuarioList().add(ofertaConsultadaUsuario);
                ofertaIdNew = em.merge(ofertaIdNew);
            }
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getOfertaConsultadaUsuarioList().remove(ofertaConsultadaUsuario);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getOfertaConsultadaUsuarioList().add(ofertaConsultadaUsuario);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = ofertaConsultadaUsuario.getConsultaId();
                if (findOfertaConsultadaUsuario(id) == null) {
                    throw new NonexistentEntityException("The ofertaConsultadaUsuario with id " + id + " no longer exists.");
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
            OfertaConsultadaUsuario ofertaConsultadaUsuario;
            try {
                ofertaConsultadaUsuario = em.getReference(OfertaConsultadaUsuario.class, id);
                ofertaConsultadaUsuario.getConsultaId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ofertaConsultadaUsuario with id " + id + " no longer exists.", enfe);
            }
            Oferta ofertaId = ofertaConsultadaUsuario.getOfertaId();
            if (ofertaId != null) {
                ofertaId.getOfertaConsultadaUsuarioList().remove(ofertaConsultadaUsuario);
                ofertaId = em.merge(ofertaId);
            }
            Usuario usuarioId = ofertaConsultadaUsuario.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getOfertaConsultadaUsuarioList().remove(ofertaConsultadaUsuario);
                usuarioId = em.merge(usuarioId);
            }
            em.remove(ofertaConsultadaUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OfertaConsultadaUsuario> findOfertaConsultadaUsuarioEntities() {
        return findOfertaConsultadaUsuarioEntities(true, -1, -1);
    }

    public List<OfertaConsultadaUsuario> findOfertaConsultadaUsuarioEntities(int maxResults, int firstResult) {
        return findOfertaConsultadaUsuarioEntities(false, maxResults, firstResult);
    }

    private List<OfertaConsultadaUsuario> findOfertaConsultadaUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OfertaConsultadaUsuario.class));
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

    public OfertaConsultadaUsuario findOfertaConsultadaUsuario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OfertaConsultadaUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getOfertaConsultadaUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OfertaConsultadaUsuario> rt = cq.from(OfertaConsultadaUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}