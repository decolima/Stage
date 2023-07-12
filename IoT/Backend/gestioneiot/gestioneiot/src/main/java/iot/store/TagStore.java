/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.store;

import iot.entity.Tag;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Filipe Copola Cornacchia 4Laser Group
 */
public class TagStore extends BaseStore<Tag> {
    
        public List<Tag> all() {

        return em.createQuery("select e from Tag e where e.canceled = false",Tag.class)
                .getResultList();

    }
        
        
      public Optional<Tag> find(Long id){
        
        Tag found = em.find(Tag.class, id);
       
        return found == null ? Optional.empty() : Optional.of(found);
        
    } 

       
    
}
