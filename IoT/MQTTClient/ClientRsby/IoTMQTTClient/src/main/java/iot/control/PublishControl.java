/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.control;

import iot.entity.Publish;
import iot.control.JsonMaps.MessagetoJson;
import iot.control.constant.TypeMessageOut;
import iot.entity.maps.TagLog;
import iot.service.PublishService;
import iot.service.TagLogService;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author andrelima
 */
public class PublishControl {
    
    private final PublishService ps = new PublishService();
    private final ControllerControl cc = new ControllerControl();
    private final MqttControl mc =  new MqttControl();
    private final TagLogService tls = new TagLogService();
    
    
    public void publishTagDiscovery(List<TagLog> listlog){
        
        Publish pl = new Publish();
        
        pl.setPublishdate(LocalDateTime.now());
        pl.setStatus(0);
        pl = ps.save(pl);
       
        MessagetoJson msg = new MessagetoJson(pl, listlog, cc.getController(), null, TypeMessageOut.TagDiscovery);
        
        System.out.println(msg.getJsPublish());
        
        mc.publish(msg.getJsPublish());
        
        tls.updatePublish(pl.getId().toString(), listlog);
              
    }
    
    public void publishError(String error){
        
    }
    
    public void publishConfirmation(Publish confirmation){
        
    }
    
    public Publish find (Long id){
        return ps.find(id).orElse(new Publish());
        
    }
    
    public void save (Publish pl){
        
        ps.save(pl);
    }
    
}
