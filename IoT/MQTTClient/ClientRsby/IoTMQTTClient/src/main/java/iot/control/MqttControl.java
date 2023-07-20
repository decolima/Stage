/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.control;

import iot.mqtt.MQTTClient;
import iot.service.MqttService;
import iot.control.constant.TopicSubscription;
import java.io.FileNotFoundException;

/**
 *
 * @author andrelima
 */
public class MqttControl {
    
    private final MqttService ms = new MqttService();
    private final MQTTClient mqtt;
    private final ControllerControl cc;
    
    public MqttControl() throws FileNotFoundException{
        this.cc = new ControllerControl();
        mqtt = new MQTTClient(ms.loadBroker(), cc.getController().getName());
    }
    
    public void setSubscrition() throws InterruptedException{
        
        mqtt.connect();
        for(TopicSubscription topic : TopicSubscription.values()){
            mqtt.subscribe(cc.getController().getName() + "/" + topic.toString());   
        }
    }
           
}
