package manager.mqtt;

import javax.inject.Inject;
import manager.store.MqttStore;
//import javax.ejb.Singleton;
//import javax.ejb.Startup;

/**
 *
 * @author andrelima
 *
 */

public class MqttServiceBean {

    //private MQTTClient mqttClient; 
    @Inject
    private MqttStore s_mqtt;

    //@PostConstruct
    public void initialize() {
        
       // mqttClient = MQTTClient.getInstance(ConfigReader.getBrokerUrl(), ConfigReader.getClientId());
       // mqttClient.connect();

        setSubscrition();

    }

    //@PreDestroy
    public void cleanup () {
        // mqttClient.disconnect();
        s_mqtt.Disconnect();
    }
    
    private void setSubscrition() {
        
        s_mqtt.setSubscrition();
        
    }
    
}
