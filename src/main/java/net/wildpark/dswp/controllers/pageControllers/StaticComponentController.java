/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wildpark.dswp.controllers.pageControllers;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "staticComponentController")
@SessionScoped
public class StaticComponentController implements Serializable {

    private boolean showGlobalMenu = false;
    private int globalMenuHeight = 100;
    private String command;

    public StaticComponentController() {
    }

    public boolean isShowGlobalMenu() {
        return showGlobalMenu;
    }

    public void setShowGlobalMenu(boolean showGlobalMenu) {
        if (this.showGlobalMenu) {
            this.showGlobalMenu = false;
        } else {
            this.showGlobalMenu = showGlobalMenu;
        }
    }

    public void showMenu() {
        if (showGlobalMenu) {
            showGlobalMenu = false;
        } else {
            showGlobalMenu = true;
        }
    }

    public String getCommand() {
        if (showGlobalMenu) {
            return "show()";
        } else {
            return "hide()";
        }
    }

    public void setCommand(String command) {
        this.command = command;
    }

    

}
