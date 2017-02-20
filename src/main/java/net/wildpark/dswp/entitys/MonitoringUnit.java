/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wildpark.dswp.entitys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import net.wildpark.dswp.enums.ConnectionType;

/**
 *
 * @author Panker-RDP
 * 
 * Класс определиня единицы мониторинга. Имеет список подчиненных датчиков и контроллеров. Также определяется метод связи, георафическое положение.
 */
@Entity
public class MonitoringUnit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String title;
    private String geoSite;
    private String ipAddress;
    private List<Long> sensors=new ArrayList<>();
    private List<Long> controllers=new ArrayList<>();
    private Date created;
    private ConnectionType connectionType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGeoSite() {
        return geoSite;
    }

    public void setGeoSite(String geoSite) {
        this.geoSite = geoSite;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public List<Long> getSensors() {
        return sensors;
    }

    public void setSensors(List<Long> sensors) {
        this.sensors = sensors;
    }

    public List<Long> getControllers() {
        return controllers;
    }

    public void setControllers(List<Long> controllers) {
        this.controllers = controllers;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MonitoringUnit)) {
            return false;
        }
        MonitoringUnit other = (MonitoringUnit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.wildpark.dswp.entitys.MonitoringUnit[ id=" + id + " ]";
    }
    
}
