/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Table;
import manager.entity.constant.BaseEntity;

/**
 *
 * @author andrelima
 */
@Entity
@Table(name = "publishlog")
public class Publish extends BaseEntity {

    public Publish() {
    }

    public Publish(LocalDateTime publishdate) {
        setPublishdate(publishdate);
    }
    
    
    private LocalDateTime publishdate;
   
    //0 - Sent withdout Response
    //1 - Sent and Receved response. In this case, the Taglog can be deleted
    private int status;

    public LocalDateTime getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(LocalDateTime dt) {
        this.publishdate = dt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
