/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misOfertasDesktopController;

import MisOfertasDesktopEntities.ImagenOferta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import MisOfertasDesktopEntities.Oferta;
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
public class ImagenOfertaJpaController implements Serializable {

    public ImagenOfertaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ImagenOferta imagenOferta) throws PreexistingEntityException, Exception {
        if (imagenOferta.getOfertaList() == null) {
            imagenOferta.setOfertaList(new ArrayList<Oferta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Oferta> attachedOfertaList = new ArrayList<Oferta>();
            for (Oferta ofertaListOfertaToAttach : imagenOferta.getOfertaList()) {
                ofertaListOfertaToAttach = em.getReference(ofertaListOfertaToAttach.getClass(), ofertaListOfertaToAttach.getIdOferta());
                attachedOfertaList.add(ofertaListOfertaToAttach);
            }
            imagenOferta.setOfertaList(attachedOfertaList);
            em.persist(imagenOferta);
            for (Oferta ofertaListOferta : imagenOferta.getOfertaList()) {
                ImagenOferta oldImagenIdOfOfertaListOferta = ofertaListOferta.getImagenId();
                ofertaListOferta.setImagenId(imagenOferta);
                ofertaListOferta = em.merge(ofertaListOferta);
                if (oldImagenIdOfOfertaListOferta != null) {
                    oldImagenIdOfOfertaListOferta.getOfertaList().remove(ofertaListOferta);
                    oldImagenIdOfOfertaListOferta = em.merge(oldImagenIdOfOfertaListOferta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findImagenOferta(imagenOferta.getIdImagen()) != null) {
                throw new PreexistingEntityException("ImagenOferta " + imagenOferta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ImagenOferta imagenOferta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ImagenOferta persistentImagenOferta = em.find(ImagenOferta.class, imagenOferta.getIdImagen());
            List<Oferta> ofertaListOld = persistentImagenOferta.getOfertaList();
            List<Oferta> ofertaListNew = imagenOferta.getOfertaList();
            List<String> illegalOrphanMessages = null;
            for (Oferta ofertaListOldOferta : ofertaListOld) {
                if (!ofertaListNew.contains(ofertaListOldOferta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Oferta " + ofertaListOldOferta + " since its imagenId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Oferta> attachedOfertaListNew = new ArrayList<Oferta>();
            for (Oferta ofertaListNewOfertaToAttach : ofertaListNew) {
                ofertaListNewOfertaToAttach = em.getReference(ofertaListNewOfertaToAttach.getClass(), ofertaListNewOfertaToAttach.getIdOferta());
                attachedOfertaListNew.add(ofertaListNewOfertaToAttach);
            }
            ofertaListNew = attachedOfertaListNew;
            imagenOferta.setOfertaList(ofertaListNew);
            imagenOferta = em.merge(imagenOferta);
            for (Oferta ofertaListNewOferta : ofertaListNew) {
                if (!ofertaListOld.contains(ofertaListNewOferta)) {
                    ImagenOferta oldImagenIdOfOfertaListNewOferta = ofertaListNewOferta.getImagenId();
                    ofertaListNewOferta.setImagenId(imagenOferta);
                    ofertaListNewOferta = em.merge(ofertaListNewOferta);
                    if (oldImagenIdOfOfertaListNewOferta != null && !oldImagenIdOfOfertaListNewOferta.equals(imagenOferta)) {
                        oldImagenIdOfOfertaListNewOferta.getOfertaList().remove(ofertaListNewOferta);
                        oldImagenIdOfOfertaListNewOferta = em.merge(oldImagenIdOfOfertaListNewOferta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = imagenOferta.getIdImagen();
                if (findImagenOferta(id) == null) {
                    throw new NonexistentEntityException("The imagenOferta with id " + id + " no longer exists.");
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
            ImagenOferta imagenOferta;
            try {
                imagenOferta = em.getReference(ImagenOferta.class, id);
                imagenOferta.getIdImagen();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The imagenOferta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Oferta> ofertaListOrphanCheck = imagenOferta.getOfertaList();
            for (Oferta ofertaListOrphanCheckOferta : ofertaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ImagenOferta (" + imagenOferta + ") cannot be destroyed since the Oferta " + ofertaListOrphanCheckOferta + " in its ofertaList field has a non-nullable imagenId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(imagenOferta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ImagenOferta> findImagenOfertaEntities() {
        return findImagenOfertaEntities(true, -1, -1);
    }

    public List<ImagenOferta> findImagenOfertaEntities(int maxResults, int firstResult) {
        return findImagenOfertaEntities(false, maxResults, firstResult);
    }

    private List<ImagenOferta> findImagenOfertaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ImagenOferta.class));
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

    public ImagenOferta findImagenOferta(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ImagenOferta.class, id);
        } finally {
            em.close();
        }
    }

    public int getImagenOfertaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ImagenOferta> rt = cq.from(ImagenOferta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
