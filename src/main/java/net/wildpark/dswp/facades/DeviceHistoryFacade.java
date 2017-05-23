package net.wildpark.dswp.facades;

import net.wildpark.dswp.entitys.DeviceHistory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by nylaet on 23.05.2017.
 */
@Stateless
public class DeviceHistoryFacade extends AbstractFacade {
    @PersistenceContext(unitName = "net.wildpark_DSWP_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public DeviceHistoryFacade() {
        super(DeviceHistory.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
