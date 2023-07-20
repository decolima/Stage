/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author andrelima
 */
@Entity
@Table(name = "tag")
public class Tag extends BaseEntity {
    
    private Long central_id;
    
    //bluetooth mac_address
    private String address;
    
    private String name;
    
    private LocalDateTime activation;
    
    //0 - new tag, 1 - know tag, 2 - ignore tag, 3 - tag deleted
    private int status;

    public Long getCentral_id() {
        return central_id;
    }

    public void setCentral_id(Long central_id) {
        this.central_id = central_id;
    }

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
