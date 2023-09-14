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
import manager.entity.Controller;

/**
 *
 * @author André Lima
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class ControllerStore extends BaseStore<Controller> {
    
    
    //@Inject
    private final PublishStore s_publish = new PublishStore();

    
    public List<Controller> all() {

        return em.createQuery("select e from Controller e where e.canceled = false", Controller.class)
                .getResultList();

    }
        
    public Optional<Controller> find(Long id){
        
        Controller found = em.find(Controller.class, id);
       
        return found == null ? Optional.empty() : Optional.of(found);
        
    }     
      
}
