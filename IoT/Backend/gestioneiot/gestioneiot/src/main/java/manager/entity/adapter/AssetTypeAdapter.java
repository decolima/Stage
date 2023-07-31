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
import manager.store.AssetStore;

/**
 *
 * @author AndreLima
 */
public class AssetTypeAdapter implements JsonbAdapter<Asset, JsonObject>  {
    
    @Inject
    AssetStore store;

    @Override
    public JsonObject adaptToJson(Asset entity) throws Exception {
        return entity.toJsonSlice();
    }

    @Override
    public Asset adaptFromJson(JsonObject json) throws Exception {
        if (!json.containsKey("id")) {
            return null;
        }
        return store.find(json.getJsonNumber("id").longValue()).orElseThrow(() -> new NotFoundException("Asset.adaptFromJson not found"));
    }
    
    
    
}
