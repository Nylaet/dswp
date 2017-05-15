
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
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;








@Named("mqttSubscriber")
@ApplicationScoped
@Singleton
@Startup
public class MqttSubscriber
  implements MqttCallback
{
  String brokerURL = "tcp://80.252.241.65:1883";
  private MqttConnectOptions connectOpt;
  private MqttClient myClient;
  private String receveMessages = "";
  private String passes = "";
  
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
  

  public MqttSubscriber() {}
  
  public void connectionLost(Throwable thrwbl)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public void messageArrived(String string, MqttMessage mm) throws Exception
  {
    System.out.println("topic: " + string + " msg:" + new String(mm.getPayload()));
    receveMessages = (string + ":" + new String(mm.getPayload()) + "\r\n");
    if (receveMessages.contains("pistonPassage:")) {
      int size = "pistonPassage:".length();
      String a = new String(mm.getPayload());
      passes = a.substring(size);
    }
  }
  
  public String getPasses() {
    return passes;
  }
  
  public void setPasses(String passes) {
    this.passes = passes;
  }
  


  public void deliveryComplete(IMqttDeliveryToken imdt)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public String getReceveMessages() {
    return receveMessages;
  }
  
  public void setReceveMessages(String receveMessages) {
    this.receveMessages = receveMessages;
  }
  
  private void parseData(String topic, String message) {
    if (topic.contains("greetings")) {}
  }
}