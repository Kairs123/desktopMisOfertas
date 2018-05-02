/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misOfertasDesktopController;

import MisOfertasDesktopEntities.ImagenProducto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MisOfertasDesktopEntities.Producto;
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
public class ImagenProductoJpaController implements Serializable {

    public ImagenProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ImagenProducto imagenProducto) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto productoId = imagenProducto.getProductoId();
            if (productoId != null) {
                productoId = em.getReference(productoId.getClass(), productoId.getProductoId());
                imagenProducto.setProductoId(productoId);
            }
            Usuario encargado = imagenProducto.getEncargado();
            if (encargado != null) {
                encargado = em.getReference(encargado.getClass(), encargado.getUsuarioId());
                imagenProducto.setEncargado(encargado);
            }
            em.persist(imagenProducto);
            if (productoId != null) {
                productoId.getImagenProductoList().add(imagenProducto);
                productoId = em.merge(productoId);
            }
            if (encargado != null) {
                encargado.getImagenProductoList().add(imagenProducto);
                encargado = em.merge(encargado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findImagenProducto(imagenProducto.getImagenId()) != null) {
                throw new PreexistingEntityException("ImagenProducto " + imagenProducto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ImagenProducto imagenProducto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ImagenProducto persistentImagenProducto = em.find(ImagenProducto.class, imagenProducto.getImagenId());
            Producto productoIdOld = persistentImagenProducto.getProductoId();
            Producto productoIdNew = imagenProducto.getProductoId();
            Usuario encargadoOld = persistentImagenProducto.getEncargado();
            Usuario encargadoNew = imagenProducto.getEncargado();
            if (productoIdNew != null) {
                productoIdNew = em.getReference(productoIdNew.getClass(), productoIdNew.getProductoId());
                imagenProducto.setProductoId(productoIdNew);
            }
            if (encargadoNew != null) {
                encargadoNew = em.getReference(encargadoNew.getClass(), encargadoNew.getUsuarioId());
                imagenProducto.setEncargado(encargadoNew);
            }
            imagenProducto = em.merge(imagenProducto);
            if (productoIdOld != null && !productoIdOld.equals(productoIdNew)) {
                productoIdOld.getImagenProductoList().remove(imagenProducto);
                productoIdOld = em.merge(productoIdOld);
            }
            if (productoIdNew != null && !productoIdNew.equals(productoIdOld)) {
                productoIdNew.getImagenProductoList().add(imagenProducto);
                productoIdNew = em.merge(productoIdNew);
            }
            if (encargadoOld != null && !encargadoOld.equals(encargadoNew)) {
                encargadoOld.getImagenProductoList().remove(imagenProducto);
                encargadoOld = em.merge(encargadoOld);
            }
            if (encargadoNew != null && !encargadoNew.equals(encargadoOld)) {
                encargadoNew.getImagenProductoList().add(imagenProducto);
                encargadoNew = em.merge(encargadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = imagenProducto.getImagenId();
                if (findImagenProducto(id) == null) {
                    throw new NonexistentEntityException("The imagenProducto with id " + id + " no longer exists.");
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
            ImagenProducto imagenProducto;
            try {
                imagenProducto = em.getReference(ImagenProducto.class, id);
                imagenProducto.getImagenId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The imagenProducto with id " + id + " no longer exists.", enfe);
            }
            Producto productoId = imagenProducto.getProductoId();
            if (productoId != null) {
                productoId.getImagenProductoList().remove(imagenProducto);
                productoId = em.merge(productoId);
            }
            Usuario encargado = imagenProducto.getEncargado();
            if (encargado != null) {
                encargado.getImagenProductoList().remove(imagenProducto);
                encargado = em.merge(encargado);
            }
            em.remove(imagenProducto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ImagenProducto> findImagenProductoEntities() {
        return findImagenProductoEntities(true, -1, -1);
    }

    public List<ImagenProducto> findImagenProductoEntities(int maxResults, int firstResult) {
        return findImagenProductoEntities(false, maxResults, firstResult);
    }

    private List<ImagenProducto> findImagenProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ImagenProducto.class));
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

    public ImagenProducto findImagenProducto(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ImagenProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getImagenProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ImagenProducto> rt = cq.from(ImagenProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
