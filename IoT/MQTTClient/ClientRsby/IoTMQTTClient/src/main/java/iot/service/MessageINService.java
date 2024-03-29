/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.service;

/**
 *
 * @author andrelima
 */

import iot.service.control.EntityManagerHelper;
import iot.service.control.BaseService;
import iot.entity.MessageIn;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;
import java.util.Optional;


@PersistenceContext(type = PersistenceContextType.TRANSACTION)
public class MessageInService extends BaseService<MessageIn>  {
    
    
    public List<MessageIn> getall(){
        
        this.em = EntityManagerHelper.getEntityManager();
        
        return em.createQuery("select e from MessageIn e", MessageIn.class)
                .setLockMode(LockModeType.NONE)
                .getResultList();
    } 
    
    public Optional<MessageIn> find(Long id){
        
        this.em = EntityManagerHelper.getEntityManager();
        
        MessageIn found = em.find(MessageIn.class, id);
       
        return found == null ? Optional.empty() : Optional.of(found);
        
    }
  
}
