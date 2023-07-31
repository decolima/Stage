/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.store;

import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import manager.entity.Controller;

/**
 *
 * @author André Lima
 */
public class ControllerStore extends BaseStore<Controller> {
    
    @Inject
    private MqttStore s_mqtt;
    
    @Inject
    private PublishStore s_publish;
    
    
    public List<Controller> all() {

        return em.createQuery("select e from Controller e where e.canceled = false",Controller.class)
                .getResultList();

    }
        
        
    public Optional<Controller> find(Long id){
        
        Controller found = em.find(Controller.class, id);
       
        return found == null ? Optional.empty() : Optional.of(found);
        
    } 

    @Override
    public Controller save(Controller obj) {       
        Controller saved = super.save(obj);
        s_mqtt.setSubscrition(saved);
        s_publish.updateController(saved);        
        return obj;  
    }

    @Override
    public Controller update(Controller obj) {
        Controller saved = super.update(obj);
        s_publish.updateController(saved);  
        return saved;
    }
    
    
      
}
