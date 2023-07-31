/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.mqtt.mapping;

import java.time.LocalDateTime;
import manager.entity.Controller;
import manager.entity.Publish;
import manager.entity.Tag;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
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

    public MessagetoJson(Publish pl, List<Tag> listtag, Controller cc, String aux, TopicPublisher tp) {

        switch (tp) {
            case TagConfig:
                TagConfig(pl, listtag, cc, tp);
                break;
            case ControllerConfig:
                ControllerConfig(pl, cc, tp);
                break;
            case PublishConfirmation:
                PublishConfirmation(pl, cc, tp);
                break;
        }

    }

    private void TagConfig(Publish pl, List<Tag> listtag, Controller cc, TopicPublisher tp) {
        JSONArray tags = new JSONArray();

        for (Tag t : listtag.stream().filter(a -> a.getStatus() == 9).collect(Collectors.toList())) {
            JSONObject obj = new JSONObject();
            obj.put("address", (t.getAddress() != null ? t.getAddress() : ""));
            obj.put("name", (t.getName() != null ? t.getName() : ""));
            obj.put("status", t.getStatus());
            tags.add(t);
        }

        JSONObject controller = new JSONObject();
        controller.put("controler", (cc.getName() != null ? cc.getName() : ""));
        controller.put("controlerid", (cc.getId() != null ? cc.getId() : ""));

        JSONObject main = new JSONObject();
        main.put("publishid", pl.getId().toString());
        main.put("sentdate", pl.getPublishdate().format(DateTimeFormatter.ISO_DATE_TIME));
        main.put("type", tp.toString());
        main.put("auth", " - "); //to do
        main.put("controler", controller);
        main.put("tags", tags);

        jsPublish = main;
    }

    private void ControllerConfig(Publish pl, Controller cc, TopicPublisher tp) {
        
        JSONObject controller = new JSONObject();
        controller.put("controler", (cc.getName() != null ? cc.getName() : ""));
        controller.put("controlerid", (cc.getId() != null ? cc.getId() : ""));

        JSONObject main = new JSONObject();
        main.put("publishid", pl.getId().toString());
        main.put("sentdate", pl.getPublishdate().format(DateTimeFormatter.ISO_DATE_TIME));
        main.put("type", tp.toString());
        main.put("auth", " - "); //to do
        main.put("controler", controller);

        jsPublish = main;

    }

    private void PublishConfirmation(Publish pl, Controller cc, TopicPublisher tp) {
        
        JSONObject controller = new JSONObject();
        controller.put("controler", (cc.getName() != null ? cc.getName() : ""));
        controller.put("controlerid", (cc.getId() != null ? cc.getId() : ""));

        JSONObject main = new JSONObject();
        main.put("publishid", pl.getId().toString());
        main.put("sentdate", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        main.put("type", tp.toString());
        main.put("auth", " - "); //to do
        main.put("controler", controller);

        jsPublish = main;

    }

}
