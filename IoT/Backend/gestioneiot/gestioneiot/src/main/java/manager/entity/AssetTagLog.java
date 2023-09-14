/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.entity;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import manager.entity.adapter.AssetTypeAdapter;
import manager.entity.adapter.TagTypeAdapter;
import manager.entity.constant.BaseEntity;

/**
 *
 * @author andrelima
 */
@Entity
@Table(name = "assettaglog")
public class AssetTagLog extends BaseEntity {
    
    
    @JsonbTypeAdapter(AssetTypeAdapter.class)
    @ManyToOne(optional = false)
    @JoinColumn(name = "asset_id", foreignKey = @ForeignKey(name = "fk_assetlog_asset"))
    private Asset asset;
    
    @JsonbTypeAdapter(TagTypeAdapter.class)
    @ManyToOne(optional = false)
    @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "fk_assetlog_tag"))
    private Tag tag;
    
    //1 - active, 0 - deleted
    int status;

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AssetTagLog{" + "asset=" + asset + ", tag=" + tag + ", status=" + status + ", created= " + getCreated() + '}';
    }
    
    
    
}
