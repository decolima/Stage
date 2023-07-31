/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import manager.entity.adapter.PublishTypeAdapter;
import manager.entity.constant.BaseEntity;


/**
 *
 * @author andrelima
 */
@Entity
@Table(name = "messageout")
public class MessageOUT extends BaseEntity {
    
    private  String message;   
    
    //0 new message, 1 sent message
    private int status;
    
    
    @JsonbTypeAdapter(PublishTypeAdapter.class)
    @ManyToOne(optional = false)
    @JoinColumn(name = "publish_id", foreignKey = @ForeignKey(name = "fk_publish"))
    private Publish publish;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Publish getPublish() {
        return publish;
    }

    public void setPublish(Publish publish) {
        this.publish = publish;
    }
    

    
}
