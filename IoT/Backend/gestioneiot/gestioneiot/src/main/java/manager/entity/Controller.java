/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.entity;

import manager.entity.adapter.CompanyTypeAdapter;
import manager.entity.constant.BaseEntity;
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
    
    //0 - Disable, 1 -  Active
    private int discovery;
    
    private String address;
    
    private String geolocation;
    
    private String responsible;

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

    public int getDiscovery() {
        return discovery;
    }

    public void setDiscovery(int discovery) {
        this.discovery = discovery;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }
 
}
