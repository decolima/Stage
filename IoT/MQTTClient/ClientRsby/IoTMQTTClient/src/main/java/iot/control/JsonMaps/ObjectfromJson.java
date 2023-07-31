/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.control.JsonMaps;

import iot.control.PublishControl;
import iot.control.TagControl;
import iot.control.constant.TopicPublisher;
import iot.control.constant.TypeMessageIn;
import iot.control.constant.TypeMessageOut;
import iot.entity.Controller;
import iot.entity.Publish;
import iot.entity.Tag;
import iot.entity.maps.TagLog;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 *
 * @author andrelima
 */
public class ObjectfromJson {
    private final TagControl tc = new TagControl();
    private final List<Tag> tags = new ArrayList<>();
    private final Controller cc = new Controller();
    private final PublishControl pc = new PublishControl();
    Publish ph = new Publish();
    
    
    public ObjectfromJson(JSONObject obj, TypeMessageIn tm) {
        
         switch (tm) {
            case TagConfig:
                TagConfig(obj);
                break;
            case ControllerConfig:
                ControllerConfig(obj);
                break;
            case PublishConfirmation:
                PublishConfirmation(obj);
                break;
        }
        
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Controller getCc() {
        return cc;
    }

    public Publish getPh() {
        return ph;
    } 
    

    private void TagConfig(JSONObject obj) {
        
        JSONArray jtags = new JSONArray(obj.get("tags").toString());
        
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
        }

    }

    private void ControllerConfig(JSONObject obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void PublishConfirmation(JSONObject obj) {
        
        Long id = Long.valueOf(obj.getString("publishid") != null ? obj.getString("publishid") : "0");
        String status = obj.getString("status") != null ? obj.getString("status") : "99";

        this.ph = pc.find(id);
        this.ph.setStatus(Integer.valueOf(status));

    }


   
}
