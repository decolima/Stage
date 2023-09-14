/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.control.JsonMaps;

import iot.control.constant.TopicPublisher;
import iot.control.constant.TypeMessageOut;
import iot.entity.Controller;
import iot.entity.Publish;
import iot.entity.maps.TagLog;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;

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

    public MessagetoJson(Publish pl, List<TagLog> listlog, Controller cc, String aux, TypeMessageOut tm) {

        switch (tm) {
            case TagDiscovery:
                TagDiscovery(pl, listlog, cc, tm);
                break;
            case Error:
                Error(pl, cc, aux, tm);
                break;
        }

    }

    private void TagDiscovery(Publish pl, List<TagLog> listlog, Controller cc, TypeMessageOut tm) {
        JSONArray tags = new JSONArray();

        for (TagLog t : listlog.stream().filter(a -> a.getStatus_tag() == 1).collect(Collectors.toList())) {
            JSONObject obj = new JSONObject();
            obj.put("address", (t.getAddress() != null ? t.getAddress() : ""));
            obj.put("name", (t.getName() != null ? t.getName() : ""));
            obj.put("used", (t.getStatus() != null ? t.getStatus() : ""));
            obj.put("status", t.getStatus_tag());
            obj.put("discovery", (t.getDatadiscovery() != null ? t.getDatadiscovery() : ""));

            tags.put(obj);
        }

        for (TagLog t : listlog.stream().filter(a -> a.getStatus_tag() == 9).collect(Collectors.toList())) {
            JSONObject obj = new JSONObject();
            obj.put("address", (t.getAddress() != null ? t.getAddress() : ""));
            obj.put("name", (t.getName() != null ? t.getName() : ""));
            obj.put("used", (t.getStatus() != null ? t.getStatus() : ""));
            obj.put("status", t.getStatus_tag());
            obj.put("discovery", (t.getDatadiscovery() != null ? t.getDatadiscovery() : ""));

            tags.put(obj);
        }

        if (cc.getDiscovery() == 1) {

            for (TagLog t : listlog.stream().filter(a -> a.getStatus_tag() == 0).collect(Collectors.toList())) {
                JSONObject obj = new JSONObject();
                obj.put("address", (t.getAddress() != null ? t.getAddress() : ""));
                obj.put("name", (t.getName() != null ? t.getName() : ""));
                obj.put("used", (t.getStatus() != null ? t.getStatus() : ""));
                obj.put("status", t.getStatus_tag());
                obj.put("discovery", (t.getDatadiscovery() != null ? t.getDatadiscovery() : ""));

                tags.put(obj);
            }

        }

        JSONObject controller = new JSONObject();
        controller.put("controller", (cc.getName() != null ? cc.getName() : ""));
        controller.put("controllerid", (cc.getMain_id() != null ? cc.getMain_id() : ""));

        JSONObject main = new JSONObject();
        main.put("publishid", pl.getId().toString());
        main.put("sentdate", pl.getPublishdate().format(DateTimeFormatter.ISO_DATE_TIME));
        main.put("type", tm.toString());
        main.put("auth", " - "); //to do
        main.put("controller", controller);
        main.put("tags", tags);

        jsPublish = main;
    }

    private void Error(Publish pl, Controller cc, String aux, TypeMessageOut tm) {

        JSONObject controller = new JSONObject();
        controller.put("controller", (cc.getName() != null ? cc.getName() : ""));
        controller.put("controllerid", (cc.getMain_id() != null ? cc.getMain_id() : ""));

        JSONObject main = new JSONObject();
        main.put("publishid", pl.getId().toString());
        main.put("sentdate", pl.getPublishdate().format(DateTimeFormatter.ISO_DATE_TIME));
        main.put("type", tm.toString());
        main.put("auth", " - "); //to do
        main.put("controler", controller);
        main.put("error", aux);

        jsPublish = main;

    }

    public void PublishConfirmation(JSONObject obj, String status) {

        JSONObject controller = (JSONObject) obj.get("controller");

        JSONObject main = new JSONObject();
        main.put("publishid", obj.getString("publishid"));
        main.put("sentdate", obj.getString("sentdate"));
        main.put("type", TopicPublisher.PublishConfirmation.toString());
        main.put("status", status); //to do
        main.put("auth", " - "); //to do
        main.put("controller", controller);

        jsPublish = main;

    }
}
