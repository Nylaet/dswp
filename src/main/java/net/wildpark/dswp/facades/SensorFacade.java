/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wildpark.dswp.facades;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.wildpark.dswp.entitys.Sensor;

/**
 *
 * @author nylae
 */
@Stateless
public class SensorFacade extends AbstractFacade<Sensor> {

    @PersistenceContext(unitName = "net.wildpark_DSWP_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SensorFacade() {
        super(Sensor.class);
    }
    
}
