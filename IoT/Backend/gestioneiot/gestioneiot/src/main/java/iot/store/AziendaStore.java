/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.store;

import iot.entity.Azienda;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Filipe Copola Cornacchia 4Laser Group
 */
public class AziendaStore extends BaseStore<Azienda> {
    
        public List<Azienda> all() {

        return em.createQuery("select e from Azienda e where e.canceled = false",Azienda.class)
                .getResultList();

    }
        
        
      public Optional<Azienda> find(Long id){
        
        Azienda found = em.find(Azienda.class, id);
       
        return found == null ? Optional.empty() : Optional.of(found);
        
    } 

    @Override
    public Azienda save(Azienda entity){
    

        return super.save(entity);
        
    }      
    
}
