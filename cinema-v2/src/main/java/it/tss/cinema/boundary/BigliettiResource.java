/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.tss.cinema.boundary;



import it.tss.cinema.Boundary;
import it.tss.cinema.control.BigliettoStore;
import it.tss.cinema.control.ProgrammazioneStore;
import it.tss.cinema.entity.Biglietto;
import it.tss.cinema.entity.Film;
import it.tss.cinema.entity.Programmazione;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 *
 * @author ospite
 */
@DenyAll
@Boundary
@Path("biglietti")
public class BigliettiResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    BigliettoStore store;
    
    @Inject
    ProgrammazioneStore ProgrammazioneStore;
    
    /*
    
        this.programmazione = programmazione;
        this.utente = utente;
        this.tipo = tipo;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.nome_cliente = nome_cliente;   
    
    
    @RolesAllowed({"ADMIN"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Programmazione> creaBiglietto(@Valid bigliettiDTO  e) {  
        //Programmazione foundProgramma = ProgrammazioneStore.findById(e.film_id).orElseThrow(() -> new NotFoundException());  
        List<Biglietto> listaBigletti = new ArrayList<>();

 
                //.forEach(posti -> {
                //        listp.add(
                 //   BigliettoStore.save(new Biglietto(e.programmazione, e.data_bigletto, e.post_x,v,e.post_y))
                 //               );
                 //             }
                 //   );
        
        
         //return listaBigletti;

        
    }
 */
 
    

    @RolesAllowed({"ADMIN"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Biglietto> search(
            @QueryParam("programmazione_id") Long programmazioneId,
            @QueryParam("utente_id") Long utenteId,
            @DefaultValue("1") @QueryParam("page") Integer page,
            @QueryParam("per_page") Integer perPage) {
        return store.search(programmazioneId, utenteId,  page, perPage);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Biglietto find(@PathParam("id") Long id) {
        Biglietto found = store.findById(id).orElseThrow(() -> new NotFoundException());
        checkUser(found.getUtente().getId());
        return found;
    }

    @RolesAllowed({"ADMIN", "USER"})
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") Long id) {
        Biglietto found = store.findById(id).orElseThrow(() -> new NotFoundException());
        checkUser(found.getUtente().getId());
        store.remove(found.getId());
    }

    private void checkUser(Long id) {
        if (jwt.getGroups().contains("ADMIN")) {
            return;
        }
        long loggedId = Long.parseLong(jwt.getSubject());
        if (loggedId != id) {
            throw new BadRequestException("Id biglietto non valido");
        }
    }
    
    @RolesAllowed({"ADMIN"})
    @GET
    @Path("programmazione/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Biglietto> byProgrammazioneId(@PathParam("id") Long programmazione_id) {
        return store.byProgrammazioneId(programmazione_id);
    }
    
    

}