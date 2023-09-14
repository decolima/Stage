/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.service.control;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author andrelima
 * @param <TEntity>
 */
@Transactional(Transactional.TxType.REQUIRED)
public class BaseService<TEntity> extends ErrorLog{

    @PersistenceContext
    protected EntityManager em;

    public TEntity save(TEntity obj) {

        em = EntityManagerHelper.getEntityManager();

        em.getTransaction().begin();
        obj = em.merge(obj);
        em.getTransaction().commit();
        return obj;

    }

    public TEntity update(TEntity obj) {

        em = EntityManagerHelper.getEntityManager();

        em.getTransaction().begin();
        obj = em.merge(obj);
        em.getTransaction().commit();
        return obj;
    }

    public boolean remove(TEntity obj) {

        try {
            update(obj);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
