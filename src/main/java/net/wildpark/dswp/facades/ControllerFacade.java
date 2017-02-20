/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wildpark.dswp.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.wildpark.dswp.entitys.Controller;

/**
 *
 * @author nylae
 */
@Stateless
public class ControllerFacade extends AbstractFacade<Controller> {

    @PersistenceContext(unitName = "net.wildpark_DSWP_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ControllerFacade() {
        super(Controller.class);
    }
    
}
