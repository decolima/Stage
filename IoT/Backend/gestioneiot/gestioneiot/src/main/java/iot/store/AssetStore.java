/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.store;

import iot.entity.Asset;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Filipe Copola Cornacchia 4Laser Group
 */
public class AssetStore extends BaseStore<Asset> {
    
        public List<Asset> all() {

        return em.createQuery("select e from Asset e where e.canceled = false",Asset.class)
                .getResultList();

    }
        
        
      public Optional<Asset> find(Long id){
        
        Asset found = em.find(Asset.class, id);
       
        return found == null ? Optional.empty() : Optional.of(found);
        
    } 

    @Override
    public Asset save(Asset entity){
    

        return super.save(entity);
        
    }      
    
}
