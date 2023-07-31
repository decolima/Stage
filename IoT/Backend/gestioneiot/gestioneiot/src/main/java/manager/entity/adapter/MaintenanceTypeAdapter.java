/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.entity.adapter;

import javax.json.bind.adapter.JsonbAdapter;
import javax.json.JsonObject;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import manager.entity.Asset;
import manager.entity.Maintenance;
import manager.store.AssetStore;
import manager.store.MaintenanceStore;

/**
 *
 * @author AndreLima
 */
public class MaintenanceTypeAdapter implements JsonbAdapter<Maintenance, JsonObject>  {
    
    @Inject
    MaintenanceStore store;

    @Override
    public JsonObject adaptToJson(Maintenance entity) throws Exception {
        return entity.toJsonSlice();
    }

    @Override
    public Maintenance adaptFromJson(JsonObject json) throws Exception {
        if (!json.containsKey("id")) {
            return null;
        }
        return store.find(json.getJsonNumber("id").longValue()).orElseThrow(() -> new NotFoundException("Maintenance.adaptFromJson not found"));
    }
    
    
    
}
