/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misOfertasDesktopDAO;

import MisOfertasDesktopEntities.Producto;
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
    
    public ProductoDAO()
    {
        emf = Persistence.createEntityManagerFactory("misOfertasDesktopPU");
        productoController = new ProductoJpaController(emf);
    }
    public void addProducto(Producto producto) throws Exception
    {
        productoController.create(producto);
    }
    public Long getMaxId()            
    {        
        Long maxId = productoController.getEntityManager().createNamedQuery("Producto.getMaxId",Long.class).getSingleResult();
        return maxId;
    }
   
    

}
