/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misOfertasDesktopService;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import misofertasdesktop.Producto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import misOfertasDesktopDAO.ProductoDAO;
/**
 *
 * @author David
 */

public class ProductoServices {
    private static EntityManager em = null;
    private static EntityManagerFactory factory = null;
    
    public static void init()
    {
        factory = Persistence.createEntityManagerFactory("misOfertasDesktopPU");
        em = factory.createEntityManager();
    };
    public void crearProductoService() throws ParseException
    {
        StoredProcedureQuery createProducto = em.createNamedStoredProcedureQuery("createProducto");
        createProducto.setParameter("p_nombre_producto", "Test3");
        createProducto.setParameter("p_rubro_id", 1);
        createProducto.setParameter("p_es_perecible", 1);
        createProducto.setParameter("p_fecha_venc", "30-03-1992");
        createProducto.setParameter("p_activo", 1);
        createProducto.execute();
    };
    
    
    public List<Object[]> listarProductos(String productName) throws Exception  {

    {
        StoredProcedureQuery resultado = em.createStoredProcedureQuery("PKG_CRUD_PRODUCTO.PRC_LISTAR_POR_NOMBRE").
                registerStoredProcedureParameter(1, String.class, ParameterMode.IN).
                registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR).
                setParameter(1, "Test1");
        resultado.execute();
        List<Object[]> resulset = resultado.getResultList();
        return resulset;
    }
   
 }
}
