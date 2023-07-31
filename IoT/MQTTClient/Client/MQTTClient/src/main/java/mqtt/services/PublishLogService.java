/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mqtt.services;

/**
 *
 * @author andrelima
 */

import mqtt.entity.PublishLog;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;
import java.util.Optional;


@PersistenceContext(type = PersistenceContextType.TRANSACTION)
public class PublishLogService extends BaseService<PublishLog>  {
    
    
    public List<PublishLog> getall(){
        
        this.em = DbManager.getInstance().getEM();
        
        return em.createQuery("select e from PublishLog e", PublishLog.class)
                .setLockMode(LockModeType.NONE)
                .getResultList();
    } 
    
    public List<PublishLog> getPublishReceved(){
        
        this.em = DbManager.getInstance().getEM();
        return em.createQuery("select e from PublishLog e WHERE e.status = 1", PublishLog.class)
                .setLockMode(LockModeType.NONE)
                .getResultList();   
    } 
    
    public Optional<PublishLog> find(Long id){
        
        PublishLog found = em.find(PublishLog.class, id);
       
        return found == null ? Optional.empty() : Optional.of(found);
        
    }
  
}
