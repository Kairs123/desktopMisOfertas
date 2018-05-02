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
import cl.duoc.misofertas.domain.Persona;
import cl.duoc.misofertas.domain.TipoUsuario;
import cl.duoc.misofertas.domain.ImagenProducto;
import java.util.ArrayList;
import java.util.List;
import cl.duoc.misofertas.domain.Preferencias;
import cl.duoc.misofertas.domain.Valoracion;
import cl.duoc.misofertas.domain.UsuarioTienda;
import cl.duoc.misofertas.domain.DescuentoEmitido;
import cl.duoc.misofertas.domain.OfertaConsultadaUsuario;
import cl.duoc.misofertas.domain.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import cl.duoc.misofertas.controller.exceptions.IllegalOrphanException;
import cl.duoc.misofertas.controller.exceptions.NonexistentEntityException;
import cl.duoc.misofertas.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author David
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getImagenProductoList() == null) {
            usuario.setImagenProductoList(new ArrayList<ImagenProducto>());
        }
        if (usuario.getPreferenciasList() == null) {
            usuario.setPreferenciasList(new ArrayList<Preferencias>());
        }
        if (usuario.getValoracionList() == null) {
            usuario.setValoracionList(new ArrayList<Valoracion>());
        }
        if (usuario.getUsuarioTiendaList() == null) {
            usuario.setUsuarioTiendaList(new ArrayList<UsuarioTienda>());
        }
        if (usuario.getDescuentoEmitidoList() == null) {
            usuario.setDescuentoEmitidoList(new ArrayList<DescuentoEmitido>());
        }
        if (usuario.getOfertaConsultadaUsuarioList() == null) {
            usuario.setOfertaConsultadaUsuarioList(new ArrayList<OfertaConsultadaUsuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona personaId = usuario.getPersonaId();
            if (personaId != null) {
                personaId = em.getReference(personaId.getClass(), personaId.getPersonaId());
                usuario.setPersonaId(personaId);
            }
            TipoUsuario tipoUser = usuario.getTipoUser();
            if (tipoUser != null) {
                tipoUser = em.getReference(tipoUser.getClass(), tipoUser.getTipoUserId());
                usuario.setTipoUser(tipoUser);
            }
            List<ImagenProducto> attachedImagenProductoList = new ArrayList<ImagenProducto>();
            for (ImagenProducto imagenProductoListImagenProductoToAttach : usuario.getImagenProductoList()) {
                imagenProductoListImagenProductoToAttach = em.getReference(imagenProductoListImagenProductoToAttach.getClass(), imagenProductoListImagenProductoToAttach.getImagenId());
                attachedImagenProductoList.add(imagenProductoListImagenProductoToAttach);
            }
            usuario.setImagenProductoList(attachedImagenProductoList);
            List<Preferencias> attachedPreferenciasList = new ArrayList<Preferencias>();
            for (Preferencias preferenciasListPreferenciasToAttach : usuario.getPreferenciasList()) {
                preferenciasListPreferenciasToAttach = em.getReference(preferenciasListPreferenciasToAttach.getClass(), preferenciasListPreferenciasToAttach.getPreferenciaId());
                attachedPreferenciasList.add(preferenciasListPreferenciasToAttach);
            }
            usuario.setPreferenciasList(attachedPreferenciasList);
            List<Valoracion> attachedValoracionList = new ArrayList<Valoracion>();
            for (Valoracion valoracionListValoracionToAttach : usuario.getValoracionList()) {
                valoracionListValoracionToAttach = em.getReference(valoracionListValoracionToAttach.getClass(), valoracionListValoracionToAttach.getValoracionId());
                attachedValoracionList.add(valoracionListValoracionToAttach);
            }
            usuario.setValoracionList(attachedValoracionList);
            List<UsuarioTienda> attachedUsuarioTiendaList = new ArrayList<UsuarioTienda>();
            for (UsuarioTienda usuarioTiendaListUsuarioTiendaToAttach : usuario.getUsuarioTiendaList()) {
                usuarioTiendaListUsuarioTiendaToAttach = em.getReference(usuarioTiendaListUsuarioTiendaToAttach.getClass(), usuarioTiendaListUsuarioTiendaToAttach.getUsuarioTiendaId());
                attachedUsuarioTiendaList.add(usuarioTiendaListUsuarioTiendaToAttach);
            }
            usuario.setUsuarioTiendaList(attachedUsuarioTiendaList);
            List<DescuentoEmitido> attachedDescuentoEmitidoList = new ArrayList<DescuentoEmitido>();
            for (DescuentoEmitido descuentoEmitidoListDescuentoEmitidoToAttach : usuario.getDescuentoEmitidoList()) {
                descuentoEmitidoListDescuentoEmitidoToAttach = em.getReference(descuentoEmitidoListDescuentoEmitidoToAttach.getClass(), descuentoEmitidoListDescuentoEmitidoToAttach.getDescuentoId());
                attachedDescuentoEmitidoList.add(descuentoEmitidoListDescuentoEmitidoToAttach);
            }
            usuario.setDescuentoEmitidoList(attachedDescuentoEmitidoList);
            List<OfertaConsultadaUsuario> attachedOfertaConsultadaUsuarioList = new ArrayList<OfertaConsultadaUsuario>();
            for (OfertaConsultadaUsuario ofertaConsultadaUsuarioListOfertaConsultadaUsuarioToAttach : usuario.getOfertaConsultadaUsuarioList()) {
                ofertaConsultadaUsuarioListOfertaConsultadaUsuarioToAttach = em.getReference(ofertaConsultadaUsuarioListOfertaConsultadaUsuarioToAttach.getClass(), ofertaConsultadaUsuarioListOfertaConsultadaUsuarioToAttach.getConsultaId());
                attachedOfertaConsultadaUsuarioList.add(ofertaConsultadaUsuarioListOfertaConsultadaUsuarioToAttach);
            }
            usuario.setOfertaConsultadaUsuarioList(attachedOfertaConsultadaUsuarioList);
            em.persist(usuario);
            if (personaId != null) {
                personaId.getUsuarioList().add(usuario);
                personaId = em.merge(personaId);
            }
            if (tipoUser != null) {
                tipoUser.getUsuarioList().add(usuario);
                tipoUser = em.merge(tipoUser);
            }
            for (ImagenProducto imagenProductoListImagenProducto : usuario.getImagenProductoList()) {
                Usuario oldEncargadoOfImagenProductoListImagenProducto = imagenProductoListImagenProducto.getEncargado();
                imagenProductoListImagenProducto.setEncargado(usuario);
                imagenProductoListImagenProducto = em.merge(imagenProductoListImagenProducto);
                if (oldEncargadoOfImagenProductoListImagenProducto != null) {
                    oldEncargadoOfImagenProductoListImagenProducto.getImagenProductoList().remove(imagenProductoListImagenProducto);
                    oldEncargadoOfImagenProductoListImagenProducto = em.merge(oldEncargadoOfImagenProductoListImagenProducto);
                }
            }
            for (Preferencias preferenciasListPreferencias : usuario.getPreferenciasList()) {
                Usuario oldUsuarioIdOfPreferenciasListPreferencias = preferenciasListPreferencias.getUsuarioId();
                preferenciasListPreferencias.setUsuarioId(usuario);
                preferenciasListPreferencias = em.merge(preferenciasListPreferencias);
                if (oldUsuarioIdOfPreferenciasListPreferencias != null) {
                    oldUsuarioIdOfPreferenciasListPreferencias.getPreferenciasList().remove(preferenciasListPreferencias);
                    oldUsuarioIdOfPreferenciasListPreferencias = em.merge(oldUsuarioIdOfPreferenciasListPreferencias);
                }
            }
            for (Valoracion valoracionListValoracion : usuario.getValoracionList()) {
                Usuario oldUsuarioIdOfValoracionListValoracion = valoracionListValoracion.getUsuarioId();
                valoracionListValoracion.setUsuarioId(usuario);
                valoracionListValoracion = em.merge(valoracionListValoracion);
                if (oldUsuarioIdOfValoracionListValoracion != null) {
                    oldUsuarioIdOfValoracionListValoracion.getValoracionList().remove(valoracionListValoracion);
                    oldUsuarioIdOfValoracionListValoracion = em.merge(oldUsuarioIdOfValoracionListValoracion);
                }
            }
            for (UsuarioTienda usuarioTiendaListUsuarioTienda : usuario.getUsuarioTiendaList()) {
                Usuario oldUsuarioIdOfUsuarioTiendaListUsuarioTienda = usuarioTiendaListUsuarioTienda.getUsuarioId();
                usuarioTiendaListUsuarioTienda.setUsuarioId(usuario);
                usuarioTiendaListUsuarioTienda = em.merge(usuarioTiendaListUsuarioTienda);
                if (oldUsuarioIdOfUsuarioTiendaListUsuarioTienda != null) {
                    oldUsuarioIdOfUsuarioTiendaListUsuarioTienda.getUsuarioTiendaList().remove(usuarioTiendaListUsuarioTienda);
                    oldUsuarioIdOfUsuarioTiendaListUsuarioTienda = em.merge(oldUsuarioIdOfUsuarioTiendaListUsuarioTienda);
                }
            }
            for (DescuentoEmitido descuentoEmitidoListDescuentoEmitido : usuario.getDescuentoEmitidoList()) {
                Usuario oldUsuarioIdOfDescuentoEmitidoListDescuentoEmitido = descuentoEmitidoListDescuentoEmitido.getUsuarioId();
                descuentoEmitidoListDescuentoEmitido.setUsuarioId(usuario);
                descuentoEmitidoListDescuentoEmitido = em.merge(descuentoEmitidoListDescuentoEmitido);
                if (oldUsuarioIdOfDescuentoEmitidoListDescuentoEmitido != null) {
                    oldUsuarioIdOfDescuentoEmitidoListDescuentoEmitido.getDescuentoEmitidoList().remove(descuentoEmitidoListDescuentoEmitido);
                    oldUsuarioIdOfDescuentoEmitidoListDescuentoEmitido = em.merge(oldUsuarioIdOfDescuentoEmitidoListDescuentoEmitido);
                }
            }
            for (OfertaConsultadaUsuario ofertaConsultadaUsuarioListOfertaConsultadaUsuario : usuario.getOfertaConsultadaUsuarioList()) {
                Usuario oldUsuarioIdOfOfertaConsultadaUsuarioListOfertaConsultadaUsuario = ofertaConsultadaUsuarioListOfertaConsultadaUsuario.getUsuarioId();
                ofertaConsultadaUsuarioListOfertaConsultadaUsuario.setUsuarioId(usuario);
                ofertaConsultadaUsuarioListOfertaConsultadaUsuario = em.merge(ofertaConsultadaUsuarioListOfertaConsultadaUsuario);
                if (oldUsuarioIdOfOfertaConsultadaUsuarioListOfertaConsultadaUsuario != null) {
                    oldUsuarioIdOfOfertaConsultadaUsuarioListOfertaConsultadaUsuario.getOfertaConsultadaUsuarioList().remove(ofertaConsultadaUsuarioListOfertaConsultadaUsuario);
                    oldUsuarioIdOfOfertaConsultadaUsuarioListOfertaConsultadaUsuario = em.merge(oldUsuarioIdOfOfertaConsultadaUsuarioListOfertaConsultadaUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getUsuarioId()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getUsuarioId());
            Persona personaIdOld = persistentUsuario.getPersonaId();
            Persona personaIdNew = usuario.getPersonaId();
            TipoUsuario tipoUserOld = persistentUsuario.getTipoUser();
            TipoUsuario tipoUserNew = usuario.getTipoUser();
            List<ImagenProducto> imagenProductoListOld = persistentUsuario.getImagenProductoList();
            List<ImagenProducto> imagenProductoListNew = usuario.getImagenProductoList();
            List<Preferencias> preferenciasListOld = persistentUsuario.getPreferenciasList();
            List<Preferencias> preferenciasListNew = usuario.getPreferenciasList();
            List<Valoracion> valoracionListOld = persistentUsuario.getValoracionList();
            List<Valoracion> valoracionListNew = usuario.getValoracionList();
            List<UsuarioTienda> usuarioTiendaListOld = persistentUsuario.getUsuarioTiendaList();
            List<UsuarioTienda> usuarioTiendaListNew = usuario.getUsuarioTiendaList();
            List<DescuentoEmitido> descuentoEmitidoListOld = persistentUsuario.getDescuentoEmitidoList();
            List<DescuentoEmitido> descuentoEmitidoListNew = usuario.getDescuentoEmitidoList();
            List<OfertaConsultadaUsuario> ofertaConsultadaUsuarioListOld = persistentUsuario.getOfertaConsultadaUsuarioList();
            List<OfertaConsultadaUsuario> ofertaConsultadaUsuarioListNew = usuario.getOfertaConsultadaUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (ImagenProducto imagenProductoListOldImagenProducto : imagenProductoListOld) {
                if (!imagenProductoListNew.contains(imagenProductoListOldImagenProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ImagenProducto " + imagenProductoListOldImagenProducto + " since its encargado field is not nullable.");
                }
            }
            for (Preferencias preferenciasListOldPreferencias : preferenciasListOld) {
                if (!preferenciasListNew.contains(preferenciasListOldPreferencias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Preferencias " + preferenciasListOldPreferencias + " since its usuarioId field is not nullable.");
                }
            }
            for (Valoracion valoracionListOldValoracion : valoracionListOld) {
                if (!valoracionListNew.contains(valoracionListOldValoracion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Valoracion " + valoracionListOldValoracion + " since its usuarioId field is not nullable.");
                }
            }
            for (UsuarioTienda usuarioTiendaListOldUsuarioTienda : usuarioTiendaListOld) {
                if (!usuarioTiendaListNew.contains(usuarioTiendaListOldUsuarioTienda)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsuarioTienda " + usuarioTiendaListOldUsuarioTienda + " since its usuarioId field is not nullable.");
                }
            }
            for (DescuentoEmitido descuentoEmitidoListOldDescuentoEmitido : descuentoEmitidoListOld) {
                if (!descuentoEmitidoListNew.contains(descuentoEmitidoListOldDescuentoEmitido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DescuentoEmitido " + descuentoEmitidoListOldDescuentoEmitido + " since its usuarioId field is not nullable.");
                }
            }
            for (OfertaConsultadaUsuario ofertaConsultadaUsuarioListOldOfertaConsultadaUsuario : ofertaConsultadaUsuarioListOld) {
                if (!ofertaConsultadaUsuarioListNew.contains(ofertaConsultadaUsuarioListOldOfertaConsultadaUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OfertaConsultadaUsuario " + ofertaConsultadaUsuarioListOldOfertaConsultadaUsuario + " since its usuarioId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (personaIdNew != null) {
                personaIdNew = em.getReference(personaIdNew.getClass(), personaIdNew.getPersonaId());
                usuario.setPersonaId(personaIdNew);
            }
            if (tipoUserNew != null) {
                tipoUserNew = em.getReference(tipoUserNew.getClass(), tipoUserNew.getTipoUserId());
                usuario.setTipoUser(tipoUserNew);
            }
            List<ImagenProducto> attachedImagenProductoListNew = new ArrayList<ImagenProducto>();
            for (ImagenProducto imagenProductoListNewImagenProductoToAttach : imagenProductoListNew) {
                imagenProductoListNewImagenProductoToAttach = em.getReference(imagenProductoListNewImagenProductoToAttach.getClass(), imagenProductoListNewImagenProductoToAttach.getImagenId());
                attachedImagenProductoListNew.add(imagenProductoListNewImagenProductoToAttach);
            }
            imagenProductoListNew = attachedImagenProductoListNew;
            usuario.setImagenProductoList(imagenProductoListNew);
            List<Preferencias> attachedPreferenciasListNew = new ArrayList<Preferencias>();
            for (Preferencias preferenciasListNewPreferenciasToAttach : preferenciasListNew) {
                preferenciasListNewPreferenciasToAttach = em.getReference(preferenciasListNewPreferenciasToAttach.getClass(), preferenciasListNewPreferenciasToAttach.getPreferenciaId());
                attachedPreferenciasListNew.add(preferenciasListNewPreferenciasToAttach);
            }
            preferenciasListNew = attachedPreferenciasListNew;
            usuario.setPreferenciasList(preferenciasListNew);
            List<Valoracion> attachedValoracionListNew = new ArrayList<Valoracion>();
            for (Valoracion valoracionListNewValoracionToAttach : valoracionListNew) {
                valoracionListNewValoracionToAttach = em.getReference(valoracionListNewValoracionToAttach.getClass(), valoracionListNewValoracionToAttach.getValoracionId());
                attachedValoracionListNew.add(valoracionListNewValoracionToAttach);
            }
            valoracionListNew = attachedValoracionListNew;
            usuario.setValoracionList(valoracionListNew);
            List<UsuarioTienda> attachedUsuarioTiendaListNew = new ArrayList<UsuarioTienda>();
            for (UsuarioTienda usuarioTiendaListNewUsuarioTiendaToAttach : usuarioTiendaListNew) {
                usuarioTiendaListNewUsuarioTiendaToAttach = em.getReference(usuarioTiendaListNewUsuarioTiendaToAttach.getClass(), usuarioTiendaListNewUsuarioTiendaToAttach.getUsuarioTiendaId());
                attachedUsuarioTiendaListNew.add(usuarioTiendaListNewUsuarioTiendaToAttach);
            }
            usuarioTiendaListNew = attachedUsuarioTiendaListNew;
            usuario.setUsuarioTiendaList(usuarioTiendaListNew);
            List<DescuentoEmitido> attachedDescuentoEmitidoListNew = new ArrayList<DescuentoEmitido>();
            for (DescuentoEmitido descuentoEmitidoListNewDescuentoEmitidoToAttach : descuentoEmitidoListNew) {
                descuentoEmitidoListNewDescuentoEmitidoToAttach = em.getReference(descuentoEmitidoListNewDescuentoEmitidoToAttach.getClass(), descuentoEmitidoListNewDescuentoEmitidoToAttach.getDescuentoId());
                attachedDescuentoEmitidoListNew.add(descuentoEmitidoListNewDescuentoEmitidoToAttach);
            }
            descuentoEmitidoListNew = attachedDescuentoEmitidoListNew;
            usuario.setDescuentoEmitidoList(descuentoEmitidoListNew);
            List<OfertaConsultadaUsuario> attachedOfertaConsultadaUsuarioListNew = new ArrayList<OfertaConsultadaUsuario>();
            for (OfertaConsultadaUsuario ofertaConsultadaUsuarioListNewOfertaConsultadaUsuarioToAttach : ofertaConsultadaUsuarioListNew) {
                ofertaConsultadaUsuarioListNewOfertaConsultadaUsuarioToAttach = em.getReference(ofertaConsultadaUsuarioListNewOfertaConsultadaUsuarioToAttach.getClass(), ofertaConsultadaUsuarioListNewOfertaConsultadaUsuarioToAttach.getConsultaId());
                attachedOfertaConsultadaUsuarioListNew.add(ofertaConsultadaUsuarioListNewOfertaConsultadaUsuarioToAttach);
            }
            ofertaConsultadaUsuarioListNew = attachedOfertaConsultadaUsuarioListNew;
            usuario.setOfertaConsultadaUsuarioList(ofertaConsultadaUsuarioListNew);
            usuario = em.merge(usuario);
            if (personaIdOld != null && !personaIdOld.equals(personaIdNew)) {
                personaIdOld.getUsuarioList().remove(usuario);
                personaIdOld = em.merge(personaIdOld);
            }
            if (personaIdNew != null && !personaIdNew.equals(personaIdOld)) {
                personaIdNew.getUsuarioList().add(usuario);
                personaIdNew = em.merge(personaIdNew);
            }
            if (tipoUserOld != null && !tipoUserOld.equals(tipoUserNew)) {
                tipoUserOld.getUsuarioList().remove(usuario);
                tipoUserOld = em.merge(tipoUserOld);
            }
            if (tipoUserNew != null && !tipoUserNew.equals(tipoUserOld)) {
                tipoUserNew.getUsuarioList().add(usuario);
                tipoUserNew = em.merge(tipoUserNew);
            }
            for (ImagenProducto imagenProductoListNewImagenProducto : imagenProductoListNew) {
                if (!imagenProductoListOld.contains(imagenProductoListNewImagenProducto)) {
                    Usuario oldEncargadoOfImagenProductoListNewImagenProducto = imagenProductoListNewImagenProducto.getEncargado();
                    imagenProductoListNewImagenProducto.setEncargado(usuario);
                    imagenProductoListNewImagenProducto = em.merge(imagenProductoListNewImagenProducto);
                    if (oldEncargadoOfImagenProductoListNewImagenProducto != null && !oldEncargadoOfImagenProductoListNewImagenProducto.equals(usuario)) {
                        oldEncargadoOfImagenProductoListNewImagenProducto.getImagenProductoList().remove(imagenProductoListNewImagenProducto);
                        oldEncargadoOfImagenProductoListNewImagenProducto = em.merge(oldEncargadoOfImagenProductoListNewImagenProducto);
                    }
                }
            }
            for (Preferencias preferenciasListNewPreferencias : preferenciasListNew) {
                if (!preferenciasListOld.contains(preferenciasListNewPreferencias)) {
                    Usuario oldUsuarioIdOfPreferenciasListNewPreferencias = preferenciasListNewPreferencias.getUsuarioId();
                    preferenciasListNewPreferencias.setUsuarioId(usuario);
                    preferenciasListNewPreferencias = em.merge(preferenciasListNewPreferencias);
                    if (oldUsuarioIdOfPreferenciasListNewPreferencias != null && !oldUsuarioIdOfPreferenciasListNewPreferencias.equals(usuario)) {
                        oldUsuarioIdOfPreferenciasListNewPreferencias.getPreferenciasList().remove(preferenciasListNewPreferencias);
                        oldUsuarioIdOfPreferenciasListNewPreferencias = em.merge(oldUsuarioIdOfPreferenciasListNewPreferencias);
                    }
                }
            }
            for (Valoracion valoracionListNewValoracion : valoracionListNew) {
                if (!valoracionListOld.contains(valoracionListNewValoracion)) {
                    Usuario oldUsuarioIdOfValoracionListNewValoracion = valoracionListNewValoracion.getUsuarioId();
                    valoracionListNewValoracion.setUsuarioId(usuario);
                    valoracionListNewValoracion = em.merge(valoracionListNewValoracion);
                    if (oldUsuarioIdOfValoracionListNewValoracion != null && !oldUsuarioIdOfValoracionListNewValoracion.equals(usuario)) {
                        oldUsuarioIdOfValoracionListNewValoracion.getValoracionList().remove(valoracionListNewValoracion);
                        oldUsuarioIdOfValoracionListNewValoracion = em.merge(oldUsuarioIdOfValoracionListNewValoracion);
                    }
                }
            }
            for (UsuarioTienda usuarioTiendaListNewUsuarioTienda : usuarioTiendaListNew) {
                if (!usuarioTiendaListOld.contains(usuarioTiendaListNewUsuarioTienda)) {
                    Usuario oldUsuarioIdOfUsuarioTiendaListNewUsuarioTienda = usuarioTiendaListNewUsuarioTienda.getUsuarioId();
                    usuarioTiendaListNewUsuarioTienda.setUsuarioId(usuario);
                    usuarioTiendaListNewUsuarioTienda = em.merge(usuarioTiendaListNewUsuarioTienda);
                    if (oldUsuarioIdOfUsuarioTiendaListNewUsuarioTienda != null && !oldUsuarioIdOfUsuarioTiendaListNewUsuarioTienda.equals(usuario)) {
                        oldUsuarioIdOfUsuarioTiendaListNewUsuarioTienda.getUsuarioTiendaList().remove(usuarioTiendaListNewUsuarioTienda);
                        oldUsuarioIdOfUsuarioTiendaListNewUsuarioTienda = em.merge(oldUsuarioIdOfUsuarioTiendaListNewUsuarioTienda);
                    }
                }
            }
            for (DescuentoEmitido descuentoEmitidoListNewDescuentoEmitido : descuentoEmitidoListNew) {
                if (!descuentoEmitidoListOld.contains(descuentoEmitidoListNewDescuentoEmitido)) {
                    Usuario oldUsuarioIdOfDescuentoEmitidoListNewDescuentoEmitido = descuentoEmitidoListNewDescuentoEmitido.getUsuarioId();
                    descuentoEmitidoListNewDescuentoEmitido.setUsuarioId(usuario);
                    descuentoEmitidoListNewDescuentoEmitido = em.merge(descuentoEmitidoListNewDescuentoEmitido);
                    if (oldUsuarioIdOfDescuentoEmitidoListNewDescuentoEmitido != null && !oldUsuarioIdOfDescuentoEmitidoListNewDescuentoEmitido.equals(usuario)) {
                        oldUsuarioIdOfDescuentoEmitidoListNewDescuentoEmitido.getDescuentoEmitidoList().remove(descuentoEmitidoListNewDescuentoEmitido);
                        oldUsuarioIdOfDescuentoEmitidoListNewDescuentoEmitido = em.merge(oldUsuarioIdOfDescuentoEmitidoListNewDescuentoEmitido);
                    }
                }
            }
            for (OfertaConsultadaUsuario ofertaConsultadaUsuarioListNewOfertaConsultadaUsuario : ofertaConsultadaUsuarioListNew) {
                if (!ofertaConsultadaUsuarioListOld.contains(ofertaConsultadaUsuarioListNewOfertaConsultadaUsuario)) {
                    Usuario oldUsuarioIdOfOfertaConsultadaUsuarioListNewOfertaConsultadaUsuario = ofertaConsultadaUsuarioListNewOfertaConsultadaUsuario.getUsuarioId();
                    ofertaConsultadaUsuarioListNewOfertaConsultadaUsuario.setUsuarioId(usuario);
                    ofertaConsultadaUsuarioListNewOfertaConsultadaUsuario = em.merge(ofertaConsultadaUsuarioListNewOfertaConsultadaUsuario);
                    if (oldUsuarioIdOfOfertaConsultadaUsuarioListNewOfertaConsultadaUsuario != null && !oldUsuarioIdOfOfertaConsultadaUsuarioListNewOfertaConsultadaUsuario.equals(usuario)) {
                        oldUsuarioIdOfOfertaConsultadaUsuarioListNewOfertaConsultadaUsuario.getOfertaConsultadaUsuarioList().remove(ofertaConsultadaUsuarioListNewOfertaConsultadaUsuario);
                        oldUsuarioIdOfOfertaConsultadaUsuarioListNewOfertaConsultadaUsuario = em.merge(oldUsuarioIdOfOfertaConsultadaUsuarioListNewOfertaConsultadaUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = usuario.getUsuarioId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUsuarioId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ImagenProducto> imagenProductoListOrphanCheck = usuario.getImagenProductoList();
            for (ImagenProducto imagenProductoListOrphanCheckImagenProducto : imagenProductoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the ImagenProducto " + imagenProductoListOrphanCheckImagenProducto + " in its imagenProductoList field has a non-nullable encargado field.");
            }
            List<Preferencias> preferenciasListOrphanCheck = usuario.getPreferenciasList();
            for (Preferencias preferenciasListOrphanCheckPreferencias : preferenciasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Preferencias " + preferenciasListOrphanCheckPreferencias + " in its preferenciasList field has a non-nullable usuarioId field.");
            }
            List<Valoracion> valoracionListOrphanCheck = usuario.getValoracionList();
            for (Valoracion valoracionListOrphanCheckValoracion : valoracionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Valoracion " + valoracionListOrphanCheckValoracion + " in its valoracionList field has a non-nullable usuarioId field.");
            }
            List<UsuarioTienda> usuarioTiendaListOrphanCheck = usuario.getUsuarioTiendaList();
            for (UsuarioTienda usuarioTiendaListOrphanCheckUsuarioTienda : usuarioTiendaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the UsuarioTienda " + usuarioTiendaListOrphanCheckUsuarioTienda + " in its usuarioTiendaList field has a non-nullable usuarioId field.");
            }
            List<DescuentoEmitido> descuentoEmitidoListOrphanCheck = usuario.getDescuentoEmitidoList();
            for (DescuentoEmitido descuentoEmitidoListOrphanCheckDescuentoEmitido : descuentoEmitidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the DescuentoEmitido " + descuentoEmitidoListOrphanCheckDescuentoEmitido + " in its descuentoEmitidoList field has a non-nullable usuarioId field.");
            }
            List<OfertaConsultadaUsuario> ofertaConsultadaUsuarioListOrphanCheck = usuario.getOfertaConsultadaUsuarioList();
            for (OfertaConsultadaUsuario ofertaConsultadaUsuarioListOrphanCheckOfertaConsultadaUsuario : ofertaConsultadaUsuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the OfertaConsultadaUsuario " + ofertaConsultadaUsuarioListOrphanCheckOfertaConsultadaUsuario + " in its ofertaConsultadaUsuarioList field has a non-nullable usuarioId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Persona personaId = usuario.getPersonaId();
            if (personaId != null) {
                personaId.getUsuarioList().remove(usuario);
                personaId = em.merge(personaId);
            }
            TipoUsuario tipoUser = usuario.getTipoUser();
            if (tipoUser != null) {
                tipoUser.getUsuarioList().remove(usuario);
                tipoUser = em.merge(tipoUser);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}