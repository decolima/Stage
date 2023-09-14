/*
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.control.threads;

import iot.control.ControllerControl;
import iot.control.JsonMaps.MessagetoJson;
import iot.control.JsonMaps.ObjectfromJson;
import iot.control.MessageInControl;
import iot.control.MqttControl;
import iot.control.PublishControl;
import iot.control.TagControl;
import iot.control.constant.TopicPublisher;
import iot.control.constant.TypeMessageIn;
import iot.entity.Tag;
import iot.service.control.ErrorLog;
import java.time.LocalDateTime;
import org.json.JSONObject;
import org.json.JSONException;

/**
 *
 * @author andrelima
 */
public class SubscribedTopics extends Thread {

    private final String message;
    private final MessageInControl mrc = new MessageInControl();
    private final TagControl tc = new TagControl();
    private final PublishControl pc = new PublishControl();
    private final MqttControl mqtt = new MqttControl();
    private final ControllerControl cc_c = new ControllerControl();

    public SubscribedTopics(String msg) {
        super("SubscribeTopics: " + LocalDateTime.now().toString());
        this.message = msg;
    }

    @Override
    public void run() {

        System.out.println("Process MessageReceved Started - " + super.getName());
        messagereceved(message);

    }

    private void messagereceved(String message) {

        try {

            JSONObject obj = new JSONObject(message);

            if (obj.getString("type") != null) { // todo Authorization token

                if (obj.getString("type").equals(TypeMessageIn.TagConfig.toString())) {

                    //message to update name and status of Tags
                    if (obj.get("tags") != null) {

                        Long idmessage = mrc.saveMessage(message);

                        ObjectfromJson ofj = new ObjectfromJson(obj, TypeMessageIn.TagConfig);

                        for (Tag t : ofj.getTags()) {
                            tc.save(t);
                        }

                        mrc.updateMessage(idmessage);

                        MessageConfirmation(obj);

                    } else {
                        System.out.println("Bad Structure of Message Tag - ignored");
                    }
                } else {
                    //message update status of publishTagDiscovery
                    if (obj.getString("type").equals(TypeMessageIn.PublishConfirmation.toString())) {

                        if (obj.getString("publishid") != null) {

                            Long idmessage = mrc.saveMessage(message);

                            ObjectfromJson ofj = new ObjectfromJson(obj, TypeMessageIn.PublishConfirmation);

                            if (ofj.getPh().getId() != 0) {

                                pc.save(ofj.getPh());

                            } else {
                                System.out.println("Publishid not present - ignored");
                            }

                            mrc.updateMessage(idmessage);

                        } else {
                            System.out.println("Bad Structure of Message Publish - ignored");
                        }

                    } else {
                        if (obj.getString("type").equals(TypeMessageIn.ControllerConfig.toString())) {

                            if (obj.getJSONObject("controller") != null) {

                                Long idmessage = mrc.saveMessage(message);

                                ObjectfromJson ofj = new ObjectfromJson(obj, TypeMessageIn.ControllerConfig);

                                if (ofj.isChangeController()) {
                                    cc_c.save(ofj.getCc());
                                    mrc.updateMessage(idmessage);

                                    MessageConfirmation(obj);

                                }

                            }
                        }
                    }
                }
            } else {
                System.out.println("Bad Structure of Message Type - ignored");
            }
        } catch (NumberFormatException | JSONException e) {
            System.out.println("Erro SubcribedTopics: " + e.getMessage());
            ErrorLog.logError(e);
        }
    }

    private void MessageConfirmation(JSONObject obj) {
       
        MessagetoJson msg = new MessagetoJson();
        msg.PublishConfirmation(obj, "1");
        mqtt.publish(msg.getJsPublish(), TopicPublisher.PublishConfirmation);
        
    }

}
