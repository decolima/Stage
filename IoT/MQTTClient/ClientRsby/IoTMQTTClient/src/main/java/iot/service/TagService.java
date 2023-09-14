/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.service;

/**
 *
 * @author andrelima
 */
import iot.service.control.EntityManagerHelper;
import iot.service.control.BaseService;
import iot.entity.Tag;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

@PersistenceContext(type = PersistenceContextType.TRANSACTION)
public class TagService extends BaseService<Tag> {

    public boolean setupTag() {

        this.em = EntityManagerHelper.getEntityManager();

        return em.createQuery("select e from Tag e", Tag.class)
                .setLockMode(LockModeType.NONE)
                .getResultList().isEmpty();
    }

    public List<Tag> getall() {

        this.em = EntityManagerHelper.getEntityManager();

        return em.createQuery("select e from Tag e", Tag.class)
                .setLockMode(LockModeType.NONE)
                .getResultList();
    }

    public Tag find(String address) {

        this.em = EntityManagerHelper.getEntityManager();

        Tag found = new Tag();

        try {
            found = em.createQuery("select e from Tag e WHERE e.address = :address", Tag.class)
                    .setLockMode(LockModeType.NONE)
                    .setParameter("address", address)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println("Tag is new");
        }

        return found;

    }

}
