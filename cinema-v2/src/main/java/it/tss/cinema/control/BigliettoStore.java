/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.tss.cinema.control;

import it.tss.cinema.AcquistoBiglietto;
import it.tss.cinema.Control;
import it.tss.cinema.RinunciaBiglietto;
import it.tss.cinema.entity.Biglietto;
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
public class BigliettoStore extends AbstractStore<Biglietto> {

    @Inject
    @AcquistoBiglietto
    Event<Biglietto> acquistoEvent;

    @Inject
    @RinunciaBiglietto
    Event<Biglietto> rinunciaEvent;

    @Inject
    @ConfigProperty(name = "query.result.max", defaultValue = "10")
    int defaultPageSize;

    public BigliettoStore() {
        super(Biglietto.class);
    }

    @Override
    public Biglietto save(Biglietto e) {
        if (e.getId() == null) {
            acquistoEvent.fire(e);
        }
        return super.save(e);
    }

    @Override
    public void remove(Long id) {
        Biglietto found = findById(id).orElseThrow(() -> new NotFoundException());
        rinunciaEvent.fire(found);
        em.remove(found);
    }

    
    public List<Biglietto> byProgrammazioneId(Long programmazioneId) {
        return em.createNamedQuery(Biglietto.FIND_BY_PROGRAMMAZZIONE)
                .setParameter("programmazione_id", programmazioneId)
                .getResultList();
    }

    public List<Biglietto> byProgrammazioneUtente(Long programmazioneId, Long utenteId) {
        return em.createNamedQuery(Biglietto.FIND_BY_PROGRAMMAZZIONE_UTENTE)
                .setParameter("utente_id", utenteId)
                .setParameter("programmazione_id", programmazioneId)
                .getResultList();
    }

    public List<Biglietto> search(
            Long programmazioneId,
            Long userId,
            Integer page,
            Integer perPage) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Biglietto> q = cb.createQuery(Biglietto.class);
        Root<Biglietto> root = q.from(Biglietto.class);
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
        Root root = query.from(Biglietto.class);
        query.select(cb.count(root)).where(searchPredicate(cb, root, programmazioneId, userId));
        return em.createQuery(query).getSingleResult();
    }

    public Predicate searchPredicate(CriteriaBuilder cb,
            Root<Biglietto> root,
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