/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mqtt.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author andrelima
 */
public class MqttService {
    
    public String loadBroker() {
        Properties properties = new Properties();

        String path = System.getProperty("user.dir") + "/Config.properties";
        
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
        return properties.getProperty("Mqttbroker");
        
    }
    
}
