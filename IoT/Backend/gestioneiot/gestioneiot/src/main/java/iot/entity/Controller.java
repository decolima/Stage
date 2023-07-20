/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.entity;

import iot.entity.adapter.CompanyTypeAdapter;
import iot.entity.constant.BaseEntity;
import java.time.LocalDateTime;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author andrelima
 */
@Entity
@Table(name = "controller")
public class Controller extends  BaseEntity {
    
    private String name;
    
    private LocalDateTime activation;
    
    @JsonbTypeAdapter(CompanyTypeAdapter.class)
    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "fk_company"))
    private Company company;
    
    //0 - Disable, 1 -  Active, 2 - Canceled
    private int status;
    
    private String location;

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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
        
    
}
