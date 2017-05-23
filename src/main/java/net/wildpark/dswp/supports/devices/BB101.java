package net.wildpark.dswp.supports.devices;

/**
 * Created by nylaet on 19.05.2017.
 */
public class BB101 extends Device {
    private int temp;
    private int humi;
    private int nowCapacity;

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

    public int getNowCapacity() {
        return nowCapacity;
    }

    public void setNowCapacity(int nowCapacity) {
        this.nowCapacity = nowCapacity;
    }
}
