/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.mqtt.mapping;

import java.time.LocalDateTime;
import manager.store.PublishStore;
import manager.store.TagStore;
import java.util.List;
import java.util.ArrayList;
import javax.inject.Inject;
import manager.entity.Publish;
import manager.entity.Tag;
import manager.entity.Controller;
import manager.entity.TagDiscoveryLog;
import manager.store.ControllerStore;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 *
 * @author andrelima
 */
public class ObjectfromJson {

    private TagStore s_tag = new TagStore();
    private PublishStore s_publish = new PublishStore();
    private ControllerStore s_controller = new ControllerStore();

    private final List<Tag> tags = new ArrayList<>();
    private final List<TagDiscoveryLog> tags_dl = new ArrayList<>();
    private Publish ph = new Publish();
    private String error = "";
    private Controller controller = new Controller();

    public ObjectfromJson(JSONObject obj, String tp) {

        switch (tp) {
            case "TagDiscovery":
                TagDiscovery(obj);
                break;
            case "Error":
                Error(obj);
                break;
            case "PublishConfirmation":
                PublishConfirmation(obj);
                break;
            case "Controller":
                Controller(obj);
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

    public Controller getController() {
        return controller;
    }

    public List<TagDiscoveryLog> getTagDiscovery() {
        return tags_dl;
    }

    public void TagDiscovery(JSONObject obj) {

        tags_dl.clear();

        JSONArray listobj = (JSONArray) obj.get("tags");

        for (Object js : listobj) {
            
            JSONObject j = (JSONObject) js;

            TagDiscoveryLog tdl = new TagDiscoveryLog();
            tdl.setTag(s_tag.findTag(j.getAsString("address")));
            if (tdl.getTag().getId() != null) {

                int st = Integer.valueOf(j.getAsString("status"));
                tdl.setStatus(st);
                tdl.setStatus_use(j.getAsString("used"));

                if (!tdl.getTag().getName().equals(j.getAsString("name"))) {
                    tdl.getTag().setName(j.getAsString("name"));
                }
            }
            else {
                Controller(obj);
                if(controller.getDiscovery() == 1) {
                    
                    tdl.getTag().setAddress(j.getAsString("address"));
                    tdl.getTag().setName(j.getAsString("name"));
                    tdl.getTag().setStatus(Integer.parseInt(j.getAsString("status")));
                    tdl.getTag().setStatus_use(Integer.parseInt(j.getAsString("used")));
                    tdl.getTag().setActivation(LocalDateTime.now());
                }
            }

            tags_dl.add(tdl);
        }

    }

    public void Error(JSONObject obj) {

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

    public void PublishConfirmation(JSONObject obj) {
        
        try {

            String id = obj.getAsString("publishid") != null ? obj.getAsString("publishid") : "0";
            String status = obj.getAsString("status") != null ? obj.getAsString("status") : "99";

            this.ph = s_publish.findPublish(id);
            this.ph.setStatus(Integer.parseInt(status));

        } catch (Exception e) {
            System.out.println("Error to getPublication");
        }

    }

    public void Controller(JSONObject obj) {

        JSONObject c = (JSONObject) obj.get("controller");
        
        controller = s_controller.find(Long.valueOf(c.getAsString("controlerid"))).orElse(new Controller());

    }

}
