/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.control.threads;

import java.time.LocalDateTime;

/**
 *
 * @author andrelima
 */
public class SubscribeTopics extends Thread{
    
    public SubscribeTopics() {
        super("SubscribeTopics: " + LocalDateTime.now().toString());
    }
    
    
    @Override
    public void run() {
        
        System.out.println("Process Started - " + super.getName()); 
        
    }
    
}
