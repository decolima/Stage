
package it.tss.cinema.boundary;

import it.tss.cinema.Boundary;
import it.tss.cinema.control.SalaStore;
import it.tss.cinema.entity.Sala;
import it.tss.cinema.control.OcupazioneStore;
import it.tss.cinema.entity.Ocupazione;

import java.util.List;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@DenyAll
@Boundary
@Path("ocupazione")
public class OcupazioneResource {
    
    @Inject
    OcupazioneStore store;

    @RolesAllowed({"ADMIN"})
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Ocupazione create(Ocupazione e) {
        return store.save(e);
    }

    @RolesAllowed({"ADMIN","USER"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ocupazione> all() {
        return store.all();
    }

    @RolesAllowed({"ADMIN","USER"})
    @GET
    @Path("list-by-programmazione")
    @PathParam("{programazzione_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public  List<Ocupazione> find(@QueryParam("id") int  id) {
        return store.findbyprog(id);
    }   
    
    
}
