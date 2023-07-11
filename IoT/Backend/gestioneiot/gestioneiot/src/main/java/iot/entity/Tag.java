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

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author filipe
 */
@Entity
@Table(name = "tag")
public class Tag extends  BaseEntity{
    
    @NotNull
    String adress;

    @NotNull
    @Min(0)
    int status;    

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

 
    
    
}
