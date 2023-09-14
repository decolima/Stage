/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.mqtt.mapping;

import manager.entity.Controller;
import manager.entity.Publish;
import manager.entity.Tag;
import java.time.format.DateTimeFormatter;
import java.util.List;
import manager.mqtt.constant.TopicPublisher;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 *
 * @author andrelima
 */
public class MessagetoJson {

    JSONObject jsPublish = new JSONObject();

    public JSONObject getJsPublish() {
        return jsPublish;
    }

    public MessagetoJson() {
    }
    
    public MessagetoJson(Publish pl, List<Tag> listtag, Controller cc, String aux, TopicPublisher tp) {

        switch (tp) {
            case TagConfig:
                TagConfig(pl, listtag, cc, tp);
                break;
            case ControllerConfig:
                ControllerConfig(pl, cc, tp);
                break;
        }

    }

    private void TagConfig(Publish pl, List<Tag> listtag, Controller cc, TopicPublisher tp) {
        JSONArray tags = new JSONArray();

        for (Tag t : listtag) {
            JSONObject obj = new JSONObject();
            obj.put("address", (t.getAddress() != null ? t.getAddress() : ""));
            obj.put("name", (t.getName() != null ? t.getName() : ""));
            obj.put("status", t.getStatus());
            tags.add(t);
        }

        JSONObject controller = new JSONObject();
        controller.put("controller", (cc.getName() != null ? cc.getName() : ""));
        controller.put("controlerid", (cc.getId() != null ? cc.getId() : ""));

        JSONObject main = new JSONObject();
        main.put("publishid", pl.getId().toString());
        main.put("sentdate", pl.getPublishdate().format(DateTimeFormatter.ISO_DATE_TIME));
        main.put("type", tp.toString());
        main.put("auth", " - "); //to do
        main.put("controller", controller);
        main.put("tags", tags);

        jsPublish = main;
    }

    private void ControllerConfig(Publish pl, Controller cc, TopicPublisher tp) {
        
        JSONObject controller = new JSONObject();
        controller.put("controller", (cc.getName() != null ? cc.getName() : ""));
        controller.put("main_id", (cc.getId() != null ? cc.getId() : ""));
        controller.put("discovery", cc.getDiscovery());
        controller.put("status", cc.getStatus());
        controller.put("activation", cc.getActivation().format(DateTimeFormatter.ISO_DATE_TIME));
        controller.put("company", cc.getCompany().getName());

        JSONObject main = new JSONObject();
        main.put("publishid", pl.getId().toString());
        main.put("sentdate", pl.getPublishdate().format(DateTimeFormatter.ISO_DATE_TIME));
        main.put("type", tp.toString());
        main.put("auth", " - "); //to do
        main.put("controller", controller);

        jsPublish = main;

    }

    public void PublishConfirmation(JSONObject obj, String status) {
       
        JSONObject controller = (JSONObject) obj.get("controller");

        JSONObject main = new JSONObject();
        main.put("publishid", obj.getAsString("publishid"));
        main.put("sentdate", obj.getAsString("sentdate"));
        main.put("type", TopicPublisher.PublishConfirmation.toString() );
        main.put("status", status); //to do
        main.put("auth", " - "); //to do
        main.put("controller", controller);
        
        
        jsPublish = main;

    }

}
