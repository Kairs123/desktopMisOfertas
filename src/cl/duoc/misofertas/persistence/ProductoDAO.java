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
