/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.mqtt;

import java.time.LocalDateTime;

/**
 *
 * @author andrelima
 */
public class SubscribedTopics extends Thread{
    
    private final String message;   
    private MQTTClient mqtt;

    public SubscribedTopics(String msg) {
        super("SubscribeTopics: " + LocalDateTime.now().toString());
        this.message = msg;
    }

    @Override
    public void run() {

        System.out.println("Process MessageReceved Started - " + super.getName()); 
        //System.out.println("Process MessageReceved Started - " );
        //messagereceved(message + super.getName());
        //messagereceved(message);
        //this.interrupt();

    }
    
    
    
}
