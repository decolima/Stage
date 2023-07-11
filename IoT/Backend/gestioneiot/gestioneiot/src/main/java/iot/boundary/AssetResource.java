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
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


/**
 *
 * @author Filipe Copola Cornacchia 4Laser Group
 */
@Path("asset")
@Tag(name = "Asset Management", description = "Allows you to manage assets")
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
    @Operation(description = "Allows the registration of a new Asset")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "New Asset successfully created"),
        @APIResponse(responseCode = "404", description = "Asset creation failed")
    })
    public Response create(@Valid Asset entity) {
        

        Asset saved = storeAsset.save(entity);
        
        return Response.status(Response.Status.CREATED)
                .entity(saved)
                .build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Returns the list of all Asset")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "List returned successfully"),
        @APIResponse(responseCode = "404", description = "List not found")
    })
    @RolesAllowed({"Admin","User"})
    public List<Asset> all() {
        return storeAsset.all();
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Update data of Asset")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Asset successfully updated"),
        @APIResponse(responseCode = "404", description = "Update failed")
            
    })
    @RolesAllowed("Admin")
    public Asset update(@PathParam("id") Long id, @Valid Asset entity) {
        Asset found = storeAsset.find(id).orElseThrow(() -> new NotFoundException("user not found. id=" + id));
        entity.setId(id);
        return storeAsset.update(entity);
    }

    @DELETE
    @Path("{id}")
    @Operation(description = "Delete an Asset resource using the ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Asset successfully deleted"),
        @APIResponse(responseCode = "404", description = "Asset not found")

    })
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    public Response delete(@PathParam("id") Long id) {
        Asset found = storeAsset.find(id).orElseThrow(() -> new NotFoundException("Asset not found. id=" + id));
        storeAsset.remove(found);
        return Response.status(Response.Status.OK)
                .build();
    }
    
    
    
    
}
