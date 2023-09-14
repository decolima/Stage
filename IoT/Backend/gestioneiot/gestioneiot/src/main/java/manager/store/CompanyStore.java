/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.store;

import manager.store.control.BaseStore;
import manager.entity.Company;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

/**
 *
 * @author Filipe Copola Cornacchia 4Laser Group
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class CompanyStore extends BaseStore<Company> {
    
        public List<Company> all() {

        return em.createQuery("select e from Company e where e.canceled = false",Company.class)
                .getResultList();

    }
        
        
      public Optional<Company> find(Long id){
        
        Company found = em.find(Company.class, id);
       
        return found == null ? Optional.empty() : Optional.of(found);
        
    } 

     
    
}
