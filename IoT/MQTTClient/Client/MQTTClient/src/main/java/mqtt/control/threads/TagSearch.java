/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mqtt.control.threads;

import mqtt.control.TagControl;

/**
 *
 * @author andrelima
 */
public class TagSearch  {
    private final TagControl tc = new TagControl();
    
    public void manager(){
        System.out.println("Process TagSearch Started");
        tc.loadTagtoWork();
    }
    
}
