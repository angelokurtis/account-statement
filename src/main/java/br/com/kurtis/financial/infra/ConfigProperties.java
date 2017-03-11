package br.com.kurtis.financial.infra;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigProperties {

    private static final Properties properties;

    static {
        properties = new Properties();
        final InputStream resource = ConfigProperties.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            properties.load(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String propertyKey) {
        return properties.getProperty(propertyKey);
    }

}
