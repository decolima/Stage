/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mqtt.services;

/**
 *
 * @author andrelima
 */

import mqtt.entity.MessageReceved;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;
import java.util.Optional;


@PersistenceContext(type = PersistenceContextType.TRANSACTION)
public class MessageRecevedService extends BaseService<MessageReceved>  {
    
    
    public List<MessageReceved> getall(){
        
        this.em = DbManager.getInstance().getEM();
        
        return em.createQuery("select e from MessageReceved e", MessageReceved.class)
                .setLockMode(LockModeType.NONE)
                .getResultList();
    } 
    
    public Optional<MessageReceved> find(Long id){
        
        MessageReceved found = em.find(MessageReceved.class, id);
       
        return found == null ? Optional.empty() : Optional.of(found);
        
    }
  
}
