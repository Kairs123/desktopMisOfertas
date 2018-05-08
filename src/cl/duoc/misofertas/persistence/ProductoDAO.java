<<<<<<< HEAD:src/cl/duoc/misofertas/persistence/ProductoDAO.java
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.misofertas.persistence;

import javax.persistence.EntityManager;

/**
 *
 * @author David
 */
public class ProductoDAO {
    EntityManager em;
    
    public ProductoDAO()
    {
    }
    public String createProducto(String nombreProducto,int rubroId,int isPerecible,int isActive)
    {
        String respuesta = null;
        try {
            em.createNamedStoredProcedureQuery("PKG_CRUD_PRODUCTO.PRC_CREATE_PRODUCTO").execute();
        } catch (Exception e) {
        }
        return respuesta;
    }
}
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misOfertasDesktopDAO;

import MisOfertasDesktopEntities.Producto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import misOfertasDesktopController.ProductoJpaController;

/**
 *
 * @author David
 */
public class ProductoDAO {

    private final ProductoJpaController productoController;
    private final EntityManagerFactory emf;

    public ProductoDAO() {
        emf = Persistence.createEntityManagerFactory("misOfertasDesktopPU");
        productoController = new ProductoJpaController(emf);
    }

    public void addProducto(Producto producto) throws Exception {
        productoController.create(producto);
    }

    public Long getMaxId() {
        Long maxId = productoController.getEntityManager().createNamedQuery("Producto.getMaxId", Long.class).getSingleResult();
        return maxId;
    }

    public void editProducto(Producto producto) throws Exception {
        productoController.edit(producto);
    }

    public Producto findById(Long idProducto) {
        return productoController.findProducto(idProducto);
    }

    public List<Producto> listAll() {
        return productoController.findProductoEntities();
    }

}
>>>>>>> b3f4f50fe70fd4e5ecab788315cae76eb2b7706c:src/misOfertasDesktopDAO/ProductoDAO.java
