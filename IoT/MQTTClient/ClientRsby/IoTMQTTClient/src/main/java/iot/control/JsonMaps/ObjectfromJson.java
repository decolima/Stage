/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.control.JsonMaps;

import iot.control.ControllerControl;
import iot.control.PublishControl;
import iot.control.TagControl;
import iot.control.constant.TypeMessageIn;
import iot.entity.Controller;
import iot.entity.Publish;
import iot.entity.Tag;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
    private Controller cc = new Controller();
    private final PublishControl pc = new PublishControl();
    private final ControllerControl cc_c = new ControllerControl();
    private boolean changeController = false;
    
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
    
    public boolean isChangeController(){
        return changeController;
    }

    private void TagConfig(JSONObject obj) {

        JSONArray jtags = new JSONArray(obj.get("tags").toString());

        for (Object t : jtags) {

            JSONObject o = new JSONObject(t.toString());
            Tag tg = tc.findAddress(o.getString("address"));

            String name = o.getString("name") != null ? o.getString("name") : "";
            
            if (name != "") {
                tg.setName(name);
            }

            tg.setStatus(o.getInt("status"));

            if (tg.getId() == null) {
                tg.setAddress(o.getString("address"));
                tg.setActivation(LocalDateTime.now());
            }
            tags.add(tg);
        }

    }

    private void ControllerConfig(JSONObject obj) {
        
        JSONObject jcc = obj.getJSONObject("controller");
        
        cc = cc_c.getController();
        
        if(cc.getName().equals(jcc.getString("controller"))){
         
           cc.setMain_id(jcc.getLong("main_id"));
           cc.setDiscovery(jcc.getInt("discovery"));
           cc.setStatus(jcc.getInt("status"));
           cc.setActivation(LocalDateTime.parse(jcc.getString("activation"), DateTimeFormatter.ISO_DATE_TIME).toString());
           cc.setCompany(jcc.getString("company"));            
           
           changeController = true;
        }
        
    }

    private void PublishConfirmation(JSONObject obj) {

        Long id = Long.valueOf(obj.getString("publishid") != null ? obj.getString("publishid") : "0");
        String status = obj.getString("status") != null ? obj.getString("status") : "99";

        this.ph = pc.find(id);
        this.ph.setStatus(Integer.valueOf(status));

    }

}
