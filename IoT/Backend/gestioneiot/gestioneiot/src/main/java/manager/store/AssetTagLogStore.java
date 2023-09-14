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
import manager.entity.Asset;
import manager.entity.AssetTagLog;

/**
 *
 * @author André Lima
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class AssetTagLogStore extends BaseStore<AssetTagLog> {

    public List<AssetTagLog> all() {

        return em.createQuery("select e from AssetTagLog e where e.canceled = false", AssetTagLog.class)
                .getResultList();

    }

    public Optional<AssetTagLog> find(Long id) {

        AssetTagLog found = em.find(AssetTagLog.class, id);

        return found == null ? Optional.empty() : Optional.of(found);

    }

    public AssetTagLog findLog(Asset a) {

        AssetTagLog found = new AssetTagLog();
        
        try {
            found = em.createQuery("select e from AssetTagLog e where e.asset.id = :id AND e.status = 1 AND e.canceled = false", AssetTagLog.class)
                    .setParameter("id", a.getId())
                    .getSingleResult();
        } catch (Exception e) {

            System.out.println("There is no Tag for this Asset");
            
        }

        return found;
    }

}
