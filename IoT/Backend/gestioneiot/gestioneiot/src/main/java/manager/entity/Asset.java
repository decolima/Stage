/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.entity;


import manager.entity.constant.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import manager.entity.adapter.ControllerTypeAdapter;
import manager.entity.adapter.TagTypeAdapter;
import manager.entity.adapter.TypeAssetTypeAdapter;

/**
 *
 * @author André Lima
 */
@Entity
@Table(name = "asset")
public class Asset extends BaseEntity {

    
    @JsonbTypeAdapter(TypeAssetTypeAdapter.class)
    @ManyToOne(optional = false)
    @JoinColumn(name = "typeasset_id", foreignKey = @ForeignKey(name = "fk_tpasset"))
    private TypeAsset typeasset;
    
    private String name;
    
    private LocalDateTime activation;

    @JsonbTypeAdapter(ControllerTypeAdapter.class)
    @ManyToOne(optional = false)
    @JoinColumn(name = "controller_id", foreignKey = @ForeignKey(name = "fk_controller"))
    private Controller controller;
    
    @JsonbTypeAdapter(TagTypeAdapter.class)
    @ManyToOne(optional = true)
    @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "fk_tag"))
    private Tag tag;

    public TypeAsset getTypeasset() {
        return typeasset;
    }

    public void setTypeasset(TypeAsset typeasset) {
        this.typeasset = typeasset;
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

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
   
    


    @Override
    public String toString() {
        return "Asset{" + "typeasset=" + typeasset + ", name=" + name + ", activation=" + activation + '}';
    }
    

}
