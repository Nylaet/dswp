/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wildpark.dswp.supports;

import com.google.gson.Gson;
import net.wildpark.dswp.entitys.devices.WPEcoDevice;

/**
 *
 * @author nylae
 */
public class JsonParcer {

    private WPEcoDevice device;
    private String jsonString;
    private Gson gson=new Gson();
    
    public String getObjectAsJsonString(WPEcoDevice object) {
        return gson.toJson(object);        
    }
    
    public WPEcoDevice getObjectFromJsonString(String json){
        device=gson.fromJson(json, WPEcoDevice.class);
        return device;
    }    
}
