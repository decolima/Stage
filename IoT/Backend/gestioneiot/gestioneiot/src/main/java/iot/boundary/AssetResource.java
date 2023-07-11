/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.boundary;

import iot.entity.Asset;
import iot.store.AssetStore;
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
@Path("asset")
/*@Asset(name = "Gestione TAGs", description = "Permette di gestire gli TAG")*/
@DenyAll
public class AssetResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    private AssetStore storeAsset;

    @RolesAllowed({"ADMIN"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Permette la registrazione di un nuovo Asset")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "Nuovo Asset creata con successo"),
        @APIResponse(responseCode = "404", description = "Creazione di Asset fallito")
    })
    public Response create(@Valid Asset entity) {
        

        Asset saved = storeAsset.save(entity);
        
        return Response.status(Response.Status.CREATED)
                .entity(saved)
                .build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Restituisce l'elenco di tutti gli Asset")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Elenco ritornato con successo"),
        @APIResponse(responseCode = "404", description = "Elenco non trovato")
    })
    @RolesAllowed({"Admin","User"})
    public List<Asset> all() {
        return storeAsset.all();
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Aggiorna i dati dell Asset")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Asset aggirnato con successo"),
        @APIResponse(responseCode = "404", description = "Aggiornamento falito")
            
    })
    @RolesAllowed("Admin")
    public Asset update(@PathParam("id") Long id, @Valid Asset entity) {
        Asset found = storeAsset.find(id).orElseThrow(() -> new NotFoundException("user non trovato. id=" + id));
        entity.setId(id);
        return storeAsset.update(entity);
    }

    @DELETE
    @Path("{id}")
    @Operation(description = "Elimina una risorsa Asset tramite l'ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Asset eliminato con successo"),
        @APIResponse(responseCode = "404", description = "Asset non trovato")

    })
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    public Response delete(@PathParam("id") Long id) {
        Asset found = storeAsset.find(id).orElseThrow(() -> new NotFoundException("Asset non trovato. id=" + id));
        storeAsset.remove(found);
        return Response.status(Response.Status.OK)
                .build();
    }
    
    
    
    
}
