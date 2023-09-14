package manager.store;

import manager.entity.Controller;
import manager.mqtt.MQTTClient;
import manager.mqtt.constant.TopicSubscription;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import manager.entity.Company;

/**
 *
 * @author andrelima
 */
public class MqttStore {
    
    private MQTTClient mqttClient;
            
    public void setSubscrition(List<Company> companys) {
        try {
            mqttClient = MQTTClient.getInstance();
        } catch (Exception e) {
            System.out.println("Error on GetInstance of MQTTClient");
        }

        mqttClient.connect();
        
        if(!companys.isEmpty()){
            for(Company c : companys){
                for(TopicSubscription t : TopicSubscription.values()){
                    try {   
                        mqttClient.subscribe(c.getName() + "/" + t.toString());
                    } catch (InterruptedException ex) {
                        System.out.println("Error on Subscribe - List<Company>");
                    }
                }
            }    
        }
        
        try {   
            mqttClient.subscribe("WebCompany/#");
        } catch (InterruptedException ex) {
            System.out.println("Error on WebCompany");
        }
        
        
    }
    
    public void setSubscrition(Company c) {
        
        try {
            mqttClient = MQTTClient.getInstance();
        } catch (Exception e) {
            System.out.println("Error on GetInstance of MQTTClient");
        }
        
        mqttClient.connect();
        
        for(TopicSubscription t : TopicSubscription.values()){
            try {   
                mqttClient.subscribe(c.getName() + "/" + t.toString());
            } catch (InterruptedException ex) {
                System.out.println("Error on Subscribe - Company");
            }
        }

    }
    
    
   
    public void publish(String Topic, String msg){

        try {
            mqttClient = MQTTClient.getInstance();
        } catch (Exception e) {
            System.out.println("Error on GetInstance of MQTTClient");
        }
        
        mqttClient.connect();
        mqttClient.publish(Topic, msg, 0);
        
    }
       
    public void Disconnect() {
        
        try {
            mqttClient = MQTTClient.getInstance();
        } catch (Exception e) {
            System.out.println("Error on GetInstance of MQTTClient");
        }
        
        mqttClient.disconnect();
        
    }
    
    
    
}
