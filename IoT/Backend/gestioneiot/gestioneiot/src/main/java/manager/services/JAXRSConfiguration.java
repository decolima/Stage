package manager.services;

import manager.mqtt.MqttServiceBean;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.eclipse.microprofile.auth.LoginConfig;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import javax.inject.Inject;
//import javax.ejb.*;

/**
 * Configures a JAX-RS endpoint. Delete this class, if you are not exposing
 * JAX-RS resources in your application.
 *
 * @author airhacks.com
 */
@SecurityScheme(securitySchemeName = "jwt", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "jwt")
@LoginConfig(authMethod = "MP-JWT", realmName = "MP-JWT")
@DeclareRoles({"Admin", "User", "Maintenance"})
@ApplicationPath("api")
public class JAXRSConfiguration extends Application {

    @Inject // Injeta a classe MqttServiceBean
    private MqttServiceBean mqttService;

    @PostConstruct
    public void initialize() {
        try {
            mqttService.initialize();
        } catch (Exception e) {
            System.out.println("Erro ao Inicializar MQTT");
        }
        
    }

    @PreDestroy
    public void cleanup() {
        mqttService.cleanup();
    }

}
