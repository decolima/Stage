/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.entity;

import manager.entity.constant.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author André Lima
 */
@Entity
@Table(name = "company")
public class Company extends BaseEntity {


    @NotBlank
    @Column(nullable = false)
    String name;
  
    @NotBlank
    @Column(nullable = false)
    String responsible;
    
    @NotBlank
    @Column(nullable = false)
    String email;
    
    String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Company{" + "name=" + name + ", responsible=" + responsible + ", email=" + email + ", phone=" + phone + '}';
    }

}