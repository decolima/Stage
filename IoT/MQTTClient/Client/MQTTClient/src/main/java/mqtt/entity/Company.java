/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mqtt.entity;

import mqtt.entity.constant.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author Filipe Copola Cornacchia 4Laser Group
 */
@Entity
@Table(name = "company")
public class Company extends BaseEntity {


    @NotBlank
    @Column(nullable = false)
    String name;
  
    @NotBlank
    @Column(nullable = false)
    String responsible_name;
    
    @NotBlank
    @Column(nullable = false)
    String responsible_email;
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponsible_name() {
        return responsible_name;
    }

    public void setResponsible_name(String responsible_name) {
        this.responsible_name = responsible_name;
    }

    public String getResponsible_email() {
        return responsible_email;
    }

    public void setResponsible_email(String responsible_email) {
        this.responsible_email = responsible_email;
    }



 
   

    

}