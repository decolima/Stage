/*
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager.mqtt;

import manager.mqtt.mapping.ObjectfromJson;
import manager.store.MessageInStore;
import manager.store.PublishStore;
import manager.entity.constant.TypeMessageIn;
import java.time.LocalDateTime;
import manager.entity.MessageIn;
import javax.enterprise.context.RequestScoped;
import manager.entity.TagDiscoveryLog;
import manager.mqtt.constant.TopicPublisher;
import manager.mqtt.mapping.MessagetoJson;
import manager.store.MqttStore;
import manager.store.TagDiscoveryLogStore;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

/**
 *
 * @author andrelima
 */
@RequestScoped
public class SubscribedTopics extends Thread {

    private final TagDiscoveryLogStore s_tagdl = new TagDiscoveryLogStore();
    private final MessageInStore s_messagein = new MessageInStore(); 
    private final PublishStore s_publish = new PublishStore();
    private final MqttStore s_mqtt = new MqttStore();
    
    private final String message;
    

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

        System.out.println("Message arrived: " + message); 
        MessageIn msg = s_messagein.savemsg(message);
        
        try {

            JSONParser parser = new JSONParser(); 
            JSONObject obj = (JSONObject) parser.parse(message);
          

            if (obj.getAsString("type") != null) { // todo Authorization token

                if (obj.getAsString("type").equals(TypeMessageIn.TagDiscovery.toString())) {

                    //message to informe tagDiscovery
                    if (obj.get("tags") != null) {
                        
                        ObjectfromJson ofj = new ObjectfromJson(obj, TypeMessageIn.TagDiscovery.toString());

                        if(!ofj.getTagDiscovery().isEmpty()){
                            
                            for (TagDiscoveryLog t : ofj.getTagDiscovery()) {                        
                                t.setMsg(msg);
                                s_tagdl.save(t);
                            }
                            
                            MessagetoJson mtj = new MessagetoJson();
                            mtj.PublishConfirmation(obj, "1"); //status 1 Proceed with success
                            JSONObject controller = (JSONObject) obj.get("controller");
                            
                            s_mqtt.publish(controller.getAsString("controller") + "/" + TopicPublisher.PublishConfirmation.toString(), mtj.getJsPublish().toJSONString());
                            
                        }
                        else {
                            System.out.println("No related tag found in the message - ignored");  
                        }
                        msg.setStatus(1);
                        s_messagein.updatemsg(msg);

                    } else {
                        System.out.println("Bad Structure of Message TagLog - ignored");
                        msg.setStatus(99);
                        s_messagein.updatemsg(msg);
                    }
                } else {
                    //message update status of publishTagDiscovery
                    if (obj.getAsString("type").equals(TypeMessageIn.PublishConfirmation.toString())) {

                        if (obj.getAsString("publishid") != null) {

                            ObjectfromJson ofj = new ObjectfromJson(obj,TypeMessageIn.PublishConfirmation.toString());
                            
                            if (ofj.getPh().getId() != 0) {

                                s_publish.updateByMqtt(ofj.getPh());
                                System.out.println("Publish updated: " + ofj.getPh().getId().toString());

                            } else {
                                System.out.println("Publishid not present - ignored");
                            }

                        } else {
                            System.out.println("Bad Structure of Message Publish - ignored");
                            msg.setStatus(99);
                            s_messagein.updatemsg(msg);
                        }

                    } else {
                        //other type of message   
                    }
                }
            } else {
                System.out.println("Bad Structure of Message Type - ignored");
                msg.setStatus(99);
                s_messagein.updatemsg(msg);
            }
        }
        catch (Exception e) {
            System.out.println("Erro SubcribedTopics: " + e.getMessage());
            msg.setStatus(99);
            s_messagein.updatemsg(msg);
        }
        
    }
            
}
