/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.wildpark.dswp.supports.devices;



/**
 *
 * @author nylae
 */

public class WPEcoDevice {

    private static final long serialVersionUID = 1L;
    private boolean energySave;
    private boolean alarm;
    private String name;
    private int battery;
    private int reportTime;//Надо в геттере и сеттере сразу преобразовывать милисекунды в минуты и обратно
    private int chipId;
    private Device sensors;
    private String modelType;

    public boolean isEnergySave() {
        return energySave;
    }

    public void setEnergySave(boolean energySave) {
        this.energySave = energySave;
    }

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getReportTime() {
        return reportTime;
    }

    public void setReportTime(int reportTime) {
        this.reportTime = reportTime;
    }

    public int getChipId() {
        return chipId;
    }

    public void setChipId(int chipId) {
        this.chipId = chipId;
    }

    public Device getSensors() {
        return sensors;
    }

    public void setSensors(Device sensors) {
        this.sensors = sensors;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

}
