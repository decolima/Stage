/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mqtt.control;

import mqtt.control.constant.TopicPublisher;
import mqtt.entity.PublishLog;
import mqtt.entity.maps.TagLog;
import mqtt.service.PublishLogService;
import mqtt.service.TagLogService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author andrelima
 */
public class PublishLogControl {
    
    private final PublishLogService ps = new PublishLogService();
    private final ControllerControl cc = new ControllerControl();
    private final MqttControl mc =  new MqttControl();
    private final TagLogService tls = new TagLogService();
    
    
    public void publish(List<TagLog> listlog){
        
        
        JSONArray founded = new JSONArray();
        JSONArray news = new JSONArray();
        JSONArray missing = new JSONArray();
        JSONObject jsPublish = new JSONObject();   
        PublishLog pl = new PublishLog();
        
        pl.setPublishdate(LocalDateTime.now());
        pl.setStatus(0);
        pl = ps.save(pl);
       
        
        //creating list of tag managed founded
        for(TagLog t : listlog.stream().filter(a -> a.getStatus_tag() == 1).collect(Collectors.toList())){
            JSONObject obj = new JSONObject();
            obj.put("address", t.getAddress());
            obj.put("name", t.getName() != null ? t.getName()  : "");
            obj.put("status", t.getStatus());
            obj.put("discovery", t.getDatadiscovery());
            
            founded.put(obj);        
        }
        
        //creating list of new tag
        for(TagLog t : listlog.stream().filter(a -> a.getStatus_tag() == 0).collect(Collectors.toList())){
            JSONObject obj = new JSONObject();
            obj.put("address", t.getAddress());
            obj.put("name", t.getName() != null ? t.getName()  : "");
            obj.put("status", t.getStatus());
            obj.put("discovery", t.getDatadiscovery());
    
            news.put(obj);        
        }
        
        //creating list of missing tags
        for(TagLog t : listlog.stream().filter(a -> a.getStatus_tag() == 9).collect(Collectors.toList())){
            JSONObject obj = new JSONObject();
            obj.put("address", t.getAddress());
            obj.put("name", t.getName() != null ? t.getName()  : "");
            obj.put("status", t.getStatus());
            obj.put("discovery", t.getDatadiscovery());
    
            missing.put(obj);        
        }
        
        JSONObject ph = new JSONObject();
            ph.put("publishid", pl.getId().toString());
            ph.put("sentdate", pl.getPublishdate().format(DateTimeFormatter.ISO_DATE_TIME));
            ph.put("controler", cc.getController().getName() != null ? cc.getController().getName() : "");
            ph.put("controlerid", cc.getController().getMain_id() != null ? cc.getController().getMain_id() : "");
            ph.put("type", TopicPublisher.Discovery.toString());
            ph.put("auth", " - "); //to do 
            
            
        
        jsPublish.put("main", ph);
        jsPublish.put("founded", founded);
        jsPublish.put("news", news);
        jsPublish.put("missing", missing);
        
        //System.out.println(jsPublish);
        
        mc.publish(jsPublish);
        
        
        tls.updatePublish(pl.getId().toString(), listlog);
        
              
    }
    
    public PublishLog find (Long id){
        return ps.find(id).orElse(new PublishLog());
        
    }
    
    public void save (PublishLog pl){
        
        ps.save(pl);
    }
    
}
