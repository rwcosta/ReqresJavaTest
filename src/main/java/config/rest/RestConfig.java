package config.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.PropertiesConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

import static config.PropertiesConfig.getProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestConfig {
    private static final String REST_PROPERTY = "rest";

    private String baseURL;
    private Integer port;
    private String basePath;
    private Map<String, Object> headers;

    public static RestConfig loadConfig() {
        return new ObjectMapper()
                .convertValue(getProperty(PropertiesConfig.getProperties(), REST_PROPERTY), RestConfig.class);
    }
}
