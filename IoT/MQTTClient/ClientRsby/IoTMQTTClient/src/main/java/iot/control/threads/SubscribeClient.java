/*
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.control.threads;

import iot.control.MqttControl;
import java.time.LocalDateTime;

/**
 *
 * @author andrelima
 */
public class SubscribeClient extends Thread {
    
    MqttControl mc = new MqttControl();
   
    public void manager() { 
        System.out.println("Process SubscribeTopics Started");
        mc.setSubscrition();  
    }
        
}
