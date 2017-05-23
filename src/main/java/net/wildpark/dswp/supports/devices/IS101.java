package net.wildpark.dswp.supports.devices;

/**
 * Created by nylaet on 19.05.2017.
 */
public class IS101 extends Device {
    private int temp;
    private int humi;

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getHumi() {
        return humi;
    }

    public void setHumi(int humi) {
        this.humi = humi;
    }
}
