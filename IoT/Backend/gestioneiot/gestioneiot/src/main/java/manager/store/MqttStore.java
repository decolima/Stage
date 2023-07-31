package manager.store;

import javax.inject.Inject;
import manager.entity.Controller;
import manager.mqtt.ConfigReader;
import manager.mqtt.MQTTClient;
import manager.mqtt.constant.TopicSubscription;

/**
 *
 * @author andrelima
 */
public class MqttStore {
    
    private MQTTClient mqttClient;
    
    @Inject
    private ControllerStore s_controller;
    
    
    public void setSubscrition() {
        
        mqttClient = MQTTClient.getInstance(ConfigReader.getBrokerUrl(), ConfigReader.getClientId());

        mqttClient.connect();
        
        //subscription for all controller that have this company
        for(Controller c : s_controller.all()){
            for(TopicSubscription t : TopicSubscription.values()){
                try {   
                    mqttClient.subscribe(c.getName() + "/" + t.toString());
                } catch (InterruptedException ex) {
                    //Logger.getLogger(MqttControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void setSubscrition(Controller c) {
        
        mqttClient.connect();
        
        //subscription new controller that have this company
  
        for(TopicSubscription t : TopicSubscription.values()){
            try {   
                mqttClient.subscribe(c.getName() + "/" + t.toString());
            } catch (InterruptedException ex) {
                //Logger.getLogger(MqttControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
   
    public void publish(String msg, String Topic){

        mqttClient.connect();
        
        mqttClient.publish(Topic, msg, 0);
        
    }
       
    public void Disconnect() {
        
        mqttClient.disconnect();
        
    }
    
    
    
}
