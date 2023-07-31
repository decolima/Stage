/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.control;

import iot.entity.MessageIn;
import iot.service.MessageInService;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.NotFoundException;
/**
 *
 * @author andrelima
 */
public class MessageInControl {
    
    private final MessageInService ms = new MessageInService();
    
    public Long saveMessage(String msg){
        
        MessageIn mr = new MessageIn();
        mr.setMessage(msg);
        mr.setRecevedat(LocalDateTime.now());
        mr.setStatus(0);
        mr = ms.save(mr);
        return mr.getId();
    }

    public boolean updateMessage(Long id) {
            
        try {
            MessageIn mr = ms.find(id).orElseThrow(() -> new NotFoundException("MessageReceved not found. id=" + id));
            mr.setStatus(1);
            ms.update(mr);
            return true;
        } catch (NotFoundException ex) {
            Logger.getLogger(MessageInControl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }

}
