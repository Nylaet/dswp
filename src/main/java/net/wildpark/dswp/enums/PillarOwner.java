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
public enum PillarOwner {
    NOT_SETUP("Не определено"),MYKGORSVIT("НиколаевГорСвет"),MYKOBLENERGO("ОблЭнерго"),ELECROTRANS("НикЭлектроТранс"),PRIVATEOWNER("Частный владелец");
    private String owner;

    private PillarOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }
    
}
