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
import MisOfertasDesktopEntities.Comuna;
import MisOfertasDesktopEntities.PrefTiendaUsuario;
import java.util.ArrayList;
import java.util.List;
import MisOfertasDesktopEntities.Rubro;
import MisOfertasDesktopEntities.Oferta;
import MisOfertasDesktopEntities.Tienda;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import misOfertasDesktopController.exceptions.IllegalOrphanException;
import misOfertasDesktopController.exceptions.NonexistentEntityException;
import misOfertasDesktopController.exceptions.PreexistingEntityException;

/**
 *
 * @author David
 */
public class TiendaJpaController implements Serializable {

    public TiendaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tienda tienda) throws PreexistingEntityException, Exception {
        if (tienda.getPrefTiendaUsuarioList() == null) {
            tienda.setPrefTiendaUsuarioList(new ArrayList<PrefTiendaUsuario>());
        }
        if (tienda.getRubroList() == null) {
            tienda.setRubroList(new ArrayList<Rubro>());
        }
        if (tienda.getOfertaList() == null) {
            tienda.setOfertaList(new ArrayList<Oferta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comuna comuna = tienda.getComuna();
            if (comuna != null) {
                comuna = em.getReference(comuna.getClass(), comuna.getIdComuna());
                tienda.setComuna(comuna);
            }
            List<PrefTiendaUsuario> attachedPrefTiendaUsuarioList = new ArrayList<PrefTiendaUsuario>();
            for (PrefTiendaUsuario prefTiendaUsuarioListPrefTiendaUsuarioToAttach : tienda.getPrefTiendaUsuarioList()) {
                prefTiendaUsuarioListPrefTiendaUsuarioToAttach = em.getReference(prefTiendaUsuarioListPrefTiendaUsuarioToAttach.getClass(), prefTiendaUsuarioListPrefTiendaUsuarioToAttach.getIdPrefTienda());
                attachedPrefTiendaUsuarioList.add(prefTiendaUsuarioListPrefTiendaUsuarioToAttach);
            }
            tienda.setPrefTiendaUsuarioList(attachedPrefTiendaUsuarioList);
            List<Rubro> attachedRubroList = new ArrayList<Rubro>();
            for (Rubro rubroListRubroToAttach : tienda.getRubroList()) {
                rubroListRubroToAttach = em.getReference(rubroListRubroToAttach.getClass(), rubroListRubroToAttach.getIdRubro());
                attachedRubroList.add(rubroListRubroToAttach);
            }
            tienda.setRubroList(attachedRubroList);
            List<Oferta> attachedOfertaList = new ArrayList<Oferta>();
            for (Oferta ofertaListOfertaToAttach : tienda.getOfertaList()) {
                ofertaListOfertaToAttach = em.getReference(ofertaListOfertaToAttach.getClass(), ofertaListOfertaToAttach.getIdOferta());
                attachedOfertaList.add(ofertaListOfertaToAttach);
            }
            tienda.setOfertaList(attachedOfertaList);
            em.persist(tienda);
            if (comuna != null) {
                comuna.getTiendaList().add(tienda);
                comuna = em.merge(comuna);
            }
            for (PrefTiendaUsuario prefTiendaUsuarioListPrefTiendaUsuario : tienda.getPrefTiendaUsuarioList()) {
                Tienda oldTiendaIdOfPrefTiendaUsuarioListPrefTiendaUsuario = prefTiendaUsuarioListPrefTiendaUsuario.getTiendaId();
                prefTiendaUsuarioListPrefTiendaUsuario.setTiendaId(tienda);
                prefTiendaUsuarioListPrefTiendaUsuario = em.merge(prefTiendaUsuarioListPrefTiendaUsuario);
                if (oldTiendaIdOfPrefTiendaUsuarioListPrefTiendaUsuario != null) {
                    oldTiendaIdOfPrefTiendaUsuarioListPrefTiendaUsuario.getPrefTiendaUsuarioList().remove(prefTiendaUsuarioListPrefTiendaUsuario);
                    oldTiendaIdOfPrefTiendaUsuarioListPrefTiendaUsuario = em.merge(oldTiendaIdOfPrefTiendaUsuarioListPrefTiendaUsuario);
                }
            }
            for (Rubro rubroListRubro : tienda.getRubroList()) {
                Tienda oldTiendaIdOfRubroListRubro = rubroListRubro.getTiendaId();
                rubroListRubro.setTiendaId(tienda);
                rubroListRubro = em.merge(rubroListRubro);
                if (oldTiendaIdOfRubroListRubro != null) {
                    oldTiendaIdOfRubroListRubro.getRubroList().remove(rubroListRubro);
                    oldTiendaIdOfRubroListRubro = em.merge(oldTiendaIdOfRubroListRubro);
                }
            }
            for (Oferta ofertaListOferta : tienda.getOfertaList()) {
                Tienda oldTiendaIdOfOfertaListOferta = ofertaListOferta.getTiendaId();
                ofertaListOferta.setTiendaId(tienda);
                ofertaListOferta = em.merge(ofertaListOferta);
                if (oldTiendaIdOfOfertaListOferta != null) {
                    oldTiendaIdOfOfertaListOferta.getOfertaList().remove(ofertaListOferta);
                    oldTiendaIdOfOfertaListOferta = em.merge(oldTiendaIdOfOfertaListOferta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTienda(tienda.getIdTienda()) != null) {
                throw new PreexistingEntityException("Tienda " + tienda + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tienda tienda) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tienda persistentTienda = em.find(Tienda.class, tienda.getIdTienda());
            Comuna comunaOld = persistentTienda.getComuna();
            Comuna comunaNew = tienda.getComuna();
            List<PrefTiendaUsuario> prefTiendaUsuarioListOld = persistentTienda.getPrefTiendaUsuarioList();
            List<PrefTiendaUsuario> prefTiendaUsuarioListNew = tienda.getPrefTiendaUsuarioList();
            List<Rubro> rubroListOld = persistentTienda.getRubroList();
            List<Rubro> rubroListNew = tienda.getRubroList();
            List<Oferta> ofertaListOld = persistentTienda.getOfertaList();
            List<Oferta> ofertaListNew = tienda.getOfertaList();
            List<String> illegalOrphanMessages = null;
            for (Rubro rubroListOldRubro : rubroListOld) {
                if (!rubroListNew.contains(rubroListOldRubro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rubro " + rubroListOldRubro + " since its tiendaId field is not nullable.");
                }
            }
            for (Oferta ofertaListOldOferta : ofertaListOld) {
                if (!ofertaListNew.contains(ofertaListOldOferta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Oferta " + ofertaListOldOferta + " since its tiendaId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (comunaNew != null) {
                comunaNew = em.getReference(comunaNew.getClass(), comunaNew.getIdComuna());
                tienda.setComuna(comunaNew);
            }
            List<PrefTiendaUsuario> attachedPrefTiendaUsuarioListNew = new ArrayList<PrefTiendaUsuario>();
            for (PrefTiendaUsuario prefTiendaUsuarioListNewPrefTiendaUsuarioToAttach : prefTiendaUsuarioListNew) {
                prefTiendaUsuarioListNewPrefTiendaUsuarioToAttach = em.getReference(prefTiendaUsuarioListNewPrefTiendaUsuarioToAttach.getClass(), prefTiendaUsuarioListNewPrefTiendaUsuarioToAttach.getIdPrefTienda());
                attachedPrefTiendaUsuarioListNew.add(prefTiendaUsuarioListNewPrefTiendaUsuarioToAttach);
            }
            prefTiendaUsuarioListNew = attachedPrefTiendaUsuarioListNew;
            tienda.setPrefTiendaUsuarioList(prefTiendaUsuarioListNew);
            List<Rubro> attachedRubroListNew = new ArrayList<Rubro>();
            for (Rubro rubroListNewRubroToAttach : rubroListNew) {
                rubroListNewRubroToAttach = em.getReference(rubroListNewRubroToAttach.getClass(), rubroListNewRubroToAttach.getIdRubro());
                attachedRubroListNew.add(rubroListNewRubroToAttach);
            }
            rubroListNew = attachedRubroListNew;
            tienda.setRubroList(rubroListNew);
            List<Oferta> attachedOfertaListNew = new ArrayList<Oferta>();
            for (Oferta ofertaListNewOfertaToAttach : ofertaListNew) {
                ofertaListNewOfertaToAttach = em.getReference(ofertaListNewOfertaToAttach.getClass(), ofertaListNewOfertaToAttach.getIdOferta());
                attachedOfertaListNew.add(ofertaListNewOfertaToAttach);
            }
            ofertaListNew = attachedOfertaListNew;
            tienda.setOfertaList(ofertaListNew);
            tienda = em.merge(tienda);
            if (comunaOld != null && !comunaOld.equals(comunaNew)) {
                comunaOld.getTiendaList().remove(tienda);
                comunaOld = em.merge(comunaOld);
            }
            if (comunaNew != null && !comunaNew.equals(comunaOld)) {
                comunaNew.getTiendaList().add(tienda);
                comunaNew = em.merge(comunaNew);
            }
            for (PrefTiendaUsuario prefTiendaUsuarioListOldPrefTiendaUsuario : prefTiendaUsuarioListOld) {
                if (!prefTiendaUsuarioListNew.contains(prefTiendaUsuarioListOldPrefTiendaUsuario)) {
                    prefTiendaUsuarioListOldPrefTiendaUsuario.setTiendaId(null);
                    prefTiendaUsuarioListOldPrefTiendaUsuario = em.merge(prefTiendaUsuarioListOldPrefTiendaUsuario);
                }
            }
            for (PrefTiendaUsuario prefTiendaUsuarioListNewPrefTiendaUsuario : prefTiendaUsuarioListNew) {
                if (!prefTiendaUsuarioListOld.contains(prefTiendaUsuarioListNewPrefTiendaUsuario)) {
                    Tienda oldTiendaIdOfPrefTiendaUsuarioListNewPrefTiendaUsuario = prefTiendaUsuarioListNewPrefTiendaUsuario.getTiendaId();
                    prefTiendaUsuarioListNewPrefTiendaUsuario.setTiendaId(tienda);
                    prefTiendaUsuarioListNewPrefTiendaUsuario = em.merge(prefTiendaUsuarioListNewPrefTiendaUsuario);
                    if (oldTiendaIdOfPrefTiendaUsuarioListNewPrefTiendaUsuario != null && !oldTiendaIdOfPrefTiendaUsuarioListNewPrefTiendaUsuario.equals(tienda)) {
                        oldTiendaIdOfPrefTiendaUsuarioListNewPrefTiendaUsuario.getPrefTiendaUsuarioList().remove(prefTiendaUsuarioListNewPrefTiendaUsuario);
                        oldTiendaIdOfPrefTiendaUsuarioListNewPrefTiendaUsuario = em.merge(oldTiendaIdOfPrefTiendaUsuarioListNewPrefTiendaUsuario);
                    }
                }
            }
            for (Rubro rubroListNewRubro : rubroListNew) {
                if (!rubroListOld.contains(rubroListNewRubro)) {
                    Tienda oldTiendaIdOfRubroListNewRubro = rubroListNewRubro.getTiendaId();
                    rubroListNewRubro.setTiendaId(tienda);
                    rubroListNewRubro = em.merge(rubroListNewRubro);
                    if (oldTiendaIdOfRubroListNewRubro != null && !oldTiendaIdOfRubroListNewRubro.equals(tienda)) {
                        oldTiendaIdOfRubroListNewRubro.getRubroList().remove(rubroListNewRubro);
                        oldTiendaIdOfRubroListNewRubro = em.merge(oldTiendaIdOfRubroListNewRubro);
                    }
                }
            }
            for (Oferta ofertaListNewOferta : ofertaListNew) {
                if (!ofertaListOld.contains(ofertaListNewOferta)) {
                    Tienda oldTiendaIdOfOfertaListNewOferta = ofertaListNewOferta.getTiendaId();
                    ofertaListNewOferta.setTiendaId(tienda);
                    ofertaListNewOferta = em.merge(ofertaListNewOferta);
                    if (oldTiendaIdOfOfertaListNewOferta != null && !oldTiendaIdOfOfertaListNewOferta.equals(tienda)) {
                        oldTiendaIdOfOfertaListNewOferta.getOfertaList().remove(ofertaListNewOferta);
                        oldTiendaIdOfOfertaListNewOferta = em.merge(oldTiendaIdOfOfertaListNewOferta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tienda.getIdTienda();
                if (findTienda(id) == null) {
                    throw new NonexistentEntityException("The tienda with id " + id + " no longer exists.");
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
            Tienda tienda;
            try {
                tienda = em.getReference(Tienda.class, id);
                tienda.getIdTienda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tienda with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Rubro> rubroListOrphanCheck = tienda.getRubroList();
            for (Rubro rubroListOrphanCheckRubro : rubroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tienda (" + tienda + ") cannot be destroyed since the Rubro " + rubroListOrphanCheckRubro + " in its rubroList field has a non-nullable tiendaId field.");
            }
            List<Oferta> ofertaListOrphanCheck = tienda.getOfertaList();
            for (Oferta ofertaListOrphanCheckOferta : ofertaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tienda (" + tienda + ") cannot be destroyed since the Oferta " + ofertaListOrphanCheckOferta + " in its ofertaList field has a non-nullable tiendaId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Comuna comuna = tienda.getComuna();
            if (comuna != null) {
                comuna.getTiendaList().remove(tienda);
                comuna = em.merge(comuna);
            }
            List<PrefTiendaUsuario> prefTiendaUsuarioList = tienda.getPrefTiendaUsuarioList();
            for (PrefTiendaUsuario prefTiendaUsuarioListPrefTiendaUsuario : prefTiendaUsuarioList) {
                prefTiendaUsuarioListPrefTiendaUsuario.setTiendaId(null);
                prefTiendaUsuarioListPrefTiendaUsuario = em.merge(prefTiendaUsuarioListPrefTiendaUsuario);
            }
            em.remove(tienda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tienda> findTiendaEntities() {
        return findTiendaEntities(true, -1, -1);
    }

    public List<Tienda> findTiendaEntities(int maxResults, int firstResult) {
        return findTiendaEntities(false, maxResults, firstResult);
    }

    private List<Tienda> findTiendaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tienda.class));
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

    public Tienda findTienda(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tienda.class, id);
        } finally {
            em.close();
        }
    }

    public int getTiendaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tienda> rt = cq.from(Tienda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
