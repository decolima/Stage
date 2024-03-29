/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.entity;

import javax.json.Json;
import javax.json.JsonObject;
import manager.entity.constant.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Filipe Copola Cornacchia 4Laser Group
 */
@Entity
@Table(name = "typeasset")
public class TypeAsset extends BaseEntity {

    private String type;
    private String model;
    private String brand;
    private String identifier_name;

    public String getIdentifier_name() {
        return identifier_name;
    }

    public void setIdentifier_name(String identifier_name) {
        this.identifier_name = identifier_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "TypeAsset{" + "type=" + type + ", model=" + model + ", brand=" + brand + ", identifier_name=" + identifier_name + '}';
    }
    
    @Override
    public JsonObject toJsonSlice() {

        return Json.createObjectBuilder()
                .add("id", this.id)
                .add("model", this.model)
                .add("brand", this.brand)
                .add("identifier_name", this.identifier_name)
                .build();
    }
    
    

}
