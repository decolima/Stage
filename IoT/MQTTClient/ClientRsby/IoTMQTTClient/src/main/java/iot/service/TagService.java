/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.service;

/**
 *
 * @author andrelima
 */

import iot.entity.Tag;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;


@PersistenceContext(type = PersistenceContextType.TRANSACTION)
public class TagService extends BaseService<Tag>  {
    
    public boolean setupTag(){
        
        this.em = DbManager.getInstance().getEM();
        
        return em.createQuery("select e from Tag e", Tag.class)
                .setLockMode(LockModeType.NONE)
                .getResultList().isEmpty();
    } 
  
}
