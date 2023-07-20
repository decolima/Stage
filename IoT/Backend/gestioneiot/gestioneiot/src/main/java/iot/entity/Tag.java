/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.entity;

/**
 *
 * @author Filipe Copola Cornacchia 4Laser Group
 */


import iot.entity.constant.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author filipe
 */
@Entity
@Table(name = "tag")
public class Tag extends  BaseEntity{
    
    @NotNull
    String address;
    
    //bluetooth mac_address
    private String name;
    
    private LocalDateTime activation;

    //0 - new tag, 1 - know tag, 2 - ignore tag, 3 - tag deleted
    int status;    

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getActivation() {
        return activation;
    }

    public void setActivation(LocalDateTime activation) {
        this.activation = activation;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
     
    
}
