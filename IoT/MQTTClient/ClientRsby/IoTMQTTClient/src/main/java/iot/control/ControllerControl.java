/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.control;

import iot.entity.Controller;
import iot.service.ControllerService;
import java.io.FileNotFoundException;

/**
 *
 * @author andrelima
 */
public final class ControllerControl {
    private final ControllerService cs = new ControllerService();
    private Controller controller = new Controller();
    
    public ControllerControl() throws FileNotFoundException{
        getControll();
    }
    
    
    public void getControll() throws FileNotFoundException{
    
        if(cs.isController()) {
        
            cs.setupController();
        }
        
        controller = cs.getController();
        
    }
    
    public Controller getController(){
        return controller;
    }
    
}
