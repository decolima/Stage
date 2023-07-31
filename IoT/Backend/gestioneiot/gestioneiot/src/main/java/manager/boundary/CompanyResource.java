/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.boundary;

import manager.entity.Company;
import manager.store.CompanyStore;
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
import manager.entity.Controller;
import manager.security.JWTManager;
import manager.store.ControllerStore;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 *
 * @author Filipe Copola Cornacchia 4Laser Group
 */
@Path("company")
@Tag(name = "Company Management", description = "Allows you to manage Company users")
@DenyAll
public class CompanyResource {

    @Context
    UriInfo uriInfo;

    @Inject
    private JWTManager jwtManager;

    @Inject
    private JsonWebToken token;

    @Claim(value = "sub")
    private String sub;

    @Inject
    private CompanyStore scompany;

    @Inject
    private ControllerStore scontroller;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Returns the list of all Company")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "List returned successfully"),
        @APIResponse(responseCode = "404", description = "List not found")
    })
    @RolesAllowed({"Admin", "User", "Maintenance"})
    public List<Company> all() {
        return scompany.all();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Asset Management", description = "Allows you to manage Company")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "New Company successfully created"),
        @APIResponse(responseCode = "404", description = "Company creation failed")
    })
    @RolesAllowed("Admin")
    public Response create(@Valid Company entity) {

        try {
            Company saved = scompany.save(entity);
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
    @Operation(description = "Update company data")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Company successfully upgraded"),
        @APIResponse(responseCode = "404", description = "Update failed")

    })
    @RolesAllowed({"Admin", "User"})
    public Response update(@Valid Company entity) {
        try {
            Company found = scompany.find(entity.getId()).orElseThrow(() -> new NotFoundException("Company not found"));
            found = scompany.update(entity);
            return Response.status(Response.Status.CREATED)
                    .entity(found)
                    .build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
        
    }

    @DELETE
    @Operation(description = "Delete a Company resource by ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Company successfully deleted"),
        @APIResponse(responseCode = "404", description = "Company not found")

    })
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    public Response delete(@PathParam("id") Long id) {
        try {
            Company found = scompany.find(id).orElseThrow(() -> new NotFoundException("Company not found. id=" + id));
            found.setCanceled(true);
            scompany.remove(found);
            return Response.status(Response.Status.OK)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }

    }

    // ----- Gestione Controller
    //Path("controller")
    @GET
    @Path("controller")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Returns the list of Controller that Company")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "List returned successfully"),
        @APIResponse(responseCode = "404", description = "List not found")
    })
    @RolesAllowed({"Admin", "User", "Maintenance"})
    public List<Controller> allController() {
        return scontroller.all();
    }

    @POST
    @Path("controller")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Controller Management", description = "Allows you to manage Company")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "New Company successfully created"),
        @APIResponse(responseCode = "404", description = "Company creation failed")
    })
    @RolesAllowed({"Admin", "User"})
    public Response createController(@Valid Controller entity) {

        try {
            Controller saved = scontroller.save(entity);
            return Response.status(Response.Status.CREATED)
                    .entity(saved)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }

    }

    @PUT
    @Path("controller")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Update controller data")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Controller successfully upgraded"),
        @APIResponse(responseCode = "404", description = "Update failed")

    })
    @RolesAllowed({"Admin", "User"})
    public Response updateController(@Valid Controller entity) {
        try {
            Controller found = scontroller.find(entity.getId()).orElseThrow(() -> new NotFoundException("Controller not found"));
            found = scontroller.update(entity);
            return Response.status(Response.Status.CREATED)
                    .entity(found)
                    .build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
        
    }

    @DELETE
    @Path("controller")
    @Operation(description = "Delete a controller resource by ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Company successfully deleted"),
        @APIResponse(responseCode = "404", description = "Company not found")

    })
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Admin", "User"})
    public Response deleteController(@PathParam("id") Long id) {        
        try {
            Controller found = scontroller.find(id).orElseThrow(() -> new NotFoundException("Controller not found. id=" + id));
            found.setCanceled(true);
            scontroller.remove(found);
            return Response.status(Response.Status.OK)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }

    }
}
