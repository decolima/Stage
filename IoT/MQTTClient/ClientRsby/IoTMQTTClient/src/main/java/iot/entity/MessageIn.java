/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;


/**
 *
 * @author andrelima
 */
@Entity
@Table(name = "messagein")
public class MessageIn extends BaseEntity {
    
    private  String message;
    private  LocalDateTime recevedat;
    //0 new message, 1 processed message
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getRecevedat() {
        return recevedat;
    }

    public void setRecevedat(LocalDateTime recevedat) {
        this.recevedat = recevedat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
    
    
    @Override
    public String toString() {
        return "MessageReceved{" + "message=" + message + ", recevedat=" + recevedat + '}';
    }
    
    
}
