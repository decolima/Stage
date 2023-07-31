/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.entity;

/**
 *
 * @author Filipe Copola Cornacchia 4Laser Group
 */


import manager.entity.constant.BaseEntity;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author André Lima
 */
@Entity
@Table(name = "tag")
public class Tag extends BaseEntity {
    
    @NotNull
    private String address;
    
    //bluetooth mac_address
    private String name;
    
    private LocalDateTime activation;

    //0 - new tag, 1 - know tag, 2 - ignore tag, 3 - tag deleted
    private int status;    

    //status to load %of batery o other info
    private int status_use; 

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

    public int getStatus_use() {
        return status_use;
    }

    public void setStatus_use(int status_use) {
        this.status_use = status_use;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.address);
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
     
    
    
}
