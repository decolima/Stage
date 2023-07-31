/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package iot.mqtt;

import iot.control.threads.SubscribedTopics;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author andrelima
 * 
 **/


public class MQTTClient {
    
    private static MQTTClient instance;
    private MqttClient client;
    private final String broker;
    private final String clientId;
    private final MemoryPersistence persistence;
    private final BlockingQueue<String> messageQueue;
    private final List<String> subscribedTopics = new ArrayList<>();
    private boolean isConnecting = false;

    private MQTTClient(String broker, String clientId) {
        this.broker = broker;
        this.clientId = clientId;
        this.persistence = new MemoryPersistence();
        this.messageQueue = new LinkedBlockingQueue<>();
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
        }
    }

    private void setupCallbacks() {
        client.setCallback(new MqttCallback() {
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String payload = new String(message.getPayload());
                System.out.println("Received message: " + payload);
                messageQueue.offer(payload);
                
                new Thread(() -> {
                    SubscribedTopics st = new SubscribedTopics(payload);
                    st.start();
                }).start();
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

    public void connect(){
        reconnect();
    }
    
    private void reconnect() {
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

        if (!client.isConnected()) {
            System.err.println("Failed to reconnect after " + maxReconnectAttempts + " attempts. Giving up.");
        }
    }

    public void subscribe(String topic) throws InterruptedException {
        try {
            client.subscribe(topic);
            subscribedTopics.add(topic);
        } catch (MqttException e) {
            System.err.println("Failed to subscribe to topic: " + topic);
            e.printStackTrace();
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
        }
    }

    public void disconnect() {
        try {
            client.disconnect();
            System.out.println("Disconnected");
        } catch (MqttException e) {
            System.err.println("Failed to disconnect");
            e.printStackTrace();
        }
    }

    public BlockingQueue<String> getMessageQueue() {
        return messageQueue;
    }
}


//versao antiga
/*
package iot.mqtt;

import iot.control.threads.SubscribedTopics;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MQTTClient {

    private static MQTTClient instance;
    private MqttClient client;
    private final String broker;
    private final String clientId;
    private final MemoryPersistence persistence;
    private final BlockingQueue<String> messageQueue;
    private final List<String> subscribedTopics = new ArrayList<>();

    private MQTTClient(String broker, String clientId) {
        this.broker = broker;
        this.clientId = clientId;
        this.persistence = new MemoryPersistence();
        this.messageQueue = new LinkedBlockingQueue<>();
    }

    // Singleton
    public static MQTTClient getInstance(String broker, String clientId) {
        if (instance == null) {
            instance = new MQTTClient(broker, clientId);
        }
        return instance;
    }

    public void connect() {
        
        try {
            if (client == null || !client.isConnected()) {
                client = new MqttClient(broker, clientId, persistence);
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true);

                System.out.println("Connecting to broker: " + broker);
                client.connect(connOpts);
                System.out.println("Connected");
            }
            else{
                System.out.println("Already connected with broker: " + broker);
            }
                
        } catch (MqttException e) {
            e.printStackTrace();
        }
        
        /*try {
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

    public void subscribe(String topic) throws InterruptedException {
        try {
            client.setCallback(new MqttCallback() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String payload = new String(message.getPayload());
                    System.out.println("Received message: " + payload);
                    messageQueue.offer(payload);

                    SubscribedTopics st = new SubscribedTopics(payload);
                    st.run();
                }

                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection lost: " + cause.getMessage());
                    // Reconnect and resume subscriptions
                    connect();
                    for (String topic : subscribedTopics) {
                        try {
                            client.subscribe(topic);
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });

            client.subscribe(topic);
            subscribedTopics.add(topic);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String topic, String message, int qos) {
        try {
            System.out.println("Publishing message: " + message);
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            mqttMessage.setQos(qos);
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

    public BlockingQueue<String> getMessageQueue() {
        return messageQueue;
    }
}
*/