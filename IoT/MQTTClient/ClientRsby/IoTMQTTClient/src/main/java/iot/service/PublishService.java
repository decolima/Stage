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
import iot.entity.Publish;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;
import java.util.Optional;


@PersistenceContext(type = PersistenceContextType.TRANSACTION)
public class PublishService extends BaseService<Publish>  {
    
    
    public List<Publish> getall(){
        
        this.em = EntityManagerHelper.getEntityManager();
        
        return em.createQuery("select e from Publish e", Publish.class)
                .setLockMode(LockModeType.NONE)
                .getResultList();
    } 
    
    public List<Publish> getPublishReceved(){
        
        this.em = EntityManagerHelper.getEntityManager();
        
        return em.createQuery("select e from Publish e WHERE e.status = 1", Publish.class)
                .setLockMode(LockModeType.NONE)
                .getResultList();   
    } 
    
    public Optional<Publish> find(Long id){
        
        this.em = EntityManagerHelper.getEntityManager();
        
        Publish found = em.find(Publish.class, id);
       
        return found == null ? Optional.empty() : Optional.of(found);
        
    }
  
}
