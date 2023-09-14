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
import manager.entity.Maintenance;

/**
 *
 * @author André Lima
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class MaintenanceStore extends BaseStore<Maintenance> {
    
        public List<Maintenance> all() {

        return em.createQuery("select e from Maintenance e where e.canceled = false",Maintenance.class)
                .getResultList();

    }
        
        
      public Optional<Maintenance> find(Long id){
        
        Maintenance found = em.find(Maintenance.class, id);
       
        return found == null ? Optional.empty() : Optional.of(found);
        
    } 

 
}
