/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.mqtt;


import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


/**
 *
 * @author andrelima
 */

public class MQTTClient {

    private MqttClient client;
    private final String broker;
    private final String clientId;
    private final MemoryPersistence persistence;

    public MQTTClient(String broker, String clientId) {
        this.broker = broker;
        this.clientId = clientId;
        this.persistence = new MemoryPersistence();
    }

    public void connect() {
        try {
            client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);
            System.out.println("Connected");

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String topic) {
        try {
            System.out.println("Subscribing to topic: " + topic);
            client.setCallback(new MqttCallback() {
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Received message: " + new String(message.getPayload()));
                }

                public void connectionLost(Throwable cause) {
                    System.out.println("Connection lost: " + cause.getMessage());
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });
            client.subscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String topic, String message) {
        try {
            System.out.println("Publishing message: " + message);
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            client.publish(topic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            client.disconnect();
            System.out.println("Disconnected");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /*
    public static void main(String[] args) {
        String broker = "tcp://localhost:1883";
        String clientId = "JavaMQTTClient";
        String topic = "test/topic";
        String message = "Hello, MQTT!";

        MQTTClient mqttClient = new MQTTClient(broker, clientId);
        mqttClient.connect();
        mqttClient.subscribe(topic);
        mqttClient.publish(topic, message);
        mqttClient.disconnect();
    }
    */
}
