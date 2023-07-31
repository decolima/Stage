/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.store;

import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import manager.entity.Asset;
import manager.entity.Tag;
import java.util.ArrayList;
import java.util.Objects;
/**
 *
 * @author André Lima
 */
public class AssetStore extends BaseStore<Asset> {
    
    List<Tag> tags = new ArrayList<>();
    
    @Inject
    private PublishStore s_publish;
    
    @Inject
    private TagStore s_tag;
    
    public List<Asset> all() {

        return em.createQuery("select e from Asset e where e.canceled = false",Asset.class)
                .getResultList();

    }
        
        
    public Optional<Asset> find(Long id){
        
        Asset found = em.find(Asset.class, id);
       
        return found == null ? Optional.empty() : Optional.of(found);
        
    }
    
    
    public Asset assetfromTag(Tag tg) {

        return em.createQuery("select e from Asset e where e.canceled = false AND e.tag.id = :id",Asset.class)
                .setParameter("id", tg.getId())
                .getSingleResult();
    }

    @Override
    public Asset update(Asset obj) {
       
        Asset old = new Asset();
        
        try {
            old = find(obj.getId()).orElseThrow();
        } catch (Exception e) {
            
            System.out.println("Has no old version of Asset");   
        }
        
        Asset saved = super.update(obj);
        
        if(saved.getTag() != null) {
            
            tags.clear();
            
            if(old.getTag() != null) {
                
                if(!Objects.equals(saved.getTag().getId(), old.getTag().getId())) {                               
                
                    old.getTag().setStatus(3);
                    s_tag.remove(old.getTag());

                    tags.add(old.getTag());
                    tags.add(saved.getTag());

                    s_publish.updateTag(tags, saved.getController());
                }
                
            }
            else {
                tags.add(saved.getTag());
                s_publish.updateTag(tags, saved.getController());
            }
        }
        
        return saved;
    }

    @Override
    public Asset save(Asset obj) {
        Asset saved = super.save(obj);
        tags.clear();
        
        if(saved.getTag() != null) {
            
            tags.add(saved.getTag());
            
            s_publish.updateTag(tags, saved.getController());   
        }
        
        return saved ;
    }

    
 
}
