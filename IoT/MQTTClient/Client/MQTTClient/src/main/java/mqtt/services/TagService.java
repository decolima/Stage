/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mqtt.services;

/**
 *
 * @author andrelima
 */

import mqtt.entity.Tag;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;


@PersistenceContext(type = PersistenceContextType.TRANSACTION)
public class TagService extends BaseService<Tag>  {
    
    public boolean setupTag(){
        
        this.em = DbManager.getInstance().getEM();
        
        return em.createQuery("select e from Tag e", Tag.class)
                .setLockMode(LockModeType.NONE)
                .getResultList().isEmpty();
    } 
    
    public List<Tag> getall(){
        
        this.em = DbManager.getInstance().getEM();
        
        return em.createQuery("select e from Tag e", Tag.class)
                .setLockMode(LockModeType.NONE)
                .getResultList();
    }
    
    public Tag find(String address){
        
        Tag found = new Tag();
                
        found = em.createQuery("select e from Tag e WHERE e.address = :address", Tag.class)
                .setLockMode(LockModeType.NONE)
                .setParameter("address", address)
                .getSingleResult();
        
        return found;
        
    }
  
}
