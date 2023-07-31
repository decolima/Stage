/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mqtt.control;

import mqtt.entity.MessageReceved;
import mqtt.service.MessageRecevedService;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.NotFoundException;
/**
 *
 * @author andrelima
 */
public class MessageRecevedControl {
    
    private final MessageRecevedService ms = new MessageRecevedService();
    
    public Long saveMessage(String msg){
        
        MessageReceved mr = new MessageReceved();
        mr.setMessage(msg);
        mr.setRecevedat(LocalDateTime.now());
        mr.setStatus(0);
        mr = ms.save(mr);
        return mr.getId();
    }

    public boolean updateMessage(Long id) {
            
        try {
            MessageReceved mr = ms.find(id).orElseThrow(() -> new NotFoundException("MessageReceved not found. id=" + id));
            mr.setStatus(1);
            ms.update(mr);
            return true;
        } catch (NotFoundException ex) {
            Logger.getLogger(MessageRecevedControl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }

}
