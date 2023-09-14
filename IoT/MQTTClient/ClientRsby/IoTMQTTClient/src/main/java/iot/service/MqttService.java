/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.service;

import iot.service.control.ErrorLog;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author andrelima
 */
public class MqttService extends ErrorLog {
    
    private static final String PROPERTIES_FILE = "config.properties";
    private static Properties properties = new Properties();
    
    static {
        try (InputStream inputStream = ControllerService.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            logError(e);
        }
    }
    
    public String loadBroker() {
        
        return properties.getProperty("Mqttbroker");
        
    }
    
}
