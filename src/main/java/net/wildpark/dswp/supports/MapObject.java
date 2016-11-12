/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wildpark.dswp.supports;

import java.util.Date;
import java.util.Map;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.primefaces.model.map.LatLng;

/**
 *
 * @author Panker-RDP
 */
public abstract class MapObject {
    String about;
    public LatLng latLng;
    @Temporal(TemporalType.TIMESTAMP)
    public Date added;
    @Temporal(TemporalType.TIMESTAMP)
    public Date lastChange;
    public Map<String,String> otherValues;
    public String markerImage;

    public String getAbout() {
        if(about.isEmpty())return "Пусто";
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    public Date getLastChange() {
        return lastChange;
    }

    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }

    public Map<String, String> getOtherValues() {
        return otherValues;
    }

    public void setOtherValues(Map<String, String> otherValues) {
        this.otherValues = otherValues;
    }

    public String getMarkerImage() {
        return markerImage;
    }

    public void setMarkerImage(String markerImage) {
        this.markerImage = markerImage;
    }
    
    
    
}
