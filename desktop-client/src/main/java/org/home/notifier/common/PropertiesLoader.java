package org.home.notifier.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private static Properties applicationProperties = null;

    public static Properties getProperties() {
        if (applicationProperties == null) {
            try (InputStream input = PropertiesLoader.class.getClassLoader().getResourceAsStream("application.properties")) {
                if (input == null) {
                    throw new RuntimeException("Fail to find application properties");
                }
                applicationProperties = new Properties();
                applicationProperties.load(input);
            } catch (IOException e) {
                throw new RuntimeException("Fail to load application properties");
            }
        }
        return applicationProperties;
    }
}
