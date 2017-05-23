package net.wildpark.dswp.supports.devices;

/**
 * Created by nylaet on 19.05.2017.
 */
public class ARC101 extends Device{
    /*
    device.sensors.timer1=100;
    device.sensors.timer2=3000;
    device.sensors.swich1State=false;
    device.sensors.swich2State=false;
    device.sensors.relay1State=false;
    device.sensors.relay2State=false;
    device.sensors.passes=0;
    */
    private int timer1=100;
    private int timer2=3000;
    private boolean swich1State=false;
    private boolean Swich2State=false;
    private boolean relay1State=false;
    private boolean relay2State=false;
    private int passes=0;

    public int getTimer1() {
        return timer1;
    }

    public void setTimer1(int timer1) {
        this.timer1 = timer1;
    }

    public int getTimer2() {
        return timer2;
    }

    public void setTimer2(int timer2) {
        this.timer2 = timer2;
    }

    public boolean isSwich1State() {
        return swich1State;
    }

    public void setSwich1State(boolean swich1State) {
        this.swich1State = swich1State;
    }

    public boolean isSwich2State() {
        return Swich2State;
    }

    public void setSwich2State(boolean swich2State) {
        Swich2State = swich2State;
    }

    public boolean isRelay1State() {
        return relay1State;
    }

    public void setRelay1State(boolean relay1State) {
        this.relay1State = relay1State;
    }

    public boolean isRelay2State() {
        return relay2State;
    }

    public void setRelay2State(boolean relay2State) {
        this.relay2State = relay2State;
    }

    public int getPasses() {
        return passes;
    }

    public void setPasses(int passes) {
        this.passes = passes;
    }
}
