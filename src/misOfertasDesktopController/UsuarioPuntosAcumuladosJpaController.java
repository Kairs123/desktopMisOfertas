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
import MisOfertasDesktopEntities.Usuario;
import MisOfertasDesktopEntities.UsuarioPuntosAcumulados;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import misOfertasDesktopController.exceptions.NonexistentEntityException;
import misOfertasDesktopController.exceptions.PreexistingEntityException;

/**
 *
 * @author David
 */
public class UsuarioPuntosAcumuladosJpaController implements Serializable {

    public UsuarioPuntosAcumuladosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuarioPuntosAcumulados usuarioPuntosAcumulados) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuarioId = usuarioPuntosAcumulados.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getIdUsuario());
                usuarioPuntosAcumulados.setUsuarioId(usuarioId);
            }
            em.persist(usuarioPuntosAcumulados);
            if (usuarioId != null) {
                usuarioId.getUsuarioPuntosAcumuladosList().add(usuarioPuntosAcumulados);
                usuarioId = em.merge(usuarioId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuarioPuntosAcumulados(usuarioPuntosAcumulados.getIdPuntaje()) != null) {
                throw new PreexistingEntityException("UsuarioPuntosAcumulados " + usuarioPuntosAcumulados + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuarioPuntosAcumulados usuarioPuntosAcumulados) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UsuarioPuntosAcumulados persistentUsuarioPuntosAcumulados = em.find(UsuarioPuntosAcumulados.class, usuarioPuntosAcumulados.getIdPuntaje());
            Usuario usuarioIdOld = persistentUsuarioPuntosAcumulados.getUsuarioId();
            Usuario usuarioIdNew = usuarioPuntosAcumulados.getUsuarioId();
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getIdUsuario());
                usuarioPuntosAcumulados.setUsuarioId(usuarioIdNew);
            }
            usuarioPuntosAcumulados = em.merge(usuarioPuntosAcumulados);
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getUsuarioPuntosAcumuladosList().remove(usuarioPuntosAcumulados);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getUsuarioPuntosAcumuladosList().add(usuarioPuntosAcumulados);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = usuarioPuntosAcumulados.getIdPuntaje();
                if (findUsuarioPuntosAcumulados(id) == null) {
                    throw new NonexistentEntityException("The usuarioPuntosAcumulados with id " + id + " no longer exists.");
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
            UsuarioPuntosAcumulados usuarioPuntosAcumulados;
            try {
                usuarioPuntosAcumulados = em.getReference(UsuarioPuntosAcumulados.class, id);
                usuarioPuntosAcumulados.getIdPuntaje();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioPuntosAcumulados with id " + id + " no longer exists.", enfe);
            }
            Usuario usuarioId = usuarioPuntosAcumulados.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getUsuarioPuntosAcumuladosList().remove(usuarioPuntosAcumulados);
                usuarioId = em.merge(usuarioId);
            }
            em.remove(usuarioPuntosAcumulados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UsuarioPuntosAcumulados> findUsuarioPuntosAcumuladosEntities() {
        return findUsuarioPuntosAcumuladosEntities(true, -1, -1);
    }

    public List<UsuarioPuntosAcumulados> findUsuarioPuntosAcumuladosEntities(int maxResults, int firstResult) {
        return findUsuarioPuntosAcumuladosEntities(false, maxResults, firstResult);
    }

    private List<UsuarioPuntosAcumulados> findUsuarioPuntosAcumuladosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuarioPuntosAcumulados.class));
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

    public UsuarioPuntosAcumulados findUsuarioPuntosAcumulados(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuarioPuntosAcumulados.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioPuntosAcumuladosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuarioPuntosAcumulados> rt = cq.from(UsuarioPuntosAcumulados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
