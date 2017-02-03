/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wildpark.dswp.controllers.applicationScopeControllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.Startup;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import net.wildpark.dswp.entitys.User;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "usersController")
@ApplicationScoped
@Startup
public class UsersAllController {

    private List<Session> sessions = new ArrayList<>();

    public UsersAllController() {

    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public void addSession(String session, User user) {
        sessions.add(new Session(session, user));
    }

    public void removeSession(User user) {
        Iterator sessIt = sessions.iterator();
        while (sessIt.hasNext()) {
            Session session = (Session) sessIt.next();
            if (session.getUser().equals(user)) {
                sessions.remove(session);
            }
        }
    }

    public class Session {

        private String session;
        private User user;
        private Date connected;
        private String growlId;

        public Session(String session, User user) {
            this.session = session;
            this.user = user;
            this.growlId = user.getId().toString();
            this.connected = new Date();
        }

        public String getSession() {
            return session;
        }

        public void setSession(String session) {
            this.session = session;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Date getConnected() {
            return connected;
        }

        public void setConnected(Date connected) {
            this.connected = connected;
        }

        public String getGrowlId() {
            return growlId;
        }

        public void setGrowlId(String growlId) {
            this.growlId = growlId;
        }

    }

}
