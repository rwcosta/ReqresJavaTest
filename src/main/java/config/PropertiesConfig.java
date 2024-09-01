package config;

import exception.config.PropertyNotFoundException;
import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

public class PropertiesConfig {
    @Getter
    private static final Map<String, Object> properties = loadProperties();

    public static Map<String, Object> loadProperties() {
        Yaml yaml = new Yaml();
        Map<String, Object> properties;

        InputStream inputStream = PropertiesConfig.class.getClassLoader().getResourceAsStream("application.yaml");

        if (Objects.nonNull(inputStream))
            properties = yaml.load(inputStream);
        else
            throw new RuntimeException("Application properties not found");

        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }

    public static Map<String, Object> getProperty(String property) {
        return getProperty(properties, property);
    }

    public static Map<String, Object> getProperty(Map<String, Object> map, String property) {
        Map<String, Object> propertyMap = (Map<String, Object>) map.get(property);
        if (Objects.isNull(propertyMap))
            throw new PropertyNotFoundException(property);

        return propertyMap;
    }
}
