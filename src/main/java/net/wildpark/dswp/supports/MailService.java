/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wildpark.dswp.supports;

import javax.inject.Named;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import net.wildpark.dswp.entitys.Log;
import net.wildpark.dswp.enums.LoggerLevel;
import net.wildpark.dswp.facades.LogFacade;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "mailService")
@ApplicationScoped
@Singleton
public class MailService implements Serializable {

    @EJB
    LogFacade logFacade;
    public MailService() {
    }
    
    public boolean sendEmail(String to, String textBody) {
        try {
            HtmlEmail email = new HtmlEmail();
            email.setCharset("utf-8");
            email.setHostName("mail.wildpark.net");
            email.setSmtpPort(25);
            email.setAuthenticator(new DefaultAuthenticator("informer@mksat.net", "22v5C728"));
            email.setFrom("informer@mksat.net");
            email.setSubject("Automatic message from darkside.wildpark.net");
            email.setHtmlMsg(textBody);
            email.addTo(to);
            email.send();
            logFacade.create(new Log("Sended mail " +  to));
        } catch (EmailException ex) {
            logFacade.create(new Log("Error with mail sending", ex,LoggerLevel.ERROR));
            System.out.println(ex);
            return false;
        }
        return true;
    }
}
