package net.wildpark.dswp.supports;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import net.wildpark.dswp.entitys.DeviceHistory;
import net.wildpark.dswp.entitys.Log;
import net.wildpark.dswp.facades.DeviceHistoryFacade;
import net.wildpark.dswp.facades.LogFacade;
import net.wildpark.dswp.facades.MqttMessageFacade;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.List;

@Named("mqttSubscriber")
@ApplicationScoped
@Singleton
@Startup
public class MqttSubscriber implements MqttCallback {

    String brokerURL = "tcp://80.252.241.65:1883";
    private MqttConnectOptions connectOpt;
    private MqttClient myClient;


    @EJB
    private MqttMessageFacade messageFacade;
    @EJB
    private LogFacade logFacade;
    @EJB
    private DeviceHistoryFacade historyFacade;

    @PostConstruct
    private void init() {
        try {
            connectOpt = new MqttConnectOptions();
            connectOpt.setAutomaticReconnect(true);
            connectOpt.setCleanSession(false);
            connectOpt.setKeepAliveInterval(30);
            connectOpt.setUserName("panker");
            connectOpt.setPassword("156456851".toCharArray());
            myClient = new MqttClient("tcp://80.252.241.65:1883", "glassfish_server_subscribe");
            myClient.setCallback(this);
            myClient.connect(connectOpt);
            myClient.subscribe("#", 0);
        } catch (MqttException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @PreDestroy
    private void deInit() {
        try {
            myClient.disconnect();
            System.out.println("Subscriber is Disconnected");
        } catch (MqttException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public MqttSubscriber() {
    }

    public void connectionLost(Throwable thrwbl) {
        logFacade.create(new Log("Connection lost"));
    }


    @Override
    public void messageArrived(java.lang.String topic, MqttMessage mm) throws Exception {
        System.out.println("topic: " + topic + " msg:" + new String(mm.getPayload()));
        net.wildpark.dswp.entitys.MqttMessage mqttMessages = new net.wildpark.dswp.entitys.MqttMessage();
        mqttMessages.setMessage(topic + new String(mm.getPayload()));
        messageFacade.create(mqttMessages);
        String[] splitedTopic = topic.split("/");
        if (!isAddedToBase(splitedTopic,new String(mm.getPayload()))) {
            registerNewDevice(splitedTopic, new String(mm.getPayload()));
        }

    }

    private void registerNewDevice(String[] splitedTopic, String s) {
        if(splitedTopic[1].equals("greetings"))return;
        DeviceHistory newDevice=new DeviceHistory();
        newDevice.setChipId(splitedTopic[1]);
        historyFacade.create(newDevice);
        addNewRecord(newDevice,splitedTopic,s);
    }

    private void addNewRecord(DeviceHistory device, String[] splitedTopic, String message) {
        if(splitedTopic[2].equals("data")){
            device.getDataHistory().add(message);
        }else if(splitedTopic[2].equals("log")){
            device.getLogHistory().add(message);
        }else if(splitedTopic[2].equals("commands")){
            device.getCommandHistory().add(message);
        }
        historyFacade.edit(device);
    }

    private boolean isAddedToBase(String[] topic,String message) {
        List<DeviceHistory> registeredDevice = historyFacade.findAll();
        for (DeviceHistory device : registeredDevice) {
            if (device.getChipId().equals(topic[1])){
                addNewRecord(device,topic,message);
                return true;
            }
        }
        return false;
    }


    public void deliveryComplete(IMqttDeliveryToken imdt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    private void parseData(String topic, String message) {
        if (topic.contains("greetings")) {
        }
    }
}
