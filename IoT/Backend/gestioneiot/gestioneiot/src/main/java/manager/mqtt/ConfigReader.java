package manager.mqtt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 *
 * @author andrelima
 */

public class ConfigReader {
    private static final String PROPERTIES_FILE = "config.properties";

    private static Properties properties = new Properties();

    static {
        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getBrokerUrl() {
        return properties.getProperty("brokerUrl");
    }

    public static String getClientId() {
        return properties.getProperty("clientId");
    }
}
