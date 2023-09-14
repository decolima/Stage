/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.boundary;

import java.util.List;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import manager.entity.AssetMaintenance;
import manager.entity.Maintenance;
import manager.security.JWTManager;
import manager.store.AssetMaintenanceStore;
import manager.store.MaintenanceStore;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;


/**
 *
 * @author André Lima
 */
@Path("maintenance")
@org.eclipse.microprofile.openapi.annotations.tags.Tag(name = "Maintenance Management", description = "Allows you to manage Maintenances")
@DenyAll
public class MaintenanceResource {

    @Context
    UriInfo uriInfo;

    @Inject
    private JWTManager jwtManager;

    @Inject
    private JsonWebToken token;

    @Claim(value = "sub")
    private String sub;

    @Inject
    private MaintenanceStore s_maintenance;
    
    @Inject
    private AssetMaintenanceStore s_assetmaintenance;
    

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Returns the list of all Maintenances")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "List successfully returned"),
        @APIResponse(responseCode = "404", description = "List not found")
    })
    @RolesAllowed({"Admin","User"})
    public List<Maintenance> all() {
        return s_maintenance.all();
    }
    
    @RolesAllowed({"Admin", "User"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Allows the registration of a new Maintenance")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "New Maintenance created successfully"),
        @APIResponse(responseCode = "404", description = "Failed to create Maintenance")
    })
    public Response create(@Valid Maintenance entity) {
        
        try {
            Maintenance saved = s_maintenance.save(entity);
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
    @Operation(description = "Update Maintenance Data")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Maintenance successfully updated"),
        @APIResponse(responseCode = "404", description = "Maintenance Update failed")
            
    })
    @RolesAllowed("Admin")
    public Response update(@Valid Maintenance entity) {
        try {
            Maintenance found = s_maintenance.find(entity.getId()).orElseThrow(() -> new NotFoundException("Maintenance not found"));
            entity.setVersion(found.getVersion());
            found = s_maintenance.update(entity);
            return Response.status(Response.Status.CREATED)
                    .entity(found)
                    .build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
        
    }

    @DELETE
    @Path("{id}")
    @Operation(description = "Delete a Maintenance resource by ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Maintenance deleted successfully"),
        @APIResponse(responseCode = "404", description = "Maintenance not found")

    })
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    public Response delete(@PathParam("id") Long id) {
        Maintenance found = s_maintenance.find(id).orElseThrow(() -> new NotFoundException("Maintenance not found. id=" + id));
        found.setCanceled(true);
        s_maintenance.remove(found);
        return Response.status(Response.Status.OK)
                .build();
    }
    
    //management of assets of maintenance
    //@Path("assets")
    
    @GET
    @Path("assets")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Returns the list of all Assets of this Maintenance")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "List successfully returned"),
        @APIResponse(responseCode = "404", description = "List not found")
    })
    @RolesAllowed({"Admin","User"})
    public List<AssetMaintenance> allAssets(@PathParam("id") Long id) {
        return s_assetmaintenance.all(id);
    }
    
    
    @POST
    @Path("assets")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Allows the registration of a new Asset Maitenance")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "New AssetMaintenance created successfully"),
        @APIResponse(responseCode = "404", description = "Failed to create Maintenance")
    })
    @RolesAllowed({"Admin", "User"})
    public Response createAssets(@Valid AssetMaintenance entity) {
        
        try {
            AssetMaintenance saved = s_assetmaintenance.save(entity);
            return Response.status(Response.Status.CREATED)
                    .entity(saved)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
    
    @PUT
    @Path("assets")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Update Asset Maintenance Data")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Asset Maintenance successfully updated"),
        @APIResponse(responseCode = "404", description = "Asset Maintenance Update failed")
            
    })
    @RolesAllowed("Admin")
    public Response updateAssets(@Valid AssetMaintenance entity) {
        try {
            AssetMaintenance found = s_assetmaintenance.find(entity.getId()).orElseThrow(() -> new NotFoundException("Asset Maintenance not found"));
            entity.setVersion(found.getVersion());
            found = s_assetmaintenance.update(entity);
            return Response.status(Response.Status.CREATED)
                    .entity(found)
                    .build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
        
    }

    @DELETE
    @Path("assets")
    @Operation(description = "Delete a Asset Maintenance resource by ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Asset Maintenance deleted successfully"),
        @APIResponse(responseCode = "404", description = "Asset Maintenance not found")

    })
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    public Response deleteAssets(@PathParam("id") Long id) {
        AssetMaintenance found = s_assetmaintenance.find(id).orElseThrow(() -> new NotFoundException("Asset Maintenance not found. id=" + id));
        found.setCanceled(true);
        s_assetmaintenance.remove(found);
        return Response.status(Response.Status.OK)
                .build();
    }   
    
}
