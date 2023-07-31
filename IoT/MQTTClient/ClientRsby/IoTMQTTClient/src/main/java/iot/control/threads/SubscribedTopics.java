/*
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.control.threads;

import iot.control.JsonMaps.ObjectfromJson;
import iot.control.MessageInControl;
import iot.control.PublishControl;
import iot.control.TagControl;
import iot.control.constant.TypeMessageIn;
import iot.entity.Tag;
import iot.mqtt.MQTTClient;
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

            if (obj.getString("type") != null) { // todo Authorization token

                if (obj.getString("type").equals(TypeMessageIn.TagConfig.toString())) {

                    //message to update name and status of Tags
                    if (obj.get("tags") != null) {

                        Long idmessage = mrc.saveMessage(message);
                        
                        ObjectfromJson ofj = new ObjectfromJson(obj.getJSONObject("tags"), TypeMessageIn.TagConfig);

                        for (Tag t : ofj.getTags()) {
                            tc.save(t);
                        }

                        mrc.updateMessage(idmessage);

                    } else {
                        System.out.println("Bad Structure of Message Tag - ignored");
                    }
                } else {
                    //message update status of publishTagDiscovery
                    if (obj.getString("type").equals(TypeMessageIn.PublishConfirmation.toString())) {

                        if (obj.get("publish") != null) {

                            Long idmessage = mrc.saveMessage(message);

                            ObjectfromJson ofj = new ObjectfromJson(obj.getJSONObject("publish"), TypeMessageIn.PublishConfirmation);
                            
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
                        //other type of message   
                    }
                }
            } else {
                System.out.println("Bad Structure of Message Type - ignored");
            }
        }
        catch (NumberFormatException | JSONException e) {
            System.out.println("Erro SubcribedTopics: " + e.getMessage());
        }
    }
            
}
