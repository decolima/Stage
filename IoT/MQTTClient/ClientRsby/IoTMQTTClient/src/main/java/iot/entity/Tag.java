/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.entity;

import java.time.LocalDateTime;
import java.util.Objects;
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
    
    //0 - new tag, 1 - managed tag, 2 - ignore tag, 3 - tag deleted, 9 - tag missing
    private int status;

    public Tag() {
    }

    public Tag(Long central_id, String address, String name, LocalDateTime activation, int status) {
        this.central_id = central_id;
        this.address = address;
        this.name = name;
        this.activation = activation;
        this.status = status;
    }

    
    
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.address);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tag other = (Tag) obj;
        return Objects.equals(this.address, other.address);
    }

    @Override
    public String toString() {
        return "Tag{" + "central_id=" + central_id + ", address=" + address + ", name=" + name + ", activation=" + activation + ", status=" + status + '}';
    }

  
    
    
    
}
