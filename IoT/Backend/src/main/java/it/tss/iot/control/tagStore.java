/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.tss.iot.control;

import it.tss.iot.AcquistoBiglietto;
import it.tss.iot.Control;
import it.tss.iot.RinunciaBiglietto;
import it.tss.iot.entity.TAG;
import java.util.List;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.NotFoundException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author ospite
 */
@Control
public class tagStore extends AbstractStore<TAG> {

    @Inject
    @AcquistoBiglietto
    Event<TAG> acquistoEvent;

    @Inject
    @RinunciaBiglietto
    Event<TAG> rinunciaEvent;

    @Inject
    @ConfigProperty(name = "query.result.max", defaultValue = "10")
    int defaultPageSize;

    public tagStore() {
        super(TAG.class);
    }

    @Override
    public TAG save(TAG e) {
        if (e.getId() == null) {
            acquistoEvent.fire(e);
        }
        return super.save(e);
    }

    @Override
    public void remove(Long id) {
        TAG found = findById(id).orElseThrow(() -> new NotFoundException());
        rinunciaEvent.fire(found);
        em.remove(found);
    }

    
    public List<TAG> byProgrammazioneId(Long programmazioneId) {
        return em.createNamedQuery(TAG.FIND_BY_PROGRAMMAZZIONE)
                .setParameter("programmazione_id", programmazioneId)
                .getResultList();
    }

    public List<TAG> byProgrammazioneUtente(Long programmazioneId, Long utenteId) {
        return em.createNamedQuery(TAG.FIND_BY_PROGRAMMAZZIONE_UTENTE)
                .setParameter("utente_id", utenteId)
                .setParameter("programmazione_id", programmazioneId)
                .getResultList();
    }

    public List<TAG> search(
            Long programmazioneId,
            Long userId,
            Integer page,
            Integer perPage) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TAG> q = cb.createQuery(TAG.class);
        Root<TAG> root = q.from(TAG.class);
        q.select(root).
                where(searchPredicate(cb, root, programmazioneId, userId));
        int maxResult = perPage == null ? defaultPageSize : perPage;
        return em.createQuery(q)
                .setFirstResult((page - 1) * maxResult)
                .setMaxResults(maxResult)
                .getResultList();
    }

    public long searchCount(Long programmazioneId, Long userId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root root = query.from(TAG.class);
        query.select(cb.count(root)).where(searchPredicate(cb, root, programmazioneId, userId));
        return em.createQuery(query).getSingleResult();
    }

    public Predicate searchPredicate(CriteriaBuilder cb,
            Root<TAG> root,
            Long programmazioneId,
            Long userId) {
        Predicate result = cb.conjunction();
        if (programmazioneId != null) {
            result = cb.and(result, cb.equal(
                    root.get("programmazione")
                            .get("id"), programmazioneId));
        }
        if (userId != null) {
            result = cb.and(result, cb.equal(root.get("utente").get("id"), userId));
        }
 

        return result;
    }
}