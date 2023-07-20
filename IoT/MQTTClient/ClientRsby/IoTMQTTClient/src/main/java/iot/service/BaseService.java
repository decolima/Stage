/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author andrelima
 * @param <TEntity>
 */
@Transactional(Transactional.TxType.REQUIRED)
public class BaseService<TEntity> {

    @PersistenceContext
    EntityManager em;

    public TEntity save(TEntity obj) {

        em.getTransaction().begin();
        obj = em.merge(obj);
        em.getTransaction().commit();
        return obj;

    }

    public TEntity update(TEntity obj) {

        em.getTransaction().begin();
        obj = em.merge(obj);
        em.getTransaction().commit();
        return obj;
    }

    public boolean remove(TEntity obj) {

        try {
            em.getTransaction().begin();
            em.remove(obj);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
