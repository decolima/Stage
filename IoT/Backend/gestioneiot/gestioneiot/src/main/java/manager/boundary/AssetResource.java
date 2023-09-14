/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.boundary;

import java.util.ArrayList;
import manager.entity.TypeAsset;
import manager.store.TypeAssetStore;
import java.util.List;
import java.util.Objects;
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
import manager.entity.Tag;
import manager.entity.MessageOut;
import manager.mqtt.constant.TopicPublisher;
import manager.security.JWTManager;
import manager.store.AssetStore;
import manager.store.AssetTagLogStore;
import manager.store.MessageOutStore;
import manager.store.MqttStore;
import manager.store.PublishStore;
import manager.store.TagStore;
import net.minidev.json.JSONObject;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

/**
 *
 * @author André Lima
 */
@Path("asset")
@org.eclipse.microprofile.openapi.annotations.tags.Tag(name = "Asset Management", description = "Allows you to manage Assets for only Controller")
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
    private AssetStore s_asset;

    @Inject
    private AssetTagLogStore s_assettaglog;
    
    @Inject
    private TypeAssetStore s_typeasset;

    @Inject
    private MqttStore s_mqtt;

    @Inject
    private PublishStore s_publish;

    @Inject
    private MessageOutStore s_msgout;

    @Inject
    private TagStore s_tag;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Returns the list of all Asset")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "List returned successfully"),
        @APIResponse(responseCode = "404", description = "List not found")
    })
    @RolesAllowed({"Admin", "User"})
    public List<Asset> all() {
        return s_asset.all();
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
            Asset saved = s_asset.save(entity);

            if (saved.getTag() != null) {

                MessageOut msgOut = s_publish.updateTag(saved.getTag(), saved.getController());
                msgOut = s_msgout.save(msgOut);

                try {
                    s_mqtt.publish(saved.getController().getName() + "/" + TopicPublisher.TagConfig.toString(), msgOut.getMessage());
                    msgOut.setStatus(1);
                    s_msgout.update(msgOut);
                } catch (Exception e) {
                    msgOut.setStatus(99);
                    s_msgout.update(msgOut);
                    System.out.println("Error to sent SaveTag");
                }
            }

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
            Asset found = s_asset.find(entity.getId()).orElseThrow(() -> new NotFoundException("Asset not found"));
            Tag tagfound = found.getTag();
            entity.setVersion(found.getVersion());

            Tag entityTag = s_tag.find(entity.getTag().getId()).orElseThrow(() -> new NotFoundException("Tag not found"));

            if (entityTag.getStatus() != 1) {
                throw new Exception("Tag not acceptable."); 
            }

            Asset saved = s_asset.update(entity);

            List<Tag> tags = new ArrayList<>();

            if (saved.getTag() != null) {
                if (found.getTag() != null) {
                    if (!Objects.equals(saved.getTag().getId(), found.getTag().getId())) {
                        found.getTag().setStatus(3);
                        s_tag.remove(found.getTag());
                        tags.add(found.getTag());
                        tags.add(saved.getTag());
                    }
                } else {
                    tags.add(saved.getTag());
                }
            } else {
                if (found.getTag() != null) {
                    found.getTag().setStatus(3);
                    s_tag.remove(found.getTag());
                    tags.add(found.getTag());
                }
            }

            if (!tags.isEmpty()) {

                MessageOut msgOut = s_publish.updateTag(tags, found.getController());
                msgOut = s_msgout.save(msgOut);

                try {
                    s_mqtt.publish(saved.getController().getName() + "/" + TopicPublisher.TagConfig.toString(), msgOut.getMessage());
                    msgOut.setStatus(1);
                    s_msgout.update(msgOut);
                } catch (Exception e) {
                    msgOut.setStatus(99);
                    s_msgout.update(msgOut);
                    System.out.println("Error to sent UpdateTag");
                }

            }

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
    public Response delete(@PathParam("id") Long id
    ) {
        try {
            Asset found = s_asset.find(id).orElseThrow(() -> new NotFoundException("Asset not found. id=" + id));
            found.setCanceled(true);
            s_asset.remove(found);
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
    @Operation(description = "Returns the list of all TypeAsset")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "List returned successfully"),
        @APIResponse(responseCode = "404", description = "List not found")
    })
    @RolesAllowed({"Admin", "User"})
    public List<TypeAsset> allTypes() {
        return s_typeasset.all();
    }

    @POST
    @Path("typeasset")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Allows the registration of a new TypeAsset")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "New TypeAsset successfully created"),
        @APIResponse(responseCode = "404", description = "TypeAsset creation failed")
    })
    @RolesAllowed({"Admin", "User"})
    public Response createType(@Valid TypeAsset entity
    ) {

        try {
            TypeAsset saved = s_typeasset.save(entity);
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
        @APIResponse(responseCode = "404", description = "TypeAsset Update failed")

    })
    @RolesAllowed({"Admin", "User"})
    public Response updateType(@Valid TypeAsset entity
    ) {
        try {
            TypeAsset found = s_typeasset.find(entity.getId()).orElseThrow(() -> new NotFoundException("TypeAsset not found"));
            entity.setVersion(found.getVersion());
            found = s_typeasset.update(entity);
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
        @APIResponse(responseCode = "200", description = "TypeAsset successfully deleted"),
        @APIResponse(responseCode = "404", description = "TypeAsset not found")

    })
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"Admin", "User"})
    public Response deleteType(@PathParam("id") Long id
    ) {
        try {
            TypeAsset found = s_typeasset.find(id).orElseThrow(() -> new NotFoundException("TypeAsset not found. id=" + id));
            found.setCanceled(true);
            s_typeasset.remove(found);
            return Response.status(Response.Status.OK)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    //manager of log of presence or missing
    //Path("monitoring")
    @GET
    @Path("monitoring")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Get log of Asset/Tag from Controller")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Success"),
        @APIResponse(responseCode = "404", description = "Failed")
    })
    @RolesAllowed({"Admin", "User"})
    public List<JSONObject> monitoring(@DefaultValue("1")
            @QueryParam("page") int page, @DefaultValue("10")
            @QueryParam("size") int size, @DefaultValue("1")
            @QueryParam("id") Long id
    ) {
        System.out.println(token);
        return s_asset.monitoring(id);
    }

}
