/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.mqtt.mapping;

import manager.store.PublishStore;
import manager.store.TagStore;
import manager.mqtt.constant.TopicSubscription;
import java.util.List;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import manager.entity.Controller;
import manager.entity.Publish;
import manager.entity.Tag;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 *
 * @author andrelima
 */
public class ObjectfromJson {
    
    @Inject
    TagStore s_tag;

    @Inject
    PublishStore s_publish;
    
    
    private final List<Tag> tags = new ArrayList<>();
    private Publish ph = new Publish();
    private String error = "";
    
    public ObjectfromJson(JSONObject obj, TopicSubscription tp) {
        
         switch (tp) {
            case Discovery:
                TagDiscovery(obj);
                break;
            case Error:
                Error(obj);
                break;
            case PublishConfirmation:
                PublishConfirmation(obj);
                break;
        }
        
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Publish getPh() {
        return ph;
    } 

    public String getError() {
        return error;
    }
    
    
    private void TagDiscovery(JSONObject obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void Error(JSONObject obj) {
        
       /* JSONArray jtags = new JSONArray(obj.getAsString("tags"));
        
        for (Object t : jtags) {

            JSONObject o = new JSONObject(t.toString());
            Tag tg = tc.findAddress(o.getString("address"));

            if (tg.getId() != null) {

                String name = o.getString("name") != null ? o.getString("name") : "";
                String status = o.getString("status") != null ? o.getString("status") : "99";

                if (name != "") {
                    tg.setName(name);
                }

                if (status != "99") {
                    tg.setStatus(Integer.valueOf(status));
                }

                tags.add(tg);
            }
        }*/

    }


    private void PublishConfirmation(JSONObject obj) {
        try {
           Long id = Long.valueOf(obj.getAsString("publishid") != null ? obj.getAsString("publishid") : "0");
           String status = obj.getAsString("status") != null ? obj.getAsString("status") : "99";

           this.ph = s_publish.find(id).orElseThrow(() -> new NotFoundException("Publish not found. id=" + id));
           this.ph.setStatus(Integer.valueOf(status));   
        } catch (Exception e) {
            System.out.println("Error to getPublication");
        }

    }

   
}
