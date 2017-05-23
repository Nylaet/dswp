package net.wildpark.dswp.controllers.pageControllers;

import net.wildpark.dswp.supports.devices.WPEcoDevice;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/**
 * Created by nylaet on 23.05.2017.
 */
@Named(value = "deviceEdit")
@SessionScoped
public class DeviceEdit {
    private WPEcoDevice ecoDevice=new WPEcoDevice();

    public WPEcoDevice getEcoDevice() {
        return ecoDevice;
    }

    public void setEcoDevice(WPEcoDevice ecoDevice) {
        this.ecoDevice = ecoDevice;
    }
}
