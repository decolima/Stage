/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.tss.iot.boundary;

import it.tss.iot.Boundary;
import it.tss.iot.control.SalaStore;
import it.tss.iot.entity.Azienda;
import it.tss.iot.entity.Asset;
import java.util.List;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ospite
 */
@DenyAll
@Boundary
@Path("sale")
public class AziendaResource {

    @Inject
    SalaStore store;

    @RolesAllowed({"ADMIN"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Asset create(Asset e) {
        return store.save(e);
    }

    @RolesAllowed({"ADMIN","USER"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Asset> all() {
        return store.all();
    }
    
    @RolesAllowed({"ADMIN"})
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Asset update(@PathParam("id") Long id, Asset e) {
        e.setId(id);
        return store.save(e);
    }    
    

    @RolesAllowed({"ADMIN","USER"})
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Asset find(@PathParam("id") Long id) {
        return store.findById(id).orElseThrow(() -> new NotFoundException());
    }
    
    @RolesAllowed({"ADMIN"})
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        store.remove(id);
    }
}