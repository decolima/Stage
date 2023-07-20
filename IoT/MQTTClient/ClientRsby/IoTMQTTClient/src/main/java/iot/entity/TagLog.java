/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author andrelima
 */
@Entity
@Table(name = "taglog")
public class TagLog  extends BaseEntity {
    
    private String address;
    
    private String discoverydate;
    
    //0 - new discovery, 1 - discovery sent to broker 
    private int status;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDiscoverydate() {
        return discoverydate;
    }

    public void setDiscoverydate(String discoverydate) {
        this.discoverydate = discoverydate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
