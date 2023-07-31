/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package mqtt.control;

import mqtt.control.constant.TopicPublisher;
import mqtt.mqtt.MQTTClient;
import mqtt.control.constant.TopicSubscription;
import mqtt.service.MqttService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author andrelima
 */

        
public class MqttControl {
    
    private final MqttService ms = new MqttService();
    private final ControllerControl cc = new ControllerControl();
    private MQTTClient mqtt = MQTTClient.getInstance(ms.loadBroker(), cc.getController().getName());
    
      
    public void setSubscrition() {
        
        mqtt = MQTTClient.getInstance(ms.loadBroker(), cc.getController().getName());
        mqtt.connect();
        for(TopicSubscription topic : TopicSubscription.values()){
            try {   
                mqtt.subscribe(cc.getController().getName() + "/" + topic.toString());
            } catch (InterruptedException ex) {
                Logger.getLogger(MqttControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void publish(JSONObject obj) {
        
        mqtt = MQTTClient.getInstance(ms.loadBroker(), cc.getController().getName());
        mqtt.connect();
        System.out.println(obj.toString());
        try {
            mqtt.publish(cc.getController().getName() + "/" + TopicPublisher.Discovery.toString(), obj.toString(), 0);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
        
    }
           
}
