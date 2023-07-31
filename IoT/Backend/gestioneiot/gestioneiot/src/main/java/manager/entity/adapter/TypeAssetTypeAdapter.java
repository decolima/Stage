/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.entity.adapter;

import javax.json.bind.adapter.JsonbAdapter;
import javax.json.JsonObject;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import manager.entity.TypeAsset;
import manager.store.TypeAssetStore;

/**
 *
 * @author AndreLima
 */
public class TypeAssetTypeAdapter implements JsonbAdapter<TypeAsset, JsonObject>  {
    
    @Inject
    TypeAssetStore store;

    @Override
    public JsonObject adaptToJson(TypeAsset entity) throws Exception {
        return entity.toJsonSlice();
    }

    @Override
    public TypeAsset adaptFromJson(JsonObject json) throws Exception {
        if (!json.containsKey("id")) {
            return null;
        }
        return store.find(json.getJsonNumber("id").longValue()).orElseThrow(() -> new NotFoundException("TypeAsset.adaptFromJson not found"));
    }
    
    
    
}
