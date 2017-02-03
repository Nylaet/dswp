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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import net.wildpark.dswp.controllers.entitysController.UserController;
import net.wildpark.dswp.entitys.Log;
import net.wildpark.dswp.enums.LoggerLevel;
import net.wildpark.dswp.facades.LogFacade;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "logsPageController")
@SessionScoped
public class LogsPageController implements Serializable {

    @EJB
    private LogFacade logFacade;
    @Inject
    private UserController userController;

    private List<Log> logs = new ArrayList<>();
    private List<Log> filteredLogs = new ArrayList<>();

    public LogsPageController() {
    }

    @PostConstruct
    public void init() {
        try {
            logs = logFacade.findAll();
        } catch (Exception ex) {
            logFacade.create(new Log("Ошибка выполнения " + this.getClass().toString(), ex, LoggerLevel.ERROR));
        }
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    public List<Log> getFilteredLogs() {
        return filteredLogs;
    }

    public void setFilteredLogs(List<Log> filteredLogs) {
        this.filteredLogs = filteredLogs;
    }

    public List<String> levels() {
        List<String> levels=new ArrayList<>();
        for (LoggerLevel value : LoggerLevel.values()) {
            levels.add(value.getAbout());
        }
        return levels;
    }
}
