/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timers;

import java.util.Date;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import net.wildpark.dswp.controllers.applicationScopeControllers.UsersAllController;

/**
 *
 * @author Panker-RDP
 */
@Singleton
@Startup
public class NewTimerSessionBean {
    
    @Inject
    UsersAllController uac;
    
    @Schedule(dayOfWeek = "*", month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "*", second = "0", persistent = false)
    
    public void myTimer() {
        if(uac.getSessions().isEmpty())System.out.println("We are not have any session now");
        else{
            for (UsersAllController.Session session : uac.getSessions()) {
                
            }
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
