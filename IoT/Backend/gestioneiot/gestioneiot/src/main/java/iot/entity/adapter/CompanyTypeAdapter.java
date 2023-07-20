/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.entity.adapter;

import iot.entity.Company;
import javax.json.bind.adapter.JsonbAdapter;
import javax.json.JsonObject;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import iot.store.UserStore;
import iot.entity.User;
import iot.store.CompanyStore;

/**
 *
 * @author AndreLima
 */
public class CompanyTypeAdapter implements JsonbAdapter<Company, JsonObject>  {
    
    @Inject
    CompanyStore store;

    @Override
    public JsonObject adaptToJson(Company entity) throws Exception {
        return entity.toJsonSlice();
    }

    @Override
    public Company adaptFromJson(JsonObject json) throws Exception {
        if (!json.containsKey("id")) {
            return null;
        }
        return store.find(json.getJsonNumber("id").longValue()).orElseThrow(() -> new NotFoundException("CompanyTypeAdapter.adaptFromJson not found"));
    }
    
    
    
}
