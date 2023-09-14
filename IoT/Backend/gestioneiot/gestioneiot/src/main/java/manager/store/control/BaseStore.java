/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.store.control;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author andrelima
 * @param <TEntity>
 */
@Transactional(Transactional.TxType.REQUIRED)
public class BaseStore<TEntity> {

    @PersistenceContext
    protected EntityManager em;

    public TEntity save(TEntity obj) {

        try {
            return em.merge(obj);
        } catch (Exception e) {
            return obj;
        }

    }

    public TEntity update(TEntity obj) {

        try {
            return em.merge(obj);
        } catch (Exception e) {
            return obj;
        }

    }

    public void remove(TEntity obj) {

        update(obj);

    }

}
