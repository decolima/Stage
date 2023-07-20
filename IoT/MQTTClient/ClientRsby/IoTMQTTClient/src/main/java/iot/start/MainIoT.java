/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package iot.start;

import iot.control.MqttControl;
import java.io.FileNotFoundException;

/**
 *
 * @author andrelima
 */
public class MainIoT {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
     
          /*
            String broker = "tcp://192.168.0.163";
            String clientId = "IoTMQTTClient";
            String topic = "topic";
            String message = "{id: 10, status: 100}";
            
            MQTTClient mqttClient = new MQTTClient(broker, clientId);
            mqttClient.connect();
            mqttClient.subscribe(topic);
            mqttClient.publish(topic, message);
            //mqttClient.disconnect();
           
            mqttClient.MqttSubscribe(topic);
            
         */     
        /*
        System.out.println("iot.start.MainIoT.main()");
        
        TagService ts = new TagService();
        System.out.println(ts.setupTag());
        */
        
        //ControllerService cs = new ControllerService();
        //System.out.println(cs.isController());
        //ControllerControl cc = new ControllerControl();
        
        MqttControl mc = new MqttControl();
        mc.setSubscrition();
        
    }
}
