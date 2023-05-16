/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.tss.cinema.boundary;

import it.tss.cinema.Boundary;
import it.tss.cinema.control.FilmStore;
import it.tss.cinema.control.ProgrammazioneStore;
import it.tss.cinema.control.SalaStore;
import it.tss.cinema.entity.Film;
import it.tss.cinema.entity.Programmazione;
<<<<<<< Updated upstream
=======
//import it.tss.cinema.entity.Proiezione;
>>>>>>> Stashed changes
import it.tss.cinema.entity.Sala;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ospite
 */
@DenyAll
@Boundary
@Path("programmazioni")
public class ProgrammazioniResource {

    @Inject
    ProgrammazioneStore store;

    //@Inject
    //ProiezioneStore proiezioneStore;
    @Inject
    SalaStore salaStore;

    @Inject
    FilmStore filmStore;

    @RolesAllowed({"ADMIN", "USER"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Programmazione> prossime() {
        return store.prossime();
    }

    @RolesAllowed({"ADMIN", "USER"})
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Programmazione find(@PathParam("id") Long id) {
        return store.findById(id).orElseThrow(() -> new NotFoundException());
    }

    /*
    @RolesAllowed({"ADMIN", "USER"})
    @GET
    @Path("{id}/programmazione")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Programmazione> programmazione(@PathParam("id") Long id) {
        return store.byProgrammazione(id);
    }
     */
    /*
    @RolesAllowed({"ADMIN"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
<<<<<<< Updated upstream
    public  List<Programmazione> creaProgrammazione(@Valid ProgDTO e) {
        Film found = filmStore.findById(e.film_id).orElseThrow(() -> new NotFoundException());
        Optional<Programmazione> searchProgr = store.byFilmAndData(e.film_id, e.data_programmazione);
        //Programmazione p = searchProgr.isEmpty() ?
        salaStore.all()
                .stream().filter(v -> e.tutteSale
                || e.sala_id.contains(v.getId()))
                
                 .forEach(v ->   store.save(
                         
                 new Programmazione(found, e.data_programmazione, e.prezzo,v,e.data_publicazione))) ;
        
        return store.byFilm(e.film_id);
        
    }
  
   
    
  }
      
            

    
=======
    public Programmazione creaProgrammazione(ProgDTO e) {
        Sala foundSala = salaStore.findById(e.id).orElseThrow(() -> new NotFoundException());
        Film f = filmStore.findById(e.id).orElseThrow(() -> new NotFoundException());

          //if (e == null || e.getSala() == null || e.getSala().getId() == null) {
            //throw new BadRequestException();
        //}
        Programmazione p = new Programmazione(f, LocalDate.MIN, BigDecimal.ZERO, foundSala);
        return store.save(p);
    }
    */
    
    @RolesAllowed({"ADMIN"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Programmazione> creaProgrammazione(@Valid ProgDTO e) {
        Film found = filmStore.findById(e.film_id).orElseThrow(() -> new NotFoundException());
        Optional<Programmazione> searchProgr = store.byFilmAndData(e.film_id, e.data_programmazione);
        //Programmazione p = searchProgr.isEmpty() ?
        salaStore.all()
                .stream().filter(v -> e.tutteSale
                || e.sala_id.contains(v.getId()))
                .forEach(v -> store.save(
                new Programmazione(found, e.data_programmazione, e.prezzo, v)));
         return store.byFilm(e.film_id);
    }

    /*
    @RolesAllowed({"ADMIN"})
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Programmazione creaProgrammazione(ProgDTO e) {
        //Film found = FilmStore.findById(e.film_id).orElseThrow(() -> new NotFoundException());
        //Optional<Programmazione> searchProgr = programmazioneStore.byFilmAndData(id, e.il);
        //Programmazione p = searchProgr.isEmpty() ?
  
                salaStore.all()
                .stream().filter(v -> e.tutteSale
                || e.sala_id.contains(v.getId()))
                        
                .forEach(v -> ProgrammazioneStore.save(
                new Programmazione( e.film_id, e.data_programmazione, e.prezzo,v.getId()   ) ));
                
                //Programmazione(Film film, LocalDate data_programmazione, BigDecimal prezzo, Sala sala)
         
    }
     */
}
>>>>>>> Stashed changes
