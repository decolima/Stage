/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.service;

/**
 *
 * @author andrelima
 */

import iot.entity.TagLog;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;


@PersistenceContext(type = PersistenceContextType.TRANSACTION)
public class TagLogService extends BaseService<TagLog>  {
    
    public List<TagLog> getNews(){
        
        this.em = DbManager.getInstance().getEM();
        
        return em.createQuery("select e from TagLog e where status = 0", TagLog.class)
                .setLockMode(LockModeType.NONE)
                .getResultList();
    }
    
    public List<TagLog> getSent(){
        
        this.em = DbManager.getInstance().getEM();
        
        return em.createQuery("select e from TagLog e where status = 1", TagLog.class)
                .setLockMode(LockModeType.NONE)
                .getResultList();
    }
    
    public List<TagLog> getOlds(){
        
        this.em = DbManager.getInstance().getEM();
        
        return em.createQuery("select e from TagLog e where status = 1", TagLog.class)
                .setLockMode(LockModeType.NONE)
                .getResultList();
    }
  
}
