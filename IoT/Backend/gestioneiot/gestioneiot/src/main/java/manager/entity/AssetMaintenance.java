/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.entity;


import manager.entity.constant.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import manager.entity.adapter.AssetTypeAdapter;
import manager.entity.adapter.MaintenanceTypeAdapter;
import manager.entity.adapter.TagTypeAdapter;

/**
 *
 * @author André Lima
 */
@Entity
@Table(name = "assetmaintenance")
public class AssetMaintenance extends BaseEntity {

    
    @JsonbTypeAdapter(AssetTypeAdapter.class)
    @ManyToOne(optional = false)
    @JoinColumn(name = "asset_id", foreignKey = @ForeignKey(name = "fk_maintenance_asset"))
    private Asset asset; 
    
    
    @JsonbTypeAdapter(MaintenanceTypeAdapter.class)
    @ManyToOne(optional = false)
    @JoinColumn(name = "maintenance_id", foreignKey = @ForeignKey(name = "fk_manintenance"))
    private Maintenance maintenance;
   
    
    String notemaintenance;

    //0 - no change, 1 change tag
    int changetag;
    
    @JsonbTypeAdapter(TagTypeAdapter.class)
    @ManyToOne(optional = true)
    @JoinColumn(name = "newtag_id", foreignKey = @ForeignKey(name = "fk_tag"))
    private Tag newtag;

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Maintenance getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(Maintenance maintenance) {
        this.maintenance = maintenance;
    }

    public String getNotemaintenance() {
        return notemaintenance;
    }

    public void setNotemaintenance(String notemaintenance) {
        this.notemaintenance = notemaintenance;
    }

    public int getChangetag() {
        return changetag;
    }

    public void setChangetag(int changetag) {
        this.changetag = changetag;
    }

    public Tag getNewtag() {
        return newtag;
    }

    public void setNewtag(Tag newtag) {
        this.newtag = newtag;
    }
    

}
