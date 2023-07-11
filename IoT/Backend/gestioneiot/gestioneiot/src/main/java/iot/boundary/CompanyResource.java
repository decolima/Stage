/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.boundary;

import iot.entity.Company;
import iot.store.CompanyStore;
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
@Path("azienda")
@Tag(name = "Company Management", description = "Allows you to manage Company users")
@DenyAll
public class CompanyResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    private CompanyStore storeAzienda;

    @RolesAllowed({"ADMIN"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Asset Management", description = "Allows you to manage Company")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "New Company successfully created"),
        @APIResponse(responseCode = "404", description = "Company creation failed")
    })
    public Response create(@Valid Company entity) {
        

        Company saved = storeAzienda.save(entity);
        
        return Response.status(Response.Status.CREATED)
                .entity(saved)
                .build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Returns the list of all Company")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "List returned successfully"),
        @APIResponse(responseCode = "404", description = "List not found")
    })
    @RolesAllowed({"Admin","User"})
    public List<Company> all() {
        return storeAzienda.all();
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Update company data")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Company successfully upgraded"),
        @APIResponse(responseCode = "404", description = "Update failed")
            
    })
    @RolesAllowed("Admin")
    public Company update(@PathParam("id") Long id, @Valid Company entity) {
        Company found = storeAzienda.find(id).orElseThrow(() -> new NotFoundException("Company not found. id=" + id));
        entity.setId(id);
        return storeAzienda.update(entity);
    }

    @DELETE
    @Path("{id}")
    @Operation(description = "Delete a Company resource by ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Company successfully deleted"),
        @APIResponse(responseCode = "404", description = "Company not found")

    })
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    public Response delete(@PathParam("id") Long id) {
        Company found = storeAzienda.find(id).orElseThrow(() -> new NotFoundException("Company not found. id=" + id));
        storeAzienda.remove(found);
        return Response.status(Response.Status.OK)
                .build();
    }
    
    
    
    
}
