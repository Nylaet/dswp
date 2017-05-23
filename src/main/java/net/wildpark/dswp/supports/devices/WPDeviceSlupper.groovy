package net.wildpark.dswp.supports.devices

import groovy.json.JsonSlurper
import net.wildpark.dswp.supports.devices.ARC101
import net.wildpark.dswp.supports.devices.BB101
import net.wildpark.dswp.supports.devices.IS101
import net.wildpark.dswp.supports.devices.WPEcoDevice
import org.apache.commons.cli.MissingArgumentException

/**
 * Created by nylaet on 19.05.2017.
 */
class WPDeviceSlupper {
    public static WPEcoDevice getObjectAsJO(String data){
        def jslupper= new JsonSlurper();    
        WPEcoDevice device=new WPEcoDevice();
        def object=jslupper.parseText(data);
        try {
            device.setAlarm(object.alarm);
            device.setBattery(object.battery);
            device.setChipId(object.chipId);
            device.setName(object.name);
            device.setReportTime(object.reportTime);
            device.setModelType(object.modelType);
            if (device.getModelType().equalsIgnoreCase('IS101')) {
                IS101 is101 = new IS101();
                is101.setTemp(object.sensors.temp);
                is101.setHumi(object.sensors.humi);
                device.setSensors(is101);
            } else if (device.getModelType().equalsIgnoreCase('BB101')) {
                BB101 bb101 = new BB101();
                bb101.setTemp(object.sensors.temp);
                bb101.setHumi(object.sensors.humi);
                bb101.setNowCapacity(object.sensors.nowCapacity);
                device.setSensors(bb101);
            } else if (device.getModelType().equalsIgnoreCase('ARC101')) {
                ARC101 arc101 = new ARC101();
                arc101.setPasses(object.sensors.passes);
                arc101.setRelay1State(object.sensors.relay1State);
                arc101.setRelay2State(object.sensors.relay2State);
                arc101.setSwich1State(object.sensors.swich1State);
                arc101.setSwich2State(object.sensors.swich2State);
                arc101.setTimer1(object.sensors.timer1);
                arc101.setTimer2(object.sensors.timer2);
                device.setSensors(arc101);
            } else if (device.getModelType().equalsIgnoreCase('BRC101')) {
                BRC101 brc101 = new BRC101();
                brc101.setOutPowerFromJson(object.sensors.outPower);
                brc101.setRelay1(object.sensors.relay1);
                brc101.setRelay2(object.sensors.relay2);
                brc101.setRelay3(object.sensors.relay3);
                brc101.setRelay4(object.sensors.relay4);
                brc101.setRelay5(object.sensors.relay5);
                device.setSensors(brc101);
            } else if (device.getModelType().equalsIgnoreCase('OS101')) {
                OS101 os101 = new OS101();
                os101.setTemp(object.sensors.temp);
                os101.setHumi(object.sensors.humi);
                device.setSensors(os101);
            } else if (device.getModelType().equalsIgnoreCase('BT101')) {
                BT101 bt101 = new BT101();
                bt101.setTemp1(object.sensors.temp1);
                bt101.setTemp2(object.sensors.temp2);
                bt101.setTemp3(object.sensors.temp3);
                bt101.setTemp4(object.sensors.temp4);
                bt101.setTemp5(object.sensors.temp5);
                bt101.setTemp6(object.sensors.temp6);
            }
            return device;
        }catch (MissingMethodException ex){
            println('Have trable width parsing device id='+device.chipId)
            return new WPEcoDevice();
        }
    }
}
