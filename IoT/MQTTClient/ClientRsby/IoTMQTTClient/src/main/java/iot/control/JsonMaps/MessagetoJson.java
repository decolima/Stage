/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.control.JsonMaps;

import iot.control.constant.TypeMessageOut;
import iot.entity.Controller;
import iot.entity.Publish;
import iot.entity.maps.TagLog;
import java.time.LocalDateTime;
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

    public MessagetoJson(Publish pl, List<TagLog> listlog, Controller cc, String aux, TypeMessageOut tm) {

        switch (tm) {
            case TagDiscovery:
                TagDiscovery(pl, listlog, cc, tm);
                break;
            case Error:
                Error(pl, cc, aux, tm);
                break;
            case PublishConfirmation:
                PublishConfirmation(pl, cc, tm);
                break;
        }

    }

    private void TagDiscovery(Publish pl, List<TagLog> listlog, Controller cc, TypeMessageOut tm) {
        JSONArray tags = new JSONArray();

        for (TagLog t : listlog.stream().filter(a -> a.getStatus_tag() == 9).collect(Collectors.toList())) {
            JSONObject obj = new JSONObject();
            obj.put("address", (t.getAddress() != null ? t.getAddress() : ""));
            obj.put("name", (t.getName() != null ? t.getName() : ""));
            obj.put("used", (t.getStatus() != null ? t.getStatus() : ""));
            obj.put("status", t.getStatus_tag());
            obj.put("discovery", (t.getDatadiscovery() != null ? t.getDatadiscovery() : ""));

            tags.put(obj);
        }

        JSONObject controller = new JSONObject();
        controller.put("controler", (cc.getName() != null ? cc.getName() : ""));
        controller.put("controlerid", (cc.getMain_id() != null ? cc.getMain_id() : ""));

        JSONObject main = new JSONObject();
        main.put("publishid", pl.getId().toString());
        main.put("sentdate", pl.getPublishdate().format(DateTimeFormatter.ISO_DATE_TIME));
        main.put("type", tm.toString());
        main.put("auth", " - "); //to do
        main.put("controler", controller);
        main.put("tags", tags);

        jsPublish = main;
    }

    private void Error(Publish pl, Controller cc, String aux, TypeMessageOut tm) {

        JSONObject controller = new JSONObject();
        controller.put("controler", (cc.getName() != null ? cc.getName() : ""));
        controller.put("controlerid", (cc.getMain_id() != null ? cc.getMain_id() : ""));

        JSONObject main = new JSONObject();
        main.put("publishid", pl.getId().toString());
        main.put("sentdate", pl.getPublishdate().format(DateTimeFormatter.ISO_DATE_TIME));
        main.put("type", tm.toString());
        main.put("auth", " - "); //to do
        main.put("controler", controller);
        main.put("error", aux);

        jsPublish = main;

    }

    private void PublishConfirmation(Publish pl, Controller cc, TypeMessageOut tm) {

        JSONObject controller = new JSONObject();
        controller.put("controler", (cc.getName() != null ? cc.getName() : ""));
        controller.put("controlerid", (cc.getMain_id() != null ? cc.getMain_id() : ""));

        JSONObject main = new JSONObject();
        main.put("publishid", pl.getId().toString());
        main.put("receveddate", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        main.put("type", tm.toString());
        main.put("status", "1");
        main.put("auth", " - "); //to do
        main.put("controler", controller);

        jsPublish = main;

    }
}
