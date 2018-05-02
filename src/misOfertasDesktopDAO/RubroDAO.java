/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misOfertasDesktopDAO;

import MisOfertasDesktopEntities.Rubro;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import misOfertasDesktopController.RubroJpaController;

/**
 *
 * @author David
 */
public class RubroDAO {
    private final RubroJpaController rubroController;
    private final EntityManagerFactory emf;
    
    public RubroDAO()
    {
        emf = Persistence.createEntityManagerFactory("misOfertasDesktopPU");
        rubroController = new RubroJpaController(emf);
    }
    public Rubro getRubroById(Long rubroId)
    {
        return rubroController.findRubro(rubroId);
    }
    
}
