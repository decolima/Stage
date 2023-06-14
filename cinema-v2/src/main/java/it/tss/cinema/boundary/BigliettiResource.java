/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.tss.cinema.boundary;


import javax.ws.rs.InternalServerErrorException;

import it.tss.cinema.Boundary;
import it.tss.cinema.control.BigliettoStore;
import it.tss.cinema.control.FilmStore;
import it.tss.cinema.control.ProgrammazioneStore;
import it.tss.cinema.entity.Biglietto;
import it.tss.cinema.entity.Film;
import it.tss.cinema.entity.Programmazione;
import it.tss.cinema.entity.Utente;
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
import javax.ws.rs.core.Response;
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
    
  
    
@RolesAllowed({"ADMIN"})
@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Biglietto create(Biglietto biglietto) {
    try {
        // Controlla se la Programmazione è valida
        Programmazione programmazione = validateProgrammazione(biglietto.getProgrammazione());

        // Controlla se l'utente è valido
        Utente utente = validateUtente(biglietto.getUtente());

        // Controlla se il Tipo è valido
        Biglietto.Tipo tipo = validateTipo(biglietto.getTipo());

        // Impostare le proprietà selezionate nell'oggetto Biglietto
        biglietto.setProgrammazione(programmazione);
        biglietto.setUtente(utente);
        biglietto.setTipo(tipo);

        // Salva l'oggetto Biglietto
        Biglietto savedBiglietto = store.save(biglietto);

        // Restituisci l'oggetto Biglietto salvato
        return savedBiglietto;
    } catch (BadRequestException e) {
        // Cattura eccezione richiesta errata
        // e restituire una risposta con stato 400 e messaggio di errore in JSON
        throw e;
    } catch (Exception e) {
        // Cattura altre eccezioni e restituisci una risposta con stato 500 e un messaggio di errore generico
        throw new InternalServerErrorException("Erro interno do servidor.");
    }
}

// Metodo per validare la proprietà "programmazione"
private Programmazione validateProgrammazione(Programmazione programmazione) {
    if (programmazione == null) {
        throw new BadRequestException("Dados inválidos. A propriedade 'programmazione' é obrigatória.");
    }
    
    Programmazione found = ProgrammazioneStore.findById(programmazione.getId())
            .orElseThrow(() -> new NotFoundException());
    
 
    
    return found;
}

// Metodo per convalidare la proprietà "user".
private Utente validateUtente(Utente utente) {
    if (utente == null) {
        throw new BadRequestException("Dados inválidos. A propriedade 'utente' é obrigatória.");
    }
    
 
    
    return utente;
}

// Metodo per convalidare la proprietà "type".
private Biglietto.Tipo validateTipo(Biglietto.Tipo tipo) {
    if (tipo == null) {
        throw new BadRequestException("Dados inválidos. A propriedade 'tipo' é obrigatória.");
    }

    
    return tipo;
}

  
    

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
    
