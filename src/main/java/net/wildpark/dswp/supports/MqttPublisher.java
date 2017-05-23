package net.wildpark.dswp.supports;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

@Named("mqttPublisher")
@ApplicationScoped
@Singleton
@Startup
public class MqttPublisher
  implements MqttCallback
{
  String brokerURL = "tcp://80.252.241.65:1883";
  private MqttConnectOptions connectOpt;
  private MqttClient myClient;
  private String message;
  private String myTopic;
  private boolean connected = false;
  
  public MqttPublisher() {}
  
  public void runClient()
  {
    try {
      if (!connected) init();
      MqttTopic topic = myClient.getTopic(myTopic);
      
      if (!message.isEmpty()) {
        MqttMessage mess = new MqttMessage(message.getBytes());
        mess.setQos(0);
        mess.setRetained(false);
        
        MqttDeliveryToken token = topic.publish(mess);
        token.waitForCompletion();
      }
    }
    catch (MqttException ex) {
      System.out.println("Error: " + ex.getMessage());
    }
  }
  
  public String getMessage()
  {
    return message;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }
  
  public String getMyTopic()
  {
    return myTopic;
  }
  
  public void setMyTopic(String myTopic) {
    this.myTopic = myTopic;
  }
  

  @PostConstruct
  private void init()
  {
    try
    {
      connectOpt = new MqttConnectOptions();
      connectOpt.setAutomaticReconnect(true);
      connectOpt.setCleanSession(true);
      connectOpt.setKeepAliveInterval(30);
      connectOpt.setUserName("panker");
      connectOpt.setPassword("156456851".toCharArray());
      myClient = new MqttClient("tcp://80.252.241.65:1883", "glassfish_server_publisher");
      myClient.setCallback(this);
      myClient.connect(connectOpt);
      connected = true;
    }
    catch (MqttException ex) {
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
  

  public void connectionLost(Throwable thrwbl) {}
  

  public void messageArrived(String string, MqttMessage mm)
    throws Exception
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public void deliveryComplete(IMqttDeliveryToken imdt)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}