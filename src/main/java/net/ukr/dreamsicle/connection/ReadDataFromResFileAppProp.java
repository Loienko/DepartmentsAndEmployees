package net.ukr.dreamsicle.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * class for download resources from application.properties
 */
public class ReadDataFromResFileAppProp {
    private final Properties properties = new Properties();

    public ReadDataFromResFileAppProp() {
        ReadDataFromResFileAppProp.loadProperties(properties, "application.properties");
    }

    private static void loadProperties(Properties properties, String classPathUrl) {
        try (InputStream in = ReadDataFromResFileAppProp.class.getClassLoader().getResourceAsStream(classPathUrl)) {
            properties.load(in);
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't load properties from classpath:" + classPathUrl, e);
        }
    }

    public String getProperties(String property) {
        return properties.getProperty(property);
    }
}