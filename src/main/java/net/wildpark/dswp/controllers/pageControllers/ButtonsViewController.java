/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wildpark.dswp.controllers.pageControllers;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Panker-RDP
 */
@Named(value = "buttonsViewController")
@RequestScoped
public class ButtonsViewController {

    public ButtonsViewController() {
    }

    public String getButtonTitle(String attr) {
        switch (attr) {
            case "Accept":
                return "Принять";
            case "Decline":
                return "Отменить";
            case "Add":
                return "Добавить";
            case "Del":
                return "Удалить";
            case "Back":
                return "Назад";
            case "Forward":
                return "Вперед";
            case "Edit":
                return "Изменить";
            case "Find":
                return "Найти";
            default:
                return "attr";
        }
    }

    public String getImageName(String attr) {
        switch (attr) {
            case "Accept":
                return "check";
            case "Decline":
                return "forbidden";
            case "Add":
                return "plus";
            case "Del":
                return "cross";
            case "Back":
                return "return";
            case "Forward":
                return "forward";
            case "Edit":
                return "edit";
            case "Find":
                return "search";
            default:
                return "attr";
        }
    }
}
