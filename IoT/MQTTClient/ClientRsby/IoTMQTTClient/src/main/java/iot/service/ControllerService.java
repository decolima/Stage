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
import iot.entity.Controller;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;


@PersistenceContext(type = PersistenceContextType.TRANSACTION)
public class ControllerService extends BaseService<Controller>  {
    
    private static final String PROPERTIES_FILE = "config.properties";
    private static Properties properties = new Properties();
    
    static {
        try (InputStream inputStream = ControllerService.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isController(){
        
        this.em = EntityManagerHelper.getEntityManager();
        
        return em.createQuery("select e from Controller e", Controller.class)
                .setLockMode(LockModeType.NONE)
                .getResultList().isEmpty();
    }
    
    public Controller getController(){
        
        this.em = EntityManagerHelper.getEntityManager();
        
        return em.createQuery("select e from Controller e", Controller.class)
                .setLockMode(LockModeType.NONE)
                .getResultList().get(0);
    }
    
    public void setupController(){
   
        Controller c = new Controller();
        c.setName(properties.getProperty("Name"));
        c.setStatus(1);
        c.setActivation(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
        c.setDiscovery(0);
        save(c);
        
    }
    
}
