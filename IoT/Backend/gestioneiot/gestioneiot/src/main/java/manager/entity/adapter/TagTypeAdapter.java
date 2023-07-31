/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.entity.adapter;

import manager.entity.Company;
import javax.json.bind.adapter.JsonbAdapter;
import javax.json.JsonObject;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import manager.entity.Tag;
import manager.entity.TypeAsset;
import manager.store.TagStore;
import manager.store.TypeAssetStore;

/**
 *
 * @author AndreLima
 */
public class TagTypeAdapter implements JsonbAdapter<Tag, JsonObject>  {
    
    @Inject
    TagStore store;

    @Override
    public JsonObject adaptToJson(Tag entity) throws Exception {
        return entity.toJsonSlice();
    }

    @Override
    public Tag adaptFromJson(JsonObject json) throws Exception {
        if (!json.containsKey("id")) {
            return null;
        }
        return store.find(json.getJsonNumber("id").longValue()).orElseThrow(() -> new NotFoundException("Tag.adaptFromJson not found"));
    }
    
    
    
}
