/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.entity.adapter;

import javax.json.bind.adapter.JsonbAdapter;
import javax.json.JsonObject;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import manager.entity.Publish;
import manager.store.PublishStore;

/**
 *
 * @author AndreLima
 */
public class PublishTypeAdapter implements JsonbAdapter<Publish, JsonObject>  {
    
    @Inject
    PublishStore store;

    @Override
    public JsonObject adaptToJson(Publish entity) throws Exception {
        return entity.toJsonSlice();
    }

    @Override
    public Publish adaptFromJson(JsonObject json) throws Exception {
        if (!json.containsKey("id")) {
            return null;
        }
        return store.find(json.getJsonNumber("id").longValue()).orElseThrow(() -> new NotFoundException("Publish.adaptFromJson not found"));
    }
    
    
    
}
