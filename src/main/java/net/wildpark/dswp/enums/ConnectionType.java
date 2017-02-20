/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wildpark.dswp.enums;

/**
 *
 * @author nylae
 */
public enum ConnectionType {
    MQTT("Протокол MQTT"),SNMP("Протокол SNMP");
    private String about;

    private ConnectionType(String about) {
        this.about = about;
    }

    public String getAbout() {
        return about;
    }
    
    
}
