/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wildpark.dswp.enums;

/**
 *
 * @author Panker-RDP
 */
public enum UserRole {
    DEVELOPER("Разработчик"),ADMIN("Администратор"),USER("Пользователь"),GUEST("Гость");
    private String about;

    private UserRole(String about) {
        this.about = about;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
    
}
