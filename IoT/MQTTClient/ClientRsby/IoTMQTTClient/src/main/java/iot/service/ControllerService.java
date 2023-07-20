/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.service;

/**
 *
 * @author andrelima
 */

import iot.entity.Controller;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;


@PersistenceContext(type = PersistenceContextType.TRANSACTION)
public class ControllerService extends BaseService<Controller>  {
    
    
    public boolean isController(){
        
        this.em = DbManager.getInstance().getEM();
        
        return em.createQuery("select e from Controller e", Controller.class)
                .setLockMode(LockModeType.NONE)
                .getResultList().isEmpty();
    }
    
    public Controller getController(){
        
        this.em = DbManager.getInstance().getEM();
        
        return em.createQuery("select e from Controller e", Controller.class)
                .setLockMode(LockModeType.NONE)
                .getResultList().get(0);
    }
    
    public void setupController() throws FileNotFoundException{
    
        Properties properties = new Properties();

        String path = System.getProperty("user.dir") + "/Config.properties";
        
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        
        Controller c = new Controller();
        c.setName(properties.getProperty("Name"));
        c.setStatus(0);
        c.setActivation(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
        save(c);
        //return c.getId().toString();
        
    }
    
}
