package it.tss.iot.control;

import java.util.stream.Stream;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import it.tss.iot.Control;
import it.tss.iot.entity.Azienda;
import java.util.List;

@Control
public class FilmStore extends AbstractStore<Azienda> {

    public FilmStore() {
        super(Azienda.class);
    }

    @PersistenceContext
    EntityManager em;

    public List<Azienda> all() {
        return  em.createNamedQuery(Azienda.FIND_ALL, Azienda.class)
                .getResultList();
    }
    
    public List<Azienda> findById() {
        return em.createNamedQuery(Azienda.FIND_BY_ID, Azienda.class)
                .getResultList();
    }

}