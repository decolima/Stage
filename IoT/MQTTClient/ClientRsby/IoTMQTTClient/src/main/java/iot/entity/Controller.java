package iot.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author andrelima
 */
@Entity
@Table(name = "controller")
public class Controller extends BaseEntity {
    
    private String name;
    
    //Id from main DB gestioneIoT
    private Long main_id; 
    
    //Date activation YYYY-MM-DD
    private String activation;
   
    //0 - Disable, 1 -  Active, 2 - Canceled
    private int status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMain_id() {
        return main_id;
    }

    public void setMain_id(Long main_id) {
        this.main_id = main_id;
    }

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }        
    
}
