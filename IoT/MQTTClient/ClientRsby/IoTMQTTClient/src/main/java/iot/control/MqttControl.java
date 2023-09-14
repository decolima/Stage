/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.control;

import iot.control.constant.TopicPublisher;
import iot.mqtt.MQTTClient;
import iot.control.constant.TopicSubscription;
import iot.service.MqttService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author andrelima
 */
public class MqttControl {

    private final MqttService ms = new MqttService();
    private final ControllerControl cc = new ControllerControl();
    private MQTTClient mqtt = MQTTClient.getInstance(ms.loadBroker(), cc.getController().getName());

    public void setSubscrition() {

        mqtt = MQTTClient.getInstance(ms.loadBroker(), cc.getController().getName());
        mqtt.connect();
        for (TopicSubscription topic : TopicSubscription.values()) {
            try {
                mqtt.subscribe(cc.getController().getName() + "/" + topic.toString());
            } catch (InterruptedException e) {
                Logger.getLogger(MqttControl.class.getName()).log(Level.SEVERE, null, e);

                String currentDirectory = System.getProperty("user.dir");
                String logFileName = currentDirectory + File.separator + "errorlog.txt";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
                    writer.write(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " - Class: " + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + e.getMessage());
                    writer.newLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }

    public void publish(JSONObject obj, TopicPublisher tp) {

        mqtt = MQTTClient.getInstance(ms.loadBroker(), cc.getController().getName());
        mqtt.connect();
        System.out.println(obj.toString());
        String topic = "WebCompany/";
        if (cc.getController().getMain_id() != null) {

            topic = cc.getController().getCompany() + "/";
        }

        try {
            mqtt.publish(topic + tp.toString(), obj.toString(), 0);

            System.out.println("Published on: " + topic + tp.toString());

        } catch (Exception e) {
            System.out.println(e.toString());

            String currentDirectory = System.getProperty("user.dir");
            String logFileName = currentDirectory + File.separator + "errorlog.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
                writer.write(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " - Class: " + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + e.getMessage());
                writer.newLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

    }

}
