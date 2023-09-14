package manager.mqtt;

import javax.inject.Inject;
import manager.store.CompanyStore;
import manager.store.MqttStore;

/**
 *
 * @author andrelima
 *
 */

public class MqttServiceBean {

    //private MQTTClient mqttClient; 
    @Inject
    private MqttStore s_mqtt;
    
    @Inject
    private CompanyStore s_company;

    public void initialize() {
        
       // mqttClient = MQTTClient.getInstance(ConfigReader.getBrokerUrl(), ConfigReader.getClientId());
       // mqttClient.connect();
        try {
            setSubscrition();
        } catch (Exception e) {
            System.out.println("Erro ao fazer subscription");
        }
       

    }

    public void cleanup () {
        // mqttClient.disconnect();
        s_mqtt.Disconnect();
    }
    
    private void setSubscrition() {
        
        s_mqtt.setSubscrition(s_company.all());
        
    }
    
}
