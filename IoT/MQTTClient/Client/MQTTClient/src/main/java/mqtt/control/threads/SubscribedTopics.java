/*
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mqtt.control.threads;

import mqtt.control.MessageRecevedControl;
import mqtt.control.PublishLogControl;
import mqtt.control.TagControl;
import mqtt.control.constant.TypeMessage;
import mqtt.entity.PublishLog;
import mqtt.entity.Tag;
import mqtt.mqtt.MQTTClient;
import java.time.LocalDateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONException;

/**
 *
 * @author andrelima
 */
public class SubscribedTopics extends Thread {

    private final String message;
    private final MessageRecevedControl mrc = new MessageRecevedControl();
    private final TagControl tc = new TagControl();
    private final PublishLogControl pc = new PublishLogControl();
    private MQTTClient mqtt;

    public SubscribedTopics(String msg) {
        super("SubscribeTopics: " + LocalDateTime.now().toString());
        this.message = msg;
    }

    @Override
    public void run() {

        System.out.println("Process MessageReceved Started - " + super.getName()); 
        //System.out.println("Process MessageReceved Started - " );
        //messagereceved(message + super.getName());
        messagereceved(message);
        //this.interrupt();

    }

    private void messagereceved(String message) {

        //System.out.println(message); 
        //Long idmessage = mrc.saveMessage(message);
        try {

            JSONObject obj = new JSONObject(message);

            JSONObject objmain = new JSONObject(obj.getString("main"));
            //System.out.println(objmain); 
            //System.out.println(objmain.get("type")); 

            if (obj.getString("main") != null) {

                if (objmain.getString("type") != null) {

                    if (objmain.getString("type").equals(TypeMessage.TagConfig.toString())) {

                        //message to update name and status of Tags
                        if (obj.get("tags") != null) {

                            Long idmessage = mrc.saveMessage(message);

                            JSONArray tags = new JSONArray(obj.get("tags").toString());
                            List<Tag> listtag = new ArrayList<>();

                            for (Object t : tags) {

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

                                    listtag.add(tg);
                                }

                                System.out.println(o.get("name").toString());

                            }

                            for (Tag t : listtag) {
                                tc.save(t);
                            }

                            mrc.updateMessage(idmessage);

                        } else {
                            System.out.println("Bad Structure of Message Tag - ignored");
                        }
                    } else {
                        //message update status of publish
                        if (objmain.getString("type").equals(TypeMessage.PublishConfirmation.toString())) {

                            if (obj.get("publish") != null) {

                                Long idmessage = mrc.saveMessage(message);

                                JSONObject o = new JSONObject(obj.get("publish").toString());

                                Long id = Long.valueOf(o.getString("publishid") != null ? o.getString("publishid") : "0");

                                PublishLog p = new PublishLog();
                                p = pc.find(id);

                                if (id != 0 || p.getId() == null) {

                                    String status = o.getString("status") != null ? o.getString("status") : "99";

                                    p.setStatus(Integer.valueOf(status));

                                    pc.save(p);

                                } else {
                                    System.out.println("Publishid not present - ignored");
                                }

                                mrc.updateMessage(idmessage);

                            } else {
                                System.out.println("Bad Structure of Message Publish - ignored");
                            }

                        } else {
                            //other type of message   
                        }
                    }
                } else {
                    System.out.println("Bad Structure of Message Type - ignored");
                }
            } else {
                System.out.println("No Structure of Message - ignored");
            }
        }
        catch (NumberFormatException | JSONException e) {
            System.out.println("Erro SubcribedTopics: " + e.getMessage());
        }
    }
            
}
