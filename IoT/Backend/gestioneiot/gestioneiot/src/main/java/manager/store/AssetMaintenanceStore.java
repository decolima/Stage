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
import manager.entity.AssetMaintenance;

/**
 *
 * @author André Lima
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class AssetMaintenanceStore extends BaseStore<AssetMaintenance> {
    
        public List<AssetMaintenance> all(Long id) {

        return em.createQuery("select e from AssetMaintenance e where e.maintenance.id = :id AND e.canceled = false",AssetMaintenance.class)
                .setParameter("id", id)
                .getResultList();

    }
        
        
      public Optional<AssetMaintenance> find(Long id){
        
        AssetMaintenance found = em.find(AssetMaintenance.class, id);
        
        return found == null ? Optional.empty() : Optional.of(found);
        
    } 

 
}
