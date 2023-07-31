/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.entity.maps;

import java.util.Objects;

/**
 *
 * @author andrelima
 */

public class TagLog {
        
    private String address;
    
    private String name;
    
    private String datadiscovery;
    
    //0 - new discovery, 1 - discovery sent to broker 
    private String status;
    
    private String idpublish;
    
    private int status_tag;

    public TagLog(String address, String name, String datadiscovery, String status, String idpublish) {
        this.address = address;
        this.name = name;
        this.datadiscovery = datadiscovery;
        this.status = status;
        this.idpublish = idpublish;
    }

    public TagLog(String address, String name, String datadiscovery, String status, String idpublish, int status_tag) {
        this.address = address;
        this.name = name;
        this.datadiscovery = datadiscovery;
        this.status = status;
        this.idpublish = idpublish;
        this.status_tag = status_tag;
    }

    
    


    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getDatadiscovery() {
        return datadiscovery;
    }

    public String getStatus() {
        return status;
    }

    public String getIdpublish() {
        return idpublish;
    }

    public int getStatus_tag() {
        return status_tag;
    }

    public void setStatus_tag(int status_tag) {
        this.status_tag = status_tag;
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
        final TagLog other = (TagLog) obj;
        return Objects.equals(this.address, other.address);
    }

    @Override
    public String toString() {
        return "TagLog{" + "address=" + address + ", name=" + name + ", datadiscovery=" + datadiscovery + ", status=" + status + ", idpublish=" + idpublish + ", status_tag=" + status_tag + '}';
    }
    
}
