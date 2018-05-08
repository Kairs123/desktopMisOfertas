/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misOfertasDesktopController;

import MisOfertasDesktopEntities.Menu;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MisOfertasDesktopEntities.TipoUsuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import misOfertasDesktopController.exceptions.NonexistentEntityException;
import misOfertasDesktopController.exceptions.PreexistingEntityException;

/**
 *
 * @author David
 */
public class MenuJpaController implements Serializable {

    public MenuJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Menu menu) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoUsuario tipoUsuarioId = menu.getTipoUsuarioId();
            if (tipoUsuarioId != null) {
                tipoUsuarioId = em.getReference(tipoUsuarioId.getClass(), tipoUsuarioId.getIdTipoUsuario());
                menu.setTipoUsuarioId(tipoUsuarioId);
            }
            em.persist(menu);
            if (tipoUsuarioId != null) {
                tipoUsuarioId.getMenuList().add(menu);
                tipoUsuarioId = em.merge(tipoUsuarioId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMenu(menu.getMenuId()) != null) {
                throw new PreexistingEntityException("Menu " + menu + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Menu menu) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Menu persistentMenu = em.find(Menu.class, menu.getMenuId());
            TipoUsuario tipoUsuarioIdOld = persistentMenu.getTipoUsuarioId();
            TipoUsuario tipoUsuarioIdNew = menu.getTipoUsuarioId();
            if (tipoUsuarioIdNew != null) {
                tipoUsuarioIdNew = em.getReference(tipoUsuarioIdNew.getClass(), tipoUsuarioIdNew.getIdTipoUsuario());
                menu.setTipoUsuarioId(tipoUsuarioIdNew);
            }
            menu = em.merge(menu);
            if (tipoUsuarioIdOld != null && !tipoUsuarioIdOld.equals(tipoUsuarioIdNew)) {
                tipoUsuarioIdOld.getMenuList().remove(menu);
                tipoUsuarioIdOld = em.merge(tipoUsuarioIdOld);
            }
            if (tipoUsuarioIdNew != null && !tipoUsuarioIdNew.equals(tipoUsuarioIdOld)) {
                tipoUsuarioIdNew.getMenuList().add(menu);
                tipoUsuarioIdNew = em.merge(tipoUsuarioIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = menu.getMenuId();
                if (findMenu(id) == null) {
                    throw new NonexistentEntityException("The menu with id " + id + " no longer exists.");
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
            Menu menu;
            try {
                menu = em.getReference(Menu.class, id);
                menu.getMenuId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The menu with id " + id + " no longer exists.", enfe);
            }
            TipoUsuario tipoUsuarioId = menu.getTipoUsuarioId();
            if (tipoUsuarioId != null) {
                tipoUsuarioId.getMenuList().remove(menu);
                tipoUsuarioId = em.merge(tipoUsuarioId);
            }
            em.remove(menu);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Menu> findMenuEntities() {
        return findMenuEntities(true, -1, -1);
    }

    public List<Menu> findMenuEntities(int maxResults, int firstResult) {
        return findMenuEntities(false, maxResults, firstResult);
    }

    private List<Menu> findMenuEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Menu.class));
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

    public Menu findMenu(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Menu.class, id);
        } finally {
            em.close();
        }
    }

    public int getMenuCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Menu> rt = cq.from(Menu.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}