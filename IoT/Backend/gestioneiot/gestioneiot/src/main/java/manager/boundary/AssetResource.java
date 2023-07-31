/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.boundary;

import manager.entity.TypeAsset;
import manager.store.TypeAssetStore;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import manager.entity.Asset;
import manager.security.JWTManager;
import manager.store.AssetStore;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


/**
 *
 * @author André Lima
 */
@Path("asset")
@Tag(name = "Asset Management", description = "Allows you to manage Assets")
@DenyAll
public class AssetResource {

    @Context
    UriInfo uriInfo;

    @Inject
    private JWTManager jwtManager;

    @Inject
    private JsonWebToken token;

    @Claim(value = "sub")
    private String sub;


    @Inject
    private AssetStore sasset;
    
    @Inject
    private TypeAssetStore stypeasset;

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Returns the list of all Asset")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "List returned successfully"),
        @APIResponse(responseCode = "404", description = "List not found")
    })
    @RolesAllowed({"Admin","User"})
    public List<Asset> all() {
        return sasset.all();
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Allows the registration of a new Asset")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "New Asset successfully created"),
        @APIResponse(responseCode = "404", description = "Asset creation failed")
    })
    @RolesAllowed({"Admin", "User"})
    public Response create(@Valid Asset entity) {
       
        try {
            Asset saved = sasset.save(entity);
            return Response.status(Response.Status.CREATED)
                    .entity(saved)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Update data of Asset")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Asset successfully updated"),
        @APIResponse(responseCode = "404", description = "Update failed")
            
    })
    @RolesAllowed({"Admin", "User"})
    public Response update(@Valid Asset entity) {
        try {
            Asset found = sasset.find(entity.getId()).orElseThrow(() -> new NotFoundException("Asset not found"));
            found = sasset.update(entity);
            return Response.status(Response.Status.CREATED)
                    .entity(found)
                    .build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @DELETE
    @Operation(description = "Delete an Asset resource using the ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Asset successfully deleted"),
        @APIResponse(responseCode = "404", description = "Asset not found")

    })
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Admin", "User"})
    public Response delete(@PathParam("id") Long id) {
        try {
            Asset found = sasset.find(id).orElseThrow(() -> new NotFoundException("Asset not found. id=" + id));
            found.setCanceled(true);
            sasset.remove(found);
            return Response.status(Response.Status.OK)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
    
    //Management of TypeAsset
    //@Path("typeasset")
    
    @GET
    @Path("typeasset")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Returns the list of all TyAsset")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "List returned successfully"),
        @APIResponse(responseCode = "404", description = "List not found")
    })
    @RolesAllowed({"Admin","User"})
    public List<TypeAsset> allTypes() {
        return stypeasset.all();
    }
    
    
    @POST
    @Path("typeasset")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Allows the registration of a new TypeAsset")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "New TypeAsset successfully created"),
        @APIResponse(responseCode = "404", description = "Asset creation failed")
    })
    @RolesAllowed({"Admin", "User"})
    public Response createType(@Valid TypeAsset entity) {
       
        try {
            TypeAsset saved = stypeasset.save(entity);
            return Response.status(Response.Status.CREATED)
                    .entity(saved)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
    
    @PUT
    @Path("typeasset")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Update data of TypeAsset")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "TypeAsset successfully updated"),
        @APIResponse(responseCode = "404", description = "Update failed")
            
    })
    @RolesAllowed({"Admin", "User"})
    public Response updateType(@Valid TypeAsset entity) {
        try {
            TypeAsset found = stypeasset.find(entity.getId()).orElseThrow(() -> new NotFoundException("Asset not found"));
            found = stypeasset.update(entity);
            return Response.status(Response.Status.CREATED)
                    .entity(found)
                    .build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @DELETE
    @Path("typeasset")
    @Operation(description = "Delete an Asset resource using the ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Asset successfully deleted"),
        @APIResponse(responseCode = "404", description = "Asset not found")

    })
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Admin", "User"})
    public Response deleteType(@PathParam("id") Long id) {
        try {
            TypeAsset found = stypeasset.find(id).orElseThrow(() -> new NotFoundException("Asset not found. id=" + id));
            found.setCanceled(true);
            stypeasset.remove(found);
            return Response.status(Response.Status.OK)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
    
    //manager of log of presence or missing
    
}
