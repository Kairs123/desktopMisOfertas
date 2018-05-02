/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.misofertas.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.duoc.misofertas.domain.Usuario;
import java.util.ArrayList;
import java.util.List;
import cl.duoc.misofertas.domain.Menu;
import cl.duoc.misofertas.domain.TipoUsuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import cl.duoc.misofertas.controller.exceptions.IllegalOrphanException;
import cl.duoc.misofertas.controller.exceptions.NonexistentEntityException;
import cl.duoc.misofertas.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author David
 */
public class TipoUsuarioJpaController implements Serializable {

    public TipoUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoUsuario tipoUsuario) throws PreexistingEntityException, Exception {
        if (tipoUsuario.getUsuarioList() == null) {
            tipoUsuario.setUsuarioList(new ArrayList<Usuario>());
        }
        if (tipoUsuario.getMenuList() == null) {
            tipoUsuario.setMenuList(new ArrayList<Menu>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : tipoUsuario.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getUsuarioId());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            tipoUsuario.setUsuarioList(attachedUsuarioList);
            List<Menu> attachedMenuList = new ArrayList<Menu>();
            for (Menu menuListMenuToAttach : tipoUsuario.getMenuList()) {
                menuListMenuToAttach = em.getReference(menuListMenuToAttach.getClass(), menuListMenuToAttach.getMenuId());
                attachedMenuList.add(menuListMenuToAttach);
            }
            tipoUsuario.setMenuList(attachedMenuList);
            em.persist(tipoUsuario);
            for (Usuario usuarioListUsuario : tipoUsuario.getUsuarioList()) {
                TipoUsuario oldTipoUserOfUsuarioListUsuario = usuarioListUsuario.getTipoUser();
                usuarioListUsuario.setTipoUser(tipoUsuario);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldTipoUserOfUsuarioListUsuario != null) {
                    oldTipoUserOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldTipoUserOfUsuarioListUsuario = em.merge(oldTipoUserOfUsuarioListUsuario);
                }
            }
            for (Menu menuListMenu : tipoUsuario.getMenuList()) {
                TipoUsuario oldTipoUsuarioIdOfMenuListMenu = menuListMenu.getTipoUsuarioId();
                menuListMenu.setTipoUsuarioId(tipoUsuario);
                menuListMenu = em.merge(menuListMenu);
                if (oldTipoUsuarioIdOfMenuListMenu != null) {
                    oldTipoUsuarioIdOfMenuListMenu.getMenuList().remove(menuListMenu);
                    oldTipoUsuarioIdOfMenuListMenu = em.merge(oldTipoUsuarioIdOfMenuListMenu);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipoUsuario(tipoUsuario.getTipoUserId()) != null) {
                throw new PreexistingEntityException("TipoUsuario " + tipoUsuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoUsuario tipoUsuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoUsuario persistentTipoUsuario = em.find(TipoUsuario.class, tipoUsuario.getTipoUserId());
            List<Usuario> usuarioListOld = persistentTipoUsuario.getUsuarioList();
            List<Usuario> usuarioListNew = tipoUsuario.getUsuarioList();
            List<Menu> menuListOld = persistentTipoUsuario.getMenuList();
            List<Menu> menuListNew = tipoUsuario.getMenuList();
            List<String> illegalOrphanMessages = null;
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its tipoUser field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getUsuarioId());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            tipoUsuario.setUsuarioList(usuarioListNew);
            List<Menu> attachedMenuListNew = new ArrayList<Menu>();
            for (Menu menuListNewMenuToAttach : menuListNew) {
                menuListNewMenuToAttach = em.getReference(menuListNewMenuToAttach.getClass(), menuListNewMenuToAttach.getMenuId());
                attachedMenuListNew.add(menuListNewMenuToAttach);
            }
            menuListNew = attachedMenuListNew;
            tipoUsuario.setMenuList(menuListNew);
            tipoUsuario = em.merge(tipoUsuario);
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    TipoUsuario oldTipoUserOfUsuarioListNewUsuario = usuarioListNewUsuario.getTipoUser();
                    usuarioListNewUsuario.setTipoUser(tipoUsuario);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldTipoUserOfUsuarioListNewUsuario != null && !oldTipoUserOfUsuarioListNewUsuario.equals(tipoUsuario)) {
                        oldTipoUserOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldTipoUserOfUsuarioListNewUsuario = em.merge(oldTipoUserOfUsuarioListNewUsuario);
                    }
                }
            }
            for (Menu menuListOldMenu : menuListOld) {
                if (!menuListNew.contains(menuListOldMenu)) {
                    menuListOldMenu.setTipoUsuarioId(null);
                    menuListOldMenu = em.merge(menuListOldMenu);
                }
            }
            for (Menu menuListNewMenu : menuListNew) {
                if (!menuListOld.contains(menuListNewMenu)) {
                    TipoUsuario oldTipoUsuarioIdOfMenuListNewMenu = menuListNewMenu.getTipoUsuarioId();
                    menuListNewMenu.setTipoUsuarioId(tipoUsuario);
                    menuListNewMenu = em.merge(menuListNewMenu);
                    if (oldTipoUsuarioIdOfMenuListNewMenu != null && !oldTipoUsuarioIdOfMenuListNewMenu.equals(tipoUsuario)) {
                        oldTipoUsuarioIdOfMenuListNewMenu.getMenuList().remove(menuListNewMenu);
                        oldTipoUsuarioIdOfMenuListNewMenu = em.merge(oldTipoUsuarioIdOfMenuListNewMenu);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoUsuario.getTipoUserId();
                if (findTipoUsuario(id) == null) {
                    throw new NonexistentEntityException("The tipoUsuario with id " + id + " no longer exists.");
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
            TipoUsuario tipoUsuario;
            try {
                tipoUsuario = em.getReference(TipoUsuario.class, id);
                tipoUsuario.getTipoUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoUsuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Usuario> usuarioListOrphanCheck = tipoUsuario.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoUsuario (" + tipoUsuario + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable tipoUser field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Menu> menuList = tipoUsuario.getMenuList();
            for (Menu menuListMenu : menuList) {
                menuListMenu.setTipoUsuarioId(null);
                menuListMenu = em.merge(menuListMenu);
            }
            em.remove(tipoUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoUsuario> findTipoUsuarioEntities() {
        return findTipoUsuarioEntities(true, -1, -1);
    }

    public List<TipoUsuario> findTipoUsuarioEntities(int maxResults, int firstResult) {
        return findTipoUsuarioEntities(false, maxResults, firstResult);
    }

    private List<TipoUsuario> findTipoUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoUsuario.class));
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

    public TipoUsuario findTipoUsuario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoUsuario> rt = cq.from(TipoUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
