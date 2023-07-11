/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.boundary;

import iot.entity.Tag;
import iot.store.TagStore;
import java.util.List;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;


/**
 *
 * @author Filipe Copola Cornacchia 4Laser Group
 */
@Path("tag")
/*@Tag(name = "Gestione TAGs", description = "Permette di gestire gli TAG")*/
@DenyAll
public class tagResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    private TagStore storeTag;

    @RolesAllowed({"ADMIN"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Permette la registrazione di un nuovo Tag")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "Nuovo Tag creata con successo"),
        @APIResponse(responseCode = "404", description = "Creazione di Tag fallito")
    })
    public Response create(@Valid Tag entity) {
        

        Tag saved = storeTag.save(entity);
        
        return Response.status(Response.Status.CREATED)
                .entity(saved)
                .build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Restituisce l'elenco di tutti gli Tag")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Elenco ritornato con successo"),
        @APIResponse(responseCode = "404", description = "Elenco non trovato")
    })
    @RolesAllowed({"Admin","User"})
    public List<Tag> all() {
        return storeTag.all();
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Aggiorna i dati dell Tag")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Tag aggirnato con successo"),
        @APIResponse(responseCode = "404", description = "Aggiornamento falito")
            
    })
    @RolesAllowed("Admin")
    public Tag update(@PathParam("id") Long id, @Valid Tag entity) {
        Tag found = storeTag.find(id).orElseThrow(() -> new NotFoundException("user non trovato. id=" + id));
        entity.setId(id);
        return storeTag.update(entity);
    }

    @DELETE
    @Path("{id}")
    @Operation(description = "Elimina una risorsa Tag tramite l'ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Tag eliminato con successo"),
        @APIResponse(responseCode = "404", description = "Tag non trovato")

    })
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    public Response delete(@PathParam("id") Long id) {
        Tag found = storeTag.find(id).orElseThrow(() -> new NotFoundException("Tag non trovato. id=" + id));
        storeTag.remove(found);
        return Response.status(Response.Status.OK)
                .build();
    }
    
    
    
    
}
