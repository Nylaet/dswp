package net.wildpark.dswp.supports.devices;

/**
 * Created by nylaet on 19.05.2017.
 */
public class BRC101 extends Device {
    /*
    device.sensors.relay1=false;
    device.sensors.relay2=false;
    device.sensors.relay3=false;
    device.sensors.relay4=false;
    device.sensors.relay5=false;
    device.sensors.outPower=0; -- value between 0%-100%
    */
    private boolean relay1 = false;
    private boolean relay2 = false;
    private boolean relay3 = false;
    private boolean relay4 = false;
    private boolean relay5 = false;
    private int outPower = 0;

    public boolean isRelay1() {
        return relay1;
    }

    public void setRelay1(boolean relay1) {
        this.relay1 = relay1;
    }

    public boolean isRelay2() {
        return relay2;
    }

    public void setRelay2(boolean relay2) {
        this.relay2 = relay2;
    }

    public boolean isRelay3() {
        return relay3;
    }

    public void setRelay3(boolean relay3) {
        this.relay3 = relay3;
    }

    public boolean isRelay4() {
        return relay4;
    }

    public void setRelay4(boolean relay4) {
        this.relay4 = relay4;
    }

    public boolean isRelay5() {
        return relay5;
    }

    public void setRelay5(boolean relay5) {
        this.relay5 = relay5;
    }

    public int getOutPower() {
        int x = 100 * outPower / 1023;//Representation to percent view
        return x;
    }

    public void setOutPower(int x) {
        if (x > 100) {
            x = 100;
        }
        if (x < 0) {
            x = 0;
        }
        this.outPower = 1023 * x / 100;//Representattion from percent view
    }

    public void setOutPowerFromJson(int outPower){
        this.outPower=outPower;
    }
}
