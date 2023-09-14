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
import manager.entity.MessageIn;
import manager.store.AssetStore;
import manager.store.MessageInStore;

/**
 *
 * @author AndreLima
 */
public class MessageInTypeAdapter implements JsonbAdapter<MessageIn, JsonObject>  {
    
    @Inject
    MessageInStore store;

    @Override
    public JsonObject adaptToJson(MessageIn entity) throws Exception {
        return entity.toJsonSlice();
    }

    @Override
    public MessageIn adaptFromJson(JsonObject json) throws Exception {
        if (!json.containsKey("id")) {
            return null;
        }
        return store.find(json.getJsonNumber("id").longValue()).orElseThrow(() -> new NotFoundException("MessageIn.adaptFromJson not found"));
    }
    
    
    
}
