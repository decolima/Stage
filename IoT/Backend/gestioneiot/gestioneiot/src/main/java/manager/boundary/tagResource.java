/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.boundary;

import manager.entity.Tag;
import manager.store.TagStore;
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
import manager.entity.Company;
import manager.security.JWTManager;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;


/**
 *
 * @author André Lima
 */
@Path("tag")
@org.eclipse.microprofile.openapi.annotations.tags.Tag(name = "Tag Management", description = "Allows you to manage tags")
@DenyAll
public class TagResource {

    @Context
    UriInfo uriInfo;

    @Inject
    private JWTManager jwtManager;

    @Inject
    private JsonWebToken token;

    @Claim(value = "sub")
    private String sub;

    @Inject
    private TagStore s_tag;

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Returns the list of all tags")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "List successfully returned"),
        @APIResponse(responseCode = "404", description = "List not found")
    })
    @RolesAllowed({"Admin","User"})
    public List<Tag> all() {
        return s_tag.all();
    }
    
    @RolesAllowed({"Admin", "User"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Allows the registration of a new Tag")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "New Tag created successfully"),
        @APIResponse(responseCode = "404", description = "Failed to create Tag")
    })
    public Response create(@Valid Tag entity) {
       try {
            Tag saved = s_tag.save(entity);
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
    @Operation(description = "Update Tag Data")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Tag successfully updated"),
        @APIResponse(responseCode = "404", description = "Tag Update failed")
            
    })
    @RolesAllowed({"Admin", "User"})
    public Response update(@Valid Tag entity) {
        try {
            Tag found = s_tag.find(entity.getId()).orElseThrow(() -> new NotFoundException("Tag not found"));
            found = s_tag.update(entity);
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
    @Operation(description = "Delete a Tag resource by ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Tag deleted successfully"),
        @APIResponse(responseCode = "404", description = "Tag not found")

    })
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    public Response delete(@PathParam("id") Long id) {
        Tag found = s_tag.find(id).orElseThrow(() -> new NotFoundException("tag not found. id=" + id));
        found.setCanceled(true);
        s_tag.remove(found);
        return Response.status(Response.Status.OK)
                .build();
    }
    
}
