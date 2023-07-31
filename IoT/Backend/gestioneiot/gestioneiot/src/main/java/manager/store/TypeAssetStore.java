/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.store;

import manager.entity.TypeAsset;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author André Lima
 */
public class TypeAssetStore extends BaseStore<TypeAsset> {
    
        public List<TypeAsset> all() {

        return em.createQuery("select e from TypeAsset e where e.canceled = false",TypeAsset.class)
                .getResultList();

    }
        
        
      public Optional<TypeAsset> find(Long id){
        
        TypeAsset found = em.find(TypeAsset.class, id);
       
        return found == null ? Optional.empty() : Optional.of(found);
        
    } 

 
}
