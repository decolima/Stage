/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.store;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import manager.entity.Controller;
import manager.entity.Publish;
import manager.entity.Tag;
import manager.mqtt.constant.TopicPublisher;
import manager.mqtt.mapping.MessagetoJson;
import java.util.ArrayList;

/**
 *
 * @author André Lima
 */
public class PublishStore extends BaseStore<Publish> {
    
    @Inject
    private MqttStore s_mqtt;
    
    
    public List<Publish> all() {

        return em.createQuery("select e from Publish e where e.canceled = false",Publish.class)
                .getResultList();

    }
        
        
    public Optional<Publish> find(Long id){
        
        Publish found = em.find(Publish.class, id);
       
        return found == null ? Optional.empty() : Optional.of(found);
        
    }
    
    public void publish (String msg, String tp){
        
        s_mqtt.publish(tp, msg);
        
        
    }

    void updateController(Controller cr) {
        Publish pl = new Publish(LocalDateTime.now());
        pl = save(pl);
        MessagetoJson msg = new MessagetoJson(pl, null, cr, null, TopicPublisher.ControllerConfig);
        msg.getJsPublish();
        publish(msg.getJsPublish().toJSONString(), cr.getName() + "/" + TopicPublisher.ControllerConfig.toString());
    }
    
    
    void updateTag(List<Tag> tags, Controller cr) {
        Publish pl = new Publish(LocalDateTime.now());
        pl = save(pl);
        MessagetoJson msg = new MessagetoJson(pl, tags, cr, null, TopicPublisher.TagConfig);
        msg.getJsPublish();
        publish(msg.getJsPublish().toJSONString(), cr.getName() + "/" + TopicPublisher.ControllerConfig.toString());
    }

 
}
