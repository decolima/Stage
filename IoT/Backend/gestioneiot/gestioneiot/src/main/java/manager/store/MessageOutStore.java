/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.store;

import manager.store.control.BaseStore;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import manager.entity.MessageOut;

/**
 *
 * @author André Lima
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class MessageOutStore extends BaseStore<MessageOut> {
    
        public List<MessageOut> all() {

        return em.createQuery("select e from MessageOut e where e.canceled = false",MessageOut.class)
                .getResultList();

    }
        
        
      public Optional<MessageOut> find(Long id){
        
        MessageOut found = em.find(MessageOut.class, id);
       
        return found == null ? Optional.empty() : Optional.of(found);
        
    } 
      
      
 
}
