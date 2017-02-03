/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wildpark.dswp.controllers.entitysController;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.wildpark.dswp.controllers.applicationScopeControllers.UsersAllController;
import net.wildpark.dswp.entitys.Log;
import net.wildpark.dswp.entitys.User;
import net.wildpark.dswp.enums.LoggerLevel;
import net.wildpark.dswp.enums.UserRole;
import net.wildpark.dswp.facades.LogFacade;
import net.wildpark.dswp.facades.UserFacade;
import net.wildpark.dswp.supports.MailService;
import net.wildpark.dswp.supports.MailTemplate;


/**
 *
 * @author Panker-RDP
 */
@Named(value = "userController")
@SessionScoped
public class UserController implements Serializable {

    @EJB
    private UserFacade userFacade;
    @EJB
    private LogFacade logFacade;
    @Inject
    private MailService mailService;
    @Inject
    UsersAllController uac;
    
    private User current;
    private User created;
    private String pass;
    private String repeatPass;
    private boolean entered;
    private boolean adminRole=false;
    private List <User> users=new ArrayList<>();
    private List <User> filteredUsers=new ArrayList<>();
    
    public UserController() {
    }
    
    @PostConstruct
    private void setUpDefaultUsers() {
        List<User> users = userFacade.findAll();
        boolean finded = false;
        if (!users.isEmpty()) {
            for (User user : users) {
                if (user.getEmail().equalsIgnoreCase("panker@mksat.net") || user.getEmail().equalsIgnoreCase("demo@microsity.info")) {
                    finded = true;
                }
            }
        }
        if (!finded) {
            User newUser = new User();
            newUser.setPassword("156456851");
            newUser.setLogin("panker");
            newUser.setEmail("nylaet@gmail.com");
            newUser.setPhone("+380664119956");
            newUser.setUserRole(UserRole.ADMIN);
            newUser.addMessage("Создан системой автоматически");
            newUser.setWaitingAutorization(false);
            userFacade.create(newUser);
            users.add(newUser);
            newUser = new User();
            newUser.setLogin("drizer");
            newUser.setPassword("Eco24165Eco");
            newUser.setEmail("drizer@gmail.com");
            newUser.setPhone("+380512000000");
            newUser.setUserRole(UserRole.ADMIN);
            newUser.setWaitingAutorization(false);
            userFacade.create(newUser);
            users.add(newUser);
            logFacade.create(new Log("Приложение запущено"));
        }
    }
    
    public void saveChanges(){
        userFacade.edit(current);
        logFacade.create(new Log("Пользователь "+current.getLogin()+" внес изменения своей учетной записи"));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Изменения успешно сохранены"));
    }

    public String login() {
        List<User> users = userFacade.findAll();
        
        for (User user : users) {
            if (user.getLogin().equalsIgnoreCase(current.getLogin())) {
                if (user.getPassword().equals(current.getPassword())) {
                    current = user;
                    entered = true;
                    logFacade.create(new Log(current.getLogin() + " entered from " + getIpRequest()));

                    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                    session.setAttribute("current", current);
                    uac.addSession(session.getId(), user);
                    return "home.xhtml?faces-redirect=true";
                }
            }
        }
        logFacade.create(new Log("Wrong authorization probe of "+current.getLogin()+" from " + getIpRequest(),LoggerLevel.WARN));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Не верный логин/пароль"));
        return "";
    }
    
    public String logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        current=new User();
        entered=false;
        return"index.xhtml?faces-redirect=true";
    }
    
    public String updatePassword() {
        if (pass.equals(repeatPass)) {
            current.setPassword(pass);
            userFacade.edit(current);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Пароль был успешно обновлен!"));
            logFacade.create(new Log(current.getLogin() + " change password from " + getIpRequest()));
            return "";

        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Что-то не так!"));
        return "";
    }
    
    public String getAuthorizationRequest(){
        if(current.getLogin().length()>3){
            if(current.getEmail().contains("@")){
                if(current.getAbout().length()>1){
                    logFacade.create(new Log("Пользователь "+current.getLogin()+" запросил авторизацию с IP:"+getIpRequest(),LoggerLevel.WARN));
                    boolean found=false;
                    for (User user : userFacade.findAll()) {
                        if(user.getUserRole().equals(UserRole.ADMIN)||user.getUserRole().equals(UserRole.DEVELOPER)){
                            user.addMessage("Пользователь "+current.getLogin()+" запросил авторизацию с IP:"+getIpRequest());
                            userFacade.edit(user);
                            if(!found)found=user.getLogin().equals(current.getLogin());
                        }
                    }
                    if(!found){
                    User authReq=new User();
                    authReq.setLogin(current.getLogin());
                    authReq.setEmail(current.getEmail());
                    authReq.setAbout(current.getAbout());
                    String password=generatePassword();
                    authReq.setPassword(password);
                    authReq.setUserRole(UserRole.USER);
                    authReq.addMessage("Вы подали заявку на регистрацию");
                    mailService.sendEmail(current.getEmail(), MailTemplate.getHtml(current.getLogin(), password));
                    userFacade.create(authReq);}
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Нужна информация о Вас!"));                    
                }                
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Не верный формат почтового ящика!"));
            }
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Логин должен быть больше!"));
        }
        return "";
    }
    
    private String generatePassword() {
        int charNum, count = 0;
        String str = "";
        while (count < 7) {
            
            Random r=new Random(System.currentTimeMillis());
           
                while (true) {
                    charNum = (int) (Math.random()*1000);
                    if (charNum >= 65 && charNum <= 90) {
                        str += (char) charNum;
                        break;
                    }
                    if (charNum >= 97 && charNum <= 122) {
                        str += (char) charNum;
                        break;
                    }
                }
            
            count++;
        }
        return str;
    }
        
    private String getIpRequest(){
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        
        return ipAddress;
    }
    
    public User getCurrent() {
        if(current==null)current=new User();
        return current;
    }

    public void setCurrent(User current) {
        this.current = current;
    }

    public User getCreated() {
        return created;
    }

    public void setCreated(User created) {
        this.created = created;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRepeatPass() {
        return repeatPass;
    }

    public void setRepeatPass(String repeatPass) {
        this.repeatPass = repeatPass;
    }

    public boolean isEntered() {
        return entered;
    }

    public void setEntered(boolean entered) {
        this.entered = entered;
    }
    
    public String getLores(){
        return MailTemplate.lores;
    }

    public boolean isAdminRole() {
        return(current.getUserRole().equals(UserRole.ADMIN));
    }

    public void setAdminRole(boolean adminRole) {
        this.adminRole = adminRole;
    }

    public List<User> getUsers() {
        users=userFacade.findAll();
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<User> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }
    
    public void saveUsersChanges(){
        for (User user : users) {
            userFacade.edit(user);
        }
        logFacade.create(new Log(current.getLogin()+" внес изменения в базу пользователей"));
    }
    
    
}
