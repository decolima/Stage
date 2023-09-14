/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package iot.mqtt;

import iot.control.threads.SubscribedTopics;
import iot.service.control.ErrorLog;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author andrelima
 *
 *
 */
public class MQTTClient extends ErrorLog {

    private static MQTTClient instance;
    private MqttClient client;
    private final String broker;
    private final String clientId;
    private final MemoryPersistence persistence;
    private final BlockingQueue<String> messageQueue;
    private final List<String> subscribedTopics = new ArrayList<>();
    private boolean isConnecting = false;
    private ExecutorService threadPool;

    private MQTTClient(String broker, String clientId) {
        this.broker = broker;
        this.clientId = clientId;
        this.persistence = new MemoryPersistence();
        this.messageQueue = new LinkedBlockingQueue<>();
        this.threadPool = Executors.newFixedThreadPool(100);
        initializeClient();
    }

    // Singleton
    public static MQTTClient getInstance(String broker, String clientId) {
        if (instance == null) {
            instance = new MQTTClient(broker, clientId);
        }
        return instance;
    }

    private void initializeClient() {
        try {
            client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);
            System.out.println("Connected");

            setupCallbacks();
        } catch (MqttException e) {
            System.err.println("Failed to initialize MQTT client:");
            e.printStackTrace();
            logError(e);

        }
    }

    private void setupCallbacks() {
        client.setCallback(new MqttCallback() {
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

                try {

                    String payload = new String(message.getPayload());

                    //messageQueue.offer(payload);
                    System.out.println("Received message: " + LocalDateTime.now().toString());
                    System.out.println("Received message:  " + payload);

                    threadPool.execute(() -> {
                        SubscribedTopics st = new SubscribedTopics(payload);
                        st.start();
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    logError(e);
                }

            }

            @Override
            public void connectionLost(Throwable cause) {
                System.err.println("Connection lost: " + cause.getMessage());
                if (!isConnecting) {
                    reconnect();
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                // Aqui você pode tratar eventos relacionados à entrega das mensagens,
                // mas neste exemplo, não é necessário realizar nenhuma ação específica.
            }
        });
    }

    public void connect() {
        try {
            if (!client.isConnected()) {
                reconnect();
            }
        } catch (Exception e) {
            logError(e);
        }

    }

    private void reconnect() {

        try {

            isConnecting = true;
            int maxReconnectAttempts = 3;
            int reconnectIntervalMillis = 5000;

            int reconnectAttempts = 0;
            while (!client.isConnected() && reconnectAttempts < maxReconnectAttempts) {
                try {
                    System.out.println("Trying to reconnect...");
                    client.reconnect();
                    for (String topic : subscribedTopics) {
                        try {
                            client.subscribe(topic);
                        } catch (MqttException e) {
                            System.err.println("Failed to subscribe to topic: " + topic);
                            e.printStackTrace();
                        }
                    }
                    isConnecting = false;
                    System.out.println("Reconnected");

                    if (!client.isConnected()) {
                        System.err.println("Failed to reconnect after " + maxReconnectAttempts + " attempts. Giving up.");
                    }

                } catch (MqttException e) {
                    System.err.println("Reconnection attempt " + (reconnectAttempts + 1) + " failed. Retrying in 5 seconds...");
                    e.printStackTrace();
                    try {
                        Thread.sleep(reconnectIntervalMillis);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    reconnectAttempts++;
                }
            }

        } catch (Exception e) {
            logError(e);
            disconnect();
            connect();
        }

    }

    public void subscribe(String topic) throws InterruptedException {
        try {
            client.subscribe(topic);
            subscribedTopics.add(topic);
        } catch (MqttException e) {
            System.err.println("Failed to subscribe to topic: " + topic);
            e.printStackTrace();
            logError(e);
        }
    }

    public void publish(String topic, String message, int qos) {
        try {
            System.out.println("Publishing message: " + message);
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(qos);
            client.publish(topic, mqttMessage);
        } catch (MqttException e) {
            System.err.println("Failed to publish message to topic: " + topic);
            e.printStackTrace();
            logError(e);
        }
    }

    public void disconnect() {
        try {
            client.disconnect();
            System.out.println("Disconnected");
        } catch (MqttException e) {
            System.err.println("Failed to disconnect");
            e.printStackTrace();
            logError(e);
        }
    }

    public BlockingQueue<String> getMessageQueue() {
        return messageQueue;
    }

}
