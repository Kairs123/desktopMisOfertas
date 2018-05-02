/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.duoc.misofertas.business;

import cl.duoc.misofertas.persistence.*;
import javax.persistence.EntityManager;

/**
 *
 * @author David
 */
public class ProductManager {
    
    ProductoDAO productoDAO;
    
    public String createProducto(String nombreProducto,int rubroId,int isPerecible,int isActive){
        return productoDAO.createProducto(nombreProducto, rubroId, isPerecible, isActive);
    }
}
