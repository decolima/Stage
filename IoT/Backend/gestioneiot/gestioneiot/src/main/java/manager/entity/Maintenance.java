/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.entity;

import java.time.LocalDateTime;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import manager.entity.adapter.CompanyTypeAdapter;
import manager.entity.adapter.UserTypeAdapter;
import manager.entity.constant.BaseEntity;
import manager.entity.constant.StatusMaintenance;

/**
 *
 * @author andrelima
 */

@Entity
@Table(name = "maintenance")
public class Maintenance extends BaseEntity {
    
    @JsonbTypeAdapter(UserTypeAdapter.class)
    @ManyToOne(optional = false)
    @JoinColumn(name = "requester_id", foreignKey = @ForeignKey(name = "fk_user_requester"))
    private User requester;
    
    @JsonbTypeAdapter(UserTypeAdapter.class)
    @ManyToOne(optional = true)
    @JoinColumn(name = "executioner_id", foreignKey = @ForeignKey(name = "fk_user_executioner"))
    private User executioner;
    
    LocalDateTime requestdate;
    
    LocalDateTime executiontdate;
    
    String request;
    
    String noteexecution;
    
    @Enumerated(EnumType.STRING)
    StatusMaintenance status;
    
    String noterejected;
    
    String notecanceled;

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getExecutioner() {
        return executioner;
    }

    public void setExecutioner(User executioner) {
        this.executioner = executioner;
    }

    public LocalDateTime getRequestdate() {
        return requestdate;
    }

    public void setRequestdate(LocalDateTime requestdate) {
        this.requestdate = requestdate;
    }

    public LocalDateTime getExecutiontdate() {
        return executiontdate;
    }

    public void setExecutiontdate(LocalDateTime executiontdate) {
        this.executiontdate = executiontdate;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getNoteexecution() {
        return noteexecution;
    }

    public void setNoteexecution(String noteexecution) {
        this.noteexecution = noteexecution;
    }

    public StatusMaintenance getStatus() {
        return status;
    }

    public void setStatus(StatusMaintenance status) {
        this.status = status;
    }

    public String getNoterejected() {
        return noterejected;
    }

    public void setNoterejected(String noterejected) {
        this.noterejected = noterejected;
    }

    public String getNotecanceled() {
        return notecanceled;
    }

    public void setNotecanceled(String notecanceled) {
        this.notecanceled = notecanceled;
    }

        
    
}
