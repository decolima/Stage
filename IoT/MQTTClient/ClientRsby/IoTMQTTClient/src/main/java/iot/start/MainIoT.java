/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package iot.start;

import iot.mqtt.MQTTClient;

/**
 *
 * @author andrelima
 */
public class MainIoT {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        String broker = "tcp://192.168.0.123:1883";
        String clientId = "IoTMQTTClient";
        String topic = "test/topic";
        String message = "Hello, MQTT3!";

        MQTTClient mqttClient = new MQTTClient(broker, clientId);
        mqttClient.connect();
        mqttClient.subscribe(topic);
        mqttClient.publish(topic, message);
        mqttClient.disconnect();
        
    }
    
}
