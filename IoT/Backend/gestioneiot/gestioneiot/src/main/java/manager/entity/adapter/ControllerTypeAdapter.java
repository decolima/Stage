/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.entity.adapter;

import javax.json.bind.adapter.JsonbAdapter;
import javax.json.JsonObject;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import manager.entity.Controller;
import manager.store.ControllerStore;

/**
 *
 * @author AndreLima
 */
public class ControllerTypeAdapter implements JsonbAdapter<Controller, JsonObject>  {
    
    @Inject
    ControllerStore store;

    @Override
    public JsonObject adaptToJson(Controller entity) throws Exception {
        return entity.toJsonSlice();
    }

    @Override
    public Controller adaptFromJson(JsonObject json) throws Exception {
        if (!json.containsKey("id")) {
            return null;
        }
        return store.find(json.getJsonNumber("id").longValue()).orElseThrow(() -> new NotFoundException("Controller.adaptFromJson not found"));
    }
    
    
    
}
