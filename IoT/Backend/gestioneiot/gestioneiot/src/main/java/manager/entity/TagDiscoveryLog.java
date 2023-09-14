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
import manager.entity.adapter.MessageInTypeAdapter;
import manager.entity.adapter.TagTypeAdapter;
import manager.entity.constant.BaseEntity;

/**
 *
 * @author andrelima
 */
@Entity
@Table(name = "tagdiscoverylog")
public class TagDiscoveryLog extends BaseEntity {
    

    @JsonbTypeAdapter(TagTypeAdapter.class)
    @ManyToOne(optional = false)
    @JoinColumn(name = "tag_id", foreignKey = @ForeignKey(name = "fk_tagdiscover_tag"))
    private Tag tag;
    
    //0 - new tag, 1 - managed tag, 9 - tag missing
    private int status;
    
    private String status_use;

    @JsonbTypeAdapter(MessageInTypeAdapter.class)
    @ManyToOne(optional = false)
    @JoinColumn(name = "msg_id", foreignKey = @ForeignKey(name = "fk_tagdiscover_messagein"))
    private MessageIn msg;

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

    public String getStatus_use() {
        return status_use;
    }

    public void setStatus_use(String status_use) {
        this.status_use = status_use;
    }

    public MessageIn getMsg() {
        return msg;
    }

    public void setMsg(MessageIn msg) {
        this.msg = msg;
    }
    
    
    
}
